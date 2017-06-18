package com.extensionhandler.services;

import com.extensionhandler.response.GetHotelInfoResponse;
import com.extensionhandler.util.GsonUtil;
import com.extensionhandler.util.HttpUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ridkapoor on 6/16/17.
 */
@Service("urlService")
public class URLCreator {

    @Autowired
    private GsonUtil gsonUtil;

    private static final String API_KEY = "daa942fc-d7ce-4118-9dc7-bc05b0d8eec7";
    private final String URL = "https://terminal2.expedia.com/x/mhotels/info";

    public String getSignInDeepLinkURL(String hotelId) {
        String deepLink = null;

        try {

            Map<String, String> params = new HashMap<String, String>();
            params.put("hotelId", hotelId);
            Map<String, String> headers = new HashMap<String, String>();
            headers.put("key", API_KEY);
            headers.put("'Accept", "application/json");
            final String response = HttpUtility.doGet(URL, params, headers);
            final GetHotelInfoResponse getHotelInfoResponse = gsonUtil.fromJson(response, GetHotelInfoResponse.class);
            deepLink = getHotelInfoResponse.getDeepLinkUrl();
        } catch (Exception e) {

            System.out.print(e);

        }
        return deepLink;
    }

}
