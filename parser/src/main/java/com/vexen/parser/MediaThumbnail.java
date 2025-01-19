package com.vexen.parser;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.net.URL;

import lombok.NonNull;

public final class MediaThumbnail {
    static final String XML_TAG = "thumbnail";

    @NonNull
    public final URL url;


    public final Integer width;


    public final Integer height;
    /**
     * In milliseconds
     */


    public final Integer time;

    public MediaThumbnail(@NonNull URL url, Integer width, Integer height,

                          Integer time) {
        this.url = url;
        this.width = width;
        this.height = height;
        this.time = time;
    }

    @NonNull
    static MediaThumbnail read(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, null, XML_TAG);
        final String width = parser.getAttributeValue(XmlPullParser.NO_NAMESPACE, "width");
        final String height = parser.getAttributeValue(XmlPullParser.NO_NAMESPACE, "height");
        final String time = parser.getAttributeValue(XmlPullParser.NO_NAMESPACE, "time");
        final MediaThumbnail result = new MediaThumbnail(
                Utils.nonNullUrl(parser.getAttributeValue(XmlPullParser.NO_NAMESPACE, "url")),
                width == null ? null : Utils.tryParseInt(width),
                height == null ? null : Utils.tryParseInt(height),
                time == null ? null : Utils.parseRFC2326NPT(time));
        parser.nextTag();
        return result;
    }
}
