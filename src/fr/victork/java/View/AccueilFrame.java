/**
 * @author Victor K
 * @version 1.00
 * Cette classe a pour but sélectionner un type de société pour ensuite
 * permettre à l'utilisateur de l'application de gérer les données
 */
package fr.victork.java.View;

import fr.victork.java.Entity.*;
import fr.victork.java.Exception.ExceptionEntity;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Cette classe a pour but sélectionner un type de société pour ensuite
 * permettre à l'utilisateur de l'application de gérer les données
 */
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

    /**
     * @param largeurFenetre int Largeur de la fenêtre
     * @param hauteurFenetre int Hauteur de la fenêtre
     * @param positionX      int Position X sur l'écran
     * @param positionY      int Position Y sur l'écran
     * @param pleinEcran     Boolean True si le mode plein écran est activé
     */
    public AccueilFrame(int largeurFenetre, int hauteurFenetre, int positionX,
            int positionY, boolean pleinEcran) {
        super(largeurFenetre, hauteurFenetre, positionX, positionY, pleinEcran);
        setupPanBtnsCRUDAndSetEnabledToFalse();
        styliserComboBoxSociete();
        setupGUI(largeurFenetre, hauteurFenetre, positionX, positionY,
                pleinEcran);

        // Mémorise le type de société à gérer et adapte l'affichage
        btnGererClient.addActionListener(e -> {
            this.enumInstanceDeSociete = EnumInstanceDeSociete.Client;
            setupLabelBtnsCRUD();
            resetComboBoxSocieteAndFillTheList();
            labelTypeSociete.setText(enumInstanceDeSociete.name());
            if (CollectionClients.getCollection().isEmpty()) {
                btnEditer.setEnabled(false);
                btnSupprimer.setEnabled(false);
                btnEditer.setForeground(Color.black);
                btnSupprimer.setForeground(Color.black);
                panBtnEditOrDelete.setVisible(false);
            }
        });

        // Mémorise le type de société à gérer et adapte l'affichage
        btnGererProspect.addActionListener(e -> {
            this.enumInstanceDeSociete = EnumInstanceDeSociete.Prospect;
            setupLabelBtnsCRUD();
            resetComboBoxSocieteAndFillTheList();
            labelTypeSociete.setText(enumInstanceDeSociete.name());
            if (CollectionProspects.getCollection().isEmpty()) {
                btnEditer.setEnabled(false);
                btnSupprimer.setEnabled(false);
                btnEditer.setForeground(Color.black);
                btnSupprimer.setForeground(Color.black);
                panBtnEditOrDelete.setVisible(false);
            }
        });

        // Affiche liste des sociétés du type sélectionné
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

        // Affiche le formulaire de création pour le type de société sélectionné
        btnCreer.addActionListener(e -> {
            this.dispose();
            new FormFrame(this.enumInstanceDeSociete, EnumCRUD.CREATE,
                    super.largeur, super.hauteur, super.x, super.y,
                    super.estEnPleinEcran);
        });

        /* Affiche le formulaire de modification ou de suppression en
        fonction de l'action choisi pour l'instance de Société sélectionnée */
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

        // Mémorise l'action "supprimer" et affiche la liste déroulante
        btnSupprimer.addActionListener(e -> {
            this.enumCRUD = EnumCRUD.DELETE;
            afficheComboBoxSociete();
        });

        // Mémorise l'action "supprimer" et affiche la liste déroulante
        btnEditer.addActionListener(e -> {
            this.enumCRUD = EnumCRUD.UPDATE;
            afficheComboBoxSociete();
        });

        /* Réinitialise la liste déroulante, rempli la liste déroulante,
        mémorise le choix de la société sélectionnée et modifie le texte du
        bouton en fonction de l'action du CRUD sélectionnée */
        comboBoxSociete.addActionListener(e -> {
            resetComboBoxSocieteAndFillTheList();
            societeSelection = (Societe) comboBoxSociete.getSelectedItem();
            if (enumCRUD != null && societeSelection != null) {
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

    /**
     * Cette méthode instancie une classe JComboBox et lui applique un style
     * particulier
     */
    private void styliserComboBoxSociete() {
        this.comboBoxSociete = new JComboBox();
        comboBoxSociete.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
        comboBoxSociete.setForeground(Color.BLACK);
        comboBoxSociete.setBackground(new Color(180, 180, 180));
        comboBoxSociete.setCursor(
                Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        comboBoxSociete.setVisible(false);
    }

    /**
     * Cette méthode instancie les boutons du CRUD et les insèrent dans une
     * ArrayList pour leur appliquer une stratégie de positionnement
     */
    void setupPanBtnsCRUDAndSetEnabledToFalse() {
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

    /**
     * Cette méthode réinitialise le contenu de la liste déroulante
     * Cette méthode vérifie en amont si une collection n'est pas vide pour ne
     * pas instancier une liste vide
     * Cette méthode ajoute toutes les sociétés du type sélectionnée à la
     * liste déroulante
     * Cette méthode imprime une erreur si une exception est attrapée
     */
    private void resetComboBoxSocieteAndFillTheList() {
        try {
            comboBoxSociete.removeAllItems();
            switch (enumInstanceDeSociete) {
                case Client:
                    if (CollectionClients.getCollection().isEmpty()) {
                        btnEditer.setEnabled(false);
                        btnSupprimer.setEnabled(false);
                        System.out.println("test");
                        return;
                    }
                    for (Client collectionItem :
                            CollectionClients.getCollection()) {
                        comboBoxSociete.addItem(collectionItem);
                    }
                    comboBoxSociete.setSelectedIndex(0);
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
                    comboBoxSociete.setSelectedIndex(0);
                    break;
            }
        } catch (Exception e) {
            System.out.println("messages : " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Cette méthode active ou désactive les boutons du CRUD
     *
     * @param isEnabled Boolean états des boutons
     */
    private void enabledBtnsCRUD(boolean isEnabled) {
        btnAfficher.setEnabled(isEnabled);
        btnCreer.setEnabled(isEnabled);
        btnEditer.setEnabled(isEnabled);
        btnSupprimer.setEnabled(isEnabled);
    }

    /**
     * Cette méthode rend clickable les boutons du CRUD et modifie le texte des
     * boutons en fonction du type de société sélectionnée
     */
    private void setupLabelBtnsCRUD() {
        String nomDuGroupe = enumInstanceDeSociete.name().toLowerCase();
        btnCreer.setText("Créer un " + nomDuGroupe);
        btnAfficher.setText("Afficher les " + nomDuGroupe + "s");
        btnSupprimer.setText("Supprimer un " + nomDuGroupe);
        btnEditer.setText("Modifier un " + nomDuGroupe);
        enabledBtnsCRUD(true);
    }

    /**
     * Cette méthode rend visible la liste déroulante et le bouton "valider"
     * Cette méthode change la couleur de fond en vert du bouton de l'action
     * sélectionnée
     * Cette méthode change le texte des boutons supprimer et modifier en
     * ajoutant la raison sociale de la société sélectionnée
     */
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

    /**
     * Cette méthode définit le positionnement et les dimensions de la fenêtre.
     * Cette méthode définit la disposition de la fenêtre.
     *
     * @param largeurFenetre int Largeur de la fenêtre
     * @param hauteurFenetre int Hauteur de la fenêtre
     * @param positionX      int Position X sur l'écran
     * @param positionY      int Position Y sur l'écran
     * @param pleinEcran     Boolean True si le mode plein écran est activé
     */
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
