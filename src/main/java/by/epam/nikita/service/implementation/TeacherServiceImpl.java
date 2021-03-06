package by.epam.nikita.service.implementation;


import by.epam.nikita.domain.interfaces_marker.User;
import by.epam.nikita.domain.models.Teacher;
import by.epam.nikita.repository.TeacherRepo;
import by.epam.nikita.service.serviceInterfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TeacherServiceImpl implements by.epam.nikita.service.serviceInterfaces.TeacherService {

    @Autowired
    private TeacherRepo teacherRepo;

    @Autowired
    private UserService userService;

    /**
     * Method try to get all Teachers from database
     *
     * @param pageable - parameter for pagination information
     * @return list of teachers as a page(s)
     */
    @Override
    public Page<Teacher> getAllTeachers(Pageable pageable) {
        return teacherRepo.findAll(pageable);
    }

    /**
     * Method try to get Teacher from database by {@param teacherName}
     *
     * @param teacherName - teacher's name
     * @return teacher with {@param teacherName)
     */
    @Override
    public Teacher getTeacherByUsername(String teacherName) {
        return teacherRepo.findByUsername(teacherName);
    }

    /**
     * Method try to get Teacher from database by {@param id}
     *
     * @param teacherId - teacher's id
     * @return teacher with {@param id} or else {null}
     */
    @Override
    public Teacher getTeacherById(Long teacherId) {
        return teacherRepo.findById(teacherId).orElse(null);
    }


    /**
     * This method check is {@param teacher} exists as record in database and add {@param teacher} to database
     * if it's not
     *
     * @param teacher - student which must be added to database
     * @return false if {@param teacher} already exists and true if was added {@param teacher}
     */
    @Override
    public boolean addUser(Teacher teacher) {
        Teacher teacherFromDB = teacherRepo.findByUsername(teacher.getUsername());
        if (teacherFromDB != null) {
            return false;
        } else {
            User preparedUser = userService.setUserDetails(teacher);
            return preparedUser != null;
        }
    }
}


