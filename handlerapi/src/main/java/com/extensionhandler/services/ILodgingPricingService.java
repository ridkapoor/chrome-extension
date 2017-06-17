package com.extensionhandler.services;

import com.extensionhandler.sro.GetHotelPricingSRO;

/**
 * Created by ridkapoor on 6/5/17.
 */
public interface ILodgingPricingService {

    void getPriceDetails(GetHotelPricingSRO hotelPricingSRO);
}
