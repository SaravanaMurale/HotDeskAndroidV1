package dream.guys.hotdeskandroid.model.request;

import java.util.Date;
import java.util.List;

public class EditBookingDetails {

    String editStartTTime;
    String editEndTime;
    String deskCode;
    String roomName;
    String vehicleRegNumber;
    String bookedForUser;
    String parkingSlotCode;
    int calId=0;
    int desktId=0;
    int deskTeamId=0;
    int parkingSlotId=0;
    int meetingRoomtId=0;
    int deskStatus=0;
    Date date;
    String abbrevation;
    List<String> amenities;

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
}
