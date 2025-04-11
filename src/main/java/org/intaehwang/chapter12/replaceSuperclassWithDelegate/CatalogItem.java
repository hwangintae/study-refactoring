package org.intaehwang.chapter12.replaceSuperclassWithDelegate;

import lombok.Getter;

import java.util.List;

@Getter
public class CatalogItem {
    private Long id;
    private String title;
    private List<String> tags;

    public CatalogItem(Long id, String title, List<String> tags) {
        this.id = id;
        this.title = title;
        this.tags = tags;
    }

    public boolean hasTag(String arg) {
        return tags.contains(arg);
    }
}
