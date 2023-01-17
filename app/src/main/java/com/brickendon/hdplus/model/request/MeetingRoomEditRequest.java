package com.brickendon.hdplus.model.request;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MeetingRoomEditRequest {

    private List<DeleteIds> deletedIds;

    public List<DeleteIds> getDeletedIds() {
        return deletedIds;
    }

    public void setDeletedIds(List<DeleteIds> deletedIds) {
        this.deletedIds = deletedIds;
    }

    @SerializedName("meetingRoomId")
    int meetingRoomId;
    @SerializedName("isMsTeams")
    boolean isMsTeams;
    @SerializedName("handleRecurring")
    boolean handleRecurring;
    @SerializedName("isOnlineMeeting")
    boolean isOnlineMeeting;

    public int getMeetingRoomId() {
        return meetingRoomId;
    }

    public void setMeetingRoomId(int meetingRoomId) {
        this.meetingRoomId = meetingRoomId;
    }

    public boolean isMsTeams() {
        return isMsTeams;
    }

    public void setMsTeams(boolean msTeams) {
        isMsTeams = msTeams;
    }

    public boolean isHandleRecurring() {
        return handleRecurring;
    }

    public void setHandleRecurring(boolean handleRecurring) {
        this.handleRecurring = handleRecurring;
    }

    public boolean isOnlineMeeting() {
        return isOnlineMeeting;
    }

    public void setOnlineMeeting(boolean onlineMeeting) {
        isOnlineMeeting = onlineMeeting;
    }

    public List<Changesets> getChangesetsList() {
        return changesetsList;
    }

    public void setChangesetsList(List<Changesets> changesetsList) {
        this.changesetsList = changesetsList;
    }

    @SerializedName("changesets")
    List<Changesets> changesetsList;

    public class Changesets{
        @SerializedName("changes")
        Changes changes;

        @SerializedName("date")
        String date;
        @SerializedName("id")
        int id;

        public Changes getChanges() {
            return changes;
        }

        public void setChanges(Changes changes) {
            this.changes = changes;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public class Changes{
            @SerializedName("attendees")
           List<Integer> attendeesList;
            String from;
            String subject;
            @SerializedName("externalAttendees")
            List<String> externalAttendeesList;
            String comments;
            boolean isRequest;
            String to;

            public List<Integer> getAttendeesList() {
                return attendeesList;
            }

            public void setAttendeesList(List<Integer> attendeesList) {
                this.attendeesList = attendeesList;
            }

            public String getFrom() {
                return from;
            }

            public void setFrom(String from) {
                this.from = from;
            }

            public String getSubject() {
                return subject;
            }

            public void setSubject(String subject) {
                this.subject = subject;
            }

            public List<String> getExternalAttendeesList() {
                return externalAttendeesList;
            }

            public void setExternalAttendeesList(List<String> externalAttendeesList) {
                this.externalAttendeesList = externalAttendeesList;
            }

            public String getComments() {
                return comments;
            }

            public void setComments(String comments) {
                this.comments = comments;
            }

            public boolean isRequest() {
                return isRequest;
            }

            public void setRequest(boolean request) {
                isRequest = request;
            }

            public String getTo() {
                return to;
            }

            public void setTo(String to) {
                this.to = to;
            }

            public class ExternalAttendees{

            }

            public class Attendees{

            }


        }


    }

    public class DeleteIds{


    }
}
