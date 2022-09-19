package dream.guys.hotdeskandroid.model;

import java.util.ArrayList;

import dream.guys.hotdeskandroid.model.response.DAOTeamMember;

public class FloorListModel {
    private int floorId;
    private String floorName;
    private String deskAvailability;
    ArrayList<DAOTeamMember> daoTeamMembers;


    public String getDeskAvailability() {
        return deskAvailability;
    }

    public void setDeskAvailability(String deskAvailability) {
        this.deskAvailability = deskAvailability;
    }

    public String getFloorName() {
        return floorName;
    }

    public void setFloorName(String floorName) {
        this.floorName = floorName;
    }

    public int getFloorId() {
        return floorId;
    }

    public void setFloorId(int floorId) {
        this.floorId = floorId;
    }


    public ArrayList<DAOTeamMember> getDaoTeamMembers() {
        return daoTeamMembers;
    }

    public void setDaoTeamMembers(ArrayList<DAOTeamMember> daoTeamMembers) {
        this.daoTeamMembers = daoTeamMembers;
    }
}
