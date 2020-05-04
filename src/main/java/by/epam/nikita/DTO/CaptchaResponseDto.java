package by.epam.nikita.DTO;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

import java.util.Set;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CaptchaResponseDto {

    private boolean success;

    public boolean isSuccess() {
        return success;
    }

    @JsonAlias("error-codes")
    private Set<String> errorCodes;
}