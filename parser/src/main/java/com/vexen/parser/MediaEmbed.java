package com.vexen.parser;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import lombok.NonNull;

public final class MediaEmbed {
    static final String XML_TAG = "embed";


    public final URL url;


    public final Integer width;


    public final Integer height;
    @NonNull
    public final Map<String, String> values;

    public MediaEmbed(URL url, Integer width, Integer height,
                      @NonNull Map<String, String> values) {
        this.url = url;
        this.width = width;
        this.height = height;
        this.values = Collections.unmodifiableMap(values);
    }

    @NonNull
    static MediaEmbed read(@NonNull XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, null, XML_TAG);
        final String url = parser.getAttributeValue(XmlPullParser.NO_NAMESPACE, "url");
        final String width = parser.getAttributeValue(XmlPullParser.NO_NAMESPACE, "width");
        final String height = parser.getAttributeValue(XmlPullParser.NO_NAMESPACE, "height");
        final Map<String, String> values = new HashMap<>();

        while (parser.nextTag() == XmlPullParser.START_TAG) {
            parser.require(XmlPullParser.START_TAG, null, "param");
            values.put(Utils.nonNullString(parser.getAttributeValue(XmlPullParser.NO_NAMESPACE, "name")),
                    parser.nextText());
        }

        return new MediaEmbed(url == null ? null : Utils.tryParseUrl(url),
                width == null ? null : Utils.tryParseInt(width),
                height == null ? null : Utils.tryParseInt(height),
                values);
    }
}
