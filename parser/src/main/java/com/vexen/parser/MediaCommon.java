package com.vexen.parser;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class MediaCommon {


    public final Boolean adult;


    public final MediaRating rating;


    public final MediaTitle title;


    public final MediaTitle description;
    @NonNull
    public final List<String> keywords;
    @NonNull
    public final List<MediaThumbnail> thumbnails;
    @NonNull
    public final List<MediaCategory> categories;


    public final MediaHash hash;


    public final MediaPlayer player;
    @NonNull
    public final List<MediaCredit> credits;


    public final MediaCopyright copyright;
    @NonNull
    public final List<MediaText> texts;
    @NonNull
    public final List<MediaRestriction> restrictions;


    public final MediaCommunity community;
    @NonNull
    public final List<String> comments;


    public final MediaEmbed embed;
    @NonNull
    public final List<String> responses;
    @NonNull
    public final List<URL> backLinks;


    public final MediaStatus status;
    @NonNull
    public final List<MediaPrice> prices;


    public final MediaLicense license;
    @NonNull
    public final List<MediaSubTitle> subTitles;


    public final MediaPeerLink peerLink;


    public final MediaLocation location;


    public final MediaRights rights;
    @NonNull
    public final List<MediaScene> scenes;


    public MediaCommon(@NonNull MediaCommon source) {
        this.adult = source.adult;
        this.rating = source.rating;
        this.title = source.title;
        this.description = source.description;
        this.keywords = source.keywords;
        this.thumbnails = source.thumbnails;
        this.categories = source.categories;
        this.hash = source.hash;
        this.player = source.player;
        this.credits = source.credits;
        this.copyright = source.copyright;
        this.texts = source.texts;
        this.restrictions = source.restrictions;
        this.community = source.community;
        this.comments = source.comments;
        this.embed = source.embed;
        this.responses = source.responses;
        this.backLinks = source.backLinks;
        this.status = source.status;
        this.prices = source.prices;
        this.license = source.license;
        this.subTitles = source.subTitles;
        this.peerLink = source.peerLink;
        this.location = source.location;
        this.rights = source.rights;
        this.scenes = source.scenes;
    }

    @NonNull
    static MediaCommon read(@NonNull XmlPullParser parser) throws XmlPullParserException, IOException {
        final MediaCommonBuilder builder = new MediaCommonBuilder();
        while (parser.nextTag() == XmlPullParser.START_TAG) {
            builder.parseTag(parser);
        }
        return builder.build();
    }

    static class MediaCommonBuilder {
        private final List<MediaThumbnail> thumbnails = new LinkedList<>();
        private final List<MediaCategory> categories = new LinkedList<>();
        private final List<MediaCredit> credits = new LinkedList<>();
        private final List<MediaText> texts = new LinkedList<>();
        private final List<MediaRestriction> restrictions = new LinkedList<>();
        private final List<String> comments = new LinkedList<>();
        private final List<String> responses = new LinkedList<>();
        private final List<URL> backLinks = new LinkedList<>();
        private final List<MediaPrice> prices = new LinkedList<>();
        private final List<MediaSubTitle> subTitles = new LinkedList<>();
        private final List<MediaScene> scenes = new LinkedList<>();
        private Boolean adult;
        private MediaRating rating;
        private MediaTitle title;
        private MediaTitle description;
        private List<String> keywords;
        private MediaHash hash;
        private MediaPlayer player;
        private MediaCopyright copyright;
        private MediaCommunity community;
        private MediaEmbed embed;
        private MediaStatus status;
        private MediaLicense license;
        private MediaPeerLink peerLink;
        private MediaLocation location;
        private MediaRights rights;

        /**
         * @return true if builder has consumed the tag
         */
        boolean parseTag(@NonNull XmlPullParser parser) throws XmlPullParserException, IOException {
            switch (parser.getName()) {
                case "adult":
                    adult = Boolean.parseBoolean(parser.nextText());
                    break;
                case MediaRating.XML_TAG:
                    rating = MediaRating.read(parser);
                    break;
                case "title":
                    title = MediaTitle.read(parser);
                    break;
                case "description":
                    description = MediaTitle.read(parser);
                    break;
                case "keywords":
                    keywords = Arrays.asList(parser.nextText().split(","));
                    break;
                case MediaThumbnail.XML_TAG:
                    thumbnails.add(MediaThumbnail.read(parser));
                    break;
                case MediaCategory.XML_TAG:
                    categories.add(MediaCategory.read(parser));
                    break;
                case MediaHash.XML_TAG:
                    hash = MediaHash.read(parser);
                    break;
                case MediaPlayer.XML_TAG:
                    player = MediaPlayer.read(parser);
                    break;
                case MediaCredit.XML_TAG:
                    credits.add(MediaCredit.read(parser));
                    break;
                case MediaCopyright.XML_TAG:
                    copyright = MediaCopyright.read(parser);
                    break;
                case MediaText.XML_TAG:
                    texts.add(MediaText.read(parser));
                    break;
                case MediaRestriction.XML_TAG:
                    restrictions.add(MediaRestriction.read(parser));
                    break;
                case MediaCommunity.XML_TAG:
                    community = MediaCommunity.read(parser);
                    break;
                case "comments":
                    while (parser.nextTag() == XmlPullParser.START_TAG) {
                        parser.require(XmlPullParser.START_TAG, null, "comment");
                        comments.add(parser.nextText());
                    }
                    break;
                case MediaEmbed.XML_TAG:
                    embed = MediaEmbed.read(parser);
                    break;
                case "responses":
                    while (parser.nextTag() == XmlPullParser.START_TAG) {
                        parser.require(XmlPullParser.START_TAG, null, "response");
                        responses.add(parser.nextText());
                    }
                    break;
                case "backLinks":
                    while (parser.nextTag() == XmlPullParser.START_TAG) {
                        parser.require(XmlPullParser.START_TAG, null, "backLink");
                        backLinks.add(Utils.nonNullUrl(parser.nextText()));
                    }
                    break;
                case MediaStatus.XML_TAG:
                    status = MediaStatus.read(parser);
                    break;
                case MediaPrice.XML_TAG:
                    prices.add(MediaPrice.read(parser));
                    break;
                case MediaLicense.XML_TAG:
                    license = MediaLicense.read(parser);
                    break;
                case MediaSubTitle.XML_TAG:
                    subTitles.add(MediaSubTitle.read(parser));
                    break;
                case MediaPeerLink.XML_TAG:
                    peerLink = MediaPeerLink.read(parser);
                    break;
                case MediaLocation.XML_TAG:
                    location = MediaLocation.read(parser);
                    break;
                case MediaRights.XML_TAG:
                    rights = MediaRights.read(parser);
                    break;
                case "scenes":
                    while (parser.nextTag() == XmlPullParser.START_TAG) {
                        parser.require(XmlPullParser.START_TAG, null, "scene");
                        scenes.add(MediaScene.read(parser));
                    }
                    break;
                default:
                    return false;
            }
            return true;
        }

        MediaCommon build() {
            if (keywords == null) {
                keywords = new LinkedList<>();
            }
            return new MediaCommon(
                    adult, rating, title, description, keywords, thumbnails, categories, hash, player,
                    credits, copyright, texts, restrictions, community, comments, embed, responses, backLinks,
                    status, prices, license, subTitles, peerLink, location, rights, scenes);
        }
    }
}
