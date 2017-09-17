package com.example.theo.fishbowlapp;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.*;
import android.support.v7.app.AppCompatActivity;
import android.view.*;
import android.widget.*;
import android.widget.EditText;

import com.example.theo.fishbowlapp.MySLQiteHelpers.MySQLiteHelperGameSettings;
import com.example.theo.fishbowlapp.MySLQiteHelpers.MySQLiteHelperTeams;
import com.example.theo.fishbowlapp.MySLQiteHelpers.MySQLiteHelperWords;
import com.example.theo.fishbowlapp.MySLQiteHelpers.Teams;


public class SettingsTeamsActivity extends AppCompatActivity{
    private Button next_button, back_button;
    EditText team_name1, team_name2, team_name3, team_name4;
    TextView team_title3, team_title4;
    private int words_input, category = 0;
    private String filename;
    //private Paint mPaint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_teams);

        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/Quicksand-BoldItalic.otf");
        TextView view_team1 = (TextView) findViewById(R.id.title_team1);
        view_team1.setTypeface(custom_font);
        TextView view_team2 = (TextView) findViewById(R.id.title_team2);
        view_team2.setTypeface(custom_font);
        TextView view_team3 = (TextView) findViewById(R.id.title_team3);
        view_team3.setTypeface(custom_font);
        TextView view_team4 = (TextView) findViewById(R.id.title_team4);
        view_team4.setTypeface(custom_font);


        //new ColorPickerDialog(InsertTeamDetailsActivity.this, (ColorPickerDialog.OnColorChangedListener) InsertTeamDetailsActivity.this, mPaint.getColor()).show();

        /* Button b = (Button) findViewById(R.id.button1);
        b.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new ColorPickerDialog(InsertTeamDetailsActivity.this, InsertTeamDetailsActivity.this, mPaint.getColor()).show();
            }
        });*/

        Intent iin = getIntent();
        Bundle b = iin.getExtras();

        if(b != null) {
            words_input = (int) b.get("WORDS_INPUT");

            if (words_input == 0) {
                category = (int) b.get("CATEGORY");
            }
        }

        /*if (category == 1){                 /* random words *
            if (getResources().getString(R.string.lang).equals("eng")) {
                filename = "words_1000_basic.txt";
            }
            else {
                filename = "random_words_greek.txt";
            }
        }
        else if (category == 2){            /* movies *
            filename = "movies.txt";
        }
        else if (category == 3){            /* animals *
            if (getResources().getString(R.string.lang).equals("eng")) {
                filename = "animals.txt";
            }
            else {
                filename = "animals_greek.txt";
            }
        }
        else {                              /* countries *
            if (getResources().getString(R.string.lang).equals("eng")) {
                filename = "countries.txt";
            }
            else {
                filename = "countries_greek.txt";
            }
        }*/


        final MySQLiteHelperTeams db_teams = new MySQLiteHelperTeams(this);
        final MySQLiteHelperWords db_words = new MySQLiteHelperWords(this);
        final MySQLiteHelperGameSettings db_settings = new MySQLiteHelperGameSettings(this);
        final int teams_num = db_settings.getGameSettings(1).getTeamsNum();

        /* if words input method is from system, insert words from system in database *
        if (words_input == 0){
            int words_num = db_settings.getGameSettings(1).getWordsPerPl();

            int i;
            for (i = 0; i < (words_num * teams_num); i++){
                createDictionary();
                db_words.addWord(new Words(getRandomWord(), "none"));
            }
            Log.d("getAllWordsInput2", String.valueOf(words_input));
            db_words.getAllWords();
        }*/


        team_name3 = (EditText)findViewById(R.id.team_name3);
        team_name4 = (EditText)findViewById(R.id.team_name4);
        team_title3 = (TextView)findViewById(R.id.title_team3);
        team_title4 = (TextView)findViewById(R.id.title_team4);

        /* hide the insert team name for less teams */
        if (teams_num == 2) {
            team_name3.setVisibility(View.INVISIBLE);
            team_name4.setVisibility(View.INVISIBLE);
            team_title3.setVisibility(View.INVISIBLE);
            team_title4.setVisibility(View.INVISIBLE);
        }
        if (teams_num == 3){
            team_name4.setVisibility(View.INVISIBLE);
            team_title4.setVisibility(View.INVISIBLE);
        }

