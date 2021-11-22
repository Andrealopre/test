package it.unimib.newsdalmondo.ui;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;


import it.unimib.newsdalmondo.R;
import it.unimib.newsdalmondo.model.News;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class NewsActivity extends AppCompatActivity {

    private static final String TAG = "NewsActivity";
    private Menu mNewsMenu;


    //Metodi

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        //

        //Con questo metodo posso ottenere l'input dal bottone go
        Intent intent = getIntent();

        int counter = intent.getIntExtra(MainActivity.BUTTON_PRESSED_COUNTER_KEY, 0);
        News news = intent.getParcelableExtra(MainActivity.NEWS_KEY);

        Log.d(TAG, "onCreate: " + counter);
        Log.d(TAG, "onCreate: " + news);

        /**Da qui si va a legare la classe al fragment
         * 1)Qui sto associando il fragment a questa classe tramite l'id e getSupportFragmentManager
         * 2)Questo controlla la navigazione nel grafo, tramite navHostFragment
         * 3)mBottomNavView è il riferimento alla navbar
         */

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);
        NavController navController = navHostFragment.getNavController();
        BottomNavigationView mBottomNavView = findViewById(R.id.bottom_nav_view);

        NavigationUI.setupWithNavController(mBottomNavView, navController);

        Toolbar toolbar = findViewById(R.id.new_toolbar);
        setSupportActionBar(toolbar);

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(R.id.menu_notizie, R.id.menu_interesse, R.id.menu_preferiti).build();

        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

    }
}