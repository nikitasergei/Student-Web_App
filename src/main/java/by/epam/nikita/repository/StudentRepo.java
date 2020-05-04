package by.epam.nikita.repository;

import by.epam.nikita.domain.models.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepo extends JpaRepository<Student, Long> {

    Optional<Student> findById(Long Id);

    Student findByUsername(String username);

    Student findByActivationCode(String code);

    Page<Student> findAll(Pageable pageable);
}