package com.extensionhandler.services;

import com.extensionhandler.config.AppConfig;
import com.extensionhandler.request.GetMiniHotelExtractorRequest;
import com.extensionhandler.response.GetMinHotelResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by ridkapoor on 6/15/17.
 */

@Service
public class MinExtractorServiceImpl implements IMinExtractorService {

    @Autowired
    private AppConfig appConfig;


    @Override
    public GetMinHotelResponse miniHotelExtractor(GetMiniHotelExtractorRequest request) {
        return null;
    }
}
