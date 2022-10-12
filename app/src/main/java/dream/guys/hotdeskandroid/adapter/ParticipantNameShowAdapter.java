package dream.guys.hotdeskandroid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import dream.guys.hotdeskandroid.R;
import dream.guys.hotdeskandroid.model.response.ParticipantDetsilResponse;

public class ParticipantNameShowAdapter extends RecyclerView.Adapter<ParticipantNameShowAdapter.ParticipantNameViewHolder> {

    Context context;
    List<ParticipantDetsilResponse> participantDetsilResponseList;
    OnParticipantSelectable onParticipantSelectable;
    RecyclerView recyclerView;

    public interface OnParticipantSelectable{

        public void onParticipantSelect(ParticipantDetsilResponse participantDetsilResponse, RecyclerView recyclerView);

    }


    public ParticipantNameShowAdapter(Context context, List<ParticipantDetsilResponse> participantDetsilResponseList,OnParticipantSelectable onParticipantSelectable,RecyclerView recyclerView) {

        this.context=context;
        this.participantDetsilResponseList=participantDetsilResponseList;
        this.onParticipantSelectable=onParticipantSelectable;
        this.recyclerView=recyclerView;

    }

    @NonNull
    @Override
    public ParticipantNameViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_participant_adapter, parent, false);
        return new ParticipantNameViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ParticipantNameViewHolder holder, int position) {

        holder.tvParticipantName.setText(participantDetsilResponseList.get(position).getFullName());
        holder.tvParticcipantEmail.setText(participantDetsilResponseList.get(position).getEmail());

        holder.cardViewParticipant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onParticipantSelectable.onParticipantSelect(participantDetsilResponseList.get(holder.getAbsoluteAdapterPosition()),recyclerView);

            }
        });

    }

    @Override
    public int getItemCount() {
        return participantDetsilResponseList.size();
    }

    public class ParticipantNameViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.cardViewParticipant)
        CardView cardViewParticipant;
        @BindView(R.id.tvParticipantName)
        TextView tvParticipantName;
        @BindView(R.id.tvParticcipantEmail)
        TextView tvParticcipantEmail;

        public ParticipantNameViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
