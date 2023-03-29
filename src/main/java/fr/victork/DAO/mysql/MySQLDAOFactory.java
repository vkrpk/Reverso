/**
 * @author Victor K
 * @version 1.00
 * Classe permettant la création des DAO pour MySQL.
 */
package fr.victork.DAO.mysql;

import fr.victork.DAO.AbstractDAOFactory;
import fr.victork.DAO.DAO;
import fr.victork.DAO.InterfaceDAOClient;
import fr.victork.Entity.Client;
import fr.victork.Entity.EnumInstanceDeSociete;
import fr.victork.Entity.Prospect;
import fr.victork.Entity.Societe;

/**
 * Classe permettant la création des DAO pour MySQL.
 */
public class MySQLDAOFactory extends AbstractDAOFactory {
    //--------------------- CONSTANTS ------------------------------------------
    //--------------------- STATIC VARIABLES -----------------------------------
    //--------------------- INSTANCE VARIABLES ---------------------------------
    //--------------------- CONSTRUCTORS ---------------------------------------
    //--------------------- STATIC METHODS -------------------------------------
    //--------------------- INSTANCE METHODS -----------------------------------

    /**
     * Méthode permettant d'instancier un DAO pour la classe Client.
     *
     * @return Le DAO pour la classe Client.
     */
    public InterfaceDAOClient<Client> getClientDAO() {
        return new MySQLClientDAO();
    }

    /**
     * Méthode permettant d'instancier un DAO pour la classe Prospect.
     *
     * @return Le DAO pour la classe Prospect.
     */
    public DAO<Prospect> getProspectDAO() {
        return new MySQLProspectDAO();
    }
    //--------------------- ABSTRACT METHODS -----------------------------------
    //--------------------- STATIC - GETTERS - SETTERS -------------------------
    //--------------------- GETTERS - SETTERS ----------------------------------
    //--------------------- TO STRING METHOD------------------------------------
}
