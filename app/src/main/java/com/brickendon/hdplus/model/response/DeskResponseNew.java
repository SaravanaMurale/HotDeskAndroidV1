package com.brickendon.hdplus.model.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DeskResponseNew
{

    private int locationId;
    private String locationName;
    @SerializedName("desks")
    List<TeamDeskResponse.Desk> desk;

    public int getLocationId()
    {
        return locationId;
    }

    public void setLocationId(int locationId)
    {
        this.locationId = locationId;
    }

    public String getLocationName()
    {
        return locationName;
    }

    public void setLocationName(String locationName)
    {
        this.locationName = locationName;
    }

    public List<TeamDeskResponse.Desk> getDesk()
    {
        return desk;
    }

    public void setDesk(List<TeamDeskResponse.Desk> desk)
    {
        this.desk = desk;
    }
}
