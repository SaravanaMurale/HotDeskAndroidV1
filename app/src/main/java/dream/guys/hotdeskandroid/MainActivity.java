package dream.guys.hotdeskandroid;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

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
    Menu menu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);


        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        uiInit();
/*
        navView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
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

    private void uiInit() {
        navView = findViewById(R.id.nav_view);
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main2);
        NavigationUI.setupWithNavController(binding.navView, navController);

        menu = navView.getMenu();
//        menu.findItem(R.id.navigation_home).setIcon(R.drawable.bottom_nav_selector);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            menu.findItem(R.id.navigation_home).setIconTintList(null);
        }
        navView.setItemIconTintList(null);

        navView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        System.out.println("home click balaa");
                    case R.id.navigation_book:
                        System.out.println("book click balaa");
                    case R.id.navigation_locate:
                        System.out.println("locate click balaa");

                }
                return true;
            }
        });

    }
}