package by.epam.nikita.repository;

import by.epam.nikita.domain.models.Teacher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public interface TeacherRepo extends CrudRepository<Teacher, Long> {

    Optional<Teacher> findById(Long id);

    Page<Teacher> findAll(Pageable pageable);

    Teacher findByUsername(String username);

    Teacher findByActivationCode(String code);
}
