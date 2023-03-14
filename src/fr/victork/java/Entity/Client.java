package fr.victork.java.Entity;

import fr.victork.java.Exception.ExceptionEntity;

public class Client extends Societe {
    //--------------------- CONSTANTS ------------------------------------------
    //--------------------- STATIC VARIABLES -----------------------------------
    private static int compteurIdentifiant = 0;
    //--------------------- INSTANCE VARIABLES ---------------------------------
    private Double chiffreAffaires;
    private int nombreEmployes;

    //--------------------- CONSTRUCTORS ---------------------------------------
    public Client(String raisonSociale, String numeroDeRue, String nomDeRue,
                  String codePostal, String ville, String telephone,
                  String adresseMail, String commentaires,
                  Double chiffreAffaires, int nombreEmployes)
            throws ExceptionEntity {
        super(raisonSociale, numeroDeRue, nomDeRue, codePostal, ville,
                telephone, adresseMail, commentaires);
        compteurIdentifiant++;
        this.setChiffreAffaires(chiffreAffaires);
        this.setNombreEmployes(nombreEmployes);
        super.setIdentifiant(compteurIdentifiant);
        CollectionClients.getCollection().add(this);

    }
    //--------------------- STATIC METHODS -------------------------------------
    //--------------------- INSTANCE METHODS -----------------------------------
    //--------------------- ABSTRACT METHODS -----------------------------------
    //--------------------- STATIC - GETTERS - SETTERS -------------------------
    //--------------------- GETTERS - SETTERS ----------------------------------

    public Double getChiffreAffaires() {
        return chiffreAffaires;
    }

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

    public void setNombreEmployes(int nombreEmployes) throws ExceptionEntity {
        if (nombreEmployes > 0) {
            this.nombreEmployes = nombreEmployes;
        } else {
            throw new ExceptionEntity(
                    "Le nombre d'employés doit" + " être supérieur à 0.");
        }
    }

    //--------------------- TO STRING METHOD------------------------------------
    public String toString() {
        return getRaisonSociale();
    }
}
