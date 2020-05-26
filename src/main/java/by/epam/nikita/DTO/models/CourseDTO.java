package by.epam.nikita.DTO.models;

import by.epam.nikita.domain.models.Archive;
import by.epam.nikita.domain.models.Student;
import by.epam.nikita.domain.models.Teacher;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
public class CourseDTO  {
    private Long id;

    private String courseName;

    private Teacher teacher;

    private Set<Student> students;

    private List<Archive> archiveNotes;

    public CourseDTO() {
    }
}
