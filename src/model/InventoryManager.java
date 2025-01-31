package model;

import java.util.HashMap;

public class InventoryManager {
    public HashMap<String, Integer> inventory = new HashMap<>();

    public InventoryManager() {
    }

    public void addInventory(String object) {
        if (this.inventory.containsKey(object)) {
            this.inventory.put(object, this.inventory.get(object) + 1);
        } else {
            this.inventory.put(object, 1);
        }
    }

    public void removeInventory(String object) {
        if (this.inventory.containsKey(object)) {
            if (this.inventory.get(object) == 1) {
                this.inventory.remove(object);
            } else {
                this.inventory.put(object, this.inventory.get(object) - 1);
            }
        }
    }

    public HashMap<String, Integer> getInventory() {
        return this.inventory;
    }

    public boolean hasItem(String object) {
        return this.inventory.containsKey(object);
    }

    public void reduceItem(String object) {
        if (this.inventory.containsKey(object)) {
            this.inventory.put(object, this.inventory.get(object) - 1);
        }

        //check if the item is 0, remove it from the inventory
        if (this.inventory.get(object) == 0) {
            this.inventory.remove(object);
        }

    }

    public void canInventoryItemUnlock(String object) {

        //check if an item in the inventory can unlock the object


    }


    public String toString() {
        return "You have " + this.inventory.size() + " item in your Inventory.\n" + this.inventory.toString();
    }

}
