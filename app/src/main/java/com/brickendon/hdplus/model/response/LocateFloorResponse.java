package com.brickendon.hdplus.model.response;

import com.google.gson.annotations.SerializedName;

public class LocateFloorResponse {

    @SerializedName("id")
    int locateCountryId;
    String name;
    String description;

    public int getLocateCountryId() {
        return locateCountryId;
    }

    public void setLocateCountryId(int locateCountryId) {
        this.locateCountryId = locateCountryId;
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
}
