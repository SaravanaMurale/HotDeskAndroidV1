package com.brickendon.hdplus.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class DAONotificationList {

    @SerializedName("results")
    @Expose
    private ArrayList<Result> results = null;
    @SerializedName("totalRecords")
    @Expose
    private Integer totalRecords;

    public ArrayList<Result> getResults() {
        return results;
    }

    public void setResults(ArrayList<Result> results) {
        this.results = results;
    }

    public Integer getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(Integer totalRecords) {
        this.totalRecords = totalRecords;
    }

    public class Result {

        @SerializedName("calendarEntryId")
        @Expose
        private Integer calendarEntryId;
        @SerializedName("teamId")
        @Expose
        private Integer teamId;
        @SerializedName("requestedTeamDeskId")
        @Expose
        private Integer requestedTeamDeskId;
        @SerializedName("actionedByUserId")
        @Expose
        private Integer actionedByUserId;
        @SerializedName("rejectionReason")
        @Expose
        private Object rejectionReason;
        @SerializedName("deskId")
        @Expose
        private Integer deskId;
        @SerializedName("deskCode")
        @Expose
        private String deskCode;
        @SerializedName("deskTeam")
        @Expose
        private String deskTeam;
        @SerializedName("deskDescription")
        @Expose
        private Object deskDescription;
        @SerializedName("deskLocation")
        @Expose
        private Object deskLocation;
        @SerializedName("requesterUserId")
        @Expose
        private Integer requesterUserId;
        @SerializedName("requesterTeamMembershipId")
        @Expose
        private Integer requesterTeamMembershipId;
        @SerializedName("requesterName")
        @Expose
        private String requesterName;
        @SerializedName("requesterTeam")
        @Expose
        private String requesterTeam;
        @SerializedName("requesterComment")
        @Expose
        private Object requesterComment;
        @SerializedName("approvalManagerName")
        @Expose
        private String approvalManagerName;
        @SerializedName("availableDeskCount")
        @Expose
        private Integer availableDeskCount;
        @SerializedName("availableTeamDeskIds")
        @Expose
        private Object availableTeamDeskIds;
        @SerializedName("teamDeskAvailability")
        @Expose
        private Object teamDeskAvailability;
        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("date")
        @Expose
        private String date;
        @SerializedName("from")
        @Expose
        private String from;
        @SerializedName("fromUtc")
        @Expose
        private String fromUtc;
        @SerializedName("to")
        @Expose
        private String to;
        @SerializedName("toUtc")
        @Expose
        private String toUtc;
        @SerializedName("timeZoneId")
        @Expose
        private String timeZoneId;
        @SerializedName("entityType")
        @Expose
        private Integer entityType;
        @SerializedName("status")
        @Expose
        private Integer status;
        @SerializedName("requestedDate")
        @Expose
        private String requestedDate;

        public Integer getCalendarEntryId() {
            return calendarEntryId;
        }

        public void setCalendarEntryId(Integer calendarEntryId) {
            this.calendarEntryId = calendarEntryId;
        }

        public Integer getTeamId() {
            return teamId;
        }

        public void setTeamId(Integer teamId) {
            this.teamId = teamId;
        }

        public Integer getRequestedTeamDeskId() {
            return requestedTeamDeskId;
        }

        public void setRequestedTeamDeskId(Integer requestedTeamDeskId) {
            this.requestedTeamDeskId = requestedTeamDeskId;
        }

        public Integer getActionedByUserId() {
            return actionedByUserId;
        }

        public void setActionedByUserId(Integer actionedByUserId) {
            this.actionedByUserId = actionedByUserId;
        }

        public Object getRejectionReason() {
            return rejectionReason;
        }

        public void setRejectionReason(Object rejectionReason) {
            this.rejectionReason = rejectionReason;
        }

        public Integer getDeskId() {
            return deskId;
        }

        public void setDeskId(Integer deskId) {
            this.deskId = deskId;
        }

        public String getDeskCode() {
            return deskCode;
        }

        public void setDeskCode(String deskCode) {
            this.deskCode = deskCode;
        }

        public String getDeskTeam() {
            return deskTeam;
        }

        public void setDeskTeam(String deskTeam) {
            this.deskTeam = deskTeam;
        }

        public Object getDeskDescription() {
            return deskDescription;
        }

        public void setDeskDescription(Object deskDescription) {
            this.deskDescription = deskDescription;
        }

        public Object getDeskLocation() {
            return deskLocation;
        }

        public void setDeskLocation(Object deskLocation) {
            this.deskLocation = deskLocation;
        }

        public Integer getRequesterUserId() {
            return requesterUserId;
        }

        public void setRequesterUserId(Integer requesterUserId) {
            this.requesterUserId = requesterUserId;
        }

        public Integer getRequesterTeamMembershipId() {
            return requesterTeamMembershipId;
        }

        public void setRequesterTeamMembershipId(Integer requesterTeamMembershipId) {
            this.requesterTeamMembershipId = requesterTeamMembershipId;
        }

        public String getRequesterName() {
            return requesterName;
        }

        public void setRequesterName(String requesterName) {
            this.requesterName = requesterName;
        }

        public String getRequesterTeam() {
            return requesterTeam;
        }

        public void setRequesterTeam(String requesterTeam) {
            this.requesterTeam = requesterTeam;
        }

        public Object getRequesterComment() {
            return requesterComment;
        }

        public void setRequesterComment(Object requesterComment) {
            this.requesterComment = requesterComment;
        }

        public String getApprovalManagerName() {
            return approvalManagerName;
        }

        public void setApprovalManagerName(String approvalManagerName) {
            this.approvalManagerName = approvalManagerName;
        }

        public Integer getAvailableDeskCount() {
            return availableDeskCount;
        }

        public void setAvailableDeskCount(Integer availableDeskCount) {
            this.availableDeskCount = availableDeskCount;
        }

        public Object getAvailableTeamDeskIds() {
            return availableTeamDeskIds;
        }

        public void setAvailableTeamDeskIds(Object availableTeamDeskIds) {
            this.availableTeamDeskIds = availableTeamDeskIds;
        }

        public Object getTeamDeskAvailability() {
            return teamDeskAvailability;
        }

        public void setTeamDeskAvailability(Object teamDeskAvailability) {
            this.teamDeskAvailability = teamDeskAvailability;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getFrom() {
            return from;
        }

        public void setFrom(String from) {
            this.from = from;
        }

        public String getFromUtc() {
            return fromUtc;
        }

        public void setFromUtc(String fromUtc) {
            this.fromUtc = fromUtc;
        }

        public String getTo() {
            return to;
        }

        public void setTo(String to) {
            this.to = to;
        }

        public String getToUtc() {
            return toUtc;
        }

        public void setToUtc(String toUtc) {
            this.toUtc = toUtc;
        }

        public String getTimeZoneId() {
            return timeZoneId;
        }

        public void setTimeZoneId(String timeZoneId) {
            this.timeZoneId = timeZoneId;
        }

        public Integer getEntityType() {
            return entityType;
        }

        public void setEntityType(Integer entityType) {
            this.entityType = entityType;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public String getRequestedDate() {
            return requestedDate;
        }

        public void setRequestedDate(String requestedDate) {
            this.requestedDate = requestedDate;
        }

    }

}
