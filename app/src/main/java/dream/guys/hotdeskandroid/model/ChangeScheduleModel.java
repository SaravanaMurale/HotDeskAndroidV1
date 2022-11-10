package dream.guys.hotdeskandroid.model;

import java.util.Date;

public class ChangeScheduleModel {
    /*
    case 1: callBookingForEdit(9);
    case 2: callClearApi(date, 9);
    case 3: callFulldayBookingUpdated()
     */
    private int usageTypeId;
    private int switchCase;
    private Date date;

    public int getUsageTypeId() {
        return usageTypeId;
    }

    public void setUsageTypeId(int usageTypeId) {
        this.usageTypeId = usageTypeId;
    }

    public int getSwitchCase() {
        return switchCase;
    }

    public void setSwitchCase(int switchCase) {
        this.switchCase = switchCase;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
