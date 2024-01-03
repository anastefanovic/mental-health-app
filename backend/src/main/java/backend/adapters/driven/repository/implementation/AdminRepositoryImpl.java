package backend.adapters.driven.repository.implementation;

import backend.adapters.driven.repository.jpa.AdminJpaRepository;
import backend.adapters.driven.transformer.AdminTransformer;
import backend.domain.model.Admin;
import backend.ports.driven.AdminRepository;
import backend.adapters.driven.model.AdminJpa;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AdminRepositoryImpl implements AdminRepository {
    private final AdminJpaRepository adminJpaRepository;
    private final AdminTransformer adminTransformer;

    public AdminRepositoryImpl(
            AdminJpaRepository adminJpaRepository,
            AdminTransformer adminTransformer
    ) {
        this.adminJpaRepository = adminJpaRepository;
        this.adminTransformer = adminTransformer;
    }

    @Override
    public Admin save(Admin admin) {
        AdminJpa adminJpa = adminTransformer.domainToEntity(admin);
        AdminJpa savedAdmin = adminJpaRepository.save(adminJpa);
        return adminTransformer.entityToDomain(savedAdmin);
    }

    @Override
    public Optional<Admin> findById(Long id) {
        Optional<AdminJpa> adminJpa = adminJpaRepository.findById(id);
        return adminJpa.map(adminTransformer::entityToDomain);
    }

    @Override
    public Optional<Admin> findByEmail(String email) {
        Optional<AdminJpa> adminJpa = adminJpaRepository.findByEmail(email);
        return adminJpa.map(adminTransformer::entityToDomain);
    }
}

