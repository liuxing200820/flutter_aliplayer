package com.github.plugin.flutter_aliplayer.util;

import com.aliyun.player.AliPlayer;
import com.aliyun.player.VidPlayerConfigGen;
import com.aliyun.player.source.Definition;
import com.aliyun.player.source.LiveSts;
import com.aliyun.player.source.MediaFormat;
import com.aliyun.player.source.SourceBase;
import com.aliyun.player.source.UrlSource;
import com.aliyun.player.source.VidAuth;
import com.aliyun.player.source.VidMps;
import com.aliyun.player.source.VidSourceBase;
import com.aliyun.player.source.VidSts;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

public class SourceUtil {

    public static boolean process(String type, String source, AliPlayer mAliPlayer) {
        switch (type) {
            case "UrlSource":
                mAliPlayer.setDataSource(SourceUtil.getUrlSource(source));
                return true;
            case "VidSts":
                mAliPlayer.setDataSource(SourceUtil.getVidSts(source));
                return true;
            case "VidMps":
                mAliPlayer.setDataSource(SourceUtil.getVidMps(source));
                return true;
            case "VidAuth":
                mAliPlayer.setDataSource(SourceUtil.getVidAuth(source));
                return true;
            case "LiveSts":
                mAliPlayer.setDataSource(SourceUtil.getLiveSts(source));
                return true;
        }
        return false;
    }

    private static UrlSource getUrlSource(String source) {
        JSONObject jsonObject = JSONUtil.parseObj(source);
        UrlSource urlSource = getSource(jsonObject, new UrlSource());
        urlSource.setUri(jsonObject.getStr("uri"));
        urlSource.setCacheFilePath(jsonObject.getStr("cacheFilePath"));
        return urlSource;
    }

    private static VidSts getVidSts(String source) {
        JSONObject jsonObject = JSONUtil.parseObj(source);
        VidSts vidSts = getVidSource(jsonObject, new VidSts());
        vidSts.setVid(jsonObject.getStr("vid"));
        vidSts.setAccessKeyId(jsonObject.getStr("accessKeyId"));
        vidSts.setAccessKeySecret(jsonObject.getStr("accessKeySecret"));
        vidSts.setSecurityToken(jsonObject.getStr("securityToken"));
        vidSts.setRegion(jsonObject.getStr("region"));
        return vidSts;
    }

    private static VidMps getVidMps(String source) {
        JSONObject jsonObject = JSONUtil.parseObj(source);
        VidMps vidMps = getVidSource(jsonObject, new VidMps());
        vidMps.setMediaId(jsonObject.getStr("mediaId"));
        vidMps.setAccessKeyId(jsonObject.getStr("accessKeyId"));
        vidMps.setAccessKeySecret(jsonObject.getStr("accessKeySecret"));
        vidMps.setSecurityToken(jsonObject.getStr("securityToken"));
        vidMps.setRegion(jsonObject.getStr("region"));
        vidMps.setPlayDomain(jsonObject.getStr("playDomain"));
        vidMps.setAuthInfo(jsonObject.getStr("authInfo"));
        vidMps.setHlsUriToken(jsonObject.getStr("hlsUriToken"));
        return vidMps;
    }

    private static VidAuth getVidAuth(String source) {
        JSONObject jsonObject = JSONUtil.parseObj(source);
        VidAuth vidAuth = getVidSource(jsonObject, new VidAuth());
        vidAuth.setVid(jsonObject.getStr("vid"));
        vidAuth.setPlayAuth(jsonObject.getStr("playAuth"));
        vidAuth.setRegion(jsonObject.getStr("region"));
        return vidAuth;
    }

    private static LiveSts getLiveSts(String source) {
        JSONObject jsonObject = JSONUtil.parseObj(source);
        LiveSts liveSts = getSource(jsonObject, new LiveSts());
        liveSts.setAccessKeyId(jsonObject.getStr("accessKeyId"));
        liveSts.setAccessKeySecret(jsonObject.getStr("accessKeySecret"));
        liveSts.setSecurityToken(jsonObject.getStr("securityToken"));
        liveSts.setRegion(jsonObject.getStr("region"));
        liveSts.setUrl(jsonObject.getStr("url"));
        liveSts.setDomain(jsonObject.getStr("domain"));
        liveSts.setApp(jsonObject.getStr("app"));
        liveSts.setStream(jsonObject.getStr("stream"));
        return liveSts;
    }

    private static <T extends VidSourceBase> T getVidSource(JSONObject jsonObject, T source) {
        getSource(jsonObject, source);
        JSONArray formats = jsonObject.getJSONArray("formats");
        if (formats != null && !formats.isEmpty()) {
            List<MediaFormat> mfs = new ArrayList<>();
            for (Object format : formats) {
                mfs.add(MediaFormat.valueOf((String) format));
            }
            source.setFormats(mfs);
        }
        JSONArray definitions = jsonObject.getJSONArray("definitions");
        if (definitions != null && !definitions.isEmpty()) {
            List<Definition> ds = new ArrayList<>();
            for (Object definition : definitions) {
                ds.add(Definition.valueOf((String) definition));
            }
            source.setDefinition(ds);
        }
        JSONObject playConfig = jsonObject.getJSONObject("playConfig");
        if (CollectionUtil.isNotEmpty(playConfig)) {
            playConfig = playConfig.getJSONObject("configMap");
            VidPlayerConfigGen configGen = new VidPlayerConfigGen();
            if (playConfig.containsKey("PreviewTime")) {
                configGen.setPreviewTime(playConfig.getInt("PreviewTime"));
                playConfig.remove("PreviewTime");
            }
            if (playConfig.containsKey("EncryptType")) {
                configGen.setEncryptType(VidPlayerConfigGen.EncryptType.valueOf(playConfig.getStr("EncryptType")));
                playConfig.remove("EncryptType");
            }
            if (playConfig.containsKey("MtsHlsUriToken")) {
                configGen.setMtsHlsUriToken(playConfig.getStr("MtsHlsUriToken"));
                playConfig.remove("MtsHlsUriToken");
            }
            for (Map.Entry<String, Object> entry : playConfig.entrySet()) {
                if (entry.getValue() instanceof String) {
                    configGen.addPlayerConfig(entry.getKey(), (String) entry.getValue());
                } else {
                    configGen.addPlayerConfig(entry.getKey(), (Integer) entry.getValue());
                }
            }
        }
        return source;
    }

    private static <T extends SourceBase> T getSource(JSONObject jsonObject, T source) {
        source.setTitle(jsonObject.getStr("title"));
        source.setCoverPath(jsonObject.getStr("coverPath"));
        return source;
    }
}
