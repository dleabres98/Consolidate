package com.example.consolidate;

import android.util.Log;

public class Recordbook {
    private int size = 0;
    private int[][] table;
    private int finalIndex = 0;
    Transaction[] finalDebts = new Transaction[3];


    public Recordbook(int x, int[][] arr) {

        size = x;
        table = arr;

        int[][] table = new int[size][size];

        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                table[r][c] = 0;
            }
        }
        System.out.println("array initialized");





    }

    public void addTransaction(int a, int  b, int amount) {
        table[a][b] += amount;
    }

    public String toString() {
        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                if (r!=c && table[r][c] != 0) {
                    System.out.println("" + r + " owes " + c + " $" + table[r][c]);

                }

            }
        }
        return "done";
    }

    public void consolidateDebts() {
        for (int r = 0; r <= size/2; r++) {
            for (int c = 0; c < size; c++) {
                if (r != c) {
                    if (table[r][c] > table[c][r]) {
                        table[r][c] -= table[c][r];
                        table[c][r] = 0;
                    } else if (table[r][c] < table[c][r]) {
                        table[c][r] -= table[r][c];
                        table[r][c] = 0;
                    } else if (table[r][c] == table[c][r]) {
                        table[r][c] = 0;
                        table[c][r] = 0;
                    }
                }
            }
        }

        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                if (r!=c && table[r][c] != 0) {
                    System.out.println("" + r + " owes " + c + " $" + table[r][c]);
                    Log.d("myTag", "adding new to finaldebt[" + finalIndex + "]");
                    finalDebts[finalIndex] = new Transaction(""+ r, ""+ c, table[r][c]);
                    finalIndex++;
                }

            }
        }

    }

    //ex: a owes $5 to b,  b owes $5 to c
    //AFTER minimizing transactions becomes
    //a owes $5 to c
    //need to check if it can be consolidated to just one or possibly 0?
    //DO THIS BY LOOKING AT HOW MUCH EACH LOSES
    public void minimizeTransactions() {
        System.out.println("\n\n\n-------------------MINIMIZE--------------\n\n");
        //find smallest debt
        int[] valueLost = new int[3]; //init to all zeros
        for (int i = 0; i < 3; i++) {
            valueLost[i] = 0;
        }
        Log.d("myTag", "made it to 90");

        for(int i = 0; i < finalDebts.length; i++) {
            valueLost[ finalDebts[i].returnGiver() ] -= finalDebts[i].returnAmt();
            valueLost[ finalDebts[i].returnTaker() ] += finalDebts[i].returnAmt();
        }

        for (int i = 0; i < size; i++) {
            System.out.println("" + i + " loses " + valueLost[i] );
        }















    }
}
