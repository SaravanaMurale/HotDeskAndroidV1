package dream.guys.hotdeskandroid.model.request;

import com.google.gson.annotations.SerializedName;

public class CreatePinRequest {
    @SerializedName("tenantName")
    String tenantName;
    @SerializedName("userId")
    String userId;
    @SerializedName("pin")
    String pin;
    @SerializedName("username")
    String username;
    @SerializedName("newPin")
    String newPin;
    @SerializedName("confirmNewPin")
    String confirmNewPin;

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTenantName() {
        return tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNewPin() {
        return newPin;
    }

    public void setNewPin(String newPin) {
        this.newPin = newPin;
    }

    public String getConfirmNewPin() {
        return confirmNewPin;
    }

    public void setConfirmNewPin(String confirmNewPin) {
        this.confirmNewPin = confirmNewPin;
    }
}
