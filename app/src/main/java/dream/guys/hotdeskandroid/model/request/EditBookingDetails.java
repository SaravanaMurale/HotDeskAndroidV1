package dream.guys.hotdeskandroid.model.request;

public class EditBookingDetails {

    String editStartTTime;
    String editEndTime;
    String date;
    String abbrevation;

    public EditBookingDetails() {
    }

    public EditBookingDetails(String editStartTTime, String editEndTime, String date) {
        this.editStartTTime = editStartTTime;
        this.editEndTime = editEndTime;
        this.date = date;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
