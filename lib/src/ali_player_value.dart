import 'package:flutter/cupertino.dart';

/// The duration, current position, buffering state, error state and settings
/// of a [VideoPlayerController].
class AliPlayerValue {
  /// Constructs a video with the given values. Only [duration] is required. The
  /// rest will initialize with default values when unset.
  AliPlayerValue({
    @required this.duration,
    this.size,
    this.position = const Duration(),
    this.isPlaying = false,
    this.isLoop = false,
    this.isLoading = false,
    this.volume = 1.0,
    this.errorDescription,
  });

  /// Returns an instance with a `null` [Duration].
  AliPlayerValue.uninitialized() : this(duration: null);

  /// Returns an instance with a `null` [Duration] and the given
  /// [errorDescription].
  AliPlayerValue.erroneous(String errorDescription)
      : this(duration: null, errorDescription: errorDescription);

  /// The total duration of the video.
  ///
  /// Is null when [initialized] is false.
  final Duration duration;

  /// The current playback position.
  final Duration position;

  /// True if the video is playing. False if it's paused.
  final bool isPlaying;

  /// True if the video is looping.
  final bool isLoop;

  /// True if the video is currently loading.
  final bool isLoading;

  /// The current volume of the playback.
  final double volume;

  /// A description of the error if present.
  ///
  /// If [hasError] is false this is [null].
  final String errorDescription;

  /// The [size] of the currently loaded video.
  ///
  /// Is null when [initialized] is false.
  final Size size;

  /// Indicates whether or not the video has been loaded and is ready to play.
  bool get initialized => duration != null;

  /// Indicates whether or not the video is in an error state. If this is true
  /// [errorDescription] should have information about the problem.
  bool get hasError => errorDescription != null;

  /// Returns [size.width] / [size.height] when size is non-null, or `1.0.` when
  /// size is null or the aspect ratio would be less than or equal to 0.0.
  double get aspectRatio {
    if (size == null || size.width == 0 || size.height == 0) {
      return 1.0;
    }
    final double aspectRatio = size.width / size.height;
    if (aspectRatio <= 0) {
      return 1.0;
    }
    return aspectRatio;
  }

  /// Returns a new instance that has the same values as this current instance,
  /// except for any overrides passed in as arguments to [copyWidth].
  AliPlayerValue copyWith({
    Duration duration,
    Size size,
    Duration position,
    bool isPlaying,
    bool isLoop,
    bool isLoading,
    double volume,
    String errorDescription,
  }) {
    return AliPlayerValue(
      duration: duration ?? this.duration,
      size: size ?? this.size,
      position: position ?? this.position,
      isPlaying: isPlaying ?? this.isPlaying,
      isLoop: isLoop ?? this.isLoop,
      isLoading: isLoading ?? this.isLoading,
      volume: volume ?? this.volume,
      errorDescription: errorDescription ?? this.errorDescription,
    );
  }

  @override
  String toString() {
    return '$runtimeType('
        'duration: $duration, '
        'size: $size, '
        'position: $position, '
        'isPlaying: $isPlaying, '
        'isLoop: $isLoop, '
        'isLoading: $isLoading'
        'volume: $volume, '
        'errorDescription: $errorDescription)';
  }
}