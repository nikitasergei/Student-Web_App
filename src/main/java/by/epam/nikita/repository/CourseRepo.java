package by.epam.nikita.repository;

import by.epam.nikita.domain.models.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Component
public interface CourseRepo extends CrudRepository<Course, Long> {

    Optional<Course> findById(Long id);

    Course findByCourseName(String courseName);

    Page<Course> findAll(Pageable pageable);

    Page<Course> findByTeacherId(Long teacherId, Pageable pageable);
}
