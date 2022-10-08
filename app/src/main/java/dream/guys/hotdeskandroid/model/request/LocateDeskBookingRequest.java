package dream.guys.hotdeskandroid.model.request;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class LocateDeskBookingRequest {

    @SerializedName("teamId")
    private int teamId;
    @SerializedName("teamMembershipId")
    private int teamMembershipId;
    @SerializedName("changesets")
    private List<ChangeSets> changeSets;
    @SerializedName("deletedIds")
    private List<DeleteIds> deletedIds;

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

    public List<ChangeSets> getChangeSets() {
        return changeSets;
    }

    public void setChangeSets(List<ChangeSets> changeSets) {
        this.changeSets = changeSets;
    }

    public List<DeleteIds> getDeletedIds() {
        return deletedIds;
    }

    public void setDeletedIds(List<DeleteIds> deletedIds) {
        this.deletedIds = deletedIds;
    }

    public  class ChangeSets {
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

        public  class   Changes {
            @SerializedName("usageTypeId")
            int usageTypeId;

            @SerializedName("from")
            String from;

            @SerializedName("to")
            String to;

            @SerializedName("timeZoneId")
            String timeZoneId;

            @SerializedName("teamDeskId")
            String teamDeskId;

            @SerializedName("requestedTeamId")
            int requestedTeamId;

            @SerializedName("requestedTeamDeskId")
            int requestedTeamDeskId;

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

            public String getTeamDeskId() {
                return teamDeskId;
            }

            public void setTeamDeskId(String teamDeskId) {
                this.teamDeskId = teamDeskId;
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
