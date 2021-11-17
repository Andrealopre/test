package it.unimib.newsdalmondo.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;

import com.google.android.material.snackbar.Snackbar;

import java.util.HashSet;
import java.util.Set;

import it.unimib.newsdalmondo.R;
import it.unimib.newsdalmondo.model.News;
import it.unimib.newsdalmondo.utils.Constants;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    public static final String BUTTON_PRESSED_COUNTER_KEY = "goButtonContKey";
    public static final String NEWS_KEY = "NewsKey";

    //La m è una convenzione per le variabili private
    private Button mButtonGo;
    private Spinner mSpinnerCountry;

    private CheckBox mCheckboxBusiness;
    private CheckBox mCheckboxEntertainment;
    private CheckBox mCheckboxGeneral;
    private CheckBox mCheckboxHealth;
    private CheckBox mCheckboxScience;
    private CheckBox mCheckboxSport;
    private CheckBox mCheckboxTech;
    private News mNews;

    private int mGoButtonCont = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSpinnerCountry = findViewById(R.id.spinner_nazioni);

        mCheckboxBusiness = findViewById(R.id.check_finanza);
        mCheckboxEntertainment = findViewById(R.id.check_intrattenimento);
        mCheckboxGeneral = findViewById(R.id.check_generale);
        mCheckboxHealth = findViewById(R.id.check_salute);
        mCheckboxScience = findViewById(R.id.check_scienza);
        mCheckboxSport = findViewById(R.id.check_sport);
        mCheckboxTech = findViewById(R.id.check_tech);

        if(savedInstanceState != null) {
            mGoButtonCont = savedInstanceState.getInt(BUTTON_PRESSED_COUNTER_KEY);
            mNews = savedInstanceState.getParcelable(NEWS_KEY);
        } else {
            mGoButtonCont = 0;
            mNews = new News("Defualt title", "Default source");
        }

        Log.d(TAG, "OnCreate: button go pressed " + mGoButtonCont + " times");
        Log.d(TAG, "onCreate: News: " + mNews);

        mButtonGo = findViewById(R.id.go_button);

        setViewsChecked();

        //Inizio sezione button go
        mButtonGo.setOnClickListener(view -> {
            mGoButtonCont++;
            if(isCountrySelected() && isTopicSelected()) {
                Log.d(TAG, "One country and at leasto one topic choosen");

                if(mGoButtonCont > 3) {
                    mNews.setTitle("The button has been pressed " + mGoButtonCont + " times");
                    mNews.setSource("Corriere della Sera");
                }


                saveInformation();
                Intent intent = new Intent(this, NewsActivity.class);
                intent.putExtra(BUTTON_PRESSED_COUNTER_KEY, mGoButtonCont);
                intent.putExtra(NEWS_KEY, mNews);
                startActivity(intent);


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


    /*serve a immagazzinare i dati scelti dall'utente nel file system*/
    private void saveInformation() {

        String country = mSpinnerCountry.getSelectedItem().toString();
        String countryShortName = null;

        if (country.equals(getResources().getString(R.string.france))) {
            countryShortName = Constants.FRANCE;
        } else if (country.equals(getResources().getString(R.string.germany))) {
            countryShortName = Constants.GERMANY;
        } else if (country.equals(getResources().getString(R.string.italy))) {
            countryShortName = Constants.ITALY;
        } else if (country.equals(getResources().getString(R.string.spain))) {
            countryShortName = Constants.SPAIN;
        } else if (country.equals(getResources().getString(R.string.united_states))) {
            countryShortName = Constants.USA;
        }

        Set<String> topics = new HashSet<>();

        //Inserisce in un hashset tutte le scelte dell'utente
        if(mCheckboxBusiness.isChecked()) {
            topics.add(Constants.BUSINESS);
        }
        if (mCheckboxEntertainment.isChecked()) {
            topics.add(Constants.ENTERTAINMENT);
        }
        if (mCheckboxGeneral.isChecked()) {
            topics.add(Constants.GENERAL);
        }
        if (mCheckboxHealth.isChecked()) {
            topics.add(Constants.HEALTH);
        }
        if (mCheckboxScience.isChecked()) {
            topics.add(Constants.SCIENCE);
        }
        if (mCheckboxSport.isChecked()) {
            topics.add(Constants.SPORTS);
        }
        if (mCheckboxTech.isChecked()) {
            topics.add(Constants.TECH);
        }

        SharedPreferences sharedPreferences = getSharedPreferences(Constants.SHARED_PREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Constants.SHARED_PREFERENCES_COUNTRY, countryShortName);
        editor.putStringSet(Constants.SHARED_PREFERENES_TOPIC, topics);
        editor.apply();
    }

    private void setViewsChecked() {

        SharedPreferences sharedPref = getSharedPreferences(Constants.SHARED_PREFERENCES_FILE_NAME, Context.MODE_PRIVATE);

        String countryOfInterest = sharedPref.getString(Constants.SHARED_PREFERENCES_COUNTRY, null);
        Set<String> topicsOfInterest = sharedPref.getStringSet(Constants.SHARED_PREFERENES_TOPIC, null);

        if(countryOfInterest != null) {
            switch(countryOfInterest) {
                case Constants.FRANCE:
                    mSpinnerCountry.setSelection(getSpinnerPositionBasedOnValue(getResources().getString(R.string.france)));
                    break;
                case Constants.GERMANY:
                    mSpinnerCountry.setSelection(getSpinnerPositionBasedOnValue(getResources().getString(R.string.germany)));
                    break;
                case Constants.ITALY:
                    mSpinnerCountry.setSelection(getSpinnerPositionBasedOnValue(getResources().getString(R.string.italy)));
                    break;
                case Constants.SPAIN:
                    mSpinnerCountry.setSelection(getSpinnerPositionBasedOnValue(getResources().getString(R.string.spain)));
                    break;
                case Constants.USA:
                    mSpinnerCountry.setSelection(getSpinnerPositionBasedOnValue(getResources().getString(R.string.united_states)));
            }
        }

        if(topicsOfInterest != null) {
            if(topicsOfInterest.contains(Constants.BUSINESS)) {
                mCheckboxBusiness.setChecked(true);
            }
            if(topicsOfInterest.contains(Constants.ENTERTAINMENT)) {
                mCheckboxEntertainment.setChecked(true);
            }
            if (topicsOfInterest.contains(Constants.GENERAL)) {
                mCheckboxGeneral.setChecked(true);
            }
            if (topicsOfInterest.contains(Constants.HEALTH)) {
                mCheckboxHealth.setChecked(true);
            }
            if (topicsOfInterest.contains(Constants.SCIENCE)) {
                mCheckboxScience.setChecked(true);
            }
            if (topicsOfInterest.contains(Constants.SPORTS)) {
                mCheckboxSport.setChecked(true);
            }
            if (topicsOfInterest.contains(Constants.TECH)) {
                mCheckboxTech.setChecked(true);
            }
        }

    }

    private int getSpinnerPositionBasedOnValue(String value) {
        String[] countries = getResources().getStringArray(R.array.country_selector);

        for(int i = 0; i < countries.length; i++) {
            if(countries[i].equals(value)) {
                return i;
            }
        }
        return 0;
    }

    private boolean isCountrySelected() {
        if(mSpinnerCountry.getSelectedItem() != null) {
            return true;
        }
        return false;
    }

    private boolean isTopicSelected() {
        if(mCheckboxBusiness.isChecked() || mCheckboxEntertainment.isChecked() || mCheckboxGeneral.isChecked() ||
                mCheckboxHealth.isChecked() || mCheckboxSport.isChecked() || mCheckboxTech.isChecked() || mCheckboxScience.isChecked()) {
            return true;
        }
        return false;
    }

    //All'interno dell'oggetto Bundle si possono salvare delle informazioni
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState:");
        outState.putInt(BUTTON_PRESSED_COUNTER_KEY, mGoButtonCont);
        outState.putParcelable(NEWS_KEY, mNews);
    }
}