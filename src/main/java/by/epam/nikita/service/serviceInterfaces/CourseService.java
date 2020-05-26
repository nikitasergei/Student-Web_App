package by.epam.nikita.service.serviceInterfaces;

import by.epam.nikita.domain.models.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CourseService {

    /**
     * Method try get course from database by {@param id} and return it.
     * If there isn't recorded course with {@param id} in database method {@return null}.
     *
     * @param id - id of the course which we are looking for
     * @return course with {@param id} or { null }
     */
    Course getById(Long id);

    /**
     * This method return all records from the table as a page(s) of courses
     *
     * @param pageable - parameter for pagination information
     * @return page(s) of courses
     */
    Page<Course> getAll(Pageable pageable);

    /**
     * Method try to get list of teacher's courses and view it as a page(s)
     *
     * @param teacherId - teacher's id
     * @param pageable  - parameter for pagination information
     * @return list of courses as a page(s)
     */
    Page<Course> getByTeacherId(Long teacherId, Pageable pageable);

    /**
     * Method try to get for database course with {@param courseName}
     *
     * @param courseName - course's name
     * @return course with {@param courseName}
     */
    Course getByCourseName(String courseName);

    /**
     * This method check, is {@param course} recorded in database and try
     * to save it in one of two ways: with new ID or with old ID.
     *
     * @param course - course which must be saved
     * @return true if {@param course} was saved
     */
    boolean saveCourse(Course course);
}
