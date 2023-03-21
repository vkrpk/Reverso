package fr.victork.java.DAO;

import fr.victork.java.Exception.ExceptionDAO;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

import static fr.victork.java.Log.LoggerReverso.LOGGER;


public class DatabaseConnection {
    //--------------------- CONSTANTS ------------------------------------------
    //--------------------- STATIC VARIABLES -----------------------------------
    final Properties dataProperties = new Properties();
    public static Connection connection;

    //--------------------- INSTANCE VARIABLES ---------------------------------
    public static Connection getConnection() throws Exception {
        if (connection == null) {
            new DatabaseConnection();
        }
        return connection;
    }

    //--------------------- CONSTRUCTORS ---------------------------------------
    private DatabaseConnection() throws Exception {
        try {
            FileInputStream fis = new FileInputStream("database.properties");
            dataProperties.load(fis);
            // Exécute l'initialiseur de classe dans la classe Driver qui instancie le driver en mémoire
            Class.forName(dataProperties.getProperty("jdbc.driver.class"));

            String url = dataProperties.getProperty("jdbc.url");
            String login = dataProperties.getProperty("jdbc.login");
            String password = dataProperties.getProperty("jdbc.password");

            connection = DriverManager.getConnection(url, login, password);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } catch (SQLException e) {
            new ExceptionDAO("Une erreur est survenue lors de la connexion à la base de données " +
                    e.getMessage() + e.getCause(), 1);
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
