package dream.guys.hotdeskandroid.model.request;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

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
        @SerializedName("changes")
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

        public static class Changes {
            @SerializedName("bookingStatus")
            private String bookingStatus;
            @SerializedName("from")
            private String from;
            @SerializedName("to")
            private String to;
            @SerializedName("comments")
            private String comments;
            @SerializedName("teamDeskId")
            private int teamDeskId;

            public String getComments() {
                return comments;
            }

            public void setComments(String comments) {
                this.comments = comments;
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
