package dream.guys.hotdeskandroid.model;

import com.google.gson.annotations.SerializedName;

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

    //@SerializedName("permissions")

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
