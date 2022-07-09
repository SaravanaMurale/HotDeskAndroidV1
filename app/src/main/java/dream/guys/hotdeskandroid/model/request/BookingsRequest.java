package dream.guys.hotdeskandroid.model.request;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;

public class BookingsRequest {
    @SerializedName("teamId")
    private int teamId;
    @SerializedName("teamMembershipId")
    private int teamMembershipId;
    @SerializedName("changesets")
    private ArrayList<ChangeSets> changeSets;
    @SerializedName("deletedIds")
    private ArrayList<Integer> deletedIds;

    public ArrayList<Integer> getDeletedIds() {
        return deletedIds;
    }

    public void setDeletedIds(ArrayList<Integer> deletedIds) {
        this.deletedIds = deletedIds;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public int getTeamMembershipId() {
        return teamMembershipId;
    }

    public void setTeamMembershipId(int teamMembershipId) {
        this.teamMembershipId = teamMembershipId;
    }

    public ArrayList<ChangeSets> getChangeSets() {
        return changeSets;
    }

    public void setChangeSets(ArrayList<ChangeSets> changeSets) {
        this.changeSets = changeSets;
    }


    public static class ChangeSets {
        @SerializedName("id")
        private int id;
        @SerializedName("date")
        private String date;
        @SerializedName("autoAdjustStartTime")
        private boolean autoAdjustStartTime;
        @SerializedName("changes")
        private Changes changes;

        @SerializedName("isRequest")
        private boolean isRequest;
        @SerializedName("isRecurrence")
        private boolean isRecurrence;
        @SerializedName("isRecurrenceParent")
        private boolean isRecurrenceParent;

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

        public boolean isAutoAdjustStartTime() {
            return autoAdjustStartTime;
        }

        public void setAutoAdjustStartTime(boolean autoAdjustStartTime) {
            this.autoAdjustStartTime = autoAdjustStartTime;
        }

        public Changes getChanges() {
            return changes;
        }

        public void setChanges(Changes changes) {
            this.changes = changes;
        }

        public boolean isRequest() {
            return isRequest;
        }

        public void setRequest(boolean request) {
            isRequest = request;
        }

        public boolean isRecurrence() {
            return isRecurrence;
        }

        public void setRecurrence(boolean recurrence) {
            isRecurrence = recurrence;
        }

        public boolean isRecurrenceParent() {
            return isRecurrenceParent;
        }

        public void setRecurrenceParent(boolean recurrenceParent) {
            isRecurrenceParent = recurrenceParent;
        }

        public static class Changes {
            @SerializedName("bookingStatus")
            private String bookingStatus;
            @SerializedName("from")
            private String from;
            @SerializedName("to")
            private String to;
            @SerializedName("teamDeskId")
            private int teamDeskId;

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

            public int getTeamDeskId() {
                return teamDeskId;
            }

            public void setTeamDeskId(int teamDeskId) {
                this.teamDeskId = teamDeskId;
            }

            public String getBookingStatus() {
                return bookingStatus;
            }

            public void setBookingStatus(String bookingStatus) {
                this.bookingStatus = bookingStatus;
            }
        }
    }

    private class DeletedIds {
    }
}
