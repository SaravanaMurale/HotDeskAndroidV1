package dream.guys.hotdeskandroid.model.request;

import com.google.gson.annotations.SerializedName;

public class GDPRrequest {
    @SerializedName("tenantName")
    private String tenantName;
    @SerializedName("userName")
    private String userName;
    @SerializedName("gdprAccepted")
    private boolean gdprAccepted;

    public String getTenantName() {
        return tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean isGdprAccepted() {
        return gdprAccepted;
    }

    public void setGdprAccepted(boolean gdprAccepted) {
        this.gdprAccepted = gdprAccepted;
    }
}
