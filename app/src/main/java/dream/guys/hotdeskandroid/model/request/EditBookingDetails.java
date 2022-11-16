package dream.guys.hotdeskandroid.model.request;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

import dream.guys.hotdeskandroid.model.response.BookingForEditResponse;
import dream.guys.hotdeskandroid.model.response.MeetingListToEditResponse;

public class EditBookingDetails {

    String editStartTTime;
    String editEndTime;
    String deskCode;
    String roomName;
    String vehicleRegNumber;
    String locationAddress;
    String bookedForUser;
    String parkingSlotCode;
    String timeZone;
    public String timeStatus;

    int calId=0;
    int desktId=0;
    int deskTeamId=0;
    int parkingSlotId=0;
    int meetingRoomtId=0;
    int deskStatus=0;
    int noOfPeople=0;
    int usageTypeId=0;
    Date date;
    String abbrevation;
    List<String> amenities;
    boolean isTeamsChecked;
    public int requestedTeamId;
    public int requestedTeamDeskId;
    public Status status;

    public int getUsageTypeId() {
        return usageTypeId;
    }

    public void setUsageTypeId(int usageTypeId) {
        this.usageTypeId = usageTypeId;
    }

    public String getTimeStatus() {
        return timeStatus;
    }

    public void setTimeStatus(String timeStatus) {
        this.timeStatus = timeStatus;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public int getRequestedTeamId() {
        return requestedTeamId;
    }

    public void setRequestedTeamId(int requestedTeamId) {
        this.requestedTeamId = requestedTeamId;
    }

    public int getRequestedTeamDeskId() {
        return requestedTeamDeskId;
    }

    public void setRequestedTeamDeskId(int requestedTeamDeskId) {
        this.requestedTeamDeskId = requestedTeamDeskId;
    }

    public int getNoOfPeople() {
        return noOfPeople;
    }

    public void setNoOfPeople(int noOfPeople) {
        this.noOfPeople = noOfPeople;
    }

    public boolean isTeamsChecked() {
        return isTeamsChecked;
    }

    public void setTeamsChecked(boolean teamsChecked) {
        isTeamsChecked = teamsChecked;
    }

    public String getLocationAddress() {
        return locationAddress;
    }

    public void setLocationAddress(String locationAddress) {
        this.locationAddress = locationAddress;
    }

    public int getDeskTeamId() {
        return deskTeamId;
    }

    public void setDeskTeamId(int deskTeamId) {
        this.deskTeamId = deskTeamId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public List<String> getAmenities() {
        return amenities;
    }

    public int getDesktId() {
        return desktId;
    }

    public void setDesktId(int desktId) {
        this.desktId = desktId;
    }

    public void setAmenities(List<String> amenities) {
        this.amenities = amenities;
    }

    public int getMeetingRoomtId() {
        return meetingRoomtId;
    }

    public void setMeetingRoomtId(int meetingRoomtId) {
        this.meetingRoomtId = meetingRoomtId;
    }

    public int getParkingSlotId() {
        return parkingSlotId;
    }

    public void setParkingSlotId(int parkingSlotId) {
        this.parkingSlotId = parkingSlotId;
    }

    public String getVehicleRegNumber() {
        return vehicleRegNumber;
    }

    public void setVehicleRegNumber(String vehicleRegNumber) {
        this.vehicleRegNumber = vehicleRegNumber;
    }

    public String getParkingSlotCode() {
        return parkingSlotCode;
    }

    public void setParkingSlotCode(String parkingSlotCode) {
        this.parkingSlotCode = parkingSlotCode;
    }

    public EditBookingDetails() {
    }

    public EditBookingDetails(String editStartTTime, String editEndTime, Date date) {
        this.editStartTTime = editStartTTime;
        this.editEndTime = editEndTime;
        this.date = date;
    }

    public int getCalId() {
        return calId;
    }

    public void setCalId(int calId) {
        this.calId = calId;
    }

    public String getDeskCode() {
        return deskCode;
    }

    public void setDeskCode(String deskCode) {
        this.deskCode = deskCode;
    }

    public int getDeskStatus() {
        return deskStatus;
    }

    public void setDeskStatus(int deskStatus) {
        this.deskStatus = deskStatus;
    }

    public String getEditStartTTime() {
        return editStartTTime;
    }

    public void setEditStartTTime(String editStartTTime) {
        this.editStartTTime = editStartTTime;
    }

    public String getEditEndTime() {
        return editEndTime;
    }

    public void setEditEndTime(String editEndTime) {
        this.editEndTime = editEndTime;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public class Status {
        public String timeStatus;
        public String bookingStatus;
        public String bookingType;
        public boolean isToday;

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

        public boolean isToday() {
            return isToday;
        }

        public void setToday(boolean today) {
            isToday = today;
        }
    }

    //New...
    @SerializedName("attendees")
    List<MeetingListToEditResponse.Attendees> attendeesList;
    @SerializedName("externalAttendees")
    List<String> externalAttendeesList;
    @SerializedName("subject")
    String subject;
    @SerializedName("comments")
    String comments;

    public List<MeetingListToEditResponse.Attendees> getAttendeesList() {
        return attendeesList;
    }

    public void setAttendeesList(List<MeetingListToEditResponse.Attendees> attendeesList) {
        this.attendeesList = attendeesList;
    }

    public List<String> getExternalAttendeesList() {
        return externalAttendeesList;
    }

    public void setExternalAttendeesList(List<String> externalAttendeesList) {
        this.externalAttendeesList = externalAttendeesList;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
