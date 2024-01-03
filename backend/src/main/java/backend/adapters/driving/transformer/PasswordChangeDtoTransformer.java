package backend.adapters.driving.transformer;

import backend.adapters.driving.model.PasswordChangeDto;
import backend.domain.model.PasswordChange;
import backend.domain.model.enums.UserType;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class PasswordChangeDtoTransformer implements Transformer<PasswordChange, PasswordChangeDto> {

    @Override
    public PasswordChangeDto fromDomain(PasswordChange passwordChange) {
        return new PasswordChangeDto(
                passwordChange.getUserId(),
                passwordChange.getUserType().toString(),
                passwordChange.getOldPassword(),
                passwordChange.getNewPassword()
        );
    }

    @Override
    public PasswordChange toDomain(PasswordChangeDto passwordChangeDto) {
        return new PasswordChange(
                Objects.requireNonNull(passwordChangeDto.getUserId()),
                UserType.valueOf( Objects.requireNonNull(passwordChangeDto.getUserType())),
                Objects.requireNonNull(passwordChangeDto.getOldPassword()),
                Objects.requireNonNull(passwordChangeDto.getNewPassword())
        );
    }
}
