package org.intaehwang.chapter10.replaceConditionalWithPolymorphism;

import java.util.List;

public class Rating {
    private final Voyage voyage;
    private final List<History> histories;

    public Rating(Voyage voyage, List<History> histories) {
        this.voyage = voyage;
        this.histories = histories;
    }

    public String value() {
        int vpf = voyageProfitFactor();
        int vr = voyageRisk();
        int chr = captainHistoryRisk();

        if (vpf * 3 > (vr + chr * 2)) return "A";
        else return "B";
    }

    public int voyageRisk() {
        int result = 1;

        if (voyage.getLength() > 4) result += 2;
        if (voyage.getLength() > 8) result += voyage.getLength() - 8;
        if (List.of("중국", "동인도").contains(voyage.getZone())) result += 4;

        return Math.max(result, 0);
    }

    public int captainHistoryRisk() {
        int result = 1;

        if (histories.size() < 5) result += 4;
        result += histories.stream()
                .filter(v -> v.getProfit() < 0)
                .toList()
                .size();

        return Math.max(result, 0);

    }

    public boolean hasChinaHistory() {
        return histories.stream()
                .anyMatch(v -> "중국".equals(v.getZone()));
    }

    public int voyageProfitFactor() {
        int result = 2;

        if ("중국".equals(voyage.getZone())) result += 1;
        if ("동인도".equals(voyage.getZone())) result += 1;
        result += historyLengthFactor();
        result += voyageLengthFactor();

        return result;
    }

    public int voyageLengthFactor() {
        int result = 0;

        if (voyage.getLength() > 14) result -= 1;

        return result;
    }

    public int historyLengthFactor() {
        int result = 0;

        if (histories.size() > 8) result += 1;

        return result;
    }
}
