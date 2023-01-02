package dream.guys.hotdeskandroid.model;

public class HorizontalCalendarModel {
    private String date;
    private String day;
    private String month;
    private String shortMonth;
    private String year;
    private String dayDate;
    private boolean isSelected;
    private boolean isToday;

    public String getShortMonth() {
        return shortMonth;
    }

    public void setShortMonth(String shortMonth) {
        this.shortMonth = shortMonth;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public boolean isToday() {
        return isToday;
    }

    public void setToday(boolean today) {
        isToday = today;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getDayDate() {
        return dayDate;
    }

    public void setDayDate(String dayDate) {
        this.dayDate = dayDate;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
