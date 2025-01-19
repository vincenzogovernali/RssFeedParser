package com.vexen.parser;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.net.URL;

import lombok.NonNull;

public final class MediaPlayer {
    static final String XML_TAG = "player";

    @NonNull
    public final URL url;


    public final Integer height;


    public final Integer width;

    public MediaPlayer(@NonNull URL url, Integer height, Integer width) {
        this.url = url;
        this.height = height;
        this.width = width;
    }

    @NonNull
    static MediaPlayer read(@NonNull XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, null, XML_TAG);
        final String width = parser.getAttributeValue(XmlPullParser.NO_NAMESPACE, "width");
        final String height = parser.getAttributeValue(XmlPullParser.NO_NAMESPACE, "height");
        final MediaPlayer result = new MediaPlayer(
                Utils.nonNullUrl(parser.getAttributeValue(XmlPullParser.NO_NAMESPACE, "url")),
                width == null ? null : Utils.tryParseInt(width),
                height == null ? null : Utils.tryParseInt(height));
        parser.nextTag();
        return result;
    }
}
