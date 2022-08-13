package dream.guys.hotdeskandroid.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import dream.guys.hotdeskandroid.R;

public class NoticeListAdapter extends RecyclerView.Adapter<NoticeListAdapter.NoticeListViewHolder> {

    @NonNull
    @Override
    public NoticeListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //layout_notice_list_adapter
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_notice_list_adapter, parent, false);
        return new NoticeListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoticeListViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class NoticeListViewHolder extends RecyclerView.ViewHolder{

        public NoticeListViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
