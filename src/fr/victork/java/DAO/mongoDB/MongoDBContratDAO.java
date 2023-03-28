/**
 * @author Victor K
 * @version 1.00
 * La classe implémente l'interface générique DAO<T> et est utilisée pour gérer
 * la persistance des objets de type Contrat dans une base de données MongoDB.
 * Elle utilise la classe MongoDBDatabaseConnection pour établir une connexion à la base de données.
 * La classe utilise la classe Document de MongoDB pour représenter les objets stockés dans la base de données.
 */
package fr.victork.java.DAO.mongoDB;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import fr.victork.java.DAO.mysql.MySQLDatabaseConnection;
import fr.victork.java.Entity.Client;
import fr.victork.java.Entity.Contrat;
import fr.victork.java.Entity.Prospect;
import fr.victork.java.Exception.ExceptionDAO;
import fr.victork.java.Exception.ExceptionEntity;
import org.bson.Document;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import fr.victork.java.DAO.DAO;


/**
 * La classe implémente l'interface générique DAO<T> et est utilisée pour gérer
 * la persistance des objets de type Contrat dans une base de données MongoDB.
 * Elle utilise la classe MongoDBDatabaseConnection pour établir une connexion à la base de données.
 * La classe utilise la classe Document de MongoDB pour représenter les objets stockés dans la base de données.
 */
public class MongoDBContratDAO implements DAO<Contrat> {
    //--------------------- CONSTANTS ------------------------------------------
    //--------------------- STATIC VARIABLES -----------------------------------
    Integer lastId = 0;
    //--------------------- INSTANCE VARIABLES ---------------------------------
    MongoDatabase mongoDatabase = MongoDBDatabaseConnection.getInstance().getConnection();
    MongoCollection<Document> collectionContrat = mongoDatabase.getCollection("contrat");

    //--------------------- CONSTRUCTORS ---------------------------------------
    //--------------------- STATIC METHODS -------------------------------------
    //--------------------- INSTANCE METHODS -----------------------------------

    /**
     * Cette méthode permet de supprimer un objet Contrat de la base de données à partir de son identifiant unique.
     *
     * @param id Identifiant unique de l'objet à supprimer de la base de données.
     * @throws ExceptionDAO si une exception se produit lors de l'interaction avec la base de données
     */
    @Override
    public void delete(Integer id) throws ExceptionDAO {
        collectionContrat.deleteOne(Filters.eq("identifiant_contrat", id));
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
    @Override
    public void save(Contrat contrat) throws ExceptionEntity, ExceptionDAO {
        Document query = new Document("identifiant_contrat", contrat.getIdentifiantContrat());
        Document document = collectionContrat.find(query).first();
        if (document != null) {
            document.append("identifiant_contrat", contrat.getIdentifiantContrat());
            document.append("identifiant_client", contrat.getIdentifiantClient());
            document.append("libelle", contrat.getLibelle());
            document.append("montant", contrat.getMontant());

            collectionContrat.updateOne(query, new Document("$set", document));
        } else {
            document = new Document();
            document.append("identifiant_contrat", getLastId() + 1);
            document.append("identifiant_client", contrat.getIdentifiantClient());
            document.append("libelle", contrat.getLibelle());
            document.append("montant", contrat.getMontant());

            collectionContrat.insertOne(document);
        }
    }

    /**
     * Cette méthode permet de récupérer un objet Contrat à partir de son identifiant unique dans la base de données.
     *
     * @param id Identifiant unique de l'objet à récupérer.
     * @return Contrat retourne un objet Contrat trouvé dans la base de données
     * @throws ExceptionEntity si une exception se produit lors de l'interaction avec l'entité Contrat
     * @throws ExceptionDAO    si une exception se produit lors de l'interaction avec la base de données
     */
    @Override
    public Contrat find(Integer id) throws ExceptionEntity, ExceptionDAO {
        Document query = new Document("identifiant_contrat", id);
        Document result = collectionContrat.find(query).first();
        if (result != null) {
            Integer identifiantContrat = result.getInteger("identifiant_contrat");
            Integer identifiantClient = result.getInteger("identifiant_client");
            String libelle = result.getString("libelle");
            Object montant = result.get("montant");
            Double montantDouble = (montant instanceof Number) ? ((Number) montant).doubleValue() :
                    null;
            return new Contrat(identifiantContrat, identifiantClient, libelle, montantDouble);
        }
        return null;
    }

    /**
     * Cette méthode permet de récupérer tous les objets Contrat présents dans la base de données.
     *
     * @return ArrayList<Contrat> Retourne une liste d'objet Contrat présent dans la base de données.
     * @throws ExceptionEntity si une exception se produit lors de l'interaction avec l'entité Contrat
     * @throws ExceptionDAO    si une exception se produit lors de l'interaction avec la base de données
     */
    @Override
    public ArrayList<Contrat> findAll() throws ExceptionEntity, ExceptionDAO {
        MongoCursor<Document> cursor = collectionContrat.find().iterator();
        ArrayList<Contrat> collectionContrats = new ArrayList<>();
        try {
            while (cursor.hasNext()) {
                Document result = cursor.next();
                Integer identifiantContrat = result.getInteger("identifiant_contrat");
                Integer identifiantClient = result.getInteger("identifiant_client");
                String libelle = result.getString("libelle");
                Object montant = result.get("montant");
                Double montantDouble = (montant instanceof Number) ? ((Number) montant).doubleValue() :
                        null;
                Contrat contrat = new Contrat(identifiantContrat, identifiantClient, libelle, montantDouble);
                collectionContrats.add(contrat);
            }
        } finally {
            cursor.close();
        }
        return collectionContrats;
    }

    /**
     * permet d'obtenir le dernier identifiant utilisé pour un contrat dans la base de données
     *
     * @return Retourne l'identifiant le plus élevé dans la base de données
     */
    private Integer getLastId() {
        List<Document> pipeline = Arrays.asList(
                new Document("$sort", new Document("identifiant_contrat", -1)),
                new Document("$limit", 1)
        );

        Document result = collectionContrat.aggregate(pipeline).first();

        if (result != null && result.getInteger("identifiant_contrat") != null) {
            int highestId = result.getInteger("identifiant_contrat");
            return highestId;
        } else {
            return 0;
        }
    }
    //--------------------- ABSTRACT METHODS -----------------------------------
    //--------------------- STATIC - GETTERS - SETTERS -------------------------
    //--------------------- GETTERS - SETTERS ----------------------------------
    //--------------------- TO STRING METHOD------------------------------------
}
