package com.vexen.parser;

import org.xmlpull.v1.XmlPullParser;

import java.net.URI;

import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class AtomCommonAttributes {


    public final URI base;


    public final String lang;



    AtomCommonAttributes(@NonNull XmlPullParser parser) {
        final String baseString = parser.getAttributeValue(XmlPullParser.NO_NAMESPACE, "base");
        base = baseString == null ? null : Utils.tryParseUri(baseString);
        lang = parser.getAttributeValue(XmlPullParser.NO_NAMESPACE, "lang");
    }

    AtomCommonAttributes(AtomCommonAttributes source) {
        if (source == null) {
            base = null;
            lang = null;
        } else {
            base = source.base;
            lang = source.lang;
        }
    }
}
