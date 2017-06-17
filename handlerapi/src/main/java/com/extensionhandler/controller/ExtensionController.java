package com.extensionhandler.controller;

import com.extensionhandler.request.GetMiniHotelExtractorRequest;
import com.extensionhandler.response.GetMinHotelResponse;
import com.extensionhandler.services.IMinExtractorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by ridkapoor on 6/6/17.
 */

@RestController
public class ExtensionController {

    @Autowired
    private IMinExtractorService iMinExtractorService;

    @RequestMapping(value = "/service/getMinHotelData", method = RequestMethod.POST)
    @ResponseBody
    public GetMinHotelResponse miniHotelExtractor(@RequestBody GetMiniHotelExtractorRequest request) {

        return new GetMinHotelResponse();//iMinExtractorService.miniHotelExtractor(request);

    }


}



