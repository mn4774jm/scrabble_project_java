package Final_Project;

import java.sql.*;

import static Final_Project.Database.dbURI;



public class WordStore {

    private String dbURI;

    WordStore(String databaseURI) throws SQLException {
        this.dbURI = databaseURI;

        try(Connection connection = DriverManager.getConnection(databaseURI);
            Statement statement = connection.createStatement()) {
            //create variable to save sql statement for table creation
            String createTableSQL =
                    "CREATE TABLE IF NOT EXISTS playerScores (" +
                            "name TEXT NOT NULL," +
                            "score INTEGER NOT NULL," +
                            "turn INTEGER NOT NULL)";
            //pass variable to execute sql update
            statement.executeUpdate(createTableSQL);
            //catch/throw runtime exception
        }catch (SQLException sqle){
            throw new RuntimeException(sqle);
        }


    }

        public WordObject checkWord(String wordSearched){


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
    }
