package com.brickendon.hdplus.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DAOActiveLocation implements Serializable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("parentLocationId")
    @Expose
    private Integer parentLocationId;
    @SerializedName("isLeafLocation")
    @Expose
    private Boolean isLeafLocation;
    @SerializedName("locationType")
    @Expose
    private Integer locationType;
    @SerializedName("timeZoneId")
    @Expose
    private String timeZoneId;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("isActive")
    @Expose
    private Boolean isActive;

    private int level = 1;

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getParentLocationId() {
        return parentLocationId;
    }

    public void setParentLocationId(Integer parentLocationId) {
        this.parentLocationId = parentLocationId;
    }

    public Boolean getIsLeafLocation() {
        return isLeafLocation;
    }

    public void setIsLeafLocation(Boolean isLeafLocation) {
        this.isLeafLocation = isLeafLocation;
    }

    public Integer getLocationType() {
        return locationType;
    }

    public void setLocationType(Integer locationType) {
        this.locationType = locationType;
    }

    public String getTimeZoneId() {
        return timeZoneId;
    }

    public void setTimeZoneId(String timeZoneId) {
        this.timeZoneId = timeZoneId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }


}
