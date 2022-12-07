package dream.guys.hotdeskandroid.model.request;

public class DeskStatusChangeAndSelect {

    int id;
    String deskName;
    String code;
    int status;

    public DeskStatusChangeAndSelect(int id, String deskName, String code, int status) {
        this.id = id;
        this.deskName = deskName;
        this.code = code;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDeskName() {
        return deskName;
    }

    public void setDeskName(String deskName) {
        this.deskName = deskName;
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
