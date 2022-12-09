package dream.guys.hotdeskandroid.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import dream.guys.hotdeskandroid.R;
import dream.guys.hotdeskandroid.model.AssertModel;
import dream.guys.hotdeskandroid.ui.book.BookFragment;

public class AssertListAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<AssertModel> assertList;
    Fragment fragment;
    public AssertListAdapter(Context context, ArrayList<AssertModel> assertModelListList, Fragment fragment) {
        this.context = context;
        this.assertList = assertModelListList;
        this.fragment = fragment;
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
        TextView whiteLine = rootView.findViewById(R.id.divider);
        ImageView tick = rootView.findViewById(R.id.tick);
        RelativeLayout bg = rootView.findViewById(R.id.background);

        txtName.setText(assertList.get(i).getAssertName());
        Glide.with(context)
                .load(assertList.get(i).getImage())
                .placeholder(R.drawable.chair)
                .into(image);

        if(((BookFragment)fragment).isSetup){
            if (((BookFragment)fragment).selectedicon -1 == i) {
                Glide.with(context)
                        .load(R.drawable.tick)
                        .placeholder(R.drawable.tick)
                        .into(tick);

                tick.setVisibility(View.VISIBLE);
                bg.setBackgroundColor(ContextCompat.getColor(context, R.color.figmaBackground));
            } else {
//            ((BookFragment)fragment).selectedicon=1;
                tick.setVisibility(View.GONE);
                bg.setBackgroundColor(Color.TRANSPARENT);
            }
        } else {
            if (i==0){
                Glide.with(context)
                        .load(R.drawable.ic_arrow_down)
                        .placeholder(R.drawable.tick)
                        .into(tick);

                tick.setVisibility(View.VISIBLE);
                bg.setBackgroundColor(ContextCompat.getColor(context, R.color.figmaBackground));
            }else {
                tick.setVisibility(View.GONE);
            }

            if (i ==assertList.size()-1){
                ((BookFragment)fragment).isSetup =true;
            }
        }


        if (i == assertList.size()-1){
            whiteLine.setVisibility(View.GONE);

        } else {
            whiteLine.setVisibility(View.VISIBLE);
        }


        return rootView;
    }

    public void updateItemColor(){

    }
}
