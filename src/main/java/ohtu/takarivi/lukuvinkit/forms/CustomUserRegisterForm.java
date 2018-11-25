package ohtu.takarivi.lukuvinkit.forms;

import lombok.Getter;
import lombok.Setter;
import ohtu.takarivi.lukuvinkit.repository.CustomUserRepository;
import org.springframework.validation.BindingResult;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
public class CustomUserRegisterForm {

    private static final int NAME_MIN_LENGTH = 2;
    private static final int NAME_MAX_LENGTH = 64;
    private static final int USERNAME_MIN_LENGTH = 3;
    private static final int USERNAME_MAX_LENGTH = 32;
    private static final int PASSWORD_MIN_LENGTH = 8;
    private static final int PASSWORD_MAX_LENGTH = 256;

    @NotEmpty
    @Size(min = NAME_MIN_LENGTH, max = NAME_MAX_LENGTH, message = "Nimen pituus 2-32 merkkiä")
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "Nimi saa sisältää vain kirjaimia, numeroita ja alaviivoja")
    private String name;
    @NotEmpty
    @Size(min = USERNAME_MIN_LENGTH, max = USERNAME_MAX_LENGTH, message = "Käyttäjänimen pituus 3-32 merkkiä")
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "Nimimerkki saa sisältää vain kirjaimia, numeroita ja alaviivoja")
    private String username;
    @NotEmpty
    @Size(min = PASSWORD_MIN_LENGTH, max = PASSWORD_MAX_LENGTH, message = "Salasanan pituus 8-32 merkkiä")
    private String password;
    private String verifyPassword;

    /**
     * Used to run additional validation for this form.
     * 
     * @param result The BindingResult that value rejections are submitted to.
     * @param customUserRepository The CustomUserRepository for looking up existing users.
     */
    public void validateRest(BindingResult result, CustomUserRepository customUserRepository) {
        if (checkIfUsernameTaken(customUserRepository)) {
            result.rejectValue("username", "", "Käyttäjänimi on jo olemassa");
        }
        if (checkIfPasswordsDiffer()) {
            result.rejectValue("verifyPassword", "", "Salasanat eivät täsmää");
        }
    }

    private boolean checkIfUsernameTaken(CustomUserRepository customUserRepository) {
        return customUserRepository.findByUsername(username) != null;
    }

    private boolean checkIfPasswordsDiffer() {
        return !password.equals(verifyPassword);
    }

}
