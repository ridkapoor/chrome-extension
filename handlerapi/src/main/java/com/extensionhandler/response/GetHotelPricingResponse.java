package com.extensionhandler.response;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ridkapoor on 6/6/17.
 */
public class GetHotelPricingResponse implements Serializable {

    private MonetaryDecimalPlace MonetaryDecimalPlace;
    private List<HotelList> HotelList;

    public GetHotelPricingResponse.MonetaryDecimalPlace getMonetaryDecimalPlace() {
        return MonetaryDecimalPlace;
    }

    public void setMonetaryDecimalPlace(GetHotelPricingResponse.MonetaryDecimalPlace monetaryDecimalPlace) {
        MonetaryDecimalPlace = monetaryDecimalPlace;
    }

    public List<GetHotelPricingResponse.HotelList> getHotelList() {
        return HotelList;
    }

    public void setHotelList(List<GetHotelPricingResponse.HotelList> hotelList) {
        HotelList = hotelList;
    }

    public class HotelList implements Serializable {

        private int HotelID;
        private CurrencyExchangeInfo CurrencyExchangeInfo;
        private List<ProductList> ProductList;

        public int getHotelID() {
            return HotelID;
        }

        public void setHotelID(int hotelID) {
            HotelID = hotelID;
        }

        public GetHotelPricingResponse.CurrencyExchangeInfo getCurrencyExchangeInfo() {
            return CurrencyExchangeInfo;
        }

        public void setCurrencyExchangeInfo(GetHotelPricingResponse.CurrencyExchangeInfo currencyExchangeInfo) {
            CurrencyExchangeInfo = currencyExchangeInfo;
        }

        public List<GetHotelPricingResponse.ProductList> getProductList() {
            return ProductList;
        }

        public void setProductList(List<GetHotelPricingResponse.ProductList> productList) {
            ProductList = productList;
        }
    }

    public class ProductList implements Serializable {

        private String Description;
        private List<DisplayAmountsList> DisplayAmountsList;

        public String getDescription() {
            return Description;
        }

        public void setDescription(String description) {
            Description = description;
        }

        public List<GetHotelPricingResponse.DisplayAmountsList> getDisplayAmountsList() {
            return DisplayAmountsList;
        }

        public void setDisplayAmountsList(List<GetHotelPricingResponse.DisplayAmountsList> displayAmountsList) {
            DisplayAmountsList = displayAmountsList;
        }
    }

    public class DisplayAmountsList implements Serializable {
        private List<PerDayAmounts> PerDayAmounts;

        public List<GetHotelPricingResponse.PerDayAmounts> getPerDayAmounts() {
            return PerDayAmounts;
        }

        public void setPerDayAmounts(List<GetHotelPricingResponse.PerDayAmounts> perDayAmounts) {
            PerDayAmounts = perDayAmounts;
        }
    }

    public class PerDayAmounts implements Serializable {

        private DisplayCategories DisplayCategories;

        public GetHotelPricingResponse.DisplayCategories getDisplayCategories() {
            return DisplayCategories;
        }

        public void setDisplayCategories(GetHotelPricingResponse.DisplayCategories displayCategories) {
            DisplayCategories = displayCategories;
        }
    }

    public class DisplayCategories implements Serializable {
        private DisplayBase DisplayBase;
        private NonDisplayTaxAndFee NonDisplayTaxAndFee;

        public GetHotelPricingResponse.DisplayBase getDisplayBase() {
            return DisplayBase;
        }

        public void setDisplayBase(GetHotelPricingResponse.DisplayBase displayBase) {
            DisplayBase = displayBase;
        }

        public GetHotelPricingResponse.NonDisplayTaxAndFee getNonDisplayTaxAndFee() {
            return NonDisplayTaxAndFee;
        }

        public void setNonDisplayTaxAndFee(GetHotelPricingResponse.NonDisplayTaxAndFee nonDisplayTaxAndFee) {
            NonDisplayTaxAndFee = nonDisplayTaxAndFee;
        }
    }

    public class NonDisplayTaxAndFee implements Serializable {
        private TotalPrice TotalPrice;

        public GetHotelPricingResponse.TotalPrice getTotalPrice() {
            return TotalPrice;
        }

        public void setTotalPrice(GetHotelPricingResponse.TotalPrice totalPrice) {
            TotalPrice = totalPrice;
        }
    }

    public class DisplayBase implements Serializable {
        private TotalPrice TotalPrice;

        public GetHotelPricingResponse.TotalPrice getTotalPrice() {
            return TotalPrice;
        }

        public void setTotalPrice(GetHotelPricingResponse.TotalPrice totalPrice) {
            TotalPrice = totalPrice;
        }
    }

    public class TotalPrice implements Serializable {
        public String getCurrency() {
            return Currency;
        }

        public void setCurrency(String currency) {
            Currency = currency;
        }

        private String Currency;
        private double Value;

        public double getValue() {
            return Value;
        }

        public void setValue(double value) {
            Value = value;
        }
    }

    public class CurrencyExchangeInfo implements Serializable {
        private String FromCurrency;
        private String ToCurrency;
        private double ExchangeRate;

        public String getFromCurrency() {
            return FromCurrency;
        }

        public void setFromCurrency(String fromCurrency) {
            FromCurrency = fromCurrency;
        }

        public String getToCurrency() {
            return ToCurrency;
        }

        public void setToCurrency(String toCurrency) {
            ToCurrency = toCurrency;
        }

        public double getExchangeRate() {
            return ExchangeRate;
        }

        public void setExchangeRate(double exchangeRate) {
            ExchangeRate = exchangeRate;
        }
    }

    public class MonetaryDecimalPlace implements Serializable {
        private int DecimalPlace;

        public int getDecimalPlace() {
            return DecimalPlace;
        }

        public void setDecimalPlace(int decimalPlace) {
            DecimalPlace = decimalPlace;
        }
    }

}
