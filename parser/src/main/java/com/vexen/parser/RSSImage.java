package com.vexen.parser;


import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.net.URL;
import java.util.EnumMap;
import java.util.Map;

import lombok.NonNull;

public final class RSSImage {
    static final String XML_TAG = "image";
    private static final String TAG = "Earl.RSSImage";
    @NonNull
    public final String title;


    public final String description;
    @NonNull
    public final URL link;
    @NonNull
    public final URL url;


    public final Integer width;


    public final Integer height;

    public RSSImage(@NonNull String title, String description, @NonNull URL link,
                    @NonNull URL url, Integer width, Integer height) {
        this.title = title;
        this.description = description;
        this.link = link;
        this.url = url;
        this.width = width;
        this.height = height;
    }

    @NonNull
    static RSSImage read(@NonNull XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, XmlPullParser.NO_NAMESPACE, XML_TAG);
        final Map<ST, String> map = new EnumMap<>(ST.class);
        while (parser.nextTag() == XmlPullParser.START_TAG) {
            try {
                map.put(ST.valueOf(parser.getName()), parser.nextText());
            } catch (IllegalArgumentException ignored) {
                Log.w(TAG, "Unknown RSS image tag " + parser.getName());
                Utils.skipTag(parser);
            }
            Utils.finishTag(parser);
        }
        return new RSSImage(
                Utils.nonNullString(map.remove(ST.title)),
                map.remove(ST.description),
                Utils.nonNullUrl(map.remove(ST.link)),
                Utils.nonNullUrl(map.remove(ST.url)),
                map.containsKey(ST.width) ? Utils.tryParseInt(map.remove(ST.width)) : null,   //default
                map.containsKey(ST.height) ? Utils.tryParseInt(map.remove(ST.height)) : null);//values 88X31
    }

    private enum ST {title, description, link, url, width, height}
}
