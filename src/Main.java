import controller.GameController;
import utils.Loader;
import utils.UserCommand;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {


        HashMap<String, HashSet<String>> objectSequence =   Loader.loadInteractiveSequence("res/object-sequence.txt");
        HashMap<String, UserCommand> commands =  Loader.loadCommands();

        GameController gameController = new GameController(commands, objectSequence );
        gameController.startGame();
    }
}



