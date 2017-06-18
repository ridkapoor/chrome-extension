package com.extensionhandler.services;

import com.extensionhandler.response.GetHotelPricingResponse;
import com.extensionhandler.sro.GetHotelPricingSRO;
import com.extensionhandler.util.GsonUtil;
import com.extensionhandler.util.HttpUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by ridkapoor on 6/5/17.
 */
@Service
public class LodgingPricingServiceImpl implements ILodgingPricingService {

    private final String URL = "http://lpas.integration.karmalab.net:45082/lpas";

    @Autowired
    private GsonUtil gsonUtil;

    @Override
    public GetHotelPricingResponse getPriceDetails(GetHotelPricingSRO hotelPricingSRO) {
        GetHotelPricingResponse getHotelPricingResponse = null;
        try {
            final String response = HttpUtility.doPost(URL, gsonUtil.toJSon(hotelPricingSRO));
            getHotelPricingResponse = gsonUtil.fromJson(response, GetHotelPricingResponse.class);
        } catch (Exception e) {
            System.out.print(e);
        }
        return getHotelPricingResponse;

    }
}
