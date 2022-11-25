package dream.guys.hotdeskandroid.model.response;

import java.util.ArrayList;

public class ParkingSpotModel {
    private int id;
    private String code;
    private boolean active;
    private String description;
    private String dailyAvailableFrom;
    private String dailyAvailableTo;
    private Location location;
    private ArrayList<Assignees> assignees;
    private int parkingSlotAvailability;
    // 1-
    public ArrayList<Assignees> getAssignees() {
        return assignees;
    }

    public void setAssignees(ArrayList<Assignees> assignees) {
        this.assignees = assignees;
    }

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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public int getParkingSlotAvailability() {
        return parkingSlotAvailability;
    }

    public void setParkingSlotAvailability(int parkingSlotAvailability) {
        this.parkingSlotAvailability = parkingSlotAvailability;
    }

    public class Assignees{
        private int id;
        private String firstName;

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
    }
    public class Location{
        private int id;
        private String name;
        private int parentLocationId;
        private boolean isLeafLocation;
        private int locationType;
        private String timeZoneId;
        private String description;
        private boolean isActive;

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
