package com.github.plugin.flutter_aliplayer.listener;

import com.aliyun.player.IPlayer;
import com.github.plugin.flutter_aliplayer.AliQueuingEventSink;

/**
 * 播放器状态改变监听
 */
public class OnAVPStateChangedListener implements IPlayer.OnStateChangedListener {

    private final AliQueuingEventSink eventSink;

    public OnAVPStateChangedListener(AliQueuingEventSink eventSink){
        this.eventSink = eventSink;
    }

    @Override
    public void onStateChanged(int newState) {
        //播放器状态改变事件
    }
}
