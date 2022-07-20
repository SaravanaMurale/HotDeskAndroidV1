package dream.guys.hotdeskandroid.model.response;

import com.google.gson.annotations.SerializedName;

public class TeamsResponse {

    @SerializedName("id")
    int id;
    @SerializedName("parentTeamId")
    int parentTeamId;
    @SerializedName("name")
    String name;
    @SerializedName("deskCount")
    int deskCount;
    @SerializedName("active")
    boolean active;
    @SerializedName("emailNotificationEnabled")
    boolean emailNotificationEnabled;
    @SerializedName("membersCount")
    int membersCount;
    @SerializedName("automaticApprovalStatus")
    int automaticApprovalStatus;
    @SerializedName("directSubTeamCount")
    int directSubTeamCount;
    @SerializedName("isLeafTeam")
    boolean isLeafTeam;
    @SerializedName("rowVersion")
    String rowVersion;
    @SerializedName("membersUserIds")
    String membersUserIds;

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

    public int getDeskCount() {
        return deskCount;
    }

    public void setDeskCount(int deskCount) {
        this.deskCount = deskCount;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isEmailNotificationEnabled() {
        return emailNotificationEnabled;
    }

    public void setEmailNotificationEnabled(boolean emailNotificationEnabled) {
        this.emailNotificationEnabled = emailNotificationEnabled;
    }

    public int getMembersCount() {
        return membersCount;
    }

    public void setMembersCount(int membersCount) {
        this.membersCount = membersCount;
    }

    public int getAutomaticApprovalStatus() {
        return automaticApprovalStatus;
    }

    public void setAutomaticApprovalStatus(int automaticApprovalStatus) {
        this.automaticApprovalStatus = automaticApprovalStatus;
    }

    public int getDirectSubTeamCount() {
        return directSubTeamCount;
    }

    public void setDirectSubTeamCount(int directSubTeamCount) {
        this.directSubTeamCount = directSubTeamCount;
    }

    public boolean isLeafTeam() {
        return isLeafTeam;
    }

    public void setLeafTeam(boolean leafTeam) {
        isLeafTeam = leafTeam;
    }

    public String getRowVersion() {
        return rowVersion;
    }

    public void setRowVersion(String rowVersion) {
        this.rowVersion = rowVersion;
    }

    public String getMembersUserIds() {
        return membersUserIds;
    }

    public void setMembersUserIds(String membersUserIds) {
        this.membersUserIds = membersUserIds;
    }
}
