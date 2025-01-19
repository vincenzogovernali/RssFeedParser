package com.vexen.parser;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.net.URL;

import lombok.NonNull;

public final class MediaContent extends MediaCommon {
    static final String XML_TAG = "content";


    public final URL url;


    public final Integer fileSize;


    public final String type;


    public final String medium;


    public final Boolean isDefault;


    public final String expression;


    public final Integer bitrate;


    public final Integer framerate;


    public final Integer samplingrate;


    public final Integer channels;


    public final Integer duration;


    public final Integer height;


    public final Integer width;


    public final String lang;

    public MediaContent(URL url, Integer fileSize, String type,

                        String medium, Boolean isDefault,

                        String expression, Integer bitrate,

                        Integer framerate, Integer samplingrate,

                        Integer channels, Integer duration,

                        Integer height, Integer width, String lang,
                        @NonNull MediaCommon source) {
        super(source);
        this.url = url;
        this.fileSize = fileSize;
        this.type = type;
        this.medium = medium;
        this.isDefault = isDefault;
        this.expression = expression;
        this.bitrate = bitrate;
        this.framerate = framerate;
        this.samplingrate = samplingrate;
        this.channels = channels;
        this.duration = duration;
        this.height = height;
        this.width = width;
        this.lang = lang;
    }

    @NonNull
    static MediaContent read(@NonNull XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, null, XML_TAG);
        final String url = parser.getAttributeValue(XmlPullParser.NO_NAMESPACE, "url");
        final String fileSize = parser.getAttributeValue(XmlPullParser.NO_NAMESPACE, "fileSize");
        final String isDefault = parser.getAttributeValue(XmlPullParser.NO_NAMESPACE, "isDefault");
        final String bitrate = parser.getAttributeValue(XmlPullParser.NO_NAMESPACE, "bitrate");
        final String framerate = parser.getAttributeValue(XmlPullParser.NO_NAMESPACE, "framerate");
        final String samplingrate = parser.getAttributeValue(XmlPullParser.NO_NAMESPACE, "samplingrate");
        final String channels = parser.getAttributeValue(XmlPullParser.NO_NAMESPACE, "channels");
        final String duration = parser.getAttributeValue(XmlPullParser.NO_NAMESPACE, "duration");
        final String height = parser.getAttributeValue(XmlPullParser.NO_NAMESPACE, "height");
        final String width = parser.getAttributeValue(XmlPullParser.NO_NAMESPACE, "width");
        return new MediaContent(url == null ? null : Utils.tryParseUrl(url),
                fileSize == null ? null : Utils.tryParseInt(fileSize),
                parser.getAttributeValue(XmlPullParser.NO_NAMESPACE, "type"),
                parser.getAttributeValue(XmlPullParser.NO_NAMESPACE, "medium"),
                isDefault == null ? null : Boolean.valueOf(isDefault),
                parser.getAttributeValue(XmlPullParser.NO_NAMESPACE, "expression"),
                bitrate == null ? null : Utils.tryParseInt(bitrate),
                framerate == null ? null : Utils.tryParseInt(framerate),
                samplingrate == null ? null : Utils.tryParseInt(samplingrate),
                channels == null ? null : Utils.tryParseInt(channels),
                duration == null ? null : Utils.tryParseInt(duration),
                height == null ? null : Utils.tryParseInt(height),
                width == null ? null : Utils.tryParseInt(width),
                parser.getAttributeValue(XmlPullParser.NO_NAMESPACE, "lang"),
                MediaCommon.read(parser));
    }
}
