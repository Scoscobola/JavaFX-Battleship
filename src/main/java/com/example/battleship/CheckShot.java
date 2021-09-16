package com.example.battleship;

// Listeners implement this interface to determine outcome of shot
public interface CheckShot {
    Shot checkShot(int x, int y);
}
