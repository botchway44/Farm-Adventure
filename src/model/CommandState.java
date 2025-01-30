package model;

import utils.CommandType;

public class CommandState {
    private String command;
    private String object;
    private CommandType type;

    public CommandState(String command, String object, CommandType type){
        this.command = command;
        this.object = object;
        this.type = type;
    }

    public String getCommand() {
        return command;
    }

    public String getObject() {
        return object;
    }

    public CommandType getType() {
        return type;
    }

    public String toString(){
        return this.command+" "+this.type+" "+this.object;
    }
}
