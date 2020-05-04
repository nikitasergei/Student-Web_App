package by.epam.nikita.service;

import by.epam.nikita.domain.models.Course;
import by.epam.nikita.repository.CourseRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class CourseService {

    @Autowired
    private CourseRepo courseRepo;

    @Autowired
    private ValidationService validationService;

    /**
     * Method try get course from database by {@param id} and return it.
     * If there isn't recorded course with {@param id} in database method {@return null}.
     *
     * @param id - id of the course which we are looking for
     * @return course with {@param id} or { null }
     */
    public Course getById(Long id) {
        Optional<Course> course = courseRepo.findById(id);
        return course.orElse(null);
    }

    /**
     * This method return all records from the table as a page(s) of courses
     *
     * @param pageable - parameter for pagination information
     * @return page(s) of courses
     */
    public Page<Course> getAll(Pageable pageable) {
        return courseRepo.findAll(pageable);
    }

    /**
     * Method try to get list of teacher's courses and view it as a page(s)
     *
     * @param teacherId - teacher's id
     * @param pageable  - parameter for pagination information
     * @return list of courses as a page(s)
     */
    public Page<Course> getByTeacherId(Long teacherId, Pageable pageable) {
        return courseRepo.findByTeacherId(teacherId, pageable);
    }

    /**
     * Method try to get for database course with {@param courseName}
     *
     * @param courseName - course's name
     * @return course with {@param courseName}
     */
    public Course getByCourseName(String courseName) {
        return courseRepo.findByCourseName(courseName);
    }

    /**
     * This method check, is {@param course} recorded in database and try
     * to save it in one of two ways: with new ID or with old ID.
     *
     * @param course - course which must be saved
     * @return true if {@param course} was saved
     */
    public boolean saveCourse(Course course) {
        if (validationService.isNewCourse(course.getId())) {
            if (validationService.isCoursePresentInDB(course.getCourseName())) {
                courseRepo.save(course);
                return true;
            } else return false;
        } else courseRepo.save(course);
        return true;
    }
}
