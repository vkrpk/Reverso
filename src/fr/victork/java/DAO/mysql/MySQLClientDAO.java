package fr.victork.java.DAO.mysql;

import fr.victork.java.DAO.InterfaceDAOClient;
import fr.victork.java.Entity.Client;
import fr.victork.java.Entity.Contrat;
import fr.victork.java.Exception.ExceptionDAO;
import fr.victork.java.Exception.ExceptionEntity;

import java.sql.*;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class MySQLClientDAO implements InterfaceDAOClient<Client> {
    //--------------------- CONSTANTS ------------------------------------------
    //--------------------- STATIC VARIABLES -----------------------------------
    //--------------------- INSTANCE VARIABLES ---------------------------------
    //--------------------- CONSTRUCTORS ---------------------------------------
    //--------------------- STATIC METHODS -------------------------------------
    public ArrayList<Client> findAll()
            throws ExceptionEntity, ExceptionDAO {
        String strSql = "SELECT * FROM client";
        ArrayList<Client> collectionClients = new ArrayList<>();
        try (Statement statement = MySQLDatabaseConnection.getInstance().getConnection().createStatement(); ResultSet resultSet =
                statement.executeQuery(strSql)) {
            while (resultSet.next()) {
                Integer identifiant = resultSet.getInt("client_identifiant");
                String raisonSociale = resultSet.getString("client_raison_sociale");
                String numeroDeRue = resultSet.getString("client_numero_de_rue");
                String nomDeRue = resultSet.getString("client_nom_de_rue");
                String codePostal = resultSet.getString("client_code_postal");
                String ville = resultSet.getString("client_ville");
                String telephone = resultSet.getString("client_telephone");
                String adresseMail = resultSet.getString("client_adresse_mail");
                String commentaires = resultSet.getString("client_commentaires");
                Double chiffreAffaires = resultSet.getDouble("client_chiffre_affaires");
                Integer nombreEmployes = resultSet.getInt("client_nombre_employes");
                Client client = new Client(identifiant, raisonSociale, numeroDeRue, nomDeRue,
                        codePostal, ville,
                        telephone, adresseMail, commentaires, chiffreAffaires, nombreEmployes);
                collectionClients.add(client);
            }
        } catch (SQLException sqlException) {
            throw new ExceptionDAO("Une erreur est survenue lors de la recherche de la liste des clients : " +
                    sqlException.getMessage() + ", cause : " + sqlException.getSQLState(), 5);
        }
        return collectionClients;
    }

    public Client find(Integer id)
            throws ExceptionEntity, ExceptionDAO {
        Client client = new Client();
        String strSql = "SELECT * FROM client WHERE client_identifiant=?";
        try (PreparedStatement statement = MySQLDatabaseConnection.getInstance().getConnection().prepareStatement(strSql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Integer identifiant = resultSet.getInt("client_identifiant");
                    String raisonSociale = resultSet.getString("client_raison_sociale");
                    String numeroDeRue = resultSet.getString("client_numero_de_rue");
                    String nomDeRue = resultSet.getString("client_nom_de_rue");
                    String codePostal = resultSet.getString("client_code_postal");
                    String ville = resultSet.getString("client_ville");
                    String telephone = resultSet.getString("client_telephone");
                    String adresseMail = resultSet.getString("client_adresse_mail");
                    String commentaires = resultSet.getString("client_commentaires");
                    Double chiffreAffaires = resultSet.getDouble("client_chiffre_affaires");
                    Integer nombreEmployes = resultSet.getInt("client_nombre_employes");
                    client = new Client(identifiant, raisonSociale, numeroDeRue, nomDeRue,
                            codePostal, ville,
                            telephone, adresseMail, commentaires, chiffreAffaires, nombreEmployes);
                    ArrayList<Contrat> listeContrats = findByIdClient(client);
                    client.setListeContrat(listeContrats);
                    return client;
                }
            }
        } catch (SQLException sqlException) {
            throw new ExceptionDAO("Une erreur est survenue lors de la recherche d'un client : " +
                    sqlException.getMessage() + ", cause : " + sqlException.getSQLState(), 5);
        }
        return client;
    }

    public void delete(Integer id)
            throws ExceptionDAO {
        String strSql = "DELETE FROM client WHERE client_identifiant=?";
        try (PreparedStatement statement = MySQLDatabaseConnection.getInstance().getConnection().prepareStatement(strSql)) {
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException sqlException) {
            throw new ExceptionDAO("Une erreur est survenue lors de la suppression d'un client : " +
                    sqlException.getMessage() + ", cause : " + sqlException.getSQLState(), 5);
        }
    }

    public void save(Client client)
            throws ExceptionEntity, ExceptionDAO {
        String strSql;
        if (client.getIdentifiant() == null) {
            strSql = "INSERT INTO client (client_raison_sociale, client_numero_de_rue, client_nom_de_rue, " +
                    "                      client_code_postal, client_ville,\n" +
                    "                    client_telephone,\n" +
                    "                    client_adresse_mail,\n" +
                    "                    client_commentaires, client_chiffre_affaires, client_nombre_employes)\n" +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        } else {
            strSql = "UPDATE client SET client_raison_sociale = ?, client_numero_de_rue = ?, client_nom_de_rue = ?, " +
                    "client_code_postal = ?, client_ville = ?, client_telephone = ?, " +
                    "client_adresse_mail = ?, client_commentaires = ?, client_chiffre_affaires = ?, " +
                    "client_nombre_employes = ? WHERE client_identifiant = ? ";
        }
        try (PreparedStatement statement = MySQLDatabaseConnection.getInstance().getConnection().prepareStatement(strSql)) {
            statement.setString(1, client.getRaisonSociale());
            statement.setString(2, client.getNumeroDeRue());
            statement.setString(3, client.getNomDeRue());
            statement.setString(4, client.getCodePostal());
            statement.setString(5, client.getVille());
            statement.setString(6, client.getTelephone());
            statement.setString(7, client.getAdresseMail());
            statement.setString(8, client.getCommentaires());
            statement.setDouble(9, client.getChiffreAffaires());
            statement.setInt(10, client.getNombreEmployes());
            if (client.getIdentifiant() != null) {
                statement.setInt(11, client.getIdentifiant());
            }
            statement.executeUpdate();
        } catch (SQLException sqlException) {
            if (sqlException.getErrorCode() == 1062) {
                throw new ExceptionDAO("Une erreur est survenue lors de la création d'un client : " +
                        sqlException.getMessage() + ", cause : " + sqlException.getSQLState(), 1);
            } else {
                throw new ExceptionDAO("Une erreur est survenue lors de la création d'un client : " +
                        sqlException.getMessage() + ", cause : " + sqlException.getSQLState(), 5);
            }
        }
    }

    public ArrayList<Contrat> findByIdClient(Client client) throws ExceptionEntity, ExceptionDAO {
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
    //--------------------- INSTANCE METHODS -----------------------------------
    //--------------------- ABSTRACT METHODS -----------------------------------
    //--------------------- STATIC - GETTERS - SETTERS -------------------------
    //--------------------- GETTERS - SETTERS ----------------------------------
    //--------------------- TO STRING METHOD------------------------------------
}
