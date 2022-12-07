package dream.guys.hotdeskandroid.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import dream.guys.hotdeskandroid.R;

public class LocateCarSelectAdapter extends RecyclerView.Adapter<LocateCarSelectAdapter.LocateCarSelectViewHolder> {

    @NonNull
    @Override
    public LocateCarSelectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_desk_select_adapter, parent, false);
        return new LocateCarSelectViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull LocateCarSelectViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class LocateCarSelectViewHolder extends RecyclerView.ViewHolder {


        public LocateCarSelectViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
