Thank you for taking the time to review this application, the codebase is included in the attached 7zip file.
Please decompress and navigate to the src\com\jayoheff directory, from there the application can be compiled using the following command :
javac -d classes constants/*.java impl/*.java interfaces/*.java util/*.java  vm/*.java main.java

To run the application please cd in to the classes directory and run the following
java com.jayoheff.Main

When the game starts it will
1: Prompt you to play a game or to quit Enter Y or y to continue N or n to quit. Other input will redisplay the menu options to the user.
2: It will then generate the decks and players.
3: The cards will be displayed on screen with an option for the player to Hit or Stand.
4: Should the player decide to Hit then their card score is calculated,
    If the player is bust their cards are displayed
    The delear is the winner and the player is prompted if they wish to play again.
    Alternatively is the user is the winner they're output as the winner and they are prmpted to continue.

Choose N to quit.

