package com.vexen.parser;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.net.URL;

import lombok.NonNull;

public final class MediaPeerLink {
    static final String XML_TAG = "peerLink";


    public final String type;
    @NonNull
    public final URL href;

    public MediaPeerLink(String type, @NonNull URL href) {
        this.type = type;
        this.href = href;
    }

    @NonNull
    static MediaPeerLink read(@NonNull XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, null, XML_TAG);
        final MediaPeerLink result = new MediaPeerLink(
                parser.getAttributeValue(XmlPullParser.NO_NAMESPACE, "type"),
                Utils.nonNullUrl(parser.getAttributeValue(XmlPullParser.NO_NAMESPACE, "href")));
        parser.nextTag();
        return result;
    }
}
