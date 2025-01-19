package com.vexen.parser;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.net.URI;

import lombok.NonNull;

public final class AtomContent extends AtomText {
    static final String XML_TAG = "content";


    public final URI src;

    public AtomContent(URI src, @NonNull AtomText atomText) {
        super(atomText);
        this.src = src;
    }

    @NonNull
    static AtomContent read(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, null, XML_TAG);
        final String srcString = parser.getAttributeValue(XmlPullParser.NO_NAMESPACE, "src");
        return new AtomContent(
                srcString == null ? null : Utils.tryParseUri(srcString),
                AtomText.read(parser)
        );
    }
}
