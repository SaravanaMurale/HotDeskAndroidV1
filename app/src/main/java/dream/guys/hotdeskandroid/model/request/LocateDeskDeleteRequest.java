package dream.guys.hotdeskandroid.model.request;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LocateDeskDeleteRequest {

    int teamId;
    int teamMembershipId;
    @SerializedName("changesets")
    List<ChangeSets> changesetsList;
    @SerializedName("deletedIds")
    List<Integer> deleteIdsList;

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

    public List<ChangeSets> getChangesetsList() {
        return changesetsList;
    }

    public void setChangesetsList(List<ChangeSets> changesetsList) {
        this.changesetsList = changesetsList;
    }

    public List<Integer> getDeleteIdsList() {
        return deleteIdsList;
    }

    public void setDeleteIdsList(List<Integer> deleteIdsList) {
        this.deleteIdsList = deleteIdsList;
    }

    public class ChangeSets{

    }

    public class DeleteIds{

    }




}
