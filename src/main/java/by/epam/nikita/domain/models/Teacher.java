package by.epam.nikita.domain.models;

import by.epam.nikita.domain.interfaces_marker.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
public class Teacher implements UserDetails, User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Teacher must be named")
    private String username;

    @NotBlank(message = "Password confirmation can't be empty")
    private String password;

    @Email(message = "Email is not correct")
    @NotBlank(message = "Email can't be empty")
    private String email;

    @ElementCollection(targetClass = Roles.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "teacher_role", joinColumns = @JoinColumn(name = "teacher_id"))
    @Enumerated(EnumType.STRING)
    private Set<Roles> roles;

    /**
     * One teacher can teach several courses
     */
    @OneToMany(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            mappedBy = "teacher")
    private List<Course> courseSet;

    @OneToMany(fetch = FetchType.LAZY,
            cascade = CascadeType.PERSIST,
            orphanRemoval = true,
            mappedBy = "teacher")
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isActive();
    }

}
