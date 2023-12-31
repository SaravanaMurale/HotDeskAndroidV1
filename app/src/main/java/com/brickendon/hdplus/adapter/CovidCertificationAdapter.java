package com.brickendon.hdplus.adapter;

import android.content.Context;
import android.os.Build;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.brickendon.hdplus.R;
import com.brickendon.hdplus.model.response.CovidQuestionsResponse;


public class CovidCertificationAdapter extends RecyclerView.Adapter<CovidCertificationAdapter.CovidCertificationViewHolder> {

    List<CovidQuestionsResponse> covidQuestionsResponseList;
    Context context;

    public CovidCertificationAdapter(Context context, List<CovidQuestionsResponse> covidQuestionsResponseList) {
        this.context=context;
        this.covidQuestionsResponseList=covidQuestionsResponseList;
    }


    public List<CovidQuestionsResponse> getList(){
        return covidQuestionsResponseList;
    }


    @NonNull
    @Override
    public CovidCertificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_covid_certification_adapter, parent, false);
        return new CovidCertificationViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CovidCertificationViewHolder holder, int position) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            holder.covidQuestion.setText(Html.fromHtml(covidQuestionsResponseList.get(position).getQuestion(), Html.FROM_HTML_MODE_COMPACT));
        } else {
            holder.covidQuestion.setText(Html.fromHtml(covidQuestionsResponseList.get(position).getQuestion()));
        }
     //   holder.covidQuestion.setText(covidQuestionsResponseList.get(position).getQuestion());

        //covidQuestionsResponseList.set(holder.getAbsoluteAdapterPosition()).setLanguage("");
        covidQuestionsResponseList.get(position).setLanguage("");

        if(covidQuestionsResponseList.get(position).isAnswer()){
           holder.covidYes.setChecked(true);
        }else {
            holder.covidNo.setChecked(true);
        }

        holder.covidYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                covidQuestionsResponseList.get(holder.getAbsoluteAdapterPosition()).setAnswer(true);
            }
        });

        holder.covidNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                covidQuestionsResponseList.get(holder.getAbsoluteAdapterPosition()).setAnswer(false);
            }
        });

    }

    @Override
    public int getItemCount() {
        return covidQuestionsResponseList.size();
    }

    public class CovidCertificationViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.covidQuestion)
        TextView covidQuestion;
        @BindView(R.id.covidRadioGroup)
        RadioGroup radioGroup;

        @BindView(R.id.covidYes)
        RadioButton covidYes;
        @BindView(R.id.covidNo)
        RadioButton covidNo;


        public CovidCertificationViewHolder(@NonNull View itemView) {
            super(itemView);

            ButterKnife.bind(this,itemView);
        }
    }

}
