package by.epam.nikita.domain.models;

import org.springframework.security.core.GrantedAuthority;

public enum Roles implements GrantedAuthority {
    ADMIN, STUDENT, TEACHER;

    @Override
    public String getAuthority() {
        return name();
    }
}
