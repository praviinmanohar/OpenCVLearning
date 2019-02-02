package com.example.android.opencvdemo.utils;

import android.graphics.Bitmap;
import org.opencv.core.Mat;

public interface OCR7SegmentImageEnhacement {
    Bitmap CreateBitmapFromMat(Mat mat);

    Mat deskew(Mat mat);

    Mat eliminateLines(Mat mat);
}
