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
public class TeacherDTO  {

    private Long id;

    private String username;

    private String password;

    private String email;

    private Set<Roles> roles;

    private List<Course> courseSet;

    private List<Archive> archiveNotes;

    private String filename;

    private boolean active;

    private String activationCode;

    public boolean isActive() {
        return active;
    }

    public boolean isTeacher() {
        return true;
    }

    public TeacherDTO() {
    }
}
