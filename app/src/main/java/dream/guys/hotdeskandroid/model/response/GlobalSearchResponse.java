package dream.guys.hotdeskandroid.model.response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GlobalSearchResponse {
    @SerializedName("teamDeskAvailabilities")

    List<Results> results;
}
