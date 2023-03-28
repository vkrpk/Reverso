/**
 * @author Victor K
 * @version 1.00
 * Cette classe définit un formatter personnalisé pour les logs du programme. Elle permet de formater le message de log
 * en incluant la date et l'heure, le niveau de log, le message en question, la classe et la méthode qui ont généré
 * le log.
 */
package fr.victork.java.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

/**
 * Cette classe définit un formatter personnalisé pour les logs du programme. Elle permet de formater le message de log
 * en incluant la date et l'heure, le niveau de log, le message en question, la classe et la méthode qui ont généré
 * le log.
 */
public class FormatterLog extends Formatter {
    /**
     * Cette méthode permet de formatter un enregistrement de log donné en une chaîne de caractères au format souhaité.
     * Elle inclut la date et l'heure, le niveau de log, le message en question, la classe et la méthode qui ont généré
     * le log.
     *
     * @param record l'enregistrement de log à formatter
     * @return la chaîne de caractères formatée
     */
    @Override
    public String format(LogRecord record) {
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        StringBuilder result = new StringBuilder();
        result.append(format.format(new Date()));
        result.append(" Level : ");
        result.append(record.getLevel());
        result.append(" / Message : ");
        result.append(record.getMessage());
        result.append(" / Classe : ");
        result.append(record.getSourceClassName());
        result.append(" / Méthode : ");
        result.append(record.getSourceMethodName());
        result.append("\n");
        return result.toString();
    }

}
