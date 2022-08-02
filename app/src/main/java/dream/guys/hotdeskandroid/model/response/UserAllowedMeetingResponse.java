package dream.guys.hotdeskandroid.model.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserAllowedMeetingResponse {

    int id;
    String name;
    @SerializedName("location")
    LocationMeeting locationMeeting;
    String phoneNumber;
    String description;
    int noOfPeople;
    String dailyAvailableFrom;
    String dailyAvailableTo;
    boolean active;
    int automaticApprovalStatus;
    String managers;
    String teams;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocationMeeting getLocationMeeting() {
        return locationMeeting;
    }

    public void setLocationMeeting(LocationMeeting locationMeeting) {
        this.locationMeeting = locationMeeting;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getNoOfPeople() {
        return noOfPeople;
    }

    public void setNoOfPeople(int noOfPeople) {
        this.noOfPeople = noOfPeople;
    }

    public String getDailyAvailableFrom() {
        return dailyAvailableFrom;
    }

    public void setDailyAvailableFrom(String dailyAvailableFrom) {
        this.dailyAvailableFrom = dailyAvailableFrom;
    }

    public String getDailyAvailableTo() {
        return dailyAvailableTo;
    }

    public void setDailyAvailableTo(String dailyAvailableTo) {
        this.dailyAvailableTo = dailyAvailableTo;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getAutomaticApprovalStatus() {
        return automaticApprovalStatus;
    }

    public void setAutomaticApprovalStatus(int automaticApprovalStatus) {
        this.automaticApprovalStatus = automaticApprovalStatus;
    }

    public String getManagers() {
        return managers;
    }

    public void setManagers(String managers) {
        this.managers = managers;
    }

    public String getTeams() {
        return teams;
    }

    public void setTeams(String teams) {
        this.teams = teams;
    }

    public class LocationMeeting{

            @SerializedName("id")
            int locationId;
            String name;
            int parentLocationId;
            boolean isLeafLocation;
            int locationType;
            String timeZoneId;
            String description;
            boolean isActive;

            public int getLocationId() {
                return locationId;
            }

            public void setLocationId(int locationId) {
                this.locationId = locationId;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getParentLocationId() {
                return parentLocationId;
            }

            public void setParentLocationId(int parentLocationId) {
                this.parentLocationId = parentLocationId;
            }

            public boolean isLeafLocation() {
                return isLeafLocation;
            }

            public void setLeafLocation(boolean leafLocation) {
                isLeafLocation = leafLocation;
            }

            public int getLocationType() {
                return locationType;
            }

            public void setLocationType(int locationType) {
                this.locationType = locationType;
            }

            public String getTimeZoneId() {
                return timeZoneId;
            }

            public void setTimeZoneId(String timeZoneId) {
                this.timeZoneId = timeZoneId;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public boolean isActive() {
                return isActive;
            }

            public void setActive(boolean active) {
                isActive = active;
            }
        }

}
