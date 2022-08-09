package dream.guys.hotdeskandroid.model.response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class UserDetailsResponse {

    @SerializedName("id")
    private int id;
    @SerializedName("firstName")
    private String firstName;
    @SerializedName("lastName")
    private String lastName;
    @SerializedName("fullName")
    private String fullName;
    @SerializedName("email")
    private String email;
    @SerializedName("active")
    private boolean activeStatus;
    @SerializedName("teamMembershipId")
    private int teamMembershipId;
    @SerializedName("notificationType")
    private int notificationType;
    @SerializedName("currentTeam")
    private CurrentTeam currentTeam;
    @SerializedName("defaultLocation")
    private  DefaultLocation defaultLocation;
    @SerializedName("defaultCarParkLocation")
    private DefaultCarParkLocation defaultCarParkLocation;
    @SerializedName("vehicleRegNumber")
    private String vehicleRegNumber;
    @SerializedName("preferredDesk")
    private PreferredDesk preferredDesk;
    @SerializedName("deskPhoneNumber")
    private String deskPhoneNumber;
    @SerializedName("phoneNumber")
    private String phoneNumber;
    @SerializedName("workHoursFrom")
    private String workHoursFrom;
    @SerializedName("workHoursTo")
    private String workHoursTo;
    @SerializedName("highestRole")
    private String highestRole;

    @SerializedName("permissions")
    List<String> permissionsList=new ArrayList<>();


    @SerializedName("tenantId")
    private int tenantId;
    @SerializedName("gdprAccepted")
    private boolean gdprAccepted;
    @SerializedName("gdprTimestamp")
    private String gdprTimestamp;
    @SerializedName("gdpripAddress")
    private String gdpripAddress;
    @SerializedName("bookAhead")
    private int bookAhead;
    @SerializedName("carparkBookAhead")
    private int carparkBookAhead;
    @SerializedName("countryId")
    private int countryId;
    @SerializedName("hasPinSetup")
    private boolean hasPinSetup;
    @SerializedName("calendarIntegrationType")
    private int calendarIntegrationType;
    private  boolean rememberMe;
    private String workHoursFromString;
    private String workHoursToString;

    public List<String> getPermissionsList() {
        return permissionsList;
    }

    public void setPermissionsList(List<String> permissionsList) {
        this.permissionsList = permissionsList;
    }

    public boolean isRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(boolean rememberMe) {
        this.rememberMe = rememberMe;
    }

    public String getWorkHoursFromString() {
        return workHoursFromString;
    }

    public void setWorkHoursFromString(String workHoursFromString) {
        this.workHoursFromString = workHoursFromString;
    }

    public String getWorkHoursToString() {
        return workHoursToString;
    }

    public void setWorkHoursToString(String workHoursToString) {
        this.workHoursToString = workHoursToString;
    }

    void setPermission(){
        permissionsList.add("users.manage");
        permissionsList.add("users.edit.role");
        permissionsList.add("users.delete");
        permissionsList.add("teams.manage");
        permissionsList.add("teams.addremove");
        permissionsList.add("teams.edit.managers");
        permissionsList.add("teams.manage.all");
        permissionsList.add("calendar.view");
        permissionsList.add("calendar.edit");
        permissionsList.add("calendar.edit.all.teams");
        permissionsList.add("memberships.manage");
        permissionsList.add("desks.manage");
        permissionsList.add("locations.manage");
        permissionsList.add("locations.addremove");
        permissionsList.add("locations.manage.all");
        permissionsList.add("meetingrooms.manage.all");
        permissionsList.add("meetingrooms.manage");
        permissionsList.add("meetingrooms.view");
        permissionsList.add("companysettings.manage.all");
        permissionsList.add("orderform.manage");
        permissionsList.add("parkingslot.manage");
        permissionsList.add("calendar.view");
        permissionsList.add("calendar.edit");
        permissionsList.add("meetingrooms.view");


    }

    public int getTeamMembershipId() {
        return teamMembershipId;
    }

    public void setTeamMembershipId(int teamMembershipId) {
        this.teamMembershipId = teamMembershipId;
    }

    public int getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(int notificationType) {
        this.notificationType = notificationType;
    }

    public CurrentTeam getCurrentTeam() {
        return currentTeam;
    }

    public void setCurrentTeam(CurrentTeam currentTeam) {
        this.currentTeam = currentTeam;
    }

    public DefaultLocation getDefaultLocation() {
        return defaultLocation;
    }

    public void setDefaultLocation(DefaultLocation defaultLocation) {
        this.defaultLocation = defaultLocation;
    }

    public DefaultCarParkLocation getDefaultCarParkLocation() {
        return defaultCarParkLocation;
    }

    public void setDefaultCarParkLocation(DefaultCarParkLocation defaultCarParkLocation) {
        this.defaultCarParkLocation = defaultCarParkLocation;
    }

    public String getVehicleRegNumber() {
        return vehicleRegNumber;
    }

    public void setVehicleRegNumber(String vehicleRegNumber) {
        this.vehicleRegNumber = vehicleRegNumber;
    }

    public PreferredDesk getPreferredDesk() {
        return preferredDesk;
    }

    public void setPreferredDesk(PreferredDesk preferredDesk) {
        this.preferredDesk = preferredDesk;
    }

    public String getDeskPhoneNumber() {
        return deskPhoneNumber;
    }

    public void setDeskPhoneNumber(String deskPhoneNumber) {
        this.deskPhoneNumber = deskPhoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getWorkHoursFrom() {
        return workHoursFrom;
    }

    public void setWorkHoursFrom(String workHoursFrom) {
        this.workHoursFrom = workHoursFrom;
    }

    public String getWorkHoursTo() {
        return workHoursTo;
    }

    public void setWorkHoursTo(String workHoursTo) {
        this.workHoursTo = workHoursTo;
    }

    public String getHighestRole() {
        return highestRole;
    }

    public void setHighestRole(String highestRole) {
        this.highestRole = highestRole;
    }

    public int getTenantId() {
        return tenantId;
    }

    public void setTenantId(int tenantId) {
        this.tenantId = tenantId;
    }

    public boolean isGdprAccepted() {
        return gdprAccepted;
    }

    public void setGdprAccepted(boolean gdprAccepted) {
        this.gdprAccepted = gdprAccepted;
    }

    public String getGdprTimestamp() {
        return gdprTimestamp;
    }

    public void setGdprTimestamp(String gdprTimestamp) {
        this.gdprTimestamp = gdprTimestamp;
    }

    public String getGdpripAddress() {
        return gdpripAddress;
    }

    public void setGdpripAddress(String gdpripAddress) {
        this.gdpripAddress = gdpripAddress;
    }

    public int getBookAhead() {
        return bookAhead;
    }

    public void setBookAhead(int bookAhead) {
        this.bookAhead = bookAhead;
    }

    public int getCarparkBookAhead() {
        return carparkBookAhead;
    }

    public void setCarparkBookAhead(int carparkBookAhead) {
        this.carparkBookAhead = carparkBookAhead;
    }

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public int getCalendarIntegrationType() {
        return calendarIntegrationType;
    }

    public void setCalendarIntegrationType(int calendarIntegrationType) {
        this.calendarIntegrationType = calendarIntegrationType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isActiveStatus() {
        return activeStatus;
    }

    public void setActiveStatus(boolean activeStatus) {
        this.activeStatus = activeStatus;
    }

    public boolean isHasPinSetup() {
        return hasPinSetup;
    }

    public void setHasPinSetup(boolean hasPinSetup) {
        this.hasPinSetup = hasPinSetup;
    }

    public class CurrentTeam{

        @SerializedName("id")
        private int currentTeamId;
        @SerializedName("name")
        private String currentTeamName;
        @SerializedName("parentTeamId")
        private int currentParentTeamId;
        @SerializedName("isLeafTeam")
        private boolean currentIsLeafTeam;
        @SerializedName("managerUserIds")
        private String currentManagerUserIds;
        @SerializedName("automaticApprovalStatus")
        private String currentAutomaticApprovalStatus;
        @SerializedName("isDeleted")
        boolean isDeleted;

        public int getCurrentTeamId() {
            return currentTeamId;
        }

        public void setCurrentTeamId(int currentTeamId) {
            this.currentTeamId = currentTeamId;
        }

        public String getCurrentTeamName() {
            return currentTeamName;
        }

        public void setCurrentTeamName(String currentTeamName) {
            this.currentTeamName = currentTeamName;
        }

        public int getCurrentParentTeamId() {
            return currentParentTeamId;
        }

        public void setCurrentParentTeamId(int currentParentTeamId) {
            this.currentParentTeamId = currentParentTeamId;
        }

        public boolean isCurrentIsLeafTeam() {
            return currentIsLeafTeam;
        }

        public void setCurrentIsLeafTeam(boolean currentIsLeafTeam) {
            this.currentIsLeafTeam = currentIsLeafTeam;
        }

        public String getCurrentManagerUserIds() {
            return currentManagerUserIds;
        }

        public void setCurrentManagerUserIds(String currentManagerUserIds) {
            this.currentManagerUserIds = currentManagerUserIds;
        }

        public String getCurrentAutomaticApprovalStatus() {
            return currentAutomaticApprovalStatus;
        }

        public void setCurrentAutomaticApprovalStatus(String currentAutomaticApprovalStatus) {
            this.currentAutomaticApprovalStatus = currentAutomaticApprovalStatus;
        }
    }

    public class DefaultLocation{

        @SerializedName("id")
        private int id;
        @SerializedName("name")
        private String name;
        @SerializedName("parentLocationId")
        private int parentLocationId;
        @SerializedName("isLeafLocation")
        private boolean  isLeafLocation;
        @SerializedName("locationType")
        private int locationType;
        @SerializedName("timeZoneId")
        private String timeZoneId;
        @SerializedName("description")
        private String description;
        @SerializedName("isActive")
        private boolean isActive;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getParentLocationId() {
            return parentLocationId;
        }

        public void setParentLocationId(int parentLocationId) {
            this.parentLocationId = parentLocationId;
        }

        public boolean isLeafLocation() {
            return isLeafLocation;
        }

        public void setLeafLocation(boolean leafLocation) {
            isLeafLocation = leafLocation;
        }

        public int getLocationType() {
            return locationType;
        }

        public void setLocationType(int locationType) {
            this.locationType = locationType;
        }

        public String getTimeZoneId() {
            return timeZoneId;
        }

        public void setTimeZoneId(String timeZoneId) {
            this.timeZoneId = timeZoneId;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public boolean isActive() {
            return isActive;
        }

        public void setActive(boolean active) {
            isActive = active;
        }
    }

    public class DefaultCarParkLocation{

        @SerializedName("id")
        private int id;
        @SerializedName("name")
        private String name;
        @SerializedName("parentLocationId")
        private int parentLocationId;
        @SerializedName("isLeafLocation")
        private boolean  isLeafLocation;
        @SerializedName("locationType")
        private int locationType;
        @SerializedName("timeZoneId")
        private String timeZoneId;
        @SerializedName("description")
        private String description;
        @SerializedName("isActive")
        private boolean isActive;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getParentLocationId() {
            return parentLocationId;
        }

        public void setParentLocationId(int parentLocationId) {
            this.parentLocationId = parentLocationId;
        }

        public boolean isLeafLocation() {
            return isLeafLocation;
        }

        public void setLeafLocation(boolean leafLocation) {
            isLeafLocation = leafLocation;
        }

        public int getLocationType() {
            return locationType;
        }

        public void setLocationType(int locationType) {
            this.locationType = locationType;
        }

        public String getTimeZoneId() {
            return timeZoneId;
        }

        public void setTimeZoneId(String timeZoneId) {
            this.timeZoneId = timeZoneId;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public boolean isActive() {
            return isActive;
        }

        public void setActive(boolean active) {
            isActive = active;
        }
    }

    public class PreferredDesk{

        @SerializedName("id")
        private int id;
        @SerializedName("code")
        private String code;
        @SerializedName("description")
        private String description;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }



}
