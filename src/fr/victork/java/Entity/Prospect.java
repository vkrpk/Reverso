package fr.victork.java.Entity;

import fr.victork.java.Exception.ExceptionEntity;

import java.time.LocalDate;

public class Prospect extends Societe {
    //--------------------- CONSTANTS ------------------------------------------
    //--------------------- STATIC VARIABLES -----------------------------------
    private static int compteurIdentifiant = 0;
    //--------------------- INSTANCE VARIABLES ---------------------------------
    private LocalDate dateProsprection;
    private String prospectInteresse;

    //--------------------- CONSTRUCTORS ---------------------------------------
    public Prospect(String raisonSociale, String numeroDeRue, String nomDeRue, String codePostal, String ville,
                    String telephone, String adresseMail, String commentaires, LocalDate dateProsprection,
                    String prospectInteresse) throws ExceptionEntity {
        super(raisonSociale, numeroDeRue, nomDeRue, codePostal, ville, telephone, adresseMail, commentaires);
        compteurIdentifiant++;
        this.setDateProsprection(dateProsprection);
        this.setProspectInteresse(prospectInteresse);
        super.setIdentifiant(compteurIdentifiant);
        CollectionProspects.getCollection().add(this);
    }
    //--------------------- STATIC METHODS -------------------------------------
    //--------------------- INSTANCE METHODS -----------------------------------
    //--------------------- ABSTRACT METHODS -----------------------------------
    //--------------------- STATIC - GETTERS - SETTERS -------------------------
    //--------------------- GETTERS - SETTERS ----------------------------------

    public LocalDate getDateProsprection() {
        return dateProsprection;
    }

    public void setDateProsprection(LocalDate dateProsprection) {
        this.dateProsprection = dateProsprection;
    }

    public String getProspectInteresse() {
        return prospectInteresse;
    }

    public void setProspectInteresse(String prospectInteresse) throws ExceptionEntity {
        if (prospectInteresse == "Oui" || prospectInteresse == "Non") {
            this.prospectInteresse = prospectInteresse;
        } else {
            throw new ExceptionEntity("Prospect Intéressé peut être soit Oui soit Non.");
        }
    }

    //--------------------- TO STRING METHOD------------------------------------
    public String toString() {
        return getRaisonSociale();
    }
}
