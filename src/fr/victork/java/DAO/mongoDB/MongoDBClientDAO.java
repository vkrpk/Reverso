/**
 * @author Victor K
 * @version 1.00
 * La classe implémente l'interface générique InterfaceDAOClient<T> et est utilisée pour gérer
 * la persistance des objets de type Client dans une base de données MongoDB.
 * Elle utilise la classe MongoDBDatabaseConnection pour établir une connexion à la base de données.
 * La classe utilise la classe Document de MongoDB pour représenter les objets stockés dans la base de données.
 */
package fr.victork.java.DAO.mongoDB;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import fr.victork.java.DAO.InterfaceDAOClient;
import fr.victork.java.Entity.Client;
import fr.victork.java.Entity.Contrat;
import fr.victork.java.Exception.ExceptionDAO;
import fr.victork.java.Exception.ExceptionEntity;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * La classe implémente l'interface générique InterfaceDAOClient<T> et est utilisée pour gérer
 * la persistance des objets de type Client dans une base de données MongoDB.
 * Elle utilise la classe MongoDBDatabaseConnection pour établir une connexion à la base de données.
 * La classe utilise la classe Document de MongoDB pour représenter les objets stockés dans la base de données.
 */
public class MongoDBClientDAO implements InterfaceDAOClient<Client> {
    //--------------------- CONSTANTS ------------------------------------------
    //--------------------- STATIC VARIABLES -----------------------------------
    Integer lastId = 0;
    //--------------------- INSTANCE VARIABLES ---------------------------------
    MongoDatabase mongoDatabase = MongoDBDatabaseConnection.getInstance().getConnection();
    MongoCollection<Document> collectionClient = mongoDatabase.getCollection("client");

    //--------------------- CONSTRUCTORS ---------------------------------------
    //--------------------- STATIC METHODS -------------------------------------
    //--------------------- INSTANCE METHODS -----------------------------------

    /**
     * Cette méthode permet de sauvegarder un objet Client dans la base de données.
     * Si le client existe déjà dans la base de données, les informations sont mises à jour. Sinon, un nouveau
     * document est créé.
     *
     * @param client Client dont on souhaite sauvegarder ou modifier dans la base de données
     * @throws ExceptionEntity si une exception se produit lors de l'interaction avec l'entité Client
     * @throws ExceptionDAO    si une exception se produit lors de l'interaction avec la base de données
     */
    @Override
    public void save(Client client) throws ExceptionEntity, ExceptionDAO {
        Document query = new Document("client_identifiant", client.getIdentifiant());
        Document document = collectionClient.find(query).first();
        if (document != null) {
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

            collectionClient.updateOne(query, new Document("$set", document));
        } else {
            document = new Document();
            document.append("client_identifiant", getLastId() + 1);
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
    }

    /**
     * Cette méthode permet de récupérer un objet Client à partir de son identifiant unique dans la base de données.
     *
     * @param id Identifiant unique de l'objet à récupérer.
     * @return Client retourne un objet Client trouvé dans la base de données
     * @throws ExceptionEntity si une exception se produit lors de l'interaction avec l'entité Client
     * @throws ExceptionDAO    si une exception se produit lors de l'interaction avec la base de données
     */
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
            Object chiffreAffaires = result.get("client_chiffre_affaires");
            Double chiffreAffairesDouble = (chiffreAffaires instanceof Number) ? ((Number) chiffreAffaires).doubleValue() : null;
            Integer nombreEmployes = result.getInteger("client_nombre_employes");
            Client client = new Client(identifiant, raisonSociale, numeroDeRue, nomDeRue,
                    codePostal, ville,
                    telephone, adresseMail, commentaires, chiffreAffairesDouble, nombreEmployes
            );
            ArrayList<Contrat> listeContrats = findByIdClient(client);
            client.setListeContrat(listeContrats);
            return client;
        }
        return null;
    }


    /**
     * Cette méthode permet de supprimer un objet Client de la base de données à partir de son identifiant unique.
     *
     * @param id Identifiant unique de l'objet à supprimer de la base de données.
     * @throws ExceptionDAO si une exception se produit lors de l'interaction avec la base de données
     */
    @Override
    public void delete(Integer id) throws ExceptionDAO {
        collectionClient.deleteOne(Filters.eq("client_identifiant", id));
    }


    /**
     * Cette méthode permet de récupérer tous les objets Client présents dans la base de données.
     *
     * @return ArrayList<Client> Retourne une liste d'objet Client présent dans la base de données.
     * @throws ExceptionEntity si une exception se produit lors de l'interaction avec l'entité Client
     * @throws ExceptionDAO    si une exception se produit lors de l'interaction avec la base de données
     */
    @Override
    public ArrayList<Client> findAll() throws ExceptionEntity, ExceptionDAO {
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
                Object chiffreAffaires = result.get("client_chiffre_affaires");
                Double chiffreAffairesDouble = (chiffreAffaires instanceof Number) ? ((Number) chiffreAffaires).doubleValue() : null;
                Integer nombreEmployes = result.getInteger("client_nombre_employes");
                Client client = new Client(identifiant, raisonSociale, numeroDeRue, nomDeRue,
                        codePostal, ville,
                        telephone, adresseMail, commentaires, chiffreAffairesDouble, nombreEmployes
                );
                collectionClients.add(client);
            }
        } finally {
            cursor.close();
        }
        return collectionClients;
    }

    /**
     * permet de récupérer tous les contrats associés à un client à partir de son identifiant unique dans la base de données.
     *
     * @param client Client dont on souhaite récupérer les contrats.
     * @return Retourne une liste de Contrat associée au client spécifié.
     * @throws ExceptionEntity si une exception se produit lors de l'interaction avec l'entité
     * @throws ExceptionDAO    si une exception se produit lors de l'interaction avec la base de données
     */
    public ArrayList<Contrat> findByIdClient(Client client) throws ExceptionEntity, ExceptionDAO {
        ArrayList<Contrat> collectionContratsByIdClient = new ArrayList<>();
        BasicDBObject filter = new BasicDBObject("identifiant_client", client.getIdentifiant());
        MongoCollection<Document> collectionContrat = mongoDatabase.getCollection("contrat");
        MongoCursor<Document> cursor = collectionContrat.find(filter).iterator();
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
                collectionContratsByIdClient.add(contrat);
            }
        } finally {
            cursor.close();
        }
        return collectionContratsByIdClient;
    }

    /**
     * permet d'obtenir le dernier identifiant utilisé pour un client dans la base de données
     *
     * @return Retourne l'identifiant le plus élevé dans la base de données
     */
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
