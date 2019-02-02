/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.graphics.Rect
 *  android.hardware.Camera
 *  android.hardware.Camera$Area
 *  android.hardware.Camera$AutoFocusCallback
 *  android.hardware.Camera$CameraInfo
 *  android.hardware.Camera$Parameters
 *  android.hardware.Camera$Size
 *  android.util.AttributeSet
 *  android.util.Log
 *  android.view.Display
 *  android.view.WindowManager
 *  android.widget.Toast
 *  java.lang.CharSequence
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.StringBuilder
 *  java.util.ArrayList
 *  java.util.List
 */
package com.example.android.opencvdemo.trail;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.hardware.Camera;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import org.opencv.android.JavaCameraView;

public class JavaCamResView
extends JavaCameraView {
    public JavaCamResView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public List<Camera.Size> getResolutionList() {
        return this.mCamera.getParameters().getSupportedPreviewSizes();
    }

    public boolean isValidFocusMode(Context context, int n) {
        List list = this.mCamera.getParameters().getSupportedFocusModes();
        switch (n) {
            default: {
                break;
            }
            case 6: {
                if (list.contains((Object)"continuous-picture")) break;
                return false;
            }
            case 5: {
                if (list.contains((Object)"macro")) break;
                return false;
            }
            case 4: {
                if (list.contains((Object)"infinity")) break;
                return false;
            }
            case 3: {
                if (list.contains((Object)"fixed")) break;
                return false;
            }
            case 2: {
                if (list.contains((Object)"edof")) break;
                return false;
            }
            case 1: {
                if (list.contains((Object)"continuous-video")) break;
                return false;
            }
            case 0: {
                if (list.contains((Object)"auto")) break;
                return false;
            }
        }
        return true;
    }

    public int setCameraDisplayOrientation(Activity activity) {
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        Camera.getCameraInfo((int)0, (Camera.CameraInfo)cameraInfo);
        int n = activity.getWindowManager().getDefaultDisplay().getRotation();
        int n2 = 0;
        switch (n) {
            default: {
                n2 = 0;
                break;
            }
            case 3: {
                n2 = 270;
                break;
            }
            case 2: {
                n2 = 180;
                break;
            }
            case 1: {
                n2 = 90;
            }
            case 0: 
        }
        if (cameraInfo.facing == 1) {
            return (360 - (n2 + cameraInfo.orientation) % 360) % 360;
        }
        return (360 + (cameraInfo.orientation - n2)) % 360;
    }

    public int setExpCompensation(boolean bl) {
        Camera.Parameters parameters = this.mCamera.getParameters();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("MaxExposureCompensation: ");
        stringBuilder.append(parameters.getMaxExposureCompensation());
        Log.d((String)"setExpCompensation", (String)stringBuilder.toString());
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append("MinExposureCompensation: ");
        stringBuilder2.append(parameters.getMinExposureCompensation());
        Log.d((String)"setExpCompensation", (String)stringBuilder2.toString());
        StringBuilder stringBuilder3 = new StringBuilder();
        stringBuilder3.append("ExposureCompensation: ");
        stringBuilder3.append(parameters.getExposureCompensation());
        Log.d((String)"setExpCompensation", (String)stringBuilder3.toString());
        if (bl) {
            parameters.setExposureCompensation(5 * parameters.getMinExposureCompensation() / 6);
        } else {
            parameters.setExposureCompensation(0);
        }
        StringBuilder stringBuilder4 = new StringBuilder();
        stringBuilder4.append("ExposureCompensationStep: ");
        stringBuilder4.append(parameters.getExposureCompensationStep());
        Log.d((String)"setExpCompensation", (String)stringBuilder4.toString());
        this.mCamera.setParameters(parameters);
        return 0;
    }

    public boolean setFocusArea(Context context, Rect rect) {
        Camera.Parameters parameters = this.mCamera.getParameters();
        int n = parameters.getMaxNumFocusAreas();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("MaxNumFocusAreas=");
        stringBuilder.append(n);
        Log.d((String)"setFocusArea", (String)stringBuilder.toString());
        if (n > 0) {
            ArrayList arrayList = new ArrayList();
            arrayList.add((Object)new Camera.Area(rect, 1000));
            parameters.setFocusAreas((List)arrayList);
            this.mCamera.setParameters(parameters);
            return true;
        }
        return false;
    }

    public boolean setFocusMode(Context context, int n) {
        Camera.Parameters parameters;
        boolean bl;
        block17 : {
            parameters = this.mCamera.getParameters();
            List list = parameters.getSupportedFocusModes();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("mode=");
            stringBuilder.append((Object)list);
            Log.d((String)"setFocusMode", (String)stringBuilder.toString());
            switch (n) {
                default: {
                    break;
                }
                case 6: {
                    if (list.contains((Object)"continuous-picture")) {
                        parameters.setFocusMode("continuous-picture");
                        break;
                    }
                    Toast.makeText((Context)context, (CharSequence)"CONTINUOUS_PICTURE Focus Mode not supported", (int)1).show();
                    bl = false;
                    break block17;
                }
                case 5: {
                    if (list.contains((Object)"macro")) {
                        parameters.setFocusMode("macro");
                        break;
                    }
                    Toast.makeText((Context)context, (CharSequence)"Macro Focus Mode not supported", (int)1).show();
                    bl = false;
                    break block17;
                }
                case 4: {
                    if (list.contains((Object)"infinity")) {
                        parameters.setFocusMode("infinity");
                        break;
                    }
                    Toast.makeText((Context)context, (CharSequence)"Infinity Mode not supported", (int)1).show();
                    bl = false;
                    break block17;
                }
                case 3: {
                    if (list.contains((Object)"fixed")) {
                        parameters.setFocusMode("fixed");
                        break;
                    }
                    Toast.makeText((Context)context, (CharSequence)"Fixed Focus Mode not supported", (int)1).show();
                    bl = false;
                    break block17;
                }
                case 2: {
                    if (list.contains((Object)"edof")) {
                        parameters.setFocusMode("edof");
                        break;
                    }
                    Toast.makeText((Context)context, (CharSequence)"EDOF Focus Mode not supported", (int)1).show();
                    bl = false;
                    break block17;
                }
                case 1: {
                    if (list.contains((Object)"continuous-video")) {
                        parameters.setFocusMode("continuous-video");
                        break;
                    }
                    Toast.makeText((Context)context, (CharSequence)"Continuous Focus Mode not supported", (int)1).show();
                    bl = false;
                    break block17;
                }
                case 0: {
                    if (list.contains((Object)"auto")) {
                        parameters.setFocusMode("auto");
                        break;
                    }
                    Toast.makeText((Context)context, (CharSequence)"Auto Focus Mode not supported", (int)1).show();
                    bl = false;
                    break block17;
                }
            }
            bl = true;
        }
        this.mCamera.setParameters(parameters);
        if (n != 0 && n != 2 && n != 5 && n != 6) {
            this.mCamera.cancelAutoFocus();
            return bl;
        }
        this.mCamera.autoFocus(new Camera.AutoFocusCallback(){

            public void onAutoFocus(boolean bl, Camera camera) {
            }
        });
        return bl;
    }

    public boolean setMeteringArea(Context context, Rect rect) {
        Camera.Parameters parameters = this.mCamera.getParameters();
        if (parameters.getMaxNumMeteringAreas() > 0) {
            ArrayList arrayList = new ArrayList();
            arrayList.add((Object)new Camera.Area(rect, 1000));
            parameters.setMeteringAreas((List)arrayList);
            this.mCamera.setParameters(parameters);
            return true;
        }
        return false;
    }

    public void setPreviewFPS(int n) {
        Camera.Parameters parameters = this.mCamera.getParameters();
        List list = parameters.getSupportedPreviewFpsRange();
        if (list != null) {
            for (int i = 0; i < list.size(); ++i) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("FpsRange = ");
                stringBuilder.append(((int[])list.get(i))[0]);
                stringBuilder.append(" ");
                stringBuilder.append(((int[])list.get(i))[1]);
                Log.i((String)"setPreviewFPS", (String)stringBuilder.toString());
            }
        }
        List list2 = parameters.getSupportedPreviewFpsRange();
        int n2 = n == 0 ? 0 : list2.size() - 1;
        int n3 = ((int[])list2.get(n2))[0];
        int n4 = ((int[])list2.get(n2))[1];
        parameters.setPreviewFpsRange(n3, n4);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("preview fps: ");
        stringBuilder.append(n3);
        stringBuilder.append(", ");
        stringBuilder.append(n4);
        Log.i((String)"setPreviewFPS", (String)stringBuilder.toString());
        this.mCamera.setParameters(parameters);
        List list3 = parameters.getSupportedPreviewSizes();
        if (list3 != null) {
            for (int i = 0; i < list3.size(); ++i) {
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append("SupportedPreviewSizes = ");
                stringBuilder2.append(((Camera.Size)list3.get((int)i)).width);
                stringBuilder2.append("x");
                stringBuilder2.append(((Camera.Size)list3.get((int)i)).height);
                Log.i((String)"setPreviewFPS", (String)stringBuilder2.toString());
            }
        }
        List list4 = parameters.getSupportedPictureSizes();
        if (list4 != null) {
            for (int i = 0; i < list4.size(); ++i) {
                StringBuilder stringBuilder3 = new StringBuilder();
                stringBuilder3.append("PictureSizes = ");
                stringBuilder3.append(((Camera.Size)list4.get((int)i)).width);
                stringBuilder3.append("x");
                stringBuilder3.append(((Camera.Size)list4.get((int)i)).height);
                Log.i((String)"setPreviewFPS", (String)stringBuilder3.toString());
            }
        }
        boolean bl = parameters.isZoomSupported();
        int n5 = parameters.getMaxZoom();
        int n6 = parameters.getZoom();
        StringBuilder stringBuilder4 = new StringBuilder();
        stringBuilder4.append("zoomsuported MaxZoom Zoom= ");
        stringBuilder4.append(bl);
        stringBuilder4.append(" ");
        stringBuilder4.append(n5);
        stringBuilder4.append(" ");
        stringBuilder4.append(n6);
        Log.i((String)"setPreviewFPS", (String)stringBuilder4.toString());
    }

    public void setResolution(Camera.Size size) {
        this.disconnectCamera();
        this.connectCamera(size.width, size.height);
    }

}

