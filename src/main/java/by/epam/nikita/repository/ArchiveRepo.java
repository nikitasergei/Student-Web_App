package by.epam.nikita.repository;

import by.epam.nikita.domain.models.Archive;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ArchiveRepo extends CrudRepository<Archive, Long> {

    Optional<Archive> findById(Long id);

    Page<Archive> findAll(Pageable pageable);

    Page<Archive> findAllByStudentId(Long studentId, Pageable pageable);

    Page<Archive> findAllByTeacherId(Long teacherId, Pageable pageable);

    Page<Archive> findAllByCourseId(Long courseId, Pageable pageable);
}
