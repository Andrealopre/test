package it.unimib.newsdalmondo.ui;


import androidx.appcompat.app.AppCompatActivity;


import it.unimib.newsdalmondo.R;
import it.unimib.newsdalmondo.model.News;
import it.unimib.newsdalmondo.utils.Constants;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

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

        mNewsMenu = findViewById(R.id.menu_notizie);
    }
}