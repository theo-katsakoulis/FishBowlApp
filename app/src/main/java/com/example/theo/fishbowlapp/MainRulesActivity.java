package com.example.theo.fishbowlapp;


import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainRulesActivity extends AppCompatActivity {
    Button back_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_rules);

        Typeface custom_font_title = Typeface.createFromAsset(getAssets(),  "fonts/VAG-HandWritten.otf");
        TextView rules_title = (TextView) findViewById(R.id.title_rules);
        rules_title.setTypeface(custom_font_title);

        rules_title.setGravity(Gravity.CENTER);
        TextView main_game = (TextView) findViewById(R.id.rules_game);
        TextView title_round1 = (TextView) findViewById(R.id.title_round1);
        TextView round1 = (TextView) findViewById(R.id.rules_round1);
        TextView title_round2 = (TextView) findViewById(R.id.title_round2);
        TextView round2 = (TextView) findViewById(R.id.rules_round2);
        TextView title_round3 = (TextView) findViewById(R.id.title_round3);
        TextView round3 = (TextView) findViewById(R.id.rules_round3);
        TextView rules_game_more = (TextView) findViewById(R.id.rules_game_more);
        TextView rules_winner = (TextView) findViewById(R.id.rules_winner);

        /*back_button = (Button)findViewById(R.id.back);

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMainActivity();
            }
        });*/
    }

    /*private void goToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }*/
}