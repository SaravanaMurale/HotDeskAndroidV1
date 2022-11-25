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
    List<Manager> managers;
    List<Teams> teams;

    List<Amenity> amenities;

    public List<Amenity> getAmenities() {
        return amenities;
    }

    public void setAmenities(List<Amenity> amenities) {
        this.amenities = amenities;
    }

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

    public List<Manager> getManagers() {
        return managers;
    }

    public void setManagers(List<Manager> managers) {
        this.managers = managers;
    }

    public List<Teams> getTeams() {
        return teams;
    }

    public void setTeams(List<Teams> teams) {
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

    public class Manager {
        private int id;
        private String firstName;
        private String lastName;
        private String fullName;
        private String email;
        private boolean active;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public boolean isActive() {
            return active;
        }

        public void setActive(boolean active) {
            this.active = active;
        }
    }
    public class Teams {
        private int id;
        private boolean isDeleted;
        private boolean isLeafTeam;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public boolean isDeleted() {
            return isDeleted;
        }

        public void setDeleted(boolean deleted) {
            isDeleted = deleted;
        }

        public boolean isLeafTeam() {
            return isLeafTeam;
        }

        public void setLeafTeam(boolean leafTeam) {
            isLeafTeam = leafTeam;
        }
    }
    public class Amenity {
        private int id;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}
