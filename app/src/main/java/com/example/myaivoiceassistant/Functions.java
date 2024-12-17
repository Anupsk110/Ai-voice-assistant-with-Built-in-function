package com.example.myaivoiceassistant;

import java.util.Calendar;

public class Functions {
    public static String wishMe() {
        String s = "";
        Calendar c = Calendar.getInstance();
        int time = c.get(Calendar.HOUR_OF_DAY);

        if (time >= 0 && time < 12) {
            s = "Good Morning Sir!";
        } else if (time >= 12 && time < 16) {
            s = "Good Afternoon Sir!";
        } else if (time >= 16 && time < 21) {
            s = "Good Evening Sir!";
        } else if (time >= 21 && time < 22) {
            s = "Good Night Sir!";
        } else if (time >= 22 && time < 24) {
            s = "Go for sleep Sir! its too late";
        }
            return s;

        }
    }
    