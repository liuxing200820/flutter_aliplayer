package com.github.plugin.flutter_aliplayer.listener;

import com.aliyun.player.IPlayer;
import com.aliyun.player.source.StsInfo;
import com.github.plugin.flutter_aliplayer.AliQueuingEventSink;

public class OnAVPVerifyStsCallback implements IPlayer.OnVerifyStsCallback {

    private final AliQueuingEventSink eventSink;

    public OnAVPVerifyStsCallback(AliQueuingEventSink eventSink){
        this.eventSink = eventSink;
    }

    @Override
    public IPlayer.StsStatus onVerifySts(StsInfo info) {
        return IPlayer.StsStatus.Valid;
    }
}
