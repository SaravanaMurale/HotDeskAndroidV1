package dream.guys.hotdeskandroid.webservice;

import java.time.LocalDateTime;
import java.util.List;

import dream.guys.hotdeskandroid.model.request.BookingStatusRequest;
import dream.guys.hotdeskandroid.model.request.GetTokenRequest;
import dream.guys.hotdeskandroid.model.response.BaseResponse;
import dream.guys.hotdeskandroid.model.response.BookingForEditResponse;
import dream.guys.hotdeskandroid.model.response.BookingListResponse;
import dream.guys.hotdeskandroid.model.response.DeskAvaliabilityResponse;
import dream.guys.hotdeskandroid.model.response.GetTokenResponse;
import dream.guys.hotdeskandroid.model.response.LocateCountryRespose;
import dream.guys.hotdeskandroid.model.response.UserDetailsResponse;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {
    @POST("api/Account/Token")
    Call<GetTokenResponse> getLoginToken(@Body GetTokenRequest request);

    @FormUrlEncoded
    @POST("api/Account/RequestPasswordReset")
    Call<GetTokenResponse> requestPasswordReset(@Field("tenantName") String tenatn,
                                                @Field("userName") String user);

    @GET("api/Account/LoggedInUser")
    Call<UserDetailsResponse>  getLoginUserDetails();

    @GET("api/MyWork/UserMyWorkStatus")
    Call<BookingListResponse> getUserMyWorkDetails(@Query("dayOfTheWeek") String dayOfTheWeek,
                                                   @Query("includeNonWorkingDays") boolean includeNonWorkingDays);

    @PUT("api/booking/bookingStatus")
    Call<BaseResponse> bookingStatus(@Body BookingStatusRequest calendarId);

    @PUT("api/booking/bookings")
    Call<BaseResponse> bookingBookings(@Body String body);
//    Call<BaseResponse> bookingBookings(@Body BookingsRequest body);



    @GET("api/locate/ImmediateChildLocations")
    Call<List<LocateCountryRespose>> getLocationCountryList();

    @GET("api/locate/ImmediateChildLocations")
    Call<List<LocateCountryRespose>> getCountrysChild(@Query("parentId") int parentId);

    @GET("api/locate/ImmediateChildLocations")
    Call<Response> getCountrysChildResponse(@Query("parentId") int parentId);


    @GET("api/deskLayouts/LocationDesksWithUser")
    Call<DeskAvaliabilityResponse> getAvaliableDeskDetails(@Query("locationId") int parentId,
                                                                 @Query("toDate") LocalDateTime toDate,
                                                                 @Query("fromTime") LocalDateTime fromTime,
                                                                 @Query("toTime") LocalDateTime toTime);
    @GET("api/booking/bookingsForEdit")
    Call<BookingForEditResponse> getBookingsForEdit(@Query("teamId") int parentId,
                                                    @Query("teamMembershipId") int toDate,
                                                    @Query("fromDate") String fromTime,
                                                    @Query("toDate") String toTime);


    @GET("api/locate/ImmediateChildLocations")
    Call<List<String>> getItemJsonObject(@Query("parentId") int parentId);





}
