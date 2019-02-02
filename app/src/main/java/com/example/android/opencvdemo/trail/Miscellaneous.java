/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.util.Log
 *  java.lang.CharSequence
 *  java.lang.Double
 *  java.lang.Exception
 *  java.lang.Float
 *  java.lang.Integer
 *  java.lang.Math
 *  java.lang.NumberFormatException
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.StringBuilder
 *  java.util.Arrays
 */
package com.example.android.opencvdemo.trail;

import android.util.Log;
import java.util.Arrays;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

public class Miscellaneous {
    public static Mat lut = new Mat(1, 256, CvType.CV_8UC1);
    private static int slant = 0;

    public static void adaptCompensation(Mat mat, int n, int n2, int n3, int n4, int n5, int n6, int n7) {
        Mat mat2 = mat;
        double[] arrd = new double[]{0.015, 0.03, 0.05, 0.1, 0.2};
        double d = 128.0;
        for (int i = n2 - 1; i >= 0; --i) {
            int n8 = n4 - n3;
            int n9 = n3 + n8 * i / n2;
            int n10 = n3 + n8 * (i + 1) / n2;
            Mat mat3 = mat2.submat(n5, n6, n9, n10);
            int n11 = (n6 - n5) / 5;
            Mat mat4 = mat2.submat(n5 + n11, n6 - n11, n9, n10);
            Object object = mat3.clone();
            double d2 = Miscellaneous.getvariance(mat4);
            double d3 = Imgproc.threshold(mat4, mat4, 0.0, 255.0, 8);
            if (d2 >= 150.0) {
                d = 1000.0 < d2 ? 1.05 * d3 : d3;
            }
            if (n7 == 1) {
                d *= 0.95;
            }
            mat4.release();
            Miscellaneous.setLUT(d, arrd[n]);
            Core.LUT((Mat)object, lut, mat3);
            ((Mat)object).release();
            mat3.release();
            mat2 = mat;
        }
    }

    public static void adaptGaussianBlur(Mat mat, int n, int n2, int n3, int n4, int n5, double d) {
        double d2 = n4 - n2;
        Double.isNaN((double)d2);
        Mat mat2 = mat.submat(n2, n4 - (int)(d2 * d), n, n3);
        Object object = mat2.clone();
        if (n5 == 0) {
            n5 = 3;
        }
        double d3 = n5;
        Imgproc.GaussianBlur((Mat)object, mat2, new Size(d3, d3), 0.0, 0.0);
        mat2.release();
        ((Mat)object).release();
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private static String analysisCols(int[] var0_1, int var1, int var2_2) {
    return null;
    }

    public static String arrangeValueString(String string2) {
        String string3;
        boolean bl;
        String string4;
        block16 : {
            if (string2.equals((Object)"")) {
                return "";
            }
            String[] arrstring = string2.split("\\.");
            if (2 < arrstring.length) {
                return "";
            }
            if (arrstring.length == 0) {
                return "";
            }
            string3 = arrstring[0].replace((CharSequence)" ", (CharSequence)"");
            if (string3.length() > 0 && string3.substring(0, 1).equals((Object)"-")) {
                string3 = string3.substring(1);
            }
            if (string3.contains((CharSequence)"-")) {
                return "";
            }
            while (string3.matches("^0.+")) {
                string3 = string3.substring(1);
            }
            if (string3.equals((Object)"")) {
                string3 = "0";
            }
            if (arrstring.length == 1) {
                block15 : {
                    try {
                        if (Integer.parseInt((String)string3) != 0) break block15;
                        return "0";
                    }
                    catch (NumberFormatException numberFormatException) {
                        return "";
                    }
                }
                if (string2.contains((CharSequence)"-")) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("-");
                    stringBuilder.append(string3);
                    string3 = stringBuilder.toString();
                }
                return string3;
            }
            string4 = arrstring[1];
            if (string4.contains((CharSequence)"-")) {
                return "";
            }
            try {
                int n = Integer.parseInt((String)string3);
                int n2 = Integer.parseInt((String)string4);
                bl = false;
                if (n != 0) break block16;
                bl = false;
                if (n2 != 0) break block16;
                bl = true;
            }
            catch (NumberFormatException numberFormatException) {
                return "";
            }
        }
        if (string2.contains((CharSequence)"-") && !bl) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("-");
            stringBuilder.append(string3);
            string3 = stringBuilder.toString();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string3);
        stringBuilder.append(".");
        stringBuilder.append(string4);
        return stringBuilder.toString();
    }

    public static void concatLines(Mat mat, Scalar scalar, int n, int n2, int n3, int n4) {
    }

    public static int convert(int n) {
        int n2 = n & 383;
        if (n2 == 304) {
            return -1;
        }
        if (n2 == 256) {
            return -2;
        }
        switch (n & 127) {
            default: {
                return -9;
            }
            case 127: {
                return 8;
            }
            case 126: {
                return 0;
            }
            case 123: {
                return 9;
            }
            case 121: {
                return 3;
            }
            case 115: {
                return 9;
            }
            case 114: {
                return 7;
            }
            case 112: {
                return 7;
            }
            case 109: {
                return 2;
            }
            case 95: {
                return 6;
            }
            case 91: {
                return 5;
            }
            case 51: {
                return 4;
            }
            case 49: {
                return -1;
            }
            case 48: {
                return 1;
            }
            case 31: {
                return 6;
            }
            case 1: 
        }
        return -2;
    }

    private static void countCols(byte[] arrby, int n, int n2, Scalar scalar, int[] arrn) {
        int n3 = n;
        int n4 = n2;
        double[] arrd = scalar.val;
        int n5 = 0;
        int n6 = 5 * (int)arrd[0] / 10;
        int[] arrn2 = new int[n4];
        Miscellaneous.countwhite(arrby, n3, n4, n6, arrn2);
        int n7 = n3 * 3 / 100;
        int n8 = n3 * 97 / 100;
        int n9 = n3 * 30 / 100;
        int n10 = n3 * 35 / 100;
        int n11 = n3 * 40 / 100;
        int n12 = n3 * 60 / 100;
        int n13 = n3 * 65 / 100;
        int n14 = n3 * 70 / 100;
        int n15 = n7;
        int n16 = n8;
        int n17 = n9;
        int n18 = n10;
        int n19 = n11;
        int n20 = n12;
        int n21 = n13;
        int n22 = n14;
        for (int i = 0; i < n4; ++i) {
            int[] arrn3;
            int n23;
            int n24;
            int n25;
            int n26;
            if (n5 <= 0 && arrn2[i] > 0 || n5 > 0 && arrn2[i] > 0 && n5 != arrn2[i]) {
                int n27 = 95 * arrn2[i] / 100;
                int n28 = n3 - n27;
                n23 = n6;
                double d = n28;
                arrn3 = arrn2;
                double d2 = n3;
                Double.isNaN((double)d);
                Double.isNaN((double)d2);
                double d3 = d / d2;
                double d4 = n27;
                double d5 = n7;
                Double.isNaN((double)d5);
                double d6 = d5 * d3;
                Double.isNaN((double)d4);
                int n29 = (int)(d6 + d4);
                double d7 = n8;
                Double.isNaN((double)d7);
                double d8 = d7 * d3;
                Double.isNaN((double)d4);
                int n30 = (int)(d8 + d4);
                double d9 = n9;
                Double.isNaN((double)d9);
                double d10 = d9 * d3;
                Double.isNaN((double)d4);
                int n31 = (int)(d10 + d4);
                double d11 = n10;
                Double.isNaN((double)d11);
                double d12 = d11 * d3;
                Double.isNaN((double)d4);
                int n32 = (int)(d12 + d4);
                double d13 = n11;
                Double.isNaN((double)d13);
                double d14 = d13 * d3;
                Double.isNaN((double)d4);
                int n33 = (int)(d14 + d4);
                double d15 = n12;
                Double.isNaN((double)d15);
                double d16 = d15 * d3;
                Double.isNaN((double)d4);
                int n34 = (int)(d16 + d4);
                double d17 = n13;
                Double.isNaN((double)d17);
                double d18 = d17 * d3;
                Double.isNaN((double)d4);
                int n35 = (int)(d18 + d4);
                double d19 = n14;
                Double.isNaN((double)d19);
                double d20 = d19 * d3;
                Double.isNaN((double)d4);
                int n36 = (int)(d4 + d20);
                int n37 = arrn3[i];
                n22 = n36;
                n5 = n37;
                n15 = n29;
                n26 = n30;
                n24 = n31;
                n18 = n32;
                n19 = n33;
                n25 = n34;
                n21 = n35;
            } else {
                n23 = n6;
                arrn3 = arrn2;
                if (n5 > 0 && arrn3[i] <= 0) {
                    n5 = arrn3[i];
                    n15 = n7;
                    n26 = n8;
                    n24 = n9;
                    n18 = n10;
                    n19 = n11;
                    n25 = n12;
                    n21 = n13;
                    n22 = n14;
                } else {
                    n24 = n17;
                    n25 = n20;
                    n26 = n16;
                }
            }
            int n38 = 255 & arrby[i + n24 * n2];
            int n39 = n5;
            int n40 = n23;
            boolean bl = n38 < n40;
            int n41 = i + n18 * n2;
            int n42 = n7;
            boolean bl2 = (255 & arrby[n41]) < n40;
            int n43 = i + n19 * n2;
            int n44 = n8;
            boolean bl3 = (255 & arrby[n43]) < n40;
            int n45 = i + n25 * n2;
            int n46 = n9;
            boolean bl4 = (255 & arrby[n45]) < n40;
            int n47 = i + n21 * n2;
            int n48 = n10;
            boolean bl5 = (255 & arrby[n47]) < n40;
            int n49 = i + n22 * n2;
            int n50 = n11;
            boolean bl6 = (255 & arrby[n49]) < n40;
            arrn[i] = 0;
            if (bl && bl2 && bl3) {
                arrn[i] = 18 | arrn[i];
            }
            if (bl4 && bl5 && bl6) {
                arrn[i] = 17 | arrn[i];
            }
            if (arrn[i] == 0) {
                int n51 = 0;
                for (int j = n15; j < n24; ++j) {
                    if ((255 & arrby[i + j * n2]) < n40) {
                        ++n51;
                    }
                    if (2 >= n51) continue;
                    arrn[i] = 36 | arrn[i];
                    break;
                }
                int n52 = 0;
                for (int j = n19; j < n25; ++j) {
                    if ((255 & arrby[i + j * n2]) < n40) {
                        ++n52;
                    }
                    if (2 >= n52) continue;
                    arrn[i] = 34 | arrn[i];
                    break;
                }
                if (arrn[i] != 0) {
                    int n53 = 0;
                    for (int j = n22; j < n26; ++j) {
                        if ((255 & arrby[i + j * n2]) < n40) {
                            ++n53;
                        }
                        if (2 >= n53) continue;
                        arrn[i] = 33 | arrn[i];
                        break;
                    }
                }
                if (arrn[i] == 0) {
                    int n54 = 0;
                    for (int j = n21; j < n26; ++j) {
                        if ((255 & arrby[i + j * n2]) >= n40) continue;
                        ++n54;
                    }
                    int n55 = 15;
                    if (n55 >= n54) {
                        n55 = n54;
                    }
                    arrn[i] = n55 + 128;
                }
            }
            n17 = n24;
            n20 = n25;
            n16 = n26;
            n6 = n40;
            n5 = n39;
            n7 = n42;
            n8 = n44;
            n9 = n46;
            n10 = n48;
            n11 = n50;
            n3 = n;
            n4 = n2;
            arrn2 = arrn3;
        }
    }

    private static void countwhite(byte[] arrby, int n, int n2, int n3, int[] arrn) {
        int n4;
        int n5 = n * 3 / 100;
        int n6 = n * 75 / 100;
        int n7 = n6 - n5 - 1;
        int n8 = n7 * 95 / 100;
        int n9 = n7 * 5 / 100;
        for (int i = 0; i < n2; ++i) {
            boolean bl = n3 < (255 & arrby[i + n5 * n2]);
            int n10 = n5 + 1;
            int n11 = 0;
            while (n10 < n6) {
                boolean bl2 = n3 < (255 & arrby[i + ++n10 * n2]);
                if (!bl && !bl2) break;
                ++n11;
                bl = bl2;
            }
            arrn[i] = n8 < n11 ? -1 : (n11 < n9 ? 0 : n11);
        }
        int n12 = 1000;
        int n13 = 1000;
        int n14 = 0;
        boolean bl = false;
        for (n4 = 0; n4 < n2; ++n4) {
            if (!bl) {
                if (arrn[n4] == -1) continue;
                n12 = arrn[n4];
                n14 = n4;
                n13 = n12;
                bl = true;
                continue;
            }
            if (arrn[n4] != -1) {
                if (arrn[n4] < n13) {
                    n13 = arrn[n4];
                    continue;
                }
                if (arrn[n4] >= n12) continue;
                n12 = arrn[n4];
                continue;
            }
            for (int i = n14; i < n4; ++i) {
                arrn[i] = n12;
            }
            n12 = 1000;
            n13 = 1000;
            bl = false;
        }
        if (bl && n12 < n) {
            while (n14 < n4) {
                arrn[n14] = n12;
                ++n14;
            }
        }
        boolean bl3 = false;
        for (int i = 0; i < n2; ++i) {
            if (arrn[i] == 0) {
                bl3 = true;
            }
            if (bl3 || arrn[i] <= 0) continue;
            arrn[i] = 0;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("whites[] = ");
        stringBuilder.append(Arrays.toString((int[])arrn));
        Log.i((String)"Miscellaneous", (String)stringBuilder.toString());
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public static void deleteHorizontalblack(Mat var0, int var1_4, int var2_5, int var3_2, int var4_3, int var5_1) {

    }

    private static double evaluateSlant(Mat mat, double d, Scalar scalar) {
        double d2 = Math.tan((double)(3.1415926535 * d / 180.0));
        double d3 = 0.0;
        double[] arrd = new double[]{1.0, d2, d3, d3, 1.0, d3};
        Mat mat2 = new Mat(2, 3, CvType.CV_64FC1);
        mat2.put(0, 0, arrd);
        Object object = mat.clone();
        int n = ((Mat)object).rows();
        int n2 = ((Mat)object).cols();
        double d4 = n;
        Double.isNaN((double)d4);
        int n3 = 1 + (int)(d2 * d4);
        Imgproc.warpAffine(mat, (Mat)object, mat2, ((Mat)object).size(), 1, 5, scalar);
        byte[] arrby = new byte[(int)(((Mat)object).total() * (long)((Mat)object).channels())];
        ((Mat)object).get(0, 0, arrby);
        Double.isNaN((double)d4);
        int n4 = (int)(d4 * 0.05);
        Double.isNaN((double)d4);
        int n5 = (int)(d4 * 0.95);
        double d5 = n2;
        Double.isNaN((double)d5);
        int n6 = (int)(d5 * 0.95);
        int n7 = 0;
        for (int i = n4; i < n5; ++i) {
            n7 += 255 & arrby[n3 + i * n2];
        }
        for (int i = n3 + 1; i < n6 - 1; ++i) {
            int n8 = 0;
            for (int j = n4; j < n5; ++j) {
                n8 += 255 & arrby[i + j * n2];
            }
            double d6 = n8 - n7;
            Double.isNaN((double)d6);
            Double.isNaN((double)d6);
            d3 += d6 * d6;
            n7 = n8;
        }
        double d7 = (1 + (n5 - n4)) * (1 + (n6 - 2 - n3));
        Double.isNaN((double)d7);
        double d8 = d3 / d7;
        ((Mat)object).release();
        return d8;
    }

    public static double getBrightness(Mat mat, int n, int n2, int n3, int n4) {
        int n5 = (n2 + n4) / 2;
        int n6 = (n3 - n) / 5;
        //new double[10];
        double d = 0.0;
        for (int i = n + n6; i < n3 - n6; i += 2) {
            double[] arrd = mat.get(n5, i);
            if (d >= arrd[0]) continue;
            d = arrd[0];
        }
        return d;
    }

    public static int getSlant() {
        return slant;
    }

    private static int getmaxFreqNum(int[] arrn) {
        int n = 0;
        int n2 = 0;
        for (int i = 0; i < arrn.length; ++i) {
            if (n >= arrn[i]) continue;
            n = arrn[i];
            n2 = i;
        }
        return n2;
    }

    public static double getvariance(Mat mat) {
        double d;
        int n = mat.height();
        int n2 = mat.width();
        int n3 = n * n2;
        byte[] arrby = new byte[(int)(mat.total() * (long)mat.channels())];
        mat.get(0, 0, arrby);
        double d2 = d = 0.0;
        for (int i = 0; i < n; ++i) {
            double d3 = d2;
            for (int j = 0; j < n2; ++j) {
                double d4 = 255 & arrby[j + i * n2];
                Double.isNaN((double)d4);
                d3 += d4;
            }
            d2 = d3;
        }
        double d5 = n3;
        Double.isNaN((double)d5);
        double d6 = d2 / d5;
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n2; ++j) {
                double d7 = 255 & arrby[j + i * n2];
                Double.isNaN((double)d7);
                double d8 = d7 - d6;
                d += d8 * d8;
            }
        }
        Double.isNaN((double)d5);
        return d / d5;
    }

    public static boolean isFloatValue(String string2) {
        try {
            Float.parseFloat((String)string2);
            return true;
        }
        catch (Exception exception) {
            return false;
        }
    }

    public static void modifyCharColor(Mat mat, Mat mat2, int n, int n2, int n3, int n4, int n5) {
        Mat mat3 = mat.submat(n2, n4, n, n3);
        Mat mat4 = mat2.submat(n2, n4, n, n3);
        byte[] arrby = new byte[(int)(mat3.total() * (long)mat3.channels())];
        byte[] arrby2 = new byte[(int)(mat4.total() * (long)mat4.channels())];
        mat3.get(0, 0, arrby);
        mat4.get(0, 0, arrby2);
        int n6 = mat3.cols();
        int n7 = mat3.rows();
        int n8 = mat3.channels();
        if (n5 == 1) {
            for (int i = 0; i < n6; ++i) {
                for (int j = 0; j < n7; ++j) {
                    int n9 = i + j * n6;
                    int n10 = n9 * n8;
                    arrby2[n9] = (byte)((765 - (255 & arrby[n10]) - (255 & arrby[n10 + 1]) - (255 & arrby[n10 + 2])) / 3);
                }
            }
        } else if (n5 == 2) {
            for (int i = 0; i < n6; ++i) {
                for (int j = 0; j < n7; ++j) {
                    int n11 = i + j * n6;
                    int n12 = n11 * n8;
                    arrby2[n11] = (byte)(255 - (255 & arrby[n12]) + ((255 & arrby[n12 + 1]) + (255 & arrby[n12 + 2]) >> 1) >> 1);
                }
            }
        } else if (n5 == 3) {
            for (int i = 0; i < n6; ++i) {
                for (int j = 0; j < n7; ++j) {
                    int n13 = i + j * n6;
                    int n14 = n13 * n8;
                    arrby2[n13] = (byte)(255 - (255 & arrby[n14 + 1]) + ((255 & arrby[n14]) + (255 & arrby[n14 + 2]) >> 1) >> 1);
                }
            }
        }
        mat4.put(0, 0, arrby2);
    }

    public static void modifyDecimalPoint(byte[] arrby, int n, int n2, Scalar scalar) {
        int n3 = n * 2 / 3;
        int n4 = n * 3;
        int n5 = n4 / 100;
        int n6 = n * 97 / 100;
        int n7 = (int)scalar.val[0];
        int n8 = n7 * 5 / 10;
        int n9 = n7 * 3 / 10;
        int[] arrn = new int[n2];
        block0 : for (int i = 0; i < n2; ++i) {
            int n10;
            arrn[i] = 0;
            int n11 = 0;
            for (n10 = n5; n10 < n3; n10 += 2) {
                int n12;
                if ((255 & arrby[i + n10 * n2]) < n8) {
                    // empty if block
                }
                if (2 < (n12 = ++n11)) {
                    arrn[i] = 10000;
                    break;
                }
                n11 = n12;
            }
            if (arrn[i] == 10000) continue;
            int n13 = 0;
            while (n10 < n6) {
                if ((255 & arrby[i + n10 * n2]) < n9) {
                    ++n13;
                }
                if (1 < n13) {
                    arrn[i] = 1;
                    continue block0;
                }
                ++n10;
            }
        }
        int n14 = 0;
        int n15 = 0;
        int n16 = 0;
        boolean bl = false;
        int n17 = 0;
        int n18 = 0;
        for (int i = 0; i < n2; ++i) {
            if (arrn[i] != 10000 && bl) {
                if (n17 == 0) {
                    n17 = i;
                }
                if (arrn[i] == 0) continue;
                ++n18;
                continue;
            }
            if (arrn[i] != 10000) continue;
            if (n17 != 0) {
                int n19 = i - 1;
                if (n16 < n18) {
                    n14 = n19;
                } else {
                    n17 = n15;
                    n18 = n16;
                }
                n15 = n17;
                n16 = n18;
                bl = true;
                n17 = 0;
                n18 = 0;
                continue;
            }
            bl = true;
        }
        int n20 = n * 1;
        int n21 = n20 / 4;
        int n22 = n4 / 4;
        int n23 = n14 + 1 - n15;
        if (1 < n16 && n23 < n2 * 10 / 35) {
            int n24;
            int n25;
            int n26;
            while (n3 < n) {
                for (int i = n15; i < n14; ++i) {
                    arrby[i + n3 * n2] = (byte)n7;
                }
                ++n3;
            }
            if (n21 < n23) {
                n25 = n20 / 10;
                n26 = n25 * 2;
                n24 = 2;
            } else {
                double d = n23 / 4;
                Double.isNaN((double)d);
                n25 = (int)(d + 0.5);
                n24 = 2;
                double d2 = n23 * 2 / 3;
                Double.isNaN((double)d2);
                n26 = (int)(d2 + 0.5);
            }
            for (int i = n22; i < n22 + n25 * 3 / n24; ++i) {
                for (int j = n15 + n25; j < n15 + n26; ++j) {
                    arrby[j + i * n2] = 0;
                }
            }
        }
    }

    public static void modifySlant(Mat mat, int n, int n2, int n3, int n4, Scalar scalar) {
        boolean bl;
        Object object = mat.clone();
        Mat mat2 = ((Mat)object).submat(n2, n4, n, n3);
        double d = Miscellaneous.evaluateSlant(mat2, slant, scalar);
        if (slant < 9 && d < Miscellaneous.evaluateSlant(mat2, 1 + slant, scalar)) {
            slant = 1 + slant;
            bl = true;
        } else {
            bl = false;
        }
        if (!bl && slant > 0 && d < Miscellaneous.evaluateSlant(mat2, slant - 1, scalar)) {
            --slant;
        }
        double d2 = slant;
        Double.isNaN((double)d2);
        double d3 = Math.tan((double)(d2 * 3.1415926535 / 180.0));
        double[] arrd = new double[]{1.0, d3, 0.0, 0.0, 1.0, 0.0};
        Mat mat3 = new Mat(2, 3, CvType.CV_64FC1);
        mat3.put(0, 0, arrd);
        Mat mat4 = mat.submat(n2, n4, n, n3);
        double d4 = n;
        Point point = new Point(d4, n2);
        double d5 = n4 - n2;
        Double.isNaN((double)d5);
        double d6 = d5 * d3;
        Double.isNaN((double)d4);
        Imgproc.rectangle(mat, point, new Point(d4 + d6, n4), scalar, -1);
        Imgproc.warpAffine(mat2, mat4, mat3, mat4.size(), 1, 5, scalar);
        ((Mat)object).release();
    }

    public static void setLUT(double d, double d2) {
        for (int i = 0; i < 256; ++i) {
            Mat mat = lut;
            double[] arrd = new double[1];
            double d3 = -d2;
            double d4 = i - (int)d;
            Double.isNaN((double)d4);
            arrd[0] = 255.0 * (1.0 / (1.0 + Math.exp((double)(d3 * d4))));
            mat.put(0, i, arrd);
        }
    }

    public static String sevenSegRecognition(Mat mat, Scalar scalar) {
        byte[] arrby = new byte[(int)(mat.total() * (long)mat.channels())];
        mat.get(0, 0, arrby);
        int n = mat.rows();
        int n2 = mat.cols();
        int[] arrn = new int[n2 + 10];
        for (int i = n2; i < n2 + 8; ++i) {
            arrn[i] = 128;
        }
        Miscellaneous.countCols(arrby, n, n2, scalar, arrn);
        return Miscellaneous.analysisCols(arrn, n2, n);
    }
}

