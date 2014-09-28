package com.example.twx.myapplication;


import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by twx on 21/09/14.
 */
public class ParserVLilleXMLData {
    List<Station> stations;

    public ParserVLilleXMLData() {
    }

    public void ParseAllStations(InputStream data) {
        XmlPullParser parser = null;
        this.stations = new LinkedList<Station>();
        try {
            parser = XmlPullParserFactory.newInstance().newPullParser();
            parser.setInput(data, "UTF-8");
            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_TAG :
                        if (parser.getName().equals("marker")) {
                            Station station = new Station();
                            station.setId(parser.getAttributeValue(null, "id"));
                            station.setLat(parser.getAttributeValue(null, "lat"));
                            station.setLng(parser.getAttributeValue(null, "lng"));
                            station.setName(parser.getAttributeValue(null, "name"));
                            this.stations.add(station);
                        }
                }
                eventType = parser.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public List<Station> getStations() {
        return this.stations;
    }

    public Station updateStation(InputStream data, Station station) {
        XmlPullParser parser = null;
        try {
            parser = XmlPullParserFactory.newInstance().newPullParser();
            parser.setInput(data, "UTF-8");
            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_TAG :
                        if (parser.getName().equals("adress")) {
                            eventType = parser.next();
                            station.setAddress(parser.getText());
                        } else if (parser.getName().equals("bikes")) {
                            eventType = parser.next();
                            station.setBike(parser.getText());
                        } else if (parser.getName().equals("attachs")) {
                            eventType = parser.next();
                            station.setAttach(parser.getText());
                        }
                }
                eventType = parser.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return station;
    }
}
