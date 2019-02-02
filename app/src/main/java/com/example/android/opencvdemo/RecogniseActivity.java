package com.example.android.opencvdemo;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.android.opencvdemo.trail.CMyTessTwo;
import com.example.android.opencvdemo.trail.RecordingDatas;
import com.example.android.opencvdemo.utils.OCR7SegmentDictionary;
import com.example.android.opencvdemo.utils.OCR7SegmentDictionaryImpl;
import com.example.android.opencvdemo.utils.OCR7SegmentImageEnhacement;
import com.example.android.opencvdemo.utils.OCR7SegmentImageEnhacementImpl;
import com.example.android.opencvdemo.utils.OCR7SegmentRoiDetection;
import com.example.android.opencvdemo.utils.OCR7SegmentRoiDetectionImpl;
import com.googlecode.tesseract.android.TessBaseAPI;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.InstallCallbackInterface;
import org.opencv.android.JavaCamera2View;
import org.opencv.android.JavaCameraView;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.DMatch;
import org.opencv.core.KeyPoint;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfDMatch;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import static android.graphics.Color.GREEN;
import static android.graphics.Color.RED;
import static com.example.android.opencvdemo.MainActivity.CAMERA;
import static com.example.android.opencvdemo.MainActivity.READ_EXST;
import static com.example.android.opencvdemo.MainActivity.WRITE_EXST;
import static org.opencv.core.Core.FONT_HERSHEY_SIMPLEX;

public class RecogniseActivity extends AppCompatActivity implements CameraBridgeViewBase.CvCameraViewListener2 {

    private static final String TAG = RecogniseActivity.class.getSimpleName();
    //CameraBridgeViewBase cameraView;
    JavaCameraViewEx cameraView;
    Mat mRgba, mGray, mCanny,mRGBa,mBlur,imHSV,imGray;
    private DigitRecognizer mnist;

    private int mWidth;
    private int mHeight;
    private float mScale;

    private NativeMarkerDetector mNativeOcr;

    private static final String DATA_PATH = new StringBuilder(String.valueOf(Environment.getExternalStorageDirectory().toString())).append("/SimpleAndroidOCR/").toString();
    private static final Scalar FACE_RECT_COLOR = new Scalar(0.0d, 255.0d, 0.0d, 255.0d);

    private static final String lang = "7seg";
    int N = 5;
    protected EditText _field;
    ImageView imageView;
    private OCR7SegmentDictionary dictionary = new OCR7SegmentDictionaryImpl();

    Boolean rebootapp = Boolean.valueOf(false);
    List<MatOfPoint> squares = new ArrayList();
    private TextToSpeech textToSpeech;
    int thresh = 50;

    static {
        if (!(OpenCVLoader.initDebug())) {
            Log.d(TAG, "  OpenCVLoader.initDebug(), working.");
        } else {
            Log.d(TAG, "  OpenCVLoader.initDebug(), not working.");
        }
    }

