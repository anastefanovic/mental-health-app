package backend.adapters.driving.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = ClientDto.class, name = "CLIENT"),
        @JsonSubTypes.Type(value = TherapistDto.class, name = "THERAPIST"),
        @JsonSubTypes.Type(value = AdminDto.class, name = "ADMIN")
})
public abstract class UserDto {
    private String type;
    private Long id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private boolean validationFailed = false;
    private String validationMessage = null;

    public String getType() { return type; }

    public void setType(String type) { this.type = type; }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isValidationFailed() {
        return validationFailed;
    }

    public void setValidationFailed(boolean validationFailed) {
        this.validationFailed = validationFailed;
    }

    public String getValidationMessage() {
        return validationMessage;
    }

    public void setValidationMessage(String validationMessage) {
        this.validationMessage = validationMessage;
    }

    public UserDto(
            Long id,
            String email,
            String password,
            String firstName,
            String lastName,
            String type,
            boolean validationFailed,
            String validationMessage
    ) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.type = type;
        this.validationFailed = validationFailed;
        this.validationMessage = validationMessage;
    }

    public UserDto(
            Long id,
            String email,
            String password,
            String firstName,
            String lastName,
            String type
    ) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.type = type;
    }

    public UserDto() { }
}
