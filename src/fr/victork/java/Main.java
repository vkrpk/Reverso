/**
 * @author Victor K
 * @version 1.00
 * Cette classe est le point d'entrée de l'application
 */
package fr.victork.java;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import fr.victork.java.DAO.AbstractDAOFactory;
import fr.victork.java.DAO.DAO;
import fr.victork.java.DAO.mongoDB.*;
import fr.victork.java.DAO.mysql.MySQLClientDAO;
import fr.victork.java.DAO.mysql.MySQLContratDAO;
import fr.victork.java.DAO.mysql.MySQLProspectDAO;
import fr.victork.java.Entity.*;
import fr.victork.java.Exception.ExceptionEntity;
import fr.victork.java.Log.FormatterLog;
import fr.victork.java.View.AccueilFrame;
import fr.victork.java.View.MainFrame;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
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

            Contrat contrat = new Contrat(null, 1, "TEST", 12.00);
            new MySQLContratDAO().save(contrat);

            //System.out.println(new MySQLContratDAO().find(2).getLibelle());

            MainFrame mainFrame = new MainFrame(1100, 750, -1, -1, false);
            /* Les valeurs -1 indiquent que la fenêtre doit être positionnée au milieu de l'écran */
            new AccueilFrame(1100, 750, -1, -1, false, mainFrame);
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

        //WriteFile.litUnFichierEtRempliLesCollections(file);
    }


}