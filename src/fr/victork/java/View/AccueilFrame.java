package fr.victork.java.View;

import fr.victork.java.Entity.*;
import fr.victork.java.Exception.ExceptionEntity;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class AccueilFrame extends MainFrame {
    //--------------------- CONSTANTS ------------------------------------------
    //--------------------- STATIC VARIABLES -----------------------------------
    //--------------------- INSTANCE VARIABLES ---------------------------------
    private JButton btnCreer, btnAfficher, btnSupprimer, btnEditer, btnSelectClient, btnSelectProspect, btnEditOrDelete;
    private JComboBox listSociete;
    private EnumInstanceDeSociete groupeSelectionne;
    private EnumCRUD enumCRUD;
    private Societe societeSelectionne;
    private JPanel panBtnEditOrDelete, panBtnsCrud, panSelectGroupe, panLabelGroupeSelectionne;
    private ArrayList<JButton> buttonList;
    private JLabel labelGroupeSelectionne;

    //--------------------- CONSTRUCTORS ---------------------------------------
    public AccueilFrame(int largeurFenetre, int hauteurFenetre, int positionX, int positionY, boolean pleinEcran) {
        super(largeurFenetre, hauteurFenetre, positionX, positionY, pleinEcran);
        creerPanelChoixSociete();
        creerPanelBoutonsCRUD();
        setupGUI();

        btnAfficher.addActionListener(e -> {
            try {
                this.dispose();
                AffichageFrame affichageFrame = new AffichageFrame(groupeSelectionne, super.largeur, super.hauteur,
                        super.x, super.y, super.estEnPleinEcran);
                affichageFrame.updateEnumInstanceDeSociete(groupeSelectionne);
            } catch (ExceptionEntity ex) {
                throw new RuntimeException(ex);
            }
        });

        btnCreer.addActionListener(e -> {
            this.dispose();
            new FormFrame(this.groupeSelectionne, EnumCRUD.CREATE, super.largeur, super.hauteur,
                    super.x, super.y, super.estEnPleinEcran);
        });

        btnEditOrDelete.addActionListener(e -> {
            try {
                this.dispose();
                new FormFrame(this.societeSelectionne, this.enumCRUD, super.largeur, super.hauteur,
                        super.x, super.y, super.estEnPleinEcran);
            } catch (ExceptionEntity ex) {
                throw new RuntimeException(ex);
            }
        });

        btnSupprimer.addActionListener(e -> {
            this.enumCRUD = EnumCRUD.DELETE;
            activerListe();
            btnSupprimer.setBackground(Color.green);
            btnEditer.setBackground(null);
            btnSupprimer.setForeground(Color.WHITE);
            btnEditer.setForeground(Color.BLACK);
        });

        btnEditer.addActionListener(e -> {
            this.enumCRUD = EnumCRUD.UPDATE;
            activerListe();

        });

        this.listSociete = new JComboBox();
        listSociete.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
        listSociete.setForeground(Color.BLACK);
        listSociete.setBackground(new Color(180, 180, 180));
        listSociete.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        listSociete.setVisible(false);
        panBtnEditOrDelete.add(listSociete);
        panBtnEditOrDelete.add(btnEditOrDelete);

        listSociete.addActionListener(e -> {
                    switch (groupeSelectionne) {
                        case Client:
                            societeSelectionne = (Societe) listSociete.getSelectedItem();

                            break;
                        case Prospect:
                            societeSelectionne = (Societe) listSociete.getSelectedItem();
                            break;
                    }
                    if (enumCRUD != null) {
                        switch (enumCRUD) {
                            case DELETE:
                                this.btnEditOrDelete.setText("Supprimer : " + societeSelectionne.getRaisonSociale());
                                break;
                            case UPDATE:
                                this.btnEditOrDelete.setText("Modifier : " + societeSelectionne.getRaisonSociale());
                                break;
                        }
                    }
                }
        );
        setSize(largeurFenetre, hauteurFenetre);
        if (positionX == -1 && positionY == -1) {
            setLocationRelativeTo(null);
        } else {
            setLocation(positionX, positionY);
        }
        super.estEnPleinEcran = pleinEcran;
        if (super.estEnPleinEcran) {
            setExtendedState(JFrame.MAXIMIZED_BOTH);
        }
        setVisible(true);
    }

    //--------------------- STATIC METHODS -------------------------------------
    //--------------------- INSTANCE METHODS -----------------------------------

    private void creerPanelBoutonsCRUD() {
        panBtnsCrud = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnCreer = createButton("Créer");
        btnAfficher = createButton("Afficher");
        btnSupprimer = createButton("Supprimer");
        btnEditer = createButton("Editer");

        this.buttonList = new ArrayList<>();

        this.buttonList.add(btnAfficher);
        this.buttonList.add(btnCreer);
        this.buttonList.add(btnEditer);
        this.buttonList.add(btnSupprimer);

        for (JButton button : buttonList) {
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            buttonPanel.add(button);
            panBtnsCrud.add(buttonPanel);
        }
        etatsBoutons(false);
    }

    private void btnSelectClientListListener(ActionEvent actionEvent) {
        this.groupeSelectionne = EnumInstanceDeSociete.Client;
        modifierLeLabelDesBoutonsDuCRUD();
        updateListe();
        labelGroupeSelectionne.setText(groupeSelectionne.name());
    }

    private void btnSelectProspectListListener(ActionEvent actionEvent) {
        this.groupeSelectionne = EnumInstanceDeSociete.Prospect;
        modifierLeLabelDesBoutonsDuCRUD();
        updateListe();
        labelGroupeSelectionne.setText(groupeSelectionne.name());
    }

    private void creerPanelChoixSociete() {
        panSelectGroupe = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnSelectClient = createButton("Gérer les clients");
        btnSelectProspect = createButton("Gérer les prospects");
        btnSelectClient.addActionListener(this::btnSelectClientListListener);
        btnSelectProspect.addActionListener(this::btnSelectProspectListListener);
        panSelectGroupe.add(btnSelectClient);
        panSelectGroupe.add(btnSelectProspect);
    }

    private void updateListe() {
        try {
            listSociete.removeAllItems();
            switch (groupeSelectionne) {
                case Client:
                    if (CollectionClients.getCollection().isEmpty()) {
                        btnEditer.setEnabled(false);
                        btnSupprimer.setEnabled(false);
                    }
                    for (Client collectionItem : CollectionClients.getCollection()
                    ) {
                        listSociete.addItem(collectionItem);
                    }
                    break;
                case Prospect:
                    if (CollectionProspects.getCollection().isEmpty()) {
                        btnEditer.setEnabled(false);
                        btnSupprimer.setEnabled(false);
                    }
                    for (Prospect collectionItem : CollectionProspects.getCollection()
                    ) {
                        listSociete.addItem(collectionItem);
                    }
                    break;
            }
        } catch (Exception e) {
            System.out.println("messages : " + e.getMessage());
            e.printStackTrace();
        }
    }


    private void etatsBoutons(boolean etat) {
        btnAfficher.setEnabled(etat);
        btnCreer.setEnabled(etat);
        btnEditer.setEnabled(etat);
        btnSupprimer.setEnabled(etat);
    }

    private void modifierLeLabelDesBoutonsDuCRUD() {
        String nomDuGroupe = groupeSelectionne.name().toLowerCase();
        btnCreer.setText("Créer un " + nomDuGroupe);
        btnAfficher.setText("Afficher les " + nomDuGroupe + "s");
        btnSupprimer.setText("Supprimer un " + nomDuGroupe);
        btnEditer.setText("Modifier un " + nomDuGroupe);
        etatsBoutons(true);
    }

    private void activerListe() {
        listSociete.setVisible(true);
        panBtnEditOrDelete.setVisible(true);
        switch (enumCRUD) {
            case DELETE:
                this.btnEditOrDelete.setText("Supprimer : " + societeSelectionne.getRaisonSociale());
                btnSupprimer.setBackground(null);
                btnEditer.setBackground(Color.green);
                btnEditer.setForeground(Color.WHITE);
                btnSupprimer.setForeground(Color.BLACK);
                break;
            case UPDATE:
                this.btnEditOrDelete.setText("Modifer : " + societeSelectionne.getRaisonSociale());
                btnSupprimer.setBackground(null);
                btnEditer.setBackground(Color.green);
                btnEditer.setForeground(Color.WHITE);
                btnSupprimer.setForeground(Color.BLACK);
                break;
        }
    }

    private void setupGUI() {
        setTitle("Accueil");
        super.panCentral.setLayout(new BoxLayout(super.panCentral, BoxLayout.Y_AXIS));

        this.btnEditOrDelete = createButton("");
        this.panBtnEditOrDelete = new JPanel(new FlowLayout(FlowLayout.CENTER));


        super.panCentral.add(panSelectGroupe);
        labelGroupeSelectionne = createLabel("");
        labelGroupeSelectionne.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 24));
        panLabelGroupeSelectionne = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panLabelGroupeSelectionne.add(labelGroupeSelectionne);
        super.panCentral.add(panLabelGroupeSelectionne);

        super.panCentral.add(panBtnsCrud);

        panBtnEditOrDelete.setVisible(false);

        super.panCentral.add(panBtnEditOrDelete);
    }
    //--------------------- ABSTRACT METHODS -----------------------------------
    //--------------------- STATIC - GETTERS - SETTERS -------------------------
    //--------------------- GETTERS - SETTERS ----------------------------------
    //--------------------- TO STRING METHOD------------------------------------
}
