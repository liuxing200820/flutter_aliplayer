package com.github.plugin.flutter_aliplayer.listener;

import com.aliyun.player.IPlayer;
import com.github.plugin.flutter_aliplayer.AliQueuingEventSink;

import cn.hutool.core.map.MapUtil;

/**
 * 播放器加载状态监听
 */
public class OnAVPLoadingStatusListener implements IPlayer.OnLoadingStatusListener {

    private final AliQueuingEventSink eventSink;

    public OnAVPLoadingStatusListener(AliQueuingEventSink eventSink){
        this.eventSink = eventSink;
    }

    @Override
    public void onLoadingBegin() {
        //缓冲开始。
        eventSink.success(MapUtil.builder("event", (Object) "loadingBegin").build());
    }

    @Override
    public void onLoadingProgress(int percent, float netSpeed) {
        //缓冲进度
    }

    @Override
    public void onLoadingEnd() {
        //缓冲结束
        eventSink.success(MapUtil.builder("event", (Object) "loadingEnd").build());
    }
}
