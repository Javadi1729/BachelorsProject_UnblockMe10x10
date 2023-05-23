package com.example.unblockme10x10;

import android.content.Context;
import android.media.MediaPlayer;

public class AudioPlayer {

        public static MediaPlayer buttonPressSound;

        private static MediaPlayer blockSelectSound, blockDeselectSound, blockMoveSound;
        private static MediaPlayer winSound, failSound;
        private static MediaPlayer gameStartSound;

        private static MediaPlayer musicMediaPlayer;
        private static boolean musicMediaPlayerIsPlaying = false;



        public static void playButtonPressSound(Context ctx)
        {

            if (buttonPressSound == null)         // To prevent errors caused by nonnull MediaPlayer object.
            {

                buttonPressSound = MediaPlayer.create(ctx,  R.raw.block_select);
                buttonPressSound.setVolume(100, 100);
                buttonPressSound.start();
                buttonPressSound.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        buttonPressSound.release();
                        buttonPressSound = null;
                    }
                });
            }
        }

        public static void playBlockSelectSound(Context ctx) {
            buttonPressSound = blockSelectSound;
            playButtonPressSound(ctx);
        }

        public static void playBlockDeselectSound(Context ctx) {
            if (blockDeselectSound == null)         // To prevent errors caused by nonnull MediaPlayer object.
            {

                blockDeselectSound = MediaPlayer.create(ctx,  R.raw.block_deselect);
                blockDeselectSound.setVolume(100, 100);
                blockDeselectSound.start();
                blockDeselectSound.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        blockDeselectSound.release();
                        blockDeselectSound = null;
                    }
                });
            }
        }

        public static void playBlockMoveSound(Context ctx) {
            if (blockMoveSound == null)         // To prevent errors caused by nonnull MediaPlayer object.
            {

                blockMoveSound = MediaPlayer.create(ctx,  R.raw.block_move);
                blockMoveSound.setVolume(100, 100);
                blockMoveSound.start();
                blockMoveSound.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        blockMoveSound.release();
                        blockMoveSound = null;
                    }
                });
            }
        }

        public static void playWinSound(Context ctx) {
            if (winSound == null)         // To prevent errors caused by nonnull MediaPlayer object.
            {

                winSound = MediaPlayer.create(ctx,  R.raw.win);
                winSound.setVolume(100, 100);
                winSound.start();
                winSound.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        winSound.release();
                        winSound = null;
                    }
                });
            }
        }

        public static void playFailSound(Context ctx) {
            if (failSound == null)         // To prevent errors caused by nonnull MediaPlayer object.
            {

                failSound = MediaPlayer.create(ctx,  R.raw.fail);
                failSound.setVolume(100, 100);
                failSound.start();
                failSound.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        failSound.release();
                        failSound = null;
                    }
                });
            }
        }

        public static void playGameStartSound(Context ctx) {
            if (gameStartSound == null)         // To prevent errors caused by nonnull MediaPlayer object.
            {

                gameStartSound = MediaPlayer.create(ctx,  R.raw.game_start);
                gameStartSound.setVolume(100, 100);
                gameStartSound.start();
                gameStartSound.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        gameStartSound.release();
                        gameStartSound = null;
                    }
                });
            }
        }

        public static boolean getMusicMediaPlayerIsPlaying() {
            return musicMediaPlayerIsPlaying;
        }

        public static void playMusic(Context ctx)
        {
            if (musicMediaPlayer == null)     // To prevent errors caused by nonnull MediaPlayer object
            {
                musicMediaPlayer = MediaPlayer.create(ctx, R.raw.background_music_bagatelle_in_a_minor_fur_elise_1);
                musicMediaPlayer.setLooping(true); // Set looping
                musicMediaPlayer.setVolume(100, 100);
                musicMediaPlayer.start();
                musicMediaPlayerIsPlaying = true;
            }
        }

        public static void stopMusic(Context ctx) {
            if (musicMediaPlayer.isPlaying() == true)         // To prevent errors caused by onplaying MediaPlayer object
            {
                musicMediaPlayer.stop();
                musicMediaPlayer.release();
                musicMediaPlayer = null;
                musicMediaPlayerIsPlaying = false;
            }
        }

}
