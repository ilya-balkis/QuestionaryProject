package by.questionary.security.payload.request;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
public class LoginRequest {

    @NotBlank(message = "Please fill field name")
    @Length(min = 1, max = 30, message = "Name must be 1 or 30 symbols")
    private String name;

    @NotBlank(message = "Please fill field password")
    @Length(min = 6, max = 20, message = "Password must be 6 or 20 symbols")
    private String password;

}
