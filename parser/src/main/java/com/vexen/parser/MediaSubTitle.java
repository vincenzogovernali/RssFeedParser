package com.vexen.parser;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.net.URL;

import lombok.NonNull;

public final class MediaSubTitle {
    static final String XML_TAG = "subTitle";


    public final String type;


    public final String lang;
    @NonNull
    public final URL href;

    public MediaSubTitle(String type, String lang, @NonNull URL href) {
        this.type = type;
        this.lang = lang;
        this.href = href;
    }

    @NonNull
    static MediaSubTitle read(@NonNull XmlPullParser parser) throws XmlPullParserException, IOException {
        final MediaSubTitle result = new MediaSubTitle(
                parser.getAttributeValue(XmlPullParser.NO_NAMESPACE, "type"),
                parser.getAttributeValue(XmlPullParser.NO_NAMESPACE, "lang"),
                Utils.nonNullUrl(parser.getAttributeValue(XmlPullParser.NO_NAMESPACE, "href")));
        parser.nextTag();
        return result;
    }
}
