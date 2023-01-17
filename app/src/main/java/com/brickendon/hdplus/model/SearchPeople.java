package com.brickendon.hdplus.model;

public class SearchPeople extends SearchListItem{
    //entity type 1 people
    private int id;
    private String name;
    private String email;
    private String team;
    private String deskPhoneNumber;
    private String mobile;
    private String desk;
    private String status;
    private String usageType;
    private int rank;
    private int entityType;

    //entity type 3-desk
    private String code;
    private String description;
    private String fullLocationPath;
    private boolean active;
    private CurrentLocation currentLocation;

    //entity type 4-meeting
    private int noOfPeople;
    private String phoneNumber;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getDeskPhoneNumber() {
        return deskPhoneNumber;
    }

    public void setDeskPhoneNumber(String deskPhoneNumber) {
        this.deskPhoneNumber = deskPhoneNumber;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getDesk() {
        return desk;
    }

    public void setDesk(String desk) {
        this.desk = desk;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUsageType() {
        return usageType;
    }

    public void setUsageType(String usageType) {
        this.usageType = usageType;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getEntityType() {
        return entityType;
    }

    public void setEntityType(int entityType) {
        this.entityType = entityType;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFullLocationPath() {
        return fullLocationPath;
    }

    public void setFullLocationPath(String fullLocationPath) {
        this.fullLocationPath = fullLocationPath;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public CurrentLocation getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(CurrentLocation currentLocation) {
        this.currentLocation = currentLocation;
    }

    public int getNoOfPeople() {
        return noOfPeople;
    }

    public void setNoOfPeople(int noOfPeople) {
        this.noOfPeople = noOfPeople;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    private class CurrentLocation {
        private int id;
        private String name;
        private String description;
        private int parentLocationId;
        private int locationType;
        private int timeZoneId;
        private boolean isLeafLocation;
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

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public int getParentLocationId() {
            return parentLocationId;
        }

        public void setParentLocationId(int parentLocationId) {
            this.parentLocationId = parentLocationId;
        }

        public int getLocationType() {
            return locationType;
        }

        public void setLocationType(int locationType) {
            this.locationType = locationType;
        }

        public int getTimeZoneId() {
            return timeZoneId;
        }

        public void setTimeZoneId(int timeZoneId) {
            this.timeZoneId = timeZoneId;
        }

        public boolean isLeafLocation() {
            return isLeafLocation;
        }

        public void setLeafLocation(boolean leafLocation) {
            isLeafLocation = leafLocation;
        }

        public boolean isActive() {
            return isActive;
        }

        public void setActive(boolean active) {
            isActive = active;
        }
    }
}
