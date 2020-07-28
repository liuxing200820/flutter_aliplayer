package com.github.plugin.flutter_aliplayer.listener;

import com.aliyun.player.IPlayer;
import com.aliyun.player.bean.ErrorInfo;
import com.aliyun.player.nativeclass.TrackInfo;
import com.github.plugin.flutter_aliplayer.AliQueuingEventSink;

/**
 * 播放器TrackChanged监听
 */
public class OnAVPTrackChangedListener implements IPlayer.OnTrackChangedListener {

    private final AliQueuingEventSink eventSink;

    public OnAVPTrackChangedListener(AliQueuingEventSink eventSink){
        this.eventSink = eventSink;
    }

    @Override
    public void onChangedSuccess(TrackInfo trackInfo) {
        //切换音视频流或者清晰度成功
    }

    @Override
    public void onChangedFail(TrackInfo trackInfo, ErrorInfo errorInfo) {
        //切换音视频流或者清晰度失败
    }
}
