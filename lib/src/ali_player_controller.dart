import 'dart:async';
import 'dart:convert' as convert;

import 'package:flutter/cupertino.dart';
import 'package:flutter/services.dart';

import 'ali_player_interface.dart';
import 'ali_player_value.dart';

final MethodChannel _channel = const MethodChannel('flutter_aliplayer');

/// Controls a platform video player, and provides updates when the state is
/// changing.
///
/// Instances must be initialized with initialize.
///
/// The video is displayed in a Flutter app by creating a [VideoPlayer] widget.
///
/// To reclaim the resources used by the player call [dispose].
///
/// After [dispose] all further calls are ignored.
class AliPlayerController extends ValueNotifier<AliPlayerValue> {
  /// Constructs a [AliPlayerController] playing a video from an asset.
  ///
  /// The name of the asset is given by the [dataSource] argument and must not be
  /// null. The [package] argument must be non-null when the asset comes from a
  /// package and null otherwise.
  AliPlayerController(this.dataSource)
      : super(AliPlayerValue(duration: null));

  int _textureId;

  final DataSource dataSource;

  bool _isDisposed = false;
  Completer<void> _creatingCompleter;
  StreamSubscription<dynamic> _eventSubscription;
  _VideoAppLifeCycleObserver _lifeCycleObserver;

  /// This is just exposed for testing. It shouldn't be used by anyone depending
  /// on the plugin.
  int get textureId => _textureId;

  /// Attempts to open the given [dataSource] and load metadata about the video.
  Future<void> initialize() async {
    _lifeCycleObserver = _VideoAppLifeCycleObserver(this);
    _lifeCycleObserver.initialize();
    _creatingCompleter = Completer<void>();

    String type = dataSource.runtimeType.toString();
    _textureId = await _channel.invokeMethod('player_create', { 'type': type, 'source': convert.json.encode(dataSource)});
    _creatingCompleter.complete(null);
    final Completer<void> initializingCompleter = Completer<void>();

    void eventListener(dynamic event) {
      if (_isDisposed) {
        return;
      }

      final Map<dynamic, dynamic> map = event;
      switch (map['event']) {
        case 'prepared':
          double width = double.parse(map['width'].toString());
          double height = double.parse(map['height'].toString());
          value = value.copyWith(
            duration: Duration(milliseconds: map['duration']),
            size: Size(width, height),
          );
          initializingCompleter.complete(null);
//          _applyLooping();
//          _applyVolume();
//          _applyPlayPause();
          break;
        case 'completion':
          value = value.copyWith(isPlaying: false, position: value.duration);
          break;
        case 'loadingBegin':
          value = value.copyWith(isLoading: true);
          break;
        case 'loadingEnd':
          value = value.copyWith(isLoading: false);
          break;
        case 'position':
          _updatePosition(Duration(milliseconds: map['position']));
          break;
      }
    }

    void errorListener(Object obj) {
      final PlatformException e = obj;
      value = AliPlayerValue.erroneous(e.message);
      if (!initializingCompleter.isCompleted) {
        initializingCompleter.completeError(obj);
      }
    }

    _eventSubscription = EventChannel('flutter_aliplayer/videoEvents$textureId')
        .receiveBroadcastStream()
        .listen(eventListener, onError: errorListener);
    
    prepare(dataSource);
    return initializingCompleter.future;
  }

  Future<void> prepare(DataSource dataSource) async {
    await _channel.invokeMethod('player_prepare', { "textureId": textureId });
  }

  @override
  Future<void> dispose() async {
    if (_creatingCompleter != null) {
      await _creatingCompleter.future;
      if (!_isDisposed) {
        _isDisposed = true;
        await _eventSubscription?.cancel();
        await _channel.invokeMethod('player_dispose', { "textureId": textureId });
      }
      _lifeCycleObserver.dispose();
    }
    _isDisposed = true;
    super.dispose();
  }

  /// Starts playing the video.
  ///
  /// This method returns a future that completes as soon as the "play" command
  /// has been sent to the platform, not when playback itself is totally
  /// finished.
  Future<void> play() async {
    value = value.copyWith(isPlaying: true);
    await _applyPlayPause();
  }

  /// Sets whether or not the video should loop after playing once. See also
  /// [AliPlayerValue.isLoop].
  Future<void> setLoop(bool isLoop) async {
    value = value.copyWith(isLoop: isLoop);
    await _applyLooping();
  }

  /// Pauses the video.
  Future<void> pause() async {
    value = value.copyWith(isPlaying: false);
    await _applyPlayPause();
  }

  Future<void> _applyLooping() async {
    if (!value.initialized || _isDisposed) {
      return;
    }
    await _channel.invokeMethod("player_set_loop", {"textureId": textureId, "isLoop": value.isLoop});
  }

  Future<void> _applyPlayPause() async {
    if (!value.initialized || _isDisposed) {
      return;
    }
    if (value.isPlaying) {
      await _channel.invokeMethod("player_start", {"textureId": textureId});
    } else {
      await _channel.invokeMethod("player_pause", {"textureId": textureId});
    }
  }

  Future<void> _applyVolume() async {
    if (!value.initialized || _isDisposed) {
      return;
    }
    await _channel.invokeMethod('player_set_volume', {"textureId": textureId,'volume': value.volume.toString()});
  }

  /// Sets the video's current timestamp to be at [moment]. The next
  /// time the video is played it will resume from the given [moment].
  ///
  /// If [moment] is outside of the video's full range it will be automatically
  /// and silently clamped.
  Future<void> seekTo(Duration position) async {
    if (_isDisposed) {
      return;
    }
    if (position > value.duration) {
      position = value.duration;
    } else if (position < const Duration()) {
      position = const Duration();
    }
    await _channel.invokeMethod("player_seek_to", { "textureId": textureId,"position": position.inMilliseconds });
    _updatePosition(position);
  }

  /// Sets the audio volume of [this].
  ///
  /// [volume] indicates a value between 0.0 (silent) and 1.0 (full volume) on a
  /// linear scale.
  Future<void> setVolume(double volume) async {
    value = value.copyWith(volume: volume.clamp(0.0, 1.0));
    await _applyVolume();
  }

  void _updatePosition(Duration position) {
    value = value.copyWith(position: position);
  }
}

class _VideoAppLifeCycleObserver extends Object with WidgetsBindingObserver {
  _VideoAppLifeCycleObserver(this._controller);

  bool _wasPlayingBeforePause = false;
  final AliPlayerController _controller;

  void initialize() {
    WidgetsBinding.instance.addObserver(this);
  }

  @override
  void didChangeAppLifecycleState(AppLifecycleState state) {
    switch (state) {
      case AppLifecycleState.paused:
        _wasPlayingBeforePause = _controller.value.isPlaying;
        _controller.pause();
        break;
      case AppLifecycleState.resumed:
        if (_wasPlayingBeforePause) {
          _controller.play();
        }
        break;
      default:
    }
  }

  void dispose() {
    WidgetsBinding.instance.removeObserver(this);
  }
}
