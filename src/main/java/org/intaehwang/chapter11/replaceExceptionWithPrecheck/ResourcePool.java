package org.intaehwang.chapter11.replaceExceptionWithPrecheck;

import java.util.Deque;
import java.util.List;
import java.util.NoSuchElementException;

public class ResourcePool {
    private Deque<Resource> available;
    private List<Resource> allocated;

    public ResourcePool(Deque<Resource> available, List<Resource> allocated) {
        this.available = available;
        this.allocated = allocated;
    }

    public Resource get() {
        Resource result;

        if (available.isEmpty()) {
            result = Resource.create();
            allocated.add(result);
        } else {
            result = available.pop();
            allocated.add(result);
        }


        return result;
    }
}
