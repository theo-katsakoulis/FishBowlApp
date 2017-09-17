package com.example.theo.fishbowlapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.theo.fishbowlapp.MySLQiteHelpers.MySQLiteHelperGameSettings;
import com.example.theo.fishbowlapp.MySLQiteHelpers.MySQLiteHelperTeams;
import com.example.theo.fishbowlapp.MySLQiteHelpers.MySQLiteHelperWords;
import com.example.theo.fishbowlapp.MySLQiteHelpers.Words;

import static java.lang.Integer.parseInt;

public class SettingsWordsActivity extends AppCompatActivity {
    private Button word_button;
    EditText word;
    TextView view_team, view_player;
    int words_submit = 1, words_per_team, words_team1 = 1, words_team2 = 1, words_team3 = 1, words_team4 = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_words);

        word_button = (Button)findViewById(R.id.button);
        view_team = (TextView)findViewById(R.id.display_team);
        view_team.setMovementMethod(new ScrollingMovementMethod());

        view_player = (TextView)findViewById(R.id.display_player);
        view_player.setMovementMethod(new ScrollingMovementMethod());

        final MySQLiteHelperWords db_words = new MySQLiteHelperWords(this);

        final MySQLiteHelperGameSettings db_settings = new MySQLiteHelperGameSettings(this);
        words_per_team = db_settings.getGameSettings(1).getWordsPerPl();

        final MySQLiteHelperTeams db_teams = new MySQLiteHelperTeams(this);

        final int words_to_enter = db_settings.getGameSettings(1).getWordsPerPl() * db_teams.getTeamsCount();
        view_player.setText(getText(R.string.title_team) + ": " + db_teams.getTeamById(1).getName() + " " + getText(R.string.enter_word1) + " " + words_per_team);

        word_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                word = (EditText)findViewById(R.id.player_num);

                if (word.getText().toString().trim().equals("")) {
                    word.setError(getText(R.string.words_wr_error));
                }
                else {

                    if (words_submit <= words_to_enter) {
                        db_words.addWord(new Words(word.getText().toString(), "none"));

                        words_submit++;
                        if (words_submit > words_to_enter) {
                            goToBeforeRoundScreenActivity();
                        }
                        else {
                            if (words_submit > (3*words_per_team)) {
                                view_player.setText(getText(R.string.title_team) + ": " + db_teams.getTeamById(4).getName()  + " " + getText(R.string.enter_word) + " " +  words_team4 + " " + getText(R.string.of) + " "  + words_per_team);
                                words_team4++;
                            }
                            else if (words_submit > (2*words_per_team)) {
                                view_player.setText(getText(R.string.title_team) + ": " + db_teams.getTeamById(3).getName() + " " + getText(R.string.enter_word) + " " + words_team3 + " " + getText(R.string.of) + " " + words_per_team);
                                words_team3++;
                            }
                            else if (words_submit > words_per_team) {
                                view_player.setText(getText(R.string.title_team) + ": " + db_teams.getTeamById(2).getName() + " " + getText(R.string.enter_word) + " " + words_team2 + " " + getText(R.string.of) + " " + words_per_team);
                                words_team2++;
                            }
                            else {
                                words_team1++;
                                view_player.setText(getText(R.string.title_team) + ": " + db_teams.getTeamById(1).getName() + " " + getText(R.string.enter_word) + " " + words_team1 + " " + getText(R.string.of) + " " + words_per_team);
                            }
                        }
                    }
                    else {
                        goToBeforeRoundScreenActivity();
                    }
                    db_words.getAllWords();
                }
                word.setText("");
            }
        });
    }

    public void onBackPressed() {
        MySQLiteHelperTeams db_del_teams = new MySQLiteHelperTeams(this);
        db_del_teams.dropTable();
        final MySQLiteHelperWords db_del_words = new MySQLiteHelperWords(this);
        db_del_words.dropTable();
        Intent intent = new Intent(this, SettingsTeamsActivity.class);
        startActivity(intent);
    }

    /* goe */
    private void goToBeforeRoundScreenActivity(){
        Intent intent = new Intent(this, InfoBeforeNewRoundActivity.class);
        startActivity(intent);
    }
}
