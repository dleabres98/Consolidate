package com.example.consolidate;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Arrays;
import java.util.List;


public class Recordbook {
    private int size = 0;
    private int[][] table;
    private int finalIndex = 0;
    Transaction[] finalDebts = new Transaction[6];

    List<Transaction> finalDebtsList = new ArrayList<>();


    //construct a 2 dimensional array n x n
    //[r][c] represents amt r owes c
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
        return "done printing";
    }

    //this method balances mirrored debts
    //ex: if A owes B $5, and B owes A $3
    //this would rewrite A owes B $2
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

        //DEBUGGING PURPOSES
        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                System.out.println("[" + r + "][" + c + "] = " + table[r][c]);
            }
        }

        //once all mirrored debts are handled, add all remaining debts to an array
        //as type Transaction() for easier data manipulation

        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                if ( r!=c && table[r][c] != 0) {
                    System.out.println("" + r + " owes " + c + " $" + table[r][c]);

                    /*
                    finalDebts[finalIndex] = new Transaction(""+ r, ""+ c, table[r][c]);
                    finalIndex++;
                    */

                    finalDebtsList.add(new Transaction(""+ r, ""+ c, table[r][c]));
                }

            }
        }

    }


    //this is the final method called, it will produce the ultimate list of payments due
    //from each individual
    public void minimizeTransactions() {

        //this array will be used to determine who pays who (sorted in ascending order of debt)
        Person[] sortedDebts = new Person[size];

        for(int i = 0; i < size; i++) {
            sortedDebts[i] = new Person(i, 0);
        }

        //determine the NET value owed, negative means you have to PAY, positive means you get PAID
        for(int i = 0; i < finalDebtsList.size(); i++) {
            /*
            sortedDebts[ finalDebts[i].returnGiver() ].addDebt(finalDebts[i].returnAmt()*-1);
            sortedDebts[ finalDebts[i].returnTaker() ].addDebt((finalDebts[i].returnAmt()));
            */
            sortedDebts[ finalDebtsList.get(i).returnGiver() ].addDebt(finalDebtsList.get(i).returnAmt());
            sortedDebts[ finalDebtsList.get(i).returnTaker() ].addDebt((finalDebtsList.get(i).returnAmt())*-1);

        }



        //sorted with CompareTo in Person object, in ascending order of net debt
        //also we need to remove zero net debts
        Arrays.sort(sortedDebts); //


        for (int i = 0; i < size; i++) {
            System.out.println("" + sortedDebts[i]);
        }

        //now that we have a sorted array, we need to find the index of first positive, non-zero
        //if there is a zero element, we should remove that person (worry about that later)
        int firstReceiverIndex = 0;
        int lastPayerIndex = 0;

        for (int i = 0; i < size; i++) {
            if (sortedDebts[i].returnNetAmt() > 0) {
                firstReceiverIndex = i;
                break;
            } else if (sortedDebts[i].returnNetAmt() != 0){
                lastPayerIndex = i;
            }
        }
        //TODO: Show this on the interface somehow
        System.out.println(getFinalDebts(sortedDebts, firstReceiverIndex, lastPayerIndex));
    }




    public List<String> getFinalDebts(Person[] arr, int firstReceiver, int lastIndex) {
        List<String> list = new ArrayList<String>();

        int leftIndex = 0; //start of PAYERS
        int rightIndex = firstReceiver; //start of RECEIVERS
        int lastPayIndex = lastIndex; //used to leave out those with net debt = 0

        while (leftIndex <= lastPayIndex && rightIndex < arr.length) {

            //if the PAYER can cover MORE of the PAYEE'S value
            if ((arr[leftIndex].returnNetAmt()*-1) > arr[rightIndex].returnNetAmt()) {
                list.add("" + arr[leftIndex].returnID() + " owes " + arr[rightIndex].returnID() + "$" + (arr[rightIndex].returnNetAmt()));
                arr[leftIndex].addDebt(arr[rightIndex].returnNetAmt() * -1);
                rightIndex++;

            //if the PAYER can only cover SOME of the PAYEE's value
            } else if ((arr[leftIndex].returnNetAmt()*-1) < arr[rightIndex].returnNetAmt()) {
                list.add("" + arr[leftIndex].returnID() + " owes " + arr[rightIndex].returnID() + " $" + (arr[leftIndex].returnNetAmt())*-1);
                arr[rightIndex].addDebt(arr[leftIndex].returnNetAmt() * -1);
                leftIndex++;

            //if they are equal - need to test this
            } else {
                System.out.println("\n\nequal\n\n");
                list.add("" + arr[leftIndex].returnID() + " owes " + arr[rightIndex].returnID() + " $" + (arr[rightIndex].returnNetAmt()));
                leftIndex++;
                rightIndex++;
            }
        }

        return list;

    }
}
