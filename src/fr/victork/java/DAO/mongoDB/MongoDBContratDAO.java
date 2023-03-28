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
    @Override
    public void delete(Integer id) throws ExceptionDAO {
        collectionContrat.deleteOne(Filters.eq("identifiant_contrat", id));
    }

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
