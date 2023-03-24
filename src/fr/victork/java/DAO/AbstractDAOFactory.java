package fr.victork.java.DAO;

import fr.victork.java.DAO.mysql.MySQLClientDAO;
import fr.victork.java.Entity.Client;
import fr.victork.java.Entity.Prospect;

public abstract class AbstractDAOFactory {
    //--------------------- CONSTANTS ------------------------------------------
    //--------------------- STATIC VARIABLES -----------------------------------
    //--------------------- INSTANCE VARIABLES ---------------------------------
    //--------------------- CONSTRUCTORS ---------------------------------------
    //--------------------- STATIC METHODS -------------------------------------
    public static String getFactory(Class<? extends AbstractDAOFactory> abstractDAOFactory) {
        if (abstractDAOFactory.toString().equals("MySQLDAOFactory")) {
            return "MySQLDAOFactory";
        } else if (abstractDAOFactory.toString().equals("MongoDBDAOFactory")) {
            return "MongoDBDAOFactory";
        }
        return null;
    }

    //--------------------- INSTANCE METHODS -----------------------------------
    //--------------------- ABSTRACT METHODS -----------------------------------
    protected abstract DAO<Client> getClientDAO();

    protected abstract DAO<Prospect> getProspectDAO();
    //--------------------- STATIC - GETTERS - SETTERS -------------------------
    //--------------------- GETTERS - SETTERS ----------------------------------
    //--------------------- TO STRING METHOD------------------------------------
}
