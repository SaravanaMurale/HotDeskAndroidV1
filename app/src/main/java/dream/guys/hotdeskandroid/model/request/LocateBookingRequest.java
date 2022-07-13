package dream.guys.hotdeskandroid.model.request;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LocateBookingRequest {

    @SerializedName("teamId")
    int teamId;
    @SerializedName("teamMembershipId")
    int teamMembershipId;

    @SerializedName("changesets")
    List<ChangeSets> changeSetsList;

    @SerializedName("deletedIds")
    List<DeleteIds> deleteIdsList;

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

    public List<ChangeSets> getChangeSetsList() {
        return changeSetsList;
    }

    public void setChangeSetsList(List<ChangeSets> changeSetsList) {
        this.changeSetsList = changeSetsList;
    }

    public List<DeleteIds> getDeleteIdsList() {
        return deleteIdsList;
    }

    public void setDeleteIdsList(List<DeleteIds> deleteIdsList) {
        this.deleteIdsList = deleteIdsList;
    }

    public class ChangeSets{

        @SerializedName("id")
        int changeSetId;
        @SerializedName("date")
        String changeSetDate;

        @SerializedName("changes")
        Changes changes;

        public int getChangeSetId() {
            return changeSetId;
        }

        public void setChangeSetId(int changeSetId) {
            this.changeSetId = changeSetId;
        }

        public String getChangeSetDate() {
            return changeSetDate;
        }

        public void setChangeSetDate(String changeSetDate) {
            this.changeSetDate = changeSetDate;
        }

        public Changes getChanges() {
            return changes;
        }

        public void setChanges(Changes changes) {
            this.changes = changes;
        }

        public  class Changes{
            @SerializedName("usageTypeId")
            int usageTypeId;

            @SerializedName("from")
            String from;

            @SerializedName("to")
            String to;

            @SerializedName("timeZoneId")
            String timeZoneId;

            @SerializedName("teamDeskId")
            int teamDeskId;

            @SerializedName("typeOfCheckIn")
            int typeOfCheckIn;

            public int getUsageTypeId() {
                return usageTypeId;
            }

            public void setUsageTypeId(int usageTypeId) {
                this.usageTypeId = usageTypeId;
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

            public String getTimeZoneId() {
                return timeZoneId;
            }

            public void setTimeZoneId(String timeZoneId) {
                this.timeZoneId = timeZoneId;
            }

            public int getTeamDeskId() {
                return teamDeskId;
            }

            public void setTeamDeskId(int teamDeskId) {
                this.teamDeskId = teamDeskId;
            }

            public int getTypeOfCheckIn() {
                return typeOfCheckIn;
            }

            public void setTypeOfCheckIn(int typeOfCheckIn) {
                this.typeOfCheckIn = typeOfCheckIn;
            }
        }

    }

    public class DeleteIds{


    }


}
