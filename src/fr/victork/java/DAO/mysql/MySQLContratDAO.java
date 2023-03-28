package fr.victork.java.DAO.mysql;

import fr.victork.java.DAO.DAO;
import fr.victork.java.DAO.InterfaceDAOClient;
import fr.victork.java.Entity.Client;
import fr.victork.java.Entity.Contrat;
import fr.victork.java.Exception.ExceptionDAO;
import fr.victork.java.Exception.ExceptionEntity;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class MySQLContratDAO implements DAO<Contrat> {
    //--------------------- CONSTANTS ------------------------------------------
    //--------------------- STATIC VARIABLES -----------------------------------
    //--------------------- INSTANCE VARIABLES ---------------------------------
    //--------------------- CONSTRUCTORS ---------------------------------------
    //--------------------- STATIC METHODS -------------------------------------
    //--------------------- INSTANCE METHODS -----------------------------------
    public ArrayList<Contrat> findAll()
            throws ExceptionEntity, ExceptionDAO {
        String strSql = "SELECT * FROM contrat";
        ArrayList<Contrat> collectionContrats = new ArrayList<>();
        try (Statement statement = MySQLDatabaseConnection.getInstance().getConnection().createStatement(); ResultSet resultSet =
                statement.executeQuery(strSql)) {
            while (resultSet.next()) {
                Integer identifiantContrat = resultSet.getInt("identifiant_contrat");
                Integer identifiantClient = resultSet.getInt("identifiant_client");
                String libelle = resultSet.getString("libelle");
                Double montant = resultSet.getDouble("montant");
                Contrat contrat = new Contrat(identifiantContrat, identifiantClient, libelle, montant);
                collectionContrats.add(contrat);
            }
        } catch (SQLException sqlException) {
            throw new ExceptionDAO("Une erreur est survenue lors de la recherche de la liste des contrats : " +
                    sqlException.getMessage() + ", cause : " + sqlException.getSQLState(), 5);
        }
        return collectionContrats;
    }

    public Contrat find(Integer id)
            throws ExceptionEntity, ExceptionDAO {
        Contrat contrat = new Contrat();
        String strSql = "SELECT * FROM contrat WHERE identifiant_contrat=?";
        try (PreparedStatement statement = MySQLDatabaseConnection.getInstance().getConnection().prepareStatement(strSql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Integer identifiantContrat = resultSet.getInt("identifiant_contrat");
                    Integer identifiantClient = resultSet.getInt("identifiant_client");
                    String libelle = resultSet.getString("libelle");
                    Double montant = resultSet.getDouble("montant");
                    return new Contrat(identifiantContrat, identifiantClient, libelle, montant);
                }
            }
        } catch (SQLException sqlException) {
            throw new ExceptionDAO("Une erreur est survenue lors de la recherche d'un contrat : " +
                    sqlException.getMessage() + ", cause : " + sqlException.getSQLState(), 5);
        }
        return contrat;
    }

    public void delete(Integer id)
            throws ExceptionDAO {
        String strSql = "DELETE FROM contrat WHERE identifiant_contrat=?";
        try (PreparedStatement statement = MySQLDatabaseConnection.getInstance().getConnection().prepareStatement(strSql)) {
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException sqlException) {
            throw new ExceptionDAO("Une erreur est survenue lors de la suppression d'un contrat : " +
                    sqlException.getMessage() + ", cause : " + sqlException.getSQLState(), 5);
        }
    }

    public void save(Contrat contrat)
            throws ExceptionEntity, ExceptionDAO {
        String strSql;
        if (contrat.getIdentifiantContrat() == null) {
            strSql = "INSERT INTO contrat (identifiant_client, libelle, montant\n)" +
                    "VALUES (?, ?, ?)";
        } else {
            strSql = "UPDATE contrat SET identifiant_client = ?, libelle = ?, " +
                    "montant = ? WHERE identifiant_contrat = ? ";
        }
        try (PreparedStatement statement = MySQLDatabaseConnection.getInstance().getConnection().prepareStatement(strSql)) {
            statement.setInt(1, contrat.getIdentifiantClient());
            statement.setString(2, contrat.getLibelle());
            statement.setDouble(3, contrat.getMontant());
            if (contrat.getIdentifiantContrat() != null) {
                statement.setInt(4, contrat.getIdentifiantContrat());
            }
            statement.executeUpdate();
        } catch (SQLException sqlException) {
            if (sqlException.getErrorCode() == 1062) {
                throw new ExceptionDAO("Une erreur est survenue lors de la création d'un contrat : " +
                        sqlException.getMessage() + ", cause : " + sqlException.getSQLState(), 1);
            } else {
                throw new ExceptionDAO("Une erreur est survenue lors de la création d'un contrat : " +
                        sqlException.getMessage() + ", cause : " + sqlException.getSQLState(), 5);
            }
        }
    }
    //--------------------- ABSTRACT METHODS -----------------------------------
    //--------------------- STATIC - GETTERS - SETTERS -------------------------
    //--------------------- GETTERS - SETTERS ----------------------------------
    //--------------------- TO STRING METHOD------------------------------------
}
