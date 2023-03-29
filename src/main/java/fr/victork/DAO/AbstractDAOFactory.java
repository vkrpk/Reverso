/**
 * @author Victor K
 * @version 1.00
 * La classe abstraite {@code AbstractDAOFactory} définit une méthode pour obtenir une instance d'une fabrique DAO
 * concrète pour travailler avec une source de données spécifique.
 */
package fr.victork.DAO;

import fr.victork.DAO.mongoDB.MongoDBDAOFactory;
import fr.victork.DAO.mysql.MySQLClientDAO;
import fr.victork.DAO.mysql.MySQLDAOFactory;
import fr.victork.Entity.Client;
import fr.victork.Entity.Prospect;

/**
 * La classe abstraite {@code AbstractDAOFactory} définit une méthode pour obtenir une instance d'une fabrique DAO
 * concrète pour travailler avec une source de données spécifique.
 */
public abstract class AbstractDAOFactory {
    //--------------------- CONSTANTS ------------------------------------------
    //--------------------- STATIC VARIABLES -----------------------------------
    //--------------------- INSTANCE VARIABLES ---------------------------------
    //--------------------- CONSTRUCTORS ---------------------------------------
    //--------------------- STATIC METHODS -------------------------------------

    /**
     * Obtient une instance de la fabrique DAO concrète pour travailler avec une source de données spécifique.
     *
     * @param abstractDAOFactory la classe de la fabrique DAO concrète à utiliser.
     * @return la fabrique DAO concrète.
     */
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

    /**
     * Obtient une instance de l'objet DAO pour travailler avec les données Client.
     *
     * @return une instance de DAO pour Client.
     */
    public abstract InterfaceDAOClient<Client> getClientDAO();

    /**
     * Obtient une instance de l'objet DAO pour travailler avec les données Prospect.
     *
     * @return une instance de DAO pour Prospect.
     */
    public abstract DAO<Prospect> getProspectDAO();
    //--------------------- STATIC - GETTERS - SETTERS -------------------------
    //--------------------- GETTERS - SETTERS ----------------------------------
    //--------------------- TO STRING METHOD------------------------------------
}
