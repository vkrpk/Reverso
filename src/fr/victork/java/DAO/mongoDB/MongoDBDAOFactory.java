package fr.victork.java.DAO.mongoDB;

import fr.victork.java.DAO.AbstractDAOFactory;
import fr.victork.java.DAO.DAO;
import fr.victork.java.Entity.Client;
import fr.victork.java.Entity.Prospect;

public class MongoDBDAOFactory extends AbstractDAOFactory {
    @Override
    public DAO<Client> getClientDAO() {
        return new MongoDBClientDAO();
    }

    @Override
    public DAO<Prospect> getProspectDAO() {
        return new MongoDBProspectDAO();
    }
    //--------------------- CONSTANTS ------------------------------------------
    //--------------------- STATIC VARIABLES -----------------------------------
    //--------------------- INSTANCE VARIABLES ---------------------------------
    //--------------------- CONSTRUCTORS ---------------------------------------
    //--------------------- STATIC METHODS -------------------------------------
    //--------------------- INSTANCE METHODS -----------------------------------
    //--------------------- ABSTRACT METHODS -----------------------------------
    //--------------------- STATIC - GETTERS - SETTERS -------------------------
    //--------------------- GETTERS - SETTERS ----------------------------------
    //--------------------- TO STRING METHOD------------------------------------
}
