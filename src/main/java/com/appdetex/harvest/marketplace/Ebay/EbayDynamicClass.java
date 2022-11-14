package com.appdetex.harvest.marketplace.Ebay;

import com.gargoylesoftware.htmlunit.html.HtmlElement;

import java.util.HashMap;

public class EbayDynamicClass {

    public String getClassValue(HtmlElement spanSponsored) {
        String classValue;
        String gettingToClassValue;
        String stringValueOfSpanSponsoredPath = String.valueOf(spanSponsored);
        //System.out.println("HTML XPATH: " + stringValueOfSpanSponsoredPath);
        String takeOutEmptyAndEqual = stringValueOfSpanSponsoredPath.replace(" ", "").replace("=", "");

        try {
            gettingToClassValue = takeOutEmptyAndEqual.substring(takeOutEmptyAndEqual.indexOf("class") + 6, takeOutEmptyAndEqual.indexOf("role"));
            classValue = gettingToClassValue.substring(0, gettingToClassValue.length() - 1);
        } catch (StringIndexOutOfBoundsException e) {
            return null;
        }
        return classValue;
    }

    static void assignValueAndKey(HashMap<String, Integer> sponsoredClassNames, String classValue) {
        if (sponsoredClassNames.containsKey(classValue)) {
            sponsoredClassNames.put(classValue, sponsoredClassNames.get(classValue) + 1);
        } else {
            sponsoredClassNames.put(classValue, 1);
        }
    }

    public String countingClassTimes(HashMap<String, Integer> hashMap) {
        Integer smallestValue = 100;
        String smallestKey = null;

        for (java.util.Map.Entry<String, Integer> entry : hashMap.entrySet()) {
            if (entry.getValue() < smallestValue) {
                smallestKey = entry.getKey();
                smallestValue = entry.getValue();
            }
        }
        //ystem.out.println("\n\tThe smallest is: " + smallestKey + " with " + smallestValue + "\n\n");
        return smallestKey;
    }
}
