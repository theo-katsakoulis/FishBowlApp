package com.example.theo.fishbowlapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.theo.fishbowlapp.MySLQiteHelpers.MySQLiteHelperRounds;
import com.example.theo.fishbowlapp.MySLQiteHelpers.MySQLiteHelperTeams;
import com.example.theo.fishbowlapp.MySLQiteHelpers.MySQLiteHelperWords;
import com.example.theo.fishbowlapp.MySLQiteHelpers.Rounds;
import com.example.theo.fishbowlapp.MySLQiteHelpers.Teams;

import static android.graphics.Typeface.BOLD;


public class InfoEndOfRoundActivity extends AppCompatActivity {
    TextView team_stats1, team_stats2, team_stats3, team_stats4, round_num;
    Button continue_button;
    Teams team;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_end_of_round);

        final MySQLiteHelperWords db_words = new MySQLiteHelperWords(this);
        db_words.changeStateOfWordFound();
        db_words.getAllWords();

        MySQLiteHelperRounds db_rounds = new MySQLiteHelperRounds(this);
        round_num = (TextView) findViewById(R.id.display_round_num);
        round_num.setText(getText(R.string.end_of_round) + " " + db_rounds.getRound(1).getRoundNum());

        /* update the game rounds to +1 */
        Rounds round = db_rounds.getRound(1);
        int round_num = round.getRoundNum() + 1;
        round.setRoundNum(round_num);
        db_rounds.updateRound(round);
        db_rounds.getAllRounds();

        if (db_rounds.getRound(1).getRoundNum() > 3){
            goToEndOfGameActivity();
        }

        MySQLiteHelperTeams db_teams = new MySQLiteHelperTeams(this);
        int teams_cnt = db_teams.getTeamsCount();

        continue_button = (Button) findViewById(R.id.continue_button);

        /* print the teams stats for rounds *
        team_stats1 = (TextView) findViewById(R.id.display_stats1);
        team_stats1.setText(getText(R.string.title_team) + ": " + db_teams.getTeamById(1).getName() + " , " + getText(R.string.points) + ": " + db_teams.getTeamById(1).getPoints() + "\n");
                //" , Word Mistakes: " + db_teams.getTeamById(1).getMistakesRound() + " , Found Words: " + db_teams.getTeamById(1).getFoundRound() + "\n");

        team_stats2 = (TextView) findViewById(R.id.display_stats2);
        team_stats2.setText(getText(R.string.title_team) + ": " + db_teams.getTeamById(2).getName() + " , " + getText(R.string.points) + ": " + db_teams.getTeamById(2).getPoints() + "\n");
                //" , Word Mistakes: " + db_teams.getTeamById(2).getMistakesRound() + " , Found Words: " + db_teams.getTeamById(2).getFoundRound() + "\n");

        /* update the words found and mistake for each round to 0 *
        team = db_teams.getTeamById(1);
        team.setMistakesRound(0);
        team.setFoundRound(0);
        db_teams.updateTeam(team);

        team = db_teams.getTeamById(2);
        team.setMistakesRound(0);
        team.setFoundRound(0);
        db_teams.updateTeam(team);

        if (teams_cnt == 3){
            team_stats3 = (TextView) findViewById(R.id.display_stats3);
            team_stats3.setText(getText(R.string.team) + ": " + db_teams.getTeamById(3).getName() + " , " + getText(R.string.title_team) + " " + db_teams.getTeamById(3).getPoints() + "\n");



            /* update the words found and mistake for each round to 0 *
            team = db_teams.getTeamById(3);
            team.setMistakesRound(0);
            team.setFoundRound(0);
            db_teams.updateTeam(team);
        }

        if (teams_cnt == 4){
            team_stats3 = (TextView) findViewById(R.id.display_stats3);
            team_stats3.setText(getText(R.string.title_team) + ": " + db_teams.getTeamById(3).getName() + " , " + getText(R.string.title_team) + " " + db_teams.getTeamById(3).getPoints() + "\n");
            team_stats4 = (TextView) findViewById(R.id.display_stats4);
            team_stats4.setText(getText(R.string.title_team) + ": " + db_teams.getTeamById(4).getName() + " , " + getText(R.string.title_team) + " " + db_teams.getTeamById(4).getPoints() + "\n");


            /* update the words found and mistake for each round to 0 *
            team = db_teams.getTeamById(3);
            team.setMistakesRound(0);
            team.setFoundRound(0);
            db_teams.updateTeam(team);

            team = db_teams.getTeamById(4);
            team.setMistakesRound(0);
            team.setFoundRound(0);
            db_teams.updateTeam(team);
        }*/


        TableLayout ll = (TableLayout) findViewById(R.id.display_table);
        TableRow row = new TableRow(this);
        row.setBackgroundColor(Color.parseColor("#f4695b"));

        TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
        row.setLayoutParams(lp);
        TextView tv = new TextView(this);

        TextView team_name = new TextView(this);
        TextView words_found = new TextView(this);
        TextView words_mistake = new TextView(this);
        TextView round_points = new TextView(this);

        team_name.setTypeface(null, BOLD);
        team_name.setTextSize(18);
        //team_name.setTextColor(0xFFFFFF);
        team_name.setText(getString(R.string.team));

        words_found.setTypeface(null, BOLD);
        words_found.setTextSize(18);
        words_found.setText(getString(R.string.words_found));

        /*words_mistake.setTypeface(null, BOLD);
        words_mistake.setTextSize(18);
        words_mistake.setText(getString(R.string.words_mistake));*/

        round_points.setTypeface(null, BOLD);
        round_points.setTextSize(18);
        round_points.setText(getString(R.string.round_points));

        row.addView(team_name);
        row.addView(words_found);
        //row.addView(words_mistake);
        row.addView(round_points);
        ll.addView(row, 0);

        for (int i = 1; i <= teams_cnt+1; i++) {

            row = new TableRow(this);

            if (i == teams_cnt+1){
                    team_name.setTextSize(18);
                    team_name = new TextView(this);
                    team_name.setText(" ");

                    words_found.setTextSize(18);
                    words_found = new TextView(this);
                    words_found.setText(" ");

                    /*words_mistake.setTextSize(18);
                    words_mistake = new TextView(this);
                    words_mistake.setText(" ");*/

                    round_points.setTextSize(18);
                    round_points = new TextView(this);
                    round_points.setText(" ");

                    row.addView(team_name);
                    row.addView(words_found);
                    //row.addView(words_mistake);
                    row.addView(round_points);
                    ll.addView(row, i);
                break;
            }

            team_name.setTextSize(18);
            team_name = new TextView(this);
            team_name.setText(" " + db_teams.getTeamById(i).getName());

            words_found.setTextSize(18);
            words_found = new TextView(this);
            words_found.setText(" " + db_teams.getTeamById(i).getFoundRound());

            /*words_mistake.setTextSize(18);
            words_mistake = new TextView(this);
            words_mistake.setText(" " + db_teams.getTeamById(i).getMistakesRound());*/

            round_points.setTextSize(18);
            round_points = new TextView(this);
            round_points.setText(" " + db_teams.getTeamById(i).getFoundRound() * (db_rounds.getRound(1).getRoundNum()-1));

            row.addView(team_name);
            row.addView(words_found);
            //row.addView(words_mistake);
            row.addView(round_points);
            ll.addView(row, i);

            /* update the words found and mistake for each round to 0 */
            team = db_teams.getTeamById(i);
            team.setMistakesRound(0);
            team.setFoundRound(0);
            db_teams.updateTeam(team);
        }


        continue_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToBeforeRoundScreenActivity();
            }
        });

    }

    public void onBackPressed() {
    }

    private void goToBeforeRoundScreenActivity() {
        Intent intent = new Intent(this, InfoBeforeNewRoundActivity.class);
        startActivity(intent);
    }

    private void goToEndOfGameActivity() {
        Intent intent = new Intent(this, InfoEndOfGameActivity.class);
        startActivity(intent);
    }
}