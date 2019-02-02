package com.example.android.opencvdemo.utils;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.os.Environment;
import android.util.Log;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

public class OCR7SegmentImageEnhacementImpl implements OCR7SegmentImageEnhacement {
    private static final String DATA_PATH = new StringBuilder(String.valueOf(Environment.getExternalStorageDirectory().toString())).append("/SimpleAndroidOCR/").toString();
    private static final String TAG = "OCVSample::Activity";

    public Mat deskew(Mat imginput) {
        Mat ret = imginput.clone();
        Size size = imginput.size();
        Core.bitwise_not(imginput, ret);
        Point center = new Point((double) (imginput.width() / 2), (double) (imginput.height() / 2));
        Mat lines = new Mat();
        Imgproc.HoughLinesP(ret, lines, 1.0d, 0.017453292519943295d, 100, size.width / 2.0d, 20.0d);
        double angle = 0.0d;
        for (int i = 0; i < lines.height(); i++) {
            for (int j = 0; j < lines.width(); j++) {
                angle += Math.atan2(lines.get(i, j)[3] - lines.get(i, j)[1], lines.get(i, j)[2] - lines.get(i, j)[0]);
            }
        }
        angle = (180.0d * (angle / lines.size().area())) / 3.141592653589793d;
        Log.v(TAG, "ANGLE " + angle);
        if (angle <= 1.0d && angle >= 1.0d) {
            return imginput;
        }
        Imgproc.warpAffine(imginput, ret, Imgproc.getRotationMatrix2D(center, angle, 1.0d), size);
        return ret;
    }

    public Mat eliminateLines(Mat imglines) {
        OutputStream bufferedOutputStream;
        Mat bw = new Mat();
        Core.bitwise_not(imglines, bw);
        Mat horizontal = bw.clone();
        Mat vertical = bw.clone();
        int verticalsize = vertical.rows() / 30;
        int horizontalsize = horizontal.cols() / 30;
        Log.v(TAG, "HorizontalSize " + horizontalsize);
        Mat horizontalStructure = Imgproc.getStructuringElement(0, new Size((double) horizontalsize, 1.0d));
        Imgproc.erode(horizontal, horizontal, horizontalStructure, new Point(-1.0d, -1.0d), 1);
        Imgproc.dilate(horizontal, horizontal, horizontalStructure, new Point(-1.0d, -1.0d), 1);
        Bitmap bitmap1 = Bitmap.createBitmap(horizontal.cols(), horizontal.rows(), Config.ARGB_8888);
        Utils.matToBitmap(horizontal, bitmap1);
        try {
            bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(new File(DATA_PATH + "/Horizontal.png")));
            bitmap1.compress(CompressFormat.PNG, 0, bufferedOutputStream);
            bufferedOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Mat verticalStructure = Imgproc.getStructuringElement(0, new Size(1.0d, (double) verticalsize));
        Log.v(TAG, "VerticalSize " + verticalsize);
        Imgproc.erode(vertical, vertical, verticalStructure, new Point(-1.0d, -1.0d), 1);
        Imgproc.dilate(vertical, vertical, verticalStructure, new Point(-1.0d, -1.0d), 1);
        Bitmap bitmap2 = Bitmap.createBitmap(vertical.cols(), vertical.rows(), Config.ARGB_8888);
        Utils.matToBitmap(vertical, bitmap2);
        try {
            bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(new File(DATA_PATH + "/Vertical.png")));
            bitmap2.compress(CompressFormat.PNG, 0, bufferedOutputStream);
            bufferedOutputStream.close();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        Mat edges = new Mat();
        Mat smooth_V = new Mat();
        vertical.copyTo(smooth_V);
        Imgproc.blur(smooth_V, smooth_V, new Size(2.0d, 2.0d));
        smooth_V.copyTo(vertical, edges);
        Imgproc.threshold(vertical, vertical, 127.0d, 255.0d, 1);
        return vertical;
    }

    public Bitmap CreateBitmapFromMat(Mat intupImage) {
        return null;
    }
}
