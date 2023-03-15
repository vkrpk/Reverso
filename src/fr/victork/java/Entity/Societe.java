/**
 * @author Victor K
 * @version 1.00
 * Cette classe est le modèle pour une société
 */
package fr.victork.java.Entity;

import fr.victork.java.Exception.ExceptionEntity;
import fr.victork.java.Tools.Tools;


public abstract class Societe implements Tools {
    //--------------------- CONSTANTS ------------------------------------------
    //--------------------- STATIC VARIABLES -----------------------------------
    //--------------------- INSTANCE VARIABLES ---------------------------------
    protected int identifiant;
    protected String raisonSociale;
    protected String numeroDeRue;
    protected String nomDeRue;
    protected String codePostal;
    protected String ville;
    protected String telephone;
    protected String adresseMail;
    protected String commentaires;
    //--------------------- CONSTRUCTORS ---------------------------------------

    /**
     * Définit les propriétés communes à toutes les sociétés
     *
     * @param raisonSociale Raison sociale
     * @param numeroDeRue   Numéro de la rue
     * @param nomDeRue      Nom de la rue
     * @param codePostal    Code postal
     * @param ville         Ville
     * @param telephone     Téléphone
     * @param adresseMail   Adresse mail
     * @param commentaires  Commentaires facultatifs
     * @throws ExceptionEntity Remonte une exception en cas d'erreur
     */
    public Societe(String raisonSociale, String numeroDeRue, String nomDeRue,
            String codePostal, String ville, String telephone,
            String adresseMail, String commentaires) throws ExceptionEntity {
        this.setRaisonSociale(raisonSociale);
        this.setNumeroDeRue(numeroDeRue);
        this.setNomDeRue(nomDeRue);
        this.setCodePostal(codePostal);
        this.setVille(ville);
        this.setTelephone(telephone);
        this.setAdresseMail(adresseMail);
        this.setCommentaires(commentaires);
    }

    //--------------------- STATIC METHODS -------------------------------------
    //--------------------- INSTANCE METHODS -----------------------------------
    //--------------------- ABSTRACT METHODS -----------------------------------
    //--------------------- STATIC - GETTERS - SETTERS -------------------------
    //--------------------- GETTERS - SETTERS ----------------------------------

    public int getIdentifiant() {
        return identifiant;
    }

    /**
     * Affecte un identifiant à une société
     *
     * @param identifiant Int auto incrémenté
     */
    public void setIdentifiant(int identifiant) {
        this.identifiant = identifiant;
    }

    public String getRaisonSociale() {
        return raisonSociale;
    }

    /**
     * Affecte le nom de la raison sociale à la société
     *
     * @param raisonSociale La valeur doit être une chaîne non vide
     * @throws ExceptionEntity Remonte une exception en cas d'erreur
     */
    public void setRaisonSociale(String raisonSociale) throws ExceptionEntity {
        if (Tools.controlStringIsNotEmpty(raisonSociale)) {
            this.raisonSociale = raisonSociale;
        } else {
            throw new ExceptionEntity("La raison sociale doit être une chaîne" +
                    " de caractère non vide.");
        }
    }

    public String getNumeroDeRue() {
        return numeroDeRue;
    }

    /**
     * Affecte le numéro de rue à la société
     *
     * @param numeroDeRue La valeur doit être une chaîne non vide
     * @throws ExceptionEntity Remonte une exception en cas d'erreur
     */
    public void setNumeroDeRue(String numeroDeRue) throws ExceptionEntity {
        if (Tools.controlStringIsNotEmpty(numeroDeRue)) {
            this.numeroDeRue = numeroDeRue;
        } else {
            throw new ExceptionEntity("Le numéro de rue doit être une chaîne " +
                    "de caractère non vide.");
        }
    }

    public String getNomDeRue() {
        return nomDeRue;
    }

