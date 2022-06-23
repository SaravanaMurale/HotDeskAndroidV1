package dream.guys.hotdeskandroid.webservice;

import dream.guys.hotdeskandroid.model.GetTokenRequest;
import dream.guys.hotdeskandroid.model.GetTokenResponse;
import dream.guys.hotdeskandroid.model.UserDetailsResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {

    @POST("api/Account/Token")
    Call<GetTokenResponse> getLoginToken(@Body GetTokenRequest request);

    @GET("api/Account/LoggedInUser")
    Call<UserDetailsResponse>  getLoginUserDetails();






}
