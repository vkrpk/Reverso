package fr.victork.java.Tools;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public abstract class FormatterDate {

    //--------------------- CONSTANTS ------------------------------------------
    //--------------------- STATIC VARIABLES -----------------------------------
    private static DateTimeFormatter dateTimeFormatter =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");

    //--------------------- INSTANCE VARIABLES ---------------------------------
    //--------------------- CONSTRUCTORS ---------------------------------------
    //--------------------- STATIC METHODS -------------------------------------
    //--------------------- INSTANCE METHODS -----------------------------------
    public static LocalDate convertiEtFormatDateEnLocalDate(String date) {
        return LocalDate.parse(date, FormatterDate.dateTimeFormatter);
    }

    public static String convertiEtFormatDateEnChaine(LocalDate date) {
        return date.format(FormatterDate.dateTimeFormatter);
    }
    //--------------------- ABSTRACT METHODS -----------------------------------
    //--------------------- STATIC - GETTERS - SETTERS -------------------------
    //--------------------- GETTERS - SETTERS ----------------------------------
    //--------------------- TO STRING METHOD------------------------------------
}
