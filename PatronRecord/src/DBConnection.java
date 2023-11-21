import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {

    // variables a modifier en fonction de la base
    String userName = "root";
    String password = "";
    String serverName = "localhost";
    //Attention, sous MAMP, le port est 8889
    String portNumber = "3306";
    String tableName = "personne";

    // iL faut une base nommee testPersonne !
    private static String dbName = "testpersonne";

    private static Connection instance;

    private DBConnection() throws SQLException {
        // creation de la connection
        Properties connectionProps = new Properties();
        connectionProps.put("user", userName);
        connectionProps.put("password", password);
        String urlDB = "jdbc:mysql://" + serverName + ":";
        urlDB += portNumber + "/" + dbName;
        instance = DriverManager.getConnection(urlDB, connectionProps);
    }


    public static synchronized Connection getConnection(){
        if(instance == null){
            try {
                new DBConnection();
            }
            catch (SQLException e){
                e.printStackTrace();
            }
        }
        return instance;
    }


    public static synchronized void setNomDB(String nomDB) throws SQLException {
        if(nomDB != null && !nomDB.equals(dbName)){
            dbName = nomDB;
            DBConnection.instance = null;
        }
    }


}
