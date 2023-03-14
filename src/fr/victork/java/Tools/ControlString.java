package fr.victork.java.Tools;

public class ControlString {
    //--------------------- CONSTANTS ------------------------------------------

    //--------------------- STATIC VARIABLES -----------------------------------

    //--------------------- INSTANCE VARIABLES ---------------------------------

    //--------------------- CONSTRUCTORS ---------------------------------------

    //--------------------- STATIC METHODS -------------------------------------
    public static boolean controlStringIsNotEmpty(String expression) {
        if (expression == null || expression.trim().isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    ;
    //--------------------- INSTANCE METHODS -----------------------------------

    //--------------------- ABSTRACT METHODS -----------------------------------

    //--------------------- STATIC - GETTERS - SETTERS -------------------------

    //--------------------- GETTERS - SETTERS ----------------------------------

    //--------------------- TO STRING METHOD------------------------------------
}
