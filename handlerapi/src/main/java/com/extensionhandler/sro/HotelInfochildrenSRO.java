package com.extensionhandler.sro;

import java.io.Serializable;

/**
 * Created by ridkapoor on 6/15/17.
 */
public class HotelInfochildrenSRO implements Serializable {

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

        HotelInfochildrenSRO that = (HotelInfochildrenSRO) o;

        return hotelId.equals(that.hotelId);
    }

    @Override
    public int hashCode() {
        return hotelId.hashCode();
    }

    @Override
    public String toString() {
        return "HotelInfochildrenSRO{" +
                "hotelId='" + hotelId + '\'' +
                ", price=" + price +
                '}';
    }
}
