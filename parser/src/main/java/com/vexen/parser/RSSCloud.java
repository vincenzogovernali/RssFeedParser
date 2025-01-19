package com.vexen.parser;


import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

import lombok.NonNull;

public final class RSSCloud {
    static final String XML_TAG = "cloud";

    // RSS spec doesn't declare those attributes required nor it defines them optional


    public final String domain;


    public final Integer port;


    public final String path;


    public final String registerProcedure;


    public final String protocol;

    public RSSCloud(String domain, Integer port, String path,

                    String registerProcedure, String protocol) {
        this.domain = domain;
        this.port = port;
        this.path = path;
        this.registerProcedure = registerProcedure;
        this.protocol = protocol;
    }

    @NonNull
    static RSSCloud read(@NonNull XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, XmlPullParser.NO_NAMESPACE, XML_TAG);
        final String port = parser.getAttributeValue(XmlPullParser.NO_NAMESPACE, "port");
        final String domain = parser.getAttributeValue(XmlPullParser.NO_NAMESPACE, "domain");
        final String path = parser.getAttributeValue(XmlPullParser.NO_NAMESPACE, "path");
        final String procedure = parser.getAttributeValue(XmlPullParser.NO_NAMESPACE, "registerProcedure");
        final String protocol = parser.getAttributeValue(XmlPullParser.NO_NAMESPACE, "protocol");
        parser.next();
        return new RSSCloud(
                domain, port == null ? null : Utils.tryParseInt(port), path, procedure, protocol);
    }
}
