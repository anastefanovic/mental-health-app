package backend.adapters.driven.transformer;

import backend.domain.model.Client;
import backend.adapters.driven.model.ClientJpa;
import org.springframework.stereotype.Component;

@Component
public class ClientTransformer implements Transformer<Client, ClientJpa> {
    @Override
    public ClientJpa domainToEntity(Client domain) {
        return new ClientJpa(
                domain.getId(),
                domain.getEmail(),
                domain.getPassword(),
                domain.getFirstName(),
                domain.getLastName(),
                domain.getPhoneNumber(),
                domain.getGender(),
                domain.getBirthday()
        );
    }

    @Override
    public Client entityToDomain(ClientJpa entity) {
        return new Client(
                entity.getId(),
                entity.getEmail(),
                entity.getPassword(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getPhoneNumber(),
                entity.getGender(),
                entity.getBirthday()
        );
    }
}
