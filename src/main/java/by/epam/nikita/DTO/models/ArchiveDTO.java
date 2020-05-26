package by.epam.nikita.DTO.models;

import by.epam.nikita.domain.models.Course;
import by.epam.nikita.domain.models.Student;
import by.epam.nikita.domain.models.Teacher;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ArchiveDTO  {

    private Long id;

    private Course course;

    private Teacher teacher;

    private Student student;

    private Integer rating;

    public ArchiveDTO() {
    }
}
