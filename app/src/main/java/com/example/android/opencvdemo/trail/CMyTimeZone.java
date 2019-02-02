/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.PrintStream
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.System
 *  java.util.Calendar
 *  java.util.TimeZone
 */
package com.example.android.opencvdemo.trail;

import java.io.PrintStream;
import java.util.Calendar;
import java.util.TimeZone;

public class CMyTimeZone {
    static TimeZone HostTZ;
    static int LocalTimeOffset;
    static TimeZone LocatTZ;
    static Calendar cal;
    static TimeZone utc;

    static {
        utc = TimeZone.getTimeZone((String)"UTC");
        LocatTZ = HostTZ = TimeZone.getDefault();
        LocalTimeOffset = LocatTZ.getRawOffset();
    }

    public static void getCurrentLocalTime(int[] arrn) {
        cal = Calendar.getInstance();
        cal.setTimeZone(LocatTZ);
        Calendar calendar = cal;
        arrn[0] = calendar.get(Calendar.YEAR);
        Calendar calendar2 = cal;
        arrn[1] = 1 + calendar2.get(Calendar.MONTH);
        Calendar calendar3 = cal;
        arrn[2] = calendar3.get(Calendar.DATE);
        Calendar calendar4 = cal;
        arrn[3] = calendar4.get(Calendar.HOUR_OF_DAY);
        Calendar calendar5 = cal;
        arrn[4] = calendar5.get(Calendar.MINUTE);
        Calendar calendar6 = cal;
        arrn[5] = calendar6.get(Calendar.SECOND);

        Calendar calendar7 = cal;
        arrn[6] = calendar7.get(Calendar.MILLISECOND);
        String string2 = LocatTZ.getDisplayName();
        System.out.println(string2);
        System.out.println(LocatTZ.getID());
    }

    public static void getCurrentUTC(int[] arrn) {
        cal = Calendar.getInstance();
        cal.setTimeZone(utc);
        Calendar calendar = cal;
        arrn[0] = calendar.get(Calendar.YEAR);
        Calendar calendar2 = cal;
        arrn[1] = 1 + calendar2.get(Calendar.YEAR);
        Calendar calendar3 = cal;
        arrn[2] = calendar3.get(Calendar.DATE);
        Calendar calendar4 = cal;
        arrn[3] = calendar4.get(Calendar.HOUR_OF_DAY);
        Calendar calendar5 = cal;
        arrn[4] = calendar5.get(Calendar.MINUTE);
        Calendar calendar6 = cal;
        arrn[5] = calendar6.get(Calendar.SECOND);
        Calendar calendar7 = cal;
        arrn[6] = calendar7.get(Calendar.MILLISECOND);
        String string2 = utc.getDisplayName();
        System.out.println(string2);
        System.out.println(utc.getID());
    }

    public static int getLocalOffset() {
        return LocalTimeOffset;
    }

    public static String showLocalInfo() {
        String string2 = LocatTZ.getDisplayName();
        System.out.println(string2);
        return string2;
    }
}

