package backend.adapters.driven.transformer;

import backend.domain.model.Admin;
import backend.adapters.driven.model.AdminJpa;
import org.springframework.stereotype.Component;

@Component
public class AdminTransformer implements Transformer<Admin, AdminJpa>{
    @Override
    public AdminJpa domainToEntity(Admin domain) {
        return new AdminJpa(
                domain.getId(),
                domain.getEmail(),
                domain.getPassword(),
                domain.getFirstName(),
                domain.getLastName()
        );
    }

    @Override
    public Admin entityToDomain(AdminJpa entity) {
        return new Admin(
                entity.getId(),
                entity.getEmail(),
                entity.getPassword(),
                entity.getFirstName(),
                entity.getLastName()
        );
    }

}
