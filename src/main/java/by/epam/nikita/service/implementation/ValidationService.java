package by.epam.nikita.service.implementation;

import by.epam.nikita.DTO.CaptchaResponseDto;
import by.epam.nikita.domain.interfaces_marker.User;
import by.epam.nikita.domain.models.Roles;
import by.epam.nikita.domain.models.Student;
import by.epam.nikita.domain.models.Teacher;
import by.epam.nikita.service.serviceInterfaces.CourseService;
import by.epam.nikita.service.serviceInterfaces.StudentService;
import by.epam.nikita.service.serviceInterfaces.TeacherService;
import by.epam.nikita.service.serviceInterfaces.ValidationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

@Service
public class ValidationService implements ValidationRepo {

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private CourseService courseService;

    /**
     * Check is {@param user} instance of Student.class
     *
     * @param user - user for check
     * @return true if user is instance of Student.class
     */
    @Override
    public boolean isStudent(User user) {
        return user.getClass().equals(Student.class);
    }

    /**
     * Check is current User contains Roles.STUDENT
     *
     * @param authentication - represents the token for an authentication request or for an authenticated principal
     *                       once the request has been processed by the method
     * @return true if current User contains Roles.STUDENT
     */
    @Override
    public boolean isStudent(Authentication authentication) {
        return authentication.getAuthorities().contains(Roles.STUDENT);
    }

    /**
     * Check is {@param user} instance of Teacher.class
     *
     * @param user - user for check
     * @return true if user is instance of Teacher.class
     */
    @Override
    public boolean isTeacher(User user) {
        return user.getClass().equals(Teacher.class);
    }

    /**
     * Check is current User contains Roles.TEACHER
     *
     * @param authentication - represents the token for an authentication request or for an authenticated principal
     *                       once the request has been processed by the method
     * @return true if current User contains Roles.TEACHER
     */
    @Override
    public boolean isTeacher(Authentication authentication) {
        return authentication.getAuthorities().contains(Roles.TEACHER);
    }


    /**
     * Check by {@param id} is User present in database like Teacher
     *
     * @param id - id of User which must be checked
     * @return true if he present in database
     */
    @Override
    public boolean isTeacherPresentInDB(Long id) {
        return teacherService.getTeacherById(id) != null;
    }

    /**
     * Check by {@param name} is User present in database like Teacher
     *
     * @param name - name of User which must be checked
     * @return true if he present in database
     */
    @Override
    public boolean isTeacherPresentInDB(String name) {
        return teacherService.getTeacherByUsername(name) != null;
    }

    /**
     * Check by {@param id} is User present in database like Student
     *
     * @param id - id of User which must be checked
     * @return true if he present in database
     */
    @Override
    public boolean isStudentPresentInDB(Long id) {
        return studentService.getStudentById(id) != null;
    }

    /**
     * Check by {@param name} is User present in database like Student
     *
     * @param name - name of User which must be checked
     * @return true if he present in database
     */
    @Override
    public boolean isStudentPresentInDB(String name) {
        return (studentService.getStudentByUsername(name) != null);
    }

    /**
     * Check is User with {@param id} exist in database like Student or Teacher and return him like User
     *
     * @param id - id of User which must be checked
     * @return User from database or else null
     */
    @Override
    public User isUserExistById(Long id) {
        if (isTeacherPresentInDB(id))
            return teacherService.getTeacherById(id);
        if (isStudentPresentInDB(id))
            return studentService.getStudentById(id);
        else return null;
    }

    /**
     * Check by {@param id} is Course present in database
     *
     * @param id - id of Course which must be checked
     * @return true if Course present in database
     */
    @Override
    public boolean isNewCourse(Long id) {
        return courseService.getById(id) != null;
    }

    /**
     * Check by {@param name} is Course present in database
     *
     * @param name - name of Course which must be checked
     * @return true if Course present in database
     */
    @Override
    public boolean isCoursePresentInDB(String name) {
        return courseService.getByCourseName(name) != null;
    }

    /**
     * @param file - data to check
     * @return true if {@param file} not null and contains some information
     */
    @Override
    public boolean isFailValid(MultipartFile file) {
        return (file != null && !Objects.requireNonNull(file.getOriginalFilename()).isEmpty());
    }

    /**
     * Method check input params and fill {@param model}  in accordance with them
     *
     * @param model              - definition of holder for model attributes. Using for adding attributes to the model.
     * @param captchaResponseDto - parameter for check captcha
     * @param passwordConfirm    - parameter for confirm password
     * @param user               - user which must be saved in database
     * @return - filled Model
     */
    @Override
    public Model validateState(Model model,
                               CaptchaResponseDto captchaResponseDto,
                               String passwordConfirm,
                               User user) {
        if (!captchaResponseDto.isSuccess()) {
            model.addAttribute("captchaError", "Fill Captcha");
        }
        boolean isConfirmEmpty = StringUtils.isEmpty(passwordConfirm);

        if (isConfirmEmpty) {
            model.addAttribute("passwordConfirmError", "Password confirmation can't be empty");
        }
        if (user.getPassword() != null && !user.getPassword().equals(passwordConfirm)) {
            model.addAttribute("passwordError", "Passwords is different!");
        }
        return model;
    }
}
