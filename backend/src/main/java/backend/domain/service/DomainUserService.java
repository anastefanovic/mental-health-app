package backend.domain.service;

import backend.adapters.driving.exception.DataTypeNotSupportedException;
import backend.adapters.driving.exception.ResourceNotFoundException;
import backend.domain.model.*;
import backend.ports.driving.UserService;
import backend.ports.driven.AdminRepository;
import backend.ports.driven.ClientRepository;
import backend.ports.driven.TherapistRepository;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class DomainUserService implements UserService {

    private final AdminRepository adminRepository;
    private final ClientRepository clientRepository;
    private final TherapistRepository therapistRepository;

    public DomainUserService(
            AdminRepository adminRepository,
            ClientRepository clientRepository,
            TherapistRepository therapistRepository
    ) {
        this.adminRepository = adminRepository;
        this.clientRepository = clientRepository;
        this.therapistRepository = therapistRepository;
    }

    @Override
    public User register(User newUser) {

        if(!isEmailUnique(newUser)) {
            newUser.setValidationFailed(true);
            newUser.setValidationMessage("Email " + newUser.getEmail() + " is not unique");
            return newUser;
        }

        String hash = hashPassword(newUser.getPassword());
        newUser.setPassword(hash);

        if(newUser instanceof Client) {
            return clientRepository.save(((Client) newUser));
        }
        else if (newUser instanceof Therapist) {
            return therapistRepository.save(((Therapist) newUser));
        } else {
            return adminRepository.save(((Admin) newUser));
        }

    }

    @Override
    public User login(LoginCredentials loginCredentials) {

        String encrypted = hashPassword(loginCredentials.getPassword());
        loginCredentials.setPassword(encrypted);

       return switch (loginCredentials.getUserType()) {
           case ADMIN -> adminLogin(loginCredentials);
           case CLIENT -> clientLogin(loginCredentials);
           case THERAPIST -> therapistLogin(loginCredentials);
           default -> throw new DataTypeNotSupportedException("User type " + loginCredentials.getUserType() + " not supported");
       };
    }

    @Override
    public void deleteUser(Long id) {
        therapistRepository.deleteById(id);
    }

    @Override
    public User changePassword(PasswordChange passwordChange) {
        return switch (passwordChange.getUserType()) {
            case CLIENT -> clientPasswordChange(
                    passwordChange.getUserId(),
                    passwordChange.getNewPassword(),
                    passwordChange.getOldPassword()
            );
            case THERAPIST -> therapistPasswordChange(
                    passwordChange.getUserId(),
                    passwordChange.getNewPassword(),
                    passwordChange.getOldPassword()
            );
            case ADMIN -> adminPasswordChange(
                    passwordChange.getUserId(),
                    passwordChange.getNewPassword(),
                    passwordChange.getOldPassword()
            );
            default -> throw new DataTypeNotSupportedException(
                    "Not supported user type " + passwordChange.getUserType()
            );
        };
    }

    @Override
    public User updateUser(User user) {
        if(user instanceof Client) {
            return updateClient((Client) user);
        }

        if(user instanceof Therapist) {
            return updateTherapist((Therapist) user);
        }

        throw new DataTypeNotSupportedException("Data type " + user.getClass().getTypeName()+ " not supported for update");
    }

    @Override
    public List<Therapist> getAllTherapists() {
        return therapistRepository.findAll();
    }

    @Override
    public Client getClientById(Long id) {
        return clientRepository.findById(id).orElseThrow(
                () -> {
                    throw new ResourceNotFoundException("Client with id " + id + " not found");
                }
        );
    }

    @Override
    public Therapist getTherapistById(Long id) {
        return therapistRepository.findById(id).orElseThrow(
                () -> {
                    throw new ResourceNotFoundException("Therapist with id " + id + " not found");
                }
        );
    }


    private Therapist updateTherapist(Therapist therapist) {
        Therapist oldTherapist = therapistRepository.findById(therapist.getId()).orElseThrow(
                () -> {
                    throw new ResourceNotFoundException("Therapist with id " + therapist.getId() + " not found");
                }
        );

        oldTherapist.setAddress(therapist.getAddress());
        oldTherapist.setSessionPrice(therapist.getSessionPrice());
        oldTherapist.setAbout(therapist.getAbout());
        oldTherapist.setPhoneNumber(therapist.getPhoneNumber());

        return therapistRepository.save(oldTherapist);
    }

    private Client updateClient(Client client) {
        Client oldClient = clientRepository.findById(client.getId()).orElseThrow(
                () -> {
                    throw new ResourceNotFoundException("Client with id " + client.getId() + " not found");
                }
        );

        oldClient.setFirstName(client.getFirstName());
        oldClient.setLastName(client.getLastName());
        oldClient.setPhoneNumber(client.getPhoneNumber());
        oldClient.setBirthday(client.getBirthday());
        oldClient.setGender(client.getGender());

        return clientRepository.save(oldClient);
    }

    private Client clientPasswordChange(Long id, String newPassword, String oldPassword) {
        Client client = clientRepository.findById(id).orElseThrow(
                () -> {
                    throw new ResourceNotFoundException("Client with id " + id + " not found");
                }
        );

        if(!client.getPassword().equals(hashPassword(oldPassword))) {
            client.setValidationFailed(true);
            client.setValidationMessage("Old password is not valid");
            return client;
        }

        String hashedPassword = hashPassword(newPassword);

        client.setPassword(hashedPassword);
        return clientRepository.save(client);
    }

    private Therapist therapistPasswordChange(Long id, String newPassword, String oldPassword) {
        Therapist therapist = therapistRepository.findById(id).orElseThrow(
                () -> {
                    throw new ResourceNotFoundException("Therapist with id " + id + " not found");
                }
        );

        if(!therapist.getPassword().equals(hashPassword(oldPassword))) {
            therapist.setValidationFailed(true);
            therapist.setValidationMessage("Old password is not valid");
            return therapist;
        }

        if(therapist.getFirstLogin()) { therapist.setFirstLogin(false); }

        String encryptedNewPassword = hashPassword(newPassword);

        therapist.setPassword(encryptedNewPassword);
        return therapistRepository.save(therapist);
    }

    private Admin adminPasswordChange(Long id, String newPassword, String oldPassword) {
        Admin admin = adminRepository.findById(id).orElseThrow(
                () -> {
                    throw new ResourceNotFoundException("Admin with id " + id + " not found");
                }
        );

        if(!admin.getPassword().equals(hashPassword(oldPassword))) {
            admin.setValidationFailed(true);
            admin.setValidationMessage("Old password is not valid");
            return admin;
        }

        String encryptedNewPassword = hashPassword(newPassword);

        admin.setPassword(encryptedNewPassword);
        return adminRepository.save(admin);
    }

    private boolean isEmailUnique(User newUser) {
        if(newUser instanceof Client) {
            return clientRepository.findByEmail(newUser.getEmail()).isEmpty();
        }

        if (newUser instanceof Therapist) {
            return therapistRepository.findByEmail(newUser.getEmail()).isEmpty();
        }

        if (newUser instanceof Admin) {
            return adminRepository.findByEmail(newUser.getEmail()).isEmpty();
        }

        throw new DataTypeNotSupportedException("Not supported user type " + newUser.getClass().getTypeName());
    }

    private Admin adminLogin(LoginCredentials loginCredentials) {
        Admin admin = adminRepository.findByEmail(loginCredentials.getEmail()).orElse(null);

        if (admin != null && admin.getPassword().equals(loginCredentials.getPassword())) {
            return admin;
        }

        if(admin == null) {
            admin = new Admin();
        }

        admin.setValidationFailed(true);
        admin.setValidationMessage("Admin login credentials not valid");
        return admin;
    }

    private Client clientLogin(LoginCredentials loginCredentials) {
        Client client = clientRepository.findByEmail(loginCredentials.getEmail()).orElse(null);

        if (client != null && client.getPassword().equals(loginCredentials.getPassword())) {
            return client;
        }

        if(client == null) {
            client = new Client();
        }

        client.setValidationFailed(true);
        client.setValidationMessage("Client login credentials not valid");
        return client;
    }

    private Therapist therapistLogin(LoginCredentials loginCredentials) {
        Therapist therapist = therapistRepository.findByEmail(loginCredentials.getEmail()).orElse(null);

        if (therapist != null && therapist.getPassword().equals(loginCredentials.getPassword())) {
            return therapist;
        }


        if(therapist == null) {
            therapist = new Therapist();
        }

        therapist.setValidationFailed(true);
        therapist.setValidationMessage("Therapist login credentials not valid");
        return therapist;
    }

    private String hashPassword(String password) {
        MessageDigest md = null;

        try {
            md = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        byte[] messageDigest = md.digest(password.getBytes());
        BigInteger hashedPassword = new BigInteger(1, messageDigest);
        return hashedPassword.toString(16);
    }

}
