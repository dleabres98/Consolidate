package com.example.consolidate;

import java.util.Comparator;

public class Person implements Comparable<Person> {
    private int id;
    private int netAmt;
    private boolean mustPay = false;

    public Person(int i, int val) {
        id = i;
        netAmt = val;
        if (netAmt < 0) mustPay = true;
    }

    public int returnNetAmt() { return netAmt; }

    public void addDebt(int val) {
        netAmt += val;
    }

    public String toString() {
        return "ID: " + id + " netAmt: " + netAmt + "\n";
    }

    public int compareTo(Person comparePerson) {
        int compareValue = ((Person)comparePerson).returnNetAmt();
        return this.netAmt - compareValue;
    }

}