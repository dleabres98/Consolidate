package com.example.consolidate;

import android.util.Log;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Arrays;
import java.util.List;


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
                    finalDebts[finalIndex] = new Transaction(""+ r, ""+ c, table[r][c]);
                    finalIndex++;
                }

            }
        }

    }

    //bread and butter method!
    //this is the final method called, it will produce the ultimate list of payments due
    //from each individual
    public void minimizeTransactions() {

        //this array will be used to determine who pays who (sorted in ascending order of debt)
        Person[] sortedDebts = new Person[size];

        for(int i = 0; i < size; i++) {
            sortedDebts[i] = new Person(i, 0);
        }

        //determine the NET value owed, negative means you have to PAY, positive means you get PAID
        for(int i = 0; i < finalDebts.length; i++) {
            sortedDebts[ finalDebts[i].returnGiver() ].addDebt(finalDebts[i].returnAmt()*-1);
            sortedDebts[ finalDebts[i].returnTaker() ].addDebt((finalDebts[i].returnAmt()));
        }

        //sorted with CompareTo in Person object
        Arrays.sort(sortedDebts);

        for (int i = 0; i < size; i++) {
            System.out.println("" + sortedDebts[i]);
        }

        //now that we have a sorted array, we need to find the index of first positive, non-zero
        //if there is a zero element, we should remove that person (worry about that later)
        int firstReceiverIndex = 0;
        for (int i = 0; i < size; i++) {
            if (sortedDebts[i].returnNetAmt() > 0) {
                break;
            } else {
                firstReceiverIndex += 1;
            }
        }
        System.out.println(getFinalDebts(sortedDebts, firstReceiverIndex));
    }

    public List<String> getFinalDebts(Person[] arr, int index) {
        List<String> list = new ArrayList<String>();
        int leftIndex = 0;
        int rightIndex = index;
        for (int i = 0; i < size-1; i++) {

            //if the PAYER can cover MORE of the PAYEE'S value
            if ((arr[leftIndex].returnNetAmt()*-1) > arr[rightIndex].returnNetAmt()) {
                list.add("" + arr[leftIndex].returnID() + " owes " + arr[rightIndex].returnID() + "$" + (arr[rightIndex].returnNetAmt()*-1));
                arr[leftIndex].addDebt(arr[rightIndex].returnNetAmt() * -1);
                rightIndex++;

            //if the PAYER can only cover SOME of the PAYEE's value
            } else if ((arr[leftIndex].returnNetAmt()*-1) < arr[rightIndex].returnNetAmt()) {
                list.add("" + arr[leftIndex].returnID() + " owes " + arr[rightIndex].returnID() + " $" + (arr[leftIndex].returnNetAmt()*-1));
                arr[rightIndex].addDebt(arr[leftIndex].returnNetAmt() * -1);
                leftIndex++;

            //if they are equal
            } else {
                list.add("" + arr[leftIndex].returnID() + " owes " + arr[rightIndex].returnID() + " $" + (arr[leftIndex].returnNetAmt()*-1));

                leftIndex++;
                rightIndex++;

            }

        }

        return list;

    }
}
