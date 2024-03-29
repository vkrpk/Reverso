/**
 * @author Victor K
 * @version 1.00
 * Cette classe est le point d'entrée de l'application
 */
package fr.victork;

import fr.victork.DAO.mysql.MySQLClientDAO;
import fr.victork.Entity.Client;
import fr.victork.Exception.ExceptionEntity;
import fr.victork.Log.FormatterLog;
import fr.victork.View.AccueilFrame;
import fr.victork.View.MainFrame;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;

import static fr.victork.Log.LoggerReverso.LOGGER;

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
            MainFrame mainFrame = new MainFrame(1100, 750, -1, -1, false);
            /* Les valeurs -1 indiquent que la fenêtre doit être positionnée au milieu de l'écran */
            new AccueilFrame(1100, 750, -1, -1, false, mainFrame);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.getMessage(),
                    "Erreur système", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }
}