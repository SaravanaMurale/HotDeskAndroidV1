package dream.guys.hotdeskandroid.model.request;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import dream.guys.hotdeskandroid.model.response.QuestionListResponse;


public class QuestionListRequest
{
    private FromDetails FormDetails;
    private String template;
    private String location;
    private String assessmentDate;


    public FromDetails getFormDetails()
    {
        return FormDetails;
    }

    public void setFormDetails(FromDetails formDetails)
    {
        FormDetails = formDetails;
    }

    public String getTemplate()
    {
        return template;
    }

    public void setTemplate(String template)
    {
        this.template = template;
    }

    public String getLocation()
    {
        return location;
    }

    public void setLocation(String location)
    {
        this.location = location;
    }

    public String getAssessmentDate()
    {
        return assessmentDate;
    }

    public void setAssessmentDate(String assessmentDate)
    {
        this.assessmentDate = assessmentDate;
    }

    public static class FromDetails{
        private String comment;
        private String deskId;
        private String locationId;
        private List<QuestionListResponse> questions;

        public String getComment()
        {
            return comment;
        }

        public void setComment(String comment)
        {
            this.comment = comment;
        }

        public String getDeskId()
        {
            return deskId;
        }

        public void setDeskId(String deskId)
        {
            this.deskId = deskId;
        }

        public String getLocationId()
        {
            return locationId;
        }

        public void setLocationId(String locationId)
        {
            this.locationId = locationId;
        }

        public List<QuestionListResponse> getQuestions()
        {
            return questions;
        }

        public void setQuestions(List<QuestionListResponse> questions)
        {
            this.questions = questions;
        }

        /*public class Questions{
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
    }*/

    }


}
