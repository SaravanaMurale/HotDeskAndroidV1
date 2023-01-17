package com.brickendon.hdplus.model.request;

import com.google.gson.annotations.SerializedName;

public class PersonalHelpRequest
{

    @SerializedName("description")
    private String description;
    @SerializedName("isAnonymous")
    private boolean isAnonymous;

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public boolean isAnonymous()
    {
        return isAnonymous;
    }

    public void setAnonymous(boolean anonymous)
    {
        isAnonymous = anonymous;
    }
}
