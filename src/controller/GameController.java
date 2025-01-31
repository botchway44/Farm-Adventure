package controller;

import model.CommandState;
import model.GameModel;
import model.InventoryManager;
import utils.CommandType;
import utils.Constants;
import utils.UserCommand;
import view.GameView;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class GameController {

    public GameModel gameModel;
    public GameView gameView;
    public InventoryManager inventoryManager = new InventoryManager();

    public GameController(HashMap<String, UserCommand> commands, HashMap<String, HashSet<String>> objectSequence) {
        this.gameModel = new GameModel(commands, objectSequence);
        this.gameView = new GameView();


    }

    public void startGame() {
        this.gameView.initialMessage(generateInitialCommands());

        Scanner scanner = new Scanner(System.in);
        while (true) {

            this.gameView.promptUserForInput(Constants.COMMAND_PROMPT);
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("E") || input.equalsIgnoreCase("Explore")) {
                exploreScene();
                continue;
            } else if (input.equalsIgnoreCase("I") || input.equalsIgnoreCase("inventory")) {
                displayInventory();
                continue;
            } else if (input.equalsIgnoreCase("H") || input.equalsIgnoreCase("history")) {
                historyManager();
                continue;
            } else if (input.equalsIgnoreCase("exit") || input.equalsIgnoreCase("quit") || input.equalsIgnoreCase("q")) {
                break;
            } else if (input.equalsIgnoreCase("menu") || input.equalsIgnoreCase("m")) {
                this.gameView.initialMessage(generateInitialCommands());
                continue;
            }
            processInput(input);
        }
    }


    public void exploreScene() {

        //check if there is a current action, show the objects that can be interacted in that state
        if (this.gameModel.hasCurrentState()) {
            CommandState state = this.gameModel.getCurrentStateAction();
            UserCommand command = this.gameModel.getCommand(state.getCommand());

            this.gameView.displayOutput(command.getDescription().replace("{object}", state.getObject()));
        }
        this.gameView.displayOutput(generateInitialCommands());

    }

    public void processInput(String input) {

        //Split the input token into an array and convert each to lowercase
        String[] tokens = input.toLowerCase().trim().split(" ");

        HashSet<String> tokenSet = new HashSet<>();
        Collections.addAll(tokenSet, tokens);


        UserCommand command = null;
        String commandString = "";
        String objectString = "";

        for (String token : tokenSet) {
            if (this.gameModel.hasCommand(token.trim().strip())) {
                command = this.gameModel.getCommand(token.trim().strip());
                commandString = token.trim().strip();
                break;
            }
        }


        //if a command was found, process the object to interact on
        if (command != null) {
            boolean objectFound = false;
            for (String token : tokenSet) {
                //process objects the current action can work on
                if (this.gameModel.hasCommandObject(command.getAction(), token.trim().strip())) {

                    objectFound = true;
                    this.executeCommand(command.getAction(), token.trim().strip());
                    objectString = token.trim().strip();
                    break;
                }
            }

            if (!objectFound) {
                this.gameView.displayOutput("You need to " + command.getAction() + " to an object");
                this.gameView.displayOutput("You can " + command.getAction() + " : " + command.getObjects().toString());
            }

        }
        //else prompt the user to enter another command
        else {
            this.gameView.displayOutput(Constants.WRONG_COMMAND);
        }
        // Add Command to history
        this.gameModel.addCommandToHistory(new CommandState(commandString, objectString, command != null ? command.getType() : null, input));
    }


    public void executeCommand(String command, String object) {

        UserCommand userCommand = this.gameModel.getCommand(command);

        // check the command type. If is it inventory, check if the current action has the object the user is asking to pick
        if (userCommand.getType() == CommandType.INVENTORY) {

            // check if the current state action has the object inside it
            if (this.gameModel.stateHasActivatedObject(object)) {
                this.inventoryManager.addInventory(object);

                String newMessage = userCommand.getDescription().replace("{object}", object);
                this.gameView.displayOutput(newMessage);
                this.gameView.displayOutput(this.inventoryManager.toString());
            } else {
                this.gameView.displayOutput("You cannot interact with " + object + ", you have to be at the " + object + "'s location");
            }

        }
        //if the command is ACTION, set it as the current state and execute
        else if (userCommand.getType() == CommandType.ACTION) {
            // set the current action to this action
            this.gameModel.setCurrentStateAction(new CommandState(command, object, userCommand.getType()));
            String newMessage = userCommand.getDescription().replace("{object}", object);
            this.gameView.displayOutput(newMessage);
        }
        //if it is INTERACT,  set it as the current state and execute
        else if (userCommand.getType() == CommandType.INTERACT) {

            //check if the user has an object in the input that exist as
            if (this.gameModel.stateHasActivatedObject(object)) {
                //check if the object exist in the object sequence
                //check if an item in the inventory can unlock the object in the object sequence
                //if(this.inventoryManager)

                // object -> object sequence
                if (this.gameModel.canObjectsInteract(object)) {
                    // pull the interactable objects and check if any of them exist in the inventory


                    HashSet<String> items = this.gameModel.getInteractableObjects(object);
                    for (String item : items) {
                        if (this.inventoryManager.hasItem(item)) {
                            this.gameView.displayOutput("Using " + item + " to " + command + " with " + object);
                            // reduce the item in the inventory
                            this.inventoryManager.reduceItem(item);
                            String newMessage = userCommand.getDescription().replace("{object}", object);
                            this.gameView.displayOutput(newMessage);
                            return;
                        }
                    }

                    this.gameView.displayOutput("Cannot " + command + " " + object + ", You need any of these objects : " + items.toString());

                } else {
                    this.gameView.displayOutput("You cannot " + userCommand.getAction() + " " + object);
                }
            } else {
                this.gameView.displayOutput("You cannot interact with " + object + ", you have to be at the " + object + "'s location");
            }
        }

    }

    private void displayInventory() {
        this.gameView.displayOutput(this.inventoryManager.toString());
    }

    public void historyManager() {
        for (CommandState command : this.gameModel.getCommandsHistory()) {
            this.gameView.displayOutput("- " + command.toString());
        }
    }

    private String generateInitialCommands() {
        StringBuilder newMessage = new StringBuilder(Constants.COMMANDS_AVAILABLE);
        for (String command : this.gameModel.getCommands().keySet()) {
            newMessage.append(" ").append(command).append(",");
        }
        return newMessage.toString();
    }
}
