/**
 * @author Victor K
 * @version 1.00
 * La classe MySQLDatabaseConnection permet de se connecter à une base de données MySQL.
 * Elle utilise le patron de conception Singleton pour garantir qu'une seule instance de la classe est créée et maintenue en mémoire.
 */
package fr.victork.DAO.mysql;

import fr.victork.Exception.ExceptionDAO;

import javax.swing.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;
import java.util.logging.Level;

import static fr.victork.Log.LoggerReverso.LOGGER;


/**
 * La classe MySQLDatabaseConnection permet de se connecter à une base de données MySQL.
 * Elle utilise le patron de conception Singleton pour garantir qu'une seule instance de la classe est créée et maintenue en mémoire.
 */
public class MySQLDatabaseConnection {
    //--------------------- CONSTANTS ------------------------------------------
    //--------------------- STATIC VARIABLES -----------------------------------
    final Properties dataProperties = new Properties();
    private static MySQLDatabaseConnection instance;
    private Connection connection;

    //--------------------- INSTANCE VARIABLES ---------------------------------
    //--------------------- CONSTRUCTORS ---------------------------------------

    /**
     * Constructeur privé de la classe MySQLDatabaseConnection
     * Il lit les informations de connexion depuis le fichier database.properties
     * et établit une connexion à la base de données MySQL
     * Il gère les exceptions qui peuvent être lancées en cas de problèmes de connexion.
     */
    private MySQLDatabaseConnection() {
        try {
            FileInputStream fis = new FileInputStream("database.properties");
            dataProperties.load(fis);
            // Exécute l'initialiseur de classe dans la classe Driver qui instancie le driver en mémoire
           // Class.forName(dataProperties.getProperty("jdbc.driver.class"));

            String url = dataProperties.getProperty("jdbc.url");
            String login = dataProperties.getProperty("jdbc.login");
            String password = dataProperties.getProperty("jdbc.password");

            connection = DriverManager.getConnection(url, login, password);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } catch (SQLException e) {
            new ExceptionDAO("Une erreur est survenue lors de la connexion à la base de données " +
                    e.getMessage(), 1);
        }
       /* catch (ClassNotFoundException classNotFoundException) {
            classNotFoundException.printStackTrace();
            System.exit(1);
            LOGGER.log(Level.SEVERE, classNotFoundException.getMessage());
        }*/
    }

    /**
     * Méthode statique qui permet de récupérer l'instance unique de la classe MySQLDatabaseConnection
     * Si l'instance n'existe pas ou si la connexion est fermée, elle la recréé.
     *
     * @return l'instance unique de la classe MySQLDatabaseConnection
     * @throws SQLException si une erreur se produit lors de la connexion à la base de données.
     */
    public static MySQLDatabaseConnection getInstance() throws SQLException {
        if (instance == null) {
            instance = new MySQLDatabaseConnection();
        } else if (instance.getConnection().isClosed()) {
            instance = new MySQLDatabaseConnection();
        }

        return instance;
    }

    //--------------------- STATIC METHODS -------------------------------------

    /**
     * Méthode permettant de fermer la connexion à la base de données lors de l'arrêt de l'application
     * Cette méthode est appelée via le ShutdownHook de l'application.
     */
    static {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                try {
                    if (MySQLDatabaseConnection.getInstance().getConnection() != null) {
                        try {
                            LOGGER.info("Database fermée");
                            MySQLDatabaseConnection.getInstance().getConnection().close();
                        } catch (SQLException ex) {
                            LOGGER.severe(ex.getMessage());
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    LOGGER.log(Level.SEVERE, e.getMessage());
                    JOptionPane.showMessageDialog(null, e.getMessage(),
                            "Erreur système", JOptionPane.ERROR_MESSAGE);
                    System.exit(1);
                }
            }
        });
    }

    //--------------------- INSTANCE METHODS -----------------------------------
    //--------------------- ABSTRACT METHODS -----------------------------------
    //--------------------- STATIC - GETTERS - SETTERS -------------------------

    /**
     * Getter de la connexion à la base de données MySQL
     *
     * @return la connexion à la base
     */
    public Connection getConnection() {
        return connection;
    }
    //--------------------- GETTERS - SETTERS ----------------------------------
    //--------------------- TO STRING METHOD------------------------------------
}
