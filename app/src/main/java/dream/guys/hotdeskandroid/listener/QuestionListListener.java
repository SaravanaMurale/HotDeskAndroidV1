package dream.guys.hotdeskandroid.listener;

public interface QuestionListListener
{
    void updateAnswer(int categoryPosition,int questionPosition,boolean answer);
    void showThingsToConsider(int categoryPosition,int questionPosition,String showThingsToConsider);
}
