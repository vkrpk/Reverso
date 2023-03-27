package fr.victork.java.DAO.mysql;

import fr.victork.java.Exception.ExceptionDAO;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;
import java.util.logging.Level;

import static fr.victork.java.Log.LoggerReverso.LOGGER;


public class MySQLDatabaseConnection {
    //--------------------- CONSTANTS ------------------------------------------
    //--------------------- STATIC VARIABLES -----------------------------------
    final Properties dataProperties = new Properties();
    private static MySQLDatabaseConnection instance;
    private Connection connection;

    //--------------------- INSTANCE VARIABLES ---------------------------------
    //--------------------- CONSTRUCTORS ---------------------------------------
    private MySQLDatabaseConnection() {
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
                    e.getMessage(), 1);
        } catch (ClassNotFoundException classNotFoundException) {
            classNotFoundException.printStackTrace();
            System.exit(1);
            LOGGER.log(Level.SEVERE, classNotFoundException.getMessage());
        }
    }

    public static MySQLDatabaseConnection getInstance() throws SQLException {
        if (instance == null) {
            instance = new MySQLDatabaseConnection();
        } else if (instance.getConnection().isClosed()) {
            instance = new MySQLDatabaseConnection();
        }

        return instance;
    }

    //--------------------- STATIC METHODS -------------------------------------
 /*   static {
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
    }*/
    //--------------------- INSTANCE METHODS -----------------------------------
    //--------------------- ABSTRACT METHODS -----------------------------------
    //--------------------- STATIC - GETTERS - SETTERS -------------------------
    public Connection getConnection() {
        return connection;
    }
    //--------------------- GETTERS - SETTERS ----------------------------------
    //--------------------- TO STRING METHOD------------------------------------
}
