package com.example.theo.fishbowlapp;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.theo.fishbowlapp.MySLQiteHelpers.GameSettings;
import com.example.theo.fishbowlapp.MySLQiteHelpers.MySQLiteHelperGameSettings;
import com.example.theo.fishbowlapp.MySLQiteHelpers.MySQLiteHelperRounds;
import com.example.theo.fishbowlapp.MySLQiteHelpers.MySQLiteHelperTeams;
import com.example.theo.fishbowlapp.MySLQiteHelpers.MySQLiteHelperWords;
import com.example.theo.fishbowlapp.MySLQiteHelpers.Teams;
import com.example.theo.fishbowlapp.MySLQiteHelpers.Words;


public class PlayingGameActivity extends AppCompatActivity {
    private Button next_word_button, mistake_word_button, found_word_button;
    TextView view_team, display_round, timer;
    int words_played = 1, words_found = 0;
    String team_name_playing;
    Words word_name;
    Teams team_playing;
    CountDownTimer myTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playing_game);

        /* get the name of the team that plays this round */
        final MySQLiteHelperRounds db_rounds = new MySQLiteHelperRounds(this);
        int team_id = db_rounds.getRound(1).getTeamPlaying();
        final MySQLiteHelperTeams db_teams = new MySQLiteHelperTeams(this);
        team_name_playing = db_teams.getTeamById(team_id).getName();


        mistake_word_button = (Button)findViewById(R.id.mistake_word_button);
        next_word_button = (Button)findViewById(R.id.next_word_button);
        found_word_button = (Button)findViewById(R.id.found_word_button);

        display_round = (TextView)findViewById(R.id.display_round);
        display_round.setText(getText(R.string.title_round) + " " + db_rounds.getRound(1).getRoundNum());

        timer = (TextView)findViewById(R.id.timer);

        view_team = (TextView)findViewById(R.id.display_team);
        view_team.setText(getText(R.string.title_team_playing) + " " + team_name_playing);


        final MySQLiteHelperWords db_words = new MySQLiteHelperWords(this);
        final int table_words_count = db_words.getWordsCount();

        /* get the words from the table and display them on screen */
        word_name = db_words.getRandomWord();
        while ("mistaken".equals(word_name.getWordState()) || "found".equals(word_name.getWordState())) {
            word_name = db_words.getRandomWord();
        }
        final String tmp = word_name.getWord();
        TextView textView = (TextView) findViewById(R.id.word);
        textView.setText(tmp);


        final MySQLiteHelperGameSettings db_game_settings = new MySQLiteHelperGameSettings(this);
        GameSettings game_settings = db_game_settings.getGameSettings(1);

        int sec = game_settings.getSeconds();

        myTimer = new CountDownTimer(sec*1000, 1000) {
            public void onTick(long millisUntilFinished) {
                timer.setText(getText(R.string.title_rem_secs) + " " + millisUntilFinished / 1000);

                if ((millisUntilFinished / 1000) == 11){
                    beepSound();
                }

                if ((millisUntilFinished / 1000) == 10){
                    timer.setTextColor(Color.RED);
                }
            }

            public void onFinish() {
                goToInfoBetweenRoundActivity();
            }
        }.start();


        mistake_word_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ++words_played;

                /* update the words state to mistaken and remove 1 point */
                team_playing = db_teams.getTeamByName(team_name_playing);
                int points = team_playing.getPoints();
                points--;
                if (points > 0) {
                    team_playing.setPoints(points);
                    db_teams.updateTeam(team_playing);
                }

                /* set the mistaken words to +1 and update team */
                team_playing.setMistakesRound(team_playing.getMistakesRound() + 1);
                team_playing.setMistakesOverall(team_playing.getMistakesOverall() + 1);
                db_teams.updateTeam(team_playing);
                //db_teams.getAllTeams();

                word_name.setWordState("mistaken");
                db_words.updateWord(word_name);
                //db_words.getAllWords();

                if (db_words.getWordsCount() == db_words.getWordsCountState()){
                    goToInfoBetweenRoundActivity();
                }
                else {

                    /* get a random word from the table and display it on screen */
                    word_name = db_words.getRandomWord();
                    while ("mistaken".equals(word_name.getWordState()) || "found".equals(word_name.getWordState())) {
                        word_name = db_words.getRandomWord();
                    }

                    final String tmp = word_name.getWord();
                    TextView textView = (TextView) findViewById(R.id.word);
                    textView.setText(tmp);
                }

            }
        });

        next_word_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (words_played > table_words_count){
                    goToInfoBetweenRoundActivity();
                }

                /* get a random word from the table and display it on screen */
                word_name = db_words.getRandomWord();
                while ("mistaken".equals(word_name.getWordState()) || "found".equals(word_name.getWordState())){
                    word_name = db_words.getRandomWord();
                }

                final String tmp = word_name.getWord();
                TextView textView = (TextView) findViewById(R.id.word);
                textView.setText(tmp);
            }
        });

        found_word_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ++words_played;
                words_found++;
                int points;

                /* add the points (different for each round) */
                team_playing = db_teams.getTeamByName(team_name_playing);
                if (db_rounds.getRound(1).getRoundNum() == 1){
                    points = team_playing.getPoints();
                    points = points +1;
                }
                else if (db_rounds.getRound(1).getRoundNum() == 2){
                    points = team_playing.getPoints();
                    points = points +2;
                }
                else{
                    points = team_playing.getPoints();
                    points = points +3;
                }

                /* set the found words to +1, add the points and update team */
                team_playing.setFoundRound(team_playing.getFoundRound() + 1);
                team_playing.setFoundOverall(team_playing.getFoundOverall() + 1);
                team_playing.setPoints(points);
                db_teams.updateTeam(team_playing);
                db_teams.getTeamByName(team_name_playing);

                /* update the word state to found */
                word_name.setWordState("found");
                db_words.updateWord(word_name);
                //db_words.getAllWords();

                if (db_words.getWordsCount() == db_words.getWordsCountState()){
                    goToInfoBetweenRoundActivity();
                }
                else {

                    /* get a random word from the table and display it on screen */
                    word_name = db_words.getRandomWord();
                    while ("mistaken".equals(word_name.getWordState()) || "found".equals(word_name.getWordState())) {
                        word_name = db_words.getRandomWord();
                    }

                    final String tmp = word_name.getWord();
                    TextView textView = (TextView) findViewById(R.id.word);
                    textView.setText(tmp);
                }

            }
        });
    }

    public void onBackPressed() {
    }

    private void beepSound(){
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.beep);
        mp.start();
    }

    /* paei sti selida endiamesa stous gyrous */
    private void goToInfoBetweenRoundActivity() {
        myTimer.cancel();
        //myTimer = null;
        Intent intent = new Intent(this, InfoBetweenRoundFirstActivity.class);
        intent.putExtra("WORDS_FOUND", words_found);
        startActivity(intent);
    }

}
