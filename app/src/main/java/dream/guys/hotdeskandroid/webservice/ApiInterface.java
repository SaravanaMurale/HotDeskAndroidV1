package dream.guys.hotdeskandroid.webservice;

import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.time.LocalDateTime;
import java.util.List;

import dream.guys.hotdeskandroid.model.request.BookingStatusRequest;
import dream.guys.hotdeskandroid.model.request.BookingsRequest;
import dream.guys.hotdeskandroid.model.request.CreatePinRequest;
import dream.guys.hotdeskandroid.model.request.ForgotPasswordRequest;
import dream.guys.hotdeskandroid.model.request.GDPRrequest;
import dream.guys.hotdeskandroid.model.request.GetTokenRequest;
import dream.guys.hotdeskandroid.model.request.LocateBookingRequest;
import dream.guys.hotdeskandroid.model.request.LocateCarParkBookingRequest;
import dream.guys.hotdeskandroid.model.response.BaseResponse;
import dream.guys.hotdeskandroid.model.response.BookingForEditResponse;
import dream.guys.hotdeskandroid.model.response.BookingListResponse;
import dream.guys.hotdeskandroid.model.response.CheckPinLoginResponse;
import dream.guys.hotdeskandroid.model.response.DeskAvaliabilityResponse;
import dream.guys.hotdeskandroid.model.response.GetTokenResponse;
import dream.guys.hotdeskandroid.model.response.ImageResponse;
import dream.guys.hotdeskandroid.model.response.IncomingRequestResponse;
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
    // Pin APis
    // to check wheter this user can setup pin or not
    @POST("api/settings/PinNumberSetting")
    Call<Boolean> checkPinEnabled();
    // sign up for Pin login
    @POST("api/account/UpdateSecurityPin")
    Call<BaseResponse> createPin(@Body CreatePinRequest createPinRequest);
    @POST("api/account/HasSetupPinNumberForTenantUser")
    Call<CheckPinLoginResponse> checkPinLoginAvailable(@Body CreatePinRequest createPinRequest);
    @POST("api/account/pin")
    Call<GetTokenResponse> checkPinLogin(@Body CreatePinRequest createPinRequest);


    @POST("api/account/updategdpracceptancesettings")
    Call<Boolean> updateGDPR(@Body GDPRrequest request);

    @POST("api/Account/Token")
    Call<GetTokenResponse> getLoginToken(@Body GetTokenRequest request);

    @GET("api/image/user")
    Call<ImageResponse> getUserImage();

    @GET("api/image/tenant")
    Call<ImageResponse> getTenantImage();

    @POST("api/Account/RequestPasswordReset")
    Call<Void> requestPasswordReset(@Body ForgotPasswordRequest forgotPasswordRequest);

    @GET("api/requests/incoming")
    Call<IncomingRequestResponse>  getIncomingRequest(@Query("includePastRequests") boolean includePastRequests);

    @GET("api/Account/LoggedInUser")
    Call<UserDetailsResponse>  getLoginUserDetails();

    @GET("api/MyWork/UserMyWorkStatus")
    Call<BookingListResponse> getUserMyWorkDetails(@Query("dayOfTheWeek") String dayOfTheWeek,
                                                   @Query("includeNonWorkingDays") boolean includeNonWorkingDays);

    @PUT("api/booking/bookingStatus")
    Call<BaseResponse> bookingStatus(@Body BookingStatusRequest calendarId);

    @PUT("api/booking/bookings")
    Call<BaseResponse> bookingBookings(@Body JsonObject body);

    @PUT("api/CarParkBooking/Bookings")
    Call<BaseResponse> carParkBookingBookings(@Body JsonObject body);

    @PUT("api/MeetingRoomBooking/Bookings")
    Call<BaseResponse> meetingRoomBookingBookings(@Body JsonObject body);

//    Call<BaseResponse> carParkbookingBookings(@Body String body);



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

    @PUT("api/booking/bookings")
    Call<BaseResponse>  doDeskBooking(@Body LocateBookingRequest locateBookingRequest);

    @PUT("api/carparkbooking/bookings")
    Call<BaseResponse> doCarParkingBooking(@Body LocateCarParkBookingRequest locateCarParkBookingRequest);





}
