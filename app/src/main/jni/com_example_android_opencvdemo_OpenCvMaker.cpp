#include <com_example_android_opencvdemo_OpenCvMaker.h>

JNIEXPORT jint JNICALL Java_com_example_android_opencvdemo_OpenCvMaker_makeGray
  (JNIEnv *, jclass, jlong addrInput, jlong addrOutput){
  return (jint) toGray((*(Mat*) addrInput),(*(Mat*) addrOutput));
  }

  int toGray(Mat img, Mat& gray) {
    cvtColor(img, gray,CV_RGBA2GRAY);
    if(gray.rows == img.rows && gray.cols == img.cols)
        return 1;
    return 0;
  }

JNIEXPORT jint JNICALL Java_com_example_android_opencvdemo_OpenCvMaker_makeCanny
  (JNIEnv *, jclass, jlong addrInput, jlong addrOutput){
  return (jint) toCanny((*(Mat*) addrInput),(*(Mat*) addrOutput));
  }

  int toCanny(Mat input, Mat& output) {
    Canny(input,output,75,150,3,false);
    if(output.rows == input.rows && output.cols == input.cols)
       return 1;
   return 0;
  }

JNIEXPORT jint JNICALL Java_com_example_android_opencvdemo_OpenCvMaker_makeDilate
  (JNIEnv *, jclass, jlong addrInput, jlong addrOutput){
  return (jint) toDilate((*(Mat*) addrInput),(*(Mat*) addrOutput));
  }

  int toDilate(Mat input, Mat& output){
    dilate(input,output,Mat(), Point(-1,-1),2,1,1);
    if(output.rows == input.rows && output.cols == input.cols)
       return 1;
  return 0;
  }

JNIEXPORT jint JNICALL Java_com_example_android_opencvdemo_OpenCvMaker_makeErode
  (JNIEnv *, jclass, jlong addrInput, jlong addrOutput){
  return (jint) toErode((*(Mat*) addrInput),(*(Mat*) addrOutput));
  }

  int toErode(Mat input, Mat& output){
    erode(input,output,Mat(), Point(-1,-1),2,1,1);
    if(output.rows == input.rows && output.cols == input.cols)
       return 1;
    return 0;
  }