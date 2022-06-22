package dream.guys.hotdeskandroid;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Switch;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import dream.guys.hotdeskandroid.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    BottomNavigationView navView;
    NavController navController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        uiInit();
        nightModeConfig();
/*      navView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.navigation_home:
                        item.setIconTintList(null);
                        break;
                    case R.id.navigation_book:
                        item.setIconTintList(getResources().getColorStateList(R.color.purple_700));
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
        */
    }

    private void nightModeConfig() {
        int nightModeFlags = getApplicationContext().getResources().getConfiguration().uiMode &
                Configuration.UI_MODE_NIGHT_MASK;
        Menu menu = navView.getMenu();

        switch (nightModeFlags) {
            case Configuration.UI_MODE_NIGHT_YES:

                break;
            case Configuration.UI_MODE_NIGHT_NO:
                break;
            case Configuration.UI_MODE_NIGHT_UNDEFINED:
                break;
        }

    }

    private void uiInit() {
        navView = findViewById(R.id.nav_view);
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupWithNavController(binding.navView, navController);

        navView.setItemIconTintList(null);


    }
}