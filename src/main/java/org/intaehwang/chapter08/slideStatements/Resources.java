package org.intaehwang.chapter08.slideStatements;

import java.util.List;

public class Resources {
    private List<Resource> list;

    public Resources() {}

    public static Resources create() {
        return new Resources();
    }

    public int length() {
        return list.size();
    }

    public void push(Resource resource) {
        list.add(resource);
    }

    public Resource pop() {
        if (list.isEmpty()) return Resource.create();

        Resource resource = list.get(list.size() - 1);
        list.remove(list.size() - 1);

        return resource;
    }
}
