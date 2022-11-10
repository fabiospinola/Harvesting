package com.appdetex.harvest.marketplace;

import com.gargoylesoftware.htmlunit.html.HtmlElement;

import java.util.HashMap;

public class EbayDynamicClass {

    public String getClassValue(HtmlElement spanSponsored) {
        String classValue;
        String gettingToClassValue;
        String stringValueOfSpanSponsoredPath = String.valueOf(spanSponsored);
        String takeOutEmptyAndEqual = stringValueOfSpanSponsoredPath.replace(" ", "").replace("=", "");

        try {
            gettingToClassValue = takeOutEmptyAndEqual.substring(takeOutEmptyAndEqual.indexOf("class") + 6, takeOutEmptyAndEqual.indexOf("role"));
            classValue = gettingToClassValue.substring(0, gettingToClassValue.length() - 1);
        } catch (StringIndexOutOfBoundsException e) {
            return null;
        }
        return classValue;
    }

    public void assignValueAndKey(String classValue, HashMap<String, Integer> hashMap) {
        if (hashMap.containsKey(classValue)) {
            hashMap.put(classValue, hashMap.get(classValue) + 1);
        } else {
            hashMap.put(classValue, 1);
        }
    }

    public String countingClassTimes(HashMap<String, Integer> hashMap) {
        Integer smallestValue = 100;
        String smallestKey = null;

        for (var entry : hashMap.entrySet()) {
            if (entry.getValue() < smallestValue) {
                smallestKey = entry.getKey();
                smallestValue = entry.getValue();
            }
        }
        System.out.println("\tThe smallest is: " + smallestKey + " with " + smallestValue + "\n\n");
        return smallestKey;
    }


}
