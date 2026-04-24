package CraftQuestApp;
/*
Programmer: Melo
Program: Model File for CraftQuest
Purpose: Model file - part of the MVC of the project
         stores and manage's the game's data and logic
Date: April 23, 2026
*/

import java.util.*;

// Any class that implements GameObserver will include
// onGameUpdated(), the View.java file implements this later on.
interface GameObserver {
    void onGameUpdated();
}

// The purpose of this method is that any class that implements
// Map Strategy will create the 2D array of Tile objects - Two maps created will use this.
interface MapStrategy {
    Tile[][] generate (int width, int height);
}

// Creates Tile class, by using an enum (w3schools.com/java/java_enums.asp) 
// it allows for the list of options to only be Grass, Stone, Water & Chests. 
// Only these options, no need to worry about numbers or strings
class Tile {
    enum Type {GRASS, STONE, WATER, CHEST}

    // Stores what kind of tile Type (G, S, W, C) it is
    private Type type; 

    // Has this chest been collected or not (T or F)
    private boolean collected;


    // Constructor for Tile class 
    Tile(Type type){
        this.type = type;
        this.collected = false;
    }

    // Get Methods
    Type getType(){
        return type;
    }
    boolean isCollected (){ 
        return collected; 
    }

    // The player can walk on all the tiles except water, so this method is created 
    // make water unwalkable
    boolean isWalkable(){
        return type != Type.WATER;
    }

    // implement the check tile collectability if the tile is only a chest

    // create method called collect that gets called when the player steps on a chest

    // .....


}