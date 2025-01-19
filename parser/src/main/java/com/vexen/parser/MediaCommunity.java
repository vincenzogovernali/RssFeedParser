package com.vexen.parser;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import lombok.NonNull;

public final class MediaCommunity {
    static final String XML_TAG = "community";
    private static final String TAG = "Earl.MediaCommunity";


    public final StarRating starRating;


    public final Statistics statistics;
    @NonNull
    public final List<String> tags;

    public MediaCommunity(StarRating starRating, Statistics statistics,
                          @NonNull List<String> tags) {
        this.starRating = starRating;
        this.statistics = statistics;
        this.tags = Collections.unmodifiableList(tags);
    }

    @NonNull
    static MediaCommunity read(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, null, XML_TAG);
        StarRating starRating = null;
        Statistics statistics = null;
        List<String> tags = null;
        while (parser.nextTag() == XmlPullParser.START_TAG) {
            switch (parser.getName()) {
                case StarRating.XML_TAG:
                    final String count = parser.getAttributeValue(XmlPullParser.NO_NAMESPACE, "count");
                    final String min = parser.getAttributeValue(XmlPullParser.NO_NAMESPACE, "min");
                    final String max = parser.getAttributeValue(XmlPullParser.NO_NAMESPACE, "max");
                    starRating = new StarRating(
                            parser.getAttributeValue(XmlPullParser.NO_NAMESPACE, "average"),
                            count == null ? null : Utils.tryParseInt(count),
                            min == null ? null : Utils.tryParseInt(min),
                            max == null ? null : Utils.tryParseInt(max));
                    parser.nextTag();
                    break;
                case Statistics.XML_TAG:
                    final String views = parser.getAttributeValue(XmlPullParser.NO_NAMESPACE, "views");
                    final String favorites = parser.getAttributeValue(XmlPullParser.NO_NAMESPACE, "favorites");
                    statistics = new Statistics(views == null ? null : Utils.tryParseInt(views),
                            favorites == null ? null : Utils.tryParseInt(favorites));
                    parser.nextTag();
                    break;
                case "tags":
                    tags = Arrays.asList(parser.nextText().split(","));
                    break;
                default:
                    Log.w(TAG, "Unexpected tag inside media:community: " + parser.getName());
                    Utils.skipTag(parser);
            }
            Utils.finishTag(parser);
        }

        return new MediaCommunity(starRating, statistics,
                tags == null ? new LinkedList<String>() : tags);
    }

    public static class StarRating {
        static final String XML_TAG = "starRating";


        public final String average;


        public final Integer count;


        public final Integer min;


        public final Integer max;

        public StarRating(String average, Integer count, Integer min,

                          Integer max) {
            this.average = average;
            this.count = count;
            this.min = min;
            this.max = max;
        }
    }

    public static class Statistics {
        static final String XML_TAG = "statistics";


        public final Integer views;


        public final Integer favorites;

        public Statistics(Integer views, Integer favorites) {
            this.views = views;
            this.favorites = favorites;
        }
    }
}
