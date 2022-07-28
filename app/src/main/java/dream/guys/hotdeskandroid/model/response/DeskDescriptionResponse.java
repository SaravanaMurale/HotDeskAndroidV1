package dream.guys.hotdeskandroid.model.response;

import com.google.gson.annotations.SerializedName;

public class DeskDescriptionResponse {

    @SerializedName("description")
    String deskDescription;

    public String getDeskDescription() {
        return deskDescription;
    }

    public void setDeskDescription(String deskDescription) {
        this.deskDescription = deskDescription;
    }
}
