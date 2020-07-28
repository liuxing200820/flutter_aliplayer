package com.github.plugin.flutter_aliplayer.listener;

import com.aliyun.player.IPlayer;
import com.github.plugin.flutter_aliplayer.AliQueuingEventSink;

/**
 * 播放器seek完成监听
 */
public class OnAVPSeekCompleteListener implements IPlayer.OnSeekCompleteListener {

    private final AliQueuingEventSink eventSink;

    public OnAVPSeekCompleteListener(AliQueuingEventSink eventSink){
        this.eventSink = eventSink;
    }

    @Override
    public void onSeekComplete() {
        //拖动结束
    }
}
