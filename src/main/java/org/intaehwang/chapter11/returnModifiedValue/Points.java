package org.intaehwang.chapter11.returnModifiedValue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Points {
    private final List<Double> points = new ArrayList<>();
    
    public Points(Double... values) {
        points.addAll(Arrays.asList(values));
    }
    
    public int length() {
        return points.size();
    }

    public double get(int index) {
        return points.get(index);
    }
}
