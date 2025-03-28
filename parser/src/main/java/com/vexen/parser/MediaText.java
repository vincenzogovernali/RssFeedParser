package com.vexen.parser;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

import lombok.NonNull;

public final class MediaText {
    static final String XML_TAG = "text";


    public final String type;


    public final String lang;


    public final Integer start;


    public final Integer end;
    @NonNull
    public final String value;

    public MediaText(String type, String lang, Integer start,

                     Integer end, @NonNull String value) {
        this.type = type;
        this.lang = lang;
        this.start = start;
        this.end = end;
        this.value = value;
    }

    @NonNull
    static MediaText read(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, null, XML_TAG);
        final String start = parser.getAttributeValue(XmlPullParser.NO_NAMESPACE, "start");
        final String end = parser.getAttributeValue(XmlPullParser.NO_NAMESPACE, "end");
        return new MediaText(
                parser.getAttributeValue(XmlPullParser.NO_NAMESPACE, "type"),
                parser.getAttributeValue(XmlPullParser.NO_NAMESPACE, "lang"),
                start == null ? null : Utils.parseRFC2326NPT(start),
                end == null ? null : Utils.parseRFC2326NPT(end),
                parser.nextText());
    }
}
