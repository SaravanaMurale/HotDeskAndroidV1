package com.brickendon.hdplus.model.response;

public class CarParkLocationsModel {
    public int id;
    public String name;
    public int parentLocationId;
    public boolean isLeafLocation;
    public int locationType;
    public String timeZoneId;
    public String description;
    public boolean isActive;

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
