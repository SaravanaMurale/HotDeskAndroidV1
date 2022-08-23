package dream.guys.hotdeskandroid.model.request;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ReportIssueRequest
{
    @SerializedName("metrics")
    private List<Metric> metrics;
    @SerializedName("feedbackDeskLocationInfo")
    private FeedBack feedbackDeskLocationInfo;
    @SerializedName("comments")
    private String comments;
    @SerializedName("isAnonymous")
    private boolean isAnonymous;

    public boolean isAnonymous()
    {
        return isAnonymous;
    }

    public void setAnonymous(boolean anonymous)
    {
        isAnonymous = anonymous;
    }

    public List<Metric> getMetrics()
    {
        return metrics;
    }

    public void setMetrics(List<Metric> metrics)
    {
        this.metrics = metrics;
    }

    public FeedBack getFeedbackDeskLocationInfo()
    {
        return feedbackDeskLocationInfo;
    }

    public void setFeedbackDeskLocationInfo(FeedBack feedbackDeskLocationInfo)
    {
        this.feedbackDeskLocationInfo = feedbackDeskLocationInfo;
    }

    public String getComments()
    {
        return comments;
    }

    public void setComments(String comments)
    {
        this.comments = comments;
    }

    public static class Metric {
        @SerializedName("score")
        private int score;
        @SerializedName("metric")
        private int metric;
        @SerializedName("comments")
        private String comments;

        public Metric(int score, int metric, String comments)
        {
            this.score = score;
            this.metric = metric;
            this.comments = comments;
        }

        public int getScore()
        {
            return score;
        }

        public void setScore(int score)
        {
            this.score = score;
        }

        public int getMetric()
        {
            return metric;
        }

        public void setMetric(int metric)
        {
            this.metric = metric;
        }

        public String getComments()
        {
            return comments;
        }

        public void setComments(String comments)
        {
            this.comments = comments;
        }
    }

    public static class FeedBack {

        @SerializedName("deskId")
        private int deskId;
        @SerializedName("locationId")
        private int locationId;
        @SerializedName("effectedFrom")
        private String effectedFrom;
        @SerializedName("effectedTo")
        private String effectedTo;

        public int getDeskId()
        {
            return deskId;
        }

        public void setDeskId(int deskId)
        {
            this.deskId = deskId;
        }

        public int getLocationId()
        {
            return locationId;
        }

        public void setLocationId(int locationId)
        {
            this.locationId = locationId;
        }

        public String getEffectedFrom()
        {
            return effectedFrom;
        }

        public void setEffectedFrom(String effectedFrom)
        {
            this.effectedFrom = effectedFrom;
        }

        public String getEffectedTo()
        {
            return effectedTo;
        }

        public void setEffectedTo(String effectedTo)
        {
            this.effectedTo = effectedTo;
        }
    }
}
