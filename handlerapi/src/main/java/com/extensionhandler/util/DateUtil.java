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
    private final DateFormat dateFormatUrl = new SimpleDateFormat("dd/MM/yyyy");

    public String changeFormat(String date, String format) {
        String formattedDate = null;
        Date parse = null;
        try {
            if (format.equalsIgnoreCase("dd MMM")) {
                date = date + " 2017";
                parse = dateFormat.parse(date);
            } else if (format.equalsIgnoreCase("dd/MMM/yyyy")) {
                parse = dateFormatUrl.parse(date);
            }
            formattedDate = toDateFormat.format(parse);

        } catch (ParseException e) {
            System.err.print(date + format + e.getLocalizedMessage());

        } catch (Exception e) {
            System.err.print(date + format + e.getLocalizedMessage());
        }

        return formattedDate;

    }

    public String changeFormatForURL(String date) {

        String formattedDate = null;

        try {
            Date parse = toDateFormat.parse(date);
            formattedDate = dateFormatUrl.format(parse);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return formattedDate;

    }
}
