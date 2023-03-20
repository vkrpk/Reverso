package fr.victork.java.Exception;

public class ExceptionDAO extends Exception {
    //--------------------- CONSTANTS ------------------------------------------
    //--------------------- STATIC VARIABLES -----------------------------------
    //--------------------- INSTANCE VARIABLES ---------------------------------
    private int gravite;

    //--------------------- CONSTRUCTORS ---------------------------------------
    public ExceptionDAO(String message, int gravite) {
        super(message);
        this.setGravite(gravite);
    }
    //--------------------- STATIC METHODS -------------------------------------
    //--------------------- INSTANCE METHODS -----------------------------------
    //--------------------- ABSTRACT METHODS -----------------------------------
    //--------------------- STATIC - GETTERS - SETTERS -------------------------
    //--------------------- GETTERS - SETTERS ----------------------------------

    public int getGravite() {
        return gravite;
    }

    public void setGravite(int gravite) {
        this.gravite = gravite;
    }

    //--------------------- TO STRING METHOD------------------------------------
}
