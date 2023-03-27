package fr.victork.java.DAO;

import fr.victork.java.DAO.mongoDB.MongoDBDAOFactory;
import fr.victork.java.DAO.mysql.MySQLClientDAO;
import fr.victork.java.DAO.mysql.MySQLDAOFactory;
import fr.victork.java.Entity.Client;
import fr.victork.java.Entity.Prospect;

public abstract class AbstractDAOFactory {
    //--------------------- CONSTANTS ------------------------------------------
    //--------------------- STATIC VARIABLES -----------------------------------
    //--------------------- INSTANCE VARIABLES ---------------------------------
    //--------------------- CONSTRUCTORS ---------------------------------------
    //--------------------- STATIC METHODS -------------------------------------
    public static AbstractDAOFactory getFactory(Class<? extends AbstractDAOFactory> abstractDAOFactory) {
        if (abstractDAOFactory.getSimpleName().equals("MongoDBDAOFactory")) {
            return new MongoDBDAOFactory();
        } else if (abstractDAOFactory.getSimpleName().equals("MySQLDAOFactory")) {
            return new MySQLDAOFactory();
        }
        return null;
    }

    //--------------------- INSTANCE METHODS -----------------------------------
    //--------------------- ABSTRACT METHODS -----------------------------------
    public abstract DAO<Client> getClientDAO();

    public abstract DAO<Prospect> getProspectDAO();
    //--------------------- STATIC - GETTERS - SETTERS -------------------------
    //--------------------- GETTERS - SETTERS ----------------------------------
    //--------------------- TO STRING METHOD------------------------------------
}
