package dream.guys.hotdeskandroid.model.controllerModel;

import java.util.Date;

public class EditDeskModel {
    private Integer bookingId;
    private Integer deskStatus=0;
    private String selectedStartTTime;
    private String selectedEndTime;
    private String DeskCode;
    private String selectedComments;
    private String locationAddress;
    private String deskBookingType;
    private Integer selectedTeamDeskId;
    private Integer requestedTeamId;
    private Integer requestedTeamDeskId;
    private Integer usageTypeId=0;

    private Date date;

    private String changedStartTTime;
    private String changedEndTime;
    private String changedDeskCode;
    private String changedTeamDeskId;
    private String changedComments;


    public Integer getBookingId() {
        return bookingId;
    }

    public void setBookingId(Integer bookingId) {
        this.bookingId = bookingId;
    }

    public Integer getDeskStatus() {
        return deskStatus;
    }

    public void setDeskStatus(Integer deskStatus) {
        this.deskStatus = deskStatus;
    }

    public String getSelectedStartTTime() {
        return selectedStartTTime;
    }

    public void setSelectedStartTTime(String selectedStartTTime) {
        this.selectedStartTTime = selectedStartTTime;
    }

    public String getSelectedEndTime() {
        return selectedEndTime;
    }

    public void setSelectedEndTime(String selectedEndTime) {
        this.selectedEndTime = selectedEndTime;
    }

    public String getDeskCode() {
        return DeskCode;
    }

    public void setDeskCode(String deskCode) {
        DeskCode = deskCode;
    }

    public Integer getSelectedTeamDeskId() {
        return selectedTeamDeskId;
    }

    public void setSelectedTeamDeskId(Integer selectedTeamDeskId) {
        this.selectedTeamDeskId = selectedTeamDeskId;
    }

    public String getSelectedComments() {
        return selectedComments;
    }

    public void setSelectedComments(String selectedComments) {
        this.selectedComments = selectedComments;
    }

    public String getLocationAddress() {
        return locationAddress;
    }

    public void setLocationAddress(String locationAddress) {
        this.locationAddress = locationAddress;
    }

    public String getDeskBookingType() {
        return deskBookingType;
    }

    public void setDeskBookingType(String deskBookingType) {
        this.deskBookingType = deskBookingType;
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

    public Integer getUsageTypeId() {
        return usageTypeId;
    }

    public void setUsageTypeId(Integer usageTypeId) {
        this.usageTypeId = usageTypeId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getChangedStartTTime() {
        return changedStartTTime;
    }

    public void setChangedStartTTime(String changedStartTTime) {
        this.changedStartTTime = changedStartTTime;
    }

    public String getChangedEndTime() {
        return changedEndTime;
    }

    public void setChangedEndTime(String changedEndTime) {
        this.changedEndTime = changedEndTime;
    }

    public String getChangedDeskCode() {
        return changedDeskCode;
    }

    public void setChangedDeskCode(String changedDeskCode) {
        this.changedDeskCode = changedDeskCode;
    }

    public String getChangedTeamDeskId() {
        return changedTeamDeskId;
    }

    public void setChangedTeamDeskId(String changedTeamDeskId) {
        this.changedTeamDeskId = changedTeamDeskId;
    }

    public String getChangedComments() {
        return changedComments;
    }

    public void setChangedComments(String changedComments) {
        this.changedComments = changedComments;
    }
}
