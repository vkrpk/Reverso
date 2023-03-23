package fr.victork.java.DAO;

import fr.victork.java.DAO.mysql.MySQLDatabaseConnection;
import fr.victork.java.Entity.Contrat;
import fr.victork.java.Exception.ExceptionDAO;
import fr.victork.java.Exception.ExceptionEntity;

import javax.swing.*;
import java.lang.reflect.*;
import java.sql.*;
import java.time.DateTimeException;
import java.util.ArrayList;
import java.util.logging.Level;

import static fr.victork.java.Log.LoggerReverso.LOGGER;

public class ReflexionTest {
    //--------------------- CONSTANTS ------------------------------------------
    //--------------------- STATIC VARIABLES -----------------------------------
    //--------------------- INSTANCE VARIABLES ---------------------------------
    //--------------------- CONSTRUCTORS ---------------------------------------
    //--------------------- STATIC METHODS -------------------------------------
    public static <CLASSE> ArrayList<CLASSE> findAll(Class<CLASSE> classe, Integer primaryKey)
            throws NoSuchMethodException {
        Constructor<CLASSE> constructor = classe.getDeclaredConstructor();
        ArrayList<CLASSE> arrayListClass = new ArrayList<>();
        return arrayListClass;
    }

 /*   public static <CLASSE> CLASSE find(Class<CLASSE> classe, Integer primaryKey)
            throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Constructor<CLASSE> constructor = classe.getDeclaredConstructor();
        CLASSE obj = constructor.newInstance();
        return obj;
    }*/

    public static <CLASSE> CLASSE find(Class<CLASSE> classe, Integer primaryKey)
            throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Constructor<CLASSE> constructor = classe.getDeclaredConstructor();
        CLASSE obj = constructor.newInstance();
        return obj;
    }

    public static void main(String[] args) throws ExceptionEntity, ExceptionDAO {
        try {
            //Client client = new MySQLClientDAO().find(1);
            Contrat contrat = new Contrat(3, 1, "Contrat de test généricité", 100.00);
            Class<?> metadata = Contrat.class;


            //String strSql = "SELECT * FROM " + metadata.getSimpleName().toLowerCase();
            String strSql = "SELECT * FROM contrat";
            System.out.println(strSql);
            ArrayList<?> liste = new ArrayList<>();
            try (Statement statement = MySQLDatabaseConnection.getInstance().getConnection().createStatement();
                 ResultSet resultSet = statement.executeQuery(strSql)) {
                while (resultSet.next()) {
                    //////////   ATTRIBUTS   //////////
                    System.out.println("test");
                    int i = 0;
                    while (true) {
                        Field[] fields = metadata.getDeclaredFields();
                        for (Field field : fields) {
                            Class<?> attributeMetaData = field.getType();

                            System.out.printf(
                                    "\t%-10s %s of type %s (is Primitive: %b)\n",
                                    Modifier.toString(field.getModifiers()),
                                    field.getName(),
                                    attributeMetaData.getName(),
                                    attributeMetaData.isPrimitive()
                            );
                        }
                        metadata = metadata.getSuperclass();
                        if (metadata == null) break;
                    }

                  /*  Integer identifiant = resultSet.getInt("prospect_identifiant");
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
                    collectionProspects.add(prospect);*/
                }
            } catch (SQLException sqlException) {
                throw new ExceptionDAO("Une erreur est survenue lors de la recherche de la liste des prospects : " +
                        sqlException.getMessage() + ", cause : " + sqlException.getSQLState(), 5);

            }
            //return liste;
        } catch (DateTimeException dte) {
            JOptionPane.showMessageDialog(null,
                    "La date doit être dans le format suivant : dd/MM/yyyy",
                    "Erreur de saisie", JOptionPane.ERROR_MESSAGE);
            LOGGER.log(Level.WARNING, dte.getMessage());
        } catch (NumberFormatException nft) {
            JOptionPane.showMessageDialog(null,
                    "La valeur saisie doit être uniquement composé de chiffres", "Erreur de saisie",
                    JOptionPane.ERROR_MESSAGE);
            LOGGER.log(Level.WARNING, nft.getMessage());
        } catch (ExceptionEntity ee) {
            JOptionPane.showMessageDialog(null, ee.getMessage(),
                    "Erreur de saisie", JOptionPane.ERROR_MESSAGE);
            LOGGER.log(Level.WARNING, ee.getMessage());
        } catch (ExceptionDAO exceptionDAO) {
            switch (exceptionDAO.getGravite()) {
                case 1:
                    JOptionPane.showMessageDialog(null,
                            "La raison sociale de cette société existe déjà.",
                            "Erreur de saisie",
                            JOptionPane.ERROR_MESSAGE);
                    LOGGER.log(Level.INFO, exceptionDAO.getMessage());
                    break;
                case 5:
                    exceptionDAO.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Erreur dans l'application, l'application doit fermer",
                            "Erreur dans l'application",
                            JOptionPane.ERROR_MESSAGE);
                    LOGGER.log(Level.SEVERE, exceptionDAO.getMessage());
                    System.exit(1);
                    break;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erreur dans l'application, l'application doit fermer", "Erreur dans " +
                            "l'application",
                    JOptionPane.ERROR_MESSAGE);
            LOGGER.log(Level.SEVERE, exception.getMessage());
            System.exit(1);
        }
    }
                    /*Integer identifiant = resultSet.getInt("client_identifiant");
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
                    collection.add(client);*/

    //////////   METHODES   //////////
            /*Method[] methods = metadata.getMethods();
            for (Method method : methods
            ) {
                System.out.println(method.getName());
            }*/

    //////////   CONSTRUCTEUR   //////////
           /* Constructor<?>[] constructors = metadata.getConstructors();
            for (Constructor<?> constructor : constructors
            ) {
                System.out.println(constructor.toString());
            }*/


    //--------------------- INSTANCE METHODS -----------------------------------
    //--------------------- ABSTRACT METHODS -----------------------------------
    //--------------------- STATIC - GETTERS - SETTERS -------------------------
    //--------------------- GETTERS - SETTERS ----------------------------------
    //--------------------- TO STRING METHOD------------------------------------
}
