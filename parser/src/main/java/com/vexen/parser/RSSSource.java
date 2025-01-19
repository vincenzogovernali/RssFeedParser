package com.vexen.parser;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.net.URL;

import lombok.NonNull;

public final class RSSSource {
    static final String XML_TAG = "source";

    @NonNull
    public final String value;
    @NonNull
    public final URL url;

    public RSSSource(@NonNull String value, @NonNull URL url) {
        this.value = value;
        this.url = url;
    }

    @NonNull
    static RSSSource read(@NonNull XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, XmlPullParser.NO_NAMESPACE, XML_TAG);
        URL url = Utils.nonNullUrl(parser.getAttributeValue(XmlPullParser.NO_NAMESPACE, "url"));
        return new RSSSource(Utils.nonNullString(parser.nextText()), url);
    }
}
