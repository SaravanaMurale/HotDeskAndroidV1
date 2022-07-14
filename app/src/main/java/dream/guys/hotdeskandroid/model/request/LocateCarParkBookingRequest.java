package dream.guys.hotdeskandroid.model.request;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LocateCarParkBookingRequest {

    @SerializedName("parkingSlotId")
    private int parkingSlotId;

    @SerializedName("changesets")
    private List<CarParkingChangeSets> carParkingChangeSetsList;

    @SerializedName("deletedIds")
    private List<CarParkingDeleteIds> deleteIdsList;

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
        private int id;
        @SerializedName("date")
        private String date;
        @SerializedName("changes")
        private CarParkingChanges carParkingChanges;

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
            private String from;
            @SerializedName("to")
            private String to;
            @SerializedName("comments")
            private String comments;
            @SerializedName("bookedForUser")
            private int bookedForUser;
            @SerializedName("vehicleRegNumber")
            private String vehicleRegNumber;

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
