package com.example.twx.myapplication;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Created by twx on 21/09/14.
 */
public class VLilleWebService {
    private String urlStations = "http://vlille.fr/stations/xml-stations.aspx";
    private String urlStation = "http://vlille.fr/stations/xml-station.aspx?borne=";
    private HttpURLConnection connexion;
    private InputStream stream;

    public VLilleWebService() {
    }

    public List<Station> getStations() {
        ParserVLilleXMLData parser = new ParserVLilleXMLData();
        try {
            URL myURL = new URL(this.urlStations);
            this.connexion = (HttpURLConnection) myURL.openConnection();
            this.connexion.connect();
            if (this.connexion.getResponseCode() == HttpURLConnection.HTTP_OK) {
                this.stream = this.connexion.getInputStream();
                parser.ParseAllStations(this.stream);
            }
        } catch (MalformedURLException e) {
            Log.e("URL", "failed");
        } catch (IOException e) {
            Log.e("Connexion", "failed");
        }
        this.connexion.disconnect();
        return parser.getStations();
    }

    public Station moreInformationsStation(Station station) {
        ParserVLilleXMLData parser = new ParserVLilleXMLData();
        String id = station.getId();
        String urlBorne = this.urlStation + id;
        this.connexionBorne(urlBorne);
        parser.updateStation(this.stream, station);
        return station;
    }

    public void connexionBorne(String url) {
        URL myURL = null;
        try {
            myURL = new URL(url);
            this.connexion = (HttpURLConnection) myURL.openConnection();
            if (this.connexion.getResponseCode() == HttpURLConnection.HTTP_OK) {
                this.stream = this.connexion.getInputStream();
            }
        } catch (MalformedURLException e) {
            Log.e("URL", "failed");
        } catch (IOException e) {
            Log.e("Connexion", "failed");
        }
    }
}