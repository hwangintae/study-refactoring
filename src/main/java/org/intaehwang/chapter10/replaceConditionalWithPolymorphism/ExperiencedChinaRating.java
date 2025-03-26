package org.intaehwang.chapter10.replaceConditionalWithPolymorphism;

import java.util.List;

public class ExperiencedChinaRating extends Rating{
    private final Voyage voyage;
    private final List<History> histories;

    public ExperiencedChinaRating(Voyage voyage, List<History> histories) {
        super(voyage, histories);
        this.voyage = voyage;
        this.histories = histories;
    }

    @Override
    public int captainHistoryRisk() {
        int result = super.captainHistoryRisk() - 2;

        return Math.max(result, 0);
    }

    @Override
    public int voyageProfitFactor() {
        return super.voyageProfitFactor() + 3;
    }

    @Override
    public int voyageLengthFactor() {
        int result = 0;

        if (voyage.getLength() > 12) result += 1;
        if (voyage.getLength() > 18) result -= 1;

        return result;
    }

    @Override
    public int historyLengthFactor() {
        int result = 0;
        if (histories.size() > 10) result += 1;
        return result;
    }
}
