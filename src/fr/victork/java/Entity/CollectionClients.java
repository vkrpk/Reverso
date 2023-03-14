/**
 * @author Victor K
 * @version 1.00
 * Cette classe représente la liste de tous les clients
 */
package fr.victork.java.Entity;

import java.util.ArrayList;

public class CollectionClients {
    //--------------------- CONSTANTS ------------------------------------------
    //--------------------- STATIC VARIABLES -----------------------------------
    private static ArrayList<Client> collection = new ArrayList<>();
    //--------------------- INSTANCE VARIABLES ---------------------------------
    //--------------------- CONSTRUCTORS ---------------------------------------
    //--------------------- STATIC METHODS -------------------------------------
    //--------------------- INSTANCE METHODS -----------------------------------
    //--------------------- ABSTRACT METHODS -----------------------------------
    //--------------------- STATIC - GETTERS - SETTERS -------------------------

    /**
     * @return Retourne la liste de tous les clients
     */
    public static ArrayList<Client> getCollection() {
        return collection;
    }
    //--------------------- GETTERS - SETTERS ----------------------------------
    //--------------------- TO STRING METHOD------------------------------------
}
