package com.example.consolidate;

public class Transaction {
    private String giver;
    private String taker;
    private int amount;

    public Transaction(String t, String g, int amt) {
        giver = g;
        taker = t;
        amount = amt;
    }

    public int returnAmt() { return amount; }

    public int returnTaker() { return Integer.parseInt(taker); }

    public int returnGiver() { return Integer.parseInt(giver); }

    public String toString() {
        return taker + " owes " + giver + " $" + amount;
    }
}
