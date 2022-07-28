package dream.guys.hotdeskandroid.webservice;

import android.provider.Settings;

import com.google.gson.JsonObject;

import java.util.List;

import dream.guys.hotdeskandroid.model.request.BookingStatusRequest;
import dream.guys.hotdeskandroid.model.request.CreatePinRequest;
import dream.guys.hotdeskandroid.model.request.ForgotPasswordRequest;
import dream.guys.hotdeskandroid.model.request.GDPRrequest;
import dream.guys.hotdeskandroid.model.request.GetTokenRequest;
import dream.guys.hotdeskandroid.model.request.LocateBookingRequest;
import dream.guys.hotdeskandroid.model.request.LocateCarParkBookingRequest;
import dream.guys.hotdeskandroid.model.request.LocateCarParkEditRequest;
import dream.guys.hotdeskandroid.model.request.LocateDeskBookingRequest;
import dream.guys.hotdeskandroid.model.request.MeetingRoomRequest;
import dream.guys.hotdeskandroid.model.response.BaseResponse;
import dream.guys.hotdeskandroid.model.response.BookingForEditResponse;
import dream.guys.hotdeskandroid.model.response.BookingListResponse;
import dream.guys.hotdeskandroid.model.response.CarParkAvalibilityResponse;
import dream.guys.hotdeskandroid.model.response.CarParkingDescriptionResponse;
import dream.guys.hotdeskandroid.model.response.CarParkingForEditResponse;
import dream.guys.hotdeskandroid.model.response.CarParkingslotsResponse;
import dream.guys.hotdeskandroid.model.response.CheckPinLoginResponse;
import dream.guys.hotdeskandroid.model.response.DeskAvaliabilityResponse;
import dream.guys.hotdeskandroid.model.response.DeskDescriptionResponse;
import dream.guys.hotdeskandroid.model.response.GetTokenResponse;
import dream.guys.hotdeskandroid.model.response.GlobalSearchResponse;
import dream.guys.hotdeskandroid.model.response.ImageResponse;
import dream.guys.hotdeskandroid.model.response.IncomingRequestResponse;
import dream.guys.hotdeskandroid.model.response.LocateCountryRespose;
import dream.guys.hotdeskandroid.model.response.MeetingRoomDescriptionResponse;
import dream.guys.hotdeskandroid.model.response.TypeOfLoginResponse;
import dream.guys.hotdeskandroid.model.response.TeamsResponse;
import dream.guys.hotdeskandroid.model.response.UserDetailsResponse;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {
    // Pin APis
    // to check wheter this user can setup pin or not
    @POST("api/settings/PrivacyPolicyURL")
    Call<String> privacyPolicy(@Body JsonObject jsonObject);
    @POST("api/account/TypeOfLogin")
    Call<TypeOfLoginResponse> typeOfLogin(@Body JsonObject jsonObject);
    @POST("api/account/TokenExchange")
    Call<GetTokenResponse> tokenExchange(@Body JsonObject jsonObject);
    @POST("api/settings/PinNumberSetting")
    Call<Boolean> checkPinEnabled();
    //Qr enabled
    @GET("api/Settings/QRCheckInEnforcementEnabled")
    Call<Boolean> getQrEnabled();
    // sign up for Pin login
    @POST("api/account/UpdateSecurityPin")
    Call<BaseResponse> createPin(@Body CreatePinRequest createPinRequest);
    @POST("api/account/HasSetupPinNumberForTenantUser")
    Call<CheckPinLoginResponse> checkPinLoginAvailable(@Body CreatePinRequest createPinRequest);
    @POST("api/account/pin")
    Call<GetTokenResponse> checkPinLogin(@Body CreatePinRequest createPinRequest);


    @POST("api/account/updategdpracceptancesettings")
    Call<Void> updateGDPR(@Body GDPRrequest request);

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

    @GET("api/globalsearch")
    Call<GlobalSearchResponse> getGlobalSearchData(@Query("pageSize") int pageSize,
                                                   @Query("filterText") String text);
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
                                                                 @Query("toDate") String toDate,
                                                                 @Query("fromTime") String fromTime,
                                                                 @Query("toTime") String toTime);
    @GET("api/booking/bookingsForEdit")
    Call<BookingForEditResponse> getBookingsForEdit(@Query("teamId") int parentId,
                                                    @Query("teamMembershipId") int toDate,
                                                    @Query("fromDate") String fromTime,
                                                    @Query("toDate") String toTime);


    @GET("api/locate/ImmediateChildLocations")
    Call<List<String>> getItemJsonObject(@Query("parentId") int parentId);

    //DeskBooking
    @PUT("api/booking/bookings")
    Call<BaseResponse>  doDeskBooking(@Body LocateBookingRequest locateBookingRequest);

    //DeskBookingRequest
    @PUT("api/booking/bookings")
    Call<BaseResponse>  doRequestDeskBooking(@Body LocateDeskBookingRequest locateBookingRequest);


    //CarParkBooking
    @PUT("api/carparkbooking/bookings")
    Call<BaseResponse> doCarParkingBooking(@Body LocateCarParkBookingRequest locateCarParkBookingRequest);

    //CarPark edit
    @PUT("api/carparkbooking/bookings")
    Call<BaseResponse> doCarParkingEdit(@Body LocateCarParkEditRequest locateCarParkBookingRequest);

    @GET("api/teams")
    Call<List<TeamsResponse>>  getTeams();

    @GET("api/ParkingSlot/carparkparkingslots")
    Call<List<CarParkingslotsResponse>> getCarParkingSlots(@Query("locationId") int locationId);

    @GET("api/CarParkBooking/availability")
    Call<List<CarParkAvalibilityResponse>> getCarParkingSlotAvalibility(@Query("date") String toDate,
                                                                        @Query("from") String fromTime,
                                                                        @Query("to") String toTime);

    @GET("api/carparkbooking/bookings")
    Call<CarParkingForEditResponse> getCarParkingEditList(@Query("fromDate") String fromDate,
                                                                @Query("toDate") String toDate,
                                                                @Query("slotId") int slotId);


    //MeetingRoomBook
    @PUT("api/MeetingRoomBooking/bookings")
    Call<BaseResponse> doMeetingRoomBook(@Body MeetingRoomRequest meetingRoomRequest);

    //DeskDescription
    @GET("api/Desks/{id}")
    Call<DeskDescriptionResponse> getDiskDescription(@Path("id") int deskId);

    @GET("api/MeetingRooms/{id}")
    Call<MeetingRoomDescriptionResponse> getMeetingRoomDescription(@Path("id") int roomId);

    @GET("api/ParkingSlot/{id}")
    Call<CarParkingDescriptionResponse> getCarParkingDescription(@Path("id") int carParkId);





}
