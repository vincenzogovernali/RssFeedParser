package com.vexen.parser;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.net.URI;

import lombok.NonNull;

public final class MediaCredit {
    static final String XML_TAG = "credit";


    public final String role;


    public final URI scheme;
    @NonNull
    public final String value;

    public MediaCredit(String role, URI scheme, @NonNull String value) {
        this.role = role;
        this.scheme = scheme;
        this.value = value;
    }

    @NonNull
    static MediaCredit read(@NonNull XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, null, XML_TAG);
        final String scheme = parser.getAttributeValue(XmlPullParser.NO_NAMESPACE, "scheme");
        return new MediaCredit(parser.getAttributeValue(XmlPullParser.NO_NAMESPACE, "scheme"),
                scheme == null ? null : Utils.tryParseUri(scheme),
                parser.nextText());
    }
}
