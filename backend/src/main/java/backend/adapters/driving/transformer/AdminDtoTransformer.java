package backend.adapters.driving.transformer;

import backend.adapters.driving.model.AdminDto;
import backend.domain.model.Admin;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class AdminDtoTransformer implements Transformer <Admin, AdminDto> {

    @Override
    public AdminDto fromDomain(Admin admin) {
        return new AdminDto(
                admin.getId(),
                admin.getEmail(),
                admin.getPassword(),
                admin.getFirstName(),
                admin.getLastName(),
                admin.isValidationFailed(),
                admin.getValidationMessage()
        );
    }

    @Override
    public Admin toDomain(AdminDto adminDto) {
        return new Admin(
                adminDto.getId(),
                Objects.requireNonNull(adminDto.getEmail()),
                Objects.requireNonNull(adminDto.getPassword()),
                Objects.requireNonNull(adminDto.getFirstName()),
                Objects.requireNonNull(adminDto.getLastName())
        );
    }
}
