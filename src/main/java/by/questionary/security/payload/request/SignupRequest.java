package by.questionary.security.payload.request;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class SignupRequest {

    @NotBlank(message = "Please fill field name")
    @Length(min = 1, max = 30, message = "Name must be 1 or 30 symbols")
    private String name;

    @NotBlank(message = "Please fill field email")
    @Email(message = "Email isn't correct")
    private String email;

    @NotBlank(message = "Please fill field password")
    @Length(min = 6, max = 20, message = "Password must be 6 or 20 symbols")
    private String password;

    @NotBlank(message = "Please fill field repeated password")
    @Length(min = 6, max = 20, message = "Repeated password must be 6 or 20 symbols")
    private String repeatedPassword;
}
