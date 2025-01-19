package com.vexen.parser;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.net.URL;

import lombok.NonNull;

public final class MediaLicense {
    static final String XML_TAG = "license";


    public final String type;


    public final URL href;
    @NonNull
    public final String value;

    public MediaLicense(String type, URL href, @NonNull String value) {
        this.type = type;
        this.href = href;
        this.value = value;
    }

    @NonNull
    static MediaLicense read(@NonNull XmlPullParser parser) throws XmlPullParserException, IOException {
        final String href = parser.getAttributeValue(XmlPullParser.NO_NAMESPACE, "href");
        return new MediaLicense(
                parser.getAttributeValue(XmlPullParser.NO_NAMESPACE, "type"),
                href == null ? null : Utils.tryParseUrl(href),
                parser.nextText());
    }
}
