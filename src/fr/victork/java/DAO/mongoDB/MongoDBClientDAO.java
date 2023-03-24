package fr.victork.java.DAO.mongoDB;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import fr.victork.java.DAO.DAO;
import fr.victork.java.Entity.Client;
import fr.victork.java.Exception.ExceptionDAO;
import fr.victork.java.Exception.ExceptionEntity;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MongoDBClientDAO implements DAO<Client> {
    //--------------------- CONSTANTS ------------------------------------------
    //--------------------- STATIC VARIABLES -----------------------------------
    Integer lastId = 0;
    //--------------------- INSTANCE VARIABLES ---------------------------------
    MongoDatabase mongoDatabase = MongoDBDatabaseConnection.getInstance().getConnection();
    MongoCollection<Document> collectionClient = mongoDatabase.getCollection("client");

    //--------------------- CONSTRUCTORS ---------------------------------------
    //--------------------- STATIC METHODS -------------------------------------
    //--------------------- INSTANCE METHODS -----------------------------------
    @Override
    public void save(Client client) throws ExceptionEntity, ExceptionDAO {
        Document document = new Document();
        if (client.getIdentifiant() == null) {
            document.append("client_identifiant", getLastId() + 1);
        } else {
            document.append("client_identifiant", client.getIdentifiant());
        }
        document.append("client_raison_sociale", client.getRaisonSociale());
        document.append("client_numero_de_rue", client.getNumeroDeRue());
        document.append("client_nom_de_rue", client.getNomDeRue());
        document.append("client_code_postal", client.getCodePostal());
        document.append("client_ville", client.getVille());
        document.append("client_telephone", client.getTelephone());
        document.append("client_adresse_mail", client.getAdresseMail());
        document.append("client_commentaires", client.getCommentaires());
        document.append("client_chiffre_affaires", client.getChiffreAffaires());
        document.append("client_nombre_employes", client.getNombreEmployes());
        collectionClient.insertOne(document);
    }


    @Override
    public Client find(Integer id) throws ExceptionEntity, ExceptionDAO {
        Document query = new Document("client_identifiant", id);
        Document result = collectionClient.find(query).first();
        if (result != null) {
            Integer identifiant = result.getInteger("client_identifiant");
            String raisonSociale = result.getString("client_raison_sociale");
            String numeroDeRue = result.getString("client_numero_de_rue");
            String nomDeRue = result.getString("client_nom_de_rue");
            String codePostal = result.getString("client_code_postal");
            String ville = result.getString("client_ville");
            String telephone = result.getString("client_telephone");
            String adresseMail = result.getString("client_adresse_mail");
            String commentaires = result.getString("client_commentaires");
            Double chiffreAffaires = result.getDouble("client_chiffre_affaires");
            Integer nombreEmployes = result.getInteger("client_nombre_employes");
            Client client = new Client(identifiant, raisonSociale, numeroDeRue, nomDeRue,
                    codePostal, ville,
                    telephone, adresseMail, commentaires, chiffreAffaires, nombreEmployes
            );
            return client;
        }
        return null;
    }


    @Override
    public void delete(Integer id) throws ExceptionDAO {
        collectionClient.deleteOne(Filters.eq("client_identifiant", id));
    }


    @Override
    public List<Client> findAll() throws ExceptionEntity, ExceptionDAO {
        MongoCursor<Document> cursor = collectionClient.find().iterator();
        ArrayList<Client> collectionClients = new ArrayList<>();
        try {
            while (cursor.hasNext()) {
                Document result = cursor.next();
                Integer identifiant = result.getInteger("client_identifiant");
                String raisonSociale = result.getString("client_raison_sociale");
                String numeroDeRue = result.getString("client_numero_de_rue");
                String nomDeRue = result.getString("client_nom_de_rue");
                String codePostal = result.getString("client_code_postal");
                String ville = result.getString("client_ville");
                String telephone = result.getString("client_telephone");
                String adresseMail = result.getString("client_adresse_mail");
                String commentaires = result.getString("client_commentaires");
                Double chiffreAffaires = result.getDouble("client_chiffre_affaires");
                Integer nombreEmployes = result.getInteger("client_nombre_employes");
                Client client = new Client(identifiant, raisonSociale, numeroDeRue, nomDeRue,
                        codePostal, ville,
                        telephone, adresseMail, commentaires, chiffreAffaires, nombreEmployes
                );
                collectionClients.add(client);
            }
        } finally {
            cursor.close();
        }
        return collectionClients;
    }

    private Integer getLastId() {
        List<Document> pipeline = Arrays.asList(
                new Document("$sort", new Document("client_identifiant", -1)),
                new Document("$limit", 1)
        );

        Document result = collectionClient.aggregate(pipeline).first();

        if (result != null && result.getInteger("client_identifiant") != null) {
            int highestId = result.getInteger("client_identifiant");
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
