// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'ali_player_interface.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

UrlSource _$UrlSourceFromJson(Map<String, dynamic> json) {
  return UrlSource()
    ..coverPath = json['coverPath'] as String
    ..title = json['title'] as String
    ..quality = json['quality'] as String
    ..forceQuality = json['forceQuality'] as bool
    ..uri = json['uri'] as String
    ..cacheFilePath = json['cacheFilePath'] as String;
}

Map<String, dynamic> _$UrlSourceToJson(UrlSource instance) => <String, dynamic>{
      'coverPath': instance.coverPath,
      'title': instance.title,
      'quality': instance.quality,
      'forceQuality': instance.forceQuality,
      'uri': instance.uri,
      'cacheFilePath': instance.cacheFilePath,
    };

VidSts _$VidStsFromJson(Map<String, dynamic> json) {
  return VidSts()
    ..coverPath = json['coverPath'] as String
    ..title = json['title'] as String
    ..quality = json['quality'] as String
    ..forceQuality = json['forceQuality'] as bool
    ..formats = (json['formats'] as List)
        .map((e) => _$enumDecode(_$VidMediaFormatEnumMap, e))
        .toList()
    ..definitions = (json['definitions'] as List)
        .map((e) => _$enumDecode(_$VidDefinitionEnumMap, e))
        .toList()
    ..playConfig =
        VidPlayerConfigGen.fromJson(json['playConfig'] as Map<String, dynamic>)
    ..vid = json['vid'] as String
    ..accessKeyId = json['accessKeyId'] as String
    ..accessKeySecret = json['accessKeySecret'] as String
    ..securityToken = json['securityToken'] as String
    ..region = json['region'] as String;
}

Map<String, dynamic> _$VidStsToJson(VidSts instance) => <String, dynamic>{
      'coverPath': instance.coverPath,
      'title': instance.title,
      'quality': instance.quality,
      'forceQuality': instance.forceQuality,
      'formats':
          instance.formats.map((e) => _$VidMediaFormatEnumMap[e]).toList(),
      'definitions':
          instance.definitions.map((e) => _$VidDefinitionEnumMap[e]).toList(),
      'playConfig': instance.playConfig,
      'vid': instance.vid,
      'accessKeyId': instance.accessKeyId,
      'accessKeySecret': instance.accessKeySecret,
      'securityToken': instance.securityToken,
      'region': instance.region,
    };

T _$enumDecode<T>(
  Map<T, dynamic> enumValues,
  dynamic source, {
  T unknownValue,
}) {
  if (source == null) {
    throw ArgumentError('A value must be provided. Supported values: '
        '${enumValues.values.join(', ')}');
  }

  final value = enumValues.entries
      .singleWhere((e) => e.value == source, orElse: () => null)
      ?.key;

  if (value == null && unknownValue == null) {
    throw ArgumentError('`$source` is not one of the supported values: '
        '${enumValues.values.join(', ')}');
  }
  return value ?? unknownValue;
}

const _$VidMediaFormatEnumMap = {
  VidMediaFormat.mp4: 'mp4',
  VidMediaFormat.m3u8: 'm3u8',
  VidMediaFormat.flv: 'flv',
  VidMediaFormat.mp3: 'mp3',
};

const _$VidDefinitionEnumMap = {
  VidDefinition.DEFINITION_FD: 'DEFINITION_FD',
  VidDefinition.DEFINITION_LD: 'DEFINITION_LD',
  VidDefinition.DEFINITION_SD: 'DEFINITION_SD',
  VidDefinition.DEFINITION_HD: 'DEFINITION_HD',
  VidDefinition.DEFINITION_OD: 'DEFINITION_OD',
  VidDefinition.DEFINITION_2K: 'DEFINITION_2K',
  VidDefinition.DEFINITION_4K: 'DEFINITION_4K',
  VidDefinition.DEFINITION_SQ: 'DEFINITION_SQ',
  VidDefinition.DEFINITION_HQ: 'DEFINITION_HQ',
  VidDefinition.DEFINITION_AUTO: 'DEFINITION_AUTO',
};

