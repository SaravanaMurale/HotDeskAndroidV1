package dream.guys.hotdeskandroid.model;

import com.google.gson.annotations.SerializedName;

public class GetTokenRequest {

    @SerializedName("tenantName")
    private String tenantName;
    @SerializedName("username")
    private String userName;
    @SerializedName("password")
    private String password;

    public GetTokenRequest(String tenantName, String userName, String password) {
        this.tenantName = tenantName;
        this.userName = userName;
        this.password = password;
    }

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
