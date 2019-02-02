/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.res.AssetManager
 *  android.graphics.Bitmap
 *  android.os.Environment
 *  android.util.Log
 *  java.io.File
 *  java.io.FileOutputStream
 *  java.io.IOException
 *  java.io.InputStream
 *  java.lang.Exception
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.StringBuilder
 */
package com.example.android.opencvdemo.trail;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;
import com.googlecode.tesseract.android.TessBaseAPI;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import static com.googlecode.tesseract.android.TessBaseAPI.PageSegMode.PSM_AUTO;

public class CMyTessTwo {
    private static final String DATA_PATH;
    private static final String TAG = "CMyTessTow.java";
    private static TessBaseAPI baseApi;
    public static int confidence = 0;
    private static final String lang = "eng";

    static {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Environment.getExternalStorageDirectory().toString());
        stringBuilder.append("/DDReaderWork/");
        DATA_PATH = stringBuilder.toString();
        confidence = 0;
    }

    public static void closeTessTwoAPI() {
        baseApi.end();
        baseApi = null;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static void copyTessTrainedFile(String string2, AssetManager assetManager) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(DATA_PATH);
        stringBuilder.append("tessdata/");
        stringBuilder.append(string2);
        stringBuilder.append(".traineddata");
        if (new File(stringBuilder.toString()).exists()) {
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append(" quit copying ");
            stringBuilder2.append(string2);
            stringBuilder2.append(" traineddata ");
            Log.i((String)TAG, (String)stringBuilder2.toString());
            return;
        }
        try {
            StringBuilder stringBuilder3 = new StringBuilder();
            stringBuilder3.append("tessdata/");
            stringBuilder3.append(string2);
            stringBuilder3.append(".traineddata");
            InputStream inputStream = assetManager.open(stringBuilder3.toString());
            StringBuilder stringBuilder4 = new StringBuilder();
            stringBuilder4.append(DATA_PATH);
            stringBuilder4.append("tessdata/");
            stringBuilder4.append(string2);
            stringBuilder4.append(".traineddata");
            FileOutputStream fileOutputStream = new FileOutputStream(stringBuilder4.toString());
            byte[] arrby = new byte[1024];
            do {
                int n;
                if ((n = inputStream.read(arrby)) <= 0) {
                    inputStream.close();
                    fileOutputStream.close();
                    StringBuilder stringBuilder5 = new StringBuilder();
                    stringBuilder5.append("Copied ");
                    stringBuilder5.append(string2);
                    stringBuilder5.append(" traineddata");
                    Log.v((String)TAG, (String)stringBuilder5.toString());
                    return;
                }
                fileOutputStream.write(arrby, 0, n);
            } while (true);
        }
        catch (IOException iOException) {
            StringBuilder stringBuilder6 = new StringBuilder();
            stringBuilder6.append("Was unable to copy ");
            stringBuilder6.append(string2);
            stringBuilder6.append(" traineddata ");
            stringBuilder6.append(iOException.toString());
            Log.e((String)TAG, (String)stringBuilder6.toString());
            return;
        }
    }

    private static void deleteDirectory(String string2) throws Exception {
        CMyTessTwo.recursiveDeleteFile(new File(string2));
    }

    public static void deleteTrainedFileDirectory() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(DATA_PATH);
        stringBuilder.append("tessdata");
        String string2 = stringBuilder.toString();
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append("deleting Directory ");
        stringBuilder2.append(string2);
        Log.i((String)TAG, (String)stringBuilder2.toString());
        try {
            CMyTessTwo.deleteDirectory(string2);
            return;
        }
        catch (Exception exception) {
            StringBuilder stringBuilder3 = new StringBuilder();
            stringBuilder3.append("Was unable to deleteDirectory ");
            stringBuilder3.append(string2);
            stringBuilder3.append(exception.toString());
            Log.e((String)TAG, (String)stringBuilder3.toString());
            return;
        }
    }

    public static int meanConfidence() {
        return confidence;
    }

    public static void openTessTwoAPI() {
        baseApi = new TessBaseAPI();
    }

    public static String recognize(Bitmap bitmap, int n, int n2, int n3, int n4) {
        baseApi.setImage(bitmap);
        baseApi.setRectangle(n, n2, n3, n4);
        String string2 = baseApi.getUTF8Text();
        confidence = baseApi.meanConfidence();
        baseApi.clear();
        return string2;
    }

    private static void recursiveDeleteFile(File file) throws Exception {
        if (!file.exists()) {
            return;
        }
        if (file.isDirectory()) {
            File[] arrfile = file.listFiles();
            int n = arrfile.length;
            for (int i = 0; i < n; ++i) {
                CMyTessTwo.recursiveDeleteFile(arrfile[i]);
            }
        }
        file.delete();
    }

    public static void setTrainedFile(AssetManager assetManager) {
        String[] arrstring = new String[2];
        String string2 = DATA_PATH;
        arrstring[0] = string2;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(DATA_PATH);
        stringBuilder.append("tessdata/");
        arrstring[1] = stringBuilder.toString();
        for (String string3 : arrstring) {
            File file = new File(string3);
            if (file.exists()) continue;
            if (!file.mkdirs()) {
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append("ERROR: Creation of directory ");
                stringBuilder2.append(string3);
                stringBuilder2.append(" on sdcard failed");
                Log.v((String)TAG, (String)stringBuilder2.toString());
                return;
            }
            StringBuilder stringBuilder3 = new StringBuilder();
            stringBuilder3.append("Created directory ");
            stringBuilder3.append(string3);
            stringBuilder3.append(" on sdcard");
            Log.v((String)TAG, (String)stringBuilder3.toString());
        }
        CMyTessTwo.copyTessTrainedFile(lang, assetManager);
        CMyTessTwo.copyTessTrainedFile("letsgodigital", assetManager);
        CMyTessTwo.copyTessTrainedFile("7seg", assetManager);
        CMyTessTwo.copyTessTrainedFile("cos", assetManager);
    }

    public static void startTessTwoAPI() {
        baseApi.setDebug(true);
        boolean bl = baseApi.init(DATA_PATH, "eng+letsgodigital+7seg+cos", TessBaseAPI.OEM_TESSERACT_ONLY);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("return value of baseApi.init = ");
        stringBuilder.append(bl);
        Log.i((String)TAG, (String)stringBuilder.toString());
        baseApi.setVariable("tessedit_char_whitelist", ":1234567890.-");
        baseApi.setVariable("tessedit_char_blacklist", "!@#$%^&*()_+=qwertyuiop[]}{POIUYTRWQasdASDfghFGHjklJKLl;L:'\"\\|~`xcvXCVbnmBNM,/<>?");
        baseApi.setPageSegMode(PSM_AUTO);
    }
}

