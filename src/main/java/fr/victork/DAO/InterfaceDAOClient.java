/**
 * @author Victor K
 * @version 1.00
 * Interface correspondant au design pattern DAO et spécifique à l'objet métier Client.
 * Utilise la généricité de Java pour permettre l'utilisation avec n'importe quelle classe implémentant l'interface DAO.
 * Ajoute une méthode supplémentaire findByIdClient permettant de récupérer tous les contrats d'un client spécifique.
 * @param <T> Type de l'objet métier implémentant l'interface DAO.
 */
package fr.victork.DAO;

import fr.victork.DAO.DAO;
import fr.victork.Entity.Client;
import fr.victork.Entity.Contrat;
import fr.victork.Exception.ExceptionDAO;
import fr.victork.Exception.ExceptionEntity;

import java.util.ArrayList;

/**
 * Interface correspondant au design pattern DAO et spécifique à l'objet métier Client.
 * Utilise la généricité de Java pour permettre l'utilisation avec n'importe quelle classe implémentant l'interface DAO.
 * Ajoute une méthode supplémentaire findByIdClient permettant de récupérer tous les contrats d'un client spécifique.
 *
 * @param <T> Type de l'objet métier implémentant l'interface DAO.
 */
public interface InterfaceDAOClient<T> extends DAO<T> {

    /**
     * Cette méthode permet de récupérer tous les contrats d'un client spécifique dans la base de données.
     *
     * @param client Client dont on souhaite récupérer les contrats.
     * @return Retourne une liste de Contrat associée au client spécifié.
     * @throws ExceptionEntity ExceptionEntity si une exception se produit lors de l'interaction avec l'entité <T>
     * @throws ExceptionDAO    ExceptionDAO si une exception se produit lors de l'interaction avec la base de données
     */
    ArrayList<Contrat> findByIdClient(Client client) throws ExceptionEntity, ExceptionDAO;
}
