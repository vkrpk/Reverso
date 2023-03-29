/**
 * @author Victor K
 * @version 1.00
 * Cette classe est le modèle pour une exception caractérisé
 */
package fr.victork.Exception;

/**
 * Cette classe est le modèle pour une exception caractérisé
 */
public class ExceptionEntity extends Exception {
    //--------------------- CONSTANTS ------------------------------------------
    //--------------------- STATIC VARIABLES -----------------------------------
    //--------------------- INSTANCE VARIABLES ---------------------------------
    //--------------------- CONSTRUCTORS ---------------------------------------

    /**
     * Construit une nouvelle instance de MyException avec un message
     * d'erreur spécifié.
     *
     * @param message Le message d'erreur à afficher.
     */
    public ExceptionEntity(String message) {
        super(message);
    }
    //--------------------- STATIC METHODS -------------------------------------
    //--------------------- INSTANCE METHODS -----------------------------------
    //--------------------- ABSTRACT METHODS -----------------------------------
    //--------------------- STATIC - GETTERS - SETTERS -------------------------
    //--------------------- GETTERS - SETTERS ----------------------------------
    //--------------------- TO STRING METHOD------------------------------------
}
