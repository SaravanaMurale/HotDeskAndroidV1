package dream.guys.hotdeskandroid.model.response;

import com.google.gson.annotations.SerializedName;

public class BookingListResponse {

    private BookingDetail bookingDetail;

    public BookingDetail getBookingDetail() {
        return bookingDetail;
    }

    public void setBookingDetail(BookingDetail bookingDetail) {
        this.bookingDetail = bookingDetail;
    }

    public class BookingDetail{

        @SerializedName("id")
        private int id;
        @SerializedName("date")
        private String date;
        @SerializedName("usageTypeId")
        private int usageTypeId;
        @SerializedName("teamDeskId")
        private int teamDeskId;
        @SerializedName("timeZoneId")
        private String timeZoneId;
        @SerializedName("from")
        private String from;
        @SerializedName("to")
        private String to;
        @SerializedName("fromUtc")
        private String fromUtc;
        @SerializedName("toUtc")
        private String toUtc;
        @SerializedName("comments")
        private String comments;
        @SerializedName("requestedTeamId")
        private int requestedTeamId;
        @SerializedName("requestedTeamDeskId")
        private int requestedTeamDeskId;
        @SerializedName("deskId")
        private int deskId;
        @SerializedName("deskCode")
        private String deskCode;
        @SerializedName("deskTeamName")
        private String deskTeamName;
        @SerializedName("teamMembershipId")
        private int teamMembershipId;
        @SerializedName("bookedByUserName")
        private String bookedByUserName;
        @SerializedName("status")
        private Status status;
        @SerializedName("deskBeaconMacAddress")
        private String deskBeaconMacAddress;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public int getUsageTypeId() {
            return usageTypeId;
        }

        public void setUsageTypeId(int usageTypeId) {
            this.usageTypeId = usageTypeId;
        }

        public int getTeamDeskId() {
            return teamDeskId;
        }

        public void setTeamDeskId(int teamDeskId) {
            this.teamDeskId = teamDeskId;
        }

        public String getTimeZoneId() {
            return timeZoneId;
        }

        public void setTimeZoneId(String timeZoneId) {
            this.timeZoneId = timeZoneId;
        }

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

        public Status getStatus() {
            return status;
        }

        public void setStatus(Status status) {
            this.status = status;
        }

        public String getDeskTeamName() {
            return deskTeamName;
        }

        public void setDeskTeamName(String deskTeamName) {
            this.deskTeamName = deskTeamName;
        }

        public class Status{

            @SerializedName("bookingStatus")
            private String bookingStatus;
            @SerializedName("bookingType")
            private String bookingType;
            @SerializedName("isToday")
            private boolean isToday;

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


    }
}
