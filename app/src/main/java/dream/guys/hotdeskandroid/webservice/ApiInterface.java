package dream.guys.hotdeskandroid.webservice;

import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import dream.guys.hotdeskandroid.model.request.*;
import dream.guys.hotdeskandroid.model.request.CarParkingDeleteRequest;
import dream.guys.hotdeskandroid.model.request.ChangePasswordRequest;
import dream.guys.hotdeskandroid.model.request.CovidAnswerRequest;
import dream.guys.hotdeskandroid.model.request.CreatePinRequest;
import dream.guys.hotdeskandroid.model.request.DAODeskAccept;
import dream.guys.hotdeskandroid.model.request.DAODeskReject;
import dream.guys.hotdeskandroid.model.request.DeleteMeetingRoomRequest;
import dream.guys.hotdeskandroid.model.request.ForgotPasswordRequest;
import dream.guys.hotdeskandroid.model.request.GDPRrequest;
import dream.guys.hotdeskandroid.model.request.GetTokenRequest;
import dream.guys.hotdeskandroid.model.request.LocateBookingRequest;
import dream.guys.hotdeskandroid.model.request.LocateCarParkBookingRequest;
import dream.guys.hotdeskandroid.model.request.LocateCarParkEditRequest;
import dream.guys.hotdeskandroid.model.request.LocateDeskBookEditFromAndToRequest;
import dream.guys.hotdeskandroid.model.request.LocateDeskBookEditFromRequest;
import dream.guys.hotdeskandroid.model.request.LocateDeskBookingRequest;
import dream.guys.hotdeskandroid.model.request.LocateDeskDeleteRequest;
import dream.guys.hotdeskandroid.model.request.LocationMR_Request;
import dream.guys.hotdeskandroid.model.request.MeetingRoomEditRequest;
import dream.guys.hotdeskandroid.model.request.MeetingRoomRecurrence;
import dream.guys.hotdeskandroid.model.request.MeetingRoomRequest;
import dream.guys.hotdeskandroid.model.request.OtherBookingRequest;
import dream.guys.hotdeskandroid.model.request.PersonalHelpRequest;
import dream.guys.hotdeskandroid.model.request.QuestionListRequest;
import dream.guys.hotdeskandroid.model.request.ReportIssueRequest;
import dream.guys.hotdeskandroid.model.request.TokenRequest;
import dream.guys.hotdeskandroid.model.response.ActiveTeamsResponse;
import dream.guys.hotdeskandroid.model.response.AmenitiesResponse;
import dream.guys.hotdeskandroid.model.response.*;
import dream.guys.hotdeskandroid.model.response.BookingForEditResponse;
import dream.guys.hotdeskandroid.model.response.BookingListResponse;
import dream.guys.hotdeskandroid.model.response.CarParkAvalibilityResponse;
import dream.guys.hotdeskandroid.model.response.CarParkListToEditResponse;
import dream.guys.hotdeskandroid.model.response.CarParkLocationsModel;
import dream.guys.hotdeskandroid.model.response.CarParkingDescriptionResponse;
import dream.guys.hotdeskandroid.model.response.CarParkingForEditResponse;
import dream.guys.hotdeskandroid.model.response.CarParkingslotsResponse;
import dream.guys.hotdeskandroid.model.response.CheckPinLoginResponse;
import dream.guys.hotdeskandroid.model.response.CompanyDefaultResponse;
import dream.guys.hotdeskandroid.model.response.CovidQuestionsResponse;
import dream.guys.hotdeskandroid.model.response.DAOActiveLocation;
import dream.guys.hotdeskandroid.model.response.DAOCountryList;
import dream.guys.hotdeskandroid.model.response.DAOTeamMember;
import dream.guys.hotdeskandroid.model.response.DAOUpcomingBooking;
import dream.guys.hotdeskandroid.model.response.DeskAvaliabilityResponse;
import dream.guys.hotdeskandroid.model.response.DeskDescriptionResponse;
import dream.guys.hotdeskandroid.model.response.DeskResponseNew;
import dream.guys.hotdeskandroid.model.response.DeskRoomCountResponse;
import dream.guys.hotdeskandroid.model.response.FirstAidResponse;
import dream.guys.hotdeskandroid.model.response.GetTokenResponse;
import dream.guys.hotdeskandroid.model.response.GlobalSearchResponse;
import dream.guys.hotdeskandroid.model.response.ImageResponse;
import dream.guys.hotdeskandroid.model.response.IncomingRequestResponse;
import dream.guys.hotdeskandroid.model.response.LocateCountryRespose;
import dream.guys.hotdeskandroid.model.response.LocationWithMR_Response;
import dream.guys.hotdeskandroid.model.response.MeetingListToEditResponse;
import dream.guys.hotdeskandroid.model.response.MeetingRoomDescriptionResponse;
import dream.guys.hotdeskandroid.model.response.ParkingSpotModel;
import dream.guys.hotdeskandroid.model.response.ParticipantDetsilResponse;
import dream.guys.hotdeskandroid.model.response.ProfilePicResponse;
import dream.guys.hotdeskandroid.model.response.QuestionListResponse;
import dream.guys.hotdeskandroid.model.response.RoomListResponse;
import dream.guys.hotdeskandroid.model.response.TeamDeskResponse;
import dream.guys.hotdeskandroid.model.response.TeamMembersResponse;
import dream.guys.hotdeskandroid.model.response.TypeOfLoginResponse;
import dream.guys.hotdeskandroid.model.response.TeamsResponse;
import dream.guys.hotdeskandroid.model.response.UsageTypeResponse;
import dream.guys.hotdeskandroid.model.response.UserAllowedMeetingResponse;
import dream.guys.hotdeskandroid.model.response.UserDetailsResponse;
import dream.guys.hotdeskandroid.model.response.WellbeingConfigResponse;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface ApiInterface {
    // Pin APis
    // to check wheter this user can setup pin or not
    @POST("api/settings/PrivacyPolicyURL")
    Call<String> privacyPolicy(@Body JsonObject jsonObject);

    @GET("api/settings/setting")
    Call<String> getSettingData(@Query("name") String name);
    @GET("/api/Settings/CompanyDefaultSettings")
    Call<CompanyDefaultResponse> getCompanyDefaultSettings();


    @POST("api/account/TypeOfLogin")
    Call<TypeOfLoginResponse> typeOfLogin(@Body JsonObject jsonObject);
    @PUT("api/Booking/Clear")
    Call<BaseResponse> clearBooking(@Body JsonObject jsonObject);
    @PUT("api/Booking/FullDayBooking")
    Call<BaseResponse> fullDayBooking(@Body JsonObject jsonObject);

    @POST("api/account/TokenExchange")
    Call<GetTokenResponse> tokenExchange(@Body JsonObject jsonObject);

    @POST("api/settings/PinNumberSetting")
    Call<Boolean> checkPinEnabled();

    //Qr enabled
    @GET("api/meetingrooms/amenities")
    Call<List<AmenitiesResponse>> getAmenities();
    //get parking location list
    @GET("api/ParkingSlot/locationswithparkingslots")
    Call<List<CarParkLocationsModel>> getCarParkLocation();

    //Book desk count api
    @GET("api/Calendar/DailyTeamDeskCount")
    Call<List<DeskRoomCountResponse>> getDailyDeskCount(@Query("month") String s, @Query("teamId") String teamId);
    //Book desk count api with location
    @GET("api/Calendar/DailyLocationDeskCount")
    Call<List<DeskRoomCountResponse>> getDailyDeskCountLocation(@Query("month") String s ,
                                                                @Query("locationId") String locationId,
                                                                @Query("startTime") String startTime,
                                                                @Query("endTime") String endTime);


    //parking spot list
    @GET("api/ParkingSlot/carparkparkingslots")
    Call<List<ParkingSpotModel>> getParkingSpotModels(@Query("locationId") String s);
//    //parking spot list
//    @GET("api/ParkingSlot/parkingslotsbylocation")
//    Call<List<ParkingSpotModel>> getParkingSpotModels(@Query("locationId") String s);

    //Book meeting room count api
    @GET("api/MeetingRooms/DailyMeetingRoomCounts")
    Call<List<DeskRoomCountResponse>> getDailyRoomCount(@Query("month") String s,
                                                        @Query("teamId") String teamId);
    //Book meeting room count api Location
    @GET("api/MeetingRooms/DailyMeetingRoomCounts")
    Call<List<DeskRoomCountResponse>> getDailyRoomCountLocation(@Query("month") String s,
                                                                @Query("locationId") String locationId,
                                                                @Query("startTime") String startTime,
                                                                @Query("endTime") String endTime
    );

    //Book parking count api
    @GET("api/ParkingSlot/DailyCarParkCounts")
    Call<List<DeskRoomCountResponse>> getDailyParkingCount(@Query("month") String s,
                                                           @Query("teamId") String teamId);

    //Book parking count api Location
    @GET("api/ParkingSlot/DailyCarParkCounts")
    Call<List<DeskRoomCountResponse>> getDailyParkingCountLocation(@Query("month") String s,
                                                                   @Query("locationId") String teamId,
                                                                   @Query("startTime") String startTime,
                                                                   @Query("endTime") String endTime
    );

    @GET("api/Settings/QRCheckInEnforcementEnabled")
    Call<Boolean> getQrEnabled();

    // sign up for Pin login
    @POST("api/account/UpdateSecurityPin")
    Call<BaseResponse> createPin(@Body CreatePinRequest createPinRequest);

    @POST("api/account/HasSetupPinNumberForTenantUser")
    Call<CheckPinLoginResponse> checkPinLoginAvailable(@Body CreatePinRequest createPinRequest);

    @POST("api/Account/pin")
    Call<GetTokenResponse> checkPinLogin(@Body CreatePinRequest createPinRequest);


    @POST("api/Account/updategdpracceptancesettings")
    Call<Void> updateGDPR(@Body GDPRrequest request);

    @POST("api/Account/Token")
    Call<GetTokenResponse> getLoginToken(@Body GetTokenRequest request);

    @GET("api/image/user")
    Call<ImageResponse> getUserImage();

    @GET("api/image/tenant")
    Call<ImageResponse> getTenantImage();

    @POST("api/Account/RequestPasswordReset")
    Call<Boolean> requestPasswordReset(@Body ForgotPasswordRequest forgotPasswordRequest);

    @GET("api/requests/incoming")
    Call<IncomingRequestResponse> getIncomingRequest(@Query("includePastRequests") boolean includePastRequests);

    @GET("api/Account/LoggedInUser")
    Call<UserDetailsResponse> getLoginUserDetails();

    @GET("api/mywork/teammemberstatus")
    Call<List<TeamMembersResponse>> getTeamMembers(@Query("date") String date,
                                                   @Query("teamId") int teamId);

    @GET("api/MyWork/CurrentBooking")
    Call<BookingListResponse.DayGroup.CalendarEntry> getCurrentBookingStatus(@Query("date") String date,
                                                   @Query("teamId") int teamId);
    @GET("api/MyWork/CurrentBooking")
    Call<BookingListResponse.DayGroup> getCurrentBookingStatus();

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

    @GET("api/locate/ImmediateChildLocations")
    Call<List<LocateCountryRespose>> getLocationCountryList();

    @GET("api/locate/ImmediateChildLocations")
    Call<List<LocateCountryRespose>> getCountrysChild(@Query("parentId") int parentId);

    @GET("api/locate/ImmediateChildLocations")
    Call<Response> getCountrysChildResponse(@Query("parentId") int parentId);


//    @GET("api/deskLayouts/LocationDesksWithUser")
    @GET("api/deskLayouts/LocationDesksWithUserV2Mobile")
    Call<DeskAvaliabilityResponse> getAvaliableDeskDetails(@Query("locationId") int parentId,
                                                           @Query("toDate") String toDate,
                                                           @Query("fromTime") String fromTime,
                                                           @Query("toTime") String toTime);
//    @GET("api/deskLayouts/LocationDesksWithUser")
    @GET("api/deskLayouts/LocationDesksWithUserV2Mobile")
    Call<BookingForEditResponse> getAvaliableDeskDetailsForDeskList(@Query("locationId") int parentId,
                                                           @Query("toDate") String toDate,
                                                           @Query("fromTime") String fromTime,
                                                           @Query("toTime") String toTime);
//    @GET("api/meetingrooms/allwithavailability")
    @GET("api/MeetingRooms/AllMeetingRoomsWithLocationsV2Mobile")
    Call<List<UserAllowedMeetingResponse>> getAvaliableRoomDetailsForRoomList(@Query("locationId") int parentId,
                                                              @Query("bookingDate") String toDate,
                                                              @Query("from") String fromTime,
                                                              @Query("to") String toTime);

    @GET("api/booking/bookingsForEdit")
    Call<BookingForEditResponse> getBookingsForEdit(@Query("teamId") int parentId,
                                                    @Query("teamMembershipId") int toDate,
                                                    @Query("fromDate") String fromTime,
                                                    @Query("toDate") String toTime);

    @GET("api/booking/TeamDeskAvailabilityV2Mobile")
    Call<List<BookingForEditResponse.TeamDeskAvailabilities>> getTeamDeskAvailability(@Query("teamId") int parentId,
                                                    @Query("fromDate") String fromTime,
                                                    @Query("toDate") String toTime);
    @GET("api/booking/TeamDeskAvailabilityV2Mobile")
    Call<BookingForEditResponse> getUpdaedTeamDeskAvailability(@Query("teamId") int parentId,
                                                    @Query("fromDate") String fromTime,
                                                    @Query("toDate") String toTime);


    @GET("api/locate/ImmediateChildLocations")
    Call<List<String>> getItemJsonObject(@Query("parentId") int parentId);

    //DeskBooking
    @PUT("api/booking/bookings")
    Call<BaseResponse> doDeskBooking(@Body LocateBookingRequest locateBookingRequest);


    //DeskBooking
    @PUT("api/booking/bookings")
    Call<BaseResponse> doEditDeskBooking(@Body LocateDeskBookEditFromRequest locateBookingRequest);

    //DeskBookingRequest
    @PUT("api/booking/bookings")
    Call<BaseResponse> doRequestDeskBooking(@Body LocateDeskBookingRequest locateBookingRequest);

    //DeskBookingDelete
    @PUT("api/booking/bookings")
    Call<BaseResponse> doDeleteDeskBooking(@Body LocateDeskDeleteRequest locateDeskDeleteRequest);


    //CarParkBooking
    @PUT("api/carparkbooking/bookings")
    Call<BaseResponse> doCarParkingBooking(@Body LocateCarParkBookingRequest locateCarParkBookingRequest);

    //CarPark edit
    @PUT("api/carparkbooking/bookings")
    Call<BaseResponse> doCarParkingEdit(@Body LocateCarParkEditRequest locateCarParkBookingRequest);

    //CarPark Delete
    @PUT("api/carparkbooking/bookings")
    Call<BaseResponse> doDeleteCarParking(@Body CarParkingDeleteRequest carParkingDeleteRequest);

    @GET("api/teams")
    Call<List<TeamsResponse>> getTeams();

    @GET("api/Teams/ActiveTeams")
    Call<List<ActiveTeamsResponse>> getActiveTeams();

    //CarPark Availability Checking
    @GET("api/ParkingSlot/carparkparkingslots")
    Call<List<CarParkingslotsResponse>> getCarParkingSlots(@Query("locationId") int locationId);

    //CarPark Availability Checking
    @GET("api/CarParkBooking/availability")
    Call<List<CarParkAvalibilityResponse>> getCarParkingSlotAvalibility(@Query("date") String toDate,
                                                                        @Query("from") String fromTime,
                                                                        @Query("to") String toTime);

    //Get Parking List To Edit
    @GET("api/carparkbooking/bookings")
    Call<CarParkingForEditResponse> getCarParkingEditList(@Query("fromDate") String fromDate,
                                                          @Query("toDate") String toDate,
                                                          @Query("slotId") int slotId);

    //MeetingRoomBook
    @PUT("api/MeetingRoomBooking/bookings")
    Call<BaseResponse> doMeetingRoomBook(@Body MeetingRoomRequest meetingRoomRequest);

    //MeetingEditListResponseInBooking
    @GET("api/MeetingRoomBooking/bookings")
    Call<List<MeetingListToEditResponse>> getMeetingListToEdit(@Query("fromDate") String fromDate,
                                                               @Query("toDate") String toDate);

    //MeetingEditListResponseILocate
    @GET("api/MeetingRoomBooking/bookings")
    Call<List<MeetingListToEditResponse>>  getMeetingListToEditInLocate( @Query("fromDate") String fromDate,
                                                                        @Query("toDate") String toDate,
                                                                         @Query("roomIds") String roomId);

    //Car park edit list
    @GET("api/CarParkBooking/dailyBookings")
    Call<List<CarParkListToEditResponse>> getCarParkListToEdit(@Query("fromDate") String fromDate,
                                                               @Query("toDate") String toDate);


    //MettingRoomEditInLocate
    @PUT("api/MeetingRoomBooking/bookings")
    Call<BaseResponse> doRoomEdit(@Body MeetingRoomEditRequest meetingRoomRequest);

    //MeetingRoomDelete
    @PUT("api/MeetingRoomBooking/Bookings")
    Call<BaseResponse> doDeleteMeetingRoom(@Body DeleteMeetingRoomRequest deleteMeetingRoomRequest);

    //MeetingUnavalibilityChecking
//    @GET("api/meetingrooms/userallowedmeetingrooms")
    @GET("api/meetingrooms/UserAllowedMeetingRoomsV2Mobile")
    Call<List<UserAllowedMeetingResponse>> userAllowedMeetings();

    //Get All Meetings
    @GET("api/MeetingRooms/AllMeetingRoomsWithLocationsV2Mobile")
    Call<List<UserAllowedMeetingResponse>> getAllMeetings();



    @POST("api/MeetingRooms/locationsWithMR")
    Call<List<LocationWithMR_Response>> getLocationMR(@Body LocationMR_Request locationMR_request);

    //DeskDescription
    @GET("api/Desks/{id}")
    Call<DeskDescriptionResponse> getDiskDescription(@Path("id") int deskId);

    //RoomDescription
    @GET("api/MeetingRooms/{id}")
    Call<MeetingRoomDescriptionResponse> getMeetingRoomDescription(@Path("id") int roomId);

    //CarParkDescription
    @GET("api/ParkingSlot/{id}")
    Call<CarParkingDescriptionResponse> getCarParkingDescription(@Path("id") int carParkId);

    //Meeting Participant Keyword Search
    @GET("api/users/Suggessions")
    Call<List<ParticipantDetsilResponse>> getParticipantDetails(@Query("term") String term, @Query("scope") int scope);

    //Update Setting
    @POST("api/account/UpdateProfileSettings")
    Call<ProfileResponse> updateSetting(@Body UserDetailsResponse userDetailsResponse);

    //UpdateProfile Pic
    @POST("api/image/user")
    Call<BaseResponse> updateProfilePicture(@Body ProfilePicResponse base64Img);

    //Get Profile Pic
    @GET("api/image/user")
    Call<ProfilePicResponse> getProfilePicture();

    @FormUrlEncoded
    @POST("api/push/setting")
    Call<BaseResponse> updateNotifications(
            @Field("notification") int notification);

    //Remove Profile Pic
    @POST("api/image/user")
    Call<BaseResponse>  removeProfilePicture(@Body ProfilePicResponse base64Img);


    //GetDesk Code in EditActivity
    @GET("api/mywork/teamdesks")
    Call<List<TeamDeskResponse>> getDeskListInEdit();

    //deskFeedback
    @POST("api/wellness/deskFeedback")
    Call<Void> postFeedback(@Body ReportIssueRequest reportIssueRequest);
    //New...
    //https://dev-api.hotdeskplus.com/api/locations/activeLocations
    @GET("api/locations/activeLocations")
    Call<ArrayList<DAOActiveLocation>> getActiveLocations();

    @GET("api/settings/WellbeingSectionConfig")
    Call<List<WellbeingConfigResponse>> getWellbeingSectionConfig();
    //Wellbeing
    @GET("api/wellness/CovidSelfCertificationdQuestions")
    Call<List<CovidQuestionsResponse>> getCovidQuestions(@Query("language") String language);

    @POST("api/wellness/SubmitCovidSelfCertification")
    Call<Void> submitCovidAnswer(@Body CovidAnswerRequest covidAnswerRequest);

    @GET("api/settings/WellbeingSectionConfig")
    Call<List<FirstAidResponse>> getFirstAidResponse();


    //Now Working
    @GET("api/mywork/myteammemberstatus")
    Call<ArrayList<DAOTeamMember>> getTeamMembers(@Query("date") String date,@Query("teamId") String t,
                                                  @Query("returnProfilePhotoUrls") boolean b1,
                                                  @Query("returnProfilePhotos") boolean b2);
    @GET("api/mywork/teammemberstatus")
    Call<ArrayList<DAOTeamMember>> getTeamMembersWithImage(
            @Query("date") String date,
                                                  @Query("teamId") String teamId,
                                                  @Query("returnProfilePhotoUrls") boolean b1,
                                                  @Query("returnProfilePhotos") boolean b2);

    //https://dev-api.hotdeskplus.com/api/requests/outgoing?includePastRequests=true

    @GET("api/requests/outgoing")
    Call<IncomingRequestResponse> getOutgoingRequest(@Query("includePastRequests") boolean includePastRequests,
                                                     @Query("fromMobile") boolean fromMobile);
    @GET
    Call<DeskResponseNew> getDesk(@Url String url);

    @POST("api/wellness/personalHelpRequest")
    Call<Void> postPersonalHelp(@Body PersonalHelpRequest personalHelpRequest);

    @POST("api/wellness/completeAndSign")
    Call<ResponseBody> completeAndSign(@Body QuestionListRequest questionListRequest);

    @GET("api/wellness/checklistQuestions")
    Call<List<QuestionListResponse>> getQuestionList();

    @POST("api/requests/rejectdesk")
    Call<BaseResponse> rejectDesk(@Body DAODeskReject daoDeskReject);
    //Call<BaseResponse> rejectDesk(@Field("id") int id,@Field("reason") String reason);

    @POST("api/requests/approvedesk")
    Call<BaseResponse> acceptDesk(@Body DAODeskAccept daoDeskAccept);

    //To save firebase token
    @POST("api/push/register")
    Call<BaseResponse> saveFirebaseToken(@Body TokenRequest token);

    @POST("api/Account/ChangePassword")
    Call<BaseResponse> changePassword(@Body ChangePasswordRequest changePasswordRequest);

    //Parking Slot...
    @POST("api/requests/rejectps")
    Call<BaseResponse> rejectParking(@Body DAODeskReject daoDeskReject);

    @POST("api/requests/approveps")
    Call<BaseResponse> acceptParking(@Body String params);

    @GET("api/Users/Countries")
    Call<ArrayList<DAOCountryList>> getCountryList();

    //Meeting...
    @POST("api/requests/rejectmr")
    Call<BaseResponse> rejectMeeting(@Body DAODeskReject daoDeskReject);

    @POST("api/requests/approvemr")
    Call<BaseResponse> acceptMeeting(@Body String params);

//    @GET("api/calendar/entries")
    @GET("api/Booking/UserBookings")
    Call<ArrayList<DAOUpcomingBookingNew>> getMonthBookings(@Query("fromDate") String f,
                                                         @Query("toDate") String t,
                                                         @Query("userId") int userId);

    @GET("api/Booking/UsageTypes")
    Call<List<UsageTypeResponse>>  getBookingUsageTypes();

    //RepeatDeskBookingForCurrrentWeek
    @PUT("api/booking/bookings")
    Call<BaseResponse> doRepeatBookingForWeek(@Body LocateBookingRequest locateBookingRequest);

    //RepeatDeskBookingRequestForCurrrentWeek
    @PUT("api/booking/bookings")
    Call<BaseResponse> doRepeatRequestDeskBooking(@Body LocateDeskBookingRequest locateBookingRequest);

    //RepeatCarBookingForCurrrentWeek
    @PUT("api/carparkbooking/bookings")
    Call<BaseResponse> doRepeatCarParkBooking(@Body LocateCarParkBookingRequest locateCarParkBookingRequest);


    //MeetingRoomRecurrence
    @PUT("api/MeetingRoomBooking/bookings")
    Call<BaseResponse> doMeetingRoomRecurrence(@Body MeetingRoomRecurrence meetingRoomRecurrence);

    //RepeatMeetingRoomBookingForCurrentWeek
    @PUT("api/MeetingRoomBooking/bookings")
    Call<BaseResponse> doRepeatMeetingRoomBooking(@Body MeetingRoomRequest meetingRoomRequest);

    //Settings/VehicleRegistrationRequired
    @GET("api/Settings/VehicleRegistrationRequired")
    Call<Boolean> getIsVehicleReg();

    @GET("https://dev-api.hotdeskplus.com/api/Download/Help")
    @Streaming
    Call<ResponseBody> downloadPdf();


    //Desk Booked Data
    @GET("api/Booking/BookingsOfDesk")
    Call<List<BookedDeskResponse>> getDeskBookedTime(@Query("bookingDate") String bookingDate,@Query("deskId") int deskId);

    //Car Booked Data
    @GET("api/CarParkBooking/Bookings")
    Call<BookedCarResponse> getCarBookedTime(@Query("fromDate") String fromDate,
                                             @Query("toDate") String toDate,
                                             @Query("slotId") int slotId,
                                             @Query("locationId") int locationId);

    @GET("api/MeetingRoomBooking/Bookings")
    Call<List<BookedMeetingResponse>> getMeetingBookedTime(@Query("fromDate") String fromDate,
                                                     @Query("toDate") String toDate,
                                                     @Query("roomId") int locationId);



    @PUT("api/booking/bookings")
    Call<BaseResponse> otherBookings(@Body OtherBookingRequest otherBookingRequest);
}


