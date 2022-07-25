package dream.guys.hotdeskandroid.model.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CarParkingslotsResponse {

    @SerializedName("id")
    int carParkingSlotId;
    @SerializedName("code")
    String code;
    @SerializedName("active")
    boolean active;
    @SerializedName("description")
    String description;
    @SerializedName("dailyAvailableFrom")
    String dailyAvailableFrom;
    @SerializedName("dailyAvailableTo")
    String dailyAvailableTo;
    @SerializedName("location")
    LocationParking locationParking;
    @SerializedName("parkingSlotAvailability")
    int parkingSlotAvailability;
    @SerializedName("assignees")
    List<Assigness> assignessList;

    public int getCarParkingSlotId() {
        return carParkingSlotId;
    }

    public void setCarParkingSlotId(int carParkingSlotId) {
        this.carParkingSlotId = carParkingSlotId;
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

    public LocationParking getLocationParking() {
        return locationParking;
    }

    public void setLocationParking(LocationParking locationParking) {
        this.locationParking = locationParking;
    }

    public int getParkingSlotAvailability() {
        return parkingSlotAvailability;
    }

    public void setParkingSlotAvailability(int parkingSlotAvailability) {
        this.parkingSlotAvailability = parkingSlotAvailability;
    }

    public List<Assigness> getAssignessList() {
        return assignessList;
    }

    public void setAssignessList(List<Assigness> assignessList) {
        this.assignessList = assignessList;
    }

    public class LocationParking{

        @SerializedName("id")
        int locationParkingId;
        @SerializedName("name")
        String name;
        @SerializedName("parentLocationId")
        String parentLocationId;
        @SerializedName("isLeafLocation")
        boolean isLeafLocation;
        @SerializedName("locationType")
        int locationType;
        @SerializedName("timeZoneId")
        String timeZoneId;
        @SerializedName("description")
        String description;
        @SerializedName("isActive")
        boolean isActive;

        public int getLocationParkingId() {
            return locationParkingId;
        }

        public void setLocationParkingId(int locationParkingId) {
            this.locationParkingId = locationParkingId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getParentLocationId() {
            return parentLocationId;
        }

        public void setParentLocationId(String parentLocationId) {
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

    public class Assigness{


    }





}
