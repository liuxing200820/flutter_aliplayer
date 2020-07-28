package com.github.plugin.flutter_aliplayer;

import android.content.Context;
import android.util.Log;
import android.util.LongSparseArray;

import androidx.annotation.NonNull;

import cn.hutool.core.util.StrUtil;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.EventChannel;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry;
import io.flutter.plugin.common.PluginRegistry.Registrar;
import io.flutter.view.FlutterNativeView;
import io.flutter.view.TextureRegistry;

/** FlutterAliplayerPlugin */
public class FlutterAliplayerPlugin implements FlutterPlugin, MethodCallHandler {
  private static final String TAG = "FlutterAliplayerPlugin";
  private final LongSparseArray<AliyunPlayer> videoPlayers = new LongSparseArray<>();

  private FlutterState flutterState;

  /** Register this with the v2 embedding for the plugin to respond to lifecycle callbacks. */
  public FlutterAliplayerPlugin() {}

  private FlutterAliplayerPlugin(Registrar registrar) {
    this.flutterState = new FlutterState(registrar.context(), registrar.messenger(), registrar.textures(), this);
//    flutterState.startListening(this, registrar.messenger());
  }

  /** Registers this with the stable v1 embedding. Will not respond to lifecycle events. */
  public static void registerWith(Registrar registrar) {
    final FlutterAliplayerPlugin plugin = new FlutterAliplayerPlugin(registrar);
    registrar.addViewDestroyListener(
            new PluginRegistry.ViewDestroyListener() {
              @Override
              public boolean onViewDestroy(FlutterNativeView view) {
                plugin.onDestroy();
                return false; // We are not interested in assuming ownership of the NativeView.
              }
            });
  }

  @Override
  public void onAttachedToEngine(FlutterPluginBinding binding) {
    this.flutterState = new FlutterState(binding.getApplicationContext(), binding.getBinaryMessenger(),
            binding.getTextureRegistry(), this);
//    flutterState.startListening(this, binding.getBinaryMessenger());
  }

  @Override
  public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
    if (flutterState == null) {
      Log.wtf(TAG, "Detached from the engine before registering to it.");
    }
    flutterState.stopListening();
    flutterState = null;
  }

  private void disposeAllPlayers() {
    for (int i = 0; i < videoPlayers.size(); i++) {
      videoPlayers.valueAt(i).dispose();
    }
    videoPlayers.clear();
  }

  private void onDestroy() {
    // The whole FlutterView is being destroyed. Here we release resources acquired for all
    // instances
    // of VideoPlayer. Once https://github.com/flutter/flutter/issues/19358 is resolved this may
    // be replaced with just asserting that videoPlayers.isEmpty().
    // https://github.com/flutter/flutter/issues/20989 tracks this.
    disposeAllPlayers();
  }

  public void initialize() {
    disposeAllPlayers();
  }

  public void create(String sourceType, String source, @NonNull Result result) {
    TextureRegistry.SurfaceTextureEntry handle =
            flutterState.textureRegistry.createSurfaceTexture();
    EventChannel eventChannel = new EventChannel(
            flutterState.binaryMessenger, "flutter_aliplayer/videoEvents" + handle.id());

    AliyunPlayer player =
            new AliyunPlayer(flutterState.applicationContext, eventChannel, handle, sourceType, source);
    videoPlayers.put(handle.id(), player);

    result.success(handle.id());
  }

  @Override
  public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
    if (call.method.startsWith("player_")) {
      if (call.method.equals("player_create")) {
        String type = call.argument("type");
        String source = call.argument("source");
        if (StrUtil.isBlank(type) || StrUtil.isBlank(source)) {
          result.error(
                  "Param blank",
                  "Param type and source cannot be blank",
                  null);
        } else {
          create(type, source, result);
        }
      } else {
        long textureId = ((Number) call.argument("textureId")).longValue();
        AliyunPlayer aliyunPlayer = videoPlayers.get(textureId);
        if (aliyunPlayer == null) {
          result.error(
                  "Unknown textureId",
                  "No video player associated with texture id " + textureId,
                  null);
        } else {
          aliyunPlayer.onMethodCall(call, result);
        }
      }
    } else if (call.method.equals("init")) {
      initialize();
    } else {
      result.notImplemented();
    }
  }

  private static final class FlutterState {
    private final Context applicationContext;
    private final BinaryMessenger binaryMessenger;
    private final TextureRegistry textureRegistry;

    private final MethodChannel channel;

    public FlutterState(Context applicationContext, BinaryMessenger binaryMessenger, TextureRegistry textureRegistry,
                        MethodCallHandler methodCallHandler) {
      this.applicationContext = applicationContext;
      this.binaryMessenger = binaryMessenger;
      this.textureRegistry = textureRegistry;

      this.channel = new MethodChannel(binaryMessenger,"flutter_aliplayer");
      channel.setMethodCallHandler(methodCallHandler);
    }

    void stopListening() {
      this.channel.setMethodCallHandler(null);
    }
  }
}
