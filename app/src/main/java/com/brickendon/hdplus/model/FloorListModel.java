package com.brickendon.hdplus.model;

import java.util.ArrayList;

import com.brickendon.hdplus.model.response.DAOTeamMember;

public class FloorListModel {
    private int floorId;
    private String floorName;
    private String deskAvailability;
    ArrayList<DAOTeamMember> daoTeamMembers;
    ArrayList<TeamsMemberListDataModel> daoTeamMembersNew;

    public ArrayList<TeamsMemberListDataModel> getDaoTeamMembersNew() {
        return daoTeamMembersNew;
    }

    public void setDaoTeamMembersNew(ArrayList<TeamsMemberListDataModel> daoTeamMembersNew) {
        this.daoTeamMembersNew = daoTeamMembersNew;
    }

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
