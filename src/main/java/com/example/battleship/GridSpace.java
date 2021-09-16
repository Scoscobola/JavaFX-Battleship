package com.example.battleship;

import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class GridSpace extends Rectangle {
    private final int xCoord;
    private final int yCoord;
    private String gridType;
    public OccupationType occupationType;
    public Shot hit = Shot.UNKNOWN;
    private CheckShot listener;

    public GridSpace(int width, int height, int matrixCoordX, int matrixCoordY, String type, OccupationType oType){
        super(width, height);
        this.gridType = type;
        this.occupationType = oType;
        Color color = Color.BLACK;
        switch(type){
            case "tracker":
                color = Color.YELLOW;
                break;
            case "ship":
                color = Color.BLUE;
                break;
        }
        this.setFill(color);
        this.xCoord = matrixCoordX;
        this.yCoord = matrixCoordY;

    }

    // Set the listener and define onMouseClicked event
    public void setShotListener(CheckShot l){
        this.listener = l;
        this.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent){
                System.out.printf("I was clicked and my coords are %d, %d", xCoord, yCoord);
                if (gridType.equals("tracker")) {
                    Shot result = listener.checkShot(xCoord, yCoord);
                    Alert alert;
                    switch (result) {
                        case HIT -> {
                            hit = Shot.HIT;
                            setFill(Color.GREEN);
                            alert = new Alert(Alert.AlertType.INFORMATION, "You hit a ship!", ButtonType.OK);
                            alert.showAndWait();
                        }
                        case SUNK -> {
                            hit = Shot.HIT;
                            setFill(Color.GREEN);
                            alert = new Alert(Alert.AlertType.INFORMATION, "You sunk a ship!", ButtonType.OK);
                            alert.showAndWait();
                        }
                        case WIN -> {
                            hit = Shot.HIT;
                            setFill(Color.GREEN);
                            alert = new Alert(Alert.AlertType.INFORMATION, "You sunk all your opponents ships, YOU WIN!", ButtonType.OK);
                            alert.showAndWait();
                        }
                        case MISS -> {
                            hit = Shot.HIT;
                            setFill(Color.RED);
                            alert = new Alert(Alert.AlertType.INFORMATION, "That shot MISSED", ButtonType.OK);
                            alert.showAndWait();
                        }
                        case GUESSED -> {
                            alert = new Alert(Alert.AlertType.INFORMATION, "You've already guessed that coordinate.", ButtonType.OK);
                            alert.showAndWait();
                        }
                    }
                }
            }
        });
    }
}
