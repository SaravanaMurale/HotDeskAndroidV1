package dream.guys.hotdeskandroid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import dream.guys.hotdeskandroid.listener.QuestionListListener;
import dream.guys.hotdeskandroid.R;
import dream.guys.hotdeskandroid.model.response.QuestionListResponse;

public class AssessmentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements QuestionListListener{

    private static final String TAG = "PersonalHelpAdapter";
    public List<QuestionListResponse> itemsData = new ArrayList<>();
    private QuestionListAdapter questionListAdapter;
    private LinearLayoutManager mLayoutManager;
    private List<QuestionListResponse.ChecklistQuestions> questionsList= new ArrayList<>();
    public QuestionListListener listener;

    Context context;

    public AssessmentAdapter(List<QuestionListResponse> questionListResponse,Context context) {
        this.itemsData = questionListResponse;
        this.context = context;
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView)
    {
        super.onAttachedToRecyclerView(recyclerView);
        listener = (QuestionListListener) recyclerView.getContext();
     }

     public void setQuestionListListener(QuestionListListener listener)
     {
         this.listener = listener;
     }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_workspace_header, parent, false);
        return new AssessmentViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

        AssessmentViewHolder assessmentViewHolder = (AssessmentViewHolder) viewHolder;
        assessmentViewHolder.textView.setText(position+1+"."+itemsData.get(position).getCategoryName());

        mLayoutManager = new LinearLayoutManager(viewHolder.itemView.getContext(), LinearLayoutManager.VERTICAL, false);
        assessmentViewHolder.rvQuestion.setLayoutManager(mLayoutManager);
        questionListAdapter = new QuestionListAdapter(itemsData.get(position).getChecklistQuestions(),position,context);
        questionListAdapter.setQuestionListListener(this);
        assessmentViewHolder.rvQuestion.setAdapter(questionListAdapter);
    }


    @Override
    public int getItemCount()
    {
        return itemsData.size();
    }

    @Override
    public void updateAnswer(int categoryPosition, int questionPosition, boolean answer)
    {
        if(listener!=null) listener.updateAnswer(categoryPosition,questionPosition,answer);
    }

    @Override
    public void showThingsToConsider(int catPosition, int qusPosition, String showThingsToConsider)
    {
        if(listener!=null) listener.showThingsToConsider(catPosition, qusPosition, showThingsToConsider);
    }

    static class AssessmentViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        RecyclerView rvQuestion;

        public AssessmentViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tvHeader);
            rvQuestion = itemView.findViewById(R.id.rvQuestion);

        }
    }
}