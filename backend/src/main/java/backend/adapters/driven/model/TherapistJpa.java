package backend.adapters.driven.model;


import javax.persistence.*;
import java.util.List;

@Table(name = "therapist")
@Entity
public class TherapistJpa {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String licenceInformation;

    @Column(nullable = false)
    private Long sessionPrice;

    @Column(nullable = false)
    private Boolean isFirstLogin;

    private String about;

    private String address;

    @OneToMany(
            mappedBy = "therapist",
            cascade = CascadeType.ALL
    )
    private List<AppointmentJpa> appointments;

    @OneToMany(
            mappedBy = "therapist",
            cascade = CascadeType.ALL
    )
    private List<ReviewJpa> reviews;

    @ManyToOne(targetEntity = TherapyTypeJpa.class)
    @JoinColumn(referencedColumnName = "id", nullable = false)
    private TherapyTypeJpa therapyType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getLicenceInformation() {
        return licenceInformation;
    }

    public void setLicenceInformation(String licenceInformation) {
        this.licenceInformation = licenceInformation;
    }

    public Long getSessionPrice() {
        return sessionPrice;
    }

    public void setSessionPrice(Long sessionPrice) {
        this.sessionPrice = sessionPrice;
    }

    public Boolean getFirstLogin() {
        return isFirstLogin;
    }

    public void setFirstLogin(Boolean firstLogin) {
        isFirstLogin = firstLogin;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public TherapyTypeJpa getTherapyType() {
        return therapyType;
    }

    public void setTherapyType(TherapyTypeJpa therapyType) {
        this.therapyType = therapyType;
    }

    public TherapistJpa(Long id, String email, String password, String firstName, String lastName, String phoneNumber, String licenceInformation, Long sessionPrice, Boolean isFirstLogin, String about, String address, TherapyTypeJpa therapyType) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.licenceInformation = licenceInformation;
        this.sessionPrice = sessionPrice;
        this.isFirstLogin = isFirstLogin;
        this.about = about;
        this.address = address;
        this.therapyType = therapyType;
    }

    public TherapistJpa() { }
}
