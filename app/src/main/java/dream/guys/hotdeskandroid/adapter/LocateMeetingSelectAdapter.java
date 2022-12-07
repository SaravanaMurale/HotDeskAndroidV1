package dream.guys.hotdeskandroid.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import dream.guys.hotdeskandroid.R;

public class LocateMeetingSelectAdapter  extends RecyclerView.Adapter<LocateMeetingSelectAdapter.LocateMeetingViewHolder> {

    @NonNull
    @Override
    public LocateMeetingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_desk_select_adapter, parent, false);
        return new LocateMeetingViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull LocateMeetingViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class LocateMeetingViewHolder extends RecyclerView.ViewHolder {

        public LocateMeetingViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
