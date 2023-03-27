package fr.victork.java.Entity;

import fr.victork.java.Exception.ExceptionEntity;
import fr.victork.java.Tools.Tools;

public class Contrat {
    //--------------------- CONSTANTS ------------------------------------------
    //--------------------- STATIC VARIABLES -----------------------------------
    //--------------------- INSTANCE VARIABLES ---------------------------------
    private Integer identifiantContrat;
    private Integer identifiantClient;
    private String libelle;
    private Double montant;

    //--------------------- CONSTRUCTORS ---------------------------------------
    public Contrat(Integer identifiantContrat, Integer identifiantClient, String libelle, Double montant)
            throws ExceptionEntity {
        this.setIdentifiantContrat(identifiantContrat);
        this.setIdentifiantClient(identifiantClient);
        this.setLibelle(libelle);
        this.setMontant(montant);
    }
    //--------------------- STATIC METHODS -------------------------------------
    //--------------------- INSTANCE METHODS -----------------------------------
    //--------------------- ABSTRACT METHODS -----------------------------------
    //--------------------- STATIC - GETTERS - SETTERS -------------------------
    //--------------------- GETTERS - SETTERS ----------------------------------

    public Integer getIdentifiantContrat() {
        return identifiantContrat;
    }

    public void setIdentifiantContrat(Integer identifiantContrat) {
        this.identifiantContrat = identifiantContrat;
    }

    public Integer getIdentifiantClient() {
        return identifiantClient;
    }

    public void setIdentifiantClient(Integer identifiantClient) {
        this.identifiantClient = identifiantClient;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) throws ExceptionEntity {
        if (Tools.controlStringIsNotEmpty(libelle)) {
            this.libelle = libelle;
        } else {
            throw new ExceptionEntity("Le libellé doit être une chaîne de " +
                    "caractère non vide.");
        }
    }

    public Double getMontant() {
        return montant;
    }

    public void setMontant(Double montant) throws ExceptionEntity {
        if (montant >= 0) {
            this.montant = montant;
        } else {
            throw new ExceptionEntity(
                    "Le montant doit être supérieur à 200€.");
        }
    }
    //--------------------- TO STRING METHOD------------------------------------
}
