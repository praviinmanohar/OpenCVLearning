package com.example.android.opencvdemo;

import org.opencv.core.Mat;

public class NativeMarkerDetector {

    public native int getMarker();

    /*private long mNativeObj;

    public void doOcr(Mat imageBgra, float scale) {
        Mat transformationsMat = new Mat();
        nativeFindMarkers(mNativeObj, imageBgra.nativeObj, transformationsMat.nativeObj, scale);
    }

    private native void nativeFindMarkers(long thiz, long imageBgra, long transformations, float scale);
*/
}
