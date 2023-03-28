/**
 * @author Victor K
 * @version 1.00
 * Cette classe représente un contrat
 */
package fr.victork.java.Entity;

import fr.victork.java.Exception.ExceptionEntity;
import fr.victork.java.Tools.Tools;

/**
 * Cette classe représente un contrat
 */
public class Contrat {
    //--------------------- CONSTANTS ------------------------------------------
    //--------------------- STATIC VARIABLES -----------------------------------
    //--------------------- INSTANCE VARIABLES ---------------------------------
    private Integer identifiantContrat;
    private Integer identifiantClient;
    private String libelle;
    private Double montant;

    //--------------------- CONSTRUCTORS ---------------------------------------

    /**
     * Constructeur par défaut.
     */
    public Contrat() {
    }

    /**
     * Constructeur avec paramètres.
     *
     * @param identifiantContrat l'identifiant du contrat
     * @param identifiantClient  l'identifiant du client associé au contrat
     * @param libelle            le libellé du contrat
     * @param montant            le montant du contrat
     * @throws ExceptionEntity si le libellé est vide ou si le montant est inférieur à 0
     */
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

    /**
     * Getter pour l'identifiant du contrat.
     *
     * @return l'identifiant du contrat
     */
    public Integer getIdentifiantContrat() {
        return identifiantContrat;
    }

    /**
     * Setter pour l'identifiant du contrat.
     *
     * @param identifiantContrat identifiantContrat l'identifiant du contrat
     */
    public void setIdentifiantContrat(Integer identifiantContrat) {
        this.identifiantContrat = identifiantContrat;
    }

    /**
     * Getter pour l'identifiant du client associé au contrat.
     *
     * @return l'identifiant du client associé au contrat
     */
    public Integer getIdentifiantClient() {
        return identifiantClient;
    }

    /**
     * Setter pour l'identifiant du client associé au contrat.
     *
     * @param identifiantClient l'identifiant du client associé au contrat
     */
    public void setIdentifiantClient(Integer identifiantClient) {
        this.identifiantClient = identifiantClient;
    }


    /**
     * Getter pour le libellé du contrat.
     *
     * @return le libellé du contrat
     */
    public String getLibelle() {
        return libelle;
    }

    /**
     * Setter pour le libellé du contrat.
     *
     * @param libelle libelle le libellé du contrat
     * @throws ExceptionEntity si le libellé est vide
     */
    public void setLibelle(String libelle) throws ExceptionEntity {
        if (Tools.controlStringIsNotEmpty(libelle)) {
            this.libelle = libelle;
        } else {
            throw new ExceptionEntity("Le libellé doit être une chaîne de " +
                    "caractère non vide.");
        }
    }

    /**
     * Getter pour le montant du contrat.
     *
     * @return le montant du contrat
     */
    public Double getMontant() {
        return montant;
    }

    /**
     * Setter pour le montant du contrat.
     *
     * @param montant montant le montant du contrat
     * @throws ExceptionEntity si le montant est inférieur à 0
     */
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
