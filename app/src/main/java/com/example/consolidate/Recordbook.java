package com.example.consolidate;

public class Recordbook {
    private int size = 0;
    private int[][] table;


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
                if (r!=c && table[r][c] != 0)
                    System.out.println("" + r + " owes " + c + " $" + table[r][c]);
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

    }
}
