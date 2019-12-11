package Final_Project;

import java.sql.SQLException;
import static Final_Project.Database.dbURI;

public class main {

    static GamePlayGUI gamePlayGUI;

    //create database
    public static void main(String[] args) throws SQLException {
        String databaseURI = dbURI;

        WordStore wordStore = new WordStore(databaseURI);
        WordController wordController = new WordController(wordStore);
        gamePlayGUI = new GamePlayGUI(wordController);

    }

}
