package com.brickendon.hdplus.model.response;

import com.google.gson.annotations.SerializedName;

public class CarParkingDescriptionResponse {

    @SerializedName("description")
    String description;

    public CarParkingDescriptionResponse(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
