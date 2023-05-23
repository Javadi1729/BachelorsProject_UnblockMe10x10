package com.example.unblockme10x10;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static android.content.ContentValues.TAG;

public class ActivityHome extends AppCompatActivity {
    private static final int READ_REQUEST_CODE = 42;
    public static final String EXTRA_INITIAL_STATE = "30";
    public static final String EXTRA_EXIT_SECTION = "40";
    public static final String EXTRA_LEVEL = "1";

    int levelNumber;

    Button musicButton;

    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        musicButton = findViewById(R.id.musicController);
        musicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AudioPlayer.playButtonPressSound(ActivityHome.this);
                controlMusic(view);
            }
        });

        Button helpBtn = findViewById(R.id.helpButton);
        helpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AudioPlayer.playButtonPressSound(ActivityHome.this);
                setHelpPage(view);
            }
        });

        Button exitButton = findViewById(R.id.exitButton);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AudioPlayer.playButtonPressSound(ActivityHome.this);
                setExitAppDialog();
            }
        });

        Button levelsBtn = findViewById(R.id.home_levels_btn);
        levelsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AudioPlayer.playButtonPressSound(ActivityHome.this);
                setLevelsActivity();
            }
        });

        Button customGame = findViewById(R.id.customLevelBtn);
        customGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AudioPlayer.playButtonPressSound(ActivityHome.this);
                levelNumber = 0;
                performFileSearch(view);
            }
        });

    }

    @Override
    public void onBackPressed() {
        setExitAppDialog();
    }

    public void setExitAppDialog() {

        builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);

        //Creating dialog box
        AlertDialog alert = builder.create();

        //set view to dialog as text body
        LayoutInflater inflater = getLayoutInflater();
        View dialogLayoutView = inflater.inflate(R.layout.dialog_main, null);
        alert.setView(dialogLayoutView);

        //show the dialog box
        alert.show();

        //seting title and text to the dialog (have to be after alert.show)
        TextView title = alert.findViewById(R.id.dialogTitleText);
        title.setText("Exit?");
        TextView message = alert.findViewById(R.id.goHomeDialogBodyText);
        message.setText("Do you want to exit app?");

        //setting listener to buttons (have to be after alert.show)
        Button positiveButton = alert.findViewById(R.id.dialogPositivieButton);
        positiveButton.setText("Yes");
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AudioPlayer.playButtonPressSound(ActivityHome.this);
                finish();
                System.exit(0);
            }
        });
        Button negativeButton = alert.findViewById(R.id.dialogNegativeButton);
        negativeButton.setText("No");
        negativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AudioPlayer.playButtonPressSound(ActivityHome.this);
                alert.cancel();
            }
        });

    }

    public void setHelpPage(View v) {
        Intent intent = new Intent(this, ActivityHelp.class);
        startActivity(intent);
    }

    public void controlMusic(View view) {
        if (AudioPlayer.getMusicMediaPlayerIsPlaying() == false) {
            musicButton.setBackgroundResource(R.drawable.activity_home_music_on_button);
            AudioPlayer.playMusic(ActivityHome.this);
        }
        else {
            musicButton.setBackgroundResource(R.drawable.activity_home_music_off_button);
            AudioPlayer.stopMusic(ActivityHome.this);
        }
    }

    public void setLevelsActivity() {
        Intent intent = new Intent(this, ActivityLevels.class);
        startActivity(intent);
    }

    /* Fires an intent to spin up the "file chooser" UI and select an input file. */
    public void performFileSearch(View view)
    {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);    // ACTION_OPEN_DOCUMENT is the intent to choose a file via the system's file browser.
        intent.setType("text/plain");   // Filter to show only plain text files, using the text/plain data type.
        startActivityForResult(intent, READ_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent resultData) {

        // The ACTION_OPEN_DOCUMENT intent was sent with the request code READ_REQUEST_CODE. If the request code seen here doesn't match, it's the response to some other intent, and the code below shouldn't run at all.
        super.onActivityResult(requestCode, resultCode, resultData);
        if (requestCode == READ_REQUEST_CODE && resultCode == ActivityHome.RESULT_OK) {
            // The document selected by the user won't be returned in the intent. Instead, a URI to that document will be contained in the return intent provided to this method as a parameter. Pull that URI using resultData.getData().
            Uri uri = null;
            if (resultData != null) {
                uri = resultData.getData();
                Log.i(TAG, "Uri: " + uri.toString());

                try {
                    String inputString = readTextFromUri(uri);
                    int[] initialBlocksState = InputStringParser.extractBlocksInfoFromInput(inputString);
                    int exitSection = InputStringParser.extractExitSecFromInput(inputString);
                    setTheGameUp(initialBlocksState, exitSection);
                }
                catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private String readTextFromUri(Uri uri) throws IOException
    {
        InputStream inputStream = getContentResolver().openInputStream(uri);
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                inputStream));
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
        }
        return stringBuilder.toString();
    }

    //Gets initialState info and exit section and passes them to ActivityGameScene and starts it
    public void setTheGameUp(int[] initialState, int exitSec) {
        Intent intent = new Intent(this, ActivityGameScene.class);
        intent.putExtra(EXTRA_EXIT_SECTION, exitSec);
        intent.putExtra(EXTRA_INITIAL_STATE, initialState);
        intent.putExtra(EXTRA_LEVEL, levelNumber);
        startActivity(intent);
    }

}