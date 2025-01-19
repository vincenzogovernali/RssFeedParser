package com.vexen.parser;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.net.URI;

import lombok.NonNull;

public final class AtomLink extends AtomCommonAttributes implements Enclosure {
    static final String XML_TAG = "link";
    @NonNull
    public final URI href;


    public final String rel;


    public final String type;


    public final String hreflang;


    public final String title;


    public final Integer length;

    public AtomLink(AtomCommonAttributes atomCommonAttributes, @NonNull URI href,

                    String rel, String type, String hreflang,

                    String title, Integer length) {
        super(atomCommonAttributes);
        this.href = href;
        this.rel = rel;
        this.type = type;
        this.hreflang = hreflang;
        this.title = title;
        this.length = length;
    }

    @NonNull
    static AtomLink read(@NonNull XmlPullParser parser)
            throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, null, XML_TAG);
        final String length = parser.getAttributeValue(XmlPullParser.NO_NAMESPACE, "length");
        final AtomLink result = new AtomLink(
                new AtomCommonAttributes(parser),
                Utils.nonNullUri(parser.getAttributeValue(XmlPullParser.NO_NAMESPACE, "href")),
                parser.getAttributeValue(XmlPullParser.NO_NAMESPACE, "rel"),
                parser.getAttributeValue(XmlPullParser.NO_NAMESPACE, "type"),
                parser.getAttributeValue(XmlPullParser.NO_NAMESPACE, "hreflang"),
                parser.getAttributeValue(XmlPullParser.NO_NAMESPACE, "title"),
                length == null ? null : Utils.tryParseInt(length));
        parser.nextTag();
        return result;
    }

    @NonNull
    @Override
    public String getLink() {
        return href.toString();
    }


    @Override
    public Integer getLength() {
        return length;
    }


    @Override
    public String getType() {
        return type;
    }
}
