package dream.guys.hotdeskandroid.model.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DeskAvaliabilityResponse {


    @SerializedName("teamDeskAvailability")
    List<TeamDeskAvaliabilityList> teamDeskAvaliabilityList;

    @SerializedName("locationDesks")
    List<LocationDesks> locationDesksList;

    public List<TeamDeskAvaliabilityList> getTeamDeskAvaliabilityList() {
        return teamDeskAvaliabilityList;
    }

    public void setTeamDeskAvaliabilityList(List<TeamDeskAvaliabilityList> teamDeskAvaliabilityList) {
        this.teamDeskAvaliabilityList = teamDeskAvaliabilityList;
    }

    public List<LocationDesks> getLocationDesksList() {
        return locationDesksList;
    }

    public void setLocationDesksList(List<LocationDesks> locationDesksList) {
        this.locationDesksList = locationDesksList;
    }

    public class TeamDeskAvaliabilityList{
        @SerializedName("teamDeskId")
        int teamDeskId;
        @SerializedName("deskId")
        int deskId;
        @SerializedName("teamId")
        int teamId;
        @SerializedName("teamName")
        String teamName;
        @SerializedName("code")
        String code;

        @SerializedName("availableTimeSlots")
        List<AvailableTimeSlots> availableTimeSlotsList;
        @SerializedName("currentTimeZoneOffset")
        int currentTimeZoneOffset;

        @SerializedName("isBookedByUser")
        boolean isBookedByUser;

        @SerializedName("isPartiallyAvailable")
        boolean isPartiallyAvailable;

        @SerializedName("isBookedByElse")
        boolean isBookedByElse;

        public List<AvailableTimeSlots> getAvailableTimeSlotsList() {
            return availableTimeSlotsList;
        }

        public void setAvailableTimeSlotsList(List<AvailableTimeSlots> availableTimeSlotsList) {
            this.availableTimeSlotsList = availableTimeSlotsList;
        }

        public int getCurrentTimeZoneOffset() {
            return currentTimeZoneOffset;
        }

        public void setCurrentTimeZoneOffset(int currentTimeZoneOffset) {
            this.currentTimeZoneOffset = currentTimeZoneOffset;
        }

        public boolean isBookedByUser() {
            return isBookedByUser;
        }

        public void setBookedByUser(boolean bookedByUser) {
            isBookedByUser = bookedByUser;
        }

        public boolean isPartiallyAvailable() {
            return isPartiallyAvailable;
        }

        public void setPartiallyAvailable(boolean partiallyAvailable) {
            isPartiallyAvailable = partiallyAvailable;
        }

        public boolean isBookedByElse() {
            return isBookedByElse;
        }

        public void setBookedByElse(boolean bookedByElse) {
            isBookedByElse = bookedByElse;
        }

        public int getTeamDeskId() {
            return teamDeskId;
        }

        public void setTeamDeskId(int teamDeskId) {
            this.teamDeskId = teamDeskId;
        }

        public int getDeskId() {
            return deskId;
        }

        public void setDeskId(int deskId) {
            this.deskId = deskId;
        }

        public int getTeamId() {
            return teamId;
        }

        public void setTeamId(int teamId) {
            this.teamId = teamId;
        }

        public String getTeamName() {
            return teamName;
        }

        public void setTeamName(String teamName) {
            this.teamName = teamName;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public class AvailableTimeSlots{
            String from;
            String to;

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
        }

    }

    public class LocationDesks{

        private int id;
        private String code;
        private boolean isTeamDesk;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public boolean isTeamDesk() {
            return isTeamDesk;
        }

        public void setTeamDesk(boolean teamDesk) {
            isTeamDesk = teamDesk;
        }
    }
}
