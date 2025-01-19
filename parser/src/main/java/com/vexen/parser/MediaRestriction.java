package com.vexen.parser;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

import lombok.NonNull;

public final class MediaRestriction {
    static final String XML_TAG = "restriction";


    public final String relationship;


    public final String type;
    @NonNull
    public final String value;

    public MediaRestriction(String relationship, String type,
                            @NonNull String value) {
        this.relationship = relationship;
        this.type = type;
        this.value = value;
    }

    @NonNull
    static MediaRestriction read(@NonNull XmlPullParser parser) throws XmlPullParserException, IOException {
        return new MediaRestriction(
                parser.getAttributeValue(XmlPullParser.NO_NAMESPACE, "relationship"),
                parser.getAttributeValue(XmlPullParser.NO_NAMESPACE, "type"),
                parser.nextText());
    }
}
