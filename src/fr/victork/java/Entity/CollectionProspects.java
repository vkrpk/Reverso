/**
 * @author Victor K
 * @version 1.00
 * Cette classe représente la liste de tous les prospects
 */
package fr.victork.java.Entity;

import java.util.ArrayList;

public class CollectionProspects {
    //--------------------- CONSTANTS ------------------------------------------
    //--------------------- STATIC VARIABLES -----------------------------------
    private static ArrayList<Prospect> collection = new ArrayList<>();
    //--------------------- INSTANCE VARIABLES ---------------------------------
    //--------------------- CONSTRUCTORS ---------------------------------------
    //--------------------- STATIC METHODS -------------------------------------
    //--------------------- INSTANCE METHODS -----------------------------------
    //--------------------- ABSTRACT METHODS -----------------------------------
    //--------------------- STATIC - GETTERS - SETTERS -------------------------

    /**
     * @return Retourne la liste de tous les prospects
     */
    public static ArrayList<Prospect> getCollection() {
        return collection;
    }
    //--------------------- GETTERS - SETTERS ----------------------------------
    //--------------------- TO STRING METHOD------------------------------------
}
