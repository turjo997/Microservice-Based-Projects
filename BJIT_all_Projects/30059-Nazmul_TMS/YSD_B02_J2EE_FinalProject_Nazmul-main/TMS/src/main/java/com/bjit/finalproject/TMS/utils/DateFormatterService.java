package com.bjit.finalproject.TMS.utils;

import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class DateFormatterService {
    //Saves in database the date in string format
    public String formatDateForDatabase(String dateTime){
        SimpleDateFormat inputFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        SimpleDateFormat outputFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = "";
        try{
            Date newDate = inputFormatter.parse(dateTime);
            formattedDate = outputFormatter.format(newDate);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return formattedDate;
    }

    //sends a formatted date response
    public String formatDateForClient(String dateTime){
        SimpleDateFormat outputFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        SimpleDateFormat inputFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = "";
        try{
            Date newDate = inputFormatter.parse(dateTime);
            formattedDate = outputFormatter.format(newDate);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return formattedDate;
    }
}
