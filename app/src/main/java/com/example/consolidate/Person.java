package com.example.consolidate;

public class Person implements Comparable<Person> {
    private int id;
    private int netAmt;

    public Person(int i, int val) {
        id = i;
        netAmt = val;
    }

    public int returnNetAmt() { return netAmt; }

    public void addDebt(int val) {
        netAmt += val;
    }

    public String returnID() {
        return "" + id;
    }

    public String toString() {
        return "ID: " + id + " netAmt: " + netAmt + "\n";
    }

    public int compareTo(Person comparePerson) {
        int compareValue = comparePerson.returnNetAmt();
        return this.netAmt - compareValue;
    }

}