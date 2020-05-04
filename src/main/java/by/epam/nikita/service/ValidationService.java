package by.epam.nikita.service;

import by.epam.nikita.domain.interfaces.User;
import by.epam.nikita.domain.models.Roles;
import by.epam.nikita.domain.models.Student;
import by.epam.nikita.domain.models.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class ValidationService {

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
    public boolean isStudent(Authentication authentication) {
        return authentication.getAuthorities().contains(Roles.STUDENT);
    }

    /**
     * Check is {@param user} instance of Teacher.class
     *
     * @param user - user for check
     * @return true if user is instance of Teacher.class
     */
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
    public boolean isTeacher(Authentication authentication) {
        return authentication.getAuthorities().contains(Roles.TEACHER);
    }


    /**
     * Check by {@param id} is User present in database like Teacher
     *
     * @param id - id of User which must be checked
     * @return true if he present in database
     */
    public boolean isTeacherPresentInDB(Long id) {
        return teacherService.getTeacherById(id) != null;
    }

    /**
     * Check by {@param name} is User present in database like Teacher
     *
     * @param name - name of User which must be checked
     * @return true if he present in database
     */
    public boolean isTeacherPresentInDB(String name) {
        return teacherService.getTeacherByUsername(name) != null;
    }

    /**
     * Check by {@param id} is User present in database like Student
     *
     * @param id - id of User which must be checked
     * @return true if he present in database
     */
    public boolean isStudentPresentInDB(Long id) {
        return studentService.getStudentById(id) != null;
    }

    /**
     * Check by {@param name} is User present in database like Student
     *
     * @param name - name of User which must be checked
     * @return true if he present in database
     */
    public boolean isStudentPresentInDB(String name) {
        return (studentService.getStudentByUsername(name) != null);
    }

    /**
     * Check is User with {@param id} exist in database like Student or Teacher and return him like User
     *
     * @param id - id of User which must be checked
     * @return User from database or else null
     */
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
    public boolean isNewCourse(Long id) {
        return courseService.getById(id) != null;
    }

    /**
     * Check by {@param name} is Course present in database
     *
     * @param name - name of Course which must be checked
     * @return true if Course present in database
     */
    public boolean isCoursePresentInDB(String name) {
        return courseService.getByCourseName(name) != null;
    }


}
