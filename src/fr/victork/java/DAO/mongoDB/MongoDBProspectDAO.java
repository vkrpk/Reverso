package fr.victork.java.DAO.mongoDB;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import fr.victork.java.DAO.DAO;
import fr.victork.java.Entity.Prospect;
import fr.victork.java.Exception.ExceptionDAO;
import fr.victork.java.Exception.ExceptionEntity;
import org.bson.Document;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class MongoDBProspectDAO implements DAO<Prospect> {
    //--------------------- CONSTANTS ------------------------------------------
    //--------------------- STATIC VARIABLES -----------------------------------
    Integer lastId = 0;
    //--------------------- INSTANCE VARIABLES ---------------------------------
    MongoDatabase mongoDatabase = MongoDBDatabaseConnection.getInstance().getConnection();
    MongoCollection<Document> collectionProspect = mongoDatabase.getCollection("prospect");

    //--------------------- CONSTRUCTORS ---------------------------------------
    //--------------------- STATIC METHODS -------------------------------------
    //--------------------- INSTANCE METHODS -----------------------------------
    @Override
    public void save(Prospect prospect) throws ExceptionEntity, ExceptionDAO {
        Document query = new Document("prospect_identifiant", prospect.getIdentifiant());
        Document document = collectionProspect.find(query).first();
        if (document != null) {
            document.append("prospect_raison_sociale", prospect.getRaisonSociale());
            document.append("prospect_numero_de_rue", prospect.getNumeroDeRue());
            document.append("prospect_nom_de_rue", prospect.getNomDeRue());
            document.append("prospect_code_postal", prospect.getCodePostal());
            document.append("prospect_ville", prospect.getVille());
            document.append("prospect_telephone", prospect.getTelephone());
            document.append("prospect_adresse_mail", prospect.getAdresseMail());
            document.append("prospect_commentaires", prospect.getCommentaires());
            document.append("prospect_date_prospection", prospect.getDateProsprection());
            document.append("prospect_interesse", prospect.getProspectInteresse());

            collectionProspect.updateOne(query, new Document("$set", document));
        } else {
            document = new Document();
            document.append("prospect_identifiant", getLastId() + 1);
            document.append("prospect_raison_sociale", prospect.getRaisonSociale());
            document.append("prospect_numero_de_rue", prospect.getNumeroDeRue());
            document.append("prospect_nom_de_rue", prospect.getNomDeRue());
            document.append("prospect_code_postal", prospect.getCodePostal());
            document.append("prospect_ville", prospect.getVille());
            document.append("prospect_telephone", prospect.getTelephone());
            document.append("prospect_adresse_mail", prospect.getAdresseMail());
            document.append("prospect_commentaires", prospect.getCommentaires());
            document.append("prospect_date_prospection", prospect.getDateProsprection());
            document.append("prospect_interesse", prospect.getProspectInteresse());

            collectionProspect.insertOne(document);
        }
    }


    @Override
    public Prospect find(Integer id) throws ExceptionEntity, ExceptionDAO {
        Document query = new Document("prospect_identifiant", id);
        Document result = collectionProspect.find(query).first();
        if (result != null) {
            Integer identifiant = result.getInteger("prospect_identifiant");
            String raisonSociale = result.getString("prospect_raison_sociale");
            String numeroDeRue = result.getString("prospect_numero_de_rue");
            String nomDeRue = result.getString("prospect_nom_de_rue");
            String codePostal = result.getString("prospect_code_postal");
            String ville = result.getString("prospect_ville");
            String telephone = result.getString("prospect_telephone");
            String adresseMail = result.getString("prospect_adresse_mail");
            String commentaires = result.getString("prospect_commentaires");
            Date prospectDateProspection = result.getDate("prospect_date_prospection");
            String prospectInteresse = result.getString("prospect_interesse");
            Instant instant = prospectDateProspection.toInstant();
            Prospect prospect = new Prospect(identifiant, raisonSociale, numeroDeRue, nomDeRue,
                    codePostal, ville, telephone, adresseMail, commentaires,
                    instant.atZone(ZoneId.systemDefault()).toLocalDate(), prospectInteresse
            );
            return prospect;
        }
        return null;
    }


    @Override
    public void delete(Integer id) throws ExceptionDAO {
        collectionProspect.deleteOne(Filters.eq("prospect_identifiant", id));
    }


    @Override
    public ArrayList<Prospect> findAll() throws ExceptionEntity, ExceptionDAO {
        MongoCursor<Document> cursor = collectionProspect.find().iterator();
        ArrayList<Prospect> collectionClients = new ArrayList<>();
        try {
            while (cursor.hasNext()) {
                Document result = cursor.next();
                Integer identifiant = result.getInteger("prospect_identifiant");
                String raisonSociale = result.getString("prospect_raison_sociale");
                String numeroDeRue = result.getString("prospect_numero_de_rue");
                String nomDeRue = result.getString("prospect_nom_de_rue");
                String codePostal = result.getString("prospect_code_postal");
                String ville = result.getString("prospect_ville");
                String telephone = result.getString("prospect_telephone");
                String adresseMail = result.getString("prospect_adresse_mail");
                String commentaires = result.getString("prospect_commentaires");
                Date prospectDateProspection = result.getDate("prospect_date_prospection");
                String prospectInteresse = result.getString("prospect_interesse");
                Instant instant = prospectDateProspection.toInstant();
                Prospect prospect = new Prospect(identifiant, raisonSociale, numeroDeRue, nomDeRue,
                        codePostal, ville, telephone, adresseMail, commentaires,
                        instant.atZone(ZoneId.systemDefault()).toLocalDate(), prospectInteresse
                );
                collectionClients.add(prospect);
            }
        } finally {
            cursor.close();
        }
        return collectionClients;
    }

    private Integer getLastId() {
        List<Document> pipeline = Arrays.asList(
                new Document("$sort", new Document("prospect_identifiant", -1)),
                new Document("$limit", 1)
        );

        Document result = collectionProspect.aggregate(pipeline).first();

        if (result != null && result.getInteger("prospect_identifiant") != null) {
            int highestId = result.getInteger("prospect_identifiant");
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
