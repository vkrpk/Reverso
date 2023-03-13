package fr.victork.java.Entity;

import fr.victork.java.Exception.ExceptionEntity;
import fr.victork.java.Tools.ControlString;
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

    public Societe(String raisonSociale, String numeroDeRue, String nomDeRue, String codePostal, String ville,
                   String telephone, String adresseMail, String commentaires) throws ExceptionEntity {
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

    public void setIdentifiant(int identifiant) {
        this.identifiant = identifiant;
    }

    public String getRaisonSociale() {
        return raisonSociale;
    }

    public void setRaisonSociale(String raisonSociale) throws ExceptionEntity {
        if (ControlString.controlString(raisonSociale)) {
            this.raisonSociale = raisonSociale;
        } else {
            throw new ExceptionEntity("La raison sociale doit être une chaîne de caractère non vide.");
        }
    }

    public String getNumeroDeRue() {
        return numeroDeRue;
    }

    public void setNumeroDeRue(String numeroDeRue) throws ExceptionEntity {
        if (ControlString.controlString(numeroDeRue)) {
            this.numeroDeRue = numeroDeRue;
        } else {
            throw new ExceptionEntity("Le numéro de rue doit être une chaîne de caractère non vide.");
        }
    }

    public String getNomDeRue() {
        return nomDeRue;
    }

    public void setNomDeRue(String nomDeRue) throws ExceptionEntity {
        if (ControlString.controlString(nomDeRue)) {
            this.nomDeRue = nomDeRue;
        } else {
            throw new ExceptionEntity("Le nom de rue doit être une chaîne de caractère non vide.");
        }
    }

    public String getCodePostal() {
        return codePostal;
    }

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

    public void setVille(String ville) throws ExceptionEntity {
        if (ControlString.controlString(nomDeRue)) {
            this.ville = ville;
        } else {
            throw new ExceptionEntity("Le nom de rue doit être une chaîne de caractère non vide.");
        }
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) throws ExceptionEntity {
        if (telephone != null && telephone.length() >= 10) {
            this.telephone = telephone;
        } else {
            throw new ExceptionEntity("Le numéro de téléphone n'est pas valide");
        }
    }

    public String getAdresseMail() {
        return adresseMail;
    }

    public void setAdresseMail(String adresseMail) throws ExceptionEntity {
        if (ControlString.controlString(adresseMail) && adresseMail.matches(".+@.+")) {
            this.adresseMail = adresseMail;
        } else {
            throw new ExceptionEntity("L'adresse mail n'est pas valide, elle doit être au format *@*.");
        }
    }

    public String getCommentaires() {
        return commentaires;
    }

    public void setCommentaires(String commentaires) throws ExceptionEntity {
        if (commentaires != null) {
            this.commentaires = commentaires;
        } else {
            throw new ExceptionEntity("Les commentaires doivent être une chaîne de caractère non null.");
        }
    }

    //--------------------- TO STRING METHOD------------------------------------
}
