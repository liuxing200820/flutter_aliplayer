package com.github.plugin.flutter_aliplayer.listener;

import com.aliyun.player.IPlayer;
import com.github.plugin.flutter_aliplayer.AliQueuingEventSink;

/**
 * 字幕显示和隐藏监听
 */
public class OnAVPSubtitleDisplayListener implements IPlayer.OnSubtitleDisplayListener {

    private final AliQueuingEventSink eventSink;

    public OnAVPSubtitleDisplayListener(AliQueuingEventSink eventSink){
        this.eventSink = eventSink;
    }

    @Override
    public void onSubtitleExtAdded(int trackIndex, String url) {

    }

    @Override
    public void onSubtitleShow(int trackIndex, long id, String data) {
        //显示字幕
    }

    @Override
    public void onSubtitleHide(int trackIndex, long id) {
        //隐藏字幕
    }
}
