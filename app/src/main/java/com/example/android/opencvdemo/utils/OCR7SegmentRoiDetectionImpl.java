package com.example.android.opencvdemo.utils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfInt;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

public class OCR7SegmentRoiDetectionImpl implements OCR7SegmentRoiDetection {
    private int N = 5;

    public List<MatOfPoint> findSquares(Mat inputImage) {
        List<MatOfPoint> squares = new LinkedList();
        Mat mat = new Mat(new Size((double) (inputImage.width() / 2), (double) (inputImage.height() / 2)), inputImage.type());
        Mat gray = new Mat(inputImage.size(), inputImage.type());
        Mat gray0 = new Mat(inputImage.size(), 0);
        Imgproc.pyrDown(inputImage, mat, mat.size());
        Imgproc.pyrUp(mat, inputImage, inputImage.size());
        extractChannel(inputImage, gray, 1);
        for (int l = 0; l < this.N; l++) {
            Imgproc.threshold(gray, gray0, (double) (((l + 1) * LoaderCallbackInterface.INIT_FAILED) / this.N), 255.0d, 0);
            List<MatOfPoint> contours = new LinkedList();
            Imgproc.findContours(gray0, contours, new Mat(), 1, 2);
            MatOfPoint approx = new MatOfPoint();
            for (int i = 0; i < contours.size(); i++) {
                approx = approxPolyDP((MatOfPoint) contours.get(i), Imgproc.arcLength(new MatOfPoint2f(((MatOfPoint) contours.get(i)).toArray()), true) * 0.02d, true);
                if (approx.toArray().length == 4 && Math.abs(Imgproc.contourArea(approx)) > 1000.0d && Imgproc.isContourConvex(approx)) {
                    double maxCosine = 0.0d;
                    for (int j = 2; j < 5; j++) {
                        double d = maxCosine;
                        maxCosine = Math.max(d, Math.abs(angle(approx.toArray()[j % 4], approx.toArray()[j - 2], approx.toArray()[j - 1])));
                    }
                    if (maxCosine < 0.3d) {
                        squares.add(approx);
                    }
                }
            }
        }
        return squares;
    }

    public void extractChannel(Mat source, Mat out, int channelNum) {
        List<Mat> sourceChannels = new ArrayList();
        List<Mat> outChannel = new ArrayList();
        Core.split(source, sourceChannels);
        outChannel.add(new Mat(((Mat) sourceChannels.get(0)).size(), ((Mat) sourceChannels.get(0)).type()));
        Core.mixChannels(sourceChannels, outChannel, new MatOfInt(channelNum, 0));
        Core.merge(outChannel, out);
    }

    public MatOfPoint approxPolyDP(MatOfPoint curve, double epsilon, boolean closed) {
        MatOfPoint2f tempMat = new MatOfPoint2f();
        Imgproc.approxPolyDP(new MatOfPoint2f(curve.toArray()), tempMat, epsilon, closed);
        return new MatOfPoint(tempMat.toArray());
    }

    public double angle(Point pt1, Point pt2, Point pt0) {
        double dx1 = pt1.x - pt0.x;
        double dy1 = pt1.y - pt0.y;
        double dx2 = pt2.x - pt0.x;
        double dy2 = pt2.y - pt0.y;
        return ((dx1 * dx2) + (dy1 * dy2)) / Math.sqrt((((dx1 * dx1) + (dy1 * dy1)) * ((dx2 * dx2) + (dy2 * dy2))) + 1.0E-10d);
    }
}
