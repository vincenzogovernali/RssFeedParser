package com.vexen.parser;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

import lombok.NonNull;

public final class MediaTitle {


    public final String type;
    @NonNull
    public final String value;

    public MediaTitle(String type, @NonNull String value) {
        this.type = type;
        this.value = value;
    }

    @NonNull
    static MediaTitle read(XmlPullParser parser) throws XmlPullParserException, IOException {
        return new MediaTitle(parser.getAttributeValue(XmlPullParser.NO_NAMESPACE, "type"),
                parser.nextText());
    }
}
