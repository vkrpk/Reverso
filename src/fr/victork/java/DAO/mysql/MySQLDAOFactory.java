package fr.victork.java.DAO.mysql;

import fr.victork.java.DAO.AbstractDAOFactory;
import fr.victork.java.DAO.DAO;
import fr.victork.java.Entity.Client;
import fr.victork.java.Entity.EnumInstanceDeSociete;
import fr.victork.java.Entity.Prospect;
import fr.victork.java.Entity.Societe;

public class MySQLDAOFactory extends AbstractDAOFactory {
    //--------------------- CONSTANTS ------------------------------------------
    //--------------------- STATIC VARIABLES -----------------------------------
    //--------------------- INSTANCE VARIABLES ---------------------------------
    //--------------------- CONSTRUCTORS ---------------------------------------
    //--------------------- STATIC METHODS -------------------------------------
    //--------------------- INSTANCE METHODS -----------------------------------
    public DAO<Client> getClientDAO() {
        return new MySQLClientDAO();
    }

    public DAO<Prospect> getProspectDAO() {
        return new MySQLProspectDAO();
    }
    //--------------------- ABSTRACT METHODS -----------------------------------
    //--------------------- STATIC - GETTERS - SETTERS -------------------------
    //--------------------- GETTERS - SETTERS ----------------------------------
    //--------------------- TO STRING METHOD------------------------------------
}
