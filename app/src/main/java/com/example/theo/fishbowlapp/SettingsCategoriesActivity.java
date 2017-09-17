package com.example.theo.fishbowlapp;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import com.example.theo.fishbowlapp.MySLQiteHelpers.MySQLiteHelperGameSettings;
import com.example.theo.fishbowlapp.MySLQiteHelpers.MySQLiteHelperWords;
import com.example.theo.fishbowlapp.MySLQiteHelpers.Words;


public class SettingsCategoriesActivity extends AppCompatActivity {
    private Button next_button, back_button;
    int category = 0, categories_num = 0, language = 0, words_rem = 0;
    private String filename;
    int categories[] = {0, 0, 0, 0};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_categories);

        final Button random_words_button = (Button) findViewById(R.id.random_words_button);
        final Button movies_button = (Button) findViewById(R.id.movies_button);
        final Button animals_button = (Button) findViewById(R.id.animals_button);
        final Button countries_button = (Button) findViewById(R.id.countries_button);
        //Button celeb_button = (Button) findViewById(R.id.celebrities_button);

        if (getResources().getString(R.string.lang).equals("eng")) {
            language = 0;   //english
        }
        else {
            language = 1;   //greek
        }

        random_words_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category = 1;
                random_words_button.getBackground().setAlpha(30);
                int found = 0, pos = -1;
                for (int i =0 ; i < 4; i++){
                    if (categories[i] == category){
                        found = 1;
                    }
                    if (categories[i] == 0){
                        pos = i;
                    }
                }
                if (found == 0 && pos != -1){
                    categories[pos] = category;
                    categories_num++;
                }
            }
        });

        movies_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category = 2;
                movies_button.getBackground().setAlpha(30);
                int found = 0, pos = -1;
                for (int i =0 ; i < 4; i++){
                    if (categories[i] == category){
                        found = 1;
                    }
                    if (categories[i] == 0){
                        pos = i;
                    }
                }
                if (found == 0 && pos != -1){
                    categories[pos] = category;
                    categories_num++;
                }
            }
        });

        animals_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category = 3;
                animals_button.getBackground().setAlpha(30);
                int found = 0, pos = -1;
                for (int i =0 ; i < 4; i++){
                    if (categories[i] == category){
                        found = 1;
                    }
                    if (categories[i] == 0){
                        pos = i;
                    }
                }
                if (found == 0 && pos != -1){
                    categories[pos] = category;
                    categories_num++;
                }
            }
        });

        countries_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category = 4;
                countries_button.getBackground().setAlpha(30);
                int found = 0, pos = -1;
                for (int i =0 ; i < 4; i++){
                    if (categories[i] == category){
                        found = 1;
                    }
                    if (categories[i] == 0){
                        pos = i;
                    }
                }
                if (found == 0  && pos != -1){
                    categories[pos] = category;
                    categories_num++;
                }
            }
        });

        /*celeb_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category = 5;
                goToTeamsDetails();
            }
        });*/


        final MySQLiteHelperWords db_words = new MySQLiteHelperWords(this);
        final MySQLiteHelperGameSettings db_settings = new MySQLiteHelperGameSettings(this);
        final int teams_num = db_settings.getGameSettings(1).getTeamsNum();


        next_button = (Button)findViewById(R.id.next);
        back_button = (Button)findViewById(R.id.back);

        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*for (int i = 0 ; i < 4; i++) {
                    Log.d("getAllCategories", String.valueOf(categories[i]));
                }*/
                if (category == 0){
                    TextView display_error = (TextView)findViewById(R.id.display_error);
                    display_error.setError(getText(R.string.category_error));
                }
                else {

                    if (category == 1) {                 /* random words */
                        if (getResources().getString(R.string.lang).equals("eng")) {
                            filename = "random_words_english.txt";
                        } else {
                            filename = "random_words_greek.txt";
                        }
                    } else if (category == 2) {            /* movies */
                        filename = "movies.txt";
                    } else if (category == 3) {            /* animals */
                        if (getResources().getString(R.string.lang).equals("eng")) {
                            filename = "animals_english.txt";
                        } else {
                            filename = "animals_greek.txt";
                        }
                    } else {                              /* countries */
                        if (getResources().getString(R.string.lang).equals("eng")) {
                            filename = "countries_english.txt";
                        } else {
                            filename = "countries_greek.txt";
                        }
                    }


                    /* if words input method is from system, insert words from system in database */
                    int words_num = db_settings.getGameSettings(1).getWordsPerPl();

                    for (int cat_num = 0; cat_num < 4; cat_num++) {
                        if (categories[cat_num] != 0){
                            if (categories[cat_num] == 1){          // random words
                                if (language == 0){
                                    filename = "random_words_english.txt";
                                }
                                else{
                                    filename = "random_words_greek.txt";
                                }
                            }
                            else if (categories[cat_num] == 2){      // movies
                                if (language == 0){
                                    filename = "movies.txt";
                                }
                                else{
                                    filename = "movies.txt";
                                }
                            }
                            else if (categories[cat_num] == 3){      // animals
                                if (language == 0){
                                    filename = "animals_english.txt";
                                }
                                else{
                                    filename = "animals_greek.txt";
                                }
                            }
                            else {                                  // countries
                                if (language == 0){
                                    filename = "countries_english.txt";
                                }
                                else{
                                    filename = "countries_greek.txt";
                                }
                            }
                            Log.d("getAllCategoriesFile", filename);
                            createDictionary(filename);

                            int words_per_category = (words_num * teams_num)/categories_num;
                            words_rem = (words_num * teams_num) % categories_num;
                            //Log.d("getAllCategoriesMods", String.valueOf(words_rem));

                            for (int words = 0; words < words_per_category; words++) {
                                db_words.addWord(new Words(getRandomWord(), "none"));
                            }
                        }
                    }
                    if (words_rem != 0){
                        for (int words = 0; words < words_rem; words++) {
                            db_words.addWord(new Words(getRandomWord(), "none"));
                        }
                    }
                    //db_words.getAllWords();

                    goToTeamsDetails();
                }
            }
        });

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToPreviousActivity();
            }
        });
    }

    public void onBackPressed() {
    }

    private void goToPreviousActivity(){
        MySQLiteHelperGameSettings db_del_settings = new MySQLiteHelperGameSettings(this);
        db_del_settings.dropTable();
        MySQLiteHelperWords db_del_words = new MySQLiteHelperWords(this);
        db_del_words.dropTable();

        Intent intent = new Intent(this, SettingsGameplayActivity.class);
        startActivity(intent);
    }

    private void goToTeamsDetails(){
        Intent intent = new Intent(this,SettingsTeamsActivity.class);
        intent.putExtra("CATEGORY", category);
        intent.putExtra("WORDS_INPUT", 0);
        startActivity(intent);
    }


    private ArrayList<String> dictionary;
    private int wordLength; //Set elsewhere

    private void createDictionary(String filename){
        dictionary = new ArrayList<>();

        BufferedReader dict = null; //Holds the dictionary file
        AssetManager am = this.getAssets();

        try {
            //dictionary.txt should be in the assets folder.
            dict = new BufferedReader(new InputStreamReader(am.open(filename)));

            String word;
            while((word = dict.readLine()) != null){
                if(word.length() != 0){
                    dictionary.add(word);
                }
            }

        } catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        try {
            dict.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    //Precondition: the dictionary has been created.
    private String getRandomWord(){
        return dictionary.get((int)(Math.random() * dictionary.size()));
    }
}
