package it.unimib.newsdalmondo.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import java.util.Set;

import it.unimib.newsdalmondo.R;
import it.unimib.newsdalmondo.utils.Constants;

public class LaunchActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainerViewMain);
        NavController navController = navHostFragment.getNavController();

        if(arePreferencesSet()) {
            navController.navigate(R.id.action_launch_news);
        } else {
            navController.navigate(R.id.action_launcher_mainActivity);
        }
    }

    public boolean arePreferencesSet() {
        SharedPreferences sharedPref = getSharedPreferences(Constants.SHARED_PREFERENCES_FILE_NAME, Context.MODE_PRIVATE);

        String countryOfInterest = sharedPref.getString(Constants.SHARED_PREFERENCES_COUNTRY, null);
        Set<String> topicOfInterest = sharedPref.getStringSet(Constants.SHARED_PREFERENES_TOPIC, null);

        if(countryOfInterest != null && topicOfInterest != null) {
            return true;
        }

        return false;
    }
}