package dream.guys.hotdeskandroid.model.response;

import com.google.gson.annotations.SerializedName;

public class CovidQuestionsResponse {

    int number;
    String question;
    boolean answer;
    @SerializedName("language")
    String language;
    boolean shouldPositiveAnswerTriggerFlow;
    int covidCertificationAnswerType;

    public CovidQuestionsResponse(int number, String question, boolean answer, String language, boolean shouldPositiveAnswerTriggerFlow, int covidCertificationAnswerType) {
        this.number = number;
        this.question = question;
        this.answer = answer;
        this.language = language;
        this.shouldPositiveAnswerTriggerFlow = shouldPositiveAnswerTriggerFlow;
        this.covidCertificationAnswerType = covidCertificationAnswerType;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public boolean isAnswer() {
        return answer;
    }

    public void setAnswer(boolean answer) {
        this.answer = answer;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public boolean isShouldPositiveAnswerTriggerFlow() {
        return shouldPositiveAnswerTriggerFlow;
    }

    public void setShouldPositiveAnswerTriggerFlow(boolean shouldPositiveAnswerTriggerFlow) {
        this.shouldPositiveAnswerTriggerFlow = shouldPositiveAnswerTriggerFlow;
    }

    public int getCovidCertificationAnswerType() {
        return covidCertificationAnswerType;
    }

    public void setCovidCertificationAnswerType(int covidCertificationAnswerType) {
        this.covidCertificationAnswerType = covidCertificationAnswerType;
    }
}
