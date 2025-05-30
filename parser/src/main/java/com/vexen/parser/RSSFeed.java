package com.vexen.parser;

import android.text.TextUtils;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.EnumMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public final class RSSFeed implements Feed {
    static final String XML_TAG = "channel";

    private static final String TAG = "Earl.RSSFeed";
    @NonNull
    public final String title;
    @NonNull
    public final URL link;
    @NonNull
    public final String description;


    public final String language;


    public final String copyright;


    public final String managingEditor;


    public final String webMaster;


    public final Date pubDate;


    public final Date lastBuildDate;
    @NonNull
    public final List<RSSCategory> categories;


    public final String generator;


    public final URL docs;


    public final RSSCloud cloud;


    public final Integer ttl;


    public final String rating;


    public final RSSImage image;


    public final RSSTextInput textInput;
    @NonNull
    public final List<Integer> skipHours;
    @NonNull
    public final List<String> skipDays;
    @NonNull
    public final List<RSSItem> items;


    public final ItunesFeed itunes;


    public final MediaCommon media;


    public final Content content;

    @NonNull
    static RSSFeed read(@NonNull XmlPullParser parser, int maxItems)
            throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, XmlPullParser.NO_NAMESPACE, XML_TAG);

        final Map<ST, String> map = new EnumMap<>(ST.class);
        final List<RSSItem> items = new LinkedList<>();
        final List<RSSCategory> categories = new LinkedList<>();
        RSSCloud cloud = null;
        RSSImage image = null;
        RSSTextInput textInput = null;
        final List<Integer> skipHours = new LinkedList<>();
        final List<String> skipDays = new LinkedList<>();
        ItunesFeed.ItunesFeedBuilder itunesBuilder = null;
        MediaCommon.MediaCommonBuilder mediaBuilder = null;
        Content.ContentBuilder contentBuilder = null;

        while (parser.nextTag() == XmlPullParser.START_TAG && (maxItems < 1 || items
                .size() < maxItems)) {
            final String namespace = parser.getNamespace();
            if (XmlPullParser.NO_NAMESPACE.equalsIgnoreCase(namespace)) {
                final String tagName = parser.getName();
                switch (tagName) {
                    case RSSItem.XML_TAG:
                        items.add(RSSItem.read(parser));
                        break;
                    case RSSCategory.XML_TAG:
                        categories.add(RSSCategory.read(parser));
                        break;
                    case RSSCloud.XML_TAG:
                        cloud = RSSCloud.read(parser);
                        break;
                    case RSSImage.XML_TAG:
                        image = RSSImage.read(parser);
                        break;
                    case RSSTextInput.XML_TAG:
                        textInput = RSSTextInput.read(parser);
                        break;
                    case "skipHours":
                        while (parser.nextTag() == XmlPullParser.START_TAG && "hour".equals(
                                parser.getName())) {
                            skipHours.add(Utils.tryParseInt(parser.nextText()));
                            Utils.finishTag(parser);
                        }
                        break;
                    case "skipDays":
                        while (parser.nextTag() == XmlPullParser.START_TAG && "day".equals(
                                parser.getName())) {
                            skipDays.add(parser.nextText());
                            Utils.finishTag(parser);
                        }
                        break;
                    default:
                        try {
                            map.put(ST.valueOf(tagName), parser.nextText());
                        } catch (IllegalArgumentException ignored) {
                            Log.w(TAG, "Unknown RSS feed tag " + tagName);
                            Utils.skipTag(parser);
                        }
                }
            } else {
                if (Utils.ITUNES_NAMESPACE.equalsIgnoreCase(namespace)) {
                    if (itunesBuilder == null) {
                        itunesBuilder = new ItunesFeed.ItunesFeedBuilder();
                    }
                    itunesBuilder.parseTag(parser);
                } else {
                    if (Utils.MEDIA_NAMESPACE.equalsIgnoreCase(namespace)) {
                        if (mediaBuilder == null) {
                            mediaBuilder = new MediaCommon.MediaCommonBuilder();
                        }
                        if (!mediaBuilder.parseTag(parser)) {
                            Log.w(TAG, "Unknown mrss tag on feed level");
                            Utils.skipTag(parser);
                        }
                    } else {
                        if (Utils.CONTENT_NAMESPACE.equalsIgnoreCase(namespace)) {
                            if (contentBuilder == null) {
                                contentBuilder = new Content.ContentBuilder();
                            }
                            contentBuilder.parseTag(parser);
                        } else {
                            Log.w(TAG, "Unknown RSS feed extension " + parser.getNamespace());
                            Utils.skipTag(parser);
                        }
                    }
                }
            }
            Utils.finishTag(parser);
        }

        return new RSSFeed(
                Utils.nonNullString(map.remove(ST.title)),
                Utils.nonNullUrl(map.remove(ST.link)),
                Utils.nonNullString(map.remove(ST.description)),
                map.remove(ST.language),
                map.remove(ST.copyright),
                map.remove(ST.managingEditor),
                map.remove(ST.webMaster),
                map.containsKey(ST.pubDate) ? Utils.parseDate(map.remove(ST.pubDate)) : null,
                map.containsKey(ST.lastBuildDate) ? Utils
                        .parseDate(map.remove(ST.lastBuildDate)) : null,
                categories,
                map.remove(ST.generator),
                map.containsKey(ST.docs) ? Utils.tryParseUrl(map.remove(ST.docs)) : null,
                cloud,
                map.containsKey(ST.ttl) ? Utils.tryParseInt(map.remove(ST.ttl)) : null,
                map.remove(ST.rating),
                image,
                textInput,
                skipHours,
                skipDays,
                items,
                itunesBuilder == null ? null : itunesBuilder.build(),
                mediaBuilder == null ? null : mediaBuilder.build(),
                contentBuilder == null ? null : contentBuilder.build());
    }


    @Override
    public String getLink() {
        return link.toString();
    }


    @Override
    public Date getPublicationDate() {
        return pubDate;
    }


    @Override
    public Date getUpdatedDate() {
        return null;
    }

    @NonNull
    @Override
    public String getTitle() {
        return title;
    }

    @NonNull
    @Override
    public String getDescription() {
        return TextUtils.isEmpty(description) && content != null ? content.encoded : description;
    }


    @Override
    public String getCopyright() {
        if (copyright != null) {
            return copyright;
        }
        if (media != null && media.license != null) {
            return media.license.value;
        }
        return null;
    }


    @Override
    public String getImageLink() {
        if (image != null) {
            return image.url.toString();
        }
        if (itunes != null && itunes.image != null) {
            return itunes.image.toString();
        }
        if (media != null && !media.thumbnails.isEmpty()) {
            return media.thumbnails.get(0).url.toString();
        }
        return null;
    }


    @Override
    public String getAuthor() {
        if (managingEditor != null) {
            return managingEditor;
        }
        if (itunes != null && itunes.author != null) {
            return itunes.author;
        }
        if (itunes != null && itunes.owner != null) {
            return itunes.owner.name;
        }
        if (media != null && !media.credits.isEmpty()) {
            for (MediaCredit credit : media.credits) {
                if ("author".equalsIgnoreCase(credit.role)) {
                    return credit.value;
                }
            }
            return media.credits.get(0).value;
        }
        return null;
    }

    @NonNull
    @Override
    public List<? extends Item> getItems() {
        return items;
    }

    private enum ST {
        title, link, description, language, copyright, managingEditor, webMaster, pubDate,
        lastBuildDate, generator, docs, ttl, rating
    }
}
