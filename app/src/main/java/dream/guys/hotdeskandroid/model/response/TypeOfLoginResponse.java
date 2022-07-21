package dream.guys.hotdeskandroid.model.response;

public class TypeOfLoginResponse {
    private int typeOfLogin;
    private String identityProvider;
    private String mobileIdentityProvider;

    public int getTypeOfLogin() {
        return typeOfLogin;
    }

    public void setTypeOfLogin(int typeOfLogin) {
        this.typeOfLogin = typeOfLogin;
    }

    public String getIdentityProvider() {
        return identityProvider;
    }

    public void setIdentityProvider(String identityProvider) {
        this.identityProvider = identityProvider;
    }

    public String getMobileIdentityProvider() {
        return mobileIdentityProvider;
    }

    public void setMobileIdentityProvider(String mobileIdentityProvider) {
        this.mobileIdentityProvider = mobileIdentityProvider;
    }
}
