package com.brickendon.hdplus.datasource;

import com.brickendon.hdplus.model.TeamsMemberListDataModel;

public class ExpandItem extends TeamsExpandListModel{
    private int id;
    private String name;
    private TeamsMemberListDataModel teamsMemberListDataModel;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public TeamsMemberListDataModel getTeamsMemberListDataModel() {
        return teamsMemberListDataModel;
    }

    public void setTeamsMemberListDataModel(TeamsMemberListDataModel teamsMemberListDataModel) {
        this.teamsMemberListDataModel = teamsMemberListDataModel;
    }
}
