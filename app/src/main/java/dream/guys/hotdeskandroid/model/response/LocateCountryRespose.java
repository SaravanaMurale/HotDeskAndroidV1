package dream.guys.hotdeskandroid.model.response;

import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

import java.util.List;

public class LocateCountryRespose {

    @SerializedName("id")
    int locateCountryId;
    String name;
    String description;
    int parentLocationId;
    boolean active;
    boolean isLeafLocation;
    int locationType;
    String timeZoneId;
    //String coordinates;
    @SerializedName("items")
    JSONObject items;
    @SerializedName("locationItemLayout")
    LocationItemLayout locationItemLayout;
    @SerializedName("supportZoneLayoutItems")
    List<SupportZoneLayoutItems> supportZoneLayoutItemsList;
    //String locationOptions;
    @SerializedName("backGroundImage")
    BackGroundImage backGroundImage;

    public JSONObject getItems() {
        return items;
    }

    public void setItems(JSONObject items) {
        this.items = items;
    }

    public int getLocateCountryId() {
        return locateCountryId;
    }

    public void setLocateCountryId(int locateCountryId) {
        this.locateCountryId = locateCountryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getParentLocationId() {
        return parentLocationId;
    }

    public void setParentLocationId(int parentLocationId) {
        this.parentLocationId = parentLocationId;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isLeafLocation() {
        return isLeafLocation;
    }

    public void setLeafLocation(boolean leafLocation) {
        isLeafLocation = leafLocation;
    }

    public int getLocationType() {
        return locationType;
    }

    public void setLocationType(int locationType) {
        this.locationType = locationType;
    }

    public String getTimeZoneId() {
        return timeZoneId;
    }

    public void setTimeZoneId(String timeZoneId) {
        this.timeZoneId = timeZoneId;
    }


    public LocationItemLayout getLocationItemLayout() {
        return locationItemLayout;
    }

    public void setLocationItemLayout(LocationItemLayout locationItemLayout) {
        this.locationItemLayout = locationItemLayout;
    }

    public BackGroundImage getBackGroundImage() {
        return backGroundImage;
    }

    public void setBackGroundImage(BackGroundImage backGroundImage) {
        this.backGroundImage = backGroundImage;
    }

    public List<SupportZoneLayoutItems> getSupportZoneLayoutItemsList() {
        return supportZoneLayoutItemsList;
    }

    public void setSupportZoneLayoutItemsList(List<SupportZoneLayoutItems> supportZoneLayoutItemsList) {
        this.supportZoneLayoutItemsList = supportZoneLayoutItemsList;
    }


   /* public  class Items{




















        @SerializedName("19_3")
        List<Integer> deskPos0;

        @SerializedName("18_3")
        List<Integer> deskPos1;

        @SerializedName("17_3")
        List<Integer> deskPos2;

        @SerializedName("16_3")
        List<Integer> deskPos3;

        @SerializedName("15_3")
        List<Integer> deskPos4;

        @SerializedName("14_3")
        List<Integer> deskPos5;

        @SerializedName("13_3")
        List<Integer> deskPos6;
        @SerializedName("12_3")
        List<Integer> deskPos7;
        @SerializedName("1_3")
        List<Integer> deskPos8;
        @SerializedName("10_3")
        List<Integer> deskPos9;
        @SerializedName("9_3")
        List<Integer> deskPos10;
        @SerializedName("8_3")
        List<Integer> deskPos11;
        @SerializedName("7_3")
        List<Integer> deskPos12;
        @SerializedName("6_3")
        List<Integer> deskPos13;
        @SerializedName("5_3")
        List<Integer> deskPos14;
        @SerializedName("4_3")
        List<Integer> deskPos15;
        @SerializedName("3_3")
        List<Integer> deskPos16;
        @SerializedName("2_3")
        List<Integer> deskPos17;
        @SerializedName("20_3")
        List<Integer> deskPos18;
        @SerializedName("11_3")
        List<Integer> deskPos19;

        public List<Integer> getDeskPos0() {
            return deskPos0;
        }

        public void setDeskPos0(List<Integer> deskPos0) {
            this.deskPos0 = deskPos0;
        }

        public List<Integer> getDeskPos1() {
            return deskPos1;
        }

        public void setDeskPos1(List<Integer> deskPos1) {
            this.deskPos1 = deskPos1;
        }

        public List<Integer> getDeskPos2() {
            return deskPos2;
        }

        public void setDeskPos2(List<Integer> deskPos2) {
            this.deskPos2 = deskPos2;
        }

        public List<Integer> getDeskPos3() {
            return deskPos3;
        }

        public void setDeskPos3(List<Integer> deskPos3) {
            this.deskPos3 = deskPos3;
        }

        public List<Integer> getDeskPos4() {
            return deskPos4;
        }

        public void setDeskPos4(List<Integer> deskPos4) {
            this.deskPos4 = deskPos4;
        }

        public List<Integer> getDeskPos5() {
            return deskPos5;
        }

        public void setDeskPos5(List<Integer> deskPos5) {
            this.deskPos5 = deskPos5;
        }

        public List<Integer> getDeskPos6() {
            return deskPos6;
        }

        public void setDeskPos6(List<Integer> deskPos6) {
            this.deskPos6 = deskPos6;
        }

        public List<Integer> getDeskPos7() {
            return deskPos7;
        }

        public void setDeskPos7(List<Integer> deskPos7) {
            this.deskPos7 = deskPos7;
        }

        public List<Integer> getDeskPos8() {
            return deskPos8;
        }

        public void setDeskPos8(List<Integer> deskPos8) {
            this.deskPos8 = deskPos8;
        }

        public List<Integer> getDeskPos9() {
            return deskPos9;
        }

        public void setDeskPos9(List<Integer> deskPos9) {
            this.deskPos9 = deskPos9;
        }

        public List<Integer> getDeskPos10() {
            return deskPos10;
        }

        public void setDeskPos10(List<Integer> deskPos10) {
            this.deskPos10 = deskPos10;
        }

        public List<Integer> getDeskPos11() {
            return deskPos11;
        }

        public void setDeskPos11(List<Integer> deskPos11) {
            this.deskPos11 = deskPos11;
        }

        public List<Integer> getDeskPos12() {
            return deskPos12;
        }

        public void setDeskPos12(List<Integer> deskPos12) {
            this.deskPos12 = deskPos12;
        }

        public List<Integer> getDeskPos13() {
            return deskPos13;
        }

        public void setDeskPos13(List<Integer> deskPos13) {
            this.deskPos13 = deskPos13;
        }

        public List<Integer> getDeskPos14() {
            return deskPos14;
        }

        public void setDeskPos14(List<Integer> deskPos14) {
            this.deskPos14 = deskPos14;
        }

        public List<Integer> getDeskPos15() {
            return deskPos15;
        }

        public void setDeskPos15(List<Integer> deskPos15) {
            this.deskPos15 = deskPos15;
        }

        public List<Integer> getDeskPos16() {
            return deskPos16;
        }

        public void setDeskPos16(List<Integer> deskPos16) {
            this.deskPos16 = deskPos16;
        }

        public List<Integer> getDeskPos17() {
            return deskPos17;
        }

        public void setDeskPos17(List<Integer> deskPos17) {
            this.deskPos17 = deskPos17;
        }

        public List<Integer> getDeskPos18() {
            return deskPos18;
        }

        public void setDeskPos18(List<Integer> deskPos18) {
            this.deskPos18 = deskPos18;
        }

        public List<Integer> getDeskPos19() {
            return deskPos19;
        }

        public void setDeskPos19(List<Integer> deskPos19) {
            this.deskPos19 = deskPos19;
        }
    }
*/

    public class LocationItemLayout {

        @SerializedName("desks")
        List<Desks> desksList;

        @SerializedName("meetingRooms")
        List<MeetingRooms> meetingRoomsList;

        public List<Desks> getDesks() {
            return desksList;
        }

        public void setDesks(List<Desks> desks) {
            this.desksList = desks;
        }

        public List<MeetingRooms> getMeetingRoomsList() {
            return meetingRoomsList;
        }

        public void setMeetingRoomsList(List<MeetingRooms> meetingRoomsList) {
            this.meetingRoomsList = meetingRoomsList;
        }

        public class Desks {

            @SerializedName("id")
            private int desksId;
            @SerializedName("code")
            private String deskCode;
            @SerializedName("description")
            private String deskDescription;

            public int getDesksId() {
                return desksId;
            }

            public void setDesksId(int desksId) {
                this.desksId = desksId;
            }

            public String getDeskCode() {
                return deskCode;
            }

            public void setDeskCode(String deskCode) {
                this.deskCode = deskCode;
            }

            public String getDeskDescription() {
                return deskDescription;
            }

            public void setDeskDescription(String deskDescription) {
                this.deskDescription = deskDescription;
            }
        }

        public class MeetingRooms {

            @SerializedName("id")
            private int meetingRoomId;
            @SerializedName("code")
            private String meetingRoomCode;
            @SerializedName("description")
            private String meetingRoomDesc;

            public int getMeetingRoomId() {
                return meetingRoomId;
            }

            public void setMeetingRoomId(int meetingRoomId) {
                this.meetingRoomId = meetingRoomId;
            }

            public String getMeetingRoomCode() {
                return meetingRoomCode;
            }

            public void setMeetingRoomCode(String meetingRoomCode) {
                this.meetingRoomCode = meetingRoomCode;
            }

            public String getMeetingRoomDesc() {
                return meetingRoomDesc;
            }

            public void setMeetingRoomDesc(String meetingRoomDesc) {
                this.meetingRoomDesc = meetingRoomDesc;
            }
        }

    }

    public class SupportZoneLayoutItems {

    }

    public class BackGroundImage {

        int height;
        int width;

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }
    }


}
