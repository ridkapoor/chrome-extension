package com.extensionhandler.sro;

import java.io.Serializable;

/**
 * Created by ridkapoor on 6/6/17.
 */
public class HotelInfoSRO implements Serializable {

    private String hotelId;
    private double price;

    public String getHotelId() {
        return hotelId;
    }

    public void setHotelId(String hotelId) {
        this.hotelId = hotelId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
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
