package com.brickendon.hdplus.adapter;

import static com.brickendon.hdplus.utils.Utils.getAppKeysPageScreenData;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import com.brickendon.hdplus.listener.QuestionListListener;
import com.brickendon.hdplus.R;
import com.brickendon.hdplus.model.response.QuestionListResponse;

public class QuestionListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private static final String TAG = "QuestionListAdapter";
    private List<QuestionListResponse.ChecklistQuestions> itemsData = new ArrayList<>();
    private int categoryPosition=0;
    private QuestionListListener questionListListener;
    Context context;

    public QuestionListAdapter(List<QuestionListResponse.ChecklistQuestions> questionListResponse,int categoryPosition,
                               Context context) {
        this.itemsData = questionListResponse;
        this.categoryPosition=categoryPosition;
        this.context = context;
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView)
    {
        super.onAttachedToRecyclerView(recyclerView);
        context = recyclerView.getContext();
    }

    public void setQuestionListListener(QuestionListListener questionListListener)
    {
        this.questionListListener = questionListListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;

            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_workspace_question, parent, false);
            return new QuestionViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, @SuppressLint("RecyclerView") int position) {

        QuestionViewHolder questionViewHolder = (QuestionViewHolder) viewHolder;
        questionViewHolder.textView.setText(itemsData.get(position).getRiskFactors());

        if (itemsData.get(position).isAnswer())  questionViewHolder.rb_yes.setChecked(true);
        else questionViewHolder.rb_no.setChecked(true);

        questionViewHolder.rgAnswer.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i)
            {
                switch (i)
                {
                    case R.id.rb_yes:
                         if(questionViewHolder.rb_yes.isChecked())
                         {
                             if(questionListListener!=null)
                                 questionListListener.updateAnswer(categoryPosition,position,true);
                         }
                         break;
                    case R.id.rb_no:
                         if(questionViewHolder.rb_no.isChecked())
                         {
                             if(questionListListener!=null)
                                 questionListListener.updateAnswer(categoryPosition,position,false);
                         }
                         break;
                }
            }
        });

        questionViewHolder.iv_edit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //Utils.toastShortMessage(mActivity, itemsData.get(position).getThingsToConsider());
                if(questionListListener!=null)
                    questionListListener.showThingsToConsider(categoryPosition, position
                            , itemsData.get(position).getThingsToConsider());
            }
        });

        questionViewHolder.rb_yes.setText(getAppKeysPageScreenData(context).getYes());
        questionViewHolder.rb_no.setText(getAppKeysPageScreenData(context).getNo());

    }

    @Override
    public int getItemCount()
    {
        return itemsData.size();
    }

    static class QuestionViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        RadioButton rb_yes, rb_no;
        RadioGroup rgAnswer;
        ImageView iv_edit;


        public QuestionViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tvQuestion);
            rb_yes = itemView.findViewById(R.id.rb_yes);
            rb_no = itemView.findViewById(R.id.rb_no);
            iv_edit = itemView.findViewById(R.id.iv_edit);
            rgAnswer = itemView.findViewById(R.id.rgAnswer);

        }
    }
}