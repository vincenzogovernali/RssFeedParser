package com.vexen.parser;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import lombok.NonNull;

public final class MediaGroup extends MediaCommon {
    static final String XML_TAG = "group";
    private static final String TAG = "Earl.MediaGroup";

    @NonNull
    public final List<MediaContent> contents;

    public MediaGroup(@NonNull MediaCommon common,
                      @NonNull List<MediaContent> contents) {
        super(common);
        this.contents = Collections.unmodifiableList(contents);
    }

    @NonNull
    static MediaGroup read(@NonNull XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, null, XML_TAG);
        final List<MediaContent> contents = new LinkedList<>();
        final MediaCommonBuilder builder = new MediaCommonBuilder();
        while (parser.nextTag() == XmlPullParser.START_TAG) {
            final String tagName = parser.getName();
            if (MediaContent.XML_TAG.equals(tagName)) {
                contents.add(MediaContent.read(parser));
            } else {
                if (!builder.parseTag(parser)) {
                    Log.w(TAG, "Unexpected tag found in media:group: " + tagName);
                    Utils.skipTag(parser);
                }
            }
            Utils.finishTag(parser);
        }
        return new MediaGroup(builder.build(), contents);
    }
}
