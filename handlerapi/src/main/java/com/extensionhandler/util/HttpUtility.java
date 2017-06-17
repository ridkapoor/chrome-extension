package com.extensionhandler.util;

import org.apache.http.client.utils.URIBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;


/**
 * Created by ridkapoor on 6/16/17.
 */
public class HttpUtility {

    public static String doGet(String url, Map<String, String> params, Map<String, String> headers) {

        try {


            URL obj = new URL(getUrl(url, params));
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            // optional default is GET
            con.setRequestMethod("GET");

            //add request header

            int responseCode = con.getResponseCode();
            System.out.println("\nSending 'GET' request to URL : " + url);
            System.out.println("Response Code : " + responseCode);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            //print result
            return response.toString();

        } catch (MalformedURLException e) {

        } catch (IOException e) {

        }

        return null;
    }

    private static String getUrl(String url, Map<String, String> params) {
        URIBuilder urlBuilder = new URIBuilder();
        for (Map.Entry<String, String> e : params.entrySet()) {
            urlBuilder.setParameter(e.getKey(), e.getValue());
        }
        return url + urlBuilder.toString();

    }
}
