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
    private int PointOfSale;
    private String Currency;
    private String Language = "en-US";
    private int Partner = 0;
    private String ShoppingPath = "Standalone";
    private String SiteType = "Default";
    private int MarketingChannelID = 15;
    private String[] RatePlanTypes;
    private String[] BusinessModels;
    private PriceFormat PriceFormat;
    private MessageHeader MessageHeader;

    public GetHotelPricingSRO() {
        RatePlanTypes = new String[1];
        RatePlanTypes[0] = "Standalone";
        BusinessModels = new String[2];
        BusinessModels[0] = "ExpediaCollect";
        BusinessModels[1] = "HotelCollect";
        PriceFormat = new PriceFormat();
        MessageHeader = new MessageHeader();
    }

    public int getPointOfSale() {
        return PointOfSale;
    }

    public void setPointOfSale(int pointOfSale) {
        PointOfSale = pointOfSale;
    }


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

    class PriceFormat {
        private String[] DisplayPrices;

        public PriceFormat() {
            DisplayPrices = new String[2];
            DisplayPrices[0] = "SummaryDisplayPrices";
            DisplayPrices[1] = "DetailedDisplayPrices";
        }
    }

    class MessageHeader {

        private String MessageName = "LodgingPricingAvailabilityRequest";
        private String MessageVersion = "10.0";
        private String MessageDateTime = "2017-06-18T06:55:39.164Z";
        private String UserUUID = "7b1e8419-139e-471b-a739-f3f699e76fb1";
        private String SessionUUID = "f0d6761a-698b-45ef-b05a-5532e3f1e072";
        private String ActivityUUID = "843bbe93-5cc3-4e2a-a10b-f775d4d5687c";
        private String MessageUUID = "cd0dfebc-cee1-4cd6-949a-82d54464397f";
        private String ClientID = "ChappieTool";
    }
}
