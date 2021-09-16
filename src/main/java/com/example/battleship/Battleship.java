package com.example.battleship;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;


public class Battleship extends Application {
    private Scene p1Scene;
    private Scene p2Scene;
    private Scene menu;
    private Player player1;
    private Player player2;

    // Create the JavaFX scenes based on the gamemode chosen
    public void initPlayerScenes(Stage stage, boolean mode){
        // Single player means player 2 is the "computer"
        if (mode) {
            player1 = new Player(false);
            player2 = new Player(true);
        } else{
            player1 = new Player(false);
            player2 = new Player(false);
        }
        Pane root1 = player1.createPlayerContent();
        Button button1 = new Button("Next Turn");
        button1.setOnAction(e -> stage.setScene(p2Scene));
        button1.setPrefWidth(75);
        button1.relocate(602.5, 275);
        root1.getChildren().add(button1);
        p1Scene = new Scene(root1);

        Pane root2 = player2.createPlayerContent();
        Button button2 = new Button("Next Turn");
        button2.setOnAction(e -> stage.setScene(p1Scene));
        button2.relocate(602.5, 275);
        root2.getChildren().add(button2);
        p2Scene = new Scene(root2);

        player1.setShotListener(player2.shipGrid);
        player2.setShotListener(player1.shipGrid);

        if (mode) {
            button1.setDisable(true);
            p1Scene.setOnMouseClicked(e -> {
                takeComputerTurn();
            });
        }
    }

    // Start FX app and setup main menu scene
    @Override
    public void start(Stage stage) throws IOException {
        Pane menuPane = new Pane();
        menuPane.setPrefSize(480, 480);
        Text text = new Text("Welcome to Battleship, Please select game mode");
        text.prefWidth(240);
        text.relocate(120, 100);
        Button single = new Button("Single Player");
        single.setPrefWidth(125);
        single.relocate(177.5, 140);
        single.setOnAction(e -> {
            initPlayerScenes(stage, true);
            stage.setScene(p1Scene);
        });
        Button multi = new Button("Multiplayer");
        multi.setPrefWidth(125);
        multi.setOnAction(e -> {
            initPlayerScenes(stage, false);
            stage.setScene(p1Scene);
        });
        multi.relocate(177.5, 180);
        menuPane.getChildren().addAll(text, single, multi);
        menu = new Scene(menuPane);

        stage.setScene(menu);
        stage.show();
    }

    // If single player mode, this function is called after every shot taken.
    private void takeComputerTurn() {
        Shot result;
        // Ensure the shot was not already randomly guessed
        while (true) {
            int first = (int) Math.floor(Math.random() * (9 + 1));
            int second = (int) Math.floor(Math.random() * (9 + 1));
            if (player1.shipGrid.gridSpaces[first][second].hit == Shot.UNKNOWN) {
                result = player1.shipGrid.checkShot(first, second);
                break;
            }
        }

        if (result == Shot.SUNK){
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Your ship was sunk!", ButtonType.OK);
            alert.showAndWait();
        } else if (result == Shot.HIT){
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Your ship was hit!", ButtonType.OK);
            alert.showAndWait();
        } else if (result == Shot.WIN){
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "All your ships are sunk, YOU LOST!", ButtonType.OK);
            alert.showAndWait();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}