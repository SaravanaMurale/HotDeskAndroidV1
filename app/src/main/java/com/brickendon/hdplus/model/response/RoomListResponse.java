package com.brickendon.hdplus.model.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RoomListResponse {
    @SerializedName("meetingRooms")
    List<UserAllowedMeetingResponse> meetingResponses;

    public List<UserAllowedMeetingResponse> getMeetingResponses() {
        return meetingResponses;
    }

    public void setMeetingResponses(List<UserAllowedMeetingResponse> meetingResponses) {
        this.meetingResponses = meetingResponses;
    }

}
