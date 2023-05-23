package com.example.unblockme10x10;

import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class ActivityHelpHowToPlay extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_how_to_play);

        Button backBtn = findViewById(R.id.howToPlay_backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AudioPlayer.playButtonPressSound(ActivityHelpHowToPlay.this);
                finish();
            }
        });

    }
}