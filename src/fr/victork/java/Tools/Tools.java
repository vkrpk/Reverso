package fr.victork.java.Tools;

import fr.victork.java.Exception.ExceptionEntity;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import java.awt.*;

public interface Tools {
    default boolean checkRegexDigitsLength(String string, int length) throws ExceptionEntity {
        if (StringUtils.isNumeric(string) && string.length() == length) {
            return true;
        } else {
            throw new ExceptionEntity("La longueur du nombre saisie n'est pas valide.");
        }
    }

    default boolean checkRegexDigitsMiniLength(String string, int length) throws ExceptionEntity {
        if (StringUtils.isNumeric(string) && string.length() >= length) {
            return true;
        } else {
            throw new ExceptionEntity("La longueur du nombre saisie n'est pas valide.");
        }
    }

    default String generateStringOfNumbers(int length) {
        String AlphaNumericString = "0123456789";
        StringBuilder stringBuilder = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = (int) (AlphaNumericString.length() * Math.random());
            stringBuilder.append(AlphaNumericString.charAt(index));
        }
        return stringBuilder.toString();
    }

    default int generateIntBetween(int min, int max) {
        return (int) (Math.random() * (max - min + 1) + min);
    }

    default void addCustomButton(Container parent, String buttonText) {
        JButton button = new JButton(buttonText);
        parent.add(button);
    }

}
