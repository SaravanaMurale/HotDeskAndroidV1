package dream.guys.hotdeskandroid.model.response;

import com.google.gson.annotations.SerializedName;

import retrofit2.Response;

public class BaseResponse {
    @SerializedName("resultCode")
    private String resultCode;

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    private Response response;



    public Response getResponse() {
        return response;


    }

    public void setResponse(Response response) {
        this.response = response;
    }
}
