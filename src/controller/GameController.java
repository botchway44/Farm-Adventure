package controller;

import model.CommandState;
import model.GameModel;
import model.InventoryManager;
import utils.CommandType;
import utils.UserCommand;
import view.GameView;

import java.util.*;

public class GameController {

   public GameModel gameModel;
   public GameView gameView;
   public InventoryManager inventoryManager = new InventoryManager();

   public GameController(HashMap<String, UserCommand> commands, HashMap<String, HashSet<String>> actionSequence, HashMap<String, HashSet<String>> objectSequence){
      this.gameModel = new GameModel(commands, actionSequence, objectSequence );
      this.gameView = new GameView();


   }

   public void startGame(){
         this.gameView.initialMessage();

         Scanner scanner = new Scanner(System.in);
         while(true){

            this.gameView.promptUserForInput("Enter command : ");
           String input = scanner.nextLine();

           if(input.equals("exit") || input.equals("quit") || input.equals("q")){

              break;
           }

         if(input.equals("menu") || input.equals("m")){
            this.gameView.initialMessage();
            continue;
         }

           processInput(input);
         }
   }

   public void processInput(String input) {

      //Split the input token into an array and convert each to lowercase
      String[] tokens = input.toLowerCase().trim().split(" ");

       HashSet<String> tokenSet = new HashSet<>();
       Collections.addAll(tokenSet, tokens);


       UserCommand command = null;
       String commandString = "";
       String tokenString = "";

      for(String token : tokenSet){
         if(this.gameModel.hasCommand(token.trim().strip())){
            System.out.println("Contains command" + token );
            command = this.gameModel.getCommand(token.trim().strip());
             commandString = token.trim().strip();
            break;
         }
      }



      //if a command was found, process the object to interact on
      if(command != null){
          boolean objectFound = false;
          for(String token : tokenSet){
              //process objects the current action can work on
              if(this.gameModel.hasCommandObject(command.getAction(),token.trim().strip())){
                  System.out.println("Contains object " + token );
                    objectFound = true;
                    this.executeCommand(command.getAction(), token.trim().strip());
                    tokenString = token.trim().strip();
                    break;
              }
          }

          if(!objectFound){
            this.gameView.displayOutput("You need to " + command.getAction() + " to an object");
            this.gameView.displayOutput("You can " + command.getAction() + " to "+ command.getObjects().toString());

          }

      }
      //else prompt the user to enter another command
      else {
         System.out.println("I am not sure, I understand you, try another command");
      }


      // find command in the command list

   }


   public void executeCommand(String command, String object){
       //Add to the command history
       // commandsHistory.put(command, object);
       UserCommand userCommand = this.gameModel.getCommand(command);

       System.out.println("USER COMMAND :::"+userCommand.toString());

       // check the command type. If is it inventory, check if the current action has the object the user is asking to pick
       if(userCommand.getType() == CommandType.INVENTORY){

            // check if the current state action has the object inside it
            if (this.gameModel.stateHasActivatedObject(object)){
                this.inventoryManager.addInventory(object);

                String newMessage = userCommand.getDescription().replace("{object}", object);
                this.gameView.displayOutput(newMessage);
                this.gameView.displayOutput(this.inventoryManager.getInventory().toString());
            }  else {
                this.gameView.displayOutput("You cannot Pick up this "+ object + " at this time");
            }

       }
       //if the command is ACTION, set it as the current state and execute
       else if (userCommand.getType() == CommandType.ACTION){
            // set the current action to this action
           this.gameModel.setCurrentStateAction(new CommandState(command, object, userCommand.getType()));
            String newMessage = userCommand.getDescription().replace("{object}", object);
            this.gameView.displayOutput(newMessage);
       }
       //if it is INTERACT,  set it as the current state and execute
       else if (userCommand.getType() == CommandType.INTERACT){
            //check the current Action is not null
            if(this.gameModel.hasCurrentState()){

            }else {
                this.gameView.displayOutput("");
            }
       }
       //set as the current action

//       currentState = new CommandState(command, object, userCommand.getType());

       //check action sequence and object sequence and update accordingly

   }
}
