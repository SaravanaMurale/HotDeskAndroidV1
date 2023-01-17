package com.brickendon.hdplus.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DAODeskAccept {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("RequestedTeamDeskId")
    @Expose
    private Integer requestedTeamDeskId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRequestedTeamDeskId() {
        return requestedTeamDeskId;
    }

    public void setRequestedTeamDeskId(Integer requestedTeamDeskId) {
        this.requestedTeamDeskId = requestedTeamDeskId;
    }

}
