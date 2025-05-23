package com.vexen.parser;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.net.URL;
import java.util.EnumMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public final class ItunesFeed {
    private static final String TAG = "Earl.ItunesFeed";


    public final String author;


    public final Boolean block;
    @NonNull
    public final List<ItunesCategory> categories;


    public final URL image;


    public final String explicit;


    public final Boolean complete;


    public final URL newFeedURL;


    public final ItunesOwner owner;


    public final String subtitle;


    public final String summary;

    private enum ST {author, block, explicit, complete, subtitle, summary}

    static class ItunesFeedBuilder {
        private final Map<ST, String> map = new EnumMap<>(ST.class);
        private final List<ItunesCategory> categories = new LinkedList<>();
        private ItunesOwner owner;
        private URL image;
        private URL newFeedURL;

        void parseTag(@NonNull XmlPullParser parser) throws IOException, XmlPullParserException {
            final String tagName = parser.getName();
            switch (tagName) {
                case ItunesCategory.XML_TAG:
                    categories.add(ItunesCategory.read(parser));
                    break;
                case ItunesOwner.XML_TAG:
                    owner = ItunesOwner.read(parser);
                    break;
                case "image":
                    image = Utils.tryParseUrl(parser.getAttributeValue(XmlPullParser.NO_NAMESPACE, "href"));
                    parser.nextToken();
                    break;
                case "new-feed-url":
                    newFeedURL = Utils.tryParseUrl(parser.nextText());
                    break;
                default:
                    try {
                        map.put(ST.valueOf(tagName), parser.nextText());
                    } catch (IllegalArgumentException ignored) {
                        Log.w(TAG, "Unknown Itunes feed tag " + tagName + " skipping..");
                        Utils.skipTag(parser);
                    }
            }
        }

        @NonNull
        ItunesFeed build() {
            return new ItunesFeed(
                    map.remove(ST.author),
                    map.containsKey(ST.block) ? ("yes".equals(map.remove(ST.block))) : null,
                    categories,
                    image,
                    map.remove(ST.explicit),
                    map.containsKey(ST.complete) ? ("yes".equals(map.remove(ST.complete))) : null,
                    newFeedURL,
                    owner,
                    map.remove(ST.subtitle),
                    map.remove(ST.summary));
        }
    }
}
