package com.example.unblockme10x10;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class ActivityHelp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        Button backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AudioPlayer.playButtonPressSound(ActivityHelp.this);
                finish();
            }
        });


        Button generalsBtn = findViewById(R.id.help_general_button);
        generalsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AudioPlayer.playButtonPressSound(ActivityHelp.this);
                generalsActivitySetter();
            }
        });

        Button inputFormatBtn = findViewById(R.id.help_inputFormat_button);
        inputFormatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AudioPlayer.playButtonPressSound(ActivityHelp.this);
                inputFormatActivitySetter();
            }
        });

        Button howToPlayBtn = findViewById(R.id.help_howToPlay_button);
        howToPlayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AudioPlayer.playButtonPressSound(ActivityHelp.this);
                howToPlayActivitySetter();
            }
        });

        Button aboutAppBtn = findViewById(R.id.help_aboutApp_button);
        aboutAppBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AudioPlayer.playButtonPressSound(ActivityHelp.this);
                aboutAppActivitySetter();
            }
        });

    }

    public void generalsActivitySetter() {
        Intent intent = new Intent(this, ActivityHelpGeneral.class);
        startActivity(intent);
    }

    public void inputFormatActivitySetter() {
        Intent intent = new Intent(this, ActivityHelpInputFormat.class);
        startActivity(intent);
    }

    public void howToPlayActivitySetter() {
        Intent intent = new Intent(this, ActivityHelpHowToPlay.class);
        startActivity(intent);
    }

    public void aboutAppActivitySetter() {
        Intent intent = new Intent(this, ActivityHelpAbout.class);
        startActivity(intent);
    }
}