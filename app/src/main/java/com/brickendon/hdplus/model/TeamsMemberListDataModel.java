package com.brickendon.hdplus.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import com.brickendon.hdplus.model.response.DAOTeamMember;

public class TeamsMemberListDataModel {


    private boolean isFireStatus;
    private boolean ifFirstAidStatus;

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

    @SerializedName("positionData")
    @Expose
    private int positionData;
    private String teamFromTIme;
    private String teamToTime;
    @SerializedName("lastName")
    @Expose
    private String lastName;
    @SerializedName("profileImageUrl")
    @Expose
    private String profileImageUrl;
    @SerializedName("profileImage")
    @Expose
    private String profileImage;

    DAOTeamMember daoTeamMember;

    private CalendarEntry calendarEntriesModel;

    public CalendarEntry getCalendarEntriesModel() {
        return calendarEntriesModel;
    }

    public DAOTeamMember getDaoTeamMember() {
        return daoTeamMember;
    }

    public void setDaoTeamMember(DAOTeamMember daoTeamMember) {
        this.daoTeamMember = daoTeamMember;
    }

    public void setCalendarEntriesModel(CalendarEntry calendarEntriesModel) {
        this.calendarEntriesModel = calendarEntriesModel;
    }

    public int getPositionData() {
        return positionData;
    }

    public void setPositionData(int positionData) {
        this.positionData = positionData;
    }

    public String getTeamFromTIme() {
        return teamFromTIme;
    }

    public void setTeamFromTIme(String teamFromTIme) {
        this.teamFromTIme = teamFromTIme;
    }

    public String getTeamToTime() {
        return teamToTime;
    }

    public void setTeamToTime(String teamToTime) {
        this.teamToTime = teamToTime;
    }

    public boolean isFireStatus() {
        return isFireStatus;
    }

    public void setFireStatus(boolean fireStatus) {
        isFireStatus = fireStatus;
    }

    public boolean isIfFirstAidStatus() {
        return ifFirstAidStatus;
    }

