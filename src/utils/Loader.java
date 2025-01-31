package utils;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class Loader {

    public static HashMap<String, UserCommand> loadCommands() {

        HashMap<String, UserCommand> commands = new HashMap<>();
        try {
            Scanner input = new Scanner(new File("res/story.txt"));

            while (input.hasNextLine()) {
                String line = input.nextLine();
                System.out.println(line);

                if (line.isEmpty()) {

                    System.out.println("Continuing");
                    continue;
                }


                //Split the file and check if it has 5 parts else throw an error
                String[] parts = line.split("\\|");

                if (parts.length != 5) throw new Exception("Story File Format Wrong");


                String[] commandObjects = parts[2].toLowerCase().trim().strip().split(",");
                HashSet<String> objectSet = new HashSet<>();
                for (String object : commandObjects) {
                    objectSet.add(object.trim().strip());
                }

                CommandType commandType = CommandType.ACTION;

                if (parts[1].strip().trim().equals("INVENTORY")) {
                    commandType = CommandType.INVENTORY;
                } else if (parts[1].strip().trim().equals("INTERACT")) {
                    commandType = CommandType.INTERACT;
                }

                UserCommand command = new UserCommand(parts[0].trim().strip().toLowerCase(), objectSet, parts[3], commandType);

                commands.put(parts[0].trim().strip(), command);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return commands;

    }

    public static HashMap<String, HashSet<String>> loadInteractiveSequence(String path) {

        HashMap<String, HashSet<String>> actionSequence = new HashMap<>();
        try {
            Scanner input = new Scanner(new File(path));

            while (input.hasNextLine()) {
                String line = input.nextLine();
                System.out.println(line);

                if (line.isEmpty()) {
                    continue;
                }


                //Split the file and check if it has 3 parts else throw an error
                String[] parts = line.split("\\|");


                if (parts.length != 2) {
                    throw new Exception("Format Wrong");
                }

                String[] commandObjects = parts[1].toLowerCase().trim().strip().split(",");
                HashSet<String> objectSet = new HashSet<>();
                for (String object : commandObjects) {
                    objectSet.add(object.trim().strip());
                }

                actionSequence.put(parts[0].trim().strip(), objectSet);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return actionSequence;
    }
}
