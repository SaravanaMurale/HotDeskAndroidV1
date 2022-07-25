package dream.guys.hotdeskandroid.model.request;

public class CarParkingStatusModel {

    int id;
    String key;
    int status;

    //0-unavaliable
    //1-avaliable
    //2-BookedForMe
    //3-BookedForOther
    //4-BookingRequest


    public CarParkingStatusModel() {
    }

    public CarParkingStatusModel(int id,String key, int status) {
        this.id=id;
        this.key = key;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
