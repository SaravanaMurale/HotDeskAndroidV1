package com.brickendon.hdplus.model.response;

public class BookedDeskResponse {

    int id;
    String date;
    int usageTypeId;
    int teamDeskId;
    String from;
    String to;

    String bookedByUserName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getUsageTypeId() {
        return usageTypeId;
    }

    public void setUsageTypeId(int usageTypeId) {
        this.usageTypeId = usageTypeId;
    }

    public int getTeamDeskId() {
        return teamDeskId;
    }

    public void setTeamDeskId(int teamDeskId) {
        this.teamDeskId = teamDeskId;
    }

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

    public String getBookedByUserName() {
        return bookedByUserName;
    }

    public void setBookedByUserName(String bookedByUserName) {
        this.bookedByUserName = bookedByUserName;
    }
}
