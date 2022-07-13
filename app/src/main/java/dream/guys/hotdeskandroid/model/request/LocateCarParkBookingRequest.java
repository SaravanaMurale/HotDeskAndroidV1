package dream.guys.hotdeskandroid.model.request;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LocateCarParkBookingRequest {

    @SerializedName("parkingSlotId")
    int parkingSlotId;

    @SerializedName("changesets")
    List<CarParkingChangeSets> carParkingChangeSetsList;

    @SerializedName("deletedIds")
    List<CarParkingDeleteIds> deleteIdsList;

    public int getParkingSlotId() {
        return parkingSlotId;
    }

    public void setParkingSlotId(int parkingSlotId) {
        this.parkingSlotId = parkingSlotId;
    }

    public List<CarParkingChangeSets> getCarParkingChangeSetsList() {
        return carParkingChangeSetsList;
    }

    public void setCarParkingChangeSetsList(List<CarParkingChangeSets> carParkingChangeSetsList) {
        this.carParkingChangeSetsList = carParkingChangeSetsList;
    }

    public List<CarParkingDeleteIds> getDeleteIdsList() {
        return deleteIdsList;
    }

    public void setDeleteIdsList(List<CarParkingDeleteIds> deleteIdsList) {
        this.deleteIdsList = deleteIdsList;
    }

    public class CarParkingChangeSets{

        @SerializedName("id")
        int id;
        @SerializedName("date")
        String date;
        @SerializedName("changes")
        CarParkingChanges carParkingChanges;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public CarParkingChanges getCarParkingChanges() {
            return carParkingChanges;
        }

        public void setCarParkingChanges(CarParkingChanges carParkingChanges) {
            this.carParkingChanges = carParkingChanges;
        }

        public class CarParkingChanges {

            @SerializedName("from")
            String from;
            @SerializedName("to")
            String to;
            @SerializedName("comments")
            String comments;
            @SerializedName("bookedForUser")
            int bookedForUser;
            @SerializedName("vehicleRegNumber")
            String vehicleRegNumber;

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

            public String getComments() {
                return comments;
            }

            public void setComments(String comments) {
                this.comments = comments;
            }

            public int getBookedForUser() {
                return bookedForUser;
            }

            public void setBookedForUser(int bookedForUser) {
                this.bookedForUser = bookedForUser;
            }

            public String getVehicleRegNumber() {
                return vehicleRegNumber;
            }

            public void setVehicleRegNumber(String vehicleRegNumber) {
                this.vehicleRegNumber = vehicleRegNumber;
            }
        }





    }

    public class CarParkingDeleteIds{


    }
}
