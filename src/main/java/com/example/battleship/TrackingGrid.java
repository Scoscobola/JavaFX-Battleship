package com.example.battleship;

import javafx.scene.layout.TilePane;

public class TrackingGrid {
    private final GridSpace[][] gridSpaces = new GridSpace[10][10];

    // Fill the 2D array with gridspaces
    public TrackingGrid(TilePane grid){
        for (int i = 0; i < 10; i ++){
            for (int j = 0; j < 10; j++) {
                GridSpace rec = new GridSpace(50, 50, i, j, "tracker", OccupationType.EMPTY);
                rec.setStyle("-fx-stroke: black; -fx-stroke-width: 5;");
                gridSpaces[i][j] = rec;
                grid.getChildren().add(rec);
            }
        }
    }

    // Set each listener for each grid space
    public void setShotListeners(CheckShot l){
        for (int i = 0; i < 10; i ++){
            for (int j = 0; j < 10; j++) {
                gridSpaces[i][j].setShotListener(l);
            }
        }
    }

}
