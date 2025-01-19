package com.vexen.parser;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

import lombok.NonNull;

public final class RSSCategory {
    static final String XML_TAG = "category";

    @NonNull
    public final String value;


    public final String domain;

    public RSSCategory(@NonNull String value, String domain) {
        this.value = value;
        this.domain = domain;
    }

    @NonNull
    static RSSCategory read(@NonNull XmlPullParser parser)
            throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, XmlPullParser.NO_NAMESPACE, XML_TAG);
        return new RSSCategory(Utils.nonNullString(parser.nextText()),
                parser.getAttributeValue(XmlPullParser.NO_NAMESPACE, "domain"));
    }
}
