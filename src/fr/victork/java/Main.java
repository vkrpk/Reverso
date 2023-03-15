/**
 * @author Victor K
 * @version 1.00
 * Cette classe est le point d'entrée de l'application
 */
package fr.victork.java;

import fr.victork.java.Entity.*;
import fr.victork.java.Exception.ExceptionEntity;
import fr.victork.java.View.AccueilFrame;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

/**
 * Cette classe est le point d'entrée de l'application
 */
public class Main {
    /**
     * @param args String[]
     * @throws ExceptionEntity Remonte une exception en cas d'erreur
     */
    public static void main(String[] args) throws ExceptionEntity, IOException {
        initDatas();
        /* Les valeurs -1 indiquent que la fenêtre doit être positionnée au
        milieu de l'écran */
        new AccueilFrame(800, 750, -1, -1, false);
    }

    /**
     * Initialise de fausses données à des fins de test
     *
     * @throws ExceptionEntity Remonte une exception en cas d'erreur
     */
    private static void initDatas() throws ExceptionEntity, IOException {
        File file = new File("DonnesSocietes.txt");
        WriteFile.litUnFichierEtRempliLesCollections(file);
    }
}