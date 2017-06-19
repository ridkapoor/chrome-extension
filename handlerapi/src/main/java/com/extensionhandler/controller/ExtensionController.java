package com.extensionhandler.controller;

import com.extensionhandler.request.GetMiniHotelExtractorRequest;
import com.extensionhandler.response.GetMinHotelResponse;
import com.extensionhandler.services.IMinExtractorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by ridkapoor on 6/6/17.
 */

@RestController
public class ExtensionController {

    @Autowired
    private IMinExtractorService iMinExtractorService;

    @CrossOrigin
    @RequestMapping(value = "/service/getMinHotelData", method = RequestMethod.POST)
    @ResponseBody
    public GetMinHotelResponse miniHotelExtractor(@RequestBody GetMiniHotelExtractorRequest request) {

        System.out.println(request.toString());

        final GetMinHotelResponse getMinHotelResponse = iMinExtractorService.miniHotelExtractor(request);
        if (getMinHotelResponse != null) {
            System.out.print(getMinHotelResponse.toString());
        }
        return getMinHotelResponse;

    }

}