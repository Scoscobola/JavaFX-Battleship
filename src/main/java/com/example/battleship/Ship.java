package com.example.battleship;

public class Ship {
    public final int size;
    public int[][] location;
    public boolean destroyed = false;
    public int hits;

    public Ship(int length){
        this.size = length;
        this.location = new int[this.size][2];
        this.hits = 0;
    }
}
