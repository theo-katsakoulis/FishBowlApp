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


public class InfoBeforeNewRoundActivity extends AppCompatActivity {
    TextView view_round, view_details, team_playing, show_rules;
    String team_name_playing;
    Button show_rules_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_before_new_round);

        LinearLayout rlayout = (LinearLayout) findViewById(R.id.whole_screen);

        final MySQLiteHelperRounds db_rounds = new MySQLiteHelperRounds(this);
        view_round = (TextView) findViewById(R.id.display_round);
        view_round.setText((getText(R.string.title_ready) + " " + db_rounds.getRound(1).getRoundNum()));

        /* get the name of the team that plays this round */
        int team_id = db_rounds.getRound(1).getTeamPlaying();
        final MySQLiteHelperTeams db_teams = new MySQLiteHelperTeams(this);
        team_name_playing = db_teams.getTeamById(team_id).getName();

        team_playing = (TextView) findViewById(R.id.display_team_playing);
        team_playing.setText((getText(R.string.title_team) + " " + team_name_playing + " " + (getText(R.string.title_is_playing))));


        /* display details for each round */
        if (db_rounds.getRound(1).getRoundNum() == 1){
            view_details = (TextView) findViewById(R.id.display_details);
            view_details.setText(getText(R.string.title_found_points1));
        }
        else if (db_rounds.getRound(1).getRoundNum() == 2){
            view_details = (TextView) findViewById(R.id.display_details);
            view_details.setText(getText(R.string.title_found_points2));
        }
        else {
            view_details = (TextView) findViewById(R.id.display_details);
            view_details.setText(getText(R.string.title_found_points3));
        }


        rlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToPlayingGameActivity();
            }
        });

        show_rules_button = (Button)findViewById(R.id.show_rules_button);

        show_rules_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show_rules = (TextView) findViewById(R.id.display_rules);

                /* show/hide rules textView when button is clicked */
                if (show_rules.getVisibility() == View.VISIBLE) {
                    show_rules.setVisibility(View.INVISIBLE);
                }
                else {
                    show_rules.setVisibility(View.VISIBLE);
                }

                if (db_rounds.getRound(1).getRoundNum() == 1){
                    show_rules.setText(getString(R.string.rules_round1) + "\n");
                }
                else if (db_rounds.getRound(1).getRoundNum() == 2){
                    show_rules.setText(getString(R.string.rules_round2) + "\n");
                }
                else {
                    show_rules.setText(getString(R.string.rules_round3) + "\n");
                }

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
