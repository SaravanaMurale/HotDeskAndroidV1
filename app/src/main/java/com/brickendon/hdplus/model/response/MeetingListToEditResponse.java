package com.brickendon.hdplus.model.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MeetingListToEditResponse {

    @SerializedName("id")
    int id;
    @SerializedName("bookedByUserId")
    int bookedByUserId;
    @SerializedName("meetingRoomId")
    int meetingRoomId;
    @SerializedName("meetingRoomName")
    String meetingRoomName;
    @SerializedName("date")
    String date;
    @SerializedName("from")
    String from;
    @SerializedName("to")
    String to;
    @SerializedName("fromUtc")
    String fromUtc;
    @SerializedName("toUtc")
    String toUtc;
    @SerializedName("recurrence")
    String recurrence;
    @SerializedName("status")
    Status status;


    @SerializedName("subject")
    String subject;
    @SerializedName("comments")
    String comments;

    @SerializedName("attendees")
    List<Attendees> attendeesList;

    @SerializedName("externalAttendees")
    List<String> externalAttendeesList;

    public class Attendees{

        private int id;
        private String firstName;
        private String lastName;
        private String fullName;
        private String email;
        private boolean active;

        public int getId() {
            return id;
        }

        public void setId(int id) {
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

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public boolean isActive() {
            return active;
        }

        public void setActive(boolean active) {
            this.active = active;
        }
    }

    public class ExternalAttendees{

    }

    public List<Attendees> getAttendeesList() {
        return attendeesList;
    }

    public void setAttendeesList(List<Attendees> attendeesList) {
        this.attendeesList = attendeesList;
    }

    public List<String> getExternalAttendeesList() {
        return externalAttendeesList;
    }

    public void setExternalAttendeesList(List<String> externalAttendeesList) {
        this.externalAttendeesList = externalAttendeesList;
    }

    public int getBookedByUserId() {
        return bookedByUserId;
    }

    public void setBookedByUserId(int bookedByUserId) {
        this.bookedByUserId = bookedByUserId;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public class Status {
        String timeStatus;
        String bookingStatus;
        String bookingType;

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

        public String getTimeStatus() {
            return timeStatus;
        }

        public void setTimeStatus(String timeStatus) {
            this.timeStatus = timeStatus;
        }
    }

    public String getRecurrence() {
        return recurrence;
    }

    public void setRecurrence(String recurrence) {
        this.recurrence = recurrence;
    }

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


}
