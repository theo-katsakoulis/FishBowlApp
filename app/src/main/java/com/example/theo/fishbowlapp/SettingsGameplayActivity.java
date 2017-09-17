package com.example.theo.fishbowlapp;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.*;
import android.support.v7.app.AppCompatActivity;
import android.view.*;
import android.widget.*;
import android.widget.EditText;

import com.example.theo.fishbowlapp.MySLQiteHelpers.GameSettings;
import com.example.theo.fishbowlapp.MySLQiteHelpers.MySQLiteHelperGameSettings;


public class SettingsGameplayActivity extends AppCompatActivity{
    private Button next_button, back_button;
    private int words_input = 0;
    EditText words_per_team;
    private RadioGroup radio_teams_num, radio_seconds, radio_words;
    private RadioButton radio_teams_num_button, radio_seconds_button, radio_words_button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_gameplay);

        /* custom font for textviews */
        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/Quicksand-BoldItalic.otf");
        TextView view_teams = (TextView) findViewById(R.id.textViewTeams);
        view_teams.setTypeface(custom_font);
        TextView view_time = (TextView) findViewById(R.id.textViewTime);
        view_time.setTypeface(custom_font);
        TextView view_words_input = (TextView) findViewById(R.id.textViewWordsInput);
        view_words_input.setTypeface(custom_font);
        TextView view_words_num = (TextView) findViewById(R.id.textViewWordsNum);
        view_words_num.setTypeface(custom_font);

        radio_teams_num = (RadioGroup)findViewById(R.id.radioGroupTeams);
        radio_seconds = (RadioGroup)findViewById(R.id.radioGroupSeconds);
        radio_words = (RadioGroup)findViewById(R.id.radioGroupWordsInput);

        next_button = (Button)findViewById(R.id.next);
        back_button = (Button)findViewById(R.id.back);

        final MySQLiteHelperGameSettings db_gs = new MySQLiteHelperGameSettings(this);

        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                words_per_team = (EditText)findViewById(R.id.words_per_team);
                //teams_num = (EditText)findViewById(R.id.teams_num);
                //seconds = (EditText)findViewById(R.id.seconds);
                if (words_per_team.getText().toString().trim().equals("")) {
                    words_per_team.setError(getText(R.string.words_num_error));
                }
                else {

                    /* get radio value of number of teams */
                    int selected_teamsId = radio_teams_num.getCheckedRadioButtonId();
                    radio_teams_num_button = (RadioButton) findViewById(selected_teamsId);
                    int teams_num = radio_teams_num.indexOfChild(radio_teams_num_button);
                    teams_num = teams_num + 2;

                    /* get radio value of rounds seconds */
                    int selected_secondsId = radio_seconds.getCheckedRadioButtonId();
                    radio_seconds_button = (RadioButton) findViewById(selected_secondsId);
                    int seconds = radio_seconds.indexOfChild(radio_seconds_button);
                    seconds++;

                    if ((Integer.parseInt(words_per_team.getText().toString()) > 3) && (Integer.parseInt(words_per_team.getText().toString()) < 21)){

                        if (seconds == 1) {
                            db_gs.addGameSettings(new GameSettings(teams_num, Integer.parseInt(words_per_team.getText().toString()), 60));
                        } else if (seconds == 2) {
                            db_gs.addGameSettings(new GameSettings(teams_num, Integer.parseInt(words_per_team.getText().toString()), 90));
                        } else {
                            db_gs.addGameSettings(new GameSettings(teams_num, Integer.parseInt(words_per_team.getText().toString()), 120));
                        }

                        db_gs.getAllGameSettings();

                        /* get radio value of words input method */
                        int selected_words_method = radio_words.getCheckedRadioButtonId();
                        radio_words_button = (RadioButton) findViewById(selected_words_method);
                        words_input = radio_words.indexOfChild(radio_words_button);

                        goToNextActivity();
                    }
                    else {
                        words_per_team.setError(getText(R.string.words_error));
                    }
                }
            }
        });

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMainActivity();
            }
        });
    }

    public void onBackPressed() {
    }


    private void goToMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


    private void goToNextActivity(){
        if (words_input == 0){
            Intent intent = new Intent(this, SettingsCategoriesActivity.class);
            intent.putExtra("WORDS_INPUT", words_input);
            startActivity(intent);
        }
        else {
            Intent intent = new Intent(this, SettingsTeamsActivity.class);
            intent.putExtra("WORDS_INPUT", words_input);
            startActivity(intent);
        }
    }
}
