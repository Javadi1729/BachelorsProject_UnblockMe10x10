package com.example.unblockme10x10;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ActivityLevels extends AppCompatActivity {

    public static final String EXTRA_INITIAL_STATE = "30";
    public static final String EXTRA_EXIT_SECTION = "40";
    public static final String EXTRA_LEVEL = "1";

    int levelNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_levels);

        Button backBtn = findViewById(R.id.defaultLevelsBackbtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AudioPlayer.playButtonPressSound(ActivityLevels.this);
                finish();
            }
        });

        Button level1Btn = findViewById(R.id.level1Button);
        Button level2Btn = findViewById(R.id.level2Button);
        Button level3Btn = findViewById(R.id.level3Button);
        Button level4Btn = findViewById(R.id.level4Button);
        Button level5Btn = findViewById(R.id.level5Button);
        Button level6Btn = findViewById(R.id.level6Button);
        Button level7Btn = findViewById(R.id.level7Button);
        Button level8Btn = findViewById(R.id.level8Button);
        Button level9Btn = findViewById(R.id.level9Button);
        Button level10Btn = findViewById(R.id.level10Button);
        Button level11Btn = findViewById(R.id.level11Button);
        Button level12Btn = findViewById(R.id.level12Button);
        Button level13Btn = findViewById(R.id.level13Button);
        Button level14Btn = findViewById(R.id.level14Button);
        Button level15Btn = findViewById(R.id.level15Button);
        Button level16Btn = findViewById(R.id.level16Button);
        Button level17Btn = findViewById(R.id.level17Button);
        Button level18Btn = findViewById(R.id.level18Button);
        Button level19Btn = findViewById(R.id.level19Button);
        Button level20Btn = findViewById(R.id.level20Button);

        level1Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AudioPlayer.playButtonPressSound(ActivityLevels.this);
                levelNumber = 1;
                setLevel1(view);
                finish();
            }
        });
        level2Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AudioPlayer.playButtonPressSound(ActivityLevels.this);
                levelNumber = 2;
                setLevel2(view);
                finish();
            }
        });
        level3Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AudioPlayer.playButtonPressSound(ActivityLevels.this);
                levelNumber = 3;
                setLevel3(view);
                finish();
            }
        });
        level4Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AudioPlayer.playButtonPressSound(ActivityLevels.this);
                levelNumber = 4;
                setLevel4(view);
                finish();
            }
        });
        level5Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AudioPlayer.playButtonPressSound(ActivityLevels.this);
                levelNumber = 5;
                setLevel5(view);
                finish();
            }
        });
        level6Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AudioPlayer.playButtonPressSound(ActivityLevels.this);
                levelNumber = 6;
                setLevel6(view);
                finish();
            }
        });
        level7Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AudioPlayer.playButtonPressSound(ActivityLevels.this);
                levelNumber = 7;
                setLevel7(view);
                finish();
            }
        });
        level8Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AudioPlayer.playButtonPressSound(ActivityLevels.this);
                levelNumber = 8;
                setLevel8(view);
                finish();
            }
        });
        level9Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AudioPlayer.playButtonPressSound(ActivityLevels.this);
                levelNumber = 9;
                setLevel9(view);
                finish();
            }
        });
        level10Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AudioPlayer.playButtonPressSound(ActivityLevels.this);
                levelNumber = 10;
                setLevel10(view);
                finish();
            }
        });
        level11Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AudioPlayer.playButtonPressSound(ActivityLevels.this);
                levelNumber = 11;
                setLevel11(view);
                finish();
            }
        });
        level12Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AudioPlayer.playButtonPressSound(ActivityLevels.this);
                levelNumber = 12;
                setLevel12(view);
                finish();
            }
        });
        level13Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AudioPlayer.playButtonPressSound(ActivityLevels.this);
                levelNumber = 13;
                setLevel13(view);
                finish();
            }
        });
        level14Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AudioPlayer.playButtonPressSound(ActivityLevels.this);
                levelNumber = 14;
                setLevel14(view);
                finish();
            }
        });
        level15Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AudioPlayer.playButtonPressSound(ActivityLevels.this);
                levelNumber = 15;
                setLevel15(view);
                finish();
            }
        });
        level16Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AudioPlayer.playButtonPressSound(ActivityLevels.this);
                levelNumber = 16;
                setLevel16(view);
                finish();
            }
        });
        level17Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AudioPlayer.playButtonPressSound(ActivityLevels.this);
                levelNumber = 17;
                setLevel17(view);
                finish();
            }
        });
        level18Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AudioPlayer.playButtonPressSound(ActivityLevels.this);
                levelNumber = 18;
                setLevel18(view);
                finish();
            }
        });
        level19Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AudioPlayer.playButtonPressSound(ActivityLevels.this);
                levelNumber = 19;
                setLevel19(view);
                finish();
            }
        });
        level20Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AudioPlayer.playButtonPressSound(ActivityLevels.this);
                levelNumber = 20;
                setLevel20(view);
                finish();
            }
        });

    }

    public void setLevel1(View view) {
        try{
            String inputString = readTextFromAssets("level_0001.txt");
            int[] initialBlocksState = InputStringParser.extractBlocksInfoFromInput(inputString);
            int exitSection = InputStringParser.extractExitSecFromInput(inputString);
            setTheGameUp(initialBlocksState, exitSection);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setLevel2(View view) {
        try{
            String inputString = readTextFromAssets("level_0002.txt");
            int[] initialBlocksState = InputStringParser.extractBlocksInfoFromInput(inputString);
            int exitSection = InputStringParser.extractExitSecFromInput(inputString);
            setTheGameUp(initialBlocksState, exitSection);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setLevel3(View view) {
        try{
            String inputString = readTextFromAssets("level_0003.txt");
            int[] initialBlocksState = InputStringParser.extractBlocksInfoFromInput(inputString);
            int exitSection = InputStringParser.extractExitSecFromInput(inputString);
            setTheGameUp(initialBlocksState, exitSection);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setLevel4(View view) {
        try{
            String inputString = readTextFromAssets("level_0004.txt");
            int[] initialBlocksState = InputStringParser.extractBlocksInfoFromInput(inputString);
            int exitSection = InputStringParser.extractExitSecFromInput(inputString);
            setTheGameUp(initialBlocksState, exitSection);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setLevel5(View view) {
        try{
            String inputString = readTextFromAssets("level_0005.txt");
            int[] initialBlocksState = InputStringParser.extractBlocksInfoFromInput(inputString);
            int exitSection = InputStringParser.extractExitSecFromInput(inputString);
            setTheGameUp(initialBlocksState, exitSection);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setLevel6(View view) {
        try{
            String inputString = readTextFromAssets("level_0006.txt");
            int[] initialBlocksState = InputStringParser.extractBlocksInfoFromInput(inputString);
            int exitSection = InputStringParser.extractExitSecFromInput(inputString);
            setTheGameUp(initialBlocksState, exitSection);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setLevel7(View view) {
        try{
            String inputString = readTextFromAssets("level_0007.txt");
            int[] initialBlocksState = InputStringParser.extractBlocksInfoFromInput(inputString);
            int exitSection = InputStringParser.extractExitSecFromInput(inputString);
            setTheGameUp(initialBlocksState, exitSection);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setLevel8(View view) {
        try{
            String inputString = readTextFromAssets("level_0008.txt");
            int[] initialBlocksState = InputStringParser.extractBlocksInfoFromInput(inputString);
            int exitSection = InputStringParser.extractExitSecFromInput(inputString);
            setTheGameUp(initialBlocksState, exitSection);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setLevel9(View view) {
        try{
            String inputString = readTextFromAssets("level_0009.txt");
            int[] initialBlocksState = InputStringParser.extractBlocksInfoFromInput(inputString);
            int exitSection = InputStringParser.extractExitSecFromInput(inputString);
            setTheGameUp(initialBlocksState, exitSection);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setLevel10(View view) {
        try{
            String inputString = readTextFromAssets("level_0010.txt");
            int[] initialBlocksState = InputStringParser.extractBlocksInfoFromInput(inputString);
            int exitSection = InputStringParser.extractExitSecFromInput(inputString);
            setTheGameUp(initialBlocksState, exitSection);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setLevel11(View view) {
        try{
            String inputString = readTextFromAssets("level_0011.txt");
            int[] initialBlocksState = InputStringParser.extractBlocksInfoFromInput(inputString);
            int exitSection = InputStringParser.extractExitSecFromInput(inputString);
            setTheGameUp(initialBlocksState, exitSection);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setLevel12(View view) {
        try{
            String inputString = readTextFromAssets("level_0012.txt");
            int[] initialBlocksState = InputStringParser.extractBlocksInfoFromInput(inputString);
            int exitSection = InputStringParser.extractExitSecFromInput(inputString);
            setTheGameUp(initialBlocksState, exitSection);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setLevel13(View view) {
        try{
            String inputString = readTextFromAssets("level_0013.txt");
            int[] initialBlocksState = InputStringParser.extractBlocksInfoFromInput(inputString);
            int exitSection = InputStringParser.extractExitSecFromInput(inputString);
            setTheGameUp(initialBlocksState, exitSection);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setLevel14(View view) {
        try{
            String inputString = readTextFromAssets("level_0014.txt");
            int[] initialBlocksState = InputStringParser.extractBlocksInfoFromInput(inputString);
            int exitSection = InputStringParser.extractExitSecFromInput(inputString);
            setTheGameUp(initialBlocksState, exitSection);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setLevel15(View view) {
        try{
            String inputString = readTextFromAssets("level_0015.txt");
            int[] initialBlocksState = InputStringParser.extractBlocksInfoFromInput(inputString);
            int exitSection = InputStringParser.extractExitSecFromInput(inputString);
            setTheGameUp(initialBlocksState, exitSection);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setLevel16(View view) {
        try{
            String inputString = readTextFromAssets("level_0016.txt");
            int[] initialBlocksState = InputStringParser.extractBlocksInfoFromInput(inputString);
            int exitSection = InputStringParser.extractExitSecFromInput(inputString);
            setTheGameUp(initialBlocksState, exitSection);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setLevel17(View view) {
        try{
            String inputString = readTextFromAssets("level_0017.txt");
            int[] initialBlocksState = InputStringParser.extractBlocksInfoFromInput(inputString);
            int exitSection = InputStringParser.extractExitSecFromInput(inputString);
            setTheGameUp(initialBlocksState, exitSection);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setLevel18(View view) {
        try{
            String inputString = readTextFromAssets("level_0018.txt");
            int[] initialBlocksState = InputStringParser.extractBlocksInfoFromInput(inputString);
            int exitSection = InputStringParser.extractExitSecFromInput(inputString);
            setTheGameUp(initialBlocksState, exitSection);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setLevel19(View view) {
        try{
            String inputString = readTextFromAssets("level_0019.txt");
            int[] initialBlocksState = InputStringParser.extractBlocksInfoFromInput(inputString);
            int exitSection = InputStringParser.extractExitSecFromInput(inputString);
            setTheGameUp(initialBlocksState, exitSection);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setLevel20(View view) {
        try{
            String inputString = readTextFromAssets("level_0020.txt");
            int[] initialBlocksState = InputStringParser.extractBlocksInfoFromInput(inputString);
            int exitSection = InputStringParser.extractExitSecFromInput(inputString);
            setTheGameUp(initialBlocksState, exitSection);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public String readTextFromAssets(String fileName) throws IOException {
        InputStream inputStream = getAssets().open(fileName);
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