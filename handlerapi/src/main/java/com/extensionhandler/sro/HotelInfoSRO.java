package com.extensionhandler.sro;

import java.io.Serializable;

/**
 * Created by ridkapoor on 6/6/17.
 */
public class HotelInfoSRO implements Serializable {

    private String hotelId;
    private String oldPrice;
    private String price;
    private String savings;
    private String url;
    private String roomType;

    public HotelInfoSRO(String hotelId, String oldPrice, String price, String savings, String url, String roomType) {
        this.hotelId = hotelId;
        this.oldPrice = oldPrice;
        this.price = price;
        this.savings = savings;
        this.url = url;
        this.roomType = roomType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHotelId() {
        return hotelId;
    }

    public void setHotelId(String hotelId) {
        this.hotelId = hotelId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSavings() {
        return savings;
    }

    public void setSavings(String savings) {
        this.savings = savings;
    }

    public String getOldPrice() {
        return oldPrice;
    }

    public void setOldPrice(String oldPrice) {
        this.oldPrice = oldPrice;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HotelInfoSRO that = (HotelInfoSRO) o;

        return hotelId.equals(that.hotelId);
    }

    @Override
    public int hashCode() {
        return hotelId.hashCode();
    }
}
