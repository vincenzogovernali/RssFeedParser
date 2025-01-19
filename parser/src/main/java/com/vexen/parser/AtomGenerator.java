package com.vexen.parser;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.net.URI;

import lombok.NonNull;

public final class AtomGenerator extends AtomCommonAttributes {
    static final String XML_TAG = "generator";


    public final URI uri;


    public final String version;
    @NonNull
    public final String value;

    public AtomGenerator(AtomCommonAttributes atomCommonAttributes, URI uri,

                         String version, @NonNull String value) {
        super(atomCommonAttributes);
        this.uri = uri;
        this.version = version;
        this.value = value;
    }

    @NonNull
    static AtomGenerator read(@NonNull XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, null, XML_TAG);
        final String uri = parser.getAttributeValue(XmlPullParser.NO_NAMESPACE, "uri");
        return new AtomGenerator(
                new AtomCommonAttributes(parser),
                uri == null ? null : Utils.tryParseUri(uri),
                parser.getAttributeValue(XmlPullParser.NO_NAMESPACE, "uri"),
                parser.nextText());
    }
}
