package backend.adapters.driving.model;

public class PasswordChangeDto {
    private Long userId;
    private String userType;
    private String oldPassword;
    private String newPassword;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getOldPassword() { return oldPassword; }

    public void setOldPassword(String oldPassword) { this.oldPassword = oldPassword; }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public PasswordChangeDto(Long userId, String userType, String oldPassword, String newPassword) {
        this.userId = userId;
        this.userType = userType;
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

    public PasswordChangeDto() { }
}
