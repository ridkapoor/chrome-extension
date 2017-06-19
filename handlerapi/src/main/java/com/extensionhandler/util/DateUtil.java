package com.extensionhandler.util;

import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ridkapoor on 6/19/17.
 */

@Service
public class DateUtil {

    private final DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
    private final DateFormat toDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public String changeFormat(String date) {
        String formattedDate = null;

        date = date + " 2017";
        try {
            Date parse = dateFormat.parse(date);
            formattedDate = toDateFormat.format(parse);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return formattedDate;

    }
}
