package com.github.plugin.flutter_aliplayer.listener;

import com.aliyun.player.IPlayer;
import com.github.plugin.flutter_aliplayer.AliQueuingEventSink;

import cn.hutool.core.map.MapUtil;

/**
 * 播放器播放完成监听
 */
public class OnAVPCompletionListener implements IPlayer.OnCompletionListener {

    private final AliQueuingEventSink eventSink;

    public OnAVPCompletionListener(AliQueuingEventSink eventSink){
        this.eventSink = eventSink;
    }

    @Override
    public void onCompletion() {
        //播放完成事件
        eventSink.success(MapUtil.builder("event", "completion").build());
    }
}
