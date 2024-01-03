package backend.adapters.driven.repository.implementation;

import backend.adapters.driven.transformer.TherapistTransformer;
import backend.domain.model.Therapist;
import backend.ports.driven.TherapistRepository;
import backend.adapters.driven.model.TherapistJpa;
import backend.adapters.driven.repository.jpa.TherapistJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class TherapistRepositoryImpl implements TherapistRepository {
    private final TherapistJpaRepository therapistJpaRepository;
    private final TherapistTransformer therapistTransformer;

    public TherapistRepositoryImpl(
            TherapistJpaRepository therapistJpaRepository,
            TherapistTransformer therapistTransformer
    ) {
        this.therapistJpaRepository = therapistJpaRepository;
        this.therapistTransformer = therapistTransformer;
    }

    @Override
    public Therapist save(Therapist therapist) {
        TherapistJpa therapistJpa = therapistTransformer.domainToEntity(therapist);
        TherapistJpa savedTherapist = therapistJpaRepository.save(therapistJpa);
        return therapistTransformer.entityToDomain(savedTherapist);
    }

    @Override
    public Optional<Therapist> findById(Long id) {
        Optional<TherapistJpa> therapistJpa = therapistJpaRepository.findById(id);
        return therapistJpa.map(therapistTransformer::entityToDomain);
    }

    @Override
    public Optional<Therapist> findByEmail(String email) {
        Optional<TherapistJpa> therapistJpa = therapistJpaRepository.findByEmail(email);
        return therapistJpa.map(therapistTransformer::entityToDomain);
    }

    @Override
    public List<Therapist> findAll() {
        List<TherapistJpa> allTherapistsJpa = therapistJpaRepository.findAll();
        return allTherapistsJpa.stream().map(therapistTransformer::entityToDomain
        ).toList();
    }

    @Override
    public void deleteById(Long id) {
        therapistJpaRepository.deleteById(id);
    }
}
