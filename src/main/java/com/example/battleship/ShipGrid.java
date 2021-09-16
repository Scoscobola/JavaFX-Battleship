package com.example.battleship;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;

import java.util.Arrays;

public class ShipGrid implements CheckShot{
    public final GridSpace[][] gridSpaces = new GridSpace[10][10];
    public Ship[] ships;
    public OccupationType[] occupationTypes = {OccupationType.EMPTY, OccupationType.DESTROYER, OccupationType.SUBMARINE,
    OccupationType.CRUISER, OccupationType.BATTLESHIP, OccupationType.CARRIER};

    // Fill the 2D array with gridspaces
    public ShipGrid(TilePane grid){
        for (int i = 0; i < 10; i ++){
            for (int j = 0; j < 10; j++) {
                GridSpace rec = new GridSpace(50, 50, i, j, "ship",  OccupationType.EMPTY);
                rec.setStyle("-fx-stroke: black; -fx-stroke-width: 5;");
                this.gridSpaces[i][j] = rec;
                grid.getChildren().add(rec);
            }
        }
        ships = new Ship[5];
        ships[0] = new Ship(2);
        ships[1] = new Ship(3);
        ships[2] = new Ship(3);
        ships[3] = new Ship(4);
        ships[4] = new Ship(5);
        populateShips();
    }

    // Randomly populate the 2D array with ships ensuring no ships overlap each other
    public void populateShips(){
        System.out.println(ships.length);
        for (int i = 0; i < 5; i++){
            boolean invalidPlacement = true;
            while (invalidPlacement) {
                Ship ship = ships[i];
                System.out.printf("Ship size: %d\n",ship.size);
                int randX = (int) Math.floor(Math.random() * (9 + 1));
                int randY = (int) Math.floor(Math.random() * (9 + 1));
                if (gridSpaces[randX][randY].occupationType != OccupationType.EMPTY){
                    continue;
                }
                double orientation = Math.random();
                if (orientation < 0.5 && randX + ship.size < 10) {
                    for (int j = randX, k=0; j < randX + ship.size; j++, k++){
                        System.out.printf("Placing 1 at %d, %d\n", j, randY);
                        if (gridSpaces[j][randY].occupationType != OccupationType.EMPTY){
                            System.out.println("Occupied, restarting!\n");
                            for (int m = randX; m < j; m++){
                                gridSpaces[m][randY].occupationType = occupationTypes[0];
                                gridSpaces[m][randY].setFill(Color.BLUE);
                            }
                            break;
                        }
                        ship.location[k][0] = j;
                        ship.location[k][1] = randY;
                        gridSpaces[j][randY].occupationType = occupationTypes[i+1];
                        gridSpaces[j][randY].setFill(Color.WHITE);
                        if (k == ship.size-1){
                            System.out.println("Moving on to next ship");
                            invalidPlacement = false;
                        }
                    }
                } else if (orientation >= 0.5 && randY + ship.size < 10){
                    for (int j = randY, k=0; j < randY + ship.size; j++, k++){
                        System.out.printf("Placing 2 at %d, %d\n", randX, j);
                        if (gridSpaces[randX][j].occupationType != OccupationType.EMPTY){
                            System.out.println("Occupied, restarting!\n");
                            for (int m = randY; m < j; m++){
                                gridSpaces[randX][m].occupationType = occupationTypes[0];
                                gridSpaces[randX][m].setFill(Color.BLUE);
                            }
                            break;
                        }
                        ship.location[k][1] = j;
                        ship.location[k][0] = randX;
                        gridSpaces[randX][j].occupationType = occupationTypes[i+1];
                        gridSpaces[randX][j].setFill(Color.WHITE);
                        if (k == ship.size-1){
                            System.out.println("Moving on to next ship");
                            invalidPlacement = false;
                        }
                    }
                }
            }
        }
    }

    @Override
    public Shot checkShot(int x, int y) {
        System.out.println(this.gridSpaces[x][y].hit);
        if (this.gridSpaces[x][y].hit == Shot.UNKNOWN) {
            if (this.gridSpaces[x][y].occupationType != OccupationType.EMPTY) {
                this.gridSpaces[x][y].setFill(Color.RED);
                this.gridSpaces[x][y].hit = Shot.HIT;
                Ship ship = ships[Arrays.asList(occupationTypes).indexOf(this.gridSpaces[x][y].occupationType) - 1];
                ship.hits++;
                if (ship.hits >= ship.size) {
                    ship.destroyed = true;
                    if (Arrays.stream(ships).allMatch(s -> s.destroyed)) {
                        return Shot.WIN;
                    }
                    return Shot.SUNK;
                } else {
                    return Shot.HIT;
                }
            } else {
                return Shot.MISS;
            }
        } else {
            return Shot.GUESSED;
        }
    }
}
