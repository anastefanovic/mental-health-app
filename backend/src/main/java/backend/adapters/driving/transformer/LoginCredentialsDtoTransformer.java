package backend.adapters.driving.transformer;

import backend.adapters.driving.model.LoginCredentialsDto;
import backend.domain.model.LoginCredentials;
import backend.domain.model.enums.UserType;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class LoginCredentialsDtoTransformer implements Transformer<LoginCredentials, LoginCredentialsDto> {

    @Override
    public LoginCredentialsDto fromDomain(LoginCredentials loginCredentials) {
        return new LoginCredentialsDto(
                loginCredentials.getEmail(),
                loginCredentials.getPassword(),
                loginCredentials.getUserType().toString()
        );
    }

    @Override
    public LoginCredentials toDomain(LoginCredentialsDto loginCredentialsDto) {
        return new LoginCredentials(
                Objects.requireNonNull(loginCredentialsDto.getEmail()),
                Objects.requireNonNull(loginCredentialsDto.getPassword()),
                Objects.requireNonNull(UserType.valueOf(loginCredentialsDto.getUserType()))
        );
    }
}
