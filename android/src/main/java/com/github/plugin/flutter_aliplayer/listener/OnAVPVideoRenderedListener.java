package com.github.plugin.flutter_aliplayer.listener;

import com.aliyun.player.IPlayer;
import com.github.plugin.flutter_aliplayer.AliQueuingEventSink;

/**
 * 视频渲染回调
 */
public class OnAVPVideoRenderedListener implements IPlayer.OnVideoRenderedListener {

    private final AliQueuingEventSink eventSink;

    public OnAVPVideoRenderedListener(AliQueuingEventSink eventSink){
        this.eventSink = eventSink;
    }

    @Override
    public void onVideoRendered(long timeMs, long pts) {
        // 视频帧被渲染
    }
}
