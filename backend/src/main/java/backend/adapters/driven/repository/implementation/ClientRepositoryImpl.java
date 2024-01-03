package backend.adapters.driven.repository.implementation;

import backend.adapters.driven.transformer.ClientTransformer;
import backend.domain.model.Client;
import backend.ports.driven.ClientRepository;
import backend.adapters.driven.model.ClientJpa;
import backend.adapters.driven.repository.jpa.ClientJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ClientRepositoryImpl implements ClientRepository {
    private final ClientJpaRepository clientJpaRepository;
    private final ClientTransformer clientTransformer;

    public ClientRepositoryImpl(
            ClientJpaRepository clientJpaRepository,
            ClientTransformer clientTransformer
    ) {
        this.clientJpaRepository = clientJpaRepository;
        this.clientTransformer = clientTransformer;
    }

    @Override
    public Optional<Client> findById(Long id) {
        Optional<ClientJpa> clientJpa = clientJpaRepository.findById(id);
        return clientJpa.map(clientTransformer::entityToDomain);
    }

    @Override
    public Optional<Client> findByEmail(String email) {
        Optional<ClientJpa> clientJpa = clientJpaRepository.findByEmail(email);
        return clientJpa.map(clientTransformer::entityToDomain);
    }

    @Override
    public Client save(Client client) {
        ClientJpa clientJpa = clientTransformer.domainToEntity(client);
        ClientJpa savedClient = clientJpaRepository.save(clientJpa);
        return clientTransformer.entityToDomain(savedClient);
    }
}
