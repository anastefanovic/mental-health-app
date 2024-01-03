package backend.adapters.driven.repository.implementation;

import backend.adapters.driven.transformer.TherapyTypeTransformer;
import backend.domain.model.TherapyType;
import backend.ports.driven.TherapyTypeRepository;
import backend.adapters.driven.model.TherapyTypeJpa;
import backend.adapters.driven.repository.jpa.TherapyTypeJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class TherapyTypeRepositoryImpl implements TherapyTypeRepository {
    private final TherapyTypeJpaRepository therapyTypeJpaRepository;
    private final TherapyTypeTransformer therapyTypeTransformer;

    public TherapyTypeRepositoryImpl(
            TherapyTypeJpaRepository therapyTypeJpaRepository,
            TherapyTypeTransformer therapyTypeTransformer
    ) {
        this.therapyTypeJpaRepository = therapyTypeJpaRepository;
        this.therapyTypeTransformer = therapyTypeTransformer;
    }

    @Override
    public TherapyType save(TherapyType therapyType) {
        TherapyTypeJpa therapyTypeJpa = therapyTypeTransformer.domainToEntity(therapyType);
        TherapyTypeJpa savedTherapyType = therapyTypeJpaRepository.save(therapyTypeJpa);
        return therapyTypeTransformer.entityToDomain(savedTherapyType);
    }

    @Override
    public Optional<TherapyType> findById(Long id) {
        Optional<TherapyTypeJpa> therapyTypeJpa = therapyTypeJpaRepository.findById(id);
        return therapyTypeJpa.map(therapyTypeTransformer::entityToDomain);
    }

    @Override
    public List<TherapyType> findAll() {
        List<TherapyTypeJpa> allTherapyTypesJpa = therapyTypeJpaRepository.findAll();
        return allTherapyTypesJpa.stream().map(therapyTypeTransformer::entityToDomain
        ).toList();
    }
}
