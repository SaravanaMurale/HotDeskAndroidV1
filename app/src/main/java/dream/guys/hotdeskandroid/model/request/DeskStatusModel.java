package dream.guys.hotdeskandroid.model.request;

public class DeskStatusModel {

    String key;
    int id;
    String code;
    //Desk,car,meeting room status
    int status;

    //0-unavaliable
    //1-avaliable
    //2-BookedForMe
    //3-BookedForOther
    //4-BookingRequest

    public DeskStatusModel() {
    }

    public DeskStatusModel(String key, int id, String code, int status) {
        this.key = key;
        this.id = id;
        this.code = code;
        this.status = status;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
