package dream.guys.hotdeskandroid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import dream.guys.hotdeskandroid.R;

public class RepeateDataAdapter extends RecyclerView.Adapter<RepeateDataAdapter.viewholder> {

    Context context;
    ArrayList<String> stringArrayList = new ArrayList<>();
    repeatInterface aRepeatInterface;

    public RepeateDataAdapter(Context context, ArrayList<String> stringArrayList) {
        this.context = context;
        this.stringArrayList = stringArrayList;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_repeat_data, parent, false);
        return new viewholder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {

        holder.tv_data.setText(stringArrayList.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                aRepeatInterface.repeatDataClick(holder.getAbsoluteAdapterPosition(),
                        stringArrayList.get(holder.getAbsoluteAdapterPosition()));
            }
        });

    }

    @Override
    public int getItemCount() {
        return stringArrayList.size();
    }

    public class viewholder extends RecyclerView.ViewHolder{

        TextView tv_data;

        public viewholder(@NonNull View itemView) {
            super(itemView);

            tv_data = itemView.findViewById(R.id.tv_data);

        }
    }

    public interface repeatInterface {
        public void repeatDataClick(int pos,String data);
    }

}
