package com.example.android.opencvdemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.CvException;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfDouble;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import static org.opencv.imgcodecs.Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE;
import static org.opencv.imgcodecs.Imgcodecs.imread;

public class AlgorithmActivity extends AppCompatActivity {

    ImageView imageView;
    TextView textView;

    private static final String TEST_PATH = Environment.getExternalStorageDirectory()
            .getAbsolutePath() + "/Test/";

    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS: {
                    Log.i("TAG", "OpenCV loaded successfully");
//                    mOpenCvCameraView.enableView();
//                    mOpenCvCameraView.setOnTouchListener(ColorBlobDetectionActivity.this);
                }
                break;
                default: {
                    super.onManagerConnected(status);
                }
                break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_algorithm);

        imageView = (ImageView) findViewById(R.id.imageView2);
        textView = (TextView) findViewById(R.id.textView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!OpenCVLoader.initDebug()) {
            Log.d("TAG", "Internal OpenCV library not found. Using OpenCV Manager for initialization");
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_0_0, this, mLoaderCallback);
        } else {
            Log.d("TAG", "OpenCV library found inside package. Using it!");
            mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        }
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

        Bitmap b = BitmapFactory.decodeResource(this.getResources(),
                R.drawable.example);
        Mat origImageMatrix = new Mat(b.getWidth(), b.getHeight(), CvType.CV_8UC3);

        Utils.bitmapToMat(b,origImageMatrix);

        Imgproc.cvtColor(origImageMatrix, origImageMatrix, Imgproc.COLOR_BGR2GRAY);

        Imgproc.Canny(origImageMatrix, origImageMatrix, 10, 100, 3, true);

        Utils.matToBitmap(origImageMatrix, b);

        List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
        Imgproc.findContours(origImageMatrix, contours, new Mat(), Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE, new Point(0, 0));

        Collections.reverse(contours);
        // Minimum size allowed for consideration
        MatOfPoint2f approxCurve = new MatOfPoint2f();

        Rect rect = new Rect();

        for (int contourIdx = 0; contourIdx < contours.size(); contourIdx++) {

            MatOfPoint2f contour2f = new MatOfPoint2f(contours.get(contourIdx).toArray());

            //Processing on mMOP2f1 which is in type MatOfPoint2f
            double approxDistance = Imgproc.arcLength(contour2f, true) * 0.02;

            //Detect contours
            Imgproc.approxPolyDP(contour2f, approxCurve, approxDistance, true);

            //Convert back to MatOfPoint
            MatOfPoint points = new MatOfPoint(approxCurve.toArray());

            // Get bounding rect of contour
            rect = Imgproc.boundingRect(points);

            Log.e("TAG", ""+rect.height);



            //Draw rectangle for the detected countour on the original image
            /*Imgproc.rectangle(mRGBa, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height),
                    new Scalar(255, 0, 255, 255), 2);*/
        }

        imageView.setImageBitmap(b);
    }



}

/*Mat src = imread("your image"); int row = src.rows; int col = src.cols;
    //Create contour
vector<vector<Point> > contours;
vector<Vec4i> hierarchy;
Mat src_copy = src.clone();
    findContours( src_copy, contours, hierarchy, RETR_TREE, CHAIN_APPROX_SIMPLE);

// Create Mask
Mat_<uchar> mask(row,col);
for (int j=0; j<row; j++)
    for (int i=0; i<col; i++)
        {
            if ( pointPolygonTest( contours[0], Point2f(i,j),false) =0)
            {mask(j,i)=255;}
            else
            {mask(j,i)=0;}
        };*/


/*List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
        Mat hierarchy = new Mat();

        Imgproc.findContours(origImageMatrix, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

        hierarchy.release();

        double maxArea = 0;
        MatOfPoint max_contour = new MatOfPoint();

        Iterator<MatOfPoint> iterator = contours.iterator();
        while (iterator.hasNext()){
            MatOfPoint contour = iterator.next();
            double area = Imgproc.contourArea(contour);
            if(area > maxArea){
                maxArea = area;
                max_contour = contour;
                Log.e("TAG",""+max_contour.toArray().length);
            }
        }*/


/*
mRGBa = inputFrame.rgba();

        //Remove noise from the image.
        Imgproc.medianBlur(mRGBa, mBlur, 5);

        //Convert image to HSV format
        Imgproc.cvtColor(mBlur, imHSV, Imgproc.COLOR_RGB2HSV);

        //Filter out Green coloured objects in the image.
        //TODO: This scale has to be optimized to detect on Greed colored LED
        //Core.inRange(imHSV, new Scalar(50, 100, 100, 100), new Scalar(70, 225, 255, 255), imHSV);

        //Remove noise from the image.
        Imgproc.GaussianBlur(imHSV, imHSV, new Size(9, 9), 2, 2);

        //Convert the image from HSV to Gray scale image.
        imHSV.convertTo(imGray, CvType.CV_8U);

        //Apply threshold to the gray scale image to remove unnecessay objects from image.
        Imgproc.threshold(imGray, imGray, 50, 255, Imgproc.THRESH_BINARY);

        //Perform edge detection the image
        Imgproc.Canny(imGray, imGray, 100, 200);

        //Find Contours(closed shapes) in the image. (Detected LED will be a contour)
        List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
        Imgproc.findContours(imGray, contours, new Mat(), Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE, new Point(0, 0));

        // For each detected contour(closed shape) Find the co-ordinates of the countour and draw a rectangle on the input image.
        //TODO: For each Contour detected, below logic can be changed to detect countours having only 4 edges(rectangle/square).
        for (int contourIdx = 0; contourIdx < contours.size(); contourIdx++) {
            // Minimum size allowed for consideration
            MatOfPoint2f approxCurve = new MatOfPoint2f();

            MatOfPoint2f contour2f = new MatOfPoint2f(contours.get(contourIdx).toArray());

            //Processing on mMOP2f1 which is in type MatOfPoint2f
            double approxDistance = Imgproc.arcLength(contour2f, true) * 0.02;

            //Detect contours
            Imgproc.approxPolyDP(contour2f, approxCurve, approxDistance, true);

            //Convert back to MatOfPoint
            MatOfPoint points = new MatOfPoint(approxCurve.toArray());

            // Get bounding rect of contour
            Rect rect = Imgproc.boundingRect(points);

            //Draw rectangle for the detected countour on the original image
            Imgproc.rectangle(mRGBa, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height),
                    new Scalar(255, 0, 255, 255), 2);
        }
        return  mRGBa;
*/