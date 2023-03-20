package fr.victork.java.DAO;

import com.mysql.jdbc.Connection;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import static fr.victork.java.Log.LoggerReverso.LOGGER;


public class DatabaseConnection {
    //--------------------- CONSTANTS ------------------------------------------
    //--------------------- STATIC VARIABLES -----------------------------------
    private static DatabaseConnection INSTANCE = null;
    final Properties dataProperties = new Properties();

    private static Connection connection;

    //--------------------- INSTANCE VARIABLES ---------------------------------
    public static DatabaseConnection getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DatabaseConnection();
        }
        return INSTANCE;
    }

    //--------------------- CONSTRUCTORS ---------------------------------------
    private DatabaseConnection() {
        try {
            File fichier = new File("database.properties");
            FileInputStream input = new FileInputStream(fichier);
            dataProperties.load(input);
            Connection connection = (Connection) DriverManager.getConnection(
                    dataProperties.getProperty("url"),
                    dataProperties.getProperty("login"),
                    dataProperties.getProperty("password")
            );
            System.out.println(connection);
        } catch (IOException ioException) {
            System.out.println(ioException.getMessage());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //--------------------- STATIC METHODS -------------------------------------
    static {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                if (connection != null) {
                    try {
                        LOGGER.info("Database fermée");
                        connection.close();
                    } catch (SQLException ex) {
                        LOGGER.severe(ex.getMessage());
                    }
                }
            }
        });
    }
    //--------------------- INSTANCE METHODS -----------------------------------

    //--------------------- ABSTRACT METHODS -----------------------------------
    //--------------------- STATIC - GETTERS - SETTERS -------------------------
    //--------------------- GETTERS - SETTERS ----------------------------------
    //--------------------- TO STRING METHOD------------------------------------
}
