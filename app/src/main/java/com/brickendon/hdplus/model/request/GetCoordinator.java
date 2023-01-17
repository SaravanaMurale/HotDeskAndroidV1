package com.brickendon.hdplus.model.request;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetCoordinator {

    List<String> deskId;
    private String a,b,c,d;

    public void setCoordination(List<String> deskId){
        this.deskId=deskId;

        for (int i = 0; i <deskId.size() ; i++) {
deskId.get(i);
        }
    }



    @SerializedName("items")
    private Items items;

    public class Items{

        @SerializedName("19_3")
        String sdfedfd;




    }




}
