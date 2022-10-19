package dream.guys.hotdeskandroid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import dream.guys.hotdeskandroid.R;
import dream.guys.hotdeskandroid.model.response.TeamDeskResponse;

public class CustomAdapter extends BaseAdapter {
    Context context;
    List<TeamDeskResponse.Desk> deskList;
    LayoutInflater inflter;

    public CustomAdapter(Context applicationContext, List<TeamDeskResponse.Desk> deskList) {
        this.context = applicationContext;
        this.deskList = deskList;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return deskList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.text_spinner, null);
        TextView names = (TextView) view.findViewById(R.id.txt_name);
        names.setText(deskList.get(i).getCode());
        return view;
    }
}
