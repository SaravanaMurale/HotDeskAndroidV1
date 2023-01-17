package com.brickendon.hdplus.model.request;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MeetingRoomRequest {

    @SerializedName("meetingRoomId")
    int meetingRoomId;
    @SerializedName("changesets")
    private List<Changeset> changesets;
    private List<DeleteIds> deletedIds;
    private boolean isMsTeams;
    private boolean handleRecurring;
    private boolean isOnlineMeeting;

    public int getMeetingRoomId() {
        return meetingRoomId;
    }

    public void setMeetingRoomId(int meetingRoomId) {
        this.meetingRoomId = meetingRoomId;
    }

    public List<Changeset> getChangesets() {
        return changesets;
    }

    public void setChangesets(List<Changeset> changesets) {
        this.changesets = changesets;
    }

    public List<DeleteIds> getDeletedIds() {
        return deletedIds;
    }

    public void setDeletedIds(List<DeleteIds> deletedIds) {
        this.deletedIds = deletedIds;
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

    public class Changeset{
        private int id;
        private String date;
        private Changes changes;

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

        public Changes getChanges() {
            return changes;
        }

        public void setChanges(Changes changes) {
            this.changes = changes;
        }

        public class Changes{
            public String from;
            @SerializedName("to")
            public String myto;
            public String comments;
            @SerializedName("attendees")
            public List<Integer> attendees;
            public String subject;
            @SerializedName("externalAttendees")
            public List<String> externalAttendees;
            public boolean isRequest;

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

            public String getComments() {
                return comments;
            }

            public void setComments(String comments) {
                this.comments = comments;
            }

            public List<Integer> getAttendees() {
                return attendees;
            }

            public void setAttendees(List<Integer> attendees) {
                this.attendees = attendees;
            }

            public String getSubject() {
                return subject;
            }

            public void setSubject(String subject) {
                this.subject = subject;
            }

            public List<String> getExternalAttendees() {
                return externalAttendees;
            }

            public void setExternalAttendees(List<String> externalAttendees) {
                this.externalAttendees = externalAttendees;
            }

            public boolean isRequest() {
                return isRequest;
            }

            public void setRequest(boolean request) {
                isRequest = request;
            }

            public class Attendees{

            }

            public class ExternalAttendees{

            }
        }


    }

    public class DeleteIds{


    }


}
