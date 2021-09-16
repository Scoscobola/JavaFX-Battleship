package com.example.battleship;

import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;

public class Player {
    public ShipGrid shipGrid;
    public TrackingGrid trackingGrid;
    public Pane root;
    public TilePane tileTracking;
    public TilePane tileShip;
    public boolean isComputer;

    public Player(boolean computer){
        this.isComputer = computer;
    }

    // Every shot guessed on the tracking grid asks the opponents ship grid what the outcome was
    public void setShotListener(CheckShot l){
        this.trackingGrid.setShotListeners(l);
    }

    // Instantiate various JavaFX nodes under the root pane
    public Pane createPlayerContent(){
        this.root = new Pane();
        this.root.setPrefSize(1280, 1280);

        this.tileTracking = new TilePane();
        this.tileShip = new TilePane();
        this.tileShip.relocate(730, 0);
        this.shipGrid = new ShipGrid(this.tileShip);
        this.trackingGrid = new TrackingGrid(this.tileTracking);

        tileShip.setPrefRows(10);
        tileShip.setPrefColumns(10);
        tileShip.setPrefSize(550, 550);

        tileTracking.setPrefColumns(10);
        tileTracking.setPrefRows(10);
        tileTracking.setPrefSize(550, 550);

        tileTracking.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
        tileShip.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
        this.root.getChildren().add(tileTracking);
        this.root.getChildren().add(tileShip);


        return this.root;
    }
}
