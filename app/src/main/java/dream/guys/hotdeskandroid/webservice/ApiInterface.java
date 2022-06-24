package dream.guys.hotdeskandroid.webservice;

import java.util.List;

import dream.guys.hotdeskandroid.model.request.GetTokenRequest;
import dream.guys.hotdeskandroid.model.response.BookingListResponse;
import dream.guys.hotdeskandroid.model.response.GetTokenResponse;
import dream.guys.hotdeskandroid.model.response.UserDetailsResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {

    @POST("api/Account/Token")
    Call<GetTokenResponse> getLoginToken(@Body GetTokenRequest request);

    @GET("api/Account/LoggedInUser")
    Call<UserDetailsResponse>  getLoginUserDetails();

    @GET("/api/Booking/Bookings")
    Call<List<BookingListResponse>> getHomeBookingDetails(@Query("teamMembershipId") int teamMembershipId,@Query("fromDate") String fromDate,@Query("toDate") String toDate);








}
