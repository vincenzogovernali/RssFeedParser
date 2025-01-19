package com.vexen.parser;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.net.URI;

import lombok.NonNull;

public final class MediaRating {
    static final String XML_TAG = "rating";


    public final URI scheme;
    @NonNull
    public final String value;

    public MediaRating(URI scheme, @NonNull String value) {
        this.scheme = scheme;
        this.value = value;
    }

    @NonNull
    static MediaRating read(@NonNull XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, null, XML_TAG);
        final String uri = parser.getAttributeValue(XmlPullParser.NO_NAMESPACE, "uri");
        return new MediaRating(uri == null ? null : Utils.tryParseUri(uri), parser.nextText());
    }
}
