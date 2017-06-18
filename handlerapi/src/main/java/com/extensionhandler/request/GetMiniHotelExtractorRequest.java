package com.extensionhandler.request;

import com.extensionhandler.sro.HotelInfochildrenSRO;
import com.extensionhandler.sro.RoomInfoSRO;

import java.util.Set;

/**
 * Created by ridkapoor on 6/6/17.
 */
public class GetMiniHotelExtractorRequest {

    private String checkIn;
    private String checkOut;
    private String domain;
    private Set<HotelInfochildrenSRO> hotelInfo;
    private RoomInfoSRO roomInfo;
    private String currency;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(String checkIn) {
        this.checkIn = checkIn;
    }

    public String getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(String checkOut) {
        this.checkOut = checkOut;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public Set<HotelInfochildrenSRO> getHotelInfo() {
        return hotelInfo;
    }

    public void setHotelInfo(Set<HotelInfochildrenSRO> hotelInfo) {
        this.hotelInfo = hotelInfo;
    }

    public RoomInfoSRO getRoomInfo() {
        return roomInfo;
    }

    public void setRoomInfo(RoomInfoSRO roomInfo) {
        this.roomInfo = roomInfo;
    }
}
