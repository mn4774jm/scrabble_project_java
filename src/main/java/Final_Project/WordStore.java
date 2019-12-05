package Final_Project;

import java.sql.*;

import static Final_Project.Database.dbURI;



public class WordStore {

    private String dbURI;

    WordStore(String databaseURI) {
        this.dbURI = databaseURI;

//        try (Connection conn = DriverManager.getConnection(dbURI);
//             Statement statement = conn.createStatement()) {
//            String createTable = "CREATE TABLE if not exists entries " +
//                    "(word varchar(25) NOT NULL, " +
//                    "wordtype varchar(20) NOT NULL, " +
//                    "definition text NOT NULL);" +
//                    "INSERT INTO entries VALUES
//            statement.executeUpdate(createTable);
//
//        } catch (SQLException sqle) {
//            throw new RuntimeException(sqle);
//        }

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