    /**
     * Affecte le nom de rue à la société
     *
     * @param nomDeRue La valeur doit être une chaîne non vide
     * @throws ExceptionEntity Remonte une exception en cas d'erreur
     */
    public void setNomDeRue(String nomDeRue) throws ExceptionEntity {
        if (Tools.controlStringIsNotEmpty(nomDeRue)) {
            this.nomDeRue = nomDeRue;
        } else {
            throw new ExceptionEntity("Le nom de rue doit être une chaîne de " +
                    "caractère non vide.");
        }
    }

    public String getCodePostal() {
        return codePostal;
    }

    /**
     * Affecte le code postal à la société
     *
     * @param codePostal La valeur doit être une chaîne de chiffres de
     *                   longueur 5
     * @throws ExceptionEntity Remonte une exception en cas d'erreur
     */
    public void setCodePostal(String codePostal) throws ExceptionEntity {
        if (checkRegexDigitsLength(codePostal, 5)) {
            this.codePostal = codePostal;
        } else {
            throw new ExceptionEntity("Le code postal n'est pas valide");
        }
    }

    public String getVille() {
        return ville;
    }

    /**
     * Affecte une ville à la société
     *
     * @param ville La valeur doit être une chaîne non vide
     * @throws ExceptionEntity Remonte une exception en cas d'erreur
     */
    public void setVille(String ville) throws ExceptionEntity {
        if (Tools.controlStringIsNotEmpty(nomDeRue)) {
            this.ville = ville;
        } else {
            throw new ExceptionEntity("Le nom de rue doit être une chaîne de " +
                    "caractère non vide.");
        }
    }

    public String getTelephone() {
        return telephone;
    }

    /**
     * Affecte un numéro de téléphone à la société
     *
     * @param telephone La valeur doit être une chaîne de dix caractères minimum
     * @throws ExceptionEntity Remonte une exception en cas d'erreur
     */
    public void setTelephone(String telephone) throws ExceptionEntity {
        if (telephone != null && telephone.length() >= 10) {
            this.telephone = telephone;
        } else {
            throw new ExceptionEntity(
                    "Le numéro de téléphone n'est pas " + "valide");
        }
    }

    public String getAdresseMail() {
        return adresseMail;
    }

    /**
     * Affecte une adresse mail à la société
     *
     * @param adresseMail La valeur doit être au format *@*
     * @throws ExceptionEntity Remonte une exception en cas d'erreur
     */
    public void setAdresseMail(String adresseMail) throws ExceptionEntity {
        if (Tools.controlStringIsNotEmpty(adresseMail) &&
                adresseMail.matches(".+@.+")) {
            this.adresseMail = adresseMail;
        } else {
            throw new ExceptionEntity("L'adresse mail n'est pas valide, elle " +
                    "doit être au format *@*.");
        }
    }

    public String getCommentaires() {
        return commentaires;
    }

    /**
     * Affecte des commentaires à une société
     *
     * @param commentaires La valeur doit être une chaîne de caractères
     * @throws ExceptionEntity Remonte une exception en cas d'erreur
     */
    public void setCommentaires(String commentaires) throws ExceptionEntity {
        if (commentaires != null) {
            this.commentaires = commentaires;
        } else {
            throw new ExceptionEntity("Les commentaires doivent être une " +
                    "chaîne de caractère non null.");
        }
    }

    //--------------------- TO STRING METHOD------------------------------------

    /**
     * @return Retourne les données de la société
     */
    @Override
    public String toString() {
        return "Societe{" + "identifiant=" + identifiant + ", raisonSociale='" +
                raisonSociale + '\'' + ", numeroDeRue='" + numeroDeRue + '\'' +
                ", nomDeRue='" + nomDeRue + '\'' + ", codePostal='" +
                codePostal + '\'' + ", ville='" + ville + '\'' +
                ", telephone='" + telephone + '\'' + ", adresseMail='" +
                adresseMail + '\'' + ", commentaires='" + commentaires + '\'';
    }
}
