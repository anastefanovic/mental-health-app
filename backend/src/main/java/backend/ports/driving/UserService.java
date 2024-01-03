package backend.ports.driving;

import backend.domain.model.*;

import java.security.NoSuchAlgorithmException;
import java.util.List;

public interface UserService {
    User register(User newUser);
    User login(LoginCredentials loginCredentials);
    User changePassword(PasswordChange passwordChange);
    User updateUser(User user);
    void deleteUser(Long id);
    Therapist getTherapistById(Long id);
    Client getClientById(Long id);
    List<Therapist> getAllTherapists();
}
