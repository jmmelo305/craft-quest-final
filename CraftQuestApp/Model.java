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

    // Method to check if the tile is collectible only if it's a CHEST and it hasn't been collected yet.
    // && means that both conditions must be true, and the !collected means that collected is false. 
    boolean isCollectible(){
        return type == Type.CHEST && !collected; 
    }

    // method that gets called when the play steps on a chest.
    void collect (){
        if (type == Type.CHEST){
            collected = true; 
        }
    }
    
    // Switch case to draw the corresponding tile on the screen
    // depending on the tile type
    // collected  ? "o" : "C"
    // represents that if the chest has been collected it returns "o", but if it hasn't it returns "C"
    String toSymbol(){
        switch(type){
            case GRASS: return ".";
            case STONE: return "#";
            case WATER: return "~";
            case CHEST: return collected ? "o" : "C";
            default:    return "?";
        }
    }
}

class Inventory {
// The only job of this class is to store a list of the items the player has collected. 
    
// List to store items, only strings. 
    private List <String> items;

    // Constructor 
    Inventory (){ 
        this.items = new ArrayList<>();
    }


    // Created a null item exception error - Error Handling
    void addItem(String item){
        if (item == null){
            throw new IllegalArgumentException("Item cannot be null");
        }
        items. add(item);
    }

    // getters
    List<String> getItems() {
        return items;
    }
    int getItemCount(){
        return items.size();
    }
}


// Class made to track player's position on the grid, holds their inventory,
// and notifies the View everytime something changes
class Player{
    // variables for this class.
    private int x;
    private int y;

    private Inventory inventory;
    private List<GameObserver> observers; 


    // Constructor
    Player(int startX, int startY){
        this.x = startX;
        this.y = startY;
        this.inventory = new Inventory();
        this.observers = new ArrayList<>();
    }

    // two methods that run the entire observer pattern on the player side
    void addObserver (GameObserver observer){
        observers.add(observer);
    }
    private void notifyObservers(){
        for (GameObserver o: observers){
            o.onGameUpdated();
        }
    }

    // method that focuses on movements in the game. 
    void move (int dx, int dy, World world){
        // initializing variables
        int newX; 
        int newY;
        newX = x + dx;
        newY = y + dy;

        // Blocks movement into non - walkable tiles
        if (newX < 0 || newX >= world.getWidth())
            return;
        if (newY < 0 || newY >= world.getHeight()) 
            return;

        // !target.isWalkable() means if if the targetted tile IS NOT walkable then it prevents the movement into it.
        Tile target = world.getTile(newX, newY);
        if (!target.isWalkable()) return; 

        // Move is valid, updates position
        x = newX;
        y = newY; 

        // Auto collects a chest if the player lands on one
        if (target.isCollectible()){
            target.collect();
            inventory.addItem("Chset Loot");
        }
        // tells the View something has changed. 
        notifyObservers();
    }

    // getters
    int getX (){
        return x;
    }

    int getY (){
        return y;
    }
    Inventory getInventory(){
        return inventory;
    }




}   
