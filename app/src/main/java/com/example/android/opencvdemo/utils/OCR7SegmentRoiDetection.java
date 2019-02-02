package com.example.android.opencvdemo.utils;

import java.util.List;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;

public interface OCR7SegmentRoiDetection {
    double angle(Point point, Point point2, Point point3);

    MatOfPoint approxPolyDP(MatOfPoint matOfPoint, double d, boolean z);

    void extractChannel(Mat mat, Mat mat2, int i);

    List<MatOfPoint> findSquares(Mat mat);
}
