package utils;

import java.util.ArrayList;
import java.util.HashSet;

public class UserCommand {

    private final String action;
    private final HashSet<String> objects;
    private String description;
    private CommandType type;

    public UserCommand(String action, HashSet<String> objects, String description, CommandType type){
        this.action = action.strip().toLowerCase();
        System.out.println("START"+this.action+"END");
        this.objects = objects;
        this.description = description;
        this.type = type;
    }


    public String getDescription() {
        return description;
    }

    public HashSet<String> getObjects() {
        return objects;
    }

    public String getAction() {
        return action;
    }

    public CommandType getType() {
        return type;
    }

    public String toString(){
        return action+" " + type+" "+objects.toString() + " " + description;
    }
}
