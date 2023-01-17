package com.brickendon.hdplus.model.response;

import com.google.gson.annotations.SerializedName;

public class MeetingRoomDescriptionResponse {

    @SerializedName("description")
    String description;

    public MeetingRoomDescriptionResponse(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
