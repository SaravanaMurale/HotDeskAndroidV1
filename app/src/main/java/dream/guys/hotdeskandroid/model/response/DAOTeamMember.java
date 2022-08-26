package dream.guys.hotdeskandroid.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class DAOTeamMember implements Serializable {

    @SerializedName("userId")
    @Expose
    private Integer userId;
    @SerializedName("teamId")
    @Expose
    private Integer teamId;
    @SerializedName("teamMembershipId")
    @Expose
    private Integer teamMembershipId;
    @SerializedName("firstName")
    @Expose
    private String firstName;
    @SerializedName("lastName")
    @Expose
    private String lastName;
    @SerializedName("dayGroups")
    @Expose
    private ArrayList<Object> dayGroups = null;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getTeamId() {
        return teamId;
    }

    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }

    public Integer getTeamMembershipId() {
        return teamMembershipId;
    }

    public void setTeamMembershipId(Integer teamMembershipId) {
        this.teamMembershipId = teamMembershipId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public ArrayList<Object> getDayGroups() {
        return dayGroups;
    }

    public void setDayGroups(ArrayList<Object> dayGroups) {
        this.dayGroups = dayGroups;
    }

}


