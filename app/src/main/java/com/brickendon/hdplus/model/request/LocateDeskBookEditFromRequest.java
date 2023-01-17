package com.brickendon.hdplus.model.request;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LocateDeskBookEditFromRequest {

    @SerializedName("teamId")
    Integer teamId;
    @SerializedName("teamMembershipId")
    Integer teamMembershipId;

    @SerializedName("changesets")
    List<LocateDeskBookEditFromRequest.ChangeSets> changeSetsList;

    @SerializedName("deletedIds")
    List<DeleteIds> deleteIdsList;

    public Integer getTeamId() {
        return teamId;
    }

    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }

    public Integer getTeamMembershipId() {
        return teamMembershipId;
    }

    public void setTeamMembershipId(Integer teamMembershipId) {
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

    public class ChangeSets {

        @SerializedName("id")
        Integer changeSetId;
        @SerializedName("date")
        String changeSetDate;

        @SerializedName("changes")
        LocateDeskBookEditFromRequest.ChangeSets.Changes changes;

        public Integer getChangeSetId() {
            return changeSetId;
        }

        public void setChangeSetId(Integer changeSetId) {
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

        public class Changes {
            @SerializedName("usageTypeId")
            Integer usageTypeId;

            @SerializedName("from")
            String from;

            @SerializedName("to")
            String to;

            @SerializedName("timeZoneId")
            String timeZoneId;

            @SerializedName("teamDeskId")
            Integer teamDeskId;

            @SerializedName("requestedTeamId")
            Integer requestedTeamId;

            @SerializedName("requestedTeamDeskId")
            Integer requestedTeamDeskId;

            @SerializedName("typeOfCheckIn")
            Integer typeOfCheckIn;

            @SerializedName("comments")
            String comments;

            public String getComments() {
                return comments;
            }

            public void setComments(String comments) {
                this.comments = comments;
            }

            public Integer getUsageTypeId() {
                return usageTypeId;
            }

            public void setUsageTypeId(Integer usageTypeId) {
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

            public Integer getTeamDeskId() {
                return teamDeskId;
            }

            public void setTeamDeskId(Integer teamDeskId) {
                this.teamDeskId = teamDeskId;
            }

            public Integer getRequestedTeamId() {
                return requestedTeamId;
            }

            public void setRequestedTeamId(Integer requestedTeamId) {
                this.requestedTeamId = requestedTeamId;
            }

            public Integer getRequestedTeamDeskId() {
                return requestedTeamDeskId;
            }

            public void setRequestedTeamDeskId(Integer requestedTeamDeskId) {
                this.requestedTeamDeskId = requestedTeamDeskId;
            }

            public Integer getTypeOfCheckIn() {
                return typeOfCheckIn;
            }

            public void setTypeOfCheckIn(Integer typeOfCheckIn) {
                this.typeOfCheckIn = typeOfCheckIn;
            }
        }
    }

    public class DeleteIds{


    }

}
