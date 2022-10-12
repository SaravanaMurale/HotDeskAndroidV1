package dream.guys.hotdeskandroid.model.response;

import com.google.gson.annotations.SerializedName;
import com.microsoft.identity.common.internal.authscheme.INameable;

import java.util.List;

public class ActiveTeamsResponse {
    @SerializedName("id")
    int id;
    @SerializedName("parentTeamId")
    int parentTeamId;
    @SerializedName("name")
    String name;
    @SerializedName("isDeleted")
    boolean isDeleted;
    @SerializedName("automaticApprovalStatus")
    int automaticApprovalStatus;
    @SerializedName("isLeafTeam")
    boolean isLeafTeam;
    @SerializedName("managerUserIds")
    List<Integer> managerUserIds;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParentTeamId() {
        return parentTeamId;
    }

    public void setParentTeamId(int parentTeamId) {
        this.parentTeamId = parentTeamId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public int getAutomaticApprovalStatus() {
        return automaticApprovalStatus;
    }

    public void setAutomaticApprovalStatus(int automaticApprovalStatus) {
        this.automaticApprovalStatus = automaticApprovalStatus;
    }

    public boolean isLeafTeam() {
        return isLeafTeam;
    }

    public void setLeafTeam(boolean leafTeam) {
        isLeafTeam = leafTeam;
    }

    public List<Integer> getManagerUserIds() {
        return managerUserIds;
    }

    public void setManagerUserIds(List<Integer> managerUserIds) {
        this.managerUserIds = managerUserIds;
    }
}
