package com.brickendon.hdplus.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class DAOUpcomingBooking {

    @SerializedName("user")
    @Expose
    private User user;
    @SerializedName("personDayViewEntries")
    @Expose
    private ArrayList<PersonDayViewEntry> personDayViewEntries = null;
    @SerializedName("teamId")
    @Expose
    private Integer teamId;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ArrayList<PersonDayViewEntry> getPersonDayViewEntries() {
        return personDayViewEntries;
    }

    public void setPersonDayViewEntries(ArrayList<PersonDayViewEntry> personDayViewEntries) {
        this.personDayViewEntries = personDayViewEntries;
    }

    public Integer getTeamId() {
        return teamId;
    }

    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }


    public static class PersonDayViewEntry {

        @SerializedName("date")
        @Expose
        private String date;
        @SerializedName("usageTypeId")
        @Expose
        private Integer usageTypeId;
        @SerializedName("teamMembershipId")
        @Expose
        private Integer teamMembershipId;
        @SerializedName("teamId")
        @Expose
        private Integer teamId;
        @SerializedName("membershipStartDate")
        @Expose
        private String membershipStartDate;
        @SerializedName("membershipEndDate")
        @Expose
        private Object membershipEndDate;
        @SerializedName("editable")
        @Expose
        private Boolean editable;
        @SerializedName("calendarEntries")
        @Expose
        private ArrayList<CalendarEntry> calendarEntries = null;
        @SerializedName("hasMultipleBookings")
        @Expose
        private Boolean hasMultipleBookings;

        //Extra...
        CalendarEntry calendarEntry;
        public CalendarEntry getCalendarEntry() {
            return calendarEntry;
        }
        public void setCalendarEntry(CalendarEntry calendarEntry) {
            this.calendarEntry = calendarEntry;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public Integer getUsageTypeId() {
            return usageTypeId;
        }

        public void setUsageTypeId(Integer usageTypeId) {
            this.usageTypeId = usageTypeId;
        }

        public Integer getTeamMembershipId() {
            return teamMembershipId;
        }

        public void setTeamMembershipId(Integer teamMembershipId) {
            this.teamMembershipId = teamMembershipId;
        }

        public Integer getTeamId() {
            return teamId;
        }

        public void setTeamId(Integer teamId) {
            this.teamId = teamId;
        }

        public String getMembershipStartDate() {
            return membershipStartDate;
        }

        public void setMembershipStartDate(String membershipStartDate) {
            this.membershipStartDate = membershipStartDate;
        }

        public Object getMembershipEndDate() {
            return membershipEndDate;
        }

        public void setMembershipEndDate(Object membershipEndDate) {
            this.membershipEndDate = membershipEndDate;
        }

        public Boolean getEditable() {
            return editable;
        }

        public void setEditable(Boolean editable) {
            this.editable = editable;
        }

        public ArrayList<CalendarEntry> getCalendarEntries() {
            return calendarEntries;
        }

        public void setCalendarEntries(ArrayList<CalendarEntry> calendarEntries) {
            this.calendarEntries = calendarEntries;
        }

        public Boolean getHasMultipleBookings() {
            return hasMultipleBookings;
        }

        public void setHasMultipleBookings(Boolean hasMultipleBookings) {
            this.hasMultipleBookings = hasMultipleBookings;
        }


        public static class Booking {

            @SerializedName("id")
            @Expose
            private Integer id;
            @SerializedName("deskId")
            @Expose
            private Integer deskId;
            @SerializedName("teamDeskId")
            @Expose
            private Integer teamDeskId;
            @SerializedName("deskCode")
            @Expose
            private String deskCode;
            @SerializedName("deskDescription")
            @Expose
            private Object deskDescription;
            @SerializedName("status")
            @Expose
            private Status status;
            @SerializedName("locationBuildingFloor")
            @Expose
            private Object locationBuildingFloor;

            public Integer getId() {
                return id;
            }

            public void setId(Integer id) {
                this.id = id;
            }

            public Integer getDeskId() {
                return deskId;
            }

            public void setDeskId(Integer deskId) {
                this.deskId = deskId;
            }

            public Integer getTeamDeskId() {
                return teamDeskId;
            }

            public void setTeamDeskId(Integer teamDeskId) {
                this.teamDeskId = teamDeskId;
            }

            public String getDeskCode() {
                return deskCode;
            }

            public void setDeskCode(String deskCode) {
                this.deskCode = deskCode;
            }

            public Object getDeskDescription() {
                return deskDescription;
            }

            public void setDeskDescription(Object deskDescription) {
                this.deskDescription = deskDescription;
            }

            public Status getStatus() {
                return status;
            }

            public void setStatus(Status status) {
                this.status = status;
            }

            public Object getLocationBuildingFloor() {
                return locationBuildingFloor;
            }

            public void setLocationBuildingFloor(Object locationBuildingFloor) {
                this.locationBuildingFloor = locationBuildingFloor;
            }

        }

        public static class CalendarEntry {

            @SerializedName("id")
            @Expose
            private Integer id;
            @SerializedName("teamMembershipId")
            @Expose
            private Integer teamMembershipId;
            @SerializedName("date")
            @Expose
            private String date;
            @SerializedName("comment")
            @Expose
            private Object comment;
            @SerializedName("usageTypeId")
            @Expose
            private Integer usageTypeId;
            @SerializedName("usageTypeName")
            @Expose
            private Object usageTypeName;
            @SerializedName("usageTypeAbbreviation")
            @Expose
            private Object usageTypeAbbreviation;
            @SerializedName("editable")
            @Expose
            private Boolean editable;
            @SerializedName("booking")
            @Expose
            private Booking booking;
            @SerializedName("from")
            @Expose
            private String from;
            @SerializedName("to")
            @Expose
            private String to;
            @SerializedName("fromUtc")
            @Expose
            private String fromUtc;
            @SerializedName("toUtc")
            @Expose
            private String toUtc;
            @SerializedName("timeZoneId")
            @Expose
            private Object timeZoneId;

            public Integer getId() {
                return id;
            }

            public void setId(Integer id) {
                this.id = id;
            }

            public Integer getTeamMembershipId() {
                return teamMembershipId;
            }

            public void setTeamMembershipId(Integer teamMembershipId) {
                this.teamMembershipId = teamMembershipId;
            }

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public Object getComment() {
                return comment;
            }

            public void setComment(Object comment) {
                this.comment = comment;
            }

            public Integer getUsageTypeId() {
                return usageTypeId;
            }

            public void setUsageTypeId(Integer usageTypeId) {
                this.usageTypeId = usageTypeId;
            }

            public Object getUsageTypeName() {
                return usageTypeName;
            }

            public void setUsageTypeName(Object usageTypeName) {
                this.usageTypeName = usageTypeName;
            }

            public Object getUsageTypeAbbreviation() {
                return usageTypeAbbreviation;
            }

            public void setUsageTypeAbbreviation(Object usageTypeAbbreviation) {
                this.usageTypeAbbreviation = usageTypeAbbreviation;
            }

            public Boolean getEditable() {
                return editable;
            }

            public void setEditable(Boolean editable) {
                this.editable = editable;
            }

            public Booking getBooking() {
                return booking;
            }

            public void setBooking(Booking booking) {
                this.booking = booking;
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

            public Object getTimeZoneId() {
                return timeZoneId;
            }

            public void setTimeZoneId(Object timeZoneId) {
                this.timeZoneId = timeZoneId;
            }

        }

        public static class Status {

            @SerializedName("id")
            @Expose
            private Integer id;
            @SerializedName("name")
            @Expose
            private Object name;
            @SerializedName("abbreviation")
            @Expose
            private String abbreviation;
            @SerializedName("colorCode")
            @Expose
            private String colorCode;
            @SerializedName("description")
            @Expose
            private Object description;

            public Integer getId() {
                return id;
            }

            public void setId(Integer id) {
                this.id = id;
            }

            public Object getName() {
                return name;
            }

            public void setName(Object name) {
                this.name = name;
            }

            public String getAbbreviation() {
                return abbreviation;
            }

            public void setAbbreviation(String abbreviation) {
                this.abbreviation = abbreviation;
            }

            public String getColorCode() {
                return colorCode;
            }

            public void setColorCode(String colorCode) {
                this.colorCode = colorCode;
            }

            public Object getDescription() {
                return description;
            }

            public void setDescription(Object description) {
                this.description = description;
            }

        }

    }

    public static class User {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("firstName")
        @Expose
        private String firstName;
        @SerializedName("lastName")
        @Expose
        private String lastName;
        @SerializedName("fullName")
        @Expose
        private String fullName;
        @SerializedName("email")
        @Expose
        private Object email;
        @SerializedName("active")
        @Expose
        private Boolean active;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }

        public Object getEmail() {
            return email;
        }

        public void setEmail(Object email) {
            this.email = email;
        }

        public Boolean getActive() {
            return active;
        }

        public void setActive(Boolean active) {
            this.active = active;
        }

    }


}
