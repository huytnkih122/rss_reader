package com.example.rssreader.data;

import android.util.Log;

import org.jetbrains.annotations.NotNull;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

public class RssParser {
    private RssInfo rssInfo = new RssInfo();
    private RssItem rssItem;
    private String text;

    @NotNull
    public final RssInfo parse(@NotNull InputStream inputStream) {
        Intrinsics.checkNotNullParameter(inputStream, "inputStream");

        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(inputStream, null);
            int eventType = parser.getEventType();

            for (boolean foundItem = false; eventType != XmlPullParser.END_DOCUMENT; eventType = parser.next()) {
                String tagname = parser.getName();
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if (tagname.equals( "item")) {
                            foundItem = true;
                            this.rssItem = new RssItem();
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        RssItem item;
                        if (tagname.equals("item")) {
                            item = this.rssItem;
                            if (item != null) {
                                this.rssInfo.addItem(item);
                            }
                            foundItem = false;
                        }else if (!foundItem && tagname.equals( "title")) {
                            rssInfo.setTitle(String.valueOf(this.text));
                        } else if (foundItem && tagname.equals( "title")) {
                            this.rssItem.setTitle(String.valueOf(this.text));
                        } else if (foundItem && tagname.equals( "link")) {
                            this.rssItem.setLink(String.valueOf(this.text));;
                        } else if (foundItem && tagname.equals( "pubDate")) {
                            this.rssItem.setPubDate(String.valueOf(this.text));
                        } else if (foundItem && tagname.equals( "creator")) {
                            this.rssItem.setAuthor(String.valueOf(this.text));
                        } else if (foundItem && tagname.equals( "description")) {
                            this.rssItem.setDescription(String.valueOf(this.text));
                        } else if (foundItem && tagname.equals( "encoded")) {
                            int start = this.text.indexOf("img src");   //detect image url
                            if (start != -1) {
                                String temp = this.text.substring(start);
                                String stringStart = temp.substring(temp.indexOf('"')+1);
                                int end = stringStart.indexOf('"');
                                String image = stringStart.substring(0,end);
                                this.rssItem.setImage(image);
                            }
                        }
                        break;
                    case XmlPullParser.TEXT: {
                        this.text = parser.getText();

                    }

                }
            }
        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }

        return this.rssInfo;
    }
}



