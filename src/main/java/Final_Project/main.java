package Final_Project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import static Final_Project.Database.dbURI;

public class main {

    static GamePlayGUI gamePlayGUI;



    //create database
    public static void main(String[] args) {
        String databaseURI = dbURI;

        WordStore wordStore = new WordStore(databaseURI);
        WordController wordController = new WordController(wordStore);
        gamePlayGUI = new GamePlayGUI(wordController);

//        Map<String, String> playerData = new HashMap<>();
//        GamePlayGUI gui = new GamePlayGUI(playerData);
//



    }

}
