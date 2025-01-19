package com.vexen.parser;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.net.URI;

import lombok.NonNull;

public final class MediaCategory {
    static final String XML_TAG = "category";


    public final URI scheme;


    public final String label;
    @NonNull
    public final String value;

    public MediaCategory(URI scheme, String label, @NonNull String value) {
        this.scheme = scheme;
        this.label = label;
        this.value = value;
    }

    @NonNull
    static MediaCategory read(@NonNull XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, null, XML_TAG);
        final String scheme = parser.getAttributeValue(XmlPullParser.NO_NAMESPACE, "scheme");
        return new MediaCategory(scheme == null ? null : Utils.tryParseUri(scheme),
                parser.getAttributeValue(XmlPullParser.NO_NAMESPACE, "label"),
                parser.nextText());
    }
}
