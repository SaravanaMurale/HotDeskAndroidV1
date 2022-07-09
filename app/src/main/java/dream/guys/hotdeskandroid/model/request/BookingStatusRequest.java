package dream.guys.hotdeskandroid.model.request;

import com.google.gson.annotations.SerializedName;

public class BookingStatusRequest {
    @SerializedName("calendarEntryId")
    private int calendarEntryId;

    @SerializedName("bookingStatus")
    private String bookingStatus;

    public int getCalendarEntryId() {
        return calendarEntryId;
    }

    public void setCalendarEntryId(int calendarEntryId) {
        this.calendarEntryId = calendarEntryId;
    }

    public String getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(String bookingStatus) {
        this.bookingStatus = bookingStatus;
    }
}
