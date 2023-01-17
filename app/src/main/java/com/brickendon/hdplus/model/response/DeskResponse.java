package com.brickendon.hdplus.model.response;

public class DeskResponse
{

    private int id;
    private String name;
    private int parentLocationId;
    private String isLeafLocation;
    private String timeZoneId;
    private String description;
    private boolean isActive;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getParentLocationId()
    {
        return parentLocationId;
    }

    public void setParentLocationId(int parentLocationId)
    {
        this.parentLocationId = parentLocationId;
    }

    public String getIsLeafLocation()
    {
        return isLeafLocation;
    }

    public void setIsLeafLocation(String isLeafLocation)
    {
        this.isLeafLocation = isLeafLocation;
    }

    public String getTimeZoneId()
    {
        return timeZoneId;
    }

    public void setTimeZoneId(String timeZoneId)
    {
        this.timeZoneId = timeZoneId;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public boolean isActive()
    {
        return isActive;
    }

    public void setActive(boolean active)
    {
        isActive = active;
    }
}
