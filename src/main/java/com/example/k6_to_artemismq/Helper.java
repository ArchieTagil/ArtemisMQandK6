package com.example.k6_to_artemismq;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Helper {
    public static String getDateTime() {
        Date currentDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss:SSS");
        String currDateTime = sdf.format(currentDate);
        return currDateTime;
    }
}
