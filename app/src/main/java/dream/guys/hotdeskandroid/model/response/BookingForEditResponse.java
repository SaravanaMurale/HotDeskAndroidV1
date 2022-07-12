package dream.guys.hotdeskandroid.model.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BookingForEditResponse {
    @SerializedName("userPreferences")
    UserPreferences userPreferences;
    @SerializedName("teamDeskAvailabilities")
    List<TeamDeskAvailabilities> teamDeskAvailabilities;
    @SerializedName("bookings")
    List<Bookings> bookings;

    public UserPreferences getUserPreferences() {
        return userPreferences;
    }

    public void setUserPreferences(UserPreferences userPreferences) {
        this.userPreferences = userPreferences;
    }

    public List<TeamDeskAvailabilities> getTeamDeskAvailabilities() {
        return teamDeskAvailabilities;
    }

    public void setTeamDeskAvailabilities(List<TeamDeskAvailabilities> teamDeskAvailabilities) {
        this.teamDeskAvailabilities = teamDeskAvailabilities;
    }

    public List<Bookings> getBookings() {
        return bookings;
    }

    public void setBookings(List<Bookings> bookings) {
        this.bookings = bookings;
    }

    public class TeamDeskAvailabilities {
        @SerializedName("teamDeskId")
        private int teamDeskId;
        @SerializedName("deskId")
        private int deskId;
        @SerializedName("teamId")
        private int teamId;
        @SerializedName("teamName")
        private String teamName;
        @SerializedName("code")
        private String deskCode;
        @SerializedName("teamAllocationStartDate")
        private String teamAllocationStartDate;
        @SerializedName("teamAllocationEndDate")
        private String teamAllocationEndDate;
        @SerializedName("currentTimeZoneOffset")
        private int currentTimeZoneOffset;
        @SerializedName("isBookedByUser")
        private boolean isBookedByUser;
        @SerializedName("isPartiallyAvailable")
        private boolean isPartiallyAvailable;
        @SerializedName("isBookedByElse")
        private boolean isBookedByElse;

        @SerializedName("availableTimeSlots")
        private List<AvailableTimeSlots> availableTimeSlots;
        @SerializedName("timeZones")
        private List<TimeZones> timeZones;

        public int getTeamDeskId() {
            return teamDeskId;
        }

        public void setTeamDeskId(int teamDeskId) {
            this.teamDeskId = teamDeskId;
        }

        public int getDeskId() {
            return deskId;
        }

        public void setDeskId(int deskId) {
            this.deskId = deskId;
        }

        public int getTeamId() {
            return teamId;
        }

        public void setTeamId(int teamId) {
            this.teamId = teamId;
        }

        public String getTeamName() {
            return teamName;
        }

        public void setTeamName(String teamName) {
            this.teamName = teamName;
        }

        public String getDeskCode() {
            return deskCode;
        }

        public void setDeskCode(String deskCode) {
            this.deskCode = deskCode;
        }

        public String getTeamAllocationStartDate() {
            return teamAllocationStartDate;
        }

        public void setTeamAllocationStartDate(String teamAllocationStartDate) {
            this.teamAllocationStartDate = teamAllocationStartDate;
        }

        public String getTeamAllocationEndDate() {
            return teamAllocationEndDate;
        }

        public void setTeamAllocationEndDate(String teamAllocationEndDate) {
            this.teamAllocationEndDate = teamAllocationEndDate;
        }

        public int getCurrentTimeZoneOffset() {
            return currentTimeZoneOffset;
        }

        public void setCurrentTimeZoneOffset(int currentTimeZoneOffset) {
            this.currentTimeZoneOffset = currentTimeZoneOffset;
        }

        public boolean isBookedByUser() {
            return isBookedByUser;
        }

        public void setBookedByUser(boolean bookedByUser) {
            isBookedByUser = bookedByUser;
        }

        public boolean isPartiallyAvailable() {
            return isPartiallyAvailable;
        }

        public void setPartiallyAvailable(boolean partiallyAvailable) {
            isPartiallyAvailable = partiallyAvailable;
        }

        public boolean isBookedByElse() {
            return isBookedByElse;
        }

        public void setBookedByElse(boolean bookedByElse) {
            isBookedByElse = bookedByElse;
        }

        public List<AvailableTimeSlots> getAvailableTimeSlots() {
            return availableTimeSlots;
        }

        public void setAvailableTimeSlots(List<AvailableTimeSlots> availableTimeSlots) {
            this.availableTimeSlots = availableTimeSlots;
        }

        public List<TimeZones> getTimeZones() {
            return timeZones;
        }

        public void setTimeZones(List<TimeZones> timeZones) {
            this.timeZones = timeZones;
        }

        public class AvailableTimeSlots {
            @SerializedName("from")
            private String from;
            @SerializedName("to")
            private String to;

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
        }

        private class TimeZones {
            @SerializedName("startDate")
            private String startDate;
            @SerializedName("endDate")
            private String endDate;
            @SerializedName("timeZoneId")
            private String timeZoneId;

            public String getStartDate() {
                return startDate;
            }

            public void setStartDate(String startDate) {
                this.startDate = startDate;
            }

            public String getEndDate() {
                return endDate;
            }

            public void setEndDate(String endDate) {
                this.endDate = endDate;
            }

            public String getTimeZoneId() {
                return timeZoneId;
            }

            public void setTimeZoneId(String timeZoneId) {
                this.timeZoneId = timeZoneId;
            }
        }
    }
    public class UserPreferences {
        @SerializedName("teamDeskId")
        private int teamDeskId;
        @SerializedName("teamId")
        private int teamId;
        @SerializedName("workHoursFrom")
        private String workHoursFrom;
        @SerializedName("workHoursTo")
        private String workHoursTo;
        @SerializedName("membershipNotAvailable")
        private boolean membershipNotAvailable;

    }

    private class Bookings {
        public int id;
        public String date;
        public int usageTypeId;
        public int teamDeskId;
        public String timeZoneId;
        public String from;
        @SerializedName("to")
        public String myto;
        public String fromUtc;
        public String toUtc;
        public String comments;
        public int requestedTeamId;
        public int requestedTeamDeskId;
        public int deskId;
        public String deskCode;
        public String deskTeamName;
        public int teamMembershipId;
        public String bookedByUserName;
        public Status status;

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

        public String getMyto() {
            return myto;
        }

        public void setMyto(String myto) {
            this.myto = myto;
        }

        public String getFromUtc() {
            return fromUtc;
        }

        public void setFromUtc(String fromUtc) {
            this.fromUtc = fromUtc;
        }

        public String getToUtc() {
            return toUtc;
        }

        public void setToUtc(String toUtc) {
            this.toUtc = toUtc;
        }

        public String getComments() {
            return comments;
        }

        public void setComments(String comments) {
            this.comments = comments;
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

        public int getDeskId() {
            return deskId;
        }

        public void setDeskId(int deskId) {
            this.deskId = deskId;
        }

        public String getDeskCode() {
            return deskCode;
        }

        public void setDeskCode(String deskCode) {
            this.deskCode = deskCode;
        }

        public String getDeskTeamName() {
            return deskTeamName;
        }

        public void setDeskTeamName(String deskTeamName) {
            this.deskTeamName = deskTeamName;
        }

        public int getTeamMembershipId() {
            return teamMembershipId;
        }

        public void setTeamMembershipId(int teamMembershipId) {
            this.teamMembershipId = teamMembershipId;
        }

        public String getBookedByUserName() {
            return bookedByUserName;
        }

        public void setBookedByUserName(String bookedByUserName) {
            this.bookedByUserName = bookedByUserName;
        }

        public Status getStatus() {
            return status;
        }

        public void setStatus(Status status) {
            this.status = status;
        }

        private class Status {
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
    }
}
