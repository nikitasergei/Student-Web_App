package by.epam.nikita.service.implementation;

import by.epam.nikita.domain.interfaces_marker.User;
import by.epam.nikita.domain.models.Roles;
import by.epam.nikita.domain.models.Student;
import by.epam.nikita.domain.models.Teacher;
import by.epam.nikita.repository.StudentRepo;
import by.epam.nikita.repository.TeacherRepo;
import by.epam.nikita.service.serviceInterfaces.MailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.UUID;

@Service
public class UserServiceImpl implements by.epam.nikita.service.serviceInterfaces.UserService {

    @Autowired
    private MailSender mailSenderService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private StudentRepo studentRepo;

    @Autowired
    private TeacherRepo teacherRepo;

    @Autowired
    private ValidationService validationService;

    /**
     * This method check is teacher with {@param username} already exists and load them.
     *
     * @param username - name of Teacher which we try to load.
     * @return Teacher with {@param username}
     * @throws UsernameNotFoundException - if Teacher with {@param username} doesn't exist
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Teacher teacher = teacherRepo.findByUsername(username);
        if (!validationService.isTeacherPresentInDB(username)) {
            Student student = studentRepo.findByUsername(username);
            if (!validationService.isStudentPresentInDB(username)) {
                throw new UsernameNotFoundException("User with name " + username + " not found!");
            } else return student;
        } else return teacher;
    }

    /**
     * Method send mail to {@param user} with activation link
     *
     * @param user - message recipient
     */
    protected <T> void sendMessage(User user) {
        if (!StringUtils.isEmpty(user.getEmail())) {
            String message = String.format(
                    "Hello, %s! \n" +
                            "Welcome to Student Schedule Web App. Please, visit next link: " + "http://localhost:8080/activate/%s",
                    user.getUsername(),
                    user.getActivationCode()
            );
            mailSenderService.send(user.getEmail(), "Activation Code!", message);
        }
    }

    /**
     * This method check is {@param user} exists as record in database and add {@param user} to database
     * if it's not
     *
     * @param user - student which must be added to database
     * @return false if {@param user} already exists and true if was added {@param user}
     */
    @Override
    public User setUserDetails(User user) {
        user.setActive(false);
        user.setActivationCode(UUID.randomUUID().toString());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (validationService.isStudent(user)) {
            setStudentRole(user);
        } else if (validationService.isTeacher(user)) {
            setTeacherRole(user);
        }
        sendMessage(user);
        return user;
    }

    /**
     * Method set to {@param user} Roles.STUDENT, and save him in database
     *
     * @param user - user for role setting
     */
    private void setStudentRole(User user) {
        user.setRoles(Collections.singleton(Roles.STUDENT));
        studentRepo.save((Student) user);
    }

    /**
     * Method set to {@param user} Roles.TEACHER, and save him in database
     *
     * @param user - user for role setting
     */
    private void setTeacherRole(User user) {
        user.setRoles(Collections.singleton(Roles.TEACHER));
        teacherRepo.save((Teacher) user);
    }

    /**
     * Method prepare {@param user} to activate and save him in database
     *
     * @param user - user for activation
     * @return true if {@param user} was activated
     */
    @Override
    public boolean prepareToActivateUser(User user) {
        user.setActivationCode(null);
        user.setActive(true);
        saveUser(user);
        return true;
    }

    /**
     * Method search user by {@param code} in database and try activate his account
     *
     * @param code - code for activate Student from his email
     * @return - false if records in table hasn't {@param code} and {@return true} if Student was successfully activated
     */
    @Override
    public boolean activateUser(String code) {
        Student studentByCode = studentRepo.findByActivationCode(code);
        Teacher teacherByCode = teacherRepo.findByActivationCode(code);
        if (studentByCode == null) {
            if (teacherByCode == null)
                return false;
            else return prepareToActivateUser(teacherByCode);
        } else return prepareToActivateUser(studentByCode);
    }


    /**
     * Method defines which class belongs {@param user} and save him in database
     *
     * @param user - Student or Teacher to save
     * @return user - user which was saved in database
     */
    @Override
    public User saveUser(User user) {
        if (user.getClass().equals(Student.class)) {
            return studentRepo.save((Student) user);
        } else if (user.getClass().equals(Teacher.class)) {
            return teacherRepo.save((Teacher) user);
        }
        return user;
    }

    /**
     * Method check, are there difference between {@param user}'s parameters and other method's parameters
     * If they are different, method set {@param user} new parameters and save {@param user} in database,
     * if there are no difference between them, method save {@param user} with old parameters
     *
     * @param user     - User which profile must be updated
     * @param password - password for update
     * @param email    - email for update
     * @param filename - file's name for update
     */
    @Override
    public void updateProfile(User user, String password, String email, String filename) {
        String userEmail = user.getEmail();
        String userFilename = user.getFilename();
        boolean isEmailChanged = email != null && email.equals(userEmail);
        boolean isFilenameChanged = (filename != null && !filename.equals(userFilename)
        );
        if (isEmailChanged) {
            user.setEmail(email);
            if (!StringUtils.isEmpty(email)) {
                user.setActivationCode(UUID.randomUUID().toString());
            }
        }
        if (!StringUtils.isEmpty(password)) {
            user.setPassword(passwordEncoder.encode(password));
        }
        if (isEmailChanged) {
            sendMessage(user);
        }
        if (isFilenameChanged) {
            user.setFilename(filename);
        }
        saveUser(user);
    }

    /**
     * Check is {@param user's} file equals {@param file} and return name of it, else
     * generate new filename for {@param file}
     *
     * @param user - User, who load {@param file}
     * @param file - a representation of an uploaded file received in a multipart request
     * @param path - path for create new File
     * @return name of {@param file} as String
     */
    @Override
    public String generateFileName(User user, MultipartFile file, String path) {
        String resultFilename = user.getFilename();
        if (validationService.isFailValid(file)) {
            createNewFile(path);
            String uuidFile = UUID.randomUUID().toString();
            resultFilename = uuidFile + "." + file.getOriginalFilename();
            try {
                file.transferTo(new File(path + "/" + resultFilename));
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return resultFilename;
    }

    /**
     * Check is file exist otherwise create new File
     *
     * @param path - path to create File
     * @return created file
     */
    public File createNewFile(String path) {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdir();
        }
        return file;
    }
}
