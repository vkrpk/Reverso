/**
 * @author Victor K
 * @version 1.00
 * Cette classe permet de se connecter à une base de données MongoDB et de récupérer la base de données "reverso".
 */
package fr.victork.DAO.mongoDB;


import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

/**
 * Cette classe permet de se connecter à une base de données MongoDB et de récupérer la base de données "reverso".
 */
public class MongoDBDatabaseConnection {
    //--------------------- CONSTANTS ------------------------------------------
    //--------------------- STATIC VARIABLES -----------------------------------
    private static MongoDBDatabaseConnection instance;
    //--------------------- INSTANCE VARIABLES ---------------------------------
    private MongoDatabase mongoDatabase;

    //--------------------- CONSTRUCTORS ---------------------------------------

    /**
     * Constructeur privé pour empêcher l'instanciation de la classe depuis l'extérieur.
     * Initialise une connexion à la base de données MongoDB "reverso".
     */
    private MongoDBDatabaseConnection() {
        MongoClient mongoClient = new MongoClient("localhost", 27017);
        //MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        mongoDatabase = mongoClient.getDatabase("reverso");
    }

    //--------------------- STATIC METHODS -------------------------------------

    /**
     * Méthode statique qui renvoie l'instance unique de la classe.
     *
     * @return Instance unique de la classe MongoDBDatabaseConnection
     */
    public static MongoDBDatabaseConnection getInstance() {
        if (instance == null) {
            instance = new MongoDBDatabaseConnection();
        }
        return instance;
    }

    //--------------------- INSTANCE METHODS -----------------------------------
    //--------------------- ABSTRACT METHODS -----------------------------------
    //--------------------- STATIC - GETTERS - SETTERS -------------------------
    //--------------------- GETTERS - SETTERS ----------------------------------

    /**
     * Getter pour récupérer la base de données MongoDB "reverso".
     *
     * @return Base de données MongoDB "reverso"
     */
    public MongoDatabase getConnection() {
        return mongoDatabase;
    }
    //--------------------- TO STRING METHOD------------------------------------
}
