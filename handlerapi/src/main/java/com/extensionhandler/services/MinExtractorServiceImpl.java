package com.extensionhandler.services;

import com.extensionhandler.config.AppConfig;
import com.extensionhandler.request.GetMiniHotelExtractorRequest;
import com.extensionhandler.response.GetHotelPricingResponse;
import com.extensionhandler.response.GetMinHotelResponse;
import com.extensionhandler.sro.GetHotelPricingSRO;
import com.extensionhandler.sro.HotelInfoSRO;
import com.extensionhandler.sro.HotelInfochildrenSRO;
import com.extensionhandler.sro.RoomSRO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


    @Override
    public GetMinHotelResponse miniHotelExtractor(GetMiniHotelExtractorRequest request) {

        Map<String, HotelInfochildrenSRO> partnerExpHotelIdMap = new HashMap<>();
        GetMinHotelResponse getMinHotelResponse = new GetMinHotelResponse();
        List<Integer> hotelIds = new ArrayList<>();
        Map<String, HotelInfoSRO> hotels = new HashMap<>();
        final Set<HotelInfochildrenSRO> hotelInfochildrenSROS = request.getHotelInfo();

        for (HotelInfochildrenSRO hotelInfo : hotelInfochildrenSROS) {
            final String expHotelId = appConfig.getProperty(hotelInfo.getHotelId().trim());
            partnerExpHotelIdMap.put(expHotelId, hotelInfo);
            if (expHotelId != null && !expHotelId.isEmpty())
                hotelIds.add(Integer.parseInt(expHotelId));
        }
        // get pricing from lpas
        final GetHotelPricingResponse priceDetails = lodgingPricingService.getPriceDetails(getLpasRequest(hotelIds, request));
        int decimalPlace = priceDetails.getMonetaryDecimalPlace().getDecimalPlace();

        for (GetHotelPricingResponse.HotelList hotelData : priceDetails.getHotelList()) {

            final String expHId = String.valueOf(hotelData.getHotelID());
            final GetHotelPricingResponse.CurrencyExchangeInfo currencyExchangeInfo = hotelData.getCurrencyExchangeInfo();

            final HotelInfochildrenSRO hotelInfochildrenSRO = partnerExpHotelIdMap.get(expHId);
            double modPrice = 0;
            for (GetHotelPricingResponse.ProductList productList : hotelData.getProductList()) {

                if (request.getRoomInfo().getRoomType().trim().equalsIgnoreCase(productList.getDescription().trim())) {
                    final GetHotelPricingResponse.PerDayAmounts perDayAmounts =
                            productList.getDisplayAmountsList().get(0).getPerDayAmounts().get(0);

                    modPrice = (perDayAmounts.getDisplayCategories().getDisplayBase().getTotalPrice().getValue()
                            + perDayAmounts.getDisplayCategories().getNonDisplayTaxAndFee().getTotalPrice().getValue())
                            / (Math.pow(10, decimalPlace));

                    if (currencyExchangeInfo.getToCurrency().equalsIgnoreCase(request.getCurrency()))
                        modPrice *= currencyExchangeInfo.getExchangeRate();
                    else
                        modPrice /= currencyExchangeInfo.getExchangeRate();

                    break;
                }
            }

            if ((hotelInfochildrenSRO.getPrice() > 0 && hotelInfochildrenSRO.getPrice() > modPrice)
                    || (hotelInfochildrenSRO.getPrice() == -1)) {

                hotels.put(partnerExpHotelIdMap.get(expHId).getHotelId(), new HotelInfoSRO(expHId, modPrice, urlCreator.getSignInDeepLinkURL(expHId)));
            }


        }

        getMinHotelResponse.setHotels(hotels);
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

}
