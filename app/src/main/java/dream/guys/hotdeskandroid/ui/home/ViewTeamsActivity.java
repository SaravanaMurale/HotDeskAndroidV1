package dream.guys.hotdeskandroid.ui.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import dream.guys.hotdeskandroid.MainActivity;
import dream.guys.hotdeskandroid.R;
import dream.guys.hotdeskandroid.adapter.SearchRecyclerAdapter;
import dream.guys.hotdeskandroid.model.response.GlobalSearchResponse;
import dream.guys.hotdeskandroid.model.response.TeamMembersResponse;
import dream.guys.hotdeskandroid.utils.AppConstants;
import dream.guys.hotdeskandroid.utils.ProgressDialog;
import dream.guys.hotdeskandroid.utils.SessionHandler;
import dream.guys.hotdeskandroid.utils.Utils;
import dream.guys.hotdeskandroid.webservice.ApiClient;
import dream.guys.hotdeskandroid.webservice.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewTeamsActivity extends AppCompatActivity {
    @BindView(R.id.view_teams_back)
    ImageView backIcon;
    @BindView(R.id.lv_view_team)
    ListView viewTeamListView;

    LinearLayoutManager linearLayoutManager;
    List<TeamMembersResponse> teamMembersResponses = new ArrayList<>();
    List<String> arrayString= new ArrayList<>();
    ArrayAdapter adapter;
    Dialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_teams);
        ButterKnife.bind(this);
        dialog= new Dialog(this);
        callTeamMemberStatus();

        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, arrayString);
        viewTeamListView.setAdapter(adapter);
        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    private void callTeamMemberStatus() {
        if (Utils.isNetworkAvailable(this)) {
            dialog= ProgressDialog.showProgressBar(this);
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<List<TeamMembersResponse>> call = apiService.getTeamMembers(Utils.getCurrentDate(),
                    SessionHandler.getInstance().getInt(ViewTeamsActivity.this,AppConstants.TEAM_ID));
            call.enqueue(new Callback<List<TeamMembersResponse>>() {
                @Override
                public void onResponse(Call<List<TeamMembersResponse>> call, Response<List<TeamMembersResponse>> response) {
//                    Toast.makeText(MainActivity.this, "on res", Toast.LENGTH_SHORT).show();
                    if(response.code()==200){
                        ProgressDialog.dismisProgressBar(ViewTeamsActivity.this,dialog);
                        teamMembersResponses = response.body();
//                        Toast.makeText(ViewTeamsActivity.this, ""+teamMembersResponses.size(), Toast.LENGTH_SHORT).show();
                        if (teamMembersResponses!=null &&
                                teamMembersResponses.size()>0){
                            for (int i=0;i<teamMembersResponses.size();i++){
                                arrayString.add(" "+teamMembersResponses.get(i).getFirstName()+" "+
                                        teamMembersResponses.get(i).getLastName());
                            }
                            adapter.notifyDataSetChanged();
                        }

                    }else if(response.code()==401){
                        //Handle if token got expired
                        ProgressDialog.dismisProgressBar(ViewTeamsActivity.this,dialog);
                        Utils.tokenExpiryAlert(ViewTeamsActivity.this,"");

                    } else {
                        ProgressDialog.dismisProgressBar(ViewTeamsActivity.this,dialog);
                        Log.d("Search", "onResponse: else");
                        Utils.toastShortMessage(ViewTeamsActivity.this,"Api Issue Code: "+response.code());
                    }

                }
                @Override
                public void onFailure(Call<List<TeamMembersResponse>> call, Throwable t) {
//                    Toast.makeText(ViewTeamsActivity.this, "on fail", Toast.LENGTH_SHORT).show();
                    Utils.toastShortMessage(ViewTeamsActivity.this,"Response Failure: "+t.getMessage());
                    ProgressDialog.dismisProgressBar(ViewTeamsActivity.this,dialog);
                    Log.d("Search", "onResponse: fail"+t.getMessage());
                }
            });

        } else {
            Utils.toastMessage(this, "Please Enable Internet");
        }
    }
}