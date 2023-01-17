package com.brickendon.hdplus.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProfileResponse {

    @SerializedName("deskPhoneNumber")
    @Expose
    private String deskPhoneNumber;
    @SerializedName("active")
    @Expose
    private Boolean active;
    @SerializedName("normalizedEmail")
    @Expose
    private String normalizedEmail;
    @SerializedName("notificationType")
    @Expose
    private String notificationType;
    @SerializedName("tenantId")
    @Expose
    private String tenantId;
    @SerializedName("phoneNumber")
    @Expose
    private String phoneNumber;
    @SerializedName("permissions")
    @Expose
    private List<String> permissions = null;
    @SerializedName("defaultCarParkLocation")
    @Expose
    private DefaultCarParkLocation defaultCarParkLocation;
    @SerializedName("normalizedFirstName")
    @Expose
    private String normalizedFirstName;
    @SerializedName("gdprAccepted")
    @Expose
    private Boolean gdprAccepted;
    @SerializedName("workHoursTo")
    @Expose
    private String workHoursTo;
    @SerializedName("defaultLocation")
    @Expose
    private DefaultLocation defaultLocation;
    @SerializedName("fullName")
    @Expose
    private String fullName;
    @SerializedName("calendarIntegrationType")
    @Expose
    private String calendarIntegrationType;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("carparkBookAhead")
    @Expose
    private String carparkBookAhead;
    @SerializedName("gdpripAddress")
    @Expose
    private String gdpripAddress;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("currentTeam")
    @Expose
    private CurrentTeam currentTeam;
    @SerializedName("normalizedLastName")
    @Expose
    private String normalizedLastName;
    @SerializedName("highestRole")
    @Expose
    private String highestRole;
    @SerializedName("workHoursFrom")
    @Expose
    private String workHoursFrom;
    @SerializedName("teamMembershipId")
    @Expose
    private String teamMembershipId;
    @SerializedName("countryId")
    @Expose
    private String countryId;
    @SerializedName("gdprTimestamp")
    @Expose
    private String gdprTimestamp;
    @SerializedName("bookAhead")
    @Expose
    private String bookAhead;
    @SerializedName("hasPinSetup")
    @Expose
    private Boolean hasPinSetup;
    @SerializedName("preferredDesk")
    @Expose
    private PreferredDesk preferredDesk;
    @SerializedName("firstName")
    @Expose
    private String firstName;
    @SerializedName("lastName")
    @Expose
    private String lastName;
    @SerializedName("vehicleRegNumber")
    @Expose
    private String vehicleRegNumber;

    public String getDeskPhoneNumber() {
        return deskPhoneNumber;
    }

    public void setDeskPhoneNumber(String deskPhoneNumber) {
        this.deskPhoneNumber = deskPhoneNumber;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getNormalizedEmail() {
        return normalizedEmail;
    }

    public void setNormalizedEmail(String normalizedEmail) {
        this.normalizedEmail = normalizedEmail;
    }

    public String getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(String notificationType) {
        this.notificationType = notificationType;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }

    public DefaultCarParkLocation getDefaultCarParkLocation() {
        return defaultCarParkLocation;
    }

    public void setDefaultCarParkLocation(DefaultCarParkLocation defaultCarParkLocation) {
        this.defaultCarParkLocation = defaultCarParkLocation;
    }

    public String getNormalizedFirstName() {
        return normalizedFirstName;
    }

    public void setNormalizedFirstName(String normalizedFirstName) {
        this.normalizedFirstName = normalizedFirstName;
    }

    public Boolean getGdprAccepted() {
        return gdprAccepted;
    }

    public void setGdprAccepted(Boolean gdprAccepted) {
        this.gdprAccepted = gdprAccepted;
    }

    public String getWorkHoursTo() {
        return workHoursTo;
    }

    public void setWorkHoursTo(String workHoursTo) {
        this.workHoursTo = workHoursTo;
    }

    public DefaultLocation getDefaultLocation() {
        return defaultLocation;
    }

    public void setDefaultLocation(DefaultLocation defaultLocation) {
        this.defaultLocation = defaultLocation;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getCalendarIntegrationType() {
        return calendarIntegrationType;
    }

    public void setCalendarIntegrationType(String calendarIntegrationType) {
        this.calendarIntegrationType = calendarIntegrationType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCarparkBookAhead() {
        return carparkBookAhead;
    }

    public void setCarparkBookAhead(String carparkBookAhead) {
        this.carparkBookAhead = carparkBookAhead;
    }

    public String getGdpripAddress() {
        return gdpripAddress;
    }

    public void setGdpripAddress(String gdpripAddress) {
        this.gdpripAddress = gdpripAddress;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public CurrentTeam getCurrentTeam() {
        return currentTeam;
    }

    public void setCurrentTeam(CurrentTeam currentTeam) {
        this.currentTeam = currentTeam;
    }

    public String getNormalizedLastName() {
        return normalizedLastName;
    }

    public void setNormalizedLastName(String normalizedLastName) {
        this.normalizedLastName = normalizedLastName;
    }

    public String getHighestRole() {
        return highestRole;
    }

    public void setHighestRole(String highestRole) {
        this.highestRole = highestRole;
    }

    public String getWorkHoursFrom() {
        return workHoursFrom;
    }

    public void setWorkHoursFrom(String workHoursFrom) {
        this.workHoursFrom = workHoursFrom;
    }

    public String getTeamMembershipId() {
        return teamMembershipId;
    }

    public void setTeamMembershipId(String teamMembershipId) {
        this.teamMembershipId = teamMembershipId;
    }

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    public String getGdprTimestamp() {
        return gdprTimestamp;
    }

    public void setGdprTimestamp(String gdprTimestamp) {
        this.gdprTimestamp = gdprTimestamp;
    }

    public String getBookAhead() {
        return bookAhead;
    }

    public void setBookAhead(String bookAhead) {
        this.bookAhead = bookAhead;
    }

    public Boolean getHasPinSetup() {
        return hasPinSetup;
    }

    public void setHasPinSetup(Boolean hasPinSetup) {
        this.hasPinSetup = hasPinSetup;
    }

    public PreferredDesk getPreferredDesk() {
        return preferredDesk;
    }

    public void setPreferredDesk(PreferredDesk preferredDesk) {
        this.preferredDesk = preferredDesk;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getVehicleRegNumber() {
        return vehicleRegNumber;
    }

    public void setVehicleRegNumber(String vehicleRegNumber) {
        this.vehicleRegNumber = vehicleRegNumber;
    }

    public class DefaultCarParkLocation {

        @SerializedName("isLeafLocation")
        @Expose
        private Boolean isLeafLocation;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("parentLocationId")
        @Expose
        private String parentLocationId;
        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("timeZoneId")
        @Expose
        private String timeZoneId;
        @SerializedName("locationType")
        @Expose
        private String locationType;
        @SerializedName("isActive")
        @Expose
        private Boolean isActive;

        public Boolean getIsLeafLocation() {
            return isLeafLocation;
        }

        public void setIsLeafLocation(Boolean isLeafLocation) {
            this.isLeafLocation = isLeafLocation;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getParentLocationId() {
            return parentLocationId;
        }

        public void setParentLocationId(String parentLocationId) {
            this.parentLocationId = parentLocationId;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getTimeZoneId() {
            return timeZoneId;
        }

        public void setTimeZoneId(String timeZoneId) {
            this.timeZoneId = timeZoneId;
        }

        public String getLocationType() {
            return locationType;
        }

        public void setLocationType(String locationType) {
            this.locationType = locationType;
        }

        public Boolean getIsActive() {
            return isActive;
        }

        public void setIsActive(Boolean isActive) {
            this.isActive = isActive;
        }

    }


    public class DefaultLocation {

        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("timeZoneId")
        @Expose
        private String timeZoneId;
        @SerializedName("isLeafLocation")
        @Expose
        private Boolean isLeafLocation;
        @SerializedName("locationType")
        @Expose
        private String locationType;
        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("isActive")
        @Expose
        private Boolean isActive;
        @SerializedName("parentLocationId")
        @Expose
        private String parentLocationId;
        @SerializedName("description")
        @Expose
        private String description;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTimeZoneId() {
            return timeZoneId;
        }

        public void setTimeZoneId(String timeZoneId) {
            this.timeZoneId = timeZoneId;
        }

        public Boolean getIsLeafLocation() {
            return isLeafLocation;
        }

        public void setIsLeafLocation(Boolean isLeafLocation) {
            this.isLeafLocation = isLeafLocation;
        }

        public String getLocationType() {
            return locationType;
        }

        public void setLocationType(String locationType) {
            this.locationType = locationType;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public Boolean getIsActive() {
            return isActive;
        }

        public void setIsActive(Boolean isActive) {
            this.isActive = isActive;
        }

        public String getParentLocationId() {
            return parentLocationId;
        }

        public void setParentLocationId(String parentLocationId) {
            this.parentLocationId = parentLocationId;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

    }

    public class CurrentTeam {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("isLeafTeam")
        @Expose
        private Boolean isLeafTeam;
        @SerializedName("parentTeamId")
        @Expose
        private String parentTeamId;
        @SerializedName("isDeleted")
        @Expose
        private Boolean isDeleted;
        @SerializedName("managerUserIds")
        @Expose
        private List<Object> managerUserIds = null;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("automaticApprovalStatus")
        @Expose
        private String automaticApprovalStatus;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public Boolean getIsLeafTeam() {
            return isLeafTeam;
        }

        public void setIsLeafTeam(Boolean isLeafTeam) {
            this.isLeafTeam = isLeafTeam;
        }

        public String getParentTeamId() {
            return parentTeamId;
        }

        public void setParentTeamId(String parentTeamId) {
            this.parentTeamId = parentTeamId;
        }

        public Boolean getIsDeleted() {
            return isDeleted;
        }

        public void setIsDeleted(Boolean isDeleted) {
            this.isDeleted = isDeleted;
        }

        public List<Object> getManagerUserIds() {
            return managerUserIds;
        }

        public void setManagerUserIds(List<Object> managerUserIds) {
            this.managerUserIds = managerUserIds;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAutomaticApprovalStatus() {
            return automaticApprovalStatus;
        }

        public void setAutomaticApprovalStatus(String automaticApprovalStatus) {
            this.automaticApprovalStatus = automaticApprovalStatus;
        }

    }

    public class PreferredDesk {

        @SerializedName("code")
        @Expose
        private String code;
        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("description")
        @Expose
        private String description;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

    }

}