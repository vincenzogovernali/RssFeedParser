package com.vexen.rssfeedparser;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public final class ItunesItem {
    private static final String TAG = "Earl.ItunesItem";
    @Nullable
    public final String author;
    @Nullable
    public final Boolean block;
    @Nullable
    public final URL image;
    @Nullable
    public final Integer duration;
    @Nullable
    public final String explicit;
    @Nullable
    public final Boolean isClosedCaptioned;
    @Nullable
    public final Integer order;
    @Nullable
    public final String subtitle;
    @Nullable
    public final String summary;
    /**
     * Allows users to search on text keywords
     * This one is now deprecated by Apple
     */
    @NonNull
    public final List<String> keywords;

    private enum ST {author, block, duration, explicit, isClosedCaptioned, order, subtitle, summary}

    static class ItunesItemBuilder {
        private final Map<ST, String> map = new EnumMap<>(ST.class);
        private URL image;
        private List<String> keywords;

        void parseTag(@NonNull XmlPullParser parser) throws IOException, XmlPullParserException {
            final String tagName = parser.getName();
            switch (tagName) {
                case "image":
                    final String imageStr = parser.getAttributeValue(XmlPullParser.NO_NAMESPACE, "href");
                    image = imageStr == null ? null : Utils.tryParseUrl(imageStr);
                    parser.nextText();
                    break;
                case "keywords":
                    keywords = Arrays.asList(parser.nextText().split(" "));
                    break;
                default:
                    try {
                        map.put(ST.valueOf(tagName), parser.nextText());
                    } catch (IllegalArgumentException ignored) {
                        Log.w(TAG, "Unknown itunes item tag " + tagName);
                        Utils.skipTag(parser);
                    }
            }
        }

        @NonNull
        ItunesItem build() {
            return new ItunesItem(
                    map.remove(ST.author),
                    map.containsKey(ST.block) ? "yes".equals(map.remove(ST.block)) : null,
                    image,
                    map.containsKey(ST.duration) ? Utils.parseItunesDuration(map.remove(ST.duration)) : null,
                    map.remove(ST.explicit),
                    map.containsKey(ST.isClosedCaptioned) ? " yes".equals(
                            map.remove(ST.isClosedCaptioned)) : null,
                    map.containsKey(ST.order) ? Utils.tryParseInt(map.remove(ST.order)) : null,
                    map.remove(ST.subtitle),
                    map.remove(ST.summary),
                    keywords == null ? new LinkedList<String>() : keywords);
        }
    }
}