VidMps _$VidMpsFromJson(Map<String, dynamic> json) {
  return VidMps()
    ..coverPath = json['coverPath'] as String
    ..title = json['title'] as String
    ..quality = json['quality'] as String
    ..forceQuality = json['forceQuality'] as bool
    ..formats = (json['formats'] as List)
        .map((e) => _$enumDecode(_$VidMediaFormatEnumMap, e))
        .toList()
    ..definitions = (json['definitions'] as List)
        .map((e) => _$enumDecode(_$VidDefinitionEnumMap, e))
        .toList()
    ..playConfig =
        VidPlayerConfigGen.fromJson(json['playConfig'] as Map<String, dynamic>)
    ..mediaId = json['mediaId'] as String
    ..accessKeyId = json['accessKeyId'] as String
    ..accessKeySecret = json['accessKeySecret'] as String
    ..securityToken = json['securityToken'] as String
    ..region = json['region'] as String
    ..playDomain = json['playDomain'] as String
    ..authInfo = json['authInfo'] as String
    ..hlsUriToken = json['hlsUriToken'] as String;
}

Map<String, dynamic> _$VidMpsToJson(VidMps instance) => <String, dynamic>{
      'coverPath': instance.coverPath,
      'title': instance.title,
      'quality': instance.quality,
      'forceQuality': instance.forceQuality,
      'formats':
          instance.formats.map((e) => _$VidMediaFormatEnumMap[e]).toList(),
      'definitions':
          instance.definitions.map((e) => _$VidDefinitionEnumMap[e]).toList(),
      'playConfig': instance.playConfig,
      'mediaId': instance.mediaId,
      'accessKeyId': instance.accessKeyId,
      'accessKeySecret': instance.accessKeySecret,
      'securityToken': instance.securityToken,
      'region': instance.region,
      'playDomain': instance.playDomain,
      'authInfo': instance.authInfo,
      'hlsUriToken': instance.hlsUriToken,
    };

VidAuth _$VidAuthFromJson(Map<String, dynamic> json) {
  return VidAuth()
    ..coverPath = json['coverPath'] as String
    ..title = json['title'] as String
    ..quality = json['quality'] as String
    ..forceQuality = json['forceQuality'] as bool
    ..formats = (json['formats'] as List)
        .map((e) => _$enumDecode(_$VidMediaFormatEnumMap, e))
        .toList()
    ..definitions = (json['definitions'] as List)
        .map((e) => _$enumDecode(_$VidDefinitionEnumMap, e))
        .toList()
    ..playConfig =
        VidPlayerConfigGen.fromJson(json['playConfig'] as Map<String, dynamic>)
    ..vid = json['vid'] as String
    ..playAuth = json['playAuth'] as String
    ..region = json['region'] as String;
}

Map<String, dynamic> _$VidAuthToJson(VidAuth instance) => <String, dynamic>{
      'coverPath': instance.coverPath,
      'title': instance.title,
      'quality': instance.quality,
      'forceQuality': instance.forceQuality,
      'formats':
          instance.formats.map((e) => _$VidMediaFormatEnumMap[e]).toList(),
      'definitions':
          instance.definitions.map((e) => _$VidDefinitionEnumMap[e]).toList(),
      'playConfig': instance.playConfig,
      'vid': instance.vid,
      'playAuth': instance.playAuth,
      'region': instance.region,
    };

LiveSts _$LiveStsFromJson(Map<String, dynamic> json) {
  return LiveSts()
    ..coverPath = json['coverPath'] as String
    ..title = json['title'] as String
    ..quality = json['quality'] as String
    ..forceQuality = json['forceQuality'] as bool
    ..accessKeyId = json['accessKeyId'] as String
    ..accessKeySecret = json['accessKeySecret'] as String
    ..securityToken = json['securityToken'] as String
    ..region = json['region'] as String
    ..url = json['url'] as String
    ..domain = json['domain'] as String
    ..app = json['app'] as String
    ..stream = json['stream'] as String;
}

Map<String, dynamic> _$LiveStsToJson(LiveSts instance) => <String, dynamic>{
      'coverPath': instance.coverPath,
      'title': instance.title,
      'quality': instance.quality,
      'forceQuality': instance.forceQuality,
      'accessKeyId': instance.accessKeyId,
      'accessKeySecret': instance.accessKeySecret,
      'securityToken': instance.securityToken,
      'region': instance.region,
      'url': instance.url,
      'domain': instance.domain,
      'app': instance.app,
      'stream': instance.stream,
    };

VidPlayerConfigGen _$VidPlayerConfigGenFromJson(Map<String, dynamic> json) {
  return VidPlayerConfigGen()
    ..configMap = json['configMap'] as Map<String, dynamic>;
}

Map<String, dynamic> _$VidPlayerConfigGenToJson(VidPlayerConfigGen instance) =>
    <String, dynamic>{
      'configMap': instance.configMap,
    };
