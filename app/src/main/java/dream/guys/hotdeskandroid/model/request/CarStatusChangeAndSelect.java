package dream.guys.hotdeskandroid.model.request;

public class CarStatusChangeAndSelect {

    int id;
    String carName;
    String code;
    int status;

    public CarStatusChangeAndSelect(int id, String carName, String code, int status) {
        this.id = id;
        this.carName = carName;
        this.code = code;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
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
