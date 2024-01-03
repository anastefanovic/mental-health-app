package backend.adapters.driving.transformer;

import backend.adapters.driving.exception.DataTypeNotSupportedException;
import backend.adapters.driving.model.AdminDto;
import backend.adapters.driving.model.ClientDto;
import backend.adapters.driving.model.TherapistDto;
import backend.adapters.driving.model.UserDto;
import backend.domain.model.Admin;
import backend.domain.model.Client;
import backend.domain.model.Therapist;
import backend.domain.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserDtoTransformer implements Transformer<User, UserDto> {
    private final AdminDtoTransformer adminTransformer;
    private final TherapistDtoTransformer therapistTransformer;
    private final ClientDtoTransformer clientTransformer;

    public UserDtoTransformer(
            AdminDtoTransformer adminTransformer,
            TherapistDtoTransformer therapistTransformer,
            ClientDtoTransformer clientTransformer
    ) {
        this.adminTransformer = adminTransformer;
        this.therapistTransformer = therapistTransformer;
        this.clientTransformer = clientTransformer;
    }

    @Override
    public UserDto fromDomain(User user) {
        if(user instanceof Admin) {
            return adminTransformer.fromDomain((Admin) user);
        }

        if(user instanceof Therapist) {
            return therapistTransformer.fromDomain((Therapist) user);
        }

        if(user instanceof Client) {
            return clientTransformer.fromDomain((Client) user);
        }

        throw new DataTypeNotSupportedException("Specified user type not supported");
    }

    @Override
    public User toDomain(UserDto userDto) {
        if(userDto instanceof AdminDto) {
            return adminTransformer.toDomain((AdminDto) userDto);
        }

        if(userDto instanceof TherapistDto) {
            return therapistTransformer.toDomain((TherapistDto) userDto);
        }

        if(userDto instanceof ClientDto) {
            return clientTransformer.toDomain((ClientDto) userDto);
        }

        throw new DataTypeNotSupportedException("User type " + userDto.getType() + " not supported");
    }
}
