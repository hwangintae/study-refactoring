package org.intaehwang.chapter12.replaceTypeCodeWithSubclass;

public class EmployeeType {
    private String value;

    public EmployeeType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }

    public String capitalizedName() {
        return Character.toUpperCase(this.toString().charAt(0))
                + this.toString().substring(1).toLowerCase();
    }
}
