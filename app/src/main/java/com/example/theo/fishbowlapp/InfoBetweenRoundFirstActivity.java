package com.example.theo.fishbowlapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.theo.fishbowlapp.MySLQiteHelpers.MySQLiteHelperRounds;
import com.example.theo.fishbowlapp.MySLQiteHelpers.MySQLiteHelperTeams;
import com.example.theo.fishbowlapp.MySLQiteHelpers.MySQLiteHelperWords;
import com.example.theo.fishbowlapp.MySLQiteHelpers.Rounds;


public class InfoBetweenRoundFirstActivity extends AppCompatActivity {
    TextView team_stats, round_num;
    Button continue_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_between_round_first);

        final MySQLiteHelperWords db_words = new MySQLiteHelperWords(this);
        db_words.changeStateOfWordMistaken();
        //db_words.getAllWords();

        int words_found = 0;

        Intent iin = getIntent();
        Bundle b = iin.getExtras();

        if(b != null) {
            words_found = (int) b.get("WORDS_FOUND");
        }

        MySQLiteHelperRounds db_rounds = new MySQLiteHelperRounds(this);
        round_num = (TextView) findViewById(R.id.display_round_num);
        round_num.setText(getText(R.string.title_round) + " " + db_rounds.getRound(1).getRoundNum());

        continue_button = (Button)findViewById(R.id.continue_button);

        /* get the name of the team that plays this round */
        int team_id = db_rounds.getRound(1).getTeamPlaying();
        final MySQLiteHelperTeams db_teams = new MySQLiteHelperTeams(this);
        String team_playing = db_teams.getTeamById(team_id).getName();


        int rem_words = db_words.getWordsCount() - db_words.getCountStateFound();

        Log.d("getAllGameBetween: ", team_playing);

        if (db_words.getCountStateFound() == db_words.getWordsCount()){
            goEndOfRoundActivity();
        }

        team_stats = (TextView)findViewById(R.id.display_round_stats);

        String words_found_format = getResources().getString(R.string.found_words);
        String found_msg = String.format(words_found_format, team_playing, words_found);

        String rem_words_format = getResources().getString(R.string.rem_words);
        String rem_msg = String.format(rem_words_format, rem_words, db_rounds.getRound(1).getRoundNum());

        team_stats.setText(found_msg + "\n\n" + rem_msg);


        /* update the team playing the next round */
        int teams_cnt = db_teams.getTeamsCount();
        int team_playing_id = db_rounds.getRound(1).getTeamPlaying();
        if (team_playing_id + 1 > teams_cnt){
            team_playing_id = 1;
        }
        else {
            team_playing_id++;
        }
        Rounds update_round = db_rounds.getRound(1);
        update_round.setTeamPlaying(team_playing_id);
        db_rounds.updateRound(update_round);


        continue_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToInfoBetweenRoundSecondActivity();
            }
        });

    }

    public void onBackPressed() {
    }

    private void goToInfoBetweenRoundSecondActivity() {
        Intent intent = new Intent(this, InfoBetweenRoundSecondActivity.class);
        startActivity(intent);
    }

    private void goEndOfRoundActivity() {
        Intent intent = new Intent(this, InfoEndOfRoundActivity.class);
        startActivity(intent);
    }
}
