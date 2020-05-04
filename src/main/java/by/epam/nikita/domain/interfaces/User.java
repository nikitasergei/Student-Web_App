package by.epam.nikita.domain.interfaces;

import by.epam.nikita.domain.models.Roles;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Set;

public interface User extends UserDetails {

    String getEmail();

    String getUsername();

    String getActivationCode();

    String getPassword();

    Set<Roles> getRoles();

    String getFilename();

    void setRoles(Set<Roles> role);

    void setActive(boolean active);

    void setActivationCode(String activationCode);

    void setPassword(String password);

    void setUsername(String username);

    void setEmail(String email);

    void setFilename(String filename);
}
