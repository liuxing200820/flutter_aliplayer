package com.github.plugin.flutter_aliplayer.listener;

import android.graphics.Bitmap;

import com.aliyun.player.IPlayer;
import com.github.plugin.flutter_aliplayer.AliQueuingEventSink;

/**
 * 播放器截图事件监听
 */
public class OnAVPSnapShotListener implements IPlayer.OnSnapShotListener {

    private final AliQueuingEventSink eventSink;

    public OnAVPSnapShotListener(AliQueuingEventSink eventSink){
        this.eventSink = eventSink;
    }

    @Override
    public void onSnapShot(Bitmap bm, int with, int height) {
        //截图事件
    }
}
