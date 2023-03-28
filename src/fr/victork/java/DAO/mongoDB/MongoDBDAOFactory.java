/**
 * @author Victor K
 * @version 1.00
 * Cette classe hérite de la classe abstraite AbstractDAOFactory et implémente les méthodes pour la création d'objets DAO pour la base de données MongoDB.
 * Elle fournit une méthode pour récupérer un DAO pour la gestion des objets Client dans la base de données MongoDB et
 * une méthode pour récupérer un DAO pour la gestion des objets Prospect.
 */
package fr.victork.java.DAO.mongoDB;

import fr.victork.java.DAO.AbstractDAOFactory;
import fr.victork.java.DAO.DAO;
import fr.victork.java.DAO.InterfaceDAOClient;
import fr.victork.java.Entity.Client;
import fr.victork.java.Entity.Prospect;

/**
 * Cette classe hérite de la classe abstraite AbstractDAOFactory et implémente les méthodes pour la création d'objets DAO pour la base de données MongoDB.
 * Elle fournit une méthode pour récupérer un DAO pour la gestion des objets Client dans la base de données MongoDB et
 * une méthode pour récupérer un DAO pour la gestion des objets Prospect.
 */
public class MongoDBDAOFactory extends AbstractDAOFactory {
    /**
     * obtenir l'instance DAO nécessaire pour interagir avec la base de données MongoDB pour la gestion des clients
     *
     * @return retourne une instance de la classe MongoDBClientDAO
     */
    @Override
    public InterfaceDAOClient<Client> getClientDAO() {
        return new MongoDBClientDAO();
    }

    /**
     * obtenir l'instance DAO nécessaire pour interagir avec la base de données MongoDB pour la gestion des prospects
     *
     * @return retourne une instance de la classe MongoDBProspectDAO
     */
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
