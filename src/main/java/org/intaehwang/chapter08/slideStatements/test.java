package org.intaehwang.chapter08.slideStatements;

public class test {
    private final Resources allocateResources;
    private final Resources availableResources;

    public test(Resources allocateResources, Resources availableResources) {
        this.allocateResources = allocateResources;
        this.availableResources = availableResources;
    }

    public Resource testFunction() {
        Resource result;

        if (availableResources.length() == 0) {
            result = Resource.create();
        } else {
            result = availableResources.pop();
        }

        allocateResources.push(result);

        return result;
    }
}
