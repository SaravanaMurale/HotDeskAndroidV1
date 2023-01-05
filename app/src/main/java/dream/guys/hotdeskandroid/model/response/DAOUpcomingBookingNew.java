package dream.guys.hotdeskandroid.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DAOUpcomingBookingNew {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("usageTypeId")
    @Expose
    private Integer usageTypeId;
    @SerializedName("teamDeskId")
    @Expose
    private Integer teamDeskId;
    @SerializedName("timeZoneId")
    @Expose
    private String timeZoneId;
    @SerializedName("from")
    @Expose
    private String from;
    @SerializedName("to")
    @Expose
    private String to;
    @SerializedName("fromUtc")
    @Expose
    private String fromUtc;
    @SerializedName("toUtc")
    @Expose
    private String toUtc;
    @SerializedName("comments")
    @Expose
    private String comments;
    @SerializedName("isPendingRequest")
    @Expose
    private Boolean isPendingRequest;
    @SerializedName("requestedTeamId")
    @Expose
    private Integer requestedTeamId;
    @SerializedName("requestedTeamDeskId")
    @Expose
    private Integer requestedTeamDeskId;
    @SerializedName("requestedDeskCode")
    @Expose
    private String requestedDeskCode;
    @SerializedName("requestedDeskId")
    @Expose
    private Integer requestedDeskId;
    @SerializedName("requestedDeskLocation")
    @Expose
    private RequestedDeskLocation requestedDeskLocation;
    @SerializedName("deskId")
    @Expose
    private Integer deskId;
    @SerializedName("deskCode")
    @Expose
    private String deskCode;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("deskLocation")
    @Expose
    private DeskLocation deskLocation;
    @SerializedName("deskTeamName")
    @Expose
    private String deskTeamName;
    @SerializedName("teamMembershipId")
    @Expose
    private Integer teamMembershipId;
    @SerializedName("bookedByUserName")
    @Expose
    private String bookedByUserName;
    @SerializedName("status")
    @Expose
    private Status status;
    @SerializedName("deskBeaconMacAddress")
    @Expose
    private String deskBeaconMacAddress;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getUsageTypeId() {
        return usageTypeId;
    }

    public void setUsageTypeId(Integer usageTypeId) {
        this.usageTypeId = usageTypeId;
    }

    public Integer getTeamDeskId() {
        return teamDeskId;
    }

    public void setTeamDeskId(Integer teamDeskId) {
        this.teamDeskId = teamDeskId;
    }

    public String getTimeZoneId() {
        return timeZoneId;
    }

    public void setTimeZoneId(String timeZoneId) {
        this.timeZoneId = timeZoneId;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFromUtc() {
        return fromUtc;
    }

    public void setFromUtc(String fromUtc) {
        this.fromUtc = fromUtc;
    }

    public String getToUtc() {
        return toUtc;
    }

    public void setToUtc(String toUtc) {
        this.toUtc = toUtc;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Boolean getPendingRequest() {
        return isPendingRequest;
    }

    public void setPendingRequest(Boolean pendingRequest) {
        isPendingRequest = pendingRequest;
    }

    public Integer getRequestedTeamId() {
        return requestedTeamId;
    }

    public void setRequestedTeamId(Integer requestedTeamId) {
        this.requestedTeamId = requestedTeamId;
    }

    public Integer getRequestedTeamDeskId() {
        return requestedTeamDeskId;
    }

    public void setRequestedTeamDeskId(Integer requestedTeamDeskId) {
        this.requestedTeamDeskId = requestedTeamDeskId;
    }

    public String getRequestedDeskCode() {
        return requestedDeskCode;
    }

    public void setRequestedDeskCode(String requestedDeskCode) {
        this.requestedDeskCode = requestedDeskCode;
    }

    public Integer getRequestedDeskId() {
        return requestedDeskId;
    }

    public void setRequestedDeskId(Integer requestedDeskId) {
        this.requestedDeskId = requestedDeskId;
    }

    public RequestedDeskLocation getRequestedDeskLocation() {
        return requestedDeskLocation;
    }

    public void setRequestedDeskLocation(RequestedDeskLocation requestedDeskLocation) {
        this.requestedDeskLocation = requestedDeskLocation;
    }

    public Integer getDeskId() {
        return deskId;
    }

    public void setDeskId(Integer deskId) {
        this.deskId = deskId;
    }

    public String getDeskCode() {
        return deskCode;
    }

    public void setDeskCode(String deskCode) {
        this.deskCode = deskCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public DeskLocation getDeskLocation() {
        return deskLocation;
    }

    public void setDeskLocation(DeskLocation deskLocation) {
        this.deskLocation = deskLocation;
    }

    public String getDeskTeamName() {
        return deskTeamName;
    }

    public void setDeskTeamName(String deskTeamName) {
        this.deskTeamName = deskTeamName;
    }

    public Integer getTeamMembershipId() {
        return teamMembershipId;
    }

    public void setTeamMembershipId(Integer teamMembershipId) {
        this.teamMembershipId = teamMembershipId;
    }

    public String getBookedByUserName() {
        return bookedByUserName;
    }

    public void setBookedByUserName(String bookedByUserName) {
        this.bookedByUserName = bookedByUserName;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getDeskBeaconMacAddress() {
        return deskBeaconMacAddress;
    }

    public void setDeskBeaconMacAddress(String deskBeaconMacAddress) {
        this.deskBeaconMacAddress = deskBeaconMacAddress;
    }

    public class DeskLocation {

        @SerializedName("floorID")
        @Expose
        private Integer floorID;
        @SerializedName("fLoorName")
        @Expose
        private String fLoorName;
        @SerializedName("buildingID")
        @Expose
        private Integer buildingID;
        @SerializedName("buildingName")
        @Expose
        private String buildingName;

        public Integer getFloorID() {
            return floorID;
        }

        public void setFloorID(Integer floorID) {
            this.floorID = floorID;
        }

        public String getfLoorName() {
            return fLoorName;
        }

        public void setfLoorName(String fLoorName) {
            this.fLoorName = fLoorName;
        }

        public Integer getBuildingID() {
            return buildingID;
        }

        public void setBuildingID(Integer buildingID) {
            this.buildingID = buildingID;
        }

        public String getBuildingName() {
            return buildingName;
        }

        public void setBuildingName(String buildingName) {
            this.buildingName = buildingName;
        }

    }

    public class RequestedDeskLocation {

        @SerializedName("floorID")
        @Expose
        private Integer floorID;
        @SerializedName("fLoorName")
        @Expose
        private String fLoorName;
        @SerializedName("buildingID")
        @Expose
        private Integer buildingID;
        @SerializedName("buildingName")
        @Expose
        private String buildingName;

        public Integer getFloorID() {
            return floorID;
        }

        public void setFloorID(Integer floorID) {
            this.floorID = floorID;
        }

        public String getfLoorName() {
            return fLoorName;
        }

        public void setfLoorName(String fLoorName) {
            this.fLoorName = fLoorName;
        }

        public Integer getBuildingID() {
            return buildingID;
        }

        public void setBuildingID(Integer buildingID) {
            this.buildingID = buildingID;
        }

        public String getBuildingName() {
            return buildingName;
        }

        public void setBuildingName(String buildingName) {
            this.buildingName = buildingName;
        }
    }

    public class Status {

        @SerializedName("timeStatus")
        @Expose
        private String timeStatus;
        @SerializedName("bookingStatus")
        @Expose
        private String bookingStatus;
        @SerializedName("bookingType")
        @Expose
        private String bookingType;
        @SerializedName("isToday")
        @Expose
        private Boolean isToday;

        public String getTimeStatus() {
            return timeStatus;
        }

        public void setTimeStatus(String timeStatus) {
            this.timeStatus = timeStatus;
        }

        public String getBookingStatus() {
            return bookingStatus;
        }

        public void setBookingStatus(String bookingStatus) {
            this.bookingStatus = bookingStatus;
        }

        public String getBookingType() {
            return bookingType;
        }

        public void setBookingType(String bookingType) {
            this.bookingType = bookingType;
        }

        public Boolean getIsToday() {
            return isToday;
        }

        public void setIsToday(Boolean isToday) {
            this.isToday = isToday;
        }

    }
}