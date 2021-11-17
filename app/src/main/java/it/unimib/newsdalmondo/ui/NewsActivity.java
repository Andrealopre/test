package it.unimib.newsdalmondo.ui;


import androidx.appcompat.app.AppCompatActivity;


import it.unimib.newsdalmondo.R;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class NewsActivity extends AppCompatActivity {

    private static final String TAG = "NewsActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        //


        Intent intent = getIntent();

        int count = intent.getIntExtra(MainActivity.BUTTON_PRESSED_COUNTER_KEY, 0);

        Log.d(TAG, "onCreate: " + count);
    }
}