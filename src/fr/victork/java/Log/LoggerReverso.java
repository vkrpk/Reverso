/**
 * @author Victor K
 * @version 1.00
 * Cette classe LoggerReverso représente une classe utilitaire qui permet de récupérer une instance de la classe Logger
 * pour enregistrer les logs dans l'application.
 */
package fr.victork.java.Log;

import java.util.logging.Logger;

/**
 * Cette classe LoggerReverso représente une classe utilitaire qui permet de récupérer une instance de la classe Logger
 * pour enregistrer les logs dans l'application.
 */
public class LoggerReverso {
    //--------------------- CONSTANTS ------------------------------------------
    //--------------------- STATIC VARIABLES -----------------------------------
    /**
     * variable statique représentant l'instance du logger de la classe
     */
    public static final Logger LOGGER =
            Logger.getLogger(LoggerReverso.class.getName());
    //--------------------- INSTANCE VARIABLES ---------------------------------
    //--------------------- CONSTRUCTORS ---------------------------------------
    //--------------------- STATIC METHODS -------------------------------------
    //--------------------- INSTANCE METHODS -----------------------------------
    //--------------------- ABSTRACT METHODS -----------------------------------
    //--------------------- STATIC - GETTERS - SETTERS -------------------------
    //--------------------- GETTERS - SETTERS ----------------------------------
    //--------------------- TO STRING METHOD------------------------------------
}
