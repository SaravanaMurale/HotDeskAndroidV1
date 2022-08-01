package dream.guys.hotdeskandroid.model.request;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LocateCarParkEditRequest {

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

    public class CarParkingChangeSets {

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

            @SerializedName("vehicleRegNumber")
            private String vehicleRegNumber;

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