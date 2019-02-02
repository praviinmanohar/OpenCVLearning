package com.example.android.opencvdemo.utils;

import java.util.HashMap;
import java.util.Map;

public class OCR7SegmentDictionaryImpl implements OCR7SegmentDictionary {
    private static final Integer ITEM_THRESHOLD = Integer.valueOf(5);
    private Map<String, Integer> dictionary = new HashMap();

    public OCR7SegmentDictionaryImpl() {
        fillDictionary();
    }

    public Boolean UpdateElement(String element, Integer value) {
        if (!this.dictionary.containsKey(element)) {
            return Boolean.valueOf(false);
        }
        this.dictionary.put(element, Integer.valueOf(((Integer) this.dictionary.get(element)).intValue() + value.intValue()));
        return Boolean.valueOf(true);
    }

    public Map<String, Integer> GetAllElements() {
        return this.dictionary;
    }

    public void fillDictionary() {
        this.dictionary.put("1200", Integer.valueOf(0));
        this.dictionary.put("1400", Integer.valueOf(0));
        this.dictionary.put("1600", Integer.valueOf(0));
        this.dictionary.put("1800", Integer.valueOf(0));
        this.dictionary.put("2000", Integer.valueOf(0));
        this.dictionary.put("800", Integer.valueOf(0));
        this.dictionary.put("600", Integer.valueOf(0));
        this.dictionary.put("400", Integer.valueOf(0));
        this.dictionary.put("200", Integer.valueOf(0));
        this.dictionary.put("60", Integer.valueOf(0));
        this.dictionary.put("80", Integer.valueOf(0));
        this.dictionary.put("100", Integer.valueOf(0));
        this.dictionary.put("120", Integer.valueOf(0));
        this.dictionary.put("140", Integer.valueOf(0));
        this.dictionary.put("160", Integer.valueOf(0));
        this.dictionary.put("180", Integer.valueOf(0));
        this.dictionary.put("200", Integer.valueOf(0));
        this.dictionary.put("220", Integer.valueOf(0));
        this.dictionary.put("240", Integer.valueOf(0));
        this.dictionary.put("E0", Integer.valueOf(0));
        this.dictionary.put("5", Integer.valueOf(0));
        this.dictionary.put("10", Integer.valueOf(0));
        this.dictionary.put("15", Integer.valueOf(0));
        this.dictionary.put("20", Integer.valueOf(0));
        this.dictionary.put("25", Integer.valueOf(0));
        this.dictionary.put("30", Integer.valueOf(0));
        this.dictionary.put("35", Integer.valueOf(0));
        this.dictionary.put("40", Integer.valueOf(0));
        this.dictionary.put("45", Integer.valueOf(0));
        this.dictionary.put("50", Integer.valueOf(0));
        this.dictionary.put("55", Integer.valueOf(0));
        this.dictionary.put("65", Integer.valueOf(0));
        this.dictionary.put("70", Integer.valueOf(0));
        this.dictionary.put("75", Integer.valueOf(0));
        this.dictionary.put("85", Integer.valueOf(0));
        this.dictionary.put("90", Integer.valueOf(0));
        this.dictionary.put("95", Integer.valueOf(0));
        this.dictionary.put("105", Integer.valueOf(0));
        this.dictionary.put("110", Integer.valueOf(0));
        this.dictionary.put("115", Integer.valueOf(0));
        this.dictionary.put("125", Integer.valueOf(0));
        this.dictionary.put("130", Integer.valueOf(0));
        this.dictionary.put("135", Integer.valueOf(0));
        this.dictionary.put("145", Integer.valueOf(0));
        this.dictionary.put("150", Integer.valueOf(0));
        this.dictionary.put("155", Integer.valueOf(0));
        this.dictionary.put("165", Integer.valueOf(0));
        this.dictionary.put("170", Integer.valueOf(0));
        this.dictionary.put("175", Integer.valueOf(0));
    }

    public void restartDictionary() {
        this.dictionary.clear();
        fillDictionary();
    }

    public String evaluateDictionary() {
        String ret = "";
        for (String s : this.dictionary.keySet()) {
            if (((Integer) this.dictionary.get(s)).intValue() >= ITEM_THRESHOLD.intValue()) {
                return s;
            }
        }
        return ret;
    }
}
