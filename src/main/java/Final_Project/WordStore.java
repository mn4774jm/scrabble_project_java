package Final_Project;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class WordStore {

    private String dbURI;

    WordStore(String databaseURI) throws SQLException {
        this.dbURI = databaseURI;
        //create new tables (excluding the entries table due to sheer size)
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

            //script to create leaderboard table
            String createLeaderboard =
                    "CREATE TABLE IF NOT EXISTS leaderBoard (" +
                            "name TEXT NOT NULL," +
                            "Total INTEGER NOT NULL)";
            //execute
            statement.executeUpdate(createLeaderboard);

            //catch/throw runtime exception
        } catch (SQLException sqle) {
            throw new RuntimeException(sqle);
        }
        //call method to populate if empty
        populateLearderBoard();
    }

    public void populateLearderBoard() throws SQLException{
        //This method uses conditional logic to check if the leaderBoard table is empty
        //If so; the table is populated with a hardcoded starter set
        Connection connection = DriverManager.getConnection(dbURI);
        Statement statement = connection.createStatement();
        //query to check for rows in table
        String checkForEmpty =
                "SELECT count(*) AS count FROM leaderBoard";
        //execute and store to result set
        ResultSet resultSet = statement.executeQuery(checkForEmpty);
        //get value for query value
        int rowCount = resultSet.getInt("count");
        //if table is empty insert rows
        if(rowCount == 0) {
            String insertSQL =
                    //These insert could be more concise but for reabability I left as is
                    "INSERT INTO leaderBoard VALUES ('Krognak The Unbreakable',200);"+
                    "INSERT INTO leaderBoard VALUES ('Hans Gruber', 190);"+
                    "INSERT INTO leaderBoard VALUES ('The Eagles... collectively',180);"+
                    "INSERT INTO leaderBoard VALUES ('Derek the moderalety learned', 170);"+
                    "INSERT INTO leaderBoard VALUES ('A very surprised Randy Quaid', 160);";
            //execute update
            statement.executeUpdate(insertSQL);
            //close connection
            connection.close();
        }
        connection.close();
    }

    public void addScore(scoreObject newName) throws SQLException {
        //uses prepared statements to insert new player score row
        String insertSQL = "INSERT INTO playerScores VALUES (?,?,?,?)";
        //connect to DB
        Connection connection = DriverManager.getConnection(dbURI);
        PreparedStatement preparedStatement = connection.prepareStatement(insertSQL);
        //assign prepared statements
        preparedStatement.setString(1, newName.getPlayerName());
        preparedStatement.setInt(2, newName.getPlayerScore());
        preparedStatement.setInt(3, newName.getPlayerID());
        preparedStatement.setLong(4, newName.getDatePlayed().getTime());
        //execure prepared statements and close connection
        preparedStatement.execute();
        connection.close();
    }

    public WordObject checkWord(String wordSearched) {
        //SQL statement to search DB with user's word
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

    public int getCurrentScore(int id) {
        String sqlIdSearch = "SELECT SUM(Score) AS playerSum FROM playerScores WHERE playerID = ?";
        try (Connection connection = DriverManager.getConnection(dbURI);
             PreparedStatement preparedStatement = connection.prepareStatement(sqlIdSearch)) {
            //setting parameter with user entered ID
            preparedStatement.setInt(1, id);
            //execute query and save to result set
            ResultSet resultSet = preparedStatement.executeQuery();
            //get sum back and store to variable
            int score = resultSet.getInt("playerSum");
            //return object
            return score;
        } catch (SQLException sqle) {
            // return null if no matches found
            return 0;
        }
    }

    public int retreiveLastScore(int id) {
        String sqlIdSearch = "SELECT score FROM playerScores WHERE playerID = ? AND score > 0 ORDER by playTime DESC LIMIT 1";
        try (Connection connection = DriverManager.getConnection(dbURI);
             PreparedStatement preparedStatement = connection.prepareStatement(sqlIdSearch)) {

            //setting parameter with user entered name
            preparedStatement.setInt(1, id);
            //execute query
            ResultSet resultSet = preparedStatement.executeQuery();

            //return last score
            return resultSet.getInt("score");
        } catch (SQLException sqle) {
            // return zero if no matches found
            return 0;
        }
    }

    public Vector<Vector> finalScoreData() {
        //Connect to database
        try (Connection connection = DriverManager.getConnection(dbURI);
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery("SELECT name, MAX(score) as Max, SUM(score) as Total from playerScores group by name");
            Vector<Vector> newVector = new Vector<>();
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                int maxScore = resultSet.getInt("Max");
                int totalScore = resultSet.getInt("Total");
                //create new Vector item and store data as vectors
                Vector v = new Vector();
                v.add(name);
                v.add(maxScore);
                v.add(totalScore);

                newVector.add(v);

            }
            //return Vector
            return newVector;
        } catch (SQLException sqle) {
            // return null if no matches found
            return null;
        }
    }

    public MVPObject mvp() {
        //Connect to database
        try (Connection connection = DriverManager.getConnection(dbURI);
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery("SELECT name, MAX(score) as Max from playerScores");

            String name = resultSet.getString("name");
            int maxScore = resultSet.getInt("Max");
            MVPObject mvp = new MVPObject(name, maxScore);

            //return object
            return mvp;

        } catch (SQLException sqle) {
            // return null if no matches found
            return null;
        }
    }

    public winnerObject winner() {
        //Connect to database
        try (Connection connection = DriverManager.getConnection(dbURI);
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery("SELECT name, SUM(score) as Total from playerScores group by name order by Total DESC LIMIT 1");

            String name = resultSet.getString("name");
            int total = resultSet.getInt("Total");
            winnerObject win = new winnerObject(name, total);

            //return object
            return win;

        } catch (SQLException sqle) {
            // return null if no matches found
            return null;
        }
    }

    public void addWinningScore(winnerObject newName) throws SQLException {
        String insertSQL = "INSERT INTO leaderBoard VALUES (?,?)";

        Connection connection = DriverManager.getConnection(dbURI);
        PreparedStatement preparedStatement = connection.prepareStatement(insertSQL);
        preparedStatement.setString(1, newName.getName());
        preparedStatement.setInt(2, newName.getPlaySum());
        preparedStatement.execute();
        connection.close();

    }

    public Vector<Vector> highScores() {

        //Connect to database
        try (Connection connection = DriverManager.getConnection(dbURI);
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery("SELECT name, Total from leaderBoard order by Total DESC LIMIT 10");
            Vector<Vector> newVector = new Vector<>();
            while(resultSet.next()) {
                String name = resultSet.getString("name");
                int total = resultSet.getInt("Total");
                Vector v = new Vector();
                v.add(name);
                v.add(total);

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

