package dream.guys.hotdeskandroid.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class IncomingRequestResponse implements Serializable {
    /*@SerializedName("results")
    ArrayList<Result> results = new ArrayList<Result>();

    public ArrayList<Result> getResults() {
        return results;
    }

    public void setResults(ArrayList<Result> results) {
        this.results = results;
    }

    public class Result {
        private int status;

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }*/

    @SerializedName("results")
    @Expose
    private ArrayList<Result> results = null;
    @SerializedName("totalRecords")
    @Expose
    private Integer totalRecords;


    public ArrayList<Result> getResults() {
        return results;
    }

    public void setResults(ArrayList<Result> results) {
        this.results = results;
    }

    public Integer getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(Integer totalRecords) {
        this.totalRecords = totalRecords;
    }

    public static class Result implements Serializable{

        @SerializedName("calendarEntryId")
        @Expose
        private Integer calendarEntryId;
        @SerializedName("teamId")
        @Expose
        private Integer teamId;
        @SerializedName("requestedTeamDeskId")
        @Expose
        private Integer requestedTeamDeskId;
        @SerializedName("actionedByUserId")
        @Expose
        private Object actionedByUserId;
        @SerializedName("rejectionReason")
        @Expose
        private Object rejectionReason;
        @SerializedName("deskId")
        @Expose
        private Integer deskId;
        @SerializedName("deskCode")
        @Expose
        private String deskCode;
        @SerializedName("deskTeam")
        @Expose
        private String deskTeam;
        @SerializedName("deskDescription")
        @Expose
        private String deskDescription;
        @SerializedName("deskLocation")
        @Expose
        private String deskLocation;
        @SerializedName("requesterUserId")
        @Expose
        private Integer requesterUserId;
        @SerializedName("requesterTeamMembershipId")
        @Expose
        private Integer requesterTeamMembershipId;
        @SerializedName("requesterName")
        @Expose
        private String requesterName;
        @SerializedName("requesterTeam")
        @Expose
        private String requesterTeam;
        @SerializedName("requesterComment")
        @Expose
        private Object requesterComment;
        @SerializedName("approvalManagerName")
        @Expose
        private String approvalManagerName;
        @SerializedName("availableDeskCount")
        @Expose
        private Integer availableDeskCount;
        @SerializedName("availableTeamDeskIds")
        @Expose
        private ArrayList<Object> availableTeamDeskIds = null;
        @SerializedName("teamDeskAvailability")
        @Expose
        private ArrayList<TeamDeskAvailability> teamDeskAvailability = null;
        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("date")
        @Expose
        private String date;
        @SerializedName("from")
        @Expose
        private String from;
        @SerializedName("fromUtc")
        @Expose
        private String fromUtc;
        @SerializedName("to")
        @Expose
        private String to;
        @SerializedName("toUtc")
        @Expose
        private String toUtc;
        @SerializedName("timeZoneId")
        @Expose
        private String timeZoneId;
        @SerializedName("entityType")
        @Expose
        private Integer entityType;
        @SerializedName("status")
        @Expose
        private Integer status;
        @SerializedName("requestedDate")
        @Expose
        private String requestedDate;


        //New...
        //Car...5
        @SerializedName("carParkBookingId")
        @Expose
        private Integer carParkBookingId;
        @SerializedName("parkingSlotId")
        @Expose
        private Integer parkingSlotId;
        @SerializedName("parkingSlotCode")
        @Expose
        private String parkingSlotCode;
        @SerializedName("locationName")
        @Expose
        private String locationName;

        //Meeting... 4
        @SerializedName("meetingRoomBookingId")
        @Expose
        private Integer meetingRoomBookingId;
        @SerializedName("meetingRoomId")
        @Expose
        private Integer meetingRoomId;
        @SerializedName("meetingRoomName")
        @Expose
        private String meetingRoomName;


        private String incoming = "incoming";
        public String getIncoming() {
            return incoming;
        }
        public void setIncoming(String incoming) {
            this.incoming = incoming;
        }

        private boolean isTitle;
        public boolean isTitle() {
            return isTitle;
        }
        public void setTitle(boolean title) {
            isTitle = title;
        }

        private boolean checkBoxStatus;
        public boolean isCheckBoxStatus() {
            return checkBoxStatus;
        }
        public void setCheckBoxStatus(boolean checkBoxStatus) {
            this.checkBoxStatus = checkBoxStatus;
        }

        public Result(Integer status) {
            this.status = status;
        }

        @Override
        public boolean equals(Object o) {
            return status.equals(((Result)o).status);
        }
        @Override
        public int hashCode() {
            int hash = 13;
            hash = (31 * hash) + (null == status ? 0 : status.hashCode());
            return hash;
        }

        public Integer getCalendarEntryId() {
            return calendarEntryId;
        }

        public void setCalendarEntryId(Integer calendarEntryId) {
            this.calendarEntryId = calendarEntryId;
        }

        public Integer getTeamId() {
            return teamId;
        }

        public void setTeamId(Integer teamId) {
            this.teamId = teamId;
        }

        public Integer getRequestedTeamDeskId() {
            return requestedTeamDeskId;
        }

        public void setRequestedTeamDeskId(Integer requestedTeamDeskId) {
            this.requestedTeamDeskId = requestedTeamDeskId;
        }

        public Object getActionedByUserId() {
            return actionedByUserId;
        }

        public void setActionedByUserId(Object actionedByUserId) {
            this.actionedByUserId = actionedByUserId;
        }

        public Object getRejectionReason() {
            return rejectionReason;
        }

        public void setRejectionReason(Object rejectionReason) {
            this.rejectionReason = rejectionReason;
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

        public String getDeskTeam() {
            return deskTeam;
        }

        public void setDeskTeam(String deskTeam) {
            this.deskTeam = deskTeam;
        }

        public String getDeskDescription() {
            return deskDescription;
        }

        public void setDeskDescription(String deskDescription) {
            this.deskDescription = deskDescription;
        }

        public String getDeskLocation() {
            return deskLocation;
        }

        public void setDeskLocation(String deskLocation) {
            this.deskLocation = deskLocation;
        }

        public Integer getRequesterUserId() {
            return requesterUserId;
        }

        public void setRequesterUserId(Integer requesterUserId) {
            this.requesterUserId = requesterUserId;
        }

        public Integer getRequesterTeamMembershipId() {
            return requesterTeamMembershipId;
        }

        public void setRequesterTeamMembershipId(Integer requesterTeamMembershipId) {
            this.requesterTeamMembershipId = requesterTeamMembershipId;
        }

        public String getRequesterName() {
            return requesterName;
        }

        public void setRequesterName(String requesterName) {
            this.requesterName = requesterName;
        }

        public String getRequesterTeam() {
            return requesterTeam;
        }

        public void setRequesterTeam(String requesterTeam) {
            this.requesterTeam = requesterTeam;
        }

        public Object getRequesterComment() {
            return requesterComment;
        }

        public void setRequesterComment(Object requesterComment) {
            this.requesterComment = requesterComment;
        }

        public String getApprovalManagerName() {
            return approvalManagerName;
        }

        public void setApprovalManagerName(String approvalManagerName) {
            this.approvalManagerName = approvalManagerName;
        }

        public Integer getAvailableDeskCount() {
            return availableDeskCount;
        }

        public void setAvailableDeskCount(Integer availableDeskCount) {
            this.availableDeskCount = availableDeskCount;
        }

        public ArrayList<Object> getAvailableTeamDeskIds() {
            return availableTeamDeskIds;
        }

        public void setAvailableTeamDeskIds(ArrayList<Object> availableTeamDeskIds) {
            this.availableTeamDeskIds = availableTeamDeskIds;
        }

        public ArrayList<TeamDeskAvailability> getTeamDeskAvailability() {
            return teamDeskAvailability;
        }

        public void setTeamDeskAvailability(ArrayList<TeamDeskAvailability> teamDeskAvailability) {
            this.teamDeskAvailability = teamDeskAvailability;
        }

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

        public String getFrom() {
            return from;
        }

        public void setFrom(String from) {
            this.from = from;
        }

        public String getFromUtc() {
            return fromUtc;
        }

        public void setFromUtc(String fromUtc) {
            this.fromUtc = fromUtc;
        }

        public String getTo() {
            return to;
        }

        public void setTo(String to) {
            this.to = to;
        }

        public String getToUtc() {
            return toUtc;
        }

        public void setToUtc(String toUtc) {
            this.toUtc = toUtc;
        }

        public String getTimeZoneId() {
            return timeZoneId;
        }

        public void setTimeZoneId(String timeZoneId) {
            this.timeZoneId = timeZoneId;
        }

        public Integer getEntityType() {
            return entityType;
        }

        public void setEntityType(Integer entityType) {
            this.entityType = entityType;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public String getRequestedDate() {
            return requestedDate;
        }

        public void setRequestedDate(String requestedDate) {
            this.requestedDate = requestedDate;
        }

        public Integer getCarParkBookingId() {
            return carParkBookingId;
        }

        public void setCarParkBookingId(Integer carParkBookingId) {
            this.carParkBookingId = carParkBookingId;
        }

        public Integer getParkingSlotId() {
            return parkingSlotId;
        }

        public void setParkingSlotId(Integer parkingSlotId) {
            this.parkingSlotId = parkingSlotId;
        }

        public String getParkingSlotCode() {
            return parkingSlotCode;
        }

        public void setParkingSlotCode(String parkingSlotCode) {
            this.parkingSlotCode = parkingSlotCode;
        }

        public String getLocationName() {
            return locationName;
        }

        public void setLocationName(String locationName) {
            this.locationName = locationName;
        }

        public Integer getMeetingRoomBookingId() {
            return meetingRoomBookingId;
        }

        public void setMeetingRoomBookingId(Integer meetingRoomBookingId) {
            this.meetingRoomBookingId = meetingRoomBookingId;
        }

        public Integer getMeetingRoomId() {
            return meetingRoomId;
        }

        public void setMeetingRoomId(Integer meetingRoomId) {
            this.meetingRoomId = meetingRoomId;
        }

        public String getMeetingRoomName() {
            return meetingRoomName;
        }

        public void setMeetingRoomName(String meetingRoomName) {
            this.meetingRoomName = meetingRoomName;
        }
    }

    public class TimeZone implements Serializable {

        @SerializedName("startDate")
        @Expose
        private String startDate;
        @SerializedName("endDate")
        @Expose
        private Object endDate;
        @SerializedName("timeZoneId")
        @Expose
        private String timeZoneId;

        public String getStartDate() {
            return startDate;
        }

        public void setStartDate(String startDate) {
            this.startDate = startDate;
        }

        public Object getEndDate() {
            return endDate;
        }

        public void setEndDate(Object endDate) {
            this.endDate = endDate;
        }

        public String getTimeZoneId() {
            return timeZoneId;
        }

        public void setTimeZoneId(String timeZoneId) {
            this.timeZoneId = timeZoneId;
        }

    }

    public class TeamDeskAvailability implements Serializable{

        @SerializedName("teamDeskId")
        @Expose
        private Integer teamDeskId;
        @SerializedName("deskId")
        @Expose
        private Integer deskId;
        @SerializedName("teamId")
        @Expose
        private Integer teamId;
        @SerializedName("teamName")
        @Expose
        private String teamName;
        @SerializedName("code")
        @Expose
        private String code;
        @SerializedName("teamAllocationStartDate")
        @Expose
        private String teamAllocationStartDate;
        @SerializedName("teamAllocationEndDate")
        @Expose
        private Object teamAllocationEndDate;
        @SerializedName("availableTimeSlots")
        @Expose
        private ArrayList<AvailableTimeSlot> availableTimeSlots = null;
        @SerializedName("timeZones")
        @Expose
        private ArrayList<TimeZone> timeZones = null;
        @SerializedName("currentTimeZoneOffset")
        @Expose
        private Integer currentTimeZoneOffset;
        @SerializedName("isBookedByUser")
        @Expose
        private Boolean isBookedByUser;
        @SerializedName("isPartiallyAvailable")
        @Expose
        private Boolean isPartiallyAvailable;
        @SerializedName("isBookedByElse")
        @Expose
        private Boolean isBookedByElse;

        public Integer getTeamDeskId() {
            return teamDeskId;
        }

        public void setTeamDeskId(Integer teamDeskId) {
            this.teamDeskId = teamDeskId;
        }

        public Integer getDeskId() {
            return deskId;
        }

        public void setDeskId(Integer deskId) {
            this.deskId = deskId;
        }

        public Integer getTeamId() {
            return teamId;
        }

        public void setTeamId(Integer teamId) {
            this.teamId = teamId;
        }

        public String getTeamName() {
            return teamName;
        }

        public void setTeamName(String teamName) {
            this.teamName = teamName;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getTeamAllocationStartDate() {
            return teamAllocationStartDate;
        }

        public void setTeamAllocationStartDate(String teamAllocationStartDate) {
            this.teamAllocationStartDate = teamAllocationStartDate;
        }

        public Object getTeamAllocationEndDate() {
            return teamAllocationEndDate;
        }

        public void setTeamAllocationEndDate(Object teamAllocationEndDate) {
            this.teamAllocationEndDate = teamAllocationEndDate;
        }

        public ArrayList<AvailableTimeSlot> getAvailableTimeSlots() {
            return availableTimeSlots;
        }

        public void setAvailableTimeSlots(ArrayList<AvailableTimeSlot> availableTimeSlots) {
            this.availableTimeSlots = availableTimeSlots;
        }

        public ArrayList<TimeZone> getTimeZones() {
            return timeZones;
        }

        public void setTimeZones(ArrayList<TimeZone> timeZones) {
            this.timeZones = timeZones;
        }

        public Integer getCurrentTimeZoneOffset() {
            return currentTimeZoneOffset;
        }

        public void setCurrentTimeZoneOffset(Integer currentTimeZoneOffset) {
            this.currentTimeZoneOffset = currentTimeZoneOffset;
        }

        public Boolean getIsBookedByUser() {
            return isBookedByUser;
        }

        public void setIsBookedByUser(Boolean isBookedByUser) {
            this.isBookedByUser = isBookedByUser;
        }

        public Boolean getIsPartiallyAvailable() {
            return isPartiallyAvailable;
        }

        public void setIsPartiallyAvailable(Boolean isPartiallyAvailable) {
            this.isPartiallyAvailable = isPartiallyAvailable;
        }

        public Boolean getIsBookedByElse() {
            return isBookedByElse;
        }

        public void setIsBookedByElse(Boolean isBookedByElse) {
            this.isBookedByElse = isBookedByElse;
        }

    }

    public class AvailableTimeSlot implements Serializable {

        @SerializedName("from")
        @Expose
        private String from;
        @SerializedName("to")
        @Expose
        private String to;

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

    }


    /*@Override
    public boolean equals(Object o)
    {
        Result s;
        if(!(o instanceof Result))
        {
            return false;
        }

        else
        {
            s=(Result)o;

            if (s.status.equals(1)) {
                return true;
            }
        }
        return false;
    }*/

}
