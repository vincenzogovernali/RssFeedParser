package com.vexen.parser;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

import lombok.NonNull;

public final class MediaStatus {
    static final String XML_TAG = "status";


    public final String state;


    public final String reason;

    public MediaStatus(String state, String reason) {
        this.state = state;
        this.reason = reason;
    }

    @NonNull
    static MediaStatus read(@NonNull XmlPullParser parser) throws XmlPullParserException, IOException {
        final MediaStatus result = new MediaStatus(
                parser.getAttributeValue(XmlPullParser.NO_NAMESPACE, "state"),
                parser.getAttributeValue(XmlPullParser.NO_NAMESPACE, "reason"));
        parser.nextTag();
        return result;
    }
}
