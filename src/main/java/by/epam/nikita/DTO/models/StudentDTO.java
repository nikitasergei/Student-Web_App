package by.epam.nikita.DTO.models;

import by.epam.nikita.domain.models.Archive;
import by.epam.nikita.domain.models.Course;
import by.epam.nikita.domain.models.Roles;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
public class StudentDTO  {

    private Long id;

    private String username;

    private String password;

    private String email;

    private Set<Roles> roles;

    private Set<Course> courses;

    private List<Archive> archiveNotes;

    private boolean active;

    private String activationCode;

    private String filename;

    public StudentDTO() {
    }
}
