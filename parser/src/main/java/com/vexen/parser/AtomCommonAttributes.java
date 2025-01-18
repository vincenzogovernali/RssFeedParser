package com.vexen.parser;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.xmlpull.v1.XmlPullParser;

import java.net.URI;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AtomCommonAttributes {
    @Nullable
    public final URI base;
    @Nullable
    public final String lang;



    AtomCommonAttributes(@NonNull XmlPullParser parser) {
        final String baseString = parser.getAttributeValue(XmlPullParser.NO_NAMESPACE, "base");
        base = baseString == null ? null : Utils.tryParseUri(baseString);
        lang = parser.getAttributeValue(XmlPullParser.NO_NAMESPACE, "lang");
    }

    AtomCommonAttributes(@Nullable AtomCommonAttributes source) {
        if (source == null) {
            base = null;
            lang = null;
        } else {
            base = source.base;
            lang = source.lang;
        }
    }
}
