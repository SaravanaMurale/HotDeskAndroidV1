package dream.guys.hotdeskandroid.model.response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;

public class BookingListResponse {

    private int teamId;
    private String teamName;
    private int teamMembershipId;
    private ArrayList<DayGroup> dayGroups;
    private ArrayList<Date> workingDays;

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public int getTeamMembershipId() {
        return teamMembershipId;
    }

    public void setTeamMembershipId(int teamMembershipId) {
        this.teamMembershipId = teamMembershipId;
    }

    public ArrayList<DayGroup> getDayGroups() {
        return dayGroups;
    }

    public void setDayGroups(ArrayList<DayGroup> dayGroups) {
        this.dayGroups = dayGroups;
    }

    public ArrayList<Date> getWorkingDays() {
        return workingDays;
    }

    public void setWorkingDays(ArrayList<Date> workingDays) {
        this.workingDays = workingDays;
    }

    public static class DayGroup{
        private Date date;
        private ArrayList<CalendarEntry> calendarEntries;
        private ArrayList<MeetingBooking> meetingBookings;
        private ArrayList<CarParkBooking> carParkBookings;
        private CalendarEntry calendarEntriesModel;
        private MeetingBooking meetingBookingsModel;
        private CarParkBooking carParkBookingsModel;


        // 1--> desk booking 2--> meeting 3--> parking
        private int calDeskStatus;
        //true--> show date false--> show vertical line
        private boolean dateStatus;

        public CalendarEntry getCalendarEntriesModel() {
            return calendarEntriesModel;
        }

        public void setCalendarEntriesModel(CalendarEntry calendarEntriesModel) {
            this.calendarEntriesModel = calendarEntriesModel;
        }

        public MeetingBooking getMeetingBookingsModel() {
            return meetingBookingsModel;
        }

        public void setMeetingBookingsModel(MeetingBooking meetingBookingsModel) {
            this.meetingBookingsModel = meetingBookingsModel;
        }

        public CarParkBooking getCarParkBookingsModel() {
            return carParkBookingsModel;
        }

        public void setCarParkBookingsModel(CarParkBooking carParkBookingsModel) {
            this.carParkBookingsModel = carParkBookingsModel;
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

        public ArrayList<MeetingBooking> getMeetingBookings() {
            return meetingBookings;
        }

        public void setMeetingBookings(ArrayList<MeetingBooking> meetingBookings) {
            this.meetingBookings = meetingBookings;
        }

        public ArrayList<CarParkBooking> getCarParkBookings() {
            return carParkBookings;
        }

        public void setCarParkBookings(ArrayList<CarParkBooking> carParkBookings) {
            this.carParkBookings = carParkBookings;
        }

        public class CalendarEntry{
            private int id;
            private String usageTypeName;
            private String usageTypeAbbreviation;
            private String usageTypeColorCode;
            private Date from;
            @SerializedName("to")
            private Date myto;
            private Date fromUTC;
            private Date toUTC;
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

            public Date getFrom() {
                return from;
            }

            public void setFrom(Date from) {
                this.from = from;
            }

            public Date getMyto() {
                return myto;
            }

            public void setMyto(Date myto) {
                this.myto = myto;
            }

            public Date getFromUTC() {
                return fromUTC;
            }

            public void setFromUTC(Date fromUTC) {
                this.fromUTC = fromUTC;
            }

            public Date getToUTC() {
                return toUTC;
            }

            public void setToUTC(Date toUTC) {
                this.toUTC = toUTC;
            }

            public String getTimeZoneId() {
                return timeZoneId;
            }

            public void setTimeZoneId(String timeZoneId) {
                this.timeZoneId = timeZoneId;
            }

            public Booking getBooking() {
                return booking;
            }

            public void setBooking(Booking booking) {
                this.booking = booking;
            }

            public class Booking{
                private int id;
                private int deskId;
                private int teamDeskId;
                private String deskCode;
                private String deskDescription;
                private Status status;

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

                public Status getStatus() {
                    return status;
                }

                public void setStatus(Status status) {
                    this.status = status;
                }


                public class Status{
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

        public class CarParkBooking{
            private int id;
            private int parkingSlotId;
            private Date from;
            @SerializedName("to")
            private Date myto;
            private Date fromUtc;
            private Date toUtc;
            private String timeZoneId;
            private String vehicleRegNumber;
            private String parkingSlotCode;
            private String requestStatus;

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

            public Date getFrom() {
                return from;
            }

            public void setFrom(Date from) {
                this.from = from;
            }

            public Date getMyto() {
                return myto;
            }

            public void setMyto(Date myto) {
                this.myto = myto;
            }

            public Date getFromUtc() {
                return fromUtc;
            }

            public void setFromUtc(Date fromUtc) {
                this.fromUtc = fromUtc;
            }

            public Date getToUtc() {
                return toUtc;
            }

            public void setToUtc(Date toUtc) {
                this.toUtc = toUtc;
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
        }


        public class MeetingBooking{
            private int id;
            private int meetingRoomId;
            private String meetingRoomName;
            private Date from;
            @SerializedName("to")
            private Date myto;
            private Date fromUtc;
            private Date toUtc;
            private String timeZoneId;
            private String subject;

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

            public Date getFrom() {
                return from;
            }

            public void setFrom(Date from) {
                this.from = from;
            }

            public Date getMyto() {
                return myto;
            }

            public void setMyto(Date myto) {
                this.myto = myto;
            }

            public Date getFromUtc() {
                return fromUtc;
            }

            public void setFromUtc(Date fromUtc) {
                this.fromUtc = fromUtc;
            }

            public Date getToUtc() {
                return toUtc;
            }

            public void setToUtc(Date toUtc) {
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


    }



}
