package com.example.theo.fishbowlapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.theo.fishbowlapp.MySLQiteHelpers.MySQLiteHelperRounds;
import com.example.theo.fishbowlapp.MySLQiteHelpers.MySQLiteHelperTeams;


public class InfoBetweenRoundSecondActivity extends AppCompatActivity {
    TextView view_round, view_details, team_playing, show_rules;
    String team_name_playing;
    Button show_rules_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_between_round_second);

        LinearLayout rlayout = (LinearLayout) findViewById(R.id.whole_screen);

        final MySQLiteHelperRounds db_rounds = new MySQLiteHelperRounds(this);

        /* get the name of the team that plays this round */
        int team_id = db_rounds.getRound(1).getTeamPlaying();
        final MySQLiteHelperTeams db_teams = new MySQLiteHelperTeams(this);
        team_name_playing = db_teams.getTeamById(team_id).getName();

        team_playing = (TextView) findViewById(R.id.display_team);
        team_playing.setText((getText(R.string.title_team) + ": " + team_name_playing + " " + (getText(R.string.title_ready))));

        rlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToPlayingGameActivity();
            }
        });
    }

    public void onBackPressed() {
    }


    private void goToPlayingGameActivity() {
        Intent intent = new Intent(this, PlayingGameActivity.class);
        startActivity(intent);
    }
}
