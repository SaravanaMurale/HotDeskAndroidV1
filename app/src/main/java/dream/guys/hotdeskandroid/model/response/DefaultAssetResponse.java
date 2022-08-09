package dream.guys.hotdeskandroid.model.response;

public class DefaultAssetResponse {

    int id;
    int deskId;
    int teamId;
    String code;

    public DefaultAssetResponse(int id, int deskId, int teamId, String code) {
        this.id = id;
        this.deskId = deskId;
        this.teamId = teamId;
        this.code = code;
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

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
