package dream.guys.hotdeskandroid.model.request;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import dream.guys.hotdeskandroid.model.response.CovidQuestionsResponse;

public class CovidAnswerRequest {

    @SerializedName("covidSelfCertificationQuestions")
    List<CovidQuestionsResponse> covidQuestionsResponseList;
    int result;
    int covidCertificationTransportMode;

    public List<CovidQuestionsResponse> getCovidQuestionsResponseList() {
        return covidQuestionsResponseList;
    }

    public void setCovidQuestionsResponseList(List<CovidQuestionsResponse> covidQuestionsResponseList) {
        this.covidQuestionsResponseList = covidQuestionsResponseList;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public int getCovidCertificationTransportMode() {
        return covidCertificationTransportMode;
    }

    public void setCovidCertificationTransportMode(int covidCertificationTransportMode) {
        this.covidCertificationTransportMode = covidCertificationTransportMode;
    }
}
