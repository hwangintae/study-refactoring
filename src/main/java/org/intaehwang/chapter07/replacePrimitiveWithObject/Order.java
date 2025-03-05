package org.intaehwang.chapter07.replacePrimitiveWithObject;

public class Order {
    private Priority priority;

    public Order(Priority priority) {
        this.priority = priority;
    }

    public String getPriorityString() {
        return priority.toString();
    }

    public void setPriority(String aString) {
        this.priority = new Priority(aString);
    }
}
