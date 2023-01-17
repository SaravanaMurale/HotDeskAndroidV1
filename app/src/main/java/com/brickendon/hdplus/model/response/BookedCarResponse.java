package com.brickendon.hdplus.model.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BookedCarResponse {

    @SerializedName("carParkBookings")
    List<CarParkBookings> carParkBookingsList;

    public List<CarParkBookings> getCarParkBookingsList() {
        return carParkBookingsList;
    }

    public void setCarParkBookingsList(List<CarParkBookings> carParkBookingsList) {
        this.carParkBookingsList = carParkBookingsList;
    }

    public class CarParkBookings {

        int id;
        int parkingSlotId;
        String date;
        String from;
        String to;

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

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getFrom() {
            return from;
        }

        public void setFrom(String from) {
            this.from = from;
        }

        public String getTo() {
            return to;
        }

        public void setTo(String to) {
            this.to = to;
        }
    }


}
