package fr.victork.java.DAO.mysql;

import fr.victork.java.DAO.DAO;
import fr.victork.java.Entity.Prospect;
import fr.victork.java.Exception.ExceptionDAO;
import fr.victork.java.Exception.ExceptionEntity;

import java.sql.*;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class MySQLProspectDAO implements DAO<Prospect> {
    //--------------------- CONSTANTS ------------------------------------------
    //--------------------- STATIC VARIABLES -----------------------------------
    //--------------------- INSTANCE VARIABLES ---------------------------------
    //--------------------- CONSTRUCTORS ---------------------------------------
    //--------------------- STATIC METHODS -------------------------------------
    public ArrayList<Prospect> findAll()
            throws ExceptionEntity, ExceptionDAO {
        String strSql = "SELECT * FROM prospect";
        ArrayList<Prospect> collectionProspects = new ArrayList<>();
        try (Statement statement = MySQLDatabaseConnection.getInstance().getConnection().createStatement();
             ResultSet resultSet = statement.executeQuery(strSql)) {
            while (resultSet.next()) {
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
                Prospect prospect = new Prospect(identifiant, raisonSociale, numeroDeRue, nomDeRue,
                        codePostal, ville,
                        telephone, adresseMail, commentaires, dateProsprection.toLocalDate(), prospectInteresse);
                collectionProspects.add(prospect);
            }
        } catch (SQLException sqlException) {
            throw new ExceptionDAO("Une erreur est survenue lors de la recherche de la liste des prospects : " +
                    sqlException.getMessage() + ", cause : " + sqlException.getSQLState(), 5);
        }
        return collectionProspects;
    }

    public Prospect find(Integer id)
            throws ExceptionEntity, ExceptionDAO {
        Prospect prospect = new Prospect();
        String strSql = "SELECT * FROM prospect WHERE prospect_identifiant=?";
        try (PreparedStatement statement = MySQLDatabaseConnection.getInstance().getConnection().prepareStatement(strSql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
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
                    prospect = new Prospect(identifiant, raisonSociale, numeroDeRue, nomDeRue,
                            codePostal, ville,
                            telephone, adresseMail, commentaires, dateProsprection.toLocalDate(), prospectInteresse);
                    return prospect;
                }
            }
        } catch (SQLException sqlException) {
            throw new ExceptionDAO("Une erreur est survenue lors de la recherche d'un prospect : " +
                    sqlException.getMessage() + ", cause : " + sqlException.getSQLState(), 5);
        }
        return prospect;
    }

    public void delete(Integer id)
            throws ExceptionDAO {
        Prospect prospect = new Prospect();
        String strSql = "DELETE FROM prospect WHERE prospect_identifiant=?";
        try (PreparedStatement statement = MySQLDatabaseConnection.getInstance().getConnection().prepareStatement(strSql)) {
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException sqlException) {
            throw new ExceptionDAO("Une erreur est survenue lors de la suppression d'un prospect : " +
                    sqlException.getMessage() + ", cause : " + sqlException.getSQLState(), 5);
        }
    }

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
        try (PreparedStatement statement = MySQLDatabaseConnection.getInstance().getConnection().prepareStatement(strSql)) {
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
    //--------------------- INSTANCE METHODS -----------------------------------
    //--------------------- ABSTRACT METHODS -----------------------------------
    //--------------------- STATIC - GETTERS - SETTERS -------------------------
    //--------------------- GETTERS - SETTERS ----------------------------------
    //--------------------- TO STRING METHOD------------------------------------
}
