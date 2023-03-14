package fr.victork.java.View;

import fr.victork.java.Entity.*;
import fr.victork.java.Exception.ExceptionEntity;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class AccueilFrame extends MainFrame {
    //--------------------- CONSTANTS ------------------------------------------
    //--------------------- STATIC VARIABLES -----------------------------------
    //--------------------- INSTANCE VARIABLES ---------------------------------
    private JButton btnCreer, btnAfficher, btnSupprimer, btnEditer,
            btnGererClient, btnGererProspect, btnValiderSupprimerOuEditer;
    private JComboBox comboBoxSociete;
    private EnumInstanceDeSociete enumInstanceDeSociete;
    private EnumCRUD enumCRUD;
    private Societe societeSelection;
    private JPanel panBtnEditOrDelete, panBtnsCRUD, panChoisirTypeSociete,
            panLabelTypeSociete;
    private ArrayList<JButton> listBtnsCRUD;
    private JLabel labelTypeSociete;
    private Color colorGreenCustom;

    //--------------------- CONSTRUCTORS ---------------------------------------
    public AccueilFrame(int largeurFenetre, int hauteurFenetre, int positionX,
                        int positionY, boolean pleinEcran) {
        super(largeurFenetre, hauteurFenetre, positionX, positionY, pleinEcran);
        setupPanBtnsCRUDAndSetEnabledToFalse();
        styliserComboBoxSociete();
        setupGUI(largeurFenetre, hauteurFenetre, positionX, positionY,
                pleinEcran);

        btnGererClient.addActionListener(e -> {
            this.enumInstanceDeSociete = EnumInstanceDeSociete.Client;
            setupLabelBtnsCRUD();
            resetComboBoxSocieteAndFillTheList();
            labelTypeSociete.setText(enumInstanceDeSociete.name());
        });

        btnGererProspect.addActionListener(e -> {
            this.enumInstanceDeSociete = EnumInstanceDeSociete.Prospect;
            setupLabelBtnsCRUD();
            resetComboBoxSocieteAndFillTheList();
            labelTypeSociete.setText(enumInstanceDeSociete.name());
        });


        btnAfficher.addActionListener(e -> {
            try {
                this.dispose();
                AffichageFrame affichageFrame =
                        new AffichageFrame(enumInstanceDeSociete, super.largeur,
                                super.hauteur, super.x, super.y,
                                super.estEnPleinEcran);
                affichageFrame.updateEnumInstanceDeSociete(
                        enumInstanceDeSociete);
            } catch (ExceptionEntity ex) {
                throw new RuntimeException(ex);
            }
        });

        btnCreer.addActionListener(e -> {
            this.dispose();
            new FormFrame(this.enumInstanceDeSociete, EnumCRUD.CREATE,
                    super.largeur, super.hauteur, super.x, super.y,
                    super.estEnPleinEcran);
        });

        btnValiderSupprimerOuEditer.addActionListener(e -> {
            try {
                this.dispose();
                new FormFrame(this.societeSelection, this.enumCRUD,
                        super.largeur, super.hauteur, super.x, super.y,
                        super.estEnPleinEcran);
            } catch (ExceptionEntity ex) {
                throw new RuntimeException(ex);
            }
        });

        btnSupprimer.addActionListener(e -> {
            this.enumCRUD = EnumCRUD.DELETE;
            afficheComboBoxSociete();
        });

        btnEditer.addActionListener(e -> {
            this.enumCRUD = EnumCRUD.UPDATE;
            afficheComboBoxSociete();
        });

        comboBoxSociete.addActionListener(e -> {
            resetComboBoxSocieteAndFillTheList();
            societeSelection = (Societe) comboBoxSociete.getSelectedItem();
            if (enumCRUD != null) {
                switch (enumCRUD) {
                    case DELETE:
                        this.btnValiderSupprimerOuEditer.setText(
                                "Supprimer " + ":" + " " +
                                        societeSelection.getRaisonSociale());
                        break;
                    case UPDATE:
                        this.btnValiderSupprimerOuEditer.setText("Modifier : " +
                                societeSelection.getRaisonSociale());
                        break;
                }
            }
        });
    }

    //--------------------- STATIC METHODS -------------------------------------
    //--------------------- INSTANCE METHODS -----------------------------------
    private void styliserComboBoxSociete() {
        this.comboBoxSociete = new JComboBox();
        comboBoxSociete.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
        comboBoxSociete.setForeground(Color.BLACK);
        comboBoxSociete.setBackground(new Color(180, 180, 180));
        comboBoxSociete.setCursor(
                Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        comboBoxSociete.setVisible(false);
    }

    private void setupPanBtnsCRUDAndSetEnabledToFalse() {
        panBtnsCRUD = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnCreer = createButton("Créer");
        btnAfficher = createButton("Afficher");
        btnSupprimer = createButton("Supprimer");
        btnEditer = createButton("Editer");

        this.listBtnsCRUD = new ArrayList<>();

        this.listBtnsCRUD.add(btnAfficher);
        this.listBtnsCRUD.add(btnCreer);
        this.listBtnsCRUD.add(btnEditer);
        this.listBtnsCRUD.add(btnSupprimer);

        for (JButton button : listBtnsCRUD) {
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            buttonPanel.add(button);
            panBtnsCRUD.add(buttonPanel);
        }
        enabledBtnsCRUD(false);
    }

    private void resetComboBoxSocieteAndFillTheList() {
        try {
            comboBoxSociete.removeAllItems();
            switch (enumInstanceDeSociete) {
                case Client:
                    if (CollectionClients.getCollection().isEmpty()) {
                        btnEditer.setEnabled(false);
                        btnSupprimer.setEnabled(false);
                        return;
                    }
                    for (Client collectionItem :
                            CollectionClients.getCollection()) {
                        comboBoxSociete.addItem(collectionItem);
                    }
                    comboBoxSociete.setSelectedIndex(1);
                    break;
                case Prospect:
                    if (CollectionProspects.getCollection().isEmpty()) {
                        btnEditer.setEnabled(false);
                        btnSupprimer.setEnabled(false);
                        return;
                    }
                    for (Prospect collectionItem :
                            CollectionProspects.getCollection()) {
                        comboBoxSociete.addItem(collectionItem);
                    }
                    comboBoxSociete.setSelectedIndex(1);
                    break;
            }
        } catch (Exception e) {
            System.out.println("messages : " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void enabledBtnsCRUD(boolean isEnabled) {
        btnAfficher.setEnabled(isEnabled);
        btnCreer.setEnabled(isEnabled);
        btnEditer.setEnabled(isEnabled);
        btnSupprimer.setEnabled(isEnabled);
    }

    private void setupLabelBtnsCRUD() {
        String nomDuGroupe = enumInstanceDeSociete.name().toLowerCase();
        btnCreer.setText("Créer un " + nomDuGroupe);
        btnAfficher.setText("Afficher les " + nomDuGroupe + "s");
        btnSupprimer.setText("Supprimer un " + nomDuGroupe);
        btnEditer.setText("Modifier un " + nomDuGroupe);
        enabledBtnsCRUD(true);
    }

    private void afficheComboBoxSociete() {
        comboBoxSociete.setVisible(true);
        panBtnEditOrDelete.setVisible(true);
        switch (enumCRUD) {
            case DELETE:
                this.btnValiderSupprimerOuEditer.setText(
                        "Supprimer : " + societeSelection.getRaisonSociale());
                btnSupprimer.setBackground(colorGreenCustom);
                btnEditer.setBackground(null);
                btnEditer.setForeground(Color.BLACK);
                btnSupprimer.setForeground(Color.WHITE);
                break;
            case UPDATE:
                this.btnValiderSupprimerOuEditer.setText(
                        "Modifer : " + societeSelection.getRaisonSociale());
                btnSupprimer.setBackground(null);
                btnEditer.setBackground(colorGreenCustom);
                btnEditer.setForeground(Color.WHITE);
                btnSupprimer.setForeground(Color.BLACK);
                break;
        }
    }

    private void setupGUI(int largeurFenetre, int hauteurFenetre, int positionX,
                          int positionY, boolean pleinEcran) {
        setTitle("Accueil");
        colorGreenCustom = new Color(87, 150, 92);
        super.panCentral.setLayout(
                new BoxLayout(super.panCentral, BoxLayout.Y_AXIS));

        this.btnValiderSupprimerOuEditer = createButton("");
        this.panBtnEditOrDelete = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panBtnEditOrDelete.add(comboBoxSociete);
        panBtnEditOrDelete.add(btnValiderSupprimerOuEditer);

        panChoisirTypeSociete = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnGererClient = createButton("Gérer les clients");
        btnGererProspect = createButton("Gérer les prospects");

        panChoisirTypeSociete.add(btnGererClient);
        panChoisirTypeSociete.add(btnGererProspect);
        super.panCentral.add(panChoisirTypeSociete);
        labelTypeSociete = createLabel("");
        labelTypeSociete.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 24));
        panLabelTypeSociete = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panLabelTypeSociete.add(labelTypeSociete);
        super.panCentral.add(panLabelTypeSociete);

        super.panCentral.add(panBtnsCRUD);

        panBtnEditOrDelete.setVisible(false);

        super.panCentral.add(panBtnEditOrDelete);


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
    //--------------------- ABSTRACT METHODS -----------------------------------
    //--------------------- STATIC - GETTERS - SETTERS -------------------------
    //--------------------- GETTERS - SETTERS ----------------------------------
    //--------------------- TO STRING METHOD------------------------------------
}
