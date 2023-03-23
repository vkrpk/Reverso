package fr.victork.java.DAO.mysql;

import fr.victork.java.Entity.Client;
import fr.victork.java.Entity.Contrat;
import fr.victork.java.Exception.ExceptionDAO;
import fr.victork.java.Exception.ExceptionEntity;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MySQLContratDAO {
    //--------------------- CONSTANTS ------------------------------------------
    //--------------------- STATIC VARIABLES -----------------------------------
    //private static final Connection connection = MySQLDatabaseConnection.getConnection();

    //--------------------- INSTANCE VARIABLES ---------------------------------
    //--------------------- CONSTRUCTORS ---------------------------------------
    //--------------------- STATIC METHODS -------------------------------------
    //--------------------- INSTANCE METHODS -----------------------------------
    public static ArrayList<Contrat> findByIdClient(Client client) throws ExceptionEntity, ExceptionDAO {
        String strSql = "SELECT * FROM contrat WHERE identifiant_client=?";
        ArrayList<Contrat> contratsByClient = new ArrayList<>();
        try (PreparedStatement statement = MySQLDatabaseConnection.getInstance().getConnection().prepareStatement(strSql)) {
            statement.setInt(1, client.getIdentifiant());
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Integer identifiantContrat = resultSet.getInt("identifiant_contrat");
                    Integer identifiantClient = resultSet.getInt("identifiant_client");
                    String libelle = resultSet.getString("libelle");
                    Double montant = resultSet.getDouble("montant");
                    Contrat contrat = new Contrat(identifiantContrat, identifiantClient, libelle, montant);
                    contratsByClient.add(contrat);
                }
            }
        } catch (SQLException sqlException) {
            throw new ExceptionDAO("Une erreur est survenue lors de la recherche de la liste des contrats appartenant" +
                    " à un client : " +
                    sqlException.getMessage() + ", cause : " + sqlException.getSQLState(), 5);
        }
        return contratsByClient;
    }
    //--------------------- ABSTRACT METHODS -----------------------------------
    //--------------------- STATIC - GETTERS - SETTERS -------------------------
    //--------------------- GETTERS - SETTERS ----------------------------------
    //--------------------- TO STRING METHOD------------------------------------
}
