package com.extensionhandler.util;

import org.springframework.stereotype.Service;

import java.text.DecimalFormat;

/**
 * Created by ridkapoor on 6/19/17.
 */

@Service
public class PriceUtil {

    private final DecimalFormat decimalFormat = new DecimalFormat("####,###,###.##");

    public String formatPrice(double price) {
        return decimalFormat.format(price);
    }

}
