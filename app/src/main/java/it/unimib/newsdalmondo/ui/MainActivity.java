package it.unimib.newsdalmondo.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Spinner;

import com.google.android.material.snackbar.Snackbar;

import it.unimib.newsdalmondo.R;
import it.unimib.newsdalmondo.utils.Constants;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final String BUTTON_PRESSED_COUNTER_KEY = "goButtonContKey";
    private static final String NEWS_KEY = "NewsKey";

    //La m è una convenzione per le variabili private
    private Button mButtonGo;
    private Spinner mSpinnerCountry;

    private CheckBox mCheckboxBusiness;
    private CheckBox mCheckboxEntertainment;
    private CheckBox mCheckboxGeneral;
    private CheckBox mCheckboxHealt;
    private CheckBox mCheckboxScience;
    private CheckBox mCheckboxSport;
    private CheckBox mCheckboxTech;

    private int goButtonCont = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        if(savedInstanceState != null) {
            goButtonCont = savedInstanceState.getInt(BUTTON_PRESSED_COUNTER_KEY);
        }

        readInformation();

        Log.d(TAG, "OnCreate: button go pressed " + goButtonCont + " times");

        mButtonGo = findViewById(R.id.go_button);
        mSpinnerCountry = findViewById(R.id.spinner_nazioni);


        mCheckboxBusiness = findViewById(R.id.check_finanza);
        mCheckboxEntertainment = findViewById(R.id.check_intrattenimento);
        mCheckboxGeneral = findViewById(R.id.check_generale);
        mCheckboxHealt = findViewById(R.id.check_salute);
        mCheckboxScience = findViewById(R.id.check_scienza);
        mCheckboxSport = findViewById(R.id.check_sport);
        mCheckboxTech = findViewById(R.id.check_tech);


        //Inizio sezione button go
        mButtonGo.setOnClickListener(view -> {
            goButtonCont++;
            if(isCountrySelected() && isTopicSelected()) {
                Log.d(TAG, "One country and at leasto one topic choosen");
                saveInformation();

                Intent intent = new Intent(this, NewsActivity.class);
                intent.putExtra(BUTTON_PRESSED_COUNTER_KEY, goButtonCont);
                intent.putExtra(NEWS_KEY )


            } else {
                //Oppure si usa una snackbar per mandare il messaggio di errore all'utente
                Snackbar.make(mButtonGo, R.string.errore, Snackbar.LENGTH_SHORT).show();
            }
        });
    }






    private void readInformation() {
        SharedPreferences SharedPre = getSharedPreferences(Constants.SHARED_PREFERENCES_FILE_NAME, Context.MODE_PRIVATE);

        String country = SharedPre.getString(Constants.SHARED_PREFERENCES_COUNTRY, null);

        Log.d(TAG, "readInformation: " + country);

    }
    private void saveInformation() {

        SharedPreferences sharedPreferences = getSharedPreferences(Constants.SHARED_PREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Constants.SHARED_PREFERENCES_COUNTRY, mSpinnerCountry.getSelectedItem().toString());
        editor.apply();


    }

    private boolean isCountrySelected() {
        if(mSpinnerCountry.getSelectedItem() != null) {
            return true;
        }
        return false;
    }

    private boolean isTopicSelected() {
        return mCheckboxBusiness.isChecked() || mCheckboxEntertainment.isChecked() || mCheckboxGeneral.isChecked() ||
                mCheckboxHealt.isChecked() || mCheckboxSport.isChecked() || mCheckboxTech.isChecked() || mCheckboxScience.isChecked();
    }

    //All'interno dell'oggetto Bundle si possono salvare delle informazioni
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(BUTTON_PRESSED_COUNTER_KEY, goButtonCont);


    }
}