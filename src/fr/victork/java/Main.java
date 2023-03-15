/**
 * @author Victor K
 * @version 1.00
 * Cette classe est le point d'entrée de l'application
 */
package fr.victork.java;

import fr.victork.java.Entity.Client;
import fr.victork.java.Entity.CollectionClients;
import fr.victork.java.Entity.CollectionProspects;
import fr.victork.java.Entity.Prospect;
import fr.victork.java.Exception.ExceptionEntity;
import fr.victork.java.View.AccueilFrame;

import java.time.LocalDate;

public class Main {
    /**
     * @param args
     * @throws ExceptionEntity Remonte une exception en cas d'erreur
     */
    public static void main(String[] args) throws ExceptionEntity {
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
    private static void initDatas() throws ExceptionEntity {
        Client client1 =
                new Client("eric", "3", "Rue de la paix", "75000", "Paris",
                        "0600000000", "client1@gmail.com", "Ce client ",
                        1000.00, 15);
        Client client3 =
                new Client("Jules", "3", "Rue de la paix", "75000", "Paris",
                        "0600000000", "client3@gmail.com", "Ce client ",
                        1000.00, 15);
        Client client2 =
                new Client("Charles", "3", "Rue de la paix", "75000", "Paris",
                        "0600000000", "client2@gmail.com", "Ce client ",
                        1000.00, 15);
        Client client4 =
                new Client("Pierre", "3", "Rue de la paix", "75000", "Paris",
                        "0600000000", "client4@gmail.com", "Ce client ",
                        1000.00, 15);
        Prospect prospect1 =
                new Prospect("Jean", "3", "Rue de la paix", "75000", "Paris",
                        "0600000000", "prospect1@gmail.com", "Ce client ",
                        LocalDate.now(), "Non");
        Prospect prospect2 =
                new Prospect("Lisa", "3", "Rue de la paix", "75000", "Paris",
                        "0600000000", "prospect2@gmail.com", "Ce client ",
                        LocalDate.now(), "Oui");
        CollectionClients.getCollection().add(client1);
        CollectionClients.getCollection().add(client2);
        CollectionClients.getCollection().add(client3);
        CollectionClients.getCollection().add(client4);
        CollectionProspects.getCollection().add(prospect2);
        CollectionProspects.getCollection().add(prospect1);
    }
}