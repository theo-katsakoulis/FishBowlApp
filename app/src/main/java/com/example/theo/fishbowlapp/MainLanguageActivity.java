package com.example.theo.fishbowlapp;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.Locale;


public class MainLanguageActivity extends AppCompatActivity {
    private Button greek_button, english_button, back_button;
    TextView language;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_language);

        Typeface custom_font_title = Typeface.createFromAsset(getAssets(),  "fonts/VAG-HandWritten.otf");
        language = (TextView)findViewById(R.id.language);
        language.setTypeface(custom_font_title);

        english_button = (Button)findViewById(R.id.english_button);
        greek_button = (Button)findViewById(R.id.greek_button);
        back_button = (Button)findViewById(R.id.back);

        english_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Configuration config = new Configuration();
                Locale locale;
                config.locale = Locale.ENGLISH;
                getResources().updateConfiguration(config, null);

                goToMainActivity();
            }
        });

        greek_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Configuration config = new Configuration();
                Locale locale;
                locale = new Locale("el");
                config.locale =locale;
                getResources().updateConfiguration(config, null);

                goToMainActivity();
            }
        });

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMainActivity();
            }
        });
    }

    private void goToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
