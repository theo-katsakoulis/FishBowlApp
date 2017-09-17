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

import com.example.theo.fishbowlapp.MySLQiteHelpers.MySQLiteHelperGameSettings;
import com.example.theo.fishbowlapp.MySLQiteHelpers.MySQLiteHelperRounds;
import com.example.theo.fishbowlapp.MySLQiteHelpers.MySQLiteHelperTeams;
import com.example.theo.fishbowlapp.MySLQiteHelpers.MySQLiteHelperWords;
import com.example.theo.fishbowlapp.MySLQiteHelpers.Rounds;

import static android.graphics.Typeface.BOLD;
import static com.example.theo.fishbowlapp.R.string.team;


public class InfoEndOfGameActivity extends AppCompatActivity {
    TextView team_stats1, team_stats2, team_stats3, team_stats4, display_team_won;
    Button start_new_game_button;
    int team1_points, team2_points, team3_points, team4_points;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_end_of_game);

        MySQLiteHelperTeams db_teams = new MySQLiteHelperTeams(this);
        int teams_cnt = db_teams.getTeamsCount();

        start_new_game_button = (Button) findViewById(R.id.start_new_game_button);

        team1_points = db_teams.getTeamById(1).getPoints();
        team2_points = db_teams.getTeamById(2).getPoints();

        MySQLiteHelperGameSettings db_game_set = new MySQLiteHelperGameSettings(this);

        /* find the team with most points */
        int i, max_points = 0, win_team_id = 0;
        for (i = 1; i <= db_game_set.getGameSettings(1).getTeamsNum(); i++){
            if (db_teams.getTeamById(i).getPoints() > max_points){
                max_points = db_teams.getTeamById(i).getPoints();
                win_team_id = db_teams.getTeamById(i).getId();
            }
        }

        /* in case of draw get the ids of the draw teams */
        int draw_num = 0;
        int[] draw_teams = new int[]{0,0,0,0};
        for (i = 1; i <= db_game_set.getGameSettings(1).getTeamsNum(); i++){
            if (db_teams.getTeamById(i).getPoints() == max_points){
                draw_teams[draw_num] = db_teams.getTeamById(i).getId();
                draw_num++;
            }
        }

        if (draw_num > 1) {
            display_team_won = (TextView) findViewById(R.id.display_team_won);
            if (draw_num == 2) {
                String draw2_format = getResources().getString(R.string.draw_2);
                String draw2_msg = String.format(draw2_format, db_teams.getTeamById(draw_teams[0]).getName(), db_teams.getTeamById(draw_teams[1]).getName(), max_points);
                display_team_won.setText(draw2_msg + "\n");
            }
            else if (draw_num == 3) {
                String draw3_format = getResources().getString(R.string.draw_3);
                String draw3_msg = String.format(draw3_format, db_teams.getTeamById(draw_teams[0]).getName(), db_teams.getTeamById(draw_teams[1]).getName(), db_teams.getTeamById(draw_teams[2]).getName(), max_points);
                display_team_won.setText(draw3_msg + "\n");
            }
            else {
                display_team_won.setText(getText(R.string.draw_all) + " " + max_points + " " + getText(R.string.points) + "!\n");
            }
        }
        else {
            display_team_won = (TextView) findViewById(R.id.display_team_won);
            display_team_won.setText(db_teams.getTeamById(win_team_id).getName() + " " + getText(R.string.won) + " " + db_teams.getTeamById(win_team_id).getPoints() + " " + getText(R.string.points) + "!\n");
        }

        /* print the teams stats for rounds *
        team_stats1 = (TextView) findViewById(R.id.display_stats1);
        team_stats1.setText(db_teams.getTeamById(1).getName() + " , " + getText(R.string.points) + ": " + db_teams.getTeamById(1).getPoints() + "\n");

        team_stats2 = (TextView) findViewById(R.id.display_stats2);
        team_stats2.setText(db_teams.getTeamById(2).getName() +  " , " + getText(R.string.points) + ": "  + db_teams.getTeamById(2).getPoints() + "\n");

        if (teams_cnt == 3){
            team_stats3 = (TextView) findViewById(R.id.display_stats3);
            team_stats3.setText(db_teams.getTeamById(3).getName() +  " , " + getText(R.string.points) + ": "  + db_teams.getTeamById(3).getPoints() + "\n");
        }

        if (teams_cnt == 4){
            team_stats3 = (TextView) findViewById(R.id.display_stats3);
            team_stats3.setText(db_teams.getTeamById(3).getName() +  " , " + getText(R.string.points) + ": "  + db_teams.getTeamById(3).getPoints() + "\n");
            team_stats4 = (TextView) findViewById(R.id.display_stats4);
            team_stats4.setText(db_teams.getTeamById(4).getName() +  " , " + getText(R.string.points) + ": "  + db_teams.getTeamById(4).getPoints() + "\n");
        }*/

        TableLayout ll = (TableLayout) findViewById(R.id.display_table);
        TableRow row = new TableRow(this);
        row.setBackgroundColor(Color.parseColor("#f4695b"));

        TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
        row.setLayoutParams(lp);
        TextView tv = new TextView(this);

        TextView team_name = new TextView(this);
        TextView overall_found = new TextView(this);
        TextView overall_mistakes = new TextView(this);
        TextView overall_points = new TextView(this);

        team_name.setTypeface(null, BOLD);
        team_name.setTextSize(18);
        //team_name.setTextColor(0xFFFFFF);
        team_name.setText(getString(team));

        overall_found.setTypeface(null, BOLD);
        overall_found.setTextSize(18);
        overall_found.setText(getString(R.string.overall_found));

        /*overall_mistakes.setTypeface(null, BOLD);
        overall_mistakes.setTextSize(18);
        overall_mistakes.setText(getString(R.string.overall_mistakes));*/

        overall_points.setTypeface(null, BOLD);
        overall_points.setTextSize(18);
        overall_points.setText(getString(R.string.overall_points));

        row.addView(team_name);
        row.addView(overall_found);
        //row.addView(overall_mistakes);
        row.addView(overall_points);
        ll.addView(row, 0);

        for (int j = 1; j <= teams_cnt+1; j++) {

            row = new TableRow(this);

            if (j == teams_cnt+1){
                team_name.setTextSize(18);
                team_name = new TextView(this);
                team_name.setText(" ");

                overall_found.setTextSize(18);
                overall_found = new TextView(this);
                overall_found.setText(" ");

                /*overall_mistakes.setTextSize(18);
                overall_mistakes = new TextView(this);
                overall_mistakes.setText(" ");*/

                overall_points.setTextSize(18);
                overall_points = new TextView(this);
                overall_points.setText(" ");

                row.addView(team_name);
                row.addView(overall_found);
                //row.addView(overall_mistakes);
                row.addView(overall_points);
                ll.addView(row, j);
                break;
            }

            team_name.setTextSize(18);
            team_name = new TextView(this);
            team_name.setText(" " + db_teams.getTeamById(j).getName());

            overall_found.setTextSize(18);
            overall_found = new TextView(this);
            overall_found.setText(" " + db_teams.getTeamById(j).getFoundOverall());

            /*overall_mistakes.setTextSize(18);
            overall_mistakes = new TextView(this);
            overall_mistakes.setText(" " + db_teams.getTeamById(j).getMistakesOverall());*/

            overall_points.setTextSize(18);
            overall_points = new TextView(this);
            overall_points.setText(" " + db_teams.getTeamById(j).getPoints());

            row.addView(team_name);
            row.addView(overall_found);
            //row.addView(overall_mistakes);
            row.addView(overall_points);
            ll.addView(row, j);
        }

        start_new_game_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToInsertGamesActivity();
            }
        });

    }

    public void onBackPressed() {
    }

    private void goToInsertGamesActivity() {
        /* drop all tables before the new game starts */
        MySQLiteHelperGameSettings db_del_settings = new MySQLiteHelperGameSettings(this);
        db_del_settings.dropTable();

        MySQLiteHelperTeams db_del_teams = new MySQLiteHelperTeams(this);
        db_del_teams.dropTable();

        MySQLiteHelperRounds db_del_rounds = new MySQLiteHelperRounds(this);
        db_del_rounds.dropTable();

        MySQLiteHelperRounds db_rounds = new MySQLiteHelperRounds(this);
        db_rounds.addRound(new Rounds(1, 1));

        final MySQLiteHelperWords db_del_words = new MySQLiteHelperWords(this);
        db_del_words.dropTable();

        Intent intent = new Intent(this, SettingsGameplayActivity.class);
        startActivity(intent);
    }
}