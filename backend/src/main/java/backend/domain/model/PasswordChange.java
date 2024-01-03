package backend.domain.model;

import backend.domain.model.enums.UserType;

public class PasswordChange {
    private Long userId;
    private UserType userType;
    private String oldPassword;
    private String newPassword;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
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

    public PasswordChange(Long userId, UserType userType, String oldPassword, String newPassword) {
        this.userId = userId;
        this.userType = userType;
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

    public PasswordChange() { }
}
