/**
 * @author Victor K
 * @version 1.00
 * La classe implémente l'interface générique DAO<T> et est utilisée pour gérer
 * la persistance des objets de type Contrat dans une base de données MySQL.
 * Elle utilise la classe MySQLDatabaseConnection pour établir une connexion à la base de données.
 * La classe utilise la classe Document de MongoDB pour représenter les objets stockés dans la base de données.
 */
package fr.victork.DAO.mysql;

import fr.victork.DAO.DAO;
import fr.victork.DAO.InterfaceDAOClient;
import fr.victork.Entity.Client;
import fr.victork.Entity.Contrat;
import fr.victork.Entity.Prospect;
import fr.victork.Exception.ExceptionDAO;
import fr.victork.Exception.ExceptionEntity;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * La classe implémente l'interface générique DAO<T> et est utilisée pour gérer
 * la persistance des objets de type Contrat dans une base de données MySQL.
 * Elle utilise la classe MySQLDatabaseConnection pour établir une connexion à la base de données.
 * La classe utilise la classe Document de MongoDB pour représenter les objets stockés dans la base de données.
 */
public class MySQLContratDAO implements DAO<Contrat> {
    //--------------------- CONSTANTS ------------------------------------------
    //--------------------- STATIC VARIABLES -----------------------------------
    //--------------------- INSTANCE VARIABLES ---------------------------------
    //--------------------- CONSTRUCTORS ---------------------------------------
    //--------------------- STATIC METHODS -------------------------------------
    //--------------------- INSTANCE METHODS -----------------------------------
    /**
     * permet de récupérer tous les contrats associés à un client à partir de son identifiant unique dans la base de données.
     *
     * @param client Client dont on souhaite récupérer les contrats.
     * @return Retourne une liste de Contrat associée au client spécifié.
     * @throws ExceptionEntity si une exception se produit lors de l'interaction avec l'entité
     * @throws ExceptionDAO    si une exception se produit lors de l'interaction avec la base de données
     */
    public ArrayList<Contrat> findByIdClient(Client client) throws ExceptionEntity, ExceptionDAO {
        String strSql = "SELECT * FROM contrat WHERE identifiant_client=?";
        ArrayList<Contrat> contratsByClient = new ArrayList<>();
        try (PreparedStatement statement = MySQLDatabaseConnection.getConnection().prepareStatement(strSql)) {
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

    /**
     * Cette méthode permet de récupérer tous les objets Contrat présents dans la base de données.
     *
     * @return ArrayList<Contrat> Retourne une liste d'objet Contrat présent dans la base de données.
     * @throws ExceptionEntity si une exception se produit lors de l'interaction avec l'entité Contrat
     * @throws ExceptionDAO    si une exception se produit lors de l'interaction avec la base de données
     */
    public ArrayList<Contrat> findAll()
            throws ExceptionEntity, ExceptionDAO {
        String strSql = "SELECT * FROM contrat";
        ArrayList<Contrat> collectionContrats = new ArrayList<>();
        try (Statement statement = MySQLDatabaseConnection.getConnection().createStatement(); ResultSet resultSet =
                statement.executeQuery(strSql)) {
            while (resultSet.next()) {
                collectionContrats.add(convertResultSetToContrat(resultSet));
            }
        } catch (SQLException sqlException) {
            throw new ExceptionDAO("Une erreur est survenue lors de la recherche de la liste des contrats : " +
                    sqlException.getMessage() + ", cause : " + sqlException.getSQLState(), 5);
        }
        return collectionContrats;
    }

    /**
     * Cette méthode permet de récupérer un objet Contrat à partir de son identifiant unique dans la base de données.
     *
     * @param id Identifiant unique de l'objet à récupérer.
     * @return Contrat retourne un objet Contrat trouvé dans la base de données
     * @throws ExceptionEntity si une exception se produit lors de l'interaction avec l'entité Contrat
     * @throws ExceptionDAO    si une exception se produit lors de l'interaction avec la base de données
     */
    public Contrat find(Integer id)
            throws ExceptionEntity, ExceptionDAO {
        Contrat contrat = new Contrat();
        String strSql = "SELECT * FROM contrat WHERE identifiant_contrat=?";
        try (PreparedStatement statement = MySQLDatabaseConnection.getConnection().prepareStatement(strSql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return convertResultSetToContrat(resultSet);
                }
            }
        } catch (SQLException sqlException) {
            throw new ExceptionDAO("Une erreur est survenue lors de la recherche d'un contrat : " +
                    sqlException.getMessage() + ", cause : " + sqlException.getSQLState(), 5);
        }
        return contrat;
    }

    /**
     * Cette méthode permet de supprimer un objet Contrat de la base de données à partir de son identifiant unique.
     *
     * @param id Identifiant unique de l'objet à supprimer de la base de données.
     * @throws ExceptionDAO si une exception se produit lors de l'interaction avec la base de données
     */
    public void delete(Integer id)
            throws ExceptionDAO {
        String strSql = "DELETE FROM contrat WHERE identifiant_contrat=?";
        try (PreparedStatement statement = MySQLDatabaseConnection.getConnection().prepareStatement(strSql)) {
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException sqlException) {
            throw new ExceptionDAO("Une erreur est survenue lors de la suppression d'un contrat : " +
                    sqlException.getMessage() + ", cause : " + sqlException.getSQLState(), 5);
        }
    }

    /**
     * Cette méthode permet de sauvegarder un objet Contrat dans la base de données.
     * Si le contrat existe déjà dans la base de données, les informations sont mises à jour. Sinon, un nouveau
     * document est créé.
     *
     * @param contrat Objet à sauvegarder dans la base de données.
     * @throws ExceptionEntity si une exception se produit lors de l'interaction avec l'entité
     * @throws ExceptionDAO    si une exception se produit lors de l'interaction avec la base de données
     */
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
        try (PreparedStatement statement = MySQLDatabaseConnection.getConnection().prepareStatement(strSql)) {
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

    private Contrat convertResultSetToContrat(ResultSet resultSet) throws ExceptionEntity, SQLException {
        Integer identifiantContrat = resultSet.getInt("identifiant_contrat");
        Integer identifiantClient = resultSet.getInt("identifiant_client");
        String libelle = resultSet.getString("libelle");
        Double montant = resultSet.getDouble("montant");
        return new Contrat(identifiantContrat, identifiantClient, libelle, montant);
    }
    //--------------------- ABSTRACT METHODS -----------------------------------
    //--------------------- STATIC - GETTERS - SETTERS -------------------------
    //--------------------- GETTERS - SETTERS ----------------------------------
    //--------------------- TO STRING METHOD------------------------------------
}
