/**
 * @author Victor K
 * @version 1.00
 * Cette classe comprends une série de méthodes pour contrôler des valeurs
 * reçues ou
 * générer des chaînes de caractères comprenant uniquement des chiffres
 */
package fr.victork.Tools;

import fr.victork.Exception.ExceptionEntity;
import org.apache.commons.lang3.StringUtils;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;


/**
 * Cette classe comprend une série de méthodes pour contrôler des valeurs
 * reçues ou
 * générer des chaînes de caractères comprenant uniquement des chiffres
 */
public interface Tools {
    String REGEX_MAIL = ".+@.+";

    /**
     * Vérifie si une chaîne de caractère est composée uniquement de chiffres
     * et si elle est de longueur @param length
     *
     * @param string Chaîne à évaluer
     * @param length Nombre correspondant à la longueur de la chaîne
     * @return Retourne True si les conditions sont validées
     * @throws ExceptionEntity Remonte une exception en cas d'erreur
     */
    default boolean checkRegexDigitsLength(String string, int length)
            throws ExceptionEntity {
        if (StringUtils.isNumeric(string) && string.length() == length) {
            return true;
        } else {
            throw new ExceptionEntity(
                    "La longueur du nombre saisie n'est " + "pas" + " valide.");
        }
    }

    /**
     * Vérifie si une chaîne de caractère est composée uniquement de chiffres
     * et la longueur au moins égal au @param length
     *
     * @param string Chaîne à évaluer
     * @param length Nombre correspondant à la longueur de la chaîne minimale
     * @return Retourne True si les conditions sont validées
     * @throws ExceptionEntity Remonte une exception en cas d'erreur
     */
    default boolean checkRegexDigitsMiniLength(String string, int length)
            throws ExceptionEntity {
        if (StringUtils.isNumeric(string) && string.length() >= length) {
            return true;
        } else {
            throw new ExceptionEntity(
                    "La longueur du nombre saisie n'est " + "pas" + " valide.");
        }
    }


    /**
     * Génère une chaîne de caractère composée uniquement de chiffres insérés
     * aléatoirement de longueur @param length
     *
     * @param length Nombre correspondant à la longueur de la chaîne voulue
     * @return Retourne une chaîne de caractère composée uniquement de chiffres
     */
    default String generateStringOfNumbers(int length) {
        String AlphaNumericString = "0123456789";
        StringBuilder stringBuilder = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = (int) (AlphaNumericString.length() * Math.random());
            stringBuilder.append(AlphaNumericString.charAt(index));
        }
        return stringBuilder.toString();
    }

    /**
     * Génère un nombre compris entre @param min et @param @max
     *
     * @param min Int minimal
     * @param max Int maximal
     * @return Retourne un entier
     */
    default int generateIntBetween(int min, int max) {
        return (int) (Math.random() * (max - min + 1) + min);
    }

    /**
     * vérifie si une expression est une chaîne de caractère non vide
     *
     * @param expression Chaîne de caractère
     * @return Retourne true si la condition est validée
     */
    public static boolean controlStringIsNotEmpty(String expression) {
        if (expression == null || expression.trim().isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    DecimalFormat DECIMAL_FORMAT =
            new DecimalFormat("#.##",
                    DecimalFormatSymbols.getInstance(
                            new Locale("fr", "FR")));
}
