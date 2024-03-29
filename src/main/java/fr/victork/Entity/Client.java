/**
 * @author Victor K
 * @version 1.00
 * Cette classe représente un client
 */
package fr.victork.Entity;

import fr.victork.DAO.mysql.MySQLContratDAO;
import fr.victork.Exception.ExceptionDAO;
import fr.victork.Exception.ExceptionEntity;

import java.util.ArrayList;

/**
 * Cette classe représente un client héritant de Société
 */
public class Client extends Societe {
    //--------------------- CONSTANTS ------------------------------------------
    //--------------------- STATIC VARIABLES -----------------------------------
    //--------------------- INSTANCE VARIABLES ---------------------------------
    private Double chiffreAffaires;
    private int nombreEmployes;
    private ArrayList<Contrat> listeContrat;

    //--------------------- CONSTRUCTORS ---------------------------------------

    public Client() {
    }

    /**
     * Construit une nouvelle instance de Client
     *
     * @param raisonSociale   Raison sociale
     * @param numeroDeRue     Numéro de la rue
     * @param nomDeRue        Nom de la rue
     * @param codePostal      Code postal
     * @param ville           Ville
     * @param telephone       Téléphone
     * @param adresseMail     Adresse mail
     * @param commentaires    Commentaires facultatifs
     * @param chiffreAffaires Chiffre d'affaires
     * @param nombreEmployes  Nombre d'employés
     * @throws ExceptionEntity Remonte une exception en cas d'erreur
     */
    public Client(Integer identifiant, String raisonSociale, String numeroDeRue, String nomDeRue,
                  String codePostal, String ville, String telephone,
                  String adresseMail, String commentaires, Double chiffreAffaires,
                  int nombreEmployes) throws ExceptionEntity {
        super(identifiant, raisonSociale, numeroDeRue, nomDeRue, codePostal, ville,
                telephone, adresseMail, commentaires);
        this.setChiffreAffaires(chiffreAffaires);
        this.setNombreEmployes(nombreEmployes);
    }
    //--------------------- STATIC METHODS -------------------------------------
    //--------------------- INSTANCE METHODS -----------------------------------
    //--------------------- ABSTRACT METHODS -----------------------------------
    //--------------------- STATIC - GETTERS - SETTERS -------------------------
    //--------------------- GETTERS - SETTERS ----------------------------------

    public Double getChiffreAffaires() {
        return chiffreAffaires;
    }

    /**
     * Affecte un chiffre d'affaires à un client
     *
     * @param chiffreAffaires La valeur doit être supérieure à 200
     * @throws ExceptionEntity Remonte une exception en cas d'erreur
     */
    public void setChiffreAffaires(Double chiffreAffaires)
            throws ExceptionEntity {
        if (chiffreAffaires >= 200) {
            this.chiffreAffaires = chiffreAffaires;
        } else {
            throw new ExceptionEntity(
                    "Le chiffre d'affaires " + "doit être supérieur à 200€.");
        }
    }

    public int getNombreEmployes() {
        return nombreEmployes;
    }

    /**
     * Affecte un nombre d'employés à un client
     *
     * @param nombreEmployes La valeur doit être supérieure à zéro
     * @throws ExceptionEntity Remonte une exception en cas d'erreur
     */
    public void setNombreEmployes(int nombreEmployes) throws ExceptionEntity {
        if (nombreEmployes > 0) {
            this.nombreEmployes = nombreEmployes;
        } else {
            throw new ExceptionEntity(
                    "Le nombre d'employés doit" + " être supérieur à 0.");
        }
    }

    /**
     * @param listeContrat
     */
    public void setListeContrat(ArrayList<Contrat> listeContrat) {
        this.listeContrat = listeContrat;
    }

    public ArrayList<Contrat> getListeContrat() {
        return listeContrat;
    }

    //--------------------- TO STRING METHOD------------------------------------

    /**
     * @return Retourne la raison sociale du client
     */
    public String toString() {
        return getRaisonSociale();
    }

    /**
     * @return Retourne toutes les propriétés du client
     */
    public String affichage() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append(", chiffreAffaires='" + chiffreAffaires + '\'' +
                ", nombreEmployes='" + nombreEmployes + '\'' + '}');
        return sb.toString();
    }
}
