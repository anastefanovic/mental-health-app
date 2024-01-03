package backend.adapters.driving.controller;

import backend.adapters.driving.model.*;
import backend.adapters.driving.transformer.*;
import backend.domain.model.*;
import backend.ports.driving.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final UserDtoTransformer userTransformer;
    private final ClientDtoTransformer clientTransformer;
    private final TherapistDtoTransformer therapistTransformer;
    private final LoginCredentialsDtoTransformer loginCredentialsTransformer;
    private final PasswordChangeDtoTransformer passwordChangeTransformer;

    public UserController(
            UserService userService,
            UserDtoTransformer userTransformer,
            ClientDtoTransformer clientTransformer,
            TherapistDtoTransformer therapistTransformer,
            LoginCredentialsDtoTransformer loginCredentialsTransformer,
            PasswordChangeDtoTransformer passwordChangeTransformer
            ) {
        this.userService = userService;
        this.userTransformer = userTransformer;
        this.clientTransformer = clientTransformer;
        this.therapistTransformer = therapistTransformer;
        this.loginCredentialsTransformer = loginCredentialsTransformer;
        this.passwordChangeTransformer = passwordChangeTransformer;
    }

    @PostMapping("/register")
    UserDto register(@RequestBody UserDto userDto) {
        User user = userTransformer.toDomain(userDto);
        User returnValue = userService.register(user);
        return userTransformer.fromDomain(returnValue);
    }

    @PostMapping("/login")
    UserDto login(@RequestBody LoginCredentialsDto loginCredentialsDto) {
        LoginCredentials loginCredentials = loginCredentialsTransformer.toDomain(loginCredentialsDto);
        User returnValue = userService.login(loginCredentials);
        UserDto d = userTransformer.fromDomain(returnValue);
        return userTransformer.fromDomain(returnValue);
    }

    @GetMapping("/client/{id}")
    ClientDto getClientById(@PathVariable Long id) {
        Client client = userService.getClientById(id);
        return clientTransformer.fromDomain(client);
    }

    @DeleteMapping("/delete/{id}")
    void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    @GetMapping("/therapists")
    List<TherapistDto> getAllTherapists() {
        List<Therapist> allTherapists = userService.getAllTherapists();
        return allTherapists.stream().map(
                therapistTransformer::fromDomain
        ).toList();
    }

    @GetMapping("/therapist/{id}")
    TherapistDto getTherapistById(@PathVariable Long id) {
        Therapist therapist = userService.getTherapistById(id);
        return therapistTransformer.fromDomain(therapist);
    }

    @PutMapping("/update")
    UserDto updateUser(@RequestBody UserDto userDto) {
        User user = userTransformer.toDomain(userDto);
        User returnValue = userService.updateUser(user);
        return userTransformer.fromDomain(returnValue);
    }

    @PutMapping("/password")
    UserDto changePassword(@RequestBody PasswordChangeDto passwordChangeDto) {
        PasswordChange passwordChange = passwordChangeTransformer.toDomain(passwordChangeDto);
        User user = userService.changePassword(passwordChange);
        return userTransformer.fromDomain(user);
    }
}
