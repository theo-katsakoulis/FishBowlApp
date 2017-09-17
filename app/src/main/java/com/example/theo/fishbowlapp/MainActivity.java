package com.example.theo.fishbowlapp;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.*;
import android.support.v7.app.AppCompatActivity;
import android.view.*;
import android.widget.*;

import com.example.theo.fishbowlapp.MySLQiteHelpers.GameSettings;
import com.example.theo.fishbowlapp.MySLQiteHelpers.MySQLiteHelperGameSettings;
import com.example.theo.fishbowlapp.MySLQiteHelpers.MySQLiteHelperRounds;
import com.example.theo.fishbowlapp.MySLQiteHelpers.MySQLiteHelperTeams;
import com.example.theo.fishbowlapp.MySLQiteHelpers.MySQLiteHelperWords;
import com.example.theo.fishbowlapp.MySLQiteHelpers.Rounds;
import com.example.theo.fishbowlapp.MySLQiteHelpers.Teams;
import com.example.theo.fishbowlapp.MySLQiteHelpers.Words;

import java.util.Locale;

import static android.R.attr.typeface;


public class MainActivity extends AppCompatActivity {
    private Button play_button, rules_button, language_button;
    private MySQLiteHelperGameSettings gameSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Typeface custom_font_title = Typeface.createFromAsset(getAssets(),  "fonts/AlexBrush-Regular.ttf");
        TextView view_title = (TextView) findViewById(R.id.title);
        view_title.setTypeface(custom_font_title);

        play_button = (Button)findViewById(R.id.play_button);
        rules_button = (Button)findViewById(R.id.rules_button);
        language_button = (Button)findViewById(R.id.language_button);

        int input_from_user = 1;    // 0 for default, 1 for user

        /* --------------------- game settings ------------------------------- */
        MySQLiteHelperGameSettings db_del_settings = new MySQLiteHelperGameSettings(this);
        db_del_settings.dropTable();

        if (input_from_user == 0) {
            MySQLiteHelperGameSettings db_settings = new MySQLiteHelperGameSettings(this);
            db_settings.addGameSettings(new GameSettings(4, 5, 30));

            db_settings.getAllGameSettings();
        }


        /* --------------------- teams ------------------------------- */
        MySQLiteHelperTeams db_del_teams = new MySQLiteHelperTeams(this);
        db_del_teams.dropTable();

        if (input_from_user == 0) {
            MySQLiteHelperTeams db_add_team = new MySQLiteHelperTeams(this);
            db_add_team.addTeam(new Teams("Team1", "red", 10, 0, 0, 0, 0));
            db_add_team.addTeam(new Teams("Team2", "black", 10, 0, 0, 0, 0));
            db_add_team.addTeam(new Teams("Team3", "green", 0, 0, 0, 0, 0));
            db_add_team.addTeam(new Teams("Team4", "green", 0, 0, 0, 0, 0));

            db_add_team.getAllTeams();
        }


        /* --------------------- rounds ------------------------------- */
        MySQLiteHelperRounds db_del_rounds = new MySQLiteHelperRounds(this);
        db_del_rounds.dropTable();


        MySQLiteHelperRounds db_rounds = new MySQLiteHelperRounds(this);
        db_rounds.addRound(new Rounds(1, 1));


        /* --------------------- words ------------------------------- */
        final MySQLiteHelperWords db_del_words = new MySQLiteHelperWords(this);
        db_del_words.dropTable();

        if (input_from_user == 0) {
            final MySQLiteHelperWords db_words = new MySQLiteHelperWords(this);

            db_words.addWord(new Words("one", "none"));
            db_words.addWord(new Words("two", "none"));
            db_words.addWord(new Words("three", "none"));
            db_words.addWord(new Words("four", "none"));
            db_words.addWord(new Words("five", "none"));

            db_words.getAllWords();
        }


        /* ---------------------------------------- */
        if (input_from_user == 0) {
            Intent intent = new Intent(this, InfoEndOfGameActivity.class);
            //Intent intent = new Intent(this, SettingsCategoriesActivity.class);
            startActivity(intent);
        }
        /* ---------------------------------------- */

        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/DejaVuSans-ExtraLight.ttf");
        play_button.setTypeface(custom_font);
        rules_button.setTypeface(custom_font);
        language_button.setTypeface(custom_font);

        play_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToInsertGameSettingsActivity();
            }
        });

        rules_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToRulesActivity();
            }
        });

        language_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToSelectLanguageActivity();
            }
        });
    }


    /* paei sti selida paixnidiou */
    private void goToInsertGameSettingsActivity() {
        Intent intent = new Intent(this, SettingsGameplayActivity.class);
        startActivity(intent);
    }

    /* paei sti selida me tous kanones */
    private void goToRulesActivity() {
        Intent intent = new Intent(this, MainRulesActivity.class);
        startActivity(intent);
    }

    private void goToSelectLanguageActivity() {
        Intent intent = new Intent(this, MainLanguageActivity.class);
        startActivity(intent);
    }

}
