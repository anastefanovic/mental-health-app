package backend.adapters.driving.model;


public class TherapistDto extends UserDto {
    private String phoneNumber;
    private String about;
    private String licenceInformation;
    private String address;
    private Long sessionPrice;
    private Boolean isFirstLogin = true;
    private TherapyTypeDto therapyType;

    public TherapistDto(
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
            TherapyTypeDto therapyType,
            boolean validationFailed,
            String validationMessage
    ) {
        super(id, email, password, firstName, lastName, "THERAPIST", validationFailed, validationMessage);
        this.phoneNumber = phoneNumber;
        this.about = about;
        this.licenceInformation = licenceInformation;
        this.address = address;
        this.sessionPrice = sessionPrice;
        this.isFirstLogin = isFirstLogin;
        this.therapyType = therapyType;
    }

    public TherapistDto(
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
            TherapyTypeDto therapyType
    ) {
        super(id, email, password, firstName, lastName, "THERAPIST");
        this.phoneNumber = phoneNumber;
        this.about = about;
        this.licenceInformation = licenceInformation;
        this.address = address;
        this.sessionPrice = sessionPrice;
        this.therapyType = therapyType;
        this.isFirstLogin = true;
    }

    public TherapistDto() { }

    public String getPhoneNumber() { return phoneNumber; }

    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getAbout() { return about; }

    public void setAbout(String about) { this.about = about; }

    public String getLicenceInformation() { return licenceInformation; }

    public void setLicenceInformation(String licenceInformation) { this.licenceInformation = licenceInformation; }

    public String getAddress() { return address; }

    public void setAddress(String address) { this.address = address; }

    public Long getSessionPrice() { return sessionPrice; }

    public void setSessionPrice(Long sessionPrice) { this.sessionPrice = sessionPrice; }

    public TherapyTypeDto getTherapyType() { return therapyType; }

    public Boolean getFirstLogin() { return isFirstLogin; }

    public void setFirstLogin(Boolean firstLogin) { isFirstLogin = firstLogin; }

    public void setTherapyType(TherapyTypeDto therapyType) { this.therapyType = therapyType; }

}
