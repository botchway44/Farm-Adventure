# Welcome to 🌾 Farm Adventure 🏕️

----

## Story Sequence

-------

Tracks how the actions, objects and messages interact with each other

Inventory commands require an ACTION command to be executed. In that sense, you can INTERACT or INVENTORY items in the
current action

| Action | Action TYPE | Comma Separated Reachable Objects |                  Activated Message                   |  Deactivated Message  |
|:------:|:-----------:|:---------------------------------:|:----------------------------------------------------:|:---------------------:|
|  walk  |   ACTION    |       fence, chest, cabbage       | You have walked to {object}, what do you want to do. | you walked to the ... |
|  take  |  INVENTORY  |       fence, chest, cabbage       | You have walked to {object}, what do you want to do. | you walked to the ... |
|  open  |  INTERACT   |       fence, chest, cabbage       | You have walked to {object}, what do you want to do. | you walked to the ... |
|  cut   |  INTERACT   |               grass               | You have walked to {object}, what do you want to do. | you walked to the ... |

From the story sequence above, with action **walk**, the objects **fence, chest, cabbage**  are reachable. When you
reach these objects, Activated message is used to prompt the user on what actions can be taken.

## Object Sequence

-------

The object sequence track how the Objects interacts with each other.

| Primary | Comma separated related to object |
 |:-------:|:---------------------------------:|
|   key   |            door, chest            | 

From the object sequence above, a key can be used to activate a door and a chest.

## Running The Game

---- 

##### Emoji Player

I discovered along the line my implementation allows me to let you play the text adventure with emojis. I have added a
couple of examples to the story file to help with the simulation.
Example Commands :

-

## Improvements

-------

- Create a Sequence for Rooms and allow Player to move to different Rooms
- Improve Parser to pick multiple objects and execute actions on them
- When a user enters an object, it should be able to prompt the user with the commands available for the object
- 