package backend.domain.model;

public class Therapist extends User {
    private String phoneNumber;
    private String about;
    private String licenceInformation;
    private String address;
    private Long sessionPrice;
    private Boolean isFirstLogin;
    private TherapyType therapyType;


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getSessionPrice() {
        return sessionPrice;
    }

    public void setSessionPrice(Long sessionPrice) {
        this.sessionPrice = sessionPrice;
    }

    public String getLicenceInformation() {
        return licenceInformation;
    }

    public void setLicenceInformation(String licenceInformation) {
        this.licenceInformation = licenceInformation;
    }

    public TherapyType getTherapyType() {
        return therapyType;
    }

    public void setTherapyType(TherapyType therapyType) {
        this.therapyType = therapyType;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getPhoneNumber() { return phoneNumber; }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Boolean getFirstLogin() { return isFirstLogin; }

    public void setFirstLogin(Boolean isFirstLogin) { this.isFirstLogin = isFirstLogin; }

    public Therapist(
            Long id,
            String email,
            String password,
            String firstName,
            String lastName,
            String phoneNumber,
            String about,
            String licenceInformation,
            String address,
            Long sessionPrice,
            Boolean isFirstLogin,
            TherapyType therapyType
    ) {
        super(id, email, password, firstName, lastName);
        this.phoneNumber = phoneNumber;
        this.about = about;
        this.licenceInformation = licenceInformation;
        this.address = address;
        this.sessionPrice = sessionPrice;
        this.isFirstLogin = isFirstLogin;
        this.therapyType = therapyType;
    }

    public Therapist() {
        super();
    }
}
