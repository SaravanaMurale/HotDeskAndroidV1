package dream.guys.hotdeskandroid.model.request;

import java.util.Date;

public class EditBookingDetails {

    String editStartTTime;
    String editEndTime;
    String deskCode;
    String vehicleRegNumber;
    String parkingSlotCode;
    int calId=0;
    int parkingSlotId=0;
    int meetingRoomtId=0;
    int deskStatus=0;
    Date date;
    String abbrevation;

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
