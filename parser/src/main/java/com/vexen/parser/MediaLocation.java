package com.vexen.parser;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

import lombok.NonNull;

public final class MediaLocation {
    static final String XML_TAG = "location";


    public final String description;


    public final Integer start;


    public final Integer end;

    public MediaLocation(String description, Integer start,

                         Integer end) {
        this.description = description;
        this.start = start;
        this.end = end;
    }

    @NonNull
    static MediaLocation read(@NonNull XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, null, XML_TAG);
        final String start = parser.getAttributeValue(XmlPullParser.NO_NAMESPACE, "start");
        final String end = parser.getAttributeValue(XmlPullParser.NO_NAMESPACE, "end");
        final MediaLocation result = new MediaLocation(
                parser.getAttributeValue(XmlPullParser.NO_NAMESPACE, "description"),
                start == null ? null : Utils.parseMediaRssTime(start),
                end == null ? null : Utils.parseMediaRssTime(end));
        Utils.skipTag(parser);
        return result;
    }
}
