package model;

import utils.UserCommand;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Stack;

public class GameModel {

    private final HashMap<String, UserCommand> commands;
    private final Stack<CommandState> commandsHistory;
    private CommandState currentStateAction = null;

    private final HashMap<String, HashSet<String>> objectSequence;

    public GameModel(HashMap<String, UserCommand> commands, HashMap<String, HashSet<String>> objectSequence) {
        commandsHistory = new Stack<>();
        this.commands = commands;
        this.objectSequence = objectSequence;
    }

    public boolean hasCommandObject(String action, String object) {
        if (this.commands.containsKey(action)) {
            return this.commands.get(action).getObjects().contains(object);
        }

        return false;
    }

    public boolean hasCurrentState() {

        if (currentStateAction != null) {
            return true;
        }
        return false;
    }

    public boolean hasCommand(String action) {
        return this.commands.containsKey(action);
    }

    public UserCommand getCommand(String action) {
        return this.commands.get(action);
    }

    public CommandState getCurrentStateAction() {
        return currentStateAction;
    }

    public void setCurrentStateAction(CommandState currentStateAction) {
        this.currentStateAction = currentStateAction;
    }


    public boolean stateHasActivatedObject(String object) {
        CommandState currentStateAction = this.getCurrentStateAction();

        return currentStateAction != null && currentStateAction.getObject().equals(object);
    }

    public Stack<CommandState> getCommandsHistory() {
        return commandsHistory;
    }

    public void addCommandToHistory(CommandState command) {
        this.commandsHistory.add(command);
    }

    public boolean canObjectsInteract(String object) {
        return this.objectSequence.containsKey(object);
    }


    public HashSet<String> getInteractableObjects(String object) {
        return this.objectSequence.get(object);
    }

    public HashMap<String, UserCommand> getCommands() {
        return commands;
    }
}
