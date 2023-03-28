package fr.victork.java.DAO;

import fr.victork.java.DAO.DAO;
import fr.victork.java.Entity.Client;
import fr.victork.java.Entity.Contrat;
import fr.victork.java.Exception.ExceptionDAO;
import fr.victork.java.Exception.ExceptionEntity;

import java.util.ArrayList;

public interface InterfaceDAOClient<T> extends DAO<T> {
    ArrayList<Contrat> findByIdClient(Client client) throws ExceptionEntity, ExceptionDAO;
}
