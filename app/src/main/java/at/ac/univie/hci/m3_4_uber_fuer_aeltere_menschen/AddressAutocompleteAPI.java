package at.ac.univie.hci.m3_4_uber_fuer_aeltere_menschen;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

public class AddressAutocompleteAPI extends AsyncTask<String, Void, Document> {
    @Override //wird im Hintergrund ausgefuehrt, damit der main thread nicht ueberlasstet wird
    protected Document doInBackground(String... urlstrings) {
        Document result = null;
        try {
            //API-Anfrage
            URL url = new URL(urlstrings[0]);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Accept", "application/xml");
            //connection.setRequestMethod("GET");
            System.out.println("Response Code: " + connection.getResponseCode() + " " + connection.getResponseMessage());
            //Antwort wird empfangen
            BufferedReader inputstream = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String input;
            StringBuffer response = new StringBuffer();
            //solange es input zum lesen gibt, wird input an response angehängt
            while ((input = inputstream.readLine()) != null) {
                response.append(input);
            }
            inputstream.close();
            connection.disconnect();
            //xml-document wird erzeugt
            DocumentBuilder newdocument = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            result = newdocument.parse(new InputSource(new StringReader(response.toString())));

        } catch (IOException | ParserConfigurationException | SAXException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static ArrayList<Address> getAddress(String query) throws ExecutionException, InterruptedException {
        String encodedQuery = "";
        try {
            encodedQuery = URLEncoder.encode(query, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String apiKey = "b9fbcd28f0a84434b0ddfeafa82b2fee";
        String type = "street";
        String filter = "circle:16.36541227109319,48.207759746386074,22000";
        String urlstring = "https://api.geoapify.com/v1/geocode/autocomplete?text=" + encodedQuery
                + "&lang=de&filter=" + filter + "&format=xml&apiKey=" + apiKey;
        ArrayList<Address> result = new ArrayList<Address>();
        AddressAutocompleteAPI apicall = new AddressAutocompleteAPI(); //API-Call
        Document xmldocument = apicall.execute(urlstring).get();
        NodeList locations = xmldocument.getElementsByTagName("results");
        for (int index = 0; index < locations.getLength(); ++index) {
            Node location = locations.item(index);
            Address address = new Address();
            NodeList address_line1 = ((Element) location).getElementsByTagName("address_line1");
            NodeList address_line2 = ((Element) location).getElementsByTagName("address_line2");
            NodeList street = ((Element) location).getElementsByTagName("street");
            if (street.getLength() > 0) {
                if (address_line1.getLength() > 0) {
                    String addressLine1 = address_line1.item(0).getTextContent();
                    String addressLine2 = "";
                    if (address_line2.getLength() > 0)
                        addressLine2 = address_line2.item(0).getTextContent();
                    try {
                        address.setAddressLine1(new String(addressLine1.getBytes(), "UTF-8"));
                        address.setAddressLine2(new String(addressLine2.getBytes(), "UTF-8"));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    result.add(address);
                }
            }
        }
        return result;
    }

    public static Address getCurrentAddress(String lat, String lon) throws ExecutionException, InterruptedException {
        String apiKey = "b9fbcd28f0a84434b0ddfeafa82b2fee";
        String urlstring = "https://api.geoapify.com/v1/geocode/reverse?lat=" + lat + "&lon=" + lon + "&format=xml&apiKey=" + apiKey;
        //String urlstring = "https://api.geoapify.com/v1/geocode/reverse?lat=52.51894887928074&lon=13.409808180753316&format=xml&apiKey=b9fbcd28f0a84434b0ddfeafa82b2fee";
        AddressAutocompleteAPI apicall = new AddressAutocompleteAPI(); //API-Call
        Document xmldocument = apicall.execute(urlstring).get();
        String addressLine1 = "test";
        String addressLine2 = "test";
        try {
            XPath xpath = XPathFactory.newInstance().newXPath();
            addressLine1 = xpath.evaluate   ("/results/address_line1", xmldocument);
            addressLine2 = xpath.evaluate   ("/results/address_line2", xmldocument);
            Log.d("addressLine1-prev",addressLine1);

        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
        Log.d("addressLine1",addressLine1);
        Address result = new Address(addressLine1, addressLine2);
        result.setLongitude(lon);
        result.setLatitude(lat);
        Log.d("Longitude",lon);
        Log.d("Latitude",lat);
        return result;
    }
}