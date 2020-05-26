package by.epam.nikita.domain.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Set;


@Entity
@Getter
@Setter
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Заполни поле")
    private String courseName;

    /**
     * One teacher can teach one or more courses
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;


    @ManyToMany(mappedBy = "courses")
    private Set<Student> students;

    @OneToMany(fetch = FetchType.LAZY,
            mappedBy = "course")
    private List<Archive> archiveNotes;
}
