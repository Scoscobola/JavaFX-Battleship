# JavaFX-Battleship
---

Battleship game utilizing object-oriented design implemented with Java and JavaFX.

The game can be played single-player (against the computer) or multiplayer. The tracking grid, which is where the user guesses where their opponent's ships are 
located is on the left side of the screen. The player's ship grid is on the right. At the start of the game, all the coordinate tiles in the tracking grid are 
yellow. When the player selects a tile, it will change color to either red, to indicate a miss, or green to indicate the shot hit an opponent's ship. On the ship 
grid, all tiles are blue except tile are occupied by a ship, which are colored white. When a player's ship is hit, the corresponding tile turns red. JavaFX alerts
are also used to communicate to the player the outcome of their shots. In single-player mode, alerts are also used to tell the player if one of their ships was
hit or was sunk. 

The observer pattern was used to check the outcome of guesses. When a player clicks a tile, an event is fired and the opponents ship grid determines 
the outcome of the shot. 

