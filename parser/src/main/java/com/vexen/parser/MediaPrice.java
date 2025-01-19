package com.vexen.parser;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

import lombok.NonNull;

public final class MediaPrice {
    static final String XML_TAG = "price";


    public final String type;


    public final String info;


    public final String price;


    public final String currency;

    public MediaPrice(String type, String info, String price,

                      String currency) {
        this.type = type;
        this.info = info;
        this.price = price;
        this.currency = currency;
    }

    @NonNull
    static MediaPrice read(XmlPullParser parser) throws XmlPullParserException, IOException {
        final MediaPrice result = new MediaPrice(
                parser.getAttributeValue(XmlPullParser.NO_NAMESPACE, "type"),
                parser.getAttributeValue(XmlPullParser.NO_NAMESPACE, "info"),
                parser.getAttributeValue(XmlPullParser.NO_NAMESPACE, "price"),
                parser.getAttributeValue(XmlPullParser.NO_NAMESPACE, "currency"));
        parser.nextTag();
        return result;
    }
}
