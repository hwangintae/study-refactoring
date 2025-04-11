package org.intaehwang.chapter12.replaceSuperclassWithDelegate;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class Scroll {
    private CatalogItem catalogItem;
    private Instant dateLastCleaned;

    public Scroll(Long id, String title, List<String> tags, Instant dateLastCleaned) {
        this.catalogItem = new CatalogItem(id, title, tags);
        this.dateLastCleaned = dateLastCleaned;
    }

    public boolean needsCleaning(Instant targetDate) {
        long threshold = this.hasTag("revered") ? 700 : 1500;
        return this.daysSinceLastCleaning(targetDate) > threshold;
    }

    public long daysSinceLastCleaning(Instant targetDate) {
        return this.dateLastCleaned.until(targetDate, ChronoUnit.DAYS);
    }

    public Long getId() {
        return this.catalogItem.getId();
    }

    public String getTitle() {
        return this.catalogItem.getTitle();
    }

    public boolean hasTag(String arg) {
        return this.catalogItem.hasTag(arg);
    }

}
