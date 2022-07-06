package dream.guys.hotdeskandroid.model.response;

import com.google.gson.annotations.SerializedName;

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
    //String items;
    @SerializedName("locationItemLayout")
    LocationItemLayout locationItemLayout;
    @SerializedName("supportZoneLayoutItems")
    List<SupportZoneLayoutItems> supportZoneLayoutItemsList;
    //String locationOptions;
    @SerializedName("backGroundImage")
    BackGroundImage backGroundImage;

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
