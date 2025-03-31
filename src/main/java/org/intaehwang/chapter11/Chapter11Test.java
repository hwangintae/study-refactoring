package org.intaehwang.chapter11;

import java.util.List;

public class Chapter11Test {

    public void alertForMiscreant(List<String> people) {
        for (String p : people) {
            if ("조커".equals(p)) {
                setOfAlarms();
                return;
            }
            if ("사루만".equals(p)) {
                setOfAlarms();
                return;
            }
        }
    }

    public String findMiscreant(List<String> people) {
        for (String p : people) {
            if ("조커".equals(p)) {
                return "조커";
            }
            if ("사루만".equals(p)) {
                return "사루만";
            }
        }

        return "";
    }

    public void setOfAlarms() {

    }
}