    BaseLoaderCallback mLoaderCallBack = new BaseLoaderCallback(this) {

        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case BaseLoaderCallback.SUCCESS:
                    cameraView.enableView();
                    break;
                default:
                    super.onManagerConnected(status);
                    break;
            }
        }

        //Disable OpenCVManager installation
        @Override
        public void onPackageInstall(final int operation, final InstallCallbackInterface callback) {
            switch (operation) {
                case InstallCallbackInterface.NEW_INSTALLATION: {
                    Log.i(TAG, "Tried to install OpenCV Manager package, but I still don't believe that you need it...");
                    break;
                }
                default: {
                    super.onPackageInstall(operation, callback);
                    break;
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON | WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_recognise);

        mWidth = getResources().getDimensionPixelSize(R.dimen.view_width);
        mHeight = getResources().getDimensionPixelSize(R.dimen.view_height);

        if (ContextCompat.checkSelfPermission(this, "android.permission.WRITE_EXTERNAL_STORAGE") != 0) {
            ActivityCompat.requestPermissions(this, new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, 1);
            this.rebootapp = Boolean.valueOf(true);
        }

        if (ContextCompat.checkSelfPermission(this, "android.permission.CAMERA") != 0) {
            ActivityCompat.requestPermissions(this, new String[]{"android.permission.CAMERA"}, 1);
            this.rebootapp = Boolean.valueOf(true);
        }

        imageView = (ImageView) findViewById(R.id.imageView);
        cameraView = (JavaCameraViewEx) findViewById(R.id.live_camera_frame);

        cameraView.setCameraIndex(CameraBridgeViewBase.CAMERA_ID_ANY);
        cameraView.setCvCameraViewListener(this);
        cameraView.setMaxFrameSize(mWidth, mHeight);
        //cameraView.setMaxFrameSize(640, 360);
        cameraView.disableView();

        String[] paths = new String[]{DATA_PATH, DATA_PATH + "tessdata/"};
        int length = paths.length;
        int i = 0;
        while (i < length) {
            File dir = new File(paths[i]);
            if (dir.exists() || dir.mkdirs()) {
                i++;
            } else {
                return;
            }
        }
        if (!new File(DATA_PATH + "tessdata/" + lang + ".traineddata").exists()) {
            try {
                InputStream in = getAssets().open("tessdata/7seg.traineddata");
                OutputStream out = new FileOutputStream(DATA_PATH + "tessdata/" + lang + ".traineddata");
                byte[] buf = new byte[1024];
                while (true) {
                    int len = in.read(buf);
                    if (len <= 0) {
                        in.close();
                        out.close();
                        break;
                    }
                    out.write(buf, 0, len);
                }
            } catch (IOException e) {
            }
        }

        this.dictionary.fillDictionary();
        //speak("Cargada interfaz de voz a español");
        if (this.rebootapp.booleanValue()) {
            finish();
        }

    }

    public void onInit(int status) {
        int i;
        int i2 = 1;
        if (status == -1) {
            i = 1;
        } else {
            i = 0;
        }
        if (status != -2) {
            i2 = 0;
        }
        if ((i2 | i) != 0) {
            Toast.makeText(this, "ERROR LANG_MISSING_DATA | LANG_NOT_SUPPORTED", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onCameraViewStarted(int width, int height) {
        mRgba = new Mat(height, width, CvType.CV_8UC3);
        mGray = new Mat(height, width, CvType.CV_8UC1);
        mCanny = new Mat(height, width, CvType.CV_8UC1);

        mRGBa= new Mat(height, width, CvType.CV_8UC3);
        mBlur= new Mat(height, width, CvType.CV_8UC3);
        imHSV = new Mat(height, width, CvType.CV_8UC3);
        imGray = new Mat(height, width, CvType.CV_8UC3);
    }

    @Override
    public void onCameraViewStopped() {
        mRgba.release();
    }

    @Override
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {

        OCR7SegmentRoiDetection RoiDetection = new OCR7SegmentRoiDetectionImpl();

        if (Math.random() > 0.9d) {
            this.squares = RoiDetection.findSquares(inputFrame.rgba().clone());

        }
        Mat image = inputFrame.rgba();
        Imgproc.drawContours(image, this.squares, -1, new Scalar(0.0d, 0.0d, 255.0d));
        if (!this.squares.isEmpty()) {

            for (MatOfPoint p : this.squares) {

                Mat imageROI = image.submat(Imgproc.boundingRect(p));

                int resolution = image.width() / image.height();
                double reductionFactorW = 1.0d;
                double reductionFactorH = 1.0d;

                if (image.size().width == 352.0d && image.height() == 288) {

                    reductionFactorW = 1.82d;
                    reductionFactorH = 1.68d;
                }

                if (((double) imageROI.height()) <= 172.0d / reductionFactorH && ((double) imageROI.height()) >= 44.0d / reductionFactorH && ((double) imageROI.width()) <= 380.0d / reductionFactorW && ((double) imageROI.width()) >= 95.0d / reductionFactorW) {

                    List<MatOfPoint> listaux = new LinkedList();

                    listaux.add(p);
                    Mat imageROI_prepared = prepareImage4OCR(imageROI, p);

                    Imgproc.drawContours(image, listaux, -1, FACE_RECT_COLOR, 2);

                    new BitmapFactory.Options().inSampleSize = 4;

                    Bitmap bitmap = Bitmap.createBitmap(imageROI_prepared.cols(), imageROI_prepared.rows(), Bitmap.Config.ARGB_8888);
                    Utils.matToBitmap(imageROI_prepared, bitmap);
                    try {
                        OutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(new File(DATA_PATH + "/ocr.png")));
                        bitmap.compress(Bitmap.CompressFormat.PNG, 0, bufferedOutputStream);
                        bufferedOutputStream.close();
                        Log.e("TAG","OCR.PNG");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (((double) ((((float) Core.countNonZero(imageROI_prepared)) / ((float) (imageROI_prepared.height() * imageROI_prepared.width()))) * 100.0f)) > 70.0d) {
                        Log.e("TAG","TessBase");
                        TessBaseAPI baseApi = new TessBaseAPI();
                        baseApi.setDebug(true);
                        baseApi.init(DATA_PATH, lang);
                        baseApi.setImage(bitmap);
                        String recognizedText = baseApi.getUTF8Text();
                        this.dictionary.UpdateElement(recognizedText, Integer.valueOf(1));
                        baseApi.end();
                        if (recognizedText.length() != 0) {
                            final String str = recognizedText;
                            runOnUiThread(new Runnable() {
                                public void run() {
                                    //this._field.setText(OCR7SegmentActivity.this._field.getText().toString().length() == 0 ? str : str);
                                    //OCR7SegmentActivity.this._field.setSelection(OCR7SegmentActivity.this._field.getText().toString().length());
                                   // textToSpeech.setLanguage(new Locale("esp", "ESP"));
                                    String value = dictionary.evaluateDictionary();
                                    if (!value.equals("")) {
                                        //this.speak(value);
                                        Log.e(TAG, "Value : "+value);
                                        //this.dictionary.restartDictionary();
                                    }
                                }
                            });
                        }
                    }
                }
            }
        }
        return image;


    }

    private Mat prepareImage4OCR(Mat rgb, MatOfPoint p) {
        OCR7SegmentImageEnhacement OCRImage = new OCR7SegmentImageEnhacementImpl();
        Mat ret = rgb.clone();
        Imgproc.cvtColor(ret, ret, 11);
        Imgproc.threshold(ret, ret, 0.0d, 255.0d, 9);
        Mat kernel = Mat.ones(new Size(2.0d, 2.0d), 0);
        Imgproc.medianBlur(ret, ret, 5);
        ret = OCRImage.deskew(ret);
        int cols_to_remove = (int) (((double) ret.cols()) * 0.05d);
        int rows_to_remove = (int) (((double) ret.rows()) * 0.05d);
        Mat retfinal = ret.submat(rows_to_remove, ret.rows() - rows_to_remove, cols_to_remove, ret.cols() - cols_to_remove);
        Imgproc.erode(retfinal, retfinal, kernel, new Point(), 1);
        return retfinal;
    }

    private void speak(String str) {
        this.textToSpeech.speak(str, 0, null);
        this.textToSpeech.setSpeechRate(0.0f);
        this.textToSpeech.setPitch(0.0f);
    }

    private Integer optimalThreshold(Mat imame) {
        return Integer.valueOf(0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_4_0, this, mLoaderCallBack)) {
            Log.e(TAG, "  OpenCVLoader.initAsync(), not working.");
            mLoaderCallBack.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        } else {
            Log.d(TAG, "  OpenCVLoader.initAsync(), working.");
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_4_0, this, mLoaderCallBack);
        }

        Bitmap bitmap= BitmapFactory.decodeResource(this.getResources(),
                R.drawable.omron);
        TessBaseAPI baseApi = new TessBaseAPI();
        baseApi.setDebug(true);
        baseApi.init(DATA_PATH, lang);
        baseApi.setImage(bitmap);
        String recognizedText = baseApi.getUTF8Text();
        Log.e(TAG,"recognizedText : "+recognizedText);

        checkMnist();
    }

    private void checkMnist() {

        Algorithm alg = new Algorithm(this);

        imageView.setImageBitmap(alg.getBitMap(this));

    }

    @Override
    public void onPause() {
        super.onPause();

        if (cameraView != null) {
            cameraView.disableView();
        }
    }



    public void onDestroy() {
        super.onDestroy();
        if (cameraView != null)
            cameraView.disableView();
    }

    @Override
    protected void onStart() {
        super.onStart();


    }

    private void detectHere(Mat CNN_input) {

    }


}
