package com.vexen.parser;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

import lombok.NonNull;

public final class RSSGuid {
    static final String XML_TAG = "guid";

    @NonNull
    public final String value;


    public final Boolean isPermalink;

    public RSSGuid(@NonNull String value, Boolean isPermalink) {
        this.value = value;
        this.isPermalink = isPermalink;
    }

    @NonNull
    static RSSGuid read(@NonNull XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, XmlPullParser.NO_NAMESPACE, XML_TAG);
        final String permalink = parser.getAttributeValue(XmlPullParser.NO_NAMESPACE, "isPermalink");
        return new RSSGuid(Utils.nonNullString(parser.nextText()),
                permalink == null ? null : Boolean.valueOf(permalink));
    }
}
