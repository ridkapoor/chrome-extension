package com.extensionhandler.sro;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * Created by ridkapoor on 6/5/17.
 */
public class GetHotelPricingSRO implements Serializable {

    private List<Integer> HotelIDList;
    private RoomSRO[] RoomList;
    private String Checkin;
    private String Checkout;
    private String PointOfSale;
    private String Currency;
    private String Language = "en-US";
    private String SiteType = "Default";
    private int MarketingChannelID = 0;

    public List<Integer> getHotelIDList() {
        return HotelIDList;
    }

    public void setHotelIDList(List<Integer> hotelIDList) {
        HotelIDList = hotelIDList;
    }

    public RoomSRO[] getRoomList() {
        return RoomList;
    }

    public void setRoomList(RoomSRO[] roomList) {
        RoomList = roomList;
    }

    public String getCheckin() {
        return Checkin;
    }

    public void setCheckin(String checkin) {
        Checkin = checkin;
    }

    public String getCheckout() {
        return Checkout;
    }

    public void setCheckout(String checkout) {
        Checkout = checkout;
    }

    public String getPointOfSale() {
        return PointOfSale;
    }

    public void setPointOfSale(String pointOfSale) {
        PointOfSale = pointOfSale;
    }

    public String getCurrency() {
        return Currency;
    }

    public void setCurrency(String currency) {
        Currency = currency;
    }

    public String getLanguage() {
        return Language;
    }

    public void setLanguage(String language) {
        Language = language;
    }

    public String getSiteType() {
        return SiteType;
    }

    public void setSiteType(String siteType) {
        SiteType = siteType;
    }

    public int getMarketingChannelID() {
        return MarketingChannelID;
    }

    public void setMarketingChannelID(int marketingChannelID) {
        MarketingChannelID = marketingChannelID;
    }

    @Override
    public String toString() {
        return "GetHotelPricingSRO{" +
                "HotelIDList=" + HotelIDList +
                ", RoomList=" + Arrays.toString(RoomList) +
                ", Checkin='" + Checkin + '\'' +
                ", Checkout='" + Checkout + '\'' +
                ", PointOfSale='" + PointOfSale + '\'' +
                ", Currency='" + Currency + '\'' +
                ", Language='" + Language + '\'' +
                ", SiteType='" + SiteType + '\'' +
                ", MarketingChannelID=" + MarketingChannelID +
                '}';
    }
}
