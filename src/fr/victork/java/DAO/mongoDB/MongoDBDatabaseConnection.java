package fr.victork.java.DAO.mongoDB;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

public class MongoDBDatabaseConnection {
    //--------------------- CONSTANTS ------------------------------------------
    //--------------------- STATIC VARIABLES -----------------------------------
    private static MongoDBDatabaseConnection instance;
    //--------------------- INSTANCE VARIABLES ---------------------------------
    private MongoDatabase mongoDatabase;

    //--------------------- CONSTRUCTORS ---------------------------------------
    private MongoDBDatabaseConnection() {
        MongoClient mongoClient = new MongoClient("localhost", 27017);
        mongoDatabase = mongoClient.getDatabase("reverso");
    }

    //--------------------- STATIC METHODS -------------------------------------
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
    public MongoDatabase getConnection() {
        return mongoDatabase;
    }
    //--------------------- TO STRING METHOD------------------------------------
}
