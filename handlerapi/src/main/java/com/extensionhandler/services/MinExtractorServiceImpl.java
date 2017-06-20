package com.extensionhandler.services;

import com.extensionhandler.config.AppConfig;
import com.extensionhandler.config.PriceMapConfig;
import com.extensionhandler.request.GetMiniHotelExtractorRequest;
import com.extensionhandler.response.GetHotelPricingResponse;
import com.extensionhandler.response.GetMinHotelResponse;
import com.extensionhandler.sro.GetHotelPricingSRO;
import com.extensionhandler.sro.HotelInfoSRO;
import com.extensionhandler.sro.HotelInfochildrenSRO;
import com.extensionhandler.sro.RoomSRO;
import com.extensionhandler.util.DateUtil;
import com.extensionhandler.util.PriceUtil;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by ridkapoor on 6/15/17.
 */

@Service
public class MinExtractorServiceImpl implements IMinExtractorService {

    @Autowired
    private AppConfig appConfig;


    @Autowired
    private URLCreator urlCreator;


    @Autowired
    private ILodgingPricingService lodgingPricingService;

    @Autowired
    private DateUtil dateUtil;


    @Autowired
    private PriceUtil priceUtil;

    @Autowired
    private PriceMapConfig priceMapConfig;


    @Override
    public GetMinHotelResponse miniHotelExtractor(GetMiniHotelExtractorRequest request) {

        request = sanitizeData(request);
        Map<String, HotelInfochildrenSRO> partnerExpHotelIdMap = new HashMap<>();
        GetMinHotelResponse getMinHotelResponse = new GetMinHotelResponse();
        List<Integer> hotelIds = new ArrayList<>();
        Map<String, HotelInfoSRO> hotels = new HashMap<>();
        final Set<HotelInfochildrenSRO> hotelInfochildrenSROS = request.getHotelInfo();

        if (hotelInfochildrenSROS != null) {

            for (HotelInfochildrenSRO hotelInfo : hotelInfochildrenSROS) {
                final String expHotelId = appConfig.getProperty(hotelInfo.getHotelId().trim());
                partnerExpHotelIdMap.put(expHotelId, hotelInfo);
                if (expHotelId != null && !expHotelId.isEmpty()) {
                    hotelIds.add(Integer.parseInt(expHotelId));
                }
            }

            if (!hotelIds.isEmpty()) {
                // get pricing from lpas


                final GetHotelPricingResponse priceDetails = lodgingPricingService.getPriceDetails(getLpasRequest(hotelIds, request));

                if (priceDetails != null && priceDetails.getHotelList() != null) {
                    int decimalPlace = priceDetails.getMonetaryDecimalPlace().getDecimalPlace();

                    for (GetHotelPricingResponse.HotelList hotelData : priceDetails.getHotelList()) {
                        try {
                            final String expHId = String.valueOf(hotelData.getHotelID());
                            final GetHotelPricingResponse.CurrencyExchangeInfo currencyExchangeInfo = hotelData.getCurrencyExchangeInfo();
                            final HotelInfochildrenSRO hotelInfochildrenSRO = partnerExpHotelIdMap.get(expHId);
                            double modPrice = 0;
                            GetHotelPricingResponse.ProductList lowestProductListing = null;


                            for (GetHotelPricingResponse.ProductList productList : hotelData.getProductList()) {

                                if (lowestProductListing != null) {

                                    final GetHotelPricingResponse.TotalPrice lowestPerDayAmount =
                                            lowestProductListing.getDisplayAmountsList().get(0).getPerDayAmounts().get(0)
                                                    .getDisplayCategories().getDisplayBase().getTotalPrice();

                                    final GetHotelPricingResponse.TotalPrice perDayAmounts =
                                            productList.getDisplayAmountsList().get(0).getPerDayAmounts().get(0).getDisplayCategories().getDisplayBase().getTotalPrice();


                                    if (getPriceValueBaseCurrency(lowestPerDayAmount.getValue(), request.getCurrency(), lowestPerDayAmount.getCurrency(), currencyExchangeInfo) >
                                            getPriceValueBaseCurrency(lowestPerDayAmount.getValue(), request.getCurrency(), lowestPerDayAmount.getCurrency(), currencyExchangeInfo)) {

                                        lowestProductListing = productList;
                                    }

                                } else {
                                    lowestProductListing = productList;
                                }

                            }

                            final GetHotelPricingResponse.PerDayAmounts perDayAmounts =
                                    lowestProductListing.getDisplayAmountsList().get(0).getPerDayAmounts().get(0);


                            modPrice = (perDayAmounts.getDisplayCategories().getDisplayBase().getTotalPrice().getValue())
                                    / (Math.pow(10, decimalPlace));

                            modPrice = getPriceValueBaseCurrency(modPrice, request.getCurrency(),
                                    perDayAmounts.getDisplayCategories().getDisplayBase().getTotalPrice().getCurrency(),
                                    currencyExchangeInfo);

                            final Double price = priceMapConfig.getPrice(expHId);
                            if (price != null) {
                                modPrice = price.doubleValue();
                            }

                            final double oldPrice = hotelInfochildrenSRO.getPrice();
                            if ((oldPrice > 0 && oldPrice > modPrice)
                                    || (oldPrice == -1)) {

                                String oldPriceFormatted = oldPrice == -1 ? "" : priceUtil.formatPrice(oldPrice);
                                String savings = oldPrice == -1 ? "" : priceUtil.formatPrice(oldPrice - modPrice);

                                hotels.put(partnerExpHotelIdMap.get(expHId).getHotelId(), new HotelInfoSRO(expHId, oldPriceFormatted,
                                        priceUtil.formatPrice(modPrice), savings, getDeepLinkUrl(
                                        expHId, request.getCheckIn(), request.getCheckOut(), request.getRoomInfo().getAdults(),
                                        request.getRoomInfo().getChildren()),
                                        lowestProductListing.getDescription()));
                            }
                        } catch (Exception e) {
                            System.err.print(e);
                        }
                    }
                }

                getMinHotelResponse.setHotels(hotels);
            }
        }
        return getMinHotelResponse;
    }


