package com.practice.coding.senddatatoserverurl;

public class Person {
    String name, rollNo, className;

    public Person(String name, String rollNo, String className) {
        this.name = name;
        this.rollNo = rollNo;
        this.className = className;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRollNo() {
        return rollNo;
    }

    public void setRollNo(String rollNo) {
        this.rollNo = rollNo;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
