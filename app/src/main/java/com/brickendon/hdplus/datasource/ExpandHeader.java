package com.brickendon.hdplus.datasource;

public class ExpandHeader extends TeamsExpandListModel {
    private String floorNamae;
    private String floorAvailabilityCount;
    private boolean isFloor;

    public String getFloorNamae() {
        return floorNamae;
    }

    public void setFloorNamae(String floorNamae) {
        this.floorNamae = floorNamae;
    }

    public String getFloorAvailabilityCount() {
        return floorAvailabilityCount;
    }

    public void setFloorAvailabilityCount(String floorAvailabilityCount) {
        this.floorAvailabilityCount = floorAvailabilityCount;
    }

    public boolean isFloor() {
        return isFloor;
    }

    public void setFloor(boolean floor) {
        isFloor = floor;
    }
}
