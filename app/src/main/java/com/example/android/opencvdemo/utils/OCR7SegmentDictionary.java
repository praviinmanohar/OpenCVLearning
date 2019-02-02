package com.example.android.opencvdemo.utils;

import java.util.Map;

public interface OCR7SegmentDictionary {
    Map<String, Integer> GetAllElements();

    Boolean UpdateElement(String str, Integer num);

    String evaluateDictionary();

    void fillDictionary();

    void restartDictionary();
}
