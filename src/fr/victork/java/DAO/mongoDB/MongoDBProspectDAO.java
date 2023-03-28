/**
 * @author Victor K
 * @version 1.00
 * La classe implémente l'interface générique DAO<T> et est utilisée pour gérer
 * la persistance des objets de type Prospect dans une base de données MongoDB.
 * Elle utilise la classe MongoDBDatabaseConnection pour établir une connexion à la base de données.
 * La classe utilise la classe Document de MongoDB pour représenter les objets stockés dans la base de données.
 */
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

/**
 * La classe implémente l'interface générique DAO<T> et est utilisée pour gérer
 * la persistance des objets de type Prospect dans une base de données MongoDB.
 * Elle utilise la classe MongoDBDatabaseConnection pour établir une connexion à la base de données.
 * La classe utilise la classe Document de MongoDB pour représenter les objets stockés dans la base de données.
 */
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

    /**
     * Cette méthode permet de sauvegarder un objet Prospect dans la base de données.
     * Si le prospect existe déjà dans la base de données, les informations sont mises à jour. Sinon, un nouveau
     * document est créé.
     *
     * @param prospect Prospect dont on souhaite sauvegarder ou modifier dans la base de données
     * @throws ExceptionEntity si une exception se produit lors de l'interaction avec l'entité Client
     * @throws ExceptionDAO    si une exception se produit lors de l'interaction avec la base de données
     */
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


    /**
     * Cette méthode permet de récupérer un objet Prospect à partir de son identifiant unique dans la base de données.
     *
     * @param id Identifiant unique de l'objet à récupérer.
     * @return Client retourne un objet Prospect trouvé dans la base de données
     * @throws ExceptionEntity si une exception se produit lors de l'interaction avec l'entité Prospect
     * @throws ExceptionDAO    si une exception se produit lors de l'interaction avec la base de données
     */
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


    /**
     * Cette méthode permet de supprimer un objet Prospect de la base de données à partir de son identifiant unique.
     *
     * @param id Identifiant unique de l'objet à supprimer de la base de données.
     * @throws ExceptionDAO si une exception se produit lors de l'interaction avec la base de données
     */
    @Override
    public void delete(Integer id) throws ExceptionDAO {
        collectionProspect.deleteOne(Filters.eq("prospect_identifiant", id));
    }

    /**
     * Cette méthode permet de récupérer tous les objets Prospect présents dans la base de données.
     *
     * @return ArrayList<Prospect> Retourne une liste d'objet Prospect présent dans la base de données.
     * @throws ExceptionEntity si une exception se produit lors de l'interaction avec l'entité Prospect
     * @throws ExceptionDAO    si une exception se produit lors de l'interaction avec la base de données
     */
    @Override
    public ArrayList<Prospect> findAll() throws ExceptionEntity, ExceptionDAO {
        MongoCursor<Document> cursor = collectionProspect.find().iterator();
        ArrayList<Prospect> collectionProspects = new ArrayList<>();
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
                collectionProspects.add(prospect);
            }
        } finally {
            cursor.close();
        }
        return collectionProspects;
    }

    /**
     * permet d'obtenir le dernier identifiant utilisé pour un prospect dans la base de données
     *
     * @return Retourne l'identifiant le plus élevé dans la base de données
     */
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
