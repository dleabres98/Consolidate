package com.example.consolidate;

import android.util.Log;
import java.util.Comparator;
import java.util.Arrays;


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


    public void minimizeTransactions() {
        //find smallest debt
        int[] netValue = new int[3]; //init to all zeros
        int numPayers = 0;
        Person[] sortedDebts = new Person[size];


        for (int i = 0; i < 3; i++) {
            netValue[i] = 0;
        }

        for(int i = 0; i < size; i++) {
            sortedDebts[i] = new Person(i, 0);
        }


        for(int i = 0; i < finalDebts.length; i++) {
            netValue[ finalDebts[i].returnGiver() ] -= finalDebts[i].returnAmt();
            netValue[ finalDebts[i].returnTaker() ] += finalDebts[i].returnAmt();

            sortedDebts[ finalDebts[i].returnGiver() ].addDebt(finalDebts[i].returnAmt()*-1);
            sortedDebts[ finalDebts[i].returnTaker() ].addDebt((finalDebts[i].returnAmt()));

        }
        System.out.println("\nNet Amts are as follows, negative is amt to be paid \n");
        Arrays.sort(sortedDebts);
        for (int i = 0; i < size; i++) {
            System.out.println("" + sortedDebts[i]);
        }


        for (int i = 0; i < size; i++) {
            System.out.println("\n" + i + "'s net is  " + netValue[i] + "\n");
            if (netValue[i] > 0) {
                numPayers++;
            }
        }


        for (int i = 0; i < size; i++) {
            sortedDebts[i] = new Person(i, netValue[i]);
        }

        System.out.println("Person array created\n");

        /*
        for (int i = 0; i < size; i++) {
            sortedDebts[i] = new Person(i, netValue[i]);
            System.out.println("" + sortedDebts[i]);
        }*/



        System.out.println("sorted now");

        for (int i = 0; i < size; i++) {
            System.out.println("" + sortedDebts[i]);
        }



        //break up people into two lists payers and receivers
        //sort by value (highest to lowest ? )
        //go one by one

        /*
        Person[] payers = new Person[numPayers];
        Person[] receivers = new Person[size-numPayers];

        for(int i = 0; i < size; i++) {


        }*/


















    }
}
