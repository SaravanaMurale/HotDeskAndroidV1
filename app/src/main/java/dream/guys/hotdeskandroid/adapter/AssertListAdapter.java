package dream.guys.hotdeskandroid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import dream.guys.hotdeskandroid.R;
import dream.guys.hotdeskandroid.model.AssertModel;

public class AssertListAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<AssertModel> assertList;

    public AssertListAdapter(Context context, ArrayList<AssertModel> assertModelListList) {
        this.context = context;
        this.assertList = assertModelListList;
    }

    @Override
    public int getCount() {
        return assertList != null ? assertList.size() : 0;
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View rootView = LayoutInflater.from(context)
                .inflate(R.layout.assert_list, viewGroup, false);

        TextView txtName = rootView.findViewById(R.id.name);
        ImageView image = rootView.findViewById(R.id.image);

        txtName.setText(assertList.get(i).getAssertName());
        Glide.with(context)
                .load(assertList.get(i).getImage())
                .placeholder(R.drawable.fire)
                .into(image);

//        image.setImageResource(assertList.get(i).getImage());

        return rootView;
    }
}
