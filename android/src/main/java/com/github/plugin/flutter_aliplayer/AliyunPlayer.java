package com.github.plugin.flutter_aliplayer;

import android.content.Context;
import android.util.Log;
import android.view.Surface;

import androidx.annotation.NonNull;

import com.aliyun.player.AliPlayer;
import com.aliyun.player.AliPlayerFactory;
import com.github.plugin.flutter_aliplayer.listener.OnAVPCompletionListener;
import com.github.plugin.flutter_aliplayer.listener.OnAVPErrorListener;
import com.github.plugin.flutter_aliplayer.listener.OnAVPInfoListener;
import com.github.plugin.flutter_aliplayer.listener.OnAVPLoadingStatusListener;
import com.github.plugin.flutter_aliplayer.listener.OnAVPPreparedListener;
import com.github.plugin.flutter_aliplayer.listener.OnAVPRenderingStartListener;
import com.github.plugin.flutter_aliplayer.listener.OnAVPSeekCompleteListener;
import com.github.plugin.flutter_aliplayer.listener.OnAVPSeiDataListener;
import com.github.plugin.flutter_aliplayer.listener.OnAVPSnapShotListener;
import com.github.plugin.flutter_aliplayer.listener.OnAVPStateChangedListener;
import com.github.plugin.flutter_aliplayer.listener.OnAVPSubtitleDisplayListener;
import com.github.plugin.flutter_aliplayer.listener.OnAVPTrackChangedListener;
import com.github.plugin.flutter_aliplayer.listener.OnAVPVerifyStsCallback;
import com.github.plugin.flutter_aliplayer.listener.OnAVPVideoRenderedListener;
import com.github.plugin.flutter_aliplayer.listener.OnAVPVideoSizeChangedListener;
import com.github.plugin.flutter_aliplayer.util.SourceUtil;

import io.flutter.plugin.common.EventChannel;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.view.TextureRegistry;

public final class AliyunPlayer {

    /**
     * 真正的播放器实例对象
     */
    private AliPlayer mAliPlayer;

    private Surface mSurface;
    private final TextureRegistry.SurfaceTextureEntry textureEntry;

    private AliQueuingEventSink eventSink = new AliQueuingEventSink();

    private final EventChannel eventChannel;

    private boolean isInitialized = false;

    public void initialized() {
        this.isInitialized = true;
    }

    public AliyunPlayer(Context context, EventChannel eventChannel, TextureRegistry.SurfaceTextureEntry textureEntry,
                        String sourceType, String source){
        this.eventChannel = eventChannel;
        this.textureEntry = textureEntry;

        initPlayer(context, eventChannel, textureEntry);

        SourceUtil.process(sourceType, source, this.mAliPlayer);
//        this.mAliPlayer.prepare();
    }

    private void initPlayer(Context context, EventChannel eventChannel, TextureRegistry.SurfaceTextureEntry textureEntry) {
        mAliPlayer = AliPlayerFactory.createAliPlayer(context);
//        mAliPlayer.setAutoPlay(true);

        // 注册android向flutter发事件
        eventChannel.setStreamHandler(
                new EventChannel.StreamHandler() {
                    @Override
                    public void onListen(Object arguments, EventChannel.EventSink events) {
                        eventSink.setDelegate(events);
                    }

                    @Override
                    public void onCancel(Object arguments) {
                        eventSink.setDelegate(null);
                    }
                }
        );

        mSurface = new Surface(textureEntry.surfaceTexture());
        mAliPlayer.setSurface(mSurface);

        initPlayerListener();
    }

    private void initPlayerListener() {
        mAliPlayer.setOnInfoListener(new OnAVPInfoListener(eventSink));
        mAliPlayer.setOnErrorListener(new OnAVPErrorListener(eventSink));
        mAliPlayer.setOnSeiDataListener(new OnAVPSeiDataListener(eventSink));
        mAliPlayer.setOnSnapShotListener(new OnAVPSnapShotListener(eventSink));
        mAliPlayer.setOnPreparedListener(new OnAVPPreparedListener(eventSink, this.mAliPlayer, this));
        mAliPlayer.setOnCompletionListener(new OnAVPCompletionListener(eventSink));
        mAliPlayer.setOnTrackChangedListener(new OnAVPTrackChangedListener(eventSink));
        mAliPlayer.setOnSeekCompleteListener(new OnAVPSeekCompleteListener(eventSink));
        mAliPlayer.setOnVideoRenderedListener(new OnAVPVideoRenderedListener(eventSink));
        mAliPlayer.setOnLoadingStatusListener(new OnAVPLoadingStatusListener(eventSink));
        mAliPlayer.setOnRenderingStartListener(new OnAVPRenderingStartListener(eventSink));
        mAliPlayer.setOnVerifyStsCallback(new OnAVPVerifyStsCallback(eventSink));
        mAliPlayer.setOnStateChangedListener(new OnAVPStateChangedListener(eventSink));
        mAliPlayer.setOnSubtitleDisplayListener(new OnAVPSubtitleDisplayListener(eventSink));
        mAliPlayer.setOnVideoSizeChangedListener(new OnAVPVideoSizeChangedListener(eventSink));
    }

    public void dispose() {
        if (isInitialized) {
            this.mAliPlayer.stop();
        }
        textureEntry.release();
        eventChannel.setStreamHandler(null);
        if (mSurface != null) {
            mSurface.release();
        }
        if (this.mAliPlayer != null) {
            this.mAliPlayer.setSurface(null);
            this.mAliPlayer.release();
        }
    }

    public void onMethodCall(@NonNull MethodCall call, @NonNull MethodChannel.Result result) {
        switch (call.method) {
            case "player_prepare":
//                String type = call.argument("type");
//                String source = call.argument("source");
//                if (StringUtils.isBlank(type) || StringUtils.isBlank(source)
//                        || !SourceUtil.process(type, source, mAliPlayer)) {
//                    result.error("400", null, null);
//                    return;
//                }
                this.mAliPlayer.prepare();
                result.success(null);
                break;
            case "player_start":
                this.mAliPlayer.start();
                result.success(null);
                break;
            case "player_pause":
                this.mAliPlayer.pause();
                result.success(null);
                break;
            case "player_set_volume":
                String volume = call.argument("volume");
                if (volume != null) {
                    Log.i("player", "player_set_volume:" + volume);
                    this.mAliPlayer.setVolume(Float.parseFloat(volume));
                }
                result.success(null);
                break;
            case "player_get_speed":
                result.success(this.mAliPlayer.getSpeed());
                break;
            case "player_seek_to":
                Long position = call.argument("position");
                if (position != null) {
                    this.mAliPlayer.seekTo(position);
                }
                result.success(null);
                break;
            case "player_dispose":
                dispose();
                result.success(null);
                break;
            case "player_set_loop":
                Boolean isLoop = call.argument("isLoop");
                if (isLoop != null) {
                    this.mAliPlayer.setLoop(isLoop);
                }
                result.success(null);
                break;
            default:
                result.notImplemented();
        }
    }
}
