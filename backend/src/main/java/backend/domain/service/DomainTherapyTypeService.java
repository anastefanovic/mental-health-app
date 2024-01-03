package backend.domain.service;

import backend.adapters.driving.exception.ResourceNotFoundException;
import backend.domain.model.TherapyType;
import backend.ports.driving.TherapyTypeService;
import backend.ports.driven.TherapyTypeRepository;

import java.util.List;

public class DomainTherapyTypeService implements TherapyTypeService {
    private final TherapyTypeRepository therapyTypeRepository;

    public DomainTherapyTypeService(TherapyTypeRepository therapyTypeRepository) {
        this.therapyTypeRepository = therapyTypeRepository;
    }

    @Override
    public TherapyType addTherapyType(TherapyType therapyType) {
        return therapyTypeRepository.save(therapyType);
    }

    @Override
    public List<TherapyType> getAllTherapyTypes() {
        return therapyTypeRepository.findAll();
    }

    @Override
    public TherapyType getTherapyTypeById(Long id) {
        return therapyTypeRepository.findById(id).orElseThrow(
                () -> {
                    throw new ResourceNotFoundException("Therapy type with id " + id + " not found");
                }
        );
    }
}
