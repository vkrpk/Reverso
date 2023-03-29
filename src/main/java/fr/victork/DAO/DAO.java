/**
 * @author Victor K
 * @version 1.00
 * Interface correspondant au design pattern DAO
 * elle utilise la généricité de Java pour être utilisée avec différents types d'entités.
 */
package fr.victork.DAO;

import fr.victork.DAO.mysql.MySQLDatabaseConnection;
import fr.victork.Exception.ExceptionDAO;
import fr.victork.Exception.ExceptionEntity;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

/**
 * Interface correspondant au design pattern DAO
 * elle utilise la généricité de Java pour être utilisée avec différents types d'entités.
 *
 * @param <T> Type d'entité pour laquelle la DAO est créée.
 */
public interface DAO<T> {
    //--------------------- CONSTANTS ------------------------------------------
    //--------------------- STATIC VARIABLES -----------------------------------
    //--------------------- INSTANCE VARIABLES ---------------------------------
    //--------------------- CONSTRUCTORS ---------------------------------------
    //--------------------- STATIC METHODS -------------------------------------
    //--------------------- INSTANCE METHODS -----------------------------------
    //--------------------- ABSTRACT METHODS -----------------------------------

    /**
     * Cette méthode permet de sauvegarder un objet <T> dans la base de données.
     * Si le client existe déjà dans la base de données, les informations sont mises à jour. Sinon, un nouveau document est créé.
     *
     * @param obj Objet à sauvegarder dans la base de données.
     * @throws ExceptionEntity si une exception se produit lors de l'interaction avec l'entité <T>
     * @throws ExceptionDAO    si une exception se produit lors de l'interaction avec la base de données
     */
    void save(T obj) throws ExceptionEntity, ExceptionDAO;

    /**
     * Cette méthode permet de récupérer un objet <T> à partir de son identifiant unique dans la base de données.
     *
     * @param id Identifiant unique de l'objet à récupérer.
     * @throws ExceptionEntity si une exception se produit lors de l'interaction avec l'entité <T>
     * @throws ExceptionDAO    si une exception se produit lors de l'interaction avec la base de données
     */
    T find(Integer id) throws ExceptionEntity, ExceptionDAO;

    /**
     * Cette méthode permet de supprimer un objet <T> de la base de données à partir de son identifiant unique.
     *
     * @param id Identifiant unique de l'objet à supprimer de la base de données.
     * @throws ExceptionDAO si une exception se produit lors de l'interaction avec la base de données
     */
    void delete(Integer id) throws ExceptionDAO;

    /**
     * Cette méthode permet de récupérer tous les objets <T> présents dans la base de données.
     *
     * @return Retourne une liste d'objet <T> présent dans la base de données.
     * @throws ExceptionEntity si une exception se produit lors de l'interaction avec l'entité <T>
     * @throws ExceptionDAO    si une exception se produit lors de l'interaction avec la base de données
     */
    ArrayList<T> findAll() throws ExceptionEntity, ExceptionDAO;
    //--------------------- STATIC - GETTERS - SETTERS -------------------------
    //--------------------- GETTERS - SETTERS ----------------------------------
    //--------------------- TO STRING METHOD------------------------------------
}
