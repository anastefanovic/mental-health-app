package backend.adapters.driving.model;

public class AdminDto extends UserDto {
    public AdminDto(
            Long id,
            String email,
            String password,
            String firstName,
            String lastName
    ) {
        super(id, email, password, firstName, lastName, "ADMIN");
    }


    public AdminDto(
            Long id,
            String email,
            String password,
            String firstName,
            String lastName,
            boolean validationFailed,
            String validationMessage
    ) {
        super(id, email, password, firstName, lastName, "ADMIN", validationFailed, validationMessage);
    }

    public AdminDto() { }
}
