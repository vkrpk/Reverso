/**
 * @author Victor K
 * @version 1.00
 * Cette classe représente un prospect
 */
package fr.victork.Entity;

import fr.victork.Exception.ExceptionEntity;
import fr.victork.Tools.Tools;

import java.time.LocalDate;

/**
 * Cette classe représente un prospect héritant de Société
 */
public class Prospect extends Societe {
    //--------------------- CONSTANTS ------------------------------------------
    //--------------------- STATIC VARIABLES -----------------------------------
    //--------------------- INSTANCE VARIABLES ---------------------------------
    private LocalDate dateProsprection;
    private String prospectInteresse;

    //--------------------- CONSTRUCTORS ---------------------------------------

    public Prospect() {
    }

    /**
     * Construit une nouvelle instance de Prospect
     *
     * @param raisonSociale     Raison sociale
     * @param numeroDeRue       Numéro de la rue
     * @param nomDeRue          Nom de la rue
     * @param codePostal        Code postal
     * @param ville             Ville
     * @param telephone         Téléphone
     * @param adresseMail       Adresse mail
     * @param commentaires      Commentaires facultatifs
     * @param dateProsprection  Date de prospection
     * @param prospectInteresse Intéressement du prospect
     * @throws ExceptionEntity Remonte une exception en cas d'erreur
     */
    public Prospect(Integer identifiant, String raisonSociale, String numeroDeRue, String nomDeRue,
                    String codePostal, String ville, String telephone,
                    String adresseMail, String commentaires, LocalDate dateProsprection,
                    String prospectInteresse) throws ExceptionEntity {
        super(identifiant, raisonSociale, numeroDeRue, nomDeRue, codePostal, ville,
                telephone, adresseMail, commentaires);
        this.setDateProsprection(dateProsprection);
        this.setProspectInteresse(prospectInteresse);
    }
    //--------------------- STATIC METHODS -------------------------------------
    //--------------------- INSTANCE METHODS -----------------------------------
    //--------------------- ABSTRACT METHODS -----------------------------------
    //--------------------- STATIC - GETTERS - SETTERS -------------------------
    //--------------------- GETTERS - SETTERS ----------------------------------

    /**
     * Getter pour la date de prospection du prospect
     *
     * @return La date de prospection du prospect
     */
    public LocalDate getDateProsprection() {
        return dateProsprection;
    }

    /**
     * Affecte une date de prospection à un prospect
     *
     * @param dateProsprection La valeur doit être de type LocalDate
     */
    public void setDateProsprection(LocalDate dateProsprection) {
        this.dateProsprection = dateProsprection;
    }

    public String getProspectInteresse() {
        return prospectInteresse;
    }

    /**
     * Indique si le prospect est intéressé
     *
     * @param prospectInteresse La valeur doit être "Oui" ou "Non"
     * @throws ExceptionEntity Remonte une exception en cas d'erreur
     */
    public void setProspectInteresse(String prospectInteresse)
            throws ExceptionEntity {
        if ((prospectInteresse.equals("Oui") ||
                prospectInteresse.equals("Non")) &&
                Tools.controlStringIsNotEmpty(prospectInteresse)) {
            this.prospectInteresse = prospectInteresse;
        } else {
            throw new ExceptionEntity(
                    "Prospect Intéressé peut être soit Oui " + "soit Non.");
        }
    }

    //--------------------- TO STRING METHOD------------------------------------

    /**
     * @return Retourne la raison sociale du prospect
     */
    public String toString() {
        return getRaisonSociale();
    }

    /**
     * @return Retourne toutes les propriétés du prospect
     */
    public String affichage() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append(", dateProsprection='" + dateProsprection + '\'' +
                ", prospectInteresse='" + prospectInteresse + '\'' + '}');
        return sb.toString();
    }
}
