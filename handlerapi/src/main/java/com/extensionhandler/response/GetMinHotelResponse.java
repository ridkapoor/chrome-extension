package com.extensionhandler.response;

import com.extensionhandler.sro.HotelInfoSRO;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by ridkapoor on 6/15/17.
 */
public class GetMinHotelResponse implements Serializable {

    private Map<String, HotelInfoSRO> hotels;

    public Map<String, HotelInfoSRO> getHotels() {
        return hotels;
    }

    public void setHotels(Map<String, HotelInfoSRO> hotels) {
        this.hotels = hotels;
    }

    @Override
    public String toString() {
        return "GetMinHotelResponse{" +
                "hotels=" + hotels +
                '}';
    }
}
