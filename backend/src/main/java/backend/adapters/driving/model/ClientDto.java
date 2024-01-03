package backend.adapters.driving.model;

public class ClientDto extends UserDto {
    private String phoneNumber;
    private String gender;
    private String birthday;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public ClientDto(
            Long id,
            String email,
            String password,
            String firstName,
            String lastName,
            String phoneNumber,
            String gender,
            String birthday
    ) {
        super(id, email, password, firstName, lastName, "CLIENT");
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.birthday = birthday;
    }

    public ClientDto(
            Long id,
            String email,
            String password,
            String firstName,
            String lastName,
            String phoneNumber,
            String gender,
            String birthday,
            boolean validationFailed,
            String validationMessage
    ) {
        super(id, email, password, firstName, lastName, "CLIENT", validationFailed, validationMessage);
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.birthday = birthday;
    }

    public ClientDto() { }
}
