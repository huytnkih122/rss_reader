package com.example.rssreader.data;

import org.jetbrains.annotations.NotNull;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;

import kotlin.jvm.internal.Intrinsics;

public class RssParser {
    private final RssInfo rssInfo = new RssInfo();
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
            boolean isImage = false;
            for (boolean foundItem = false; eventType != XmlPullParser.END_DOCUMENT; eventType = parser.next()) {
                String tagname = parser.getName();
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if (tagname.equals("item")) {
                            foundItem = true;
                            this.rssItem = new RssItem();
                        }
                        if (tagname.equals("image")) {
                            isImage = true;
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        RssItem item;
                        if (this.rssItem != null) {
                            String imgUrl = getImage(this.text);
                            if (imgUrl != null)
                                this.rssItem.setImage(getImage(this.text));
                        }
                        if (tagname.equals("item")) {
                            item = this.rssItem;
                            if (item != null) {
                                this.rssInfo.addItem(item);
                            }
                            foundItem = false;
                        } else if (!isImage && !foundItem && tagname.equals("title")) {
                            rssInfo.setTitle(String.valueOf(this.text));
                        } else if (isImage && tagname.equals("url")) {
                            rssInfo.setLogo(String.valueOf(this.text));
                        } else if (isImage && tagname.equals("image")) {
                            isImage = false;
                        } else if (foundItem && tagname.equals("title")) {
                            this.rssItem.setTitle(String.valueOf(this.text));
                        } else if (foundItem && tagname.equals("link") && rssItem.getLink() == null) {
                            this.rssItem.setLink(String.valueOf(this.text));
                        } else if (foundItem && tagname.equals("pubDate")) {
                            this.rssItem.setPubDate(String.valueOf(this.text));
                        } else if (foundItem && tagname.equals("creator")) {
                            this.rssItem.setAuthor(String.valueOf(this.text));
                        } else if (foundItem && tagname.equals("description")) {
                            this.rssItem.setDescription(String.valueOf(this.text));
                        } else if (foundItem && tagname.equals("encoded")) {

                            this.rssItem.setImage(getImage(this.text));

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

    private String getImage(String text) {
        int start = text.indexOf("img src");   //detect image url
        if (start != -1) {
            String temp = this.text.substring(start);
            String stringStart = temp.substring(temp.indexOf('"') + 1);
            int end = stringStart.indexOf('"');
            String imageUrl = stringStart.substring(0, end);
            return imageUrl;
        }
        return null;
    }
}





