package backend.adapters.driving.transformer;

import backend.adapters.driving.model.ClientDto;
import backend.domain.model.Client;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Objects;


@Component
public class ClientDtoTransformer implements Transformer<Client, ClientDto> {
    @Override
    public ClientDto fromDomain(Client client) {
        String birthday = (client.getBirthday() != null) ? DataFormatter.dateToString(client.getBirthday()) : null;
        return new ClientDto(
                client.getId(),
                client.getEmail(),
                client.getPassword(),
                client.getFirstName(),
                client.getLastName(),
                client.getPhoneNumber(),
                client.getGender(),
                birthday,
                client.isValidationFailed(),
                client.getValidationMessage()
        );
    }

    @Override
    public Client toDomain(ClientDto clientDto) {
        LocalDate birthday = (clientDto.getBirthday() != null) ? DataFormatter.stringToDate(clientDto.getBirthday()) : null;
        return new Client(
                clientDto.getId(),
                Objects.requireNonNull(clientDto.getEmail()),
                Objects.requireNonNull(clientDto.getPassword()),
                Objects.requireNonNull(clientDto.getFirstName()),
                Objects.requireNonNull(clientDto.getLastName()),
                Objects.requireNonNull(clientDto.getPhoneNumber()),
                clientDto.getGender(),
                birthday
        );
    }
}
