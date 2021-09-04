package com.example.rssreader.data;

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
    private final ArrayList rssItems = new ArrayList();
    private RssItem rssItem;
    private String text;

    @NotNull
    public final List parse(@NotNull InputStream inputStream) {
        Intrinsics.checkNotNullParameter(inputStream, "inputStream");

        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            Intrinsics.checkNotNullExpressionValue(factory, "factory");
            factory.setNamespaceAware(true);
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(inputStream, null);
            Intrinsics.checkNotNullExpressionValue(parser, "parser");
            int eventType = parser.getEventType();

            for (boolean foundItem = false; eventType != 1; eventType = parser.next()) {
                String tagname = parser.getName();
                switch (eventType) {
                    case 2:
                        if (StringsKt.equals(tagname, "item", true)) {
                            foundItem = true;
                            this.rssItem = new RssItem();
                        }
                        break;
                    case 3:
                        RssItem item;
                        if (StringsKt.equals(tagname, "item", true)) {
                            item = this.rssItem;
                            if (item != null) {
                                this.rssItems.add(item);
                            }
                            foundItem = false;
                        } else if (foundItem && StringsKt.equals(tagname, "title", true)) {
                            item = this.rssItem;
                            Intrinsics.checkNotNull(item);
                            item.title = String.valueOf(this.text);
                        } else if (foundItem && StringsKt.equals(tagname, "link", true)) {
                            item = this.rssItem;
                            Intrinsics.checkNotNull(item);
                            item.link = String.valueOf(this.text);
                        } else if (foundItem && StringsKt.equals(tagname, "pubDate", true)) {
                            item = this.rssItem;
                            Intrinsics.checkNotNull(item);
                            item.pubDate = String.valueOf(this.text);
                        } else if (foundItem && StringsKt.equals(tagname, "category", true)) {
                            item = this.rssItem;
                            Intrinsics.checkNotNull(item);
                            item.category = String.valueOf(this.text);
                        } else if (foundItem && StringsKt.equals(tagname, "description", true)) {
                            item = this.rssItem;
                            Intrinsics.checkNotNull(item);
                            item.description = String.valueOf(this.text);
                        }
                        break;
                    case 4:
                        this.text = parser.getText();
                }
            }
        } catch (XmlPullParserException var12) {
            var12.printStackTrace();
        } catch (IOException var13) {
            var13.printStackTrace();
        }

        return this.rssItems;
    }
}



