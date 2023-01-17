package com.brickendon.hdplus.model.request;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LocateDeskBookEditFromAndToRequest {

    @SerializedName("teamId")
    int teamId;
    @SerializedName("teamMembershipId")
    int teamMembershipId;

    @SerializedName("changesets")
    List<LocateDeskBookEditFromAndToRequest.ChangeSets> changeSetsList;

    @SerializedName("deletedIds")
    List<LocateDeskBookEditFromAndToRequest.DeleteIds> deleteIdsList;

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

    public List<LocateDeskBookEditFromAndToRequest.ChangeSets> getChangeSetsList() {
        return changeSetsList;
    }

    public void setChangeSetsList(List<LocateDeskBookEditFromAndToRequest.ChangeSets> changeSetsList) {
        this.changeSetsList = changeSetsList;
    }

    public List<LocateDeskBookEditFromAndToRequest.DeleteIds> getDeleteIdsList() {
        return deleteIdsList;
    }

    public void setDeleteIdsList(List<LocateDeskBookEditFromAndToRequest.DeleteIds> deleteIdsList) {
        this.deleteIdsList = deleteIdsList;
    }

    public class ChangeSets{

        @SerializedName("id")
        int changeSetId;
        @SerializedName("date")
        String changeSetDate;

        @SerializedName("changes")
        LocateDeskBookEditFromAndToRequest.ChangeSets.Changes changes;

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

        public LocateDeskBookEditFromAndToRequest.ChangeSets.Changes getChanges() {
            return changes;
        }

        public void setChanges(LocateDeskBookEditFromAndToRequest.ChangeSets.Changes changes) {
            this.changes = changes;
        }

        public  class Changes{

            @SerializedName("from")
            String from;

            public String getFrom() {
                return from;
            }

            public void setFrom(String from) {
                this.from = from;
            }
        }

    }

    public class DeleteIds{


    }

}
