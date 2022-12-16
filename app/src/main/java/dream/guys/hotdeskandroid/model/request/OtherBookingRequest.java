package dream.guys.hotdeskandroid.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OtherBookingRequest {

    @SerializedName("changesets")
    @Expose
    private List<Changeset> changesets = null;
    @SerializedName("deletedIds")
    @Expose
    private List<Integer> deletedIds = null;
    @SerializedName("teamId")
    @Expose
    private Integer teamId;
    @SerializedName("teamMembershipId")
    @Expose
    private Integer teamMembershipId;

    public List<Changeset> getChangesets() {
        return changesets;
    }

    public void setChangesets(List<Changeset> changesets) {
        this.changesets = changesets;
    }

    public List<Integer> getDeletedIds() {
        return deletedIds;
    }

    public void setDeletedIds(List<Integer> deletedIds) {
        this.deletedIds = deletedIds;
    }

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

    public class Changeset {

        @SerializedName("date")
        @Expose
        private String date;
        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("changes")
        @Expose
        private Changes changes;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Changes getChanges() {
            return changes;
        }

        public void setChanges(Changes changes) {
            this.changes = changes;
        }
        public class Changes {

            @SerializedName("from")
            @Expose
            private String from;
            @SerializedName("timeZoneId")
            @Expose
            private String timeZoneId;
            @SerializedName("to")
            @Expose
            private String to;
            @SerializedName("usageTypeId")
            @Expose
            private Integer usageTypeId;

            private String recurrence;

            public String getRecurrence() {
                return recurrence;
            }

            public void setRecurrence(String recurrence) {
                this.recurrence = recurrence;
            }
            public String getFrom() {
                return from;
            }

            public void setFrom(String from) {
                this.from = from;
            }

            public String getTimeZoneId() {
                return timeZoneId;
            }

            public void setTimeZoneId(String timeZoneId) {
                this.timeZoneId = timeZoneId;
            }

            public String getTo() {
                return to;
            }

            public void setTo(String to) {
                this.to = to;
            }

            public Integer getUsageTypeId() {
                return usageTypeId;
            }

            public void setUsageTypeId(Integer usageTypeId) {
                this.usageTypeId = usageTypeId;
            }

        }
    }

}