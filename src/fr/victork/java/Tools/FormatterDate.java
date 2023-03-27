/**
 * @author Victor K
 * @version 1.00
 * Cette classe est utile pour formatter les dates
 */
package fr.victork.java.Tools;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Cette classe est utile pour formatter les dates
 */
public abstract class FormatterDate {

    //--------------------- CONSTANTS ------------------------------------------
    //--------------------- STATIC VARIABLES -----------------------------------
    private static DateTimeFormatter dateTimeFormatter =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");

    //--------------------- INSTANCE VARIABLES ---------------------------------
    //--------------------- CONSTRUCTORS ---------------------------------------
    //--------------------- STATIC METHODS -------------------------------------
    //--------------------- INSTANCE METHODS -----------------------------------

    /**
     * Convertit un objet String en objet LocalDate au format yyyy-MM-dd
     *
     * @param date Chaîne de caractère au format dd/MM/yyyy
     * @return Retourne un objet LocalDate au format yyyy-MM-dd
     */
    public static LocalDate convertiEtFormatDateEnLocalDate(String date) {
        return LocalDate.parse(date, FormatterDate.dateTimeFormatter);
    }

    /**
     * Convertit un objet LocalDate en une chaîne de caractère au format
     * dd/MM/yyyy
     *
     * @param date Objet LocalDate
     * @return Retourne un objet String au format dd/MM/yyyy
     */
    public static String convertiEtFormatDateEnChaine(LocalDate date) {
        return date.format(FormatterDate.dateTimeFormatter);
    }
    //--------------------- ABSTRACT METHODS -----------------------------------
    //--------------------- STATIC - GETTERS - SETTERS -------------------------
    //--------------------- GETTERS - SETTERS ----------------------------------
    //--------------------- TO STRING METHOD------------------------------------
}
