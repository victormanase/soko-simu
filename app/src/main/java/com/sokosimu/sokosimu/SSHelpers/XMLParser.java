package com.sokosimu.sokosimu.SSHelpers;

import com.sokosimu.sokosimu.SSValues.XMLResponseValues;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;

public class XMLParser {
        private String text;
        private StringBuilder builder = new StringBuilder();
        private XMLResponseValues xmlResponseValues;

        public XMLResponseValues parse(String string) {

            xmlResponseValues = new XMLResponseValues();
            try {

                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(true);
                XmlPullParser parser = factory.newPullParser();

                parser.setInput(new StringReader(string));

                int eventType = parser.getEventType();

                while (eventType != XmlPullParser.END_DOCUMENT) {
                    String tagname = parser.getName();
                    switch (eventType) {

                        case XmlPullParser.TEXT:
                            text = parser.getText();
                            break;

                        case XmlPullParser.END_TAG:
                            if (tagname.equalsIgnoreCase("Result")) {
                               xmlResponseValues.Result = text;
                            } else if (tagname.equalsIgnoreCase("ResultExplanation")) {
                               xmlResponseValues.ResultExplanation = text;
                            } else if (xmlResponseValues.Result.equals("000")){
                                if(tagname.equalsIgnoreCase("TransRef")){
                                    xmlResponseValues.TransRef = text;
                                } else if(tagname.equalsIgnoreCase("TransToken")){
                                    xmlResponseValues.TransToken = text;
                                }
                            }
                            break;

                        default:
                            break;
                    }
                    eventType = parser.next();
                }

            } catch (IOException e) {
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            }

            return xmlResponseValues;
        }

}
