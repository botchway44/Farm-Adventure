package view;

import utils.Constants;

public class GameView {


    public void displayOutput(String output) {
        System.out.println(output);

    }

    public void promptUserForInput(String output) {
        System.out.print(output);
    }


    public void initialMessage(String command) {
        clearConsole();
        System.out.println(Constants.INITIAL_PROMPT);
        System.out.println(Constants.INSTRUCTIONS.replace("{commands}", command));
    }

    public static void clearConsole() {
        for (int i = 0; i < 50; ++i) System.out.println();
    }
}