        next_button = (Button)findViewById(R.id.next);
        back_button = (Button)findViewById(R.id.back);

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToPreviousActivity();
            }
        });

        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                team_name1 = (EditText)findViewById(R.id.team_name1);
                team_name2 = (EditText)findViewById(R.id.team_name2);

                /* show team name insert fields for number of the number of teams the user chose */
                if (team_name1.getText().toString().trim().equals("")) {
                    if (getResources().getString(R.string.lang).equals("eng")) {
                        db_teams.addTeam(new Teams("Team1", "red", 0, 0, 0, 0, 0));
                    }
                    else {
                        db_teams.addTeam(new Teams("Ομάδα1", "red", 0, 0, 0, 0, 0));
                    }
                }
                else{
                    db_teams.addTeam(new Teams(team_name1.getText().toString(), "red", 0, 0, 0, 0, 0));
                }

                if (team_name2.getText().toString().trim().equals("")) {
                    if (getResources().getString(R.string.lang).equals("eng")) {
                        db_teams.addTeam(new Teams("Team2", "black", 0, 0, 0, 0, 0));
                    }
                    else {
                        db_teams.addTeam(new Teams("Ομάδα2", "black", 0, 0, 0, 0, 0));
                    }
                }
                else{
                    db_teams.addTeam(new Teams(team_name2.getText().toString(), "black", 0, 0, 0, 0, 0));
                }


                if (teams_num == 3) {
                    team_name3 = (EditText)findViewById(R.id.team_name3);

                    if (team_name3.getText().toString().trim().equals("")) {
                        if (getResources().getString(R.string.lang).equals("eng")) {
                            db_teams.addTeam(new Teams("Team3", "green", 0, 0, 0, 0, 0));
                        }
                        else {
                            db_teams.addTeam(new Teams("Ομάδα3", "green", 0, 0, 0, 0, 0));
                        }
                    }
                    else{
                        db_teams.addTeam(new Teams(team_name3.getText().toString(), "green", 0, 0, 0, 0, 0));
                    }
                }

                if (teams_num == 4){
                    team_name3 = (EditText)findViewById(R.id.team_name3);
                    team_name4 = (EditText)findViewById(R.id.team_name4);

                    if (team_name3.getText().toString().trim().equals("")) {
                        if (getResources().getString(R.string.lang).equals("eng")) {
                            db_teams.addTeam(new Teams("Team3", "green", 0, 0, 0, 0, 0));
                        }
                        else {
                            db_teams.addTeam(new Teams("Ομάδα3", "green", 0, 0, 0, 0, 0));
                        }
                    }
                    else{
                        db_teams.addTeam(new Teams(team_name3.getText().toString(), "green", 0, 0, 0, 0, 0));
                    }
                    if (team_name4.getText().toString().trim().equals("")) {
                        if (getResources().getString(R.string.lang).equals("eng")) {
                            db_teams.addTeam(new Teams("Team4", "blue", 0, 0, 0, 0, 0));
                        }
                        else {
                            db_teams.addTeam(new Teams("Ομάδα4", "blue", 0, 0, 0, 0, 0));
                        }
                    }
                    else{
                        db_teams.addTeam(new Teams(team_name4.getText().toString(), "blue", 0, 0, 0, 0, 0));
                    }
                }

                db_teams.getAllTeams();

                goToNextActivity();
            }
        });
    }

    public void onBackPressed() {
    }

    private void goToPreviousActivity(){
        if (category == 0){
            MySQLiteHelperTeams db_del_teams = new MySQLiteHelperTeams(this);
            db_del_teams.dropTable();
            MySQLiteHelperGameSettings db_del_settings = new MySQLiteHelperGameSettings(this);
            db_del_settings.dropTable();

            Intent intent = new Intent(this, SettingsGameplayActivity.class);
            startActivity(intent);
        }
        else {
            MySQLiteHelperTeams db_del_teams = new MySQLiteHelperTeams(this);
            db_del_teams.dropTable();
            MySQLiteHelperWords db_del_words = new MySQLiteHelperWords(this);
            db_del_words.dropTable();

            Intent intent = new Intent(this, SettingsCategoriesActivity.class);
            startActivity(intent);
        }

    }

    private void goToNextActivity(){
        if (words_input == 0){
            Intent intent = new Intent(this, InfoBeforeNewRoundActivity.class);
            startActivity(intent);
        }
        else {
            Intent intent = new Intent(this, SettingsWordsActivity.class);
            startActivity(intent);
        }
    }
}