    private int[] getRoomDistribution(int input, int noOfRooms) {

        final int quotient = input / noOfRooms;
        final int remainder = input % noOfRooms;

        int[] results = new int[noOfRooms];
        for (int i = 0; i < noOfRooms; i++) {
            results[i] = i < remainder ? quotient + 1 : quotient;
        }

        return results;
    }

    private GetHotelPricingSRO getLpasRequest(List<Integer> hotelIds, GetMiniHotelExtractorRequest request) {

        GetHotelPricingSRO hotelPricingSRO = new GetHotelPricingSRO();
        hotelPricingSRO.setHotelIDList(hotelIds);
        int noOfRooms = request.getRoomInfo().getNoOfRooms();
        if (noOfRooms > 0) {
            RoomSRO[] roomList = new RoomSRO[noOfRooms];
            int[] roomDistribution = getRoomDistribution(request.getRoomInfo().getAdults(), noOfRooms);
            for (int i = 0; i < noOfRooms; i++) {
                roomList[0] = new RoomSRO(roomDistribution[i]);
            }
            hotelPricingSRO.setRoomList(roomList);
            hotelPricingSRO.setCheckin(request.getCheckIn());
            hotelPricingSRO.setCheckout(request.getCheckOut());
            hotelPricingSRO.setCurrency(request.getCurrency());
            hotelPricingSRO.setPointOfSale(1);
        }

        return hotelPricingSRO;

    }


    private GetMiniHotelExtractorRequest sanitizeData(GetMiniHotelExtractorRequest request) {
        request.setCheckIn(dateUtil.changeFormat(request.getCheckIn(), request.getDateFormat()));
        request.setCheckOut(dateUtil.changeFormat(request.getCheckOut(), request.getDateFormat()));
        return request;
    }


    private String getDeepLinkUrl(String hotelId, String checkIn, String checkOut, int noOfAdults, int noOfChildren) {

        StringBuffer stringBuffer = new StringBuffer("https://www.expedia.co.in/user/signin?ckoflag=0&uurl=e3id%3Dredr%26rurl%3D%2F");
        final String signInDeepLinkURL = urlCreator.getSignInDeepLinkURL(hotelId);
        final String[] split = signInDeepLinkURL.split("/");
        stringBuffer.append(split[3]);
        stringBuffer.append("%3F%26rfrr%3DRedirect.From.www.expedia.com%252F");
        stringBuffer.append(split[3]);
        final URIBuilder uriBuilder = new URIBuilder();
        uriBuilder.setParameter("chkin", dateUtil.changeFormatForURL(checkIn));
        uriBuilder.setParameter("chkout", dateUtil.changeFormatForURL(checkOut));
        uriBuilder.setParameter("adults", String.valueOf(noOfAdults));
        uriBuilder.setParameter("children", String.valueOf(noOfChildren));
        uriBuilder.setParameter("ts", String.valueOf(System.currentTimeMillis()));
        try {
            String encode = URLEncoder.encode(uriBuilder.toString().replaceAll("\\?", "#"), "UTF-8");
            stringBuffer.append(encode);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return stringBuffer.toString();

    }

    private double getPriceValueBaseCurrency(double price, String baseCurrency, String priceCurrency,
                                             GetHotelPricingResponse.CurrencyExchangeInfo currencyExchangeInfo) {
        double baseCurrPrice = 0;
        if (baseCurrency.equalsIgnoreCase(priceCurrency)) {
            baseCurrPrice = price;
        } else {
            if (currencyExchangeInfo.getToCurrency().equalsIgnoreCase(priceCurrency)) {
                baseCurrPrice = price * currencyExchangeInfo.getExchangeRate();
            } else {
                baseCurrPrice = price / currencyExchangeInfo.getExchangeRate();
            }
        }
        return baseCurrPrice;
    }

}
