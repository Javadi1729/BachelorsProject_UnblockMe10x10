package com.example.unblockme10x10;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsoluteLayout;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ActivityGameScene extends AppCompatActivity {

    AbsoluteLayout screen;
    AbsoluteLayout detailBox1, detailBox2, gameFrame, blockBox;
    Button optionsBtn;
    TextView titleTxt;
    TextView movesDoneTitleTxt, movesLeftTitleTxt, moooovesDoneTxt, moooovesLeftTxt;

    int ScreenWidth, ScreenHeight;

    int sectionSize;
    int  borderThickness;


    int exitSection;

    int levelNum;


    Block[] blockArray = new Block[100];
    Block[] spaceBlockArray = new Block[100];

    Block[] exitGateArray = new Block[44];

    Block tempMovingBlock;
    ArrayList<Block> moveChoices;


    int[] initialState;
    int[] currentState;

    AlertDialog.Builder builder;

    int moves;
    int remainingMoves;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_scene);


        Intent intent = getIntent();
        initialState = intent.getIntArrayExtra(ActivityHome.EXTRA_INITIAL_STATE);
        exitSection = intent.getIntExtra(ActivityHome.EXTRA_EXIT_SECTION, 0);
        levelNum = intent.getIntExtra(ActivityLevels.EXTRA_LEVEL, 0);

        moves = 0;
        remainingMoves = 50 - moves;

        tempMovingBlock = null;
        moveChoices = new ArrayList<Block>();

        //Setting main view
        screen = findViewById(R.id.mainView);
        screen.setBackgroundColor(Color.parseColor("#80B1AB"));

        //defining main layouts:
        gameFrame = new AbsoluteLayout(this);
        detailBox1 = new AbsoluteLayout(this);
        detailBox2 = new AbsoluteLayout(this);
        screen.addView(gameFrame);
        screen.addView(detailBox1);
        screen.addView(detailBox2);


        //Getting height and width of screen to use later on making responsive views*/
        // on below line we are creating and initializing variable for display metrics.
        DisplayMetrics displayMetrics = new DisplayMetrics();
        // on below line we are getting metrics for display using window manager.
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        // on below line we are getting height and width using display metrics.
        ScreenHeight = displayMetrics.heightPixels;
        ScreenWidth = displayMetrics.widthPixels;


        //Setting gameFrame parameters
        //gameFrameSize = blockBox + 2*borderThickness ==> gameFrameSize = 10*sectionSize + 2*borderThickness
        int gameFrameSize = Math.min(ScreenWidth, ScreenHeight) * 7 / 8;
        int blockBoxSize = Math.min(ScreenWidth, ScreenHeight) * 6 / 8;
        blockBoxSize = blockBoxSize - blockBoxSize%10;
        sectionSize = blockBoxSize / 10;
        borderThickness = (gameFrameSize - blockBoxSize) / 2;
        gameFrameSize = blockBoxSize + 2*borderThickness;

        int gameFrame_X = ScreenWidth / 16;
        int gameFrame_Y = ScreenHeight/2 -  gameFrameSize/2;

        gameFrame.setLayoutParams(new AbsoluteLayout.LayoutParams(gameFrameSize, gameFrameSize, gameFrame_X, gameFrame_Y));
        gameFrame.setBackgroundResource(R.drawable.activity_gamescene_game_frame);


        //Setting detailBox1 Params:
        int detailBox1Height = ScreenWidth/4;
        int detailBox1Width = gameFrameSize;

        int detailBox1_X = gameFrame_X;
        int detailBox1_Y =  gameFrame_Y/2 - detailBox1Height/2;

        detailBox1.setLayoutParams(new AbsoluteLayout.LayoutParams(detailBox1Width, detailBox1Height, detailBox1_X, detailBox1_Y));
        detailBox1.setBackgroundResource(R.drawable.activity_gamescene_detail);

        //Setting detailBox2 Params:
        int detailBox2Height = ScreenWidth/4;
        int detailBox2Width = gameFrameSize;

        int detailBox2_X = gameFrame_X;
        int detailBox2_Y =  gameFrame_Y + gameFrameSize + (((int) ScreenHeight) - (gameFrame_Y + gameFrameSize))/2 - detailBox2Height/2;

        detailBox2.setLayoutParams(new AbsoluteLayout.LayoutParams(detailBox2Width, detailBox2Height, detailBox2_X, detailBox2_Y));
        detailBox2.setBackgroundResource(R.drawable.activity_gamescene_detail);


        // Setting detailBox1 child views
        setDetailBox1Children(detailBox1Width, detailBox1Height);

        // Setting detailBox2 child views
        setDetailBox2Children(detailBox2Width, detailBox2Height);


        //Setting gameFrame child views
        // blockBoxSize and other needed params are defined previously
        blockBox = new AbsoluteLayout(this);
        gameFrame.addView(blockBox);

        blockBox.setBackgroundColor(Color.rgb(200,0,0));
        blockBox.setLayoutParams(new AbsoluteLayout.LayoutParams(
                blockBoxSize, blockBoxSize,  borderThickness, borderThickness
        ));



        // Creating And Initializing exit gates
        createInitialGates(borderThickness, sectionSize);
        //Setting exit gate
        setExitGate(exitSection);



        /**
         *  Setting up the Blocks */
        currentState = initialState.clone();

        //Setting spaceBlocks
        createSpaceBlockArray(sectionSize);

        for (Block blk : spaceBlockArray) {
            blockBox.addView(blk);
        }


        //Setting up blockArray
        createGameBlockArray(sectionSize);

        for (Block blk : blockArray) {
            blockBox.addView(blk);
        }

        AudioPlayer.playGameStartSound(ActivityGameScene.this);

    }

    public void setDetailBox1Children(int parentWidth, int parentHeight)
    {
        //Defining detailBox1 child views
        titleTxt = new TextView(this);
        optionsBtn = new Button(this);
        optionsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AudioPlayer.playButtonPressSound(ActivityGameScene.this);
                setOptionsDialog();
            }
        });

        detailBox1.addView(titleTxt);
        detailBox1.addView(optionsBtn);

        /**Setting detailBox1 child views*/
        int optionsBtnSize = parentHeight/2;
        if(optionsBtnSize > 150) {
            optionsBtnSize = 150;
        }

        int titleTxtHeight = optionsBtnSize;
        int titleTxtWidth = parentWidth - (parentHeight-optionsBtnSize) - optionsBtnSize;


        int titleTxt_X = (parentHeight-optionsBtnSize)/2;
        int titleTxt_Y = titleTxt_X;
        int optionsBtn_X = titleTxt_X +titleTxtWidth;
        int optionsBtn_Y = titleTxt_X;

        if(levelNum == 0){
            titleTxt.setText("Custom Level");
        } else {
            titleTxt.setText("Level " + levelNum);
        }
        titleTxt.setTextColor(Color.parseColor("#a8c7f3"));
        titleTxt.setTypeface(null, Typeface.BOLD);
        titleTxt.setGravity(Gravity.CENTER_VERTICAL);
        titleTxt.setTextSize(24);
        optionsBtn.setBackgroundResource(R.drawable.activity_gamescene_options_btn);

        titleTxt.setLayoutParams(new AbsoluteLayout.LayoutParams(titleTxtWidth, titleTxtHeight, titleTxt_X, titleTxt_Y));
        optionsBtn.setLayoutParams(new AbsoluteLayout.LayoutParams(optionsBtnSize, optionsBtnSize, optionsBtn_X, optionsBtn_Y));

    }

    public void setDetailBox2Children(int parentWidth, int parentHeight)
    {
        //Defining detailBox2 child views
        movesDoneTitleTxt = new TextView(this);
        moooovesDoneTxt = new TextView(this);
        movesLeftTitleTxt = new TextView(this);
        moooovesLeftTxt = new TextView(this);
        detailBox2.addView(movesDoneTitleTxt);
        detailBox2.addView(moooovesDoneTxt);
        detailBox2.addView(movesLeftTitleTxt);
        detailBox2.addView(moooovesLeftTxt);

        /** Setting detailBox2 Child views */
        int detailBox2BorderSize = parentHeight/6;
        int detailBox2ChildWidth = (parentWidth - 2*detailBox2BorderSize) / 2;
        int detailBox2ChildHeight = (parentHeight - 2*detailBox2BorderSize) / 2;

        int movesDoneTitleTxt_X = detailBox2BorderSize;
        int movesDoneTitleTxt_Y = detailBox2BorderSize;
        int moooovesDoneTxt_X = detailBox2BorderSize;
        int moooovesDoneTxt_Y = detailBox2BorderSize + detailBox2ChildHeight;
        int movesLeftTitleTxt_X = detailBox2BorderSize + detailBox2ChildWidth;
        int movesLeftTitleTxt_Y = detailBox2BorderSize;
        int moooovesLeftTxt_X = detailBox2BorderSize + detailBox2ChildWidth;
        int moooovesLeftTxt_Y = detailBox2BorderSize + detailBox2ChildHeight;

        movesDoneTitleTxt.setLayoutParams(new AbsoluteLayout.LayoutParams(detailBox2ChildWidth, detailBox2ChildHeight, movesDoneTitleTxt_X, movesDoneTitleTxt_Y));
        moooovesDoneTxt.setLayoutParams(new AbsoluteLayout.LayoutParams(detailBox2ChildWidth, detailBox2ChildHeight, moooovesDoneTxt_X, moooovesDoneTxt_Y));
        movesLeftTitleTxt.setLayoutParams(new AbsoluteLayout.LayoutParams(detailBox2ChildWidth, detailBox2ChildHeight, movesLeftTitleTxt_X, movesLeftTitleTxt_Y));
        moooovesLeftTxt.setLayoutParams(new AbsoluteLayout.LayoutParams(detailBox2ChildWidth, detailBox2ChildHeight, moooovesLeftTxt_X, moooovesLeftTxt_Y));

        movesDoneTitleTxt.setText("Moves Done");
        movesDoneTitleTxt.setTypeface(null, Typeface.BOLD);
        movesDoneTitleTxt.setTextColor(Color.parseColor("#a8c7f3"));

        moooovesDoneTxt.setText("0");
        moooovesDoneTxt.setTypeface(null, Typeface.BOLD);
        moooovesDoneTxt.setTextColor(Color.parseColor("#a8c7f3"));

        movesLeftTitleTxt.setText("Moves Left");
        movesLeftTitleTxt.setTypeface(null, Typeface.BOLD);
        movesLeftTitleTxt.setTextColor(Color.parseColor("#a8c7f3"));

        moooovesLeftTxt.setText("50");
        moooovesLeftTxt.setTypeface(null, Typeface.BOLD);
        moooovesLeftTxt.setTextColor(Color.parseColor("#a8c7f3"));

        movesDoneTitleTxt.setGravity(Gravity.CENTER);
        moooovesDoneTxt.setGravity(Gravity.CENTER);
        movesLeftTitleTxt.setGravity(Gravity.CENTER);
        moooovesLeftTxt.setGravity(Gravity.CENTER);
    }

    public void createInitialGates(int borderSizeSizeSize, int sectionSizeSizeSize) {
        for(int i=0; i<44; i++) {
            exitGateArray[i] = new Block(this);
            exitGateArray[i].setClickable(false);
            gameFrame.addView(exitGateArray[i]);
        }

        //corners need 3 exit blocks:
        //section number 0:
        exitGateArray[0].blockNum = 0;
        exitGateArray[1].blockNum = 0;
        exitGateArray[2].blockNum = 0;
        exitGateArray[0].setLayoutParams(new AbsoluteLayout.LayoutParams(borderSizeSizeSize, borderSizeSizeSize, 0, 0));
        exitGateArray[1].setLayoutParams(new AbsoluteLayout.LayoutParams(borderSizeSizeSize, sectionSizeSizeSize, 0, borderSizeSizeSize));
        exitGateArray[2].setLayoutParams(new AbsoluteLayout.LayoutParams(sectionSizeSizeSize, borderSizeSizeSize, borderSizeSizeSize, 0));
        //section number 9:
        exitGateArray[3].blockNum = 9;
        exitGateArray[4].blockNum = 9;
        exitGateArray[5].blockNum = 9;
        exitGateArray[3].setLayoutParams(new AbsoluteLayout.LayoutParams(borderSizeSizeSize, borderSizeSizeSize, borderSizeSizeSize+10*sectionSizeSizeSize, 0));
        exitGateArray[4].setLayoutParams(new AbsoluteLayout.LayoutParams(borderSizeSizeSize, sectionSizeSizeSize, borderSizeSizeSize+10*sectionSizeSizeSize, borderSizeSizeSize));
        exitGateArray[5].setLayoutParams(new AbsoluteLayout.LayoutParams(sectionSizeSizeSize, borderSizeSizeSize, borderSizeSizeSize+9*sectionSizeSizeSize, 0));
        //section number 90:
        exitGateArray[6].blockNum = 90;
        exitGateArray[7].blockNum = 90;
        exitGateArray[8].blockNum = 90;
        exitGateArray[6].setLayoutParams(new AbsoluteLayout.LayoutParams(borderSizeSizeSize, borderSizeSizeSize, 0, borderSizeSizeSize+10*sectionSizeSizeSize));
        exitGateArray[7].setLayoutParams(new AbsoluteLayout.LayoutParams(borderSizeSizeSize, sectionSizeSizeSize, 0, borderSizeSizeSize+9*sectionSizeSizeSize));
        exitGateArray[8].setLayoutParams(new AbsoluteLayout.LayoutParams(sectionSizeSizeSize, borderSizeSizeSize, borderSizeSizeSize, borderSizeSizeSize+10*sectionSizeSizeSize));
        //section number 99:
        exitGateArray[9].blockNum = 99;
        exitGateArray[10].blockNum = 99;
        exitGateArray[11].blockNum = 99;
        exitGateArray[9].setLayoutParams(new AbsoluteLayout.LayoutParams(borderSizeSizeSize, borderSizeSizeSize, borderSizeSizeSize+10*sectionSizeSizeSize, borderSizeSizeSize+10*sectionSizeSizeSize));
        exitGateArray[10].setLayoutParams(new AbsoluteLayout.LayoutParams(borderSizeSizeSize, sectionSizeSizeSize, borderSizeSizeSize+10*sectionSizeSizeSize, borderSizeSizeSize+9*sectionSizeSizeSize));
        exitGateArray[11].setLayoutParams(new AbsoluteLayout.LayoutParams(sectionSizeSizeSize, borderSizeSizeSize, borderSizeSizeSize+9*sectionSizeSizeSize, borderSizeSizeSize+10*sectionSizeSizeSize));
        //Top Border:
        for(int i=12; i<20; i++) {
            exitGateArray[i].blockNum = i-11;
            exitGateArray[i].width = sectionSizeSizeSize;
            exitGateArray[i].height = borderSizeSizeSize;
            exitGateArray[i].coordinate_X = borderSizeSizeSize + exitGateArray[i].blockNum * sectionSizeSizeSize;
            exitGateArray[i].coordinate_Y = 0;
            exitGateArray[i].setLayoutParams(new AbsoluteLayout.LayoutParams(exitGateArray[i].width, exitGateArray[i].height, exitGateArray[i].coordinate_X, exitGateArray[i].coordinate_Y));
        }
        //Bottom border:
        for(int i=20; i<28; i++) {
            exitGateArray[i].blockNum = i+71;
            exitGateArray[i].width = sectionSizeSizeSize;
            exitGateArray[i].height = borderSizeSizeSize;
            exitGateArray[i].coordinate_X = borderSizeSizeSize + exitGateArray[i].blockNum%10 * sectionSizeSizeSize;
            exitGateArray[i].coordinate_Y = borderSizeSizeSize + 10*sectionSize;
            exitGateArray[i].setLayoutParams(new AbsoluteLayout.LayoutParams(exitGateArray[i].width, exitGateArray[i].height, exitGateArray[i].coordinate_X, exitGateArray[i].coordinate_Y));
        }
        // Right border:
        for(int i=28; i<36; i++) {
            exitGateArray[i].blockNum = (i-27) * 10 + 9;
            exitGateArray[i].width = borderSizeSizeSize;
            exitGateArray[i].height = sectionSizeSizeSize;
            exitGateArray[i].coordinate_X = borderSizeSizeSize + 10 * sectionSizeSizeSize;
            exitGateArray[i].coordinate_Y = borderSizeSizeSize + exitGateArray[i].blockNum/10 * sectionSizeSizeSize;
            exitGateArray[i].setLayoutParams(new AbsoluteLayout.LayoutParams(exitGateArray[i].width, exitGateArray[i].height, exitGateArray[i].coordinate_X, exitGateArray[i].coordinate_Y));
        }
        // Left border:
        for(int i=36; i<44; i++) {
            exitGateArray[i].blockNum = (i-35) * 10;
            exitGateArray[i].width = borderSizeSizeSize;
            exitGateArray[i].height = sectionSizeSizeSize;
            exitGateArray[i].coordinate_X = 0;
            exitGateArray[i].coordinate_Y = borderSizeSizeSize + exitGateArray[i].blockNum/10 * sectionSizeSizeSize;
            exitGateArray[i].setLayoutParams(new AbsoluteLayout.LayoutParams(exitGateArray[i].width, exitGateArray[i].height, exitGateArray[i].coordinate_X, exitGateArray[i].coordinate_Y));
        }

        for (int i=0; i<44; i++) {
            exitGateArray[i].setBackgroundResource(R.drawable.block_space_default);
        }
    }

    public void setExitGate(int exitNum) {
        for(Block blk: exitGateArray) {
            if (blk.blockNum == exitNum){
                blk.setVisibility(View.VISIBLE);
            }
            else {
                blk.setVisibility(View.INVISIBLE);
            }
        }
    }

    @Override
    public void onBackPressed() {
        setOptionsDialog();
    }

    public void setOptionsDialog() {

        builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);

        //Creating dialog box
        AlertDialog alert = builder.create();

        //set view to dialog as text body
        LayoutInflater inflater = getLayoutInflater();
        View dialogLayoutView = inflater.inflate(R.layout.dialog_gamescene_options, null);

        alert.setView(dialogLayoutView);
        //show the dialog box
        alert.show();

        //setting listener to buttons (have to be after alert.show)
        Button resumeBtn = alert.findViewById(R.id.gamescene_options_resume_btn);
        resumeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AudioPlayer.playButtonPressSound(ActivityGameScene.this);
                alert.cancel();
            }
        });

        Button resetBtn = alert.findViewById(R.id.gamescene_options_reset_btn);
        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AudioPlayer.playButtonPressSound(ActivityGameScene.this);

                currentState = null;
                currentState = initialState.clone();

                moves = 0;
                remainingMoves = 50 - moves;

                moooovesDoneTxt.setText(Integer.toString(moves));
                moooovesLeftTxt.setText(Integer.toString(remainingMoves));

                // Emptying blocks section list to set new sections to them later
                for(Block block : blockArray) {
                    block.sectionList.removeAll(block.sectionList);
                }

                setSpaceBlocks(currentState, spaceBlockArray);
                setGameBlocks(currentState, blockArray);

                AudioPlayer.playGameStartSound(ActivityGameScene.this);

                alert.cancel();

            }
        });

        Button levelsBtn = alert.findViewById(R.id.gamescene_options_levels_btn);
        levelsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AudioPlayer.playButtonPressSound(ActivityGameScene.this);

                Intent intent = new Intent(ActivityGameScene.this, ActivityLevels.class);
                startActivity(intent);
                finish();
            }
        });

        Button homeBtn = alert.findViewById(R.id.gamescene_options_home_btn);
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AudioPlayer.playButtonPressSound(ActivityGameScene.this);
                finish();
            }
        });

    }

    public void setFailDialog() {

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
        title.setText("Game Over!");
        TextView message = alert.findViewById(R.id.goHomeDialogBodyText);
        message.setText("You failed the game! Do you want to retry?");

        //setting listener to buttons (have to be after alert.show)
        Button positiveButton = alert.findViewById(R.id.dialogPositivieButton);
        positiveButton.setText("Yes");
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AudioPlayer.playButtonPressSound(ActivityGameScene.this);

                currentState = null;
                currentState = initialState.clone();

                moves = 0;
                remainingMoves = 50 - moves;
                moooovesDoneTxt.setText(Integer.toString(moves));
                moooovesLeftTxt.setText(Integer.toString(remainingMoves));

                // Emptying blocks section list to set new sections to them later
                for(Block block : blockArray) {
                    block.sectionList.removeAll(block.sectionList);
                }

                setSpaceBlocks(currentState, spaceBlockArray);
                setGameBlocks(currentState, blockArray);

                AudioPlayer.playGameStartSound(ActivityGameScene.this);

                alert.cancel();
            }
        });
        Button negativeButton = alert.findViewById(R.id.dialogNegativeButton);
        negativeButton.setText("No");
        negativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AudioPlayer.playButtonPressSound(ActivityGameScene.this);
                finish();
            }
        });
    }

    public void setWinDialog() {

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
        title.setText("Congratulations!");
        TextView message = alert.findViewById(R.id.goHomeDialogBodyText);
        message.setText("You won the game! You completed the puzzle in " + moves + " moves.");

        //setting listener to buttons (have to be after alert.show)
        Button positiveButton = alert.findViewById(R.id.dialogPositivieButton);
        positiveButton.setText("Retry");
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AudioPlayer.playButtonPressSound(ActivityGameScene.this);

                currentState = null;
                currentState = initialState.clone();

                moves = 0;
                remainingMoves = 50 - moves;

                moooovesDoneTxt.setText(Integer.toString(moves));
                moooovesLeftTxt.setText(Integer.toString(remainingMoves));

                // Emptying blocks section list to set new sections to them later
                for(Block block : blockArray) {
                    block.sectionList.removeAll(block.sectionList);
                }

                setSpaceBlocks(currentState, spaceBlockArray);
                setGameBlocks(currentState, blockArray);

                AudioPlayer.playGameStartSound(ActivityGameScene.this);

                alert.cancel();

            }
        });
        Button negativeButton = alert.findViewById(R.id.dialogNegativeButton);
        negativeButton.setText("Back Home");
        negativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AudioPlayer.playButtonPressSound(ActivityGameScene.this);
                finish();
            }
        });

    }

    public void createSpaceBlockArray(int sectionSize) {
        //Setting spaceBlocks
        for (int i=0; i<100; i++) {
            spaceBlockArray[i] = new Block(this);
            spaceBlockArray[i].setClickable(false);
            spaceBlockArray[i].sectionList.add(i);
            spaceBlockArray[i].blockNum = 0;
            spaceBlockArray[i].sectionSize = sectionSize;
        }

        setSpaceBlocks(currentState, spaceBlockArray);

        // Setting event handler to spaceblocks
        for (Block blk : spaceBlockArray) {
            blk.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v)     // spaceBlocks are only clickable when they are in move choices of a selected gameBlock
                {
                    AudioPlayer.playBlockMoveSound(ActivityGameScene.this);
                    moveBlock(blk);
                    tempMovingBlock = null;
                }
            });
            blk.setClickable(false);
        }
    }

    public void createGameBlockArray(int sectionSize) {

        for (int i=0; i<100; i++) {
            blockArray[i] = new Block(this);     //Not all of these 100 blocks are used. Based of input, a number of them are used and others will remain null blocks. numbers start from 1 to 100. 100 is the goal block. (Not that none are 0 block. 0 blocks are space blocks.)
            blockArray[i].sectionSize = sectionSize;
            blockArray[i].blockNum = i+1;       // game block numbers start from 1, and number 0 blocks are space blocks
        }

        setGameBlocks(currentState, blockArray);

        // setting event handler to blocks
        for (Block blk : blockArray) {
            blk.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    if (tempMovingBlock == null)    // If no gameBlock is selected before
                    {
                        AudioPlayer.playBlockSelectSound(ActivityGameScene.this);
                        selectBlock(blk);     //sets moveChoices and tempMovingBlock
                    }
                    else    // If a gameBlock is selected before
                    {
                        AudioPlayer.playBlockDeselectSound(ActivityGameScene.this);
                        deselectBlock();       //releases selection
                        tempMovingBlock = null;
                    }

                }
            });
        }

    }

    public void setSpaceBlocks(int[] state, Block[] arrayOfSpaceBlocks) {
        for (int i=0; i < 100; i++) {
            arrayOfSpaceBlocks[i].orientation = arrayOfSpaceBlocks[i].orientationSetter(arrayOfSpaceBlocks[i].sectionList);
            arrayOfSpaceBlocks[i].width = arrayOfSpaceBlocks[i].widthSetter(arrayOfSpaceBlocks[i].sectionList, arrayOfSpaceBlocks[i].sectionSize, arrayOfSpaceBlocks[i].orientation);
            arrayOfSpaceBlocks[i].height = arrayOfSpaceBlocks[i].heightSetter(arrayOfSpaceBlocks[i].sectionList, arrayOfSpaceBlocks[i].sectionSize, arrayOfSpaceBlocks[i].orientation);
            arrayOfSpaceBlocks[i].coordinate_X = arrayOfSpaceBlocks[i].x_Setter(arrayOfSpaceBlocks[i].sectionList, arrayOfSpaceBlocks[i].sectionSize);
            arrayOfSpaceBlocks[i].coordinate_Y = arrayOfSpaceBlocks[i].y_Setter(arrayOfSpaceBlocks[i].sectionList, arrayOfSpaceBlocks[i].sectionSize);
            arrayOfSpaceBlocks[i].setLayoutParams(new AbsoluteLayout.LayoutParams(
                    arrayOfSpaceBlocks[i].width, arrayOfSpaceBlocks[i].height, arrayOfSpaceBlocks[i].coordinate_X,arrayOfSpaceBlocks[i].coordinate_Y));

            setBlockBackground(arrayOfSpaceBlocks[i], "space", "default");
            arrayOfSpaceBlocks[i].setClickable(false);

            //set the space block invisible if a gameBlock is set to the location
            if (state[i] != 0) {
                arrayOfSpaceBlocks[i].blockNum = -1;
                arrayOfSpaceBlocks[i].setVisibility(View.INVISIBLE);
            } else      // Else part is neccersary because block numbers and visiblity of space blocks change in moves
            {
                arrayOfSpaceBlocks[i].blockNum = 0;
                arrayOfSpaceBlocks[i].setVisibility(View.VISIBLE);
            }
        }
    }

    public  void setGameBlocks(int[] state, Block[] arrayOfGameBlocks) {
        for (int i=0; i < 100; i++) {
            if (state[i] == 0) {
                continue;
            } else {
                for (Block blk : arrayOfGameBlocks) {
                    if (state[i] == blk.blockNum) {
                        blk.sectionList.add(i);
                    }
                }
            }
        }
        // setting blocks properties
        for (Block blk : arrayOfGameBlocks) {
            blk.orientation = blk.orientationSetter(blk.sectionList);
            blk.width = blk.widthSetter(blk.sectionList, blk.sectionSize, blk.orientation);
            blk.height = blk.heightSetter(blk.sectionList, blk.sectionSize, blk.orientation);
            blk.coordinate_X = blk.x_Setter(blk.sectionList, blk.sectionSize);
            blk.coordinate_Y = blk.y_Setter(blk.sectionList, blk.sectionSize);
            blk.setLayoutParams(new AbsoluteLayout.LayoutParams(
                    blk.width, blk.height, blk.coordinate_X, blk.coordinate_Y));

            if(blk.blockNum == 100)     // If it is the goal block
            {
                setBlockBackground(blk, "goal", "default");
            }
            else
            {
                setBlockBackground(blk, "game", "default");
            }

        }
    }

    public void setBlockBackground(Block blk, String blockType, String blockState)
    {
        if (blockType == "space" & blockState == "default")
        {
            blk.setBackgroundResource(R.drawable.block_space_default);
        }
        else if (blockType == "space" & blockState == "focused")
        {
            blk.setBackgroundResource(R.drawable.block_space_focused);
        }
        else if (blockType == "goal" & blockState == "default")
        {
            if(blk.orientation == 0)    //If it is square
            {
                blk.setBackgroundResource(R.drawable.block_goal_default_null_01);
            }
            else if (blk.orientation == 1)      //If its horizontal
            {
                switch (blk.sectionList.size()) {
                    case 2:
                        blk.setBackgroundResource(R.drawable.block_goal_default_horiz_02);
                        break;
                    case 3:
                        blk.setBackgroundResource(R.drawable.block_goal_default_horiz_03);
                        break;
                    case 4:
                        blk.setBackgroundResource(R.drawable.block_goal_default_horiz_04);
                        break;
                    case 5:
                        blk.setBackgroundResource(R.drawable.block_goal_default_horiz_05);
                        break;
                    case 6:
                        blk.setBackgroundResource(R.drawable.block_goal_default_horiz_06);
                        break;
                    case 7:
                        blk.setBackgroundResource(R.drawable.block_goal_default_horiz_07);
                        break;
                    case 8:
                        blk.setBackgroundResource(R.drawable.block_goal_default_horiz_08);
                        break;
                    case 9:
                        blk.setBackgroundResource(R.drawable.block_goal_default_horiz_09);
                        break;
                    case 10:
                        blk.setBackgroundResource(R.drawable.block_goal_default_horiz_10);
                        break;
                    default:        //If section list is null
                        break;
                }
            }
            else        //If it is vertical
            {
                switch (blk.sectionList.size()) {
                    case 2:
                        blk.setBackgroundResource(R.drawable.block_goal_default_vert_02);
                        break;
                    case 3:
                        blk.setBackgroundResource(R.drawable.block_goal_default_vert_03);
                        break;
                    case 4:
                        blk.setBackgroundResource(R.drawable.block_goal_default_vert_04);
                        break;
                    case 5:
                        blk.setBackgroundResource(R.drawable.block_goal_default_vert_05);
                        break;
                    case 6:
                        blk.setBackgroundResource(R.drawable.block_goal_default_vert_06);
                        break;
                    case 7:
                        blk.setBackgroundResource(R.drawable.block_goal_default_vert_07);
                        break;
                    case 8:
                        blk.setBackgroundResource(R.drawable.block_goal_default_vert_08);
                        break;
                    case 9:
                        blk.setBackgroundResource(R.drawable.block_goal_default_vert_09);
                        break;
                    case 10:
                        blk.setBackgroundResource(R.drawable.block_goal_default_vert_10);
                        break;
                    default:        //If section list is null
                        break;
                }
            }
        }
        else if(blockType == "goal" & blockState == "focused")
        {
            if(blk.orientation == 0)    //If it is square
            {
                blk.setBackgroundResource(R.drawable.block_goal_focused_null_01);
            }
            else if (blk.orientation == 1)      //If its horizontal
            {
                switch (blk.sectionList.size()) {
                    case 2:
                        blk.setBackgroundResource(R.drawable.block_goal_focused_horiz_02);
                        break;
                    case 3:
                        blk.setBackgroundResource(R.drawable.block_goal_focused_horiz_03);
                        break;
                    case 4:
                        blk.setBackgroundResource(R.drawable.block_goal_focused_horiz_04);
                        break;
                    case 5:
                        blk.setBackgroundResource(R.drawable.block_goal_focused_horiz_05);
                        break;
                    case 6:
                        blk.setBackgroundResource(R.drawable.block_goal_focused_horiz_06);
                        break;
                    case 7:
                        blk.setBackgroundResource(R.drawable.block_goal_focused_horiz_07);
                        break;
                    case 8:
                        blk.setBackgroundResource(R.drawable.block_goal_focused_horiz_08);
                        break;
                    case 9:
                        blk.setBackgroundResource(R.drawable.block_goal_focused_horiz_09);
                        break;
                    case 10:
                        blk.setBackgroundResource(R.drawable.block_goal_focused_horiz_10);
                        break;
                    default:        //If section list is null
                        break;
                }
            }
            else        //If it is vertical
            {
                switch (blk.sectionList.size()) {
                    case 2:
                        blk.setBackgroundResource(R.drawable.block_goal_focused_vert_02);
                        break;
                    case 3:
                        blk.setBackgroundResource(R.drawable.block_goal_focused_vert_03);
                        break;
                    case 4:
                        blk.setBackgroundResource(R.drawable.block_goal_focused_vert_04);
                        break;
                    case 5:
                        blk.setBackgroundResource(R.drawable.block_goal_focused_vert_05);
                        break;
                    case 6:
                        blk.setBackgroundResource(R.drawable.block_goal_focused_vert_06);
                        break;
                    case 7:
                        blk.setBackgroundResource(R.drawable.block_goal_focused_vert_07);
                        break;
                    case 8:
                        blk.setBackgroundResource(R.drawable.block_goal_focused_vert_08);
                        break;
                    case 9:
                        blk.setBackgroundResource(R.drawable.block_goal_focused_vert_09);
                        break;
                    case 10:
                        blk.setBackgroundResource(R.drawable.block_goal_focused_vert_10);
                        break;
                    default:        //If section list is null
                        break;
                }
            }
        }
        else if (blockType == "game" & blockState == "default")
        {
            if(blk.orientation == 0) {
                blk.setBackgroundResource(R.drawable.block_game_default_null_01);
            }
            else if (blk.orientation == 1)      //If its horizontal
            {
                switch (blk.sectionList.size()) {
                    case 2:
                        blk.setBackgroundResource(R.drawable.block_game_default_horiz_02);
                        break;
                    case 3:
                        blk.setBackgroundResource(R.drawable.block_game_default_horiz_03);
                        break;
                    case 4:
                        blk.setBackgroundResource(R.drawable.block_game_default_horiz_04);
                        break;
                    case 5:
                        blk.setBackgroundResource(R.drawable.block_game_default_horiz_05);
                        break;
                    case 6:
                        blk.setBackgroundResource(R.drawable.block_game_default_horiz_06);
                        break;
                    case 7:
                        blk.setBackgroundResource(R.drawable.block_game_default_horiz_07);
                        break;
                    case 8:
                        blk.setBackgroundResource(R.drawable.block_game_default_horiz_08);
                        break;
                    case 9:
                        blk.setBackgroundResource(R.drawable.block_game_default_horiz_09);
                        break;
                    case 10:
                        blk.setBackgroundResource(R.drawable.block_game_default_horiz_10);
                        break;
                    default:        //If section list is null
                        break;
                }
            }
            else        //If it is a vertical block
            {
                switch (blk.sectionList.size()) {
                    case 2:
                        blk.setBackgroundResource(R.drawable.block_game_default_vert_02);
                        break;
                    case 3:
                        blk.setBackgroundResource(R.drawable.block_game_default_vert_03);
                        break;
                    case 4:
                        blk.setBackgroundResource(R.drawable.block_game_default_vert_04);
                        break;
                    case 5:
                        blk.setBackgroundResource(R.drawable.block_game_default_vert_05);
                        break;
                    case 6:
                        blk.setBackgroundResource(R.drawable.block_game_default_vert_06);
                        break;
                    case 7:
                        blk.setBackgroundResource(R.drawable.block_game_default_vert_07);
                        break;
                    case 8:
                        blk.setBackgroundResource(R.drawable.block_game_default_vert_08);
                        break;
                    case 9:
                        blk.setBackgroundResource(R.drawable.block_game_default_vert_09);
                        break;
                    case 10:
                        blk.setBackgroundResource(R.drawable.block_game_default_vert_10);
                        break;
                    default:        //If section list is null
                        break;
                }
            }
        }
        else if (blockType == "game" & blockState == "focused")
        {
            if(blk.orientation == 0) {
                blk.setBackgroundResource(R.drawable.block_game_focused_null_01);
            }
            else if (blk.orientation == 1)      //If its horizontal
            {
                switch (blk.sectionList.size()) {
                    case 2:
                        blk.setBackgroundResource(R.drawable.block_game_focused_horiz_02);
                        break;
                    case 3:
                        blk.setBackgroundResource(R.drawable.block_game_focused_horiz_03);
                        break;
                    case 4:
                        blk.setBackgroundResource(R.drawable.block_game_focused_horiz_04);
                        break;
                    case 5:
                        blk.setBackgroundResource(R.drawable.block_game_focused_horiz_05);
                        break;
                    case 6:
                        blk.setBackgroundResource(R.drawable.block_game_focused_horiz_06);
                        break;
                    case 7:
                        blk.setBackgroundResource(R.drawable.block_game_focused_horiz_07);
                        break;
                    case 8:
                        blk.setBackgroundResource(R.drawable.block_game_focused_horiz_08);
                        break;
                    case 9:
                        blk.setBackgroundResource(R.drawable.block_game_focused_horiz_09);
                        break;
                    case 10:
                        blk.setBackgroundResource(R.drawable.block_game_focused_horiz_10);
                        break;
                    default:        //If section list is null
                        break;
                }
            }
            else        //If it is a vertical block
            {
                switch (blk.sectionList.size()) {
                    case 2:
                        blk.setBackgroundResource(R.drawable.block_game_focused_vert_02);
                        break;
                    case 3:
                        blk.setBackgroundResource(R.drawable.block_game_focused_vert_03);
                        break;
                    case 4:
                        blk.setBackgroundResource(R.drawable.block_game_focused_vert_04);
                        break;
                    case 5:
                        blk.setBackgroundResource(R.drawable.block_game_focused_vert_05);
                        break;
                    case 6:
                        blk.setBackgroundResource(R.drawable.block_game_focused_vert_06);
                        break;
                    case 7:
                        blk.setBackgroundResource(R.drawable.block_game_focused_vert_07);
                        break;
                    case 8:
                        blk.setBackgroundResource(R.drawable.block_game_focused_vert_08);
                        break;
                    case 9:
                        blk.setBackgroundResource(R.drawable.block_game_focused_vert_09);
                        break;
                    case 10:
                        blk.setBackgroundResource(R.drawable.block_game_focused_vert_10);
                        break;
                    default:        //If section list is null
                        break;
                }
            }
        }
        else { }

    }

    public void selectBlock(Block blk) {

        tempMovingBlock = blk;
        if (tempMovingBlock.blockNum == 0  || tempMovingBlock.blockNum == -1)      //If it's a space block
        {
            setBlockBackground(tempMovingBlock, "space", "focused");
        }
        else      //If it is a game block or goal block
        {
            if (tempMovingBlock.blockNum == 100)        //If it is  goal block
            {
                setBlockBackground(tempMovingBlock, "goal", "focused");
            } else      //If it is a game block
            {
                setBlockBackground(tempMovingBlock, "game", "focused");
            }

            //set move choices and their backgrounds
            switch (tempMovingBlock.orientation) {
                case 1:
                    int i = tempMovingBlock.sectionList.get(0) - 1;
                    while ( (i+1)%10 > 0 && spaceBlockArray[i].blockNum == 0 ) {

                        moveChoices.add(spaceBlockArray[i]);
                        spaceBlockArray[i].setClickable(true);
                        spaceBlockArray[i].setBackgroundResource(R.drawable.block_space_focused);
                        i--;
                    }

                    int j = tempMovingBlock.sectionList.get(tempMovingBlock.sectionList.size()-1) + 1;
                    while ((j-1)%10 < 9 && spaceBlockArray[j].blockNum == 0) {
                        moveChoices.add(spaceBlockArray[j]);
                        spaceBlockArray[j].setClickable(true);
                        spaceBlockArray[j].setBackgroundResource(R.drawable.block_space_focused);
                        j++;
                    }
                    break;

                case -1:
                    int k = tempMovingBlock.sectionList.get(0) - 10;
                    while (k >= 0 && spaceBlockArray[k].blockNum == 0) {
                        moveChoices.add(spaceBlockArray[k]);
                        spaceBlockArray[k].setClickable(true);
                        spaceBlockArray[k].setBackgroundResource(R.drawable.block_space_focused);
                        k -= 10;
                    }
                    int l = tempMovingBlock.sectionList.get(tempMovingBlock.sectionList.size()-1) + 10;
                    while (l < 100 && spaceBlockArray[l].blockNum == 0) {
                        moveChoices.add(spaceBlockArray[l]);
                        spaceBlockArray[l].setClickable(true);
                        spaceBlockArray[l].setBackgroundResource(R.drawable.block_space_focused);
                        l += 10;
                    }
                    break;

                default:
                    int m = tempMovingBlock.sectionList.get(0) - 1;
                    while ( (m+1)%10 > 0 && spaceBlockArray[m].blockNum == 0 ) {
                        moveChoices.add(spaceBlockArray[m]);
                        spaceBlockArray[m].setClickable(true);
                        spaceBlockArray[m].setBackgroundResource(R.drawable.block_space_focused);
                        m--;
                    }
                    int n = tempMovingBlock.sectionList.get(tempMovingBlock.sectionList.size()-1) + 1;
                    while ((n-1)%10 < 9 && spaceBlockArray[n].blockNum == 0) {
                        moveChoices.add(spaceBlockArray[n]);
                        spaceBlockArray[n].setClickable(true);
                        spaceBlockArray[n].setBackgroundResource(R.drawable.block_space_focused);
                        n++;
                    }
                    int p = tempMovingBlock.sectionList.get(0) - 10;
                    while (p >= 0 && spaceBlockArray[p].blockNum == 0) {
                        moveChoices.add(spaceBlockArray[p]);
                        spaceBlockArray[p].setClickable(true);
                        spaceBlockArray[p].setBackgroundResource(R.drawable.block_space_focused);
                        p -= 10;
                    }
                    int q = tempMovingBlock.sectionList.get(tempMovingBlock.sectionList.size()-1) + 10;
                    while (q < 100 && spaceBlockArray[q].blockNum == 0) {
                        moveChoices.add(spaceBlockArray[q]);
                        spaceBlockArray[q].setClickable(true);
                        spaceBlockArray[q].setBackgroundResource(R.drawable.block_space_focused);
                        q += 10;
                    }
                    break;
            }
        }

    }

    public void deselectBlock() {

        if (tempMovingBlock.blockNum == 0 || tempMovingBlock.blockNum == -1) {
            tempMovingBlock.setBackgroundResource(R.drawable.block_space_default);
        }
        else {
            if (tempMovingBlock.blockNum == 100)        //If it is  goal block
            {
                setBlockBackground(tempMovingBlock, "goal", "default");

            } else      //If it is a game block
            {
                setBlockBackground(tempMovingBlock, "game", "default");

            }

            if ( !moveChoices.isEmpty() )       //use better error handler
            {
                for (Block temp : moveChoices) {
                    temp.setClickable(false);
                    temp.setBackgroundResource(R.drawable.block_space_default);
                }
                moveChoices.removeAll(moveChoices);
            }
        }
    }

    public void moveBlock(Block blk) {

        moves += 1;
        remainingMoves = 50 - moves;
        moooovesDoneTxt.setText(Integer.toString(moves));
        moooovesLeftTxt.setText(Integer.toString(remainingMoves));

        // If the chosen space block and the chosen game block are in the same row and the space block is after (in front of) the game block, do a forward move
        if ( blk.sectionList.get(0)/10 == tempMovingBlock.sectionList.get(0)/10 && blk.sectionList.get(0) > tempMovingBlock.sectionList.get(0)) {
            moveBlockForward(blk);
        }
        // If the chosen space block and the chosen game block are in the same row and the space block is before (behind) the game block, do a backward move
        else if (blk.sectionList.get(0)/10 == tempMovingBlock.sectionList.get(0)/10 && blk.sectionList.get(0) < tempMovingBlock.sectionList.get(0)){
            moveBlockBackward(blk);
        }
        // If the chosen space block and the chosen game block are in the same column and the space block is under of the game block, do a down move
        else if (blk.sectionList.get(0)/10 != tempMovingBlock.sectionList.get(0)/10 && blk.sectionList.get(0) > tempMovingBlock.sectionList.get(0)) {
            moveBlockDownward(blk);
        }
        // If the chosen space block and the chosen game block are in the same column and the space block is on top of the game block, do an up move
        else {
            moveBlockUpward(blk);
        }

        // Deselecting chosen blocks
        deselectBlock();

        // Emptying blocks section list to set new sections to them later
        for(Block block : blockArray) {
            block.sectionList.removeAll(block.sectionList);
        }

        // Resetting space blocks and game blocks
        setSpaceBlocks(currentState, spaceBlockArray);
        setGameBlocks(currentState, blockArray);


        // Checking to finish the game if player wins the game
        if (blockArray[99].sectionList.contains(exitSection))       // If block has reached exit
        {
            //playing win audio
            AudioPlayer.playWinSound(ActivityGameScene.this);
            //setting win dialog
            setWinDialog();
        }
        else
        {
            // Checking if no moves remaining then loose
            if (remainingMoves == 0)
            {
                //playing fail audio
                AudioPlayer.playFailSound(ActivityGameScene.this);
                //setting fail dialog
                setFailDialog();
            }
        }

    }

    public void moveBlockForward(Block blk) {
        int i = tempMovingBlock.sectionList.get(tempMovingBlock.sectionList.size()-1);
        int j = blk.sectionList.get(0);
        while (i < j) {
            currentState[i+1] = tempMovingBlock.blockNum;
            currentState[(i+1) - (tempMovingBlock.width/tempMovingBlock.sectionSize)] = 0;
            i++;
        }
    }

    public void moveBlockBackward(Block blk) {
        int i = tempMovingBlock.sectionList.get(0);
        int j = blk.sectionList.get(0);
        while (i > j) {
            currentState[i-1] = tempMovingBlock.blockNum;
            currentState[(i-1) + (tempMovingBlock.width/tempMovingBlock.sectionSize)] = 0;
            i--;
        }
    }

    public void moveBlockDownward(Block blk) {
        int i = tempMovingBlock.sectionList.get(tempMovingBlock.sectionList.size()-1);
        int j = blk.sectionList.get(0);
        while (i < j) {
            currentState[i+10] = tempMovingBlock.blockNum;
            currentState[(i+10) - 10*(tempMovingBlock.height/tempMovingBlock.sectionSize)] = 0;
            i += 10;
        }
    }

    public void moveBlockUpward(Block blk) {
        int i = tempMovingBlock.sectionList.get(0);
        int j = blk.sectionList.get(0);
        while (i > j) {
            currentState[i-10] = tempMovingBlock.blockNum;
            currentState[(i-10) + 10*(tempMovingBlock.height/tempMovingBlock.sectionSize)] = 0;
            i -= 10;
        }
    }

}