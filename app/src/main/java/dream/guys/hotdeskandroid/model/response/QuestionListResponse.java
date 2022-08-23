package dream.guys.hotdeskandroid.model.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class QuestionListResponse
{

    private int id;
    private String categoryName;
    @SerializedName("workstationChecklistQuestions")
    List<ChecklistQuestions> checklistQuestions;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getCategoryName()
    {
        return categoryName;
    }

    public void setCategoryName(String categoryName)
    {
        this.categoryName = categoryName;
    }

    public List<ChecklistQuestions> getChecklistQuestions()
    {
        return checklistQuestions;
    }

    public void setChecklistQuestions(List<ChecklistQuestions> checklistQuestions)
    {
        this.checklistQuestions = checklistQuestions;
    }

    public class ChecklistQuestions
    {
        private int id;
        private String riskFactors;
        private String thingsToConsider;
        private boolean answer;
        private String actionToTake;
        private int categoryId;
        private String categoryName;


        public int getId()
        {
            return id;
        }

        public void setId(int id)
        {
            this.id = id;
        }

        public String getRiskFactors()
        {
            return riskFactors;
        }

        public void setRiskFactors(String riskFactors)
        {
            this.riskFactors = riskFactors;
        }

        public String getThingsToConsider()
        {
            return thingsToConsider;
        }

        public void setThingsToConsider(String thingsToConsider)
        {
            this.thingsToConsider = thingsToConsider;
        }

        public boolean isAnswer()
        {
            return answer;
        }

        public void setAnswer(boolean answer)
        {
            this.answer = answer;
        }

        public String getActionToTake()
        {
            return actionToTake;
        }

        public void setActionToTake(String actionToTake)
        {
            this.actionToTake = actionToTake;
        }

        public int getCategoryId()
        {
            return categoryId;
        }

        public void setCategoryId(int categoryId)
        {
            this.categoryId = categoryId;
        }

        public String getCategoryName()
        {
            return categoryName;
        }

        public void setCategoryName(String categoryName)
        {
            this.categoryName = categoryName;
        }

    }
}
