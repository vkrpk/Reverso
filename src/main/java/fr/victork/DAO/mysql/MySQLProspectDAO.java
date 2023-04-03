/**
 * @author Victor K
 * @version 1.00
 * La classe implémente l'interface générique DAO<T> et est utilisée pour gérer
 * la persistance des objets de type Prospect dans une base de données MySQL.
 * Elle utilise la classe MySQLDatabaseConnection pour établir une connexion à la base de données.
 * La classe utilise la classe Document de MongoDB pour représenter les objets stockés dans la base de données.
 */
package fr.victork.DAO.mysql;

import fr.victork.DAO.DAO;
import fr.victork.Entity.Prospect;
import fr.victork.Exception.ExceptionDAO;
import fr.victork.Exception.ExceptionEntity;

import java.sql.*;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * La classe implémente l'interface générique DAO<T> et est utilisée pour gérer
 * la persistance des objets de type Prospect dans une base de données MySQL.
 * Elle utilise la classe MySQLDatabaseConnection pour établir une connexion à la base de données.
 * La classe utilise la classe Document de MongoDB pour représenter les objets stockés dans la base de données.
 */
public class MySQLProspectDAO implements DAO<Prospect> {
    //--------------------- CONSTANTS ------------------------------------------
    //--------------------- STATIC VARIABLES -----------------------------------
    //--------------------- INSTANCE VARIABLES ---------------------------------
    //--------------------- CONSTRUCTORS ---------------------------------------
    //--------------------- STATIC METHODS -------------------------------------

    /**
     * Cette méthode permet de récupérer tous les objets Prospect présents dans la base de données.
     *
     * @return ArrayList<Prospect> Retourne une liste d'objet Prospect présent dans la base de données.
     * @throws ExceptionEntity si une exception se produit lors de l'interaction avec l'entité Prospect
     * @throws ExceptionDAO    si une exception se produit lors de l'interaction avec la base de données
     */
    public ArrayList<Prospect> findAll()
            throws ExceptionEntity, ExceptionDAO {
        String strSql = "SELECT * FROM prospect";
        ArrayList<Prospect> collectionProspects = new ArrayList<>();
        try (Statement statement = MySQLDatabaseConnection.getConnection().createStatement();
             ResultSet resultSet = statement.executeQuery(strSql)) {
            while (resultSet.next()) {
                collectionProspects.add(convertResultSetToProspect(resultSet));
            }
        } catch (SQLException sqlException) {
            throw new ExceptionDAO("Une erreur est survenue lors de la recherche de la liste des prospects : " +
                    sqlException.getMessage() + ", cause : " + sqlException.getSQLState(), 5);
        }
        return collectionProspects;
    }

