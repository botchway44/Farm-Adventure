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

    public String toString() {
        return "You have " + this.inventory.size() + " item in your Inventory.\n" + this.inventory.toString();
    }

}
