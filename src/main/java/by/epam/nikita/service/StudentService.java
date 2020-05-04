package by.epam.nikita.service;

import by.epam.nikita.domain.interfaces.User;
import by.epam.nikita.domain.models.Course;
import by.epam.nikita.domain.models.Student;
import by.epam.nikita.repository.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class StudentService {

    @Autowired
    private StudentRepo studentRepo;

    @Autowired
    private UserService userService;

    @Autowired
    private CourseService courseService;

    /**
     * Method try to get all Students from database
     *
     * @param pageable - parameter for pagination information
     * @return list of Students as a page(s)
     */
    public Page<Student> getAllStudents(Pageable pageable) {
        return studentRepo.findAll(pageable);
    }

    /**
     * Method try to get Student from database by {@param studentName}
     *
     * @param username - name of Student
     * @return Student with {@param studentName}
     */
    public Student getStudentByUsername(String username) {
        return studentRepo.findByUsername(username);
    }


    /**
     * Method try to get Student by {@param id}
     *
     * @param id - id of Student
     * @return Student with {@param id} if he exist in database else {@return null}
     */
    public Student getStudentById(Long id) {
        return studentRepo.findById(id).orElse(null);
    }


    /**
     * This method check is {@param student} exists as record in database and add {@param student} to database
     * if it's not
     *
     * @param student - student which must be added to database
     * @return false if {@param student} already exists and true if was added {@param student}
     */
    public boolean addStudent(Student student) {
        Student studentFromDB = studentRepo.findByUsername(student.getUsername());
        if (studentFromDB != null) {        //this Student already exist in database
            return false;
        } else {
            User preparedUser = userService.setUserDetails(student);
            return preparedUser != null;
        }
    }

    /**
     * Method try to get all courses which attends student with {@param id}
     *
     * @param id - id of student
     * @return page(s) of courses or else {@return null}
     */
    public Page<Course> getByStudentId(Long id) {
        Student student = studentRepo.findById(id).orElse(null);
        if (student != null) {
            return new PageImpl<>(new ArrayList<>(student.getCourses()));
        } else return null;
    }

    /**
     * Method try to add Student with {@param studentId} on Course with {@param courseId}
     *
     * @param studentId - current Student's id
     * @param courseId  - current Course's id
     * @return true if Student was added on a Course and false if he wasn'to
     */
    public boolean isStudentStartLearnCourse(Long studentId, Long courseId) {
        Course course = courseService.getById(courseId);
        Student student = studentRepo.findById(studentId).orElse(null);
        if (student != null && course != null) {
            if (!student.getCourses().contains(course)) {
                student.getCourses().add(course);
                return true;
            }
        }
        return false;
    }
}

