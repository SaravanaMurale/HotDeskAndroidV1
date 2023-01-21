package com.brickendon.hdplus.utils;

public class AppConstants {

    //public static final String BASE_URL="https://hotdeskplus-dev-api.azurewebsites.net";
//    public static final String BASE_URL="https://dev-api.hotdeskplus.com/";
    //Dev url
    public static final String BASE_URL="https://dev-api.hybridhero.com/";
    //Production Url
//    public static final String BASE_URL="https://api.hybridhero.com/";

    public static final String SODIUM_SECRET_CODE = "89def69f0bdddc995078037539dc6ef4f0bdbdd3fa04ef2d11eea30779d72ac6";
    public static final String SODIUM_NONCE = "c88529b087036c035be110e0fa5b6b63041ede30e2e69e90";

    //SharedPreference Constant
    public static final String HOMEFRAGMENTINSTANCESTRING = "HOME";
    public static final String BOOKFRAGMENTINSTANCESTRING = "BOOK";
    public static final String USERTOKEN = "USERTOKEN";
    public static final String LOGIN_CHECK = "LOGIN_CHECK";
    public static final String DARK_MODE_CHECK = "DARK_MODE_CHECK";
    public static final String USER_CURRENT_STATUS = "USER_CURRENT_STATUS";
    public static final String WELCOME_VIEWED_STATUS = "WELCOME_VIEWED_STATUS";
    public static final String USER_ID = "USER_ID";
    public static final String EXPIRY_TOKEN_DATE = "EXPIRY_TOKEN_DATE";
    public static final String EXPIRY_TOKEN_TIME = "EXPIRY_TOKEN_TIME";

    //User Login Credential for biometric access
    public static final String USER_DETAILS_SAVED_STATUS="USER_DETAILS_SAVED_STATUS";
    public static final String COMPANY_NAME = "COMPANY_NAME";
    public static final String CURRENT_TEAM = "CURRENT_TEAM";
    public static final String EMAIL = "EMAIL";
    public static final String PHONE_NUMBER = "PHONENUMBER";
    public static final String PASSWORD = "PASSWORD";
    public static final String TOKEN_EXPIRY_STATUS="TOKEN_EXPIRY_STATUS";
    public static final String USERNAME="USERNAME";
    public static final String SHOWNOTIFICATION = "SHOWNOTIFICATION";
    public static final String USERIMAGE="USERIMAGE";
    public static final String TENANTIMAGE="TENANTIMAGE";
    public static final String TEAMMEMBERSHIP_ID="TEAMMEMBERSHIP_ID";
    public static final String TEAM_ID="TEAM_ID";

    //Should Not Change(Clickable Constant)
    public static final String CHECKIN="CHCKIN";
    public static final String EDIT="EDIT";
    public static final String REMOTE="REMOTE";

    //Locate
    public static final String PARENT_ID_CHECK="PARENT_ID_CHECK";
    public static final String FLOOR_POSITION_CHECK="FLOOR_POSITION_CHCECK";

    public static final String PARENT_ID="PARENT_ID";
    public static final String SUB_PARENT_ID="SUB_PARENT_ID";
    public static final String FLOOR_POSITION="FLOOR_POSITION";
    public static final String FLOOR_SELECTED_STATUS="FLOOR_SELECTED_STATUS";
    public static final String VEHICLE_NUMBER="VEHICLE_NUMBER";

    public static final String FLOOR_ICON_BLINK="FLOOR_ICON_BLINK";

    public static final String LOCATION_ID="LOCATION_ID";
    public static final String LOCATION_ID_TEMP="LOCATION_ID_TEMP";
    public static final String COUNTRY_NAME="COUNTRY_NAME";
    public static final String BUILDING="BUILDING";
    public static final String FLOOR="FLOOR";
    public static final String FINAL_FLOOR="FINAL_FLOOR";
    public static final String FULLPATHLOCATION="FULLPATHLOCATION";
    public static final String SUPPORT_ZONE_ID="SUPPORT_ZONE_ID";
    public static final String DESK="3";
    public static final String MEETING="4";
    public static final String CAR_PARKING="5";
    public static final String NEW_FLOOR_POSITION="NEW_FLOOR_POSITION";
    public static final String LOGIN_RESPONSE = "LOGIN_RESPONSE";
    public static final String SAVETOKEN="SAVETOKEN";

    public static final String SHOW_PROFILE_CLICKED_STATUS="SHOW_PROFILE_CLICKED_STATUS";

    public static final String COUNTRY_NAME_CHECK="COUNTRY_NAME_CHECK";
    public static final String BUILDING_CHECK="BUILDING_CHECK";
    public static final String FLOOR_CHECK="FLOOR_CHECK";
    public static final String FINAL_FLOOR_CHECK="FINAL_FLOOR_CHECK";
    public static final String FULLPATHLOCATION_CHECK="FULLPATHLOCATION_CHECK";



