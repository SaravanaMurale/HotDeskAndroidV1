package com.brickendon.hdplus.model.request;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import com.brickendon.hdplus.model.response.CovidQuestionsResponse;

public class CovidAnswerRequest {

    @SerializedName("covidSelfCertificationQuestions")
    List<CovidQuestionsResponse> covidQuestionsResponseList;
    int result;
    int covidCertificationTransportMode;
    @SerializedName("template")
    String template;
    @SerializedName("localDate")
    String localDate;

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getLocalDate() {
        return localDate;
    }

    public void setLocalDate(String localDate) {
        this.localDate = localDate;
    }

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
