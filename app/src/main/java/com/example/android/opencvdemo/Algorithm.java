package com.example.android.opencvdemo;

import android.content.Context;
import android.graphics.Bitmap;

import org.opencv.android.Utils;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import java.io.IOException;

public class Algorithm {

    Context mContext;

    String DIGITS_LOOKUP = "{\n" +
            "\n" +
            "\"0\":[1, 1, 1, 0, 1, 1, 1],\n" +
            "\"1\":[0, 0, 1, 0, 0, 1, 0],\n" +
            "\"2\":[1, 0, 1, 1, 1, 1, 0],\n" +
            "\"3\":[1, 0, 1, 1, 0, 1, 1],\n" +
            "\"4\":[0, 1, 1, 1, 0, 1, 0],\n" +
            "\"5\":[1, 1, 0, 1, 0, 1, 1],\n" +
            "\"6\":[1, 1, 0, 1, 1, 1, 1],\n" +
            "\"7\":[1, 0, 1, 0, 0, 1, 0],\n" +
            "\"8\":[1, 1, 1, 1, 1, 1, 1],\n" +
            "\"9\":[1, 1, 1, 1, 0, 1, 1]\n" +
            "\n" +
            "}";

  Algorithm(Context context){

      this.mContext = context;
  }

  public static Bitmap getBitMap(Context mContext){
      Bitmap b = null;
      Mat img = null;
      try {
          img = Utils.loadResource(mContext, R.drawable.omron, CvType.CV_8UC4);
          Mat r = img.clone();
           b = Bitmap.createBitmap(r.cols(), r.rows(),Bitmap.Config.ARGB_8888);
          Utils.bitmapToMat(b, img);
          Imgproc.cvtColor(img, img, Imgproc.COLOR_RGB2GRAY);
      } catch (IOException e) {
          e.printStackTrace();
      }
      return b;
  }



}
