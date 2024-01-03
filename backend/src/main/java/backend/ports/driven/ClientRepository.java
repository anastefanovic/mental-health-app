package backend.ports.driven;

import backend.domain.model.Client;

import java.util.Optional;

public interface ClientRepository {
    Optional<Client> findById(Long id);
    Optional<Client> findByEmail(String email);
    Client save(Client client);
}
