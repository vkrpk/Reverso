package fr.victork.java;

import fr.victork.java.Entity.Client;
import fr.victork.java.Entity.Prospect;
import fr.victork.java.Exception.ExceptionEntity;
import fr.victork.java.View.AccueilFrame;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) throws ExceptionEntity {
        initDatas();
        new AccueilFrame(800, 750, -1, -1, false);
    }

    private static void initDatas() throws ExceptionEntity {
        Client client1 =
                new Client("Victor", "3", "Rue de la paix", "75000", "Paris",
                        "0600000000", "client1@gmail.com", "Ce client ",
                        1000.00, 15);
        Client client3 =
                new Client("Jules", "3", "Rue de la paix", "75000", "Paris",
                        "0600000000", "client1@gmail.com", "Ce client ",
                        1000.00, 15);
        Client client2 =
                new Client("Charles", "3", "Rue de la paix", "75000", "Paris",
                        "0600000000", "client1@gmail.com", "Ce client ",
                        1000.00, 15);
        Client client4 =
                new Client("Pierre", "3", "Rue de la paix", "75000", "Paris",
                        "0600000000", "client1@gmail.com", "Ce client ",
                        1000.00, 15);
        Prospect prospect1 =
                new Prospect("Jean", "3", "Rue de la paix", "75000", "Paris",
                        "0600000000", "client1@gmail.com", "Ce client ",
                        LocalDate.now(), "Non");
        Prospect prospect2 =
                new Prospect("Lisa", "3", "Rue de la paix", "75000", "Paris",
                        "0600000000", "client1@gmail.com", "Ce client ",
                        LocalDate.now(), "Oui");
    }
}