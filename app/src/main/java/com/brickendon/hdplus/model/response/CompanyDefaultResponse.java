package com.brickendon.hdplus.model.response;

import com.google.gson.annotations.SerializedName;

public class CompanyDefaultResponse {
    private int deskShuffleLimit;
    private int workFromHomeLimit;
    private UserWorkHours userWorkHours;
    private DeskAvailabilityHours deskAvailabilityHours;
    private ParkingSlotAvailabilityHours parkingSlotAvailabilityHours;
    private int adminCancelMeetingGraceTimeInMinutes;
    private int passwordLength;
    private boolean passwordChangeEnforcementEnabled;
    private int passwordChangeEnforcementPeriodInDays;
    private boolean autoCheckIn;
    private boolean hideMobileWellbeingTab;
    private boolean disableWellbeingTab;
    private boolean disableMeetingRoomTab;
    private boolean disableCarParkTab;
    private int typeOfLogin;
    private boolean managersToDownloadCSVAtCalenderEnabled;
    private boolean preventReUseLastPasswordsEnabled;
    private int numberOfLastPasswordsToBePrevented;
    private int notificationType;
    private boolean qrCheckIn;
    private boolean vehicleRegRequired;

    public int getDeskShuffleLimit() {
        return deskShuffleLimit;
    }

    public void setDeskShuffleLimit(int deskShuffleLimit) {
        this.deskShuffleLimit = deskShuffleLimit;
    }

    public int getWorkFromHomeLimit() {
        return workFromHomeLimit;
    }

    public void setWorkFromHomeLimit(int workFromHomeLimit) {
        this.workFromHomeLimit = workFromHomeLimit;
    }

    public UserWorkHours getUserWorkHours() {
        return userWorkHours;
    }

    public void setUserWorkHours(UserWorkHours userWorkHours) {
        this.userWorkHours = userWorkHours;
    }

    public DeskAvailabilityHours getDeskAvailabilityHours() {
        return deskAvailabilityHours;
    }

    public void setDeskAvailabilityHours(DeskAvailabilityHours deskAvailabilityHours) {
        this.deskAvailabilityHours = deskAvailabilityHours;
    }

    public ParkingSlotAvailabilityHours getParkingSlotAvailabilityHours() {
        return parkingSlotAvailabilityHours;
    }

    public void setParkingSlotAvailabilityHours(ParkingSlotAvailabilityHours parkingSlotAvailabilityHours) {
        this.parkingSlotAvailabilityHours = parkingSlotAvailabilityHours;
    }

    public int getAdminCancelMeetingGraceTimeInMinutes() {
        return adminCancelMeetingGraceTimeInMinutes;
    }

    public void setAdminCancelMeetingGraceTimeInMinutes(int adminCancelMeetingGraceTimeInMinutes) {
        this.adminCancelMeetingGraceTimeInMinutes = adminCancelMeetingGraceTimeInMinutes;
    }

    public int getPasswordLength() {
        return passwordLength;
    }

    public void setPasswordLength(int passwordLength) {
        this.passwordLength = passwordLength;
    }

    public boolean isPasswordChangeEnforcementEnabled() {
        return passwordChangeEnforcementEnabled;
    }

    public void setPasswordChangeEnforcementEnabled(boolean passwordChangeEnforcementEnabled) {
        this.passwordChangeEnforcementEnabled = passwordChangeEnforcementEnabled;
    }

    public int getPasswordChangeEnforcementPeriodInDays() {
        return passwordChangeEnforcementPeriodInDays;
    }

    public void setPasswordChangeEnforcementPeriodInDays(int passwordChangeEnforcementPeriodInDays) {
        this.passwordChangeEnforcementPeriodInDays = passwordChangeEnforcementPeriodInDays;
    }

    public boolean isAutoCheckIn() {
        return autoCheckIn;
    }

    public void setAutoCheckIn(boolean autoCheckIn) {
        this.autoCheckIn = autoCheckIn;
    }

    public boolean isHideMobileWellbeingTab() {
        return hideMobileWellbeingTab;
    }

    public void setHideMobileWellbeingTab(boolean hideMobileWellbeingTab) {
        this.hideMobileWellbeingTab = hideMobileWellbeingTab;
    }

    public boolean isDisableWellbeingTab() {
        return disableWellbeingTab;
    }

    public void setDisableWellbeingTab(boolean disableWellbeingTab) {
        this.disableWellbeingTab = disableWellbeingTab;
    }

    public boolean isDisableMeetingRoomTab() {
        return disableMeetingRoomTab;
    }

    public void setDisableMeetingRoomTab(boolean disableMeetingRoomTab) {
        this.disableMeetingRoomTab = disableMeetingRoomTab;
    }

    public boolean isDisableCarParkTab() {
        return disableCarParkTab;
    }

    public void setDisableCarParkTab(boolean disableCarParkTab) {
        this.disableCarParkTab = disableCarParkTab;
    }

    public int getTypeOfLogin() {
        return typeOfLogin;
    }

    public void setTypeOfLogin(int typeOfLogin) {
        this.typeOfLogin = typeOfLogin;
    }

    public boolean isManagersToDownloadCSVAtCalenderEnabled() {
        return managersToDownloadCSVAtCalenderEnabled;
    }

    public void setManagersToDownloadCSVAtCalenderEnabled(boolean managersToDownloadCSVAtCalenderEnabled) {
        this.managersToDownloadCSVAtCalenderEnabled = managersToDownloadCSVAtCalenderEnabled;
    }

    public boolean isPreventReUseLastPasswordsEnabled() {
        return preventReUseLastPasswordsEnabled;
    }

    public void setPreventReUseLastPasswordsEnabled(boolean preventReUseLastPasswordsEnabled) {
        this.preventReUseLastPasswordsEnabled = preventReUseLastPasswordsEnabled;
    }

    public int getNumberOfLastPasswordsToBePrevented() {
        return numberOfLastPasswordsToBePrevented;
    }

    public void setNumberOfLastPasswordsToBePrevented(int numberOfLastPasswordsToBePrevented) {
        this.numberOfLastPasswordsToBePrevented = numberOfLastPasswordsToBePrevented;
    }

    public int getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(int notificationType) {
        this.notificationType = notificationType;
    }

    public boolean isQrCheckIn() {
        return qrCheckIn;
    }

    public void setQrCheckIn(boolean qrCheckIn) {
        this.qrCheckIn = qrCheckIn;
    }

    public boolean isVehicleRegRequired() {
        return vehicleRegRequired;
    }

    public void setVehicleRegRequired(boolean vehicleRegRequired) {
        this.vehicleRegRequired = vehicleRegRequired;
    }

    public class DeskAvailabilityHours{
        private String from;
        @SerializedName("to")
        private String myto;

        public String getFrom() {
            return from;
        }

        public void setFrom(String from) {
            this.from = from;
        }

        public String getMyto() {
            return myto;
        }

        public void setMyto(String myto) {
            this.myto = myto;
        }
    }

    public class ParkingSlotAvailabilityHours{
        private String from;
        @SerializedName("to")
        private String myto;

        public String getFrom() {
            return from;
        }

        public void setFrom(String from) {
            this.from = from;
        }

        public String getMyto() {
            return myto;
        }

        public void setMyto(String myto) {
            this.myto = myto;
        }
    }

    public class UserWorkHours{
        private String from;
        @SerializedName("to")
        private String myto;

        public String getFrom() {
            return from;
        }

        public void setFrom(String from) {
            this.from = from;
        }

        public String getMyto() {
            return myto;
        }

        public void setMyto(String myto) {
            this.myto = myto;
        }
    }
}
