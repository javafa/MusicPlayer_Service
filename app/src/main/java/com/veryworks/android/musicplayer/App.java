package com.veryworks.android.musicplayer;

import android.media.MediaPlayer;

/**
 * Created by pc on 2/24/2017.
 */

public class App {

    public static MediaPlayer player = null;

    // 액션 플래그
    public static final String ACTION_PLAY = "com.kodonho.android.musicplayer.Action.Play";
    public static final String ACTION_PAUSE = "com.kodonho.android.musicplayer.Action.Pause";
    public static final String ACTION_RESTART = "com.kodonho.android.musicplayer.Action.Restart";

    // 플레이어 상태 플래그
    public static final int PLAY = 0;
    public static final int PAUSE = 1;
    public static final int STOP = 2;

    // 현재 플레이어 상태
    public static int playStatus = STOP;

    // 현재 음원 index
    public static int position = 0;

}