    public void setIfFirstAidStatus(boolean ifFirstAidStatus) {
        this.ifFirstAidStatus = ifFirstAidStatus;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

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


    public static class DayGroup implements Serializable {
        private Date date;
        private ArrayList<CalendarEntry> calendarEntries;
        private ArrayList<DAOTeamMember.DayGroup.MeetingBooking> meetingBookings;
        private ArrayList<DAOTeamMember.DayGroup.CarParkBooking> carParkBookings;
        private DAOTeamMember.DayGroup.CalendarEntry calendarEntriesModel;
        private DAOTeamMember.DayGroup.MeetingBooking meetingBookingsModel;
        private DAOTeamMember.DayGroup.CarParkBooking carParkBookingsModel;
        public DAOTeamMember.DayGroup.LocationBuildingFloor locationBuildingFloor;

        // 1--> desk booking 2--> meeting 3--> parking
        private int calDeskStatus;
        //true--> show date false--> show vertical line
        private boolean dateStatus;

        public Date getDate() {
            return date;
        }

        public void setDate(Date date) {
            this.date = date;
        }

        public ArrayList<CalendarEntry> getCalendarEntries() {
            return calendarEntries;
        }

        public void setCalendarEntries(ArrayList<CalendarEntry> calendarEntries) {
            this.calendarEntries = calendarEntries;
        }

        public ArrayList<DAOTeamMember.DayGroup.MeetingBooking> getMeetingBookings() {
            return meetingBookings;
        }

        public void setMeetingBookings(ArrayList<DAOTeamMember.DayGroup.MeetingBooking> meetingBookings) {
            this.meetingBookings = meetingBookings;
        }

        public ArrayList<DAOTeamMember.DayGroup.CarParkBooking> getCarParkBookings() {
            return carParkBookings;
        }

        public void setCarParkBookings(ArrayList<DAOTeamMember.DayGroup.CarParkBooking> carParkBookings) {
            this.carParkBookings = carParkBookings;
        }

        public DAOTeamMember.DayGroup.CalendarEntry getCalendarEntriesModel() {
            return calendarEntriesModel;
        }

        public void setCalendarEntriesModel(DAOTeamMember.DayGroup.CalendarEntry calendarEntriesModel) {
            this.calendarEntriesModel = calendarEntriesModel;
        }

        public DAOTeamMember.DayGroup.MeetingBooking getMeetingBookingsModel() {
            return meetingBookingsModel;
        }

        public void setMeetingBookingsModel(DAOTeamMember.DayGroup.MeetingBooking meetingBookingsModel) {
            this.meetingBookingsModel = meetingBookingsModel;
        }

        public DAOTeamMember.DayGroup.CarParkBooking getCarParkBookingsModel() {
            return carParkBookingsModel;
        }

        public void setCarParkBookingsModel(DAOTeamMember.DayGroup.CarParkBooking carParkBookingsModel) {
            this.carParkBookingsModel = carParkBookingsModel;
        }

        public DAOTeamMember.DayGroup.LocationBuildingFloor getLocationBuildingFloor() {
            return locationBuildingFloor;
        }

        public void setLocationBuildingFloor(DAOTeamMember.DayGroup.LocationBuildingFloor locationBuildingFloor) {
            this.locationBuildingFloor = locationBuildingFloor;
        }

        public int getCalDeskStatus() {
            return calDeskStatus;
        }

        public void setCalDeskStatus(int calDeskStatus) {
            this.calDeskStatus = calDeskStatus;
        }

        public boolean isDateStatus() {
            return dateStatus;
        }

        public void setDateStatus(boolean dateStatus) {
            this.dateStatus = dateStatus;
        }


    }
    public static class CalendarEntry implements Serializable{
        private int id;
        private String usageTypeName;
        private String usageTypeAbbreviation;
        private String usageTypeColorCode;
        private String from;
        @SerializedName("to")
        private String myto;
        private String fromUTC;
        private String toUTC;
        private String timeZoneId;
        private Booking booking;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUsageTypeName() {
            return usageTypeName;
        }

        public void setUsageTypeName(String usageTypeName) {
            this.usageTypeName = usageTypeName;
        }

        public String getUsageTypeAbbreviation() {
            return usageTypeAbbreviation;
        }

        public void setUsageTypeAbbreviation(String usageTypeAbbreviation) {
            this.usageTypeAbbreviation = usageTypeAbbreviation;
        }

        public String getUsageTypeColorCode() {
            return usageTypeColorCode;
        }

        public void setUsageTypeColorCode(String usageTypeColorCode) {
            this.usageTypeColorCode = usageTypeColorCode;
        }

        public String getFrom() {
            return from;
        }

        public void setFrom(String from) {
            this.from = from;
        }

        public String getMyto() {
            return myto;
        }

        public void setMyto(String myto) {
            this.myto = myto;
        }

        public String getFromUTC() {
            return fromUTC;
        }

        public void setFromUTC(String fromUTC) {
            this.fromUTC = fromUTC;
        }

        public String getToUTC() {
            return toUTC;
        }

        public void setToUTC(String toUTC) {
            this.toUTC = toUTC;
        }

        public String getTimeZoneId() {
            return timeZoneId;
        }

        public void setTimeZoneId(String timeZoneId) {
            this.timeZoneId = timeZoneId;
        }

        public CalendarEntry.Booking getBooking() {
            return booking;
        }

        public void setBooking(CalendarEntry.Booking booking) {
            this.booking = booking;
        }

        public static class Booking implements Serializable{
            private int id;
            private int deskId;
            private int teamDeskId;
            private String deskCode;
            private String deskDescription;
            private CalendarEntry.Booking.Status status;
            private LocationBuildingFloor locationBuildingFloor;

            public LocationBuildingFloor getLocationBuildingFloor() {
                return locationBuildingFloor;
            }

            public void setLocationBuildingFloor(LocationBuildingFloor locationBuildingFloor) {
                this.locationBuildingFloor = locationBuildingFloor;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getDeskId() {
                return deskId;
            }

            public void setDeskId(int deskId) {
                this.deskId = deskId;
            }

            public int getTeamDeskId() {
                return teamDeskId;
            }

            public void setTeamDeskId(int teamDeskId) {
                this.teamDeskId = teamDeskId;
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

            public Booking.Status getStatus() {
                return status;
            }

            public void setStatus(Booking.Status status) {
                this.status = status;
            }


            public static class Status implements Serializable{
                private int id;
                private String name;
                private String abbreviation;
                private String colorCode;
                private String description;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getAbbreviation() {
                    return abbreviation;
                }

                public void setAbbreviation(String abbreviation) {
                    this.abbreviation = abbreviation;
                }

                public String getColorCode() {
                    return colorCode;
                }

                public void setColorCode(String colorCode) {
                    this.colorCode = colorCode;
                }

                public String getDescription() {
                    return description;
                }

                public void setDescription(String description) {
                    this.description = description;
                }
            }
        }

    }

    public class CarParkBooking implements Serializable{
        private int id;
        private int parkingSlotId;
        private String from;
        @SerializedName("to")
        private String myto;
        private String fromUtc;
        private String toUtc;
        private String timeZoneId;
        private String vehicleRegNumber;
        private String parkingSlotCode;
        private String requestStatus;
        private DAOTeamMember.DayGroup.LocationBuildingFloor locationBuildingFloor;

        public DAOTeamMember.DayGroup.LocationBuildingFloor getLocationBuildingFloor() {
            return locationBuildingFloor;
        }

        public void setLocationBuildingFloor(DAOTeamMember.DayGroup.LocationBuildingFloor locationBuildingFloor) {
            this.locationBuildingFloor = locationBuildingFloor;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getParkingSlotId() {
            return parkingSlotId;
        }

        public void setParkingSlotId(int parkingSlotId) {
            this.parkingSlotId = parkingSlotId;
        }


        public String getTimeZoneId() {
            return timeZoneId;
        }

        public void setTimeZoneId(String timeZoneId) {
            this.timeZoneId = timeZoneId;
        }

        public String getVehicleRegNumber() {
            return vehicleRegNumber;
        }

        public void setVehicleRegNumber(String vehicleRegNumber) {
            this.vehicleRegNumber = vehicleRegNumber;
        }

        public String getParkingSlotCode() {
            return parkingSlotCode;
        }

        public void setParkingSlotCode(String parkingSlotCode) {
            this.parkingSlotCode = parkingSlotCode;
        }

        public String getRequestStatus() {
            return requestStatus;
        }

        public void setRequestStatus(String requestStatus) {
            this.requestStatus = requestStatus;
        }

        public String getFrom() {
            return from;
        }

        public void setFrom(String from) {
            this.from = from;
        }

        public String getMyto() {
            return myto;
        }

        public void setMyto(String myto) {
            this.myto = myto;
        }

        public String getFromUtc() {
            return fromUtc;
        }

        public void setFromUtc(String fromUtc) {
            this.fromUtc = fromUtc;
        }

        public String getToUtc() {
            return toUtc;
        }

        public void setToUtc(String toUtc) {
            this.toUtc = toUtc;
        }
    }


    public class MeetingBooking implements Serializable{
        private int id;
        private int meetingRoomId;
        private String meetingRoomName;
        private String  from;
        @SerializedName("to")
        private String myto;
        private String fromUtc;
        private String toUtc;
        private String timeZoneId;
        private String subject;
        private DAOTeamMember.DayGroup.LocationBuildingFloor locationBuildingFloor;

        public DAOTeamMember.DayGroup.LocationBuildingFloor getLocationBuildingFloor() {
            return locationBuildingFloor;
        }

        public void setLocationBuildingFloor(DAOTeamMember.DayGroup.LocationBuildingFloor locationBuildingFloor) {
            this.locationBuildingFloor = locationBuildingFloor;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getMeetingRoomId() {
            return meetingRoomId;
        }

        public void setMeetingRoomId(int meetingRoomId) {
            this.meetingRoomId = meetingRoomId;
        }

        public String getMeetingRoomName() {
            return meetingRoomName;
        }

        public void setMeetingRoomName(String meetingRoomName) {
            this.meetingRoomName = meetingRoomName;
        }

        public String getFrom() {
            return from;
        }

        public void setFrom(String from) {
            this.from = from;
        }

        public String getMyto() {
            return myto;
        }

        public void setMyto(String myto) {
            this.myto = myto;
        }

        public String getFromUtc() {
            return fromUtc;
        }

        public void setFromUtc(String fromUtc) {
            this.fromUtc = fromUtc;
        }

        public String getToUtc() {
            return toUtc;
        }

        public void setToUtc(String toUtc) {
            this.toUtc = toUtc;
        }

        public String getTimeZoneId() {
            return timeZoneId;
        }

        public void setTimeZoneId(String timeZoneId) {
            this.timeZoneId = timeZoneId;
        }

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }
    }


    public static class LocationBuildingFloor implements Serializable{
        public int floorID;
        public String fLoorName;
        public int buildingID;
        public String buildingName;

        public int getFloorID() {
            return floorID;
        }

        public void setFloorID(int floorID) {
            this.floorID = floorID;
        }

        public String getfLoorName() {
            return fLoorName;
        }

        public void setfLoorName(String fLoorName) {
            this.fLoorName = fLoorName;
        }

        public int getBuildingID() {
            return buildingID;
        }

        public void setBuildingID(int buildingID) {
            this.buildingID = buildingID;
        }

        public String getBuildingName() {
            return buildingName;
        }

        public void setBuildingName(String buildingName) {
            this.buildingName = buildingName;
        }
    }
}



