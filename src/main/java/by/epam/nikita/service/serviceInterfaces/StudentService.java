package by.epam.nikita.service.serviceInterfaces;

import by.epam.nikita.domain.models.Course;
import by.epam.nikita.domain.models.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StudentService {

    /**
     * Method try to get all Students from database
     *
     * @param pageable - parameter for pagination information
     * @return list of Students as a page(s)
     */
    Page<Student> getAllStudents(Pageable pageable);

    /**
     * Method try to get Student from database by {@param studentName}
     *
     * @param username - name of Student
     * @return Student with {@param studentName}
     */
    Student getStudentByUsername(String username);

    /**
     * Method try to get Student by {@param id}
     *
     * @param id - id of Student
     * @return Student with {@param id} if he exist in database else {@return null}
     */
    Student getStudentById(Long id);

    /**
     * This method check is {@param student} exists as record in database and add {@param student} to database
     * if it's not
     *
     * @param student - student which must be added to database
     * @return false if {@param student} already exists and true if was added {@param student}
     */
    boolean addStudent(Student student);

    /**
     * Method try to get all courses which attends student with {@param id}
     *
     * @param id - id of student
     * @return page(s) of courses or else {@return null}
     */
    Page<Course> getByStudentId(Long id);


    /**
     * Method try to add Student with {@param studentId} on Course with {@param courseId}
     *
     * @param studentId - current Student's id
     * @param courseId  - current Course's id
     * @return true if Student was added on a Course and false if he wasn'to
     */
    boolean isStudentStartLearnCourse(Long studentId, Long courseId);

}
