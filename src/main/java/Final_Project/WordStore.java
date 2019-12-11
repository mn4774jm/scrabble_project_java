package Final_Project;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import static Final_Project.Database.dbURI;



public class WordStore {

    private String dbURI;

    WordStore(String databaseURI) throws SQLException {
        this.dbURI = databaseURI;

        try (Connection connection = DriverManager.getConnection(databaseURI);
             Statement statement = connection.createStatement()) {
            //Drop table each time a new game starts
            String dropCommand = "DROP TABLE IF EXISTS playerScores";
            statement.executeUpdate(dropCommand);

            //create variable to save sql statement for table creation
            String createTableSQL =
                    "CREATE TABLE IF NOT EXISTS playerScores (" +
                            "name TEXT NOT NULL," +
                            "score INTEGER NOT NULL," +
                            "playerID INTEGER NOT NULL," +
                            "playTime NUMBER not null)";
            //pass variable to execute sql update
            statement.executeUpdate(createTableSQL);
            //catch/throw runtime exception
        } catch (SQLException sqle) {
            throw new RuntimeException(sqle);
        }

    }

    public void addScore(scoreObject newName) throws SQLException {
        String insertSQL = "INSERT INTO playerScores VALUES (?,?,?,?)";

        Connection connection = DriverManager.getConnection(dbURI);
        PreparedStatement preparedStatement = connection.prepareStatement(insertSQL);

        preparedStatement.setString(1, newName.getPlayerName());
        preparedStatement.setInt(2, newName.getPlayerScore());
        preparedStatement.setInt(3, newName.getPlayerID());
        preparedStatement.setLong(4, newName.getDatePlayed().getTime());
        preparedStatement.execute();
        connection.close();
    }


    public WordObject checkWord(String wordSearched) {

        String mySQLsearchWord = "SELECT * FROM entries WHERE word = ? LIMIT 1";
        try (Connection connection = DriverManager.getConnection(dbURI);


             PreparedStatement preparedStatement = connection.prepareStatement(mySQLsearchWord)) {

            //setting parameter with user entered ID
            preparedStatement.setString(1, wordSearched);
            //execute query
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                //pull row information to create new object
                String word = resultSet.getString("word");
                String wordtype = resultSet.getString("wordtype");
                String definition = resultSet.getString("definition");

                //create new object with above variables
                return new WordObject(word, wordtype, definition);
            }
            return null;
        } catch (SQLException sqle) {
            // return null if no matches found
            System.out.println(sqle);
            return null;
        }

    }

    public scoreCounting getCurrentScore(int id) {
        String sqlIdSearch = "SELECT SUM(Score) AS playerSum FROM playerScores WHERE playerID = ?";
        try (Connection connection = DriverManager.getConnection(dbURI);
             PreparedStatement preparedStatement = connection.prepareStatement(sqlIdSearch)) {
            List<scoreObject> scores = new ArrayList<>();
            //setting parameter with user entered ID
            preparedStatement.setInt(1, id);
            //execute query
            ResultSet resultSet = preparedStatement.executeQuery();

            int score = resultSet.getInt("playerSum");

            //create new object with above variables
            scoreCounting scoreGrab = new scoreCounting(score);
//                    scores.add(scoreGrab);
//                }
            //return object
            return scoreGrab;
        } catch (SQLException sqle) {
            // return null if no matches found
            return null;
        }
    }

    public LastScoreObject retreiveLastScore(int id) {
        String sqlIdSearch = "SELECT score FROM playerScores WHERE playerID = ? AND score > 0 ORDER by playTime DESC LIMIT 1";
        try (Connection connection = DriverManager.getConnection(dbURI);
             PreparedStatement preparedStatement = connection.prepareStatement(sqlIdSearch)) {

            //setting parameter with user entered name
            preparedStatement.setInt(1, id);
            //execute query
            ResultSet resultSet = preparedStatement.executeQuery();
            int score = resultSet.getInt("score");

            //create new object with above variables
            LastScoreObject scoreGrab = new LastScoreObject(score);

            //return object
            return scoreGrab;
        } catch (SQLException sqle) {
            // return null if no matches found
            return null;
        }
    }

    public Vector<Vector> finalScoreData() {

        try (Connection connection = DriverManager.getConnection(dbURI);
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery("SELECT name, MAX(score) as Max, SUM(score) as Total from playerScores");

            Vector<Vector> newVector = new Vector<>();

            while(resultSet.next()) {

                String name = resultSet.getString("name");
                int maxScore = resultSet.getInt("Max");
                int totalScore = resultSet.getInt("Total");

                Vector v = new Vector();
                v.add(name);
                v.add(maxScore);
                v.add(totalScore);

                newVector.add(v);

            }

            //return object
            return newVector;
        } catch (SQLException sqle) {
            // return null if no matches found
            return null;
        }

    }
}