    /**
     * Cette méthode permet de récupérer un objet Prospect à partir de son identifiant unique dans la base de données.
     *
     * @param id Identifiant unique de l'objet à récupérer.
     * @return Client retourne un objet Prospect trouvé dans la base de données
     * @throws ExceptionEntity si une exception se produit lors de l'interaction avec l'entité Prospect
     * @throws ExceptionDAO    si une exception se produit lors de l'interaction avec la base de données
     */
    public Prospect find(Integer id)
            throws ExceptionEntity, ExceptionDAO {
        Prospect prospect = new Prospect();
        String strSql = "SELECT * FROM prospect WHERE prospect_identifiant=?";
        try (PreparedStatement statement = MySQLDatabaseConnection.getConnection().prepareStatement(strSql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return convertResultSetToProspect(resultSet);
                }
            }
        } catch (SQLException sqlException) {
            throw new ExceptionDAO("Une erreur est survenue lors de la recherche d'un prospect : " +
                    sqlException.getMessage() + ", cause : " + sqlException.getSQLState(), 5);
        }
        return prospect;
    }

    /**
     * Cette méthode permet de supprimer un objet Prospect de la base de données à partir de son identifiant unique.
     *
     * @param id Identifiant unique de l'objet à supprimer de la base de données.
     * @throws ExceptionDAO si une exception se produit lors de l'interaction avec la base de données
     */
    public void delete(Integer id)
            throws ExceptionDAO {
        Prospect prospect = new Prospect();
        String strSql = "DELETE FROM prospect WHERE prospect_identifiant=?";
        try (PreparedStatement statement = MySQLDatabaseConnection.getConnection().prepareStatement(strSql)) {
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException sqlException) {
            throw new ExceptionDAO("Une erreur est survenue lors de la suppression d'un prospect : " +
                    sqlException.getMessage() + ", cause : " + sqlException.getSQLState(), 5);
        }
    }

    /**
     * Cette méthode permet de sauvegarder un objet Prospect dans la base de données.
     * Si le prospect existe déjà dans la base de données, les informations sont mises à jour. Sinon, un nouveau
     * document est créé.
     *
     * @param prospect Prospect dont on souhaite sauvegarder ou modifier dans la base de données
     * @throws ExceptionEntity si une exception se produit lors de l'interaction avec l'entité Client
     * @throws ExceptionDAO    si une exception se produit lors de l'interaction avec la base de données
     */
    public void save(Prospect prospect)
            throws ExceptionEntity, ExceptionDAO {
        String strSql;
        if (prospect.getIdentifiant() == null) {
            strSql = "INSERT INTO prospect (prospect_raison_sociale, prospect_numero_de_rue, prospect_nom_de_rue, " +
                    "                      prospect_code_postal, prospect_ville,\n" +
                    "                    prospect_telephone,\n" +
                    "                    prospect_adresse_mail,\n" +
                    "                    prospect_commentaires, prospect_date_prosprection, prospect_interesse)\n" +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        } else {
            strSql = "UPDATE prospect SET prospect_raison_sociale = ?, prospect_numero_de_rue = ?, prospect_nom_de_rue = ?, " +
                    "prospect_code_postal = ?, prospect_ville = ?, prospect_telephone = ?, " +
                    "prospect_adresse_mail = ?, prospect_commentaires = ?, prospect_date_prosprection = ?, " +
                    "prospect_interesse = ? WHERE prospect_identifiant = ? ";
        }
        try (PreparedStatement statement = MySQLDatabaseConnection.getConnection().prepareStatement(strSql)) {
            statement.setString(1, prospect.getRaisonSociale());
            statement.setString(2, prospect.getNumeroDeRue());
            statement.setString(3, prospect.getNomDeRue());
            statement.setString(4, prospect.getCodePostal());
            statement.setString(5, prospect.getVille());
            statement.setString(6, prospect.getTelephone());
            statement.setString(7, prospect.getAdresseMail());
            statement.setString(8, prospect.getCommentaires());
            statement.setDate(9, java.sql.Date.valueOf(prospect.getDateProsprection()));
            statement.setString(10, prospect.getProspectInteresse());
            if (prospect.getIdentifiant() != null) {
                statement.setInt(11, prospect.getIdentifiant());
            }
            statement.executeUpdate();
        } catch (SQLException sqlException) {
            if (sqlException.getErrorCode() == 1062) {
                throw new ExceptionDAO("Une erreur est survenue lors de la création d'un prospect : " +
                        sqlException.getMessage() + ", cause : " + sqlException.getSQLState(), 1);
            } else {
                throw new ExceptionDAO("Une erreur est survenue lors de la création d'un prospect : " +
                        sqlException.getMessage() + ", cause : " + sqlException.getSQLState(), 5);
            }
        }
    }

    private Prospect convertResultSetToProspect(ResultSet resultSet) throws ExceptionEntity, SQLException {
        Integer identifiant = resultSet.getInt("prospect_identifiant");
        String raisonSociale = resultSet.getString("prospect_raison_sociale");
        String numeroDeRue = resultSet.getString("prospect_numero_de_rue");
        String nomDeRue = resultSet.getString("prospect_nom_de_rue");
        String codePostal = resultSet.getString("prospect_code_postal");
        String ville = resultSet.getString("prospect_ville");
        String telephone = resultSet.getString("prospect_telephone");
        String adresseMail = resultSet.getString("prospect_adresse_mail");
        String commentaires = resultSet.getString("prospect_commentaires");
        Date dateProsprection = resultSet.getDate("prospect_date_prosprection");
        String prospectInteresse = resultSet.getString("prospect_interesse");
        return new Prospect(identifiant, raisonSociale, numeroDeRue, nomDeRue,
                codePostal, ville,
                telephone, adresseMail, commentaires, dateProsprection.toLocalDate(), prospectInteresse);
    }
    //--------------------- INSTANCE METHODS -----------------------------------
    //--------------------- ABSTRACT METHODS -----------------------------------
    //--------------------- STATIC - GETTERS - SETTERS -------------------------
    //--------------------- GETTERS - SETTERS ----------------------------------
    //--------------------- TO STRING METHOD------------------------------------
}
