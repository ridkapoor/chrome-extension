package com.extensionhandler.services;

import com.extensionhandler.request.GetMiniHotelExtractorRequest;
import com.extensionhandler.response.GetMinHotelResponse;

/**
 * Created by ridkapoor on 6/15/17.
 */
public interface IMinExtractorService {

    GetMinHotelResponse miniHotelExtractor(GetMiniHotelExtractorRequest request);

}
