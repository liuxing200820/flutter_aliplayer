import 'package:json_annotation/json_annotation.dart';

part 'ali_player_interface.g.dart';

@JsonSerializable(nullable: false)
class UrlSource extends _SourceBase {
  String uri;
  String cacheFilePath;

  UrlSource() {
    this.quality = "AUTO";
    this.forceQuality = true;
  }

  factory UrlSource.fromJson(Map<String, dynamic> json) => _$UrlSourceFromJson(json);

  Map<String, dynamic> toJson() => _$UrlSourceToJson(this);
}

@JsonSerializable(nullable: false)
class VidSts extends _VidSourceBase {
  String vid;
  String accessKeyId;
  String accessKeySecret;
  String securityToken;
  String region;

  VidSts();

  factory VidSts.fromJson(Map<String, dynamic> json) => _$VidStsFromJson(json);

  Map<String, dynamic> toJson() => _$VidStsToJson(this);
}

@JsonSerializable(nullable: false)
class VidMps extends _VidSourceBase {
  String mediaId;
  String accessKeyId;
  String accessKeySecret;
  String securityToken;
  String region;
  String playDomain;
  String authInfo;
  String hlsUriToken;

  VidMps();

  factory VidMps.fromJson(Map<String, dynamic> json) => _$VidMpsFromJson(json);

  Map<String, dynamic> toJson() => _$VidMpsToJson(this);
}

@JsonSerializable(nullable: false)
class VidAuth extends _VidSourceBase {
  String vid;
  String playAuth;
  String region;

  VidAuth();

  factory VidAuth.fromJson(Map<String, dynamic> json) => _$VidAuthFromJson(json);

  Map<String, dynamic> toJson() => _$VidAuthToJson(this);
}

@JsonSerializable(nullable: false)
class LiveSts extends _SourceBase {
  String accessKeyId;
  String accessKeySecret;
  String securityToken;
  String region;
  String url;
  String domain;
  String app;
  String stream;

  LiveSts();

  factory LiveSts.fromJson(Map<String, dynamic> json) => _$LiveStsFromJson(json);

  Map<String, dynamic> toJson() => _$LiveStsToJson(this);
}

abstract class DataSource {

}

class _SourceBase extends DataSource {
  String coverPath;
  String title;
  String quality;
  bool forceQuality = false;
}

class _VidSourceBase extends _SourceBase {
  List<VidMediaFormat> formats;
  List<VidDefinition> definitions;
  VidPlayerConfigGen playConfig;

  void setQuality(String quality, bool forceQuality) {
    this.quality = quality;
    this.forceQuality = forceQuality;
  }
}

enum VidMediaFormat { mp4, m3u8, flv, mp3 }

enum VidDefinition {
  DEFINITION_FD,
  DEFINITION_LD,
  DEFINITION_SD,
  DEFINITION_HD,
  DEFINITION_OD,
  DEFINITION_2K,
  DEFINITION_4K,
  DEFINITION_SQ,
  DEFINITION_HQ,
  DEFINITION_AUTO
}

@JsonSerializable(nullable: false)
class VidPlayerConfigGen {
  Map<String, dynamic> configMap = Map();

  VidPlayerConfigGen();

  void setPreviewTime(int previewTime) {
    this.addPlayerConfig4Int("PreviewTime", previewTime);
  }

  void setEncryptType(VidPlayerEncryptType type) {
    this.addPlayerConfig4String("EncryptType", type.toString());
  }

  void setMtsHlsUriToken(String token) {
    this.addPlayerConfig4String("MtsHlsUriToken", token);
  }

  void addPlayerConfig4String(String key, String value) {
    if (key != null) {
      this.configMap.addAll({key: value});
    }
  }

  void addPlayerConfig4Int(String key, int value) {
    if (key != null) {
      this.configMap.addAll({key: value});
    }
  }

  factory VidPlayerConfigGen.fromJson(Map<String, dynamic> json) => _$VidPlayerConfigGenFromJson(json);

  Map<String, dynamic> toJson() => _$VidPlayerConfigGenToJson(this);
}

enum VidPlayerEncryptType { Unencrypted, AliyunVodEncryption, HLSEncryption }
