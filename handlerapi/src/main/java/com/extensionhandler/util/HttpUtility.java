package com.extensionhandler.util;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
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

            for (Map.Entry<String, String> header : headers.entrySet()) {
                con.setRequestProperty(header.getKey(), header.getValue());
            }


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

    public static String doPost(String url, String json) {

        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(url);
        StringEntity postingString = null;
        String result = null;
        try {
            postingString = new StringEntity(json);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        post.setEntity(postingString);
        post.setHeader("Content-type", "application/json");
        try {
            HttpResponse response = httpClient.execute(post);
            if (response != null) {
                InputStream in = response.getEntity().getContent(); //Get the data in the entity

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
                String inputLine;
                StringBuffer buffer = new StringBuffer();

                while ((inputLine = bufferedReader.readLine()) != null) {
                    buffer.append(inputLine);
                }
                bufferedReader.close();


                //print result
                result = buffer.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;

    }

    private static String getUrl(String url, Map<String, String> params) {
        URIBuilder urlBuilder = new URIBuilder();
        for (Map.Entry<String, String> e : params.entrySet()) {
            urlBuilder.setParameter(e.getKey(), e.getValue());
        }
        return url + urlBuilder.toString();

    }
}
