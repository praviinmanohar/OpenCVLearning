/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.media.MediaScannerConnection
 *  android.media.MediaScannerConnection$OnScanCompletedListener
 *  android.net.Uri
 *  android.os.Environment
 *  android.os.Handler
 *  android.util.Log
 *  android.widget.Toast
 *  java.io.BufferedWriter
 *  java.io.File
 *  java.io.FileOutputStream
 *  java.io.OutputStream
 *  java.io.OutputStreamWriter
 *  java.io.Writer
 *  java.lang.CharSequence
 *  java.lang.Exception
 *  java.lang.Float
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.StringBuilder
 *  java.lang.System
 */
package com.example.android.opencvdemo.trail;

import android.content.Context;
import android.content.res.Resources;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.example.android.opencvdemo.R;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

public class RecordingDatas {
    private static String FileName;
    private static long OriginTimeForRecord;
    private static String PathToSave;
    private static int RecDuration;
    private static int RecInterval;
    private static Context con;
    private static int difference;
    private static int differencePrev;
    private static Handler mHandler4;
    private static Handler mHandler6;
    static MediaScannerConnection.OnScanCompletedListener mScanCompletedListener;
    private static boolean onRecording;
    private static String previousMyString;
    private static Resources res;
    private static String strremain;
    private static int timetorecord;

