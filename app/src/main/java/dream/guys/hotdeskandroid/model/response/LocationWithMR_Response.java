package dream.guys.hotdeskandroid.model.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LocationWithMR_Response {

    int id;
    String name;
    int parentLocationId;
    boolean isLeafLocation;
    int availableMRCount;
    @SerializedName("matches")
    List<Matches> matchesList;
    @SerializedName("timeZoneId")
    String timeZoneId;

    boolean timePeriodSpansTwoDays;
    int timeZoneOffsetMinutes;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getParentLocationId() {
        return parentLocationId;
    }

    public void setParentLocationId(int parentLocationId) {
        this.parentLocationId = parentLocationId;
    }

    public boolean isLeafLocation() {
        return isLeafLocation;
    }

    public void setLeafLocation(boolean leafLocation) {
        isLeafLocation = leafLocation;
    }

    public int getAvailableMRCount() {
        return availableMRCount;
    }

    public void setAvailableMRCount(int availableMRCount) {
        this.availableMRCount = availableMRCount;
    }

    public List<Matches> getMatchesList() {
        return matchesList;
    }

    public void setMatchesList(List<Matches> matchesList) {
        this.matchesList = matchesList;
    }

    public String getTimeZoneId() {
        return timeZoneId;
    }

    public void setTimeZoneId(String timeZoneId) {
        this.timeZoneId = timeZoneId;
    }

    public boolean isTimePeriodSpansTwoDays() {
        return timePeriodSpansTwoDays;
    }

    public void setTimePeriodSpansTwoDays(boolean timePeriodSpansTwoDays) {
        this.timePeriodSpansTwoDays = timePeriodSpansTwoDays;
    }

    public int getTimeZoneOffsetMinutes() {
        return timeZoneOffsetMinutes;
    }

    public void setTimeZoneOffsetMinutes(int timeZoneOffsetMinutes) {
        this.timeZoneOffsetMinutes = timeZoneOffsetMinutes;
    }

   public class Matches{
        @SerializedName("id")
        int matchesId;
        String name;
        String phoneNumber;
        int noOfPeople;
        @SerializedName("location")
        LocationMR locationMR;

        @SerializedName("matchType")
        int matchType;
        @SerializedName("automaticApprovalStatus")
        int automaticApprovalStatus;

        @SerializedName("bookings")
        List<Bookings> bookingsList;

       public int getAutomaticApprovalStatus() {
           return automaticApprovalStatus;
       }

       public void setAutomaticApprovalStatus(int automaticApprovalStatus) {
           this.automaticApprovalStatus = automaticApprovalStatus;
       }

       public List<Bookings> getBookingsList() {
           return bookingsList;
       }

       public void setBookingsList(List<Bookings> bookingsList) {
           this.bookingsList = bookingsList;
       }

       public int getMatchType() {
           return matchType;
       }

       public void setMatchType(int matchType) {
           this.matchType = matchType;
       }

       public class Bookings{

            int id;
            int meetingRoomId;
            String meetingRoomName;
            String date;
            String from;
            String to;
            int bookedByUserId;
            String bookedByUserName;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getMeetingRoomId() {
                return meetingRoomId;
            }

            public void setMeetingRoomId(int meetingRoomId) {
                this.meetingRoomId = meetingRoomId;
            }

            public String getMeetingRoomName() {
                return meetingRoomName;
            }

            public void setMeetingRoomName(String meetingRoomName) {
                this.meetingRoomName = meetingRoomName;
            }

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
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

            public int getBookedByUserId() {
                return bookedByUserId;
            }

            public void setBookedByUserId(int bookedByUserId) {
                this.bookedByUserId = bookedByUserId;
            }

            public String getBookedByUserName() {
                return bookedByUserName;
            }

            public void setBookedByUserName(String bookedByUserName) {
                this.bookedByUserName = bookedByUserName;
            }
        }

        //ExtraAdded
        boolean allowedForBooking=false;
        int currentTimeZoneOffset=0;

       public boolean isAllowedForBooking() {
           return allowedForBooking;
       }

       public void setAllowedForBooking(boolean allowedForBooking) {
           this.allowedForBooking = allowedForBooking;
       }

       public int getCurrentTimeZoneOffset() {
           return currentTimeZoneOffset;
       }

       public void setCurrentTimeZoneOffset(int currentTimeZoneOffset) {
           this.currentTimeZoneOffset = currentTimeZoneOffset;
       }

       public int getMatchesId() {
            return matchesId;
        }

        public void setMatchesId(int matchesId) {
            this.matchesId = matchesId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public int getNoOfPeople() {
            return noOfPeople;
        }

        public void setNoOfPeople(int noOfPeople) {
            this.noOfPeople = noOfPeople;
        }

        public LocationMR getLocationMR() {
            return locationMR;
        }

        public void setLocationMR(LocationMR locationMR) {
            this.locationMR = locationMR;
        }

        public class LocationMR{

            @SerializedName("id")
            int locationMrId;
            String name;
            int parentLocationId;
            boolean isLeafLocation;
            int locationType;
            String timeZoneId;
            String description;
            boolean isActive;

            public int getLocationMrId() {
                return locationMrId;
            }

            public void setLocationMrId(int locationMrId) {
                this.locationMrId = locationMrId;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getParentLocationId() {
                return parentLocationId;
            }

            public void setParentLocationId(int parentLocationId) {
                this.parentLocationId = parentLocationId;
            }

            public boolean isLeafLocation() {
                return isLeafLocation;
            }

            public void setLeafLocation(boolean leafLocation) {
                isLeafLocation = leafLocation;
            }

            public int getLocationType() {
                return locationType;
            }

            public void setLocationType(int locationType) {
                this.locationType = locationType;
            }

            public String getTimeZoneId() {
                return timeZoneId;
            }

            public void setTimeZoneId(String timeZoneId) {
                this.timeZoneId = timeZoneId;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public boolean isActive() {
                return isActive;
            }

            public void setActive(boolean active) {
                isActive = active;
            }
        }

    }

}
