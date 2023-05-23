package com.example.unblockme10x10;

import android.content.Intent;
import android.net.Uri;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class ActivityHelpAbout extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_about);

        Button backBtn = findViewById(R.id.aboutBackbtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AudioPlayer.playButtonPressSound(ActivityHelpAbout.this);
                finish();
            }
        });

        Button githubButton = findViewById(R.id.githubButton);
        githubButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AudioPlayer.playButtonPressSound(ActivityHelpAbout.this);
                Uri uri = Uri.parse("https://github.com/Javadi1729/UnblockMe10x10/tree/master");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        Button pashazadehWebsiteButton = findViewById(R.id.pashazadehWebsiteButton);
        pashazadehWebsiteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AudioPlayer.playButtonPressSound(ActivityHelpAbout.this);
                Uri uri = Uri.parse("https://asatid.tabrizu.ac.ir/fa/pages/default.aspx?pashazadeh");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        Button javadiWebsiteButton = findViewById(R.id.javadiWebsiteButton);
        javadiWebsiteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AudioPlayer.playButtonPressSound(ActivityHelpAbout.this);
                Uri uri = Uri.parse("https://www.bing.com");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home)
        {
            AudioPlayer.playButtonPressSound(ActivityHelpAbout.this);
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

}