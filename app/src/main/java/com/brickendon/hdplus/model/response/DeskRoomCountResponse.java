package com.brickendon.hdplus.model.response;

public class DeskRoomCountResponse {
    private int locationId;
    private int availableCount;
    private String date;

    private int teamId;
    private int assignedCount;
    private int usedCount;
    private Boolean isFullyUnutilizedDeskAvailable;

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public int getAssignedCount() {
        return assignedCount;
    }

    public void setAssignedCount(int assignedCount) {
        this.assignedCount = assignedCount;
    }

    public int getUsedCount() {
        return usedCount;
    }

    public void setUsedCount(int usedCount) {
        this.usedCount = usedCount;
    }

    public Boolean getFullyUnutilizedDeskAvailable() {
        return isFullyUnutilizedDeskAvailable;
    }

    public void setFullyUnutilizedDeskAvailable(Boolean fullyUnutilizedDeskAvailable) {
        isFullyUnutilizedDeskAvailable = fullyUnutilizedDeskAvailable;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public int getAvailableCount() {
        return availableCount;
    }

    public void setAvailableCount(int availableCount) {
        this.availableCount = availableCount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
