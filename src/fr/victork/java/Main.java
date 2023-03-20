/**
 * @author Victor K
 * @version 1.00
 * Cette classe est le point d'entrée de l'application
 */
package fr.victork.java;

import fr.victork.java.Entity.*;
import fr.victork.java.Exception.ExceptionEntity;
import fr.victork.java.Log.FormatterLog;
import fr.victork.java.View.AccueilFrame;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;

import static fr.victork.java.Log.LoggerReverso.LOGGER;

/**
 * Cette classe est le point d'entrée de l'application
 */
public class Main {
    /**
     * @param args String[]
     * @throws ExceptionEntity Remonte une exception en cas d'erreur
     */
    public static void main(String[] args) throws ExceptionEntity, IOException {
        try {
            FileHandler fh = new FileHandler("LogReverso.log", true);
            LOGGER.setUseParentHandlers(false);
            LOGGER.addHandler(fh);
            fh.setFormatter(new FormatterLog());

            LOGGER.log(Level.INFO, "Démarrage de l'application");

            initDatas();

        /* Les valeurs -1 indiquent que la fenêtre doit être positionnée au
        milieu de l'écran */
            new AccueilFrame(800, 750, -1, -1, false);

        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.log(Level.SEVERE, e.getMessage());
            JOptionPane.showMessageDialog(null, e.getMessage(),
                    "Erreur système", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }

    /**
     * Initialise de fausses données à des fins de test
     *
     * @throws ExceptionEntity Remonte une exception en cas d'erreur
     */
    private static void initDatas() throws ExceptionEntity, IOException {
        File file = new File("Fixtures.txt");
        WriteFile.litUnFichierEtRempliLesCollections(file);
    }
}