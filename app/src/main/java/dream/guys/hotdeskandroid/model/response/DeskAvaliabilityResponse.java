package dream.guys.hotdeskandroid.model.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DeskAvaliabilityResponse {

    @SerializedName("locationDesks")
    List<LocationDesks> locationDesksList;

    public List<LocationDesks> getLocationDesksList() {
        return locationDesksList;
    }

    public void setLocationDesksList(List<LocationDesks> locationDesksList) {
        this.locationDesksList = locationDesksList;
    }

    public class LocationDesks{

        private int id;
        private String code;
        private boolean isTeamDesk;

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

        public boolean isTeamDesk() {
            return isTeamDesk;
        }

        public void setTeamDesk(boolean teamDesk) {
            isTeamDesk = teamDesk;
        }
    }
}
