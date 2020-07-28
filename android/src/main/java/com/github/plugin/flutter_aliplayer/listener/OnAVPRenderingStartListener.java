package com.github.plugin.flutter_aliplayer.listener;

import com.aliyun.player.IPlayer;
import com.github.plugin.flutter_aliplayer.AliQueuingEventSink;

/**
 * 播放器Render监听
 */
public class OnAVPRenderingStartListener implements IPlayer.OnRenderingStartListener {

    private final AliQueuingEventSink eventSink;

    public OnAVPRenderingStartListener(AliQueuingEventSink eventSink){
        this.eventSink = eventSink;
    }

    @Override
    public void onRenderingStart() {
        //首帧渲染显示事件
    }
}