    public static final String SELECTED_LOCATION_FROM_HOME="SELECTED_LOCATION_FROM_HOME";

    public static final String OFFICE_TODAY="You're in the office today";
    public static final String WORKING_REMOTE="You’re working remotely today";
    public static final String PIN_SETUP_DONE="PIN_SETUP_DONE";
    public static final String PIN_ACTIVE_STATUS_AFTER_LOGOUT="PIN_ACTIVE_STATUS_AFTER_LOGOUT";
    public static final String DEFAULT_CAR_PARK_LOCATION_ID="DEFAULT_CAR_PARK_LOCATION_ID";
    public static final String DEFAULT_CAR_PARK_TIMEZONE_ID="DEFAULT_CAR_PARK_TIMEZONE_ID";

    public static final String DEFAULT_LOCATION_NAME="DEFAULT_LOCATION_NAME";
    public static final String DEFAULT_LOCATION_ID="DEFAULT_LOCATION_ID";
    public static final String DEFAULT_TIME_ZONE_ID="DEFAULT_TIME_ZONE_ID";

    public static final String APPTHEME = "APPTHEME";
    public static final int MY_PERMISSIONS_REQUEST_CAMERA = 100;
    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;
    public static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 124;

    public static final String LANGUAGE = "LANGUAGE";
    public static final String LANGUAGE_KEY = "LANGUAGE_KEY";

    //New... LanguagePages
    public static final String GLOBAL_PAGE = "GLOBAL_PAGE";
    public static final String WELLBEING_PAGE = "WELLBEING_PAGE";

    public static final String WELLBEING_TYPE="WELLBEING_TYPE";
    public static final String TYPE_OF_LOGIN="TYPE_OF_LOGIN";
    public static final String PERSONAL_CONTENT="PERSONAL_CONTENT";
    public static final String PERSONAL_LINKS="PERSONAL_LINKS";

    public static Boolean FIRSTREFERAL = false;
    public static Boolean CHECKINPOPUPSTATUS = false;
    public static String REFERALID = "REFERALID";
    public static String REFERALCODEE = "REFERALCODEE";

    public static final String INCOMING = "INCOMING";
    public static final String OUTGOING = "OUTGOING";
    public static final String ACCEPT = "ACCEPT";
    public static final String REJECT = "REJECT";

    public static final String ROLE = "ROLE";
    public static final String SELECTED_COUNTRY = "SELECTED_COUNTRY";
    public static final String SELECTED_COUNTRY_ID = "SELECTED_COUNTRY_ID";

    //Multilanguage
    public static final String ME_PAGE = "ME_PAGE";
    public static final String BOOKING_PAGE = "BOOKING_PAGE";
    public static final String LOGIN_PAGE = "LOGIN_PAGE";
    public static final String COVIDSELFCERTIFICATION_PAGE = "COVIDSELFCERTIFICATION_PAGE";
    public static final String PERSONAL_HELP_PAGE = "PERSONAL_HELP_PAGE";
    public static final String APPROVALS_PAGE = "APPROVALS_PAGE";
    public static final String REQUESTS_PAGE = "REQUESTS_PAGE";
    public static final String CARPARK_PAGE = "CARPARK_PAGE";
    public static final String RESET_PASSWORD_PAGE = "RESET_PASSWORD_PAGE";
    public static final String ACCOUNTSETTINGS_PAGE = "ACCOUNTSETTINGS_PAGE";
    public static final String COMMON_TITLES_PAGE = "COMMON_TITLES_PAGE";
    public static final String SEARCH_PAGE = "SEARCH_PAGE";
    public static final String APP_FEEDBACK_PAGE = "APP_FEEDBACK_PAGE";
    public static final String ACTIONOVERLAYS_PAGE = "ACTIONOVERLAYS_PAGE";
    public static final String SETTINGS_PAGE = "SETTINGS_PAGE";
    public static final String ACTION_OVERLAYS_PAGE = "ACTION_OVERLAYS_PAGE";
    public static final String MEETING_ROOM_PAGE = "MEETING_ROOM_PAGE";
    public static final String APPKEYS_PAGE = "APPKEYS_PAGE";

    public static final String Administrator = "Administrator";
    public static final String FacilityManager = "Facility Manager";
    public static final String MeetingManager = "Meeting Room Manager";
    public static final String TeamManager = "Team Manager";
    public static final String Manager = "Manager";

    public static final String DefaultLocation = "DefaultLocation";
    public static final String ParkingLocation = "ParkingLocation";
    public static final String FROM = "FROM";

    public static final String DEFAULT_LOCATION_UNIQUE_ID="DEFAULT_LOCATION_UNIQUE_ID";

}
