# Scrabble Utility Application

The Scrabble Utility application is for use with keeping score for a round of scrabble that also allows for the user to consult a built-in dictionary, challenge other players words (Resulting in a fairly heftly penalty), and provides final statistics that are saved to a leader board.

* The program makes use of a database that contains the following tabels;
    * "entries" - Holds word information for use with the dictionary and challenge functions. Provides words, word types, and definitions
    * "playerScores" - Maintains each play that a player makes during a session. Table is dropped at the beginning of each new game and recreated to hold fresh data each time.
    * "leaderBoard" - Table that holds high scores. Initial false scores are inserted to the table the first time the program is run. This makes it so the players have scores to beat from the get go.
    
* Start menu
    * Game begins with a start menu prompt with the following options.
        * Start - Begins the game
        * Instructions - Opens users default web browser and calls the Hasbro official rules URL
        * Leader Board - Launches the leader board
        * Quit - Exits the program
        
* Game play
    * Game prompts the user to enter the number of players followed by another prompt to enter player names
    * Player names and current scores are displayed in the display.
    * The following options are provided to the player(s)
    
        * To the left of the "Submit word score" button is a blank field for the user to enter their score for their current turn. After a score is entered into the field and the button pressed the users score is entered into the database and added to the players score in the display. Pressing the button also displays a prompt to the window below the score stating which player's turn it is.
        * To the left of the "Dictionary" button is another text field where a word may be entered to check the built-in dictionary. After a word is entered and the button clicked the user will get one of two types of messages. If the word is present in the dictionary the word will appear in a pop-up window that will provide information about the word including word type and definition. **Words are not case sensitive.
        
        * To the left of the challenge button is a drop down menu where the current player can challenge another players last score by consulting the dictionary to see if their word was valid. After a name is selected and the Challenge button clicked a pop-up window will appear asking the user to enter the word to check. If the word is not present in the dictionary a pop-up message will inform the player that the challenge was successful and the challenged player loses all points from their last turn. On the other hand, if the word is in the dictionary a message will inform the player the the challenge failed and points will be deducted from the challenging players score equal to the challenged players last point entry.
        
        * The finish game button located at the bottom of the window transitions the user to the game results window. This window displays each players sname, highest individual score entered, and total score at games end. Fom this point the user can click the "Leader Board" button to see the top ten high scores recorded, click the "Return To Start" button to return to the original start menu, or press quit to exit the game.
        
* There are currently no know bugs for the program. I would, however, appreciate feedback if issues are discovered. Bugs can be sent to indierevolver@gmail.com

* Program was built using intellij with the Java 12.02 SDK. All databases are built in using SQLite and should not require any additional resources outside of a java IDE. All dependencies can be found within the pom.xml file
           
    