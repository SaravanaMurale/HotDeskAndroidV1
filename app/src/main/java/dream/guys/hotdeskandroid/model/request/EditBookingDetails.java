package dream.guys.hotdeskandroid.model.request;

import java.util.Date;

public class EditBookingDetails {

    String editStartTTime;
    String editEndTime;
    int deskStatus=0;
    Date date;
    String abbrevation;

    public EditBookingDetails() {
    }

    public EditBookingDetails(String editStartTTime, String editEndTime, Date date) {
        this.editStartTTime = editStartTTime;
        this.editEndTime = editEndTime;
        this.date = date;
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
