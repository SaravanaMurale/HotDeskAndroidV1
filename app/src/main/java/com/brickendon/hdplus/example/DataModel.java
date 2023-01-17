package com.brickendon.hdplus.example;

import java.util.ArrayList;

public class DataModel {

    private ArrayList<ValuesPOJO> nestedList;
    private String itemText;
    private boolean isExpandable = true;
    private boolean isChecked;

    public DataModel(ArrayList<ValuesPOJO> itemList, String itemText) {
        this.nestedList = itemList;
        this.itemText = itemText;
        isExpandable = true;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public void setExpandable(boolean expandable) {
        isExpandable = expandable;
    }

    public ArrayList<ValuesPOJO> getNestedList() {
        return nestedList;
    }



    public String getItemText() {
        return itemText;
    }

    public boolean isExpandable() {
        return isExpandable;
    }

}
