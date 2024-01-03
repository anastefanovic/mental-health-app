package backend.ports.driven;

import backend.domain.model.Admin;

import java.util.Optional;

public interface AdminRepository {
    Optional<Admin> findById(Long id);
    Optional<Admin> findByEmail(String email);
    Admin save(Admin admin);
}
