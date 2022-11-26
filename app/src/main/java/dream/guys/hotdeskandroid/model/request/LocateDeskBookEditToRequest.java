package dream.guys.hotdeskandroid.model.request;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LocateDeskBookEditToRequest {


    @SerializedName("teamId")
    int teamId;
    @SerializedName("teamMembershipId")
    int teamMembershipId;

    @SerializedName("changesets")
    List<LocateDeskBookEditToRequest.ChangeSets> changeSetsList;

    @SerializedName("deletedIds")
    List<LocateDeskBookEditToRequest.DeleteIds> deleteIdsList;

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

    public List<LocateDeskBookEditToRequest.ChangeSets> getChangeSetsList() {
        return changeSetsList;
    }

    public void setChangeSetsList(List<LocateDeskBookEditToRequest.ChangeSets> changeSetsList) {
        this.changeSetsList = changeSetsList;
    }

    public List<LocateDeskBookEditToRequest.DeleteIds> getDeleteIdsList() {
        return deleteIdsList;
    }

    public void setDeleteIdsList(List<LocateDeskBookEditToRequest.DeleteIds> deleteIdsList) {
        this.deleteIdsList = deleteIdsList;
    }

    public class ChangeSets{

        @SerializedName("id")
        int changeSetId;
        @SerializedName("date")
        String changeSetDate;

        @SerializedName("changes")
        LocateDeskBookEditToRequest.ChangeSets.Changes changes;

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

        public LocateDeskBookEditToRequest.ChangeSets.Changes getChanges() {
            return changes;
        }

        public void setChanges(LocateDeskBookEditToRequest.ChangeSets.Changes changes) {
            this.changes = changes;
        }

        public  class Changes{

            @SerializedName("to")
            String to;

            public String getTo() {
                return to;
            }

            public void setTo(String to) {
                this.to = to;
            }
        }

    }

    public class DeleteIds{


    }


}
