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
    //--------------------- INSTANCE METHODS -----------------------------------
    //--------------------- ABSTRACT METHODS -----------------------------------
    public abstract DAO<Client> getClientDAO();

    public abstract DAO<Prospect> getProspectDAO();
    //--------------------- STATIC - GETTERS - SETTERS -------------------------
    //--------------------- GETTERS - SETTERS ----------------------------------
    //--------------------- TO STRING METHOD------------------------------------
}
