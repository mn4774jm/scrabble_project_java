package Final_Project;

import java.sql.SQLException;
import static Final_Project.Database.dbURI;

public class main {
    static GamePlayGUI gamePlayGUI;


        //create database and call gui
    public static void main (String[]args) throws SQLException {
        String databaseURI = dbURI;
        WordStore wordStore = new WordStore(databaseURI);
        WordController wordController = new WordController(wordStore);
        gamePlayGUI = new GamePlayGUI(wordController);

    }
    //restart calls end in the gameplay gui to dispose of the current instance and then sets everything up/creates the
    //GUI again from scratch.
    public static void restart() throws SQLException {
        gamePlayGUI.end();
        String databaseURI = dbURI;
        WordStore wordStore = new WordStore(databaseURI);
        WordController wordController = new WordController(wordStore);
        gamePlayGUI = new GamePlayGUI(wordController);
    }

}