    static {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Environment.getExternalStorageDirectory().toString());
        stringBuilder.append("/DDReaderWork/");
        PathToSave = stringBuilder.toString();
        OriginTimeForRecord = 0L;
        FileName = null;
        timetorecord = 0;
        RecInterval = 1;
        RecDuration = -1;
        differencePrev = 0;
        mHandler4 = new Handler();
        mHandler6 = new Handler();
        strremain = "";
        onRecording = false;
        previousMyString = "";
        mScanCompletedListener = new MediaScannerConnection.OnScanCompletedListener(){

            public void onScanCompleted(String string2, Uri uri) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Scanned ");
                stringBuilder.append(string2);
                stringBuilder.append(":");
                Log.d((String)"MediaScannerConnection", (String)stringBuilder.toString());
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append("-> uri=");
                stringBuilder2.append((Object)uri);
                Log.d((String)"MediaScannerConnection", (String)stringBuilder2.toString());
            }
        };
    }

    private static void finalizeFile(String string2) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(PathToSave);
        stringBuilder.append(string2);
        String string3 = stringBuilder.toString();
        try {
            String[] arrstring = new String[]{string3};
            String[] arrstring2 = new String[]{"text"};
            MediaScannerConnection.scanFile((Context)con, (String[])arrstring, (String[])arrstring2, (MediaScannerConnection.OnScanCompletedListener)mScanCompletedListener);
            return;
        }
        catch (Exception exception) {
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("ErrorMessage ");
            stringBuilder2.append(exception.getMessage());
            Log.d((String)"copyfile", (String)stringBuilder2.toString());
            return;
        }
    }

    public static boolean getRecordingStatus() {
        return onRecording;
    }

    public static String getStrRemain() {
        return strremain;
    }

    private static boolean isFloatValue(String string2) {
        try {
            Float.parseFloat((String)string2);
            return true;
        }
        catch (Exception exception) {
            return false;
        }
    }

    public static void makeTestFile() {
        int[] arrn = new int[7];
        CMyTimeZone.getCurrentLocalTime(arrn);
        Object[] arrobject = new Object[]{arrn[0], arrn[1], arrn[2], arrn[3], arrn[4], arrn[5], arrn[6]};
        String string2 = String.format((String)"DDR_%04d%02d%02d_%02d%02d%02d", (Object[])arrobject);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string2);
        stringBuilder.append(".csv");
        FileName = stringBuilder.toString();
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append(FileName);
        stringBuilder2.append("\r\n");
        stringBuilder2.append(res.getString(R.string.test));
        stringBuilder2.append("\r\n0,test\r\n1,test\r\n2,test\r\n3,test");
        RecordingDatas.recordData(-1, stringBuilder2.toString());
        RecordingDatas.finalizeFile(FileName);
        FileName = null;
    }

    public static void processRecording(String string2) {
        int n = (int)(System.currentTimeMillis() - OriginTimeForRecord);
        difference = n / 1000;
        if (difference != differencePrev) {
            differencePrev = difference;
            int n2 = RecDuration - difference;
            int n3 = difference / 60;
            int n4 = difference % 60;
            int n5 = n2 / 60;
            int n6 = n2 % 60;
            if (RecDuration != -1) {
                Object[] arrobject = new Object[]{n3, n4, n5, n6};
                strremain = String.format((String)"%02d:%02d (%02d:%02d)", (Object[])arrobject);
            } else {
                Object[] arrobject = new Object[]{n3, n4};
                strremain = String.format((String)"%02d:%02d", (Object[])arrobject);
            }
        } else {
            strremain = "";
        }
        while (1000 * ((timetorecord += RecInterval) + RecInterval) <= n) {
        }
        if (1000 * timetorecord <= n) {
            String string3 = !RecordingDatas.isFloatValue(string2) ? previousMyString : string2;
            previousMyString = string2;
            RecordingDatas.recordData(timetorecord, string3);
            if (RecDuration != -1 && RecDuration < (timetorecord += RecInterval)) {
                RecordingDatas.stopRecording();
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static void recordData(int n, String string2) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(PathToSave);
        stringBuilder.append(FileName);
        String string3 = stringBuilder.toString();
        try {
            File file = new File(string3);
            file.getParentFile().mkdir();
            BufferedWriter bufferedWriter = new BufferedWriter((Writer)new OutputStreamWriter((OutputStream)new FileOutputStream(file, true), res.getString(2131558401)));
            if (n < 0) {
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append(string2);
                stringBuilder2.append("\r\n");
                bufferedWriter.write(stringBuilder2.toString());
            } else {
                StringBuilder stringBuilder3 = new StringBuilder();
                stringBuilder3.append("");
                stringBuilder3.append(n);
                stringBuilder3.append(",");
                stringBuilder3.append(string2);
                stringBuilder3.append("\r\n");
                bufferedWriter.write(stringBuilder3.toString());
            }
            bufferedWriter.flush();
            bufferedWriter.close();
            return;
        }
        catch (Exception exception) {
            StringBuilder stringBuilder4 = new StringBuilder();
            stringBuilder4.append(" file write error ");
            stringBuilder4.append(exception.getMessage());
            Log.d((String)"recordData", (String)stringBuilder4.toString());
            return;
        }
    }

    public static void setContext(Context context) {
        con = context;
    }

    public static void setRecDuration(int n) {
        RecDuration = n;
    }

    public static void setRecInterval(int n) {
        RecInterval = n;
    }

    public static void setResources(Resources resources) {
        res = resources;
    }

    public static void startRecording() {
        OriginTimeForRecord = System.currentTimeMillis();
        timetorecord = 0;
        int[] arrn = new int[7];
        CMyTimeZone.getCurrentLocalTime(arrn);
        Object[] arrobject = new Object[]{arrn[0], arrn[1], arrn[2], arrn[3], arrn[4], arrn[5], arrn[6]};
        String string2 = String.format((String)"DDR_%04d%02d%02d_%02d%02d%02d", (Object[])arrobject);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string2);
        stringBuilder.append(".csv");
        FileName = stringBuilder.toString();
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append(FileName);
        stringBuilder2.append("\r\n");
        stringBuilder2.append(res.getString(R.string.test));
        RecordingDatas.recordData(-1, stringBuilder2.toString());
        Toast.makeText((Context)con, (CharSequence)res.getString(R.string.test), (int)1).show();
        onRecording = true;
    }

    public static void stopRecording() {
        if (onRecording) {
            onRecording = false;
            RecordingDatas.finalizeFile(FileName);
            FileName = null;
        }
    }

}

