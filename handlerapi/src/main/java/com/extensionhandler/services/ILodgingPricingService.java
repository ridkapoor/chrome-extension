package com.extensionhandler.services;

import com.extensionhandler.response.GetHotelPricingResponse;
import com.extensionhandler.sro.GetHotelPricingSRO;

/**
 * Created by ridkapoor on 6/5/17.
 */
public interface ILodgingPricingService {

    GetHotelPricingResponse getPriceDetails(GetHotelPricingSRO hotelPricingSRO);
}
