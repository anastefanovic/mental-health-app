package backend.domain.model;

import java.time.LocalDate;

public class Client extends User {
    private String phoneNumber;
    private String gender;
    private LocalDate birthday;


    public String getGender() { return gender; }

    public void setGender(String gender) { this.gender = gender; }

    public LocalDate getBirthday() { return birthday; }

    public void setBirthday(LocalDate birthday) { this.birthday = birthday; }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Client(
            Long id,
            String email,
            String password,
            String firstName,
            String lastName,
            String phoneNumber,
            String gender,
            LocalDate birthday
    ) {
        super(id, email, password, firstName, lastName);
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.birthday = birthday;
    }

    public Client() {
        super();
    }
}
