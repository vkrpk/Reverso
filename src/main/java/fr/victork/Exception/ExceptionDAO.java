/**
 * @author Victor K
 * @version 1.00
 * Classe d'exception pour les erreurs rencontrées dans les DAO.
 */
package fr.victork.Exception;

/**
 * Classe d'exception pour les erreurs rencontrées dans les DAO.
 */
public class ExceptionDAO extends Exception {
    //--------------------- CONSTANTS ------------------------------------------
    //--------------------- STATIC VARIABLES -----------------------------------
    //--------------------- INSTANCE VARIABLES ---------------------------------
    /**
     * Niveau de gravité de l'exception.
     */
    private int gravite;

    //--------------------- CONSTRUCTORS ---------------------------------------

    /**
     * Constructeur de la classe ExceptionDAO.
     *
     * @param message le message d'erreur associé à l'exception.
     * @param gravite le niveau de gravité associé à l'exception.
     */
    public ExceptionDAO(String message, int gravite) {
        super(message);
        this.setGravite(gravite);
    }
    //--------------------- STATIC METHODS -------------------------------------
    //--------------------- INSTANCE METHODS -----------------------------------
    //--------------------- ABSTRACT METHODS -----------------------------------
    //--------------------- STATIC - GETTERS - SETTERS -------------------------
    //--------------------- GETTERS - SETTERS ----------------------------------

    /**
     * Récupère le niveau de gravité de l'exception.
     *
     * @return le niveau de gravité de l'exception.
     */
    public int getGravite() {
        return gravite;
    }

    /**
     * Définit le niveau de gravité de l'exception.
     *
     * @param gravite le niveau de gravité à définir pour l'exception.
     */
    public void setGravite(int gravite) {
        this.gravite = gravite;
    }

    //--------------------- TO STRING METHOD------------------------------------
}
