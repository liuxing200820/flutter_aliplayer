package com.github.plugin.flutter_aliplayer.listener;

import com.aliyun.player.IPlayer;
import com.github.plugin.flutter_aliplayer.AliQueuingEventSink;

/**
 * SEI数据回调
 */
public class OnAVPSeiDataListener implements IPlayer.OnSeiDataListener {

    private final AliQueuingEventSink eventSink;

    public OnAVPSeiDataListener(AliQueuingEventSink eventSink){
        this.eventSink = eventSink;
    }

    @Override
    public void onSeiData(int type, byte[] data) {
        // 回调
    }
}
