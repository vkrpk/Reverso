/**
 * @author Victor K
 * @version 1.00
 * Cette classe a pour but sélectionner un type de société pour ensuite
 * permettre à l'utilisateur de l'application de gérer les données
 */
package fr.victork.View;

import fr.victork.DAO.AbstractDAOFactory;
import fr.victork.DAO.mongoDB.MongoDBDAOFactory;
import fr.victork.DAO.mysql.MySQLClientDAO;
import fr.victork.DAO.mysql.MySQLDAOFactory;
import fr.victork.DAO.mysql.MySQLProspectDAO;
import fr.victork.Entity.*;
import fr.victork.Exception.ExceptionDAO;
import fr.victork.Exception.ExceptionEntity;

import javax.swing.*;
import java.awt.*;
import java.time.DateTimeException;
import java.util.ArrayList;
import java.util.logging.Level;

import static fr.victork.Log.LoggerReverso.LOGGER;

/**
 * Cette classe a pour but sélectionner un type de société pour ensuite
 * permettre à l'utilisateur de l'application de gérer les données
 */
public class AccueilFrame extends MainFrame {
    //--------------------- CONSTANTS ------------------------------------------
    //--------------------- STATIC VARIABLES -----------------------------------
    //--------------------- INSTANCE VARIABLES ---------------------------------
    private JButton btnCreer, btnAfficher, btnSupprimer, btnEditer, btnGererClient,
            btnGererProspect, btnValiderSupprimerOuEditer, btnAfficherContratByClient, btnSelectMySQL, btnSelectMongoDB;
    private JComboBox comboBoxSociete;
    private EnumInstanceDeSociete enumInstanceDeSociete;
    private EnumCRUD enumCRUD;
    private Societe societeSelection;
    private JPanel panBtnEditOrDelete, panBtnsCRUD, panChoisirTypeSociete,
            panLabelTypeSociete, panChoisirBDD;
    private ArrayList<JButton> listBtnsCRUD;
    private JLabel labelTypeSociete;
    private Color colorGreenCustom;
    private MainFrame mainFrame;

    //--------------------- CONSTRUCTORS ---------------------------------------

    /**
     * @param largeurFenetre int Largeur de la fenêtre
     * @param hauteurFenetre int Hauteur de la fenêtre
     * @param positionX      int Position X sur l'écran
     * @param positionY      int Position Y sur l'écran
     * @param pleinEcran     Boolean True si le mode plein écran est activé
     */
    public AccueilFrame(int largeurFenetre, int hauteurFenetre, int positionX,
                        int positionY, boolean pleinEcran, MainFrame mainFrame) {
        super(largeurFenetre, hauteurFenetre, positionX, positionY, pleinEcran);
        setupPanBtnsCRUDAndSetEnabledToFalse();
        styliserComboBoxSociete();
        setupGUI(largeurFenetre, hauteurFenetre, positionX, positionY,
                pleinEcran);
        this.mainFrame = mainFrame;

        // Mémorise le type de société à gérer et adapte l'affichage
        btnGererClient.addActionListener(e -> {
            panBtnEditOrDelete.setVisible(false);
            btnAfficherContratByClient.setVisible(true);
            this.enumCRUD = null;
            this.enumInstanceDeSociete = EnumInstanceDeSociete.Client;
            setupLabelBtnsCRUD();
            btnAfficherContratByClient.setEnabled(true);
            resetComboBoxSocieteAndFillTheList();
            labelTypeSociete.setText(enumInstanceDeSociete.name());
            try {
                if (mainFrame.clientDAO.findAll().isEmpty()) {
                    btnEditer.setEnabled(false);
                    btnSupprimer.setEnabled(false);
                    btnEditer.setForeground(Color.black);
                    btnSupprimer.setForeground(Color.black);
                    panBtnEditOrDelete.setVisible(false);
                }
            } catch (DateTimeException dte) {
                JOptionPane.showMessageDialog(this,
                        "La date doit être dans le format suivant : dd/MM/yyyy",
                        "Erreur de saisie", JOptionPane.ERROR_MESSAGE);
                LOGGER.log(Level.WARNING, dte.getMessage());
            } catch (NumberFormatException nft) {
                JOptionPane.showMessageDialog(this,
                        "La valeur saisie doit être uniquement composé de chiffres", "Erreur de saisie",
                        JOptionPane.ERROR_MESSAGE);
                LOGGER.log(Level.WARNING, nft.getMessage());
            } catch (ExceptionEntity ee) {
                JOptionPane.showMessageDialog(this, ee.getMessage(),
                        "Erreur de saisie", JOptionPane.ERROR_MESSAGE);
                LOGGER.log(Level.WARNING, ee.getMessage());
            } catch (ExceptionDAO exceptionDAO) {
                switch (exceptionDAO.getGravite()) {
                    case 5:
                        exceptionDAO.printStackTrace();
                        JOptionPane.showMessageDialog(this, "Erreur dans l'application, l'application doit fermer", "Erreur dans l'application",
                                JOptionPane.ERROR_MESSAGE);
                        LOGGER.log(Level.SEVERE, exceptionDAO.getMessage());
                        System.exit(1);
                        break;
                }
            } catch (Exception exception) {
                exception.printStackTrace();
                JOptionPane.showMessageDialog(this, "Erreur dans l'application, l'application doit fermer", "Erreur dans " +
                                "l'application",
                        JOptionPane.ERROR_MESSAGE);
                LOGGER.log(Level.SEVERE, exception.getMessage());
                System.exit(1);
            }
        });

        // Mémorise le type de société à gérer et adapte l'affichage
        btnGererProspect.addActionListener(e -> {
            panBtnEditOrDelete.setVisible(false);
            btnAfficherContratByClient.setVisible(false);
            this.enumCRUD = null;
            this.enumInstanceDeSociete = EnumInstanceDeSociete.Prospect;
            setupLabelBtnsCRUD();
            btnAfficherContratByClient.setEnabled(false);
            resetComboBoxSocieteAndFillTheList();
            labelTypeSociete.setText(enumInstanceDeSociete.name());
            try {
                if (mainFrame.prospectDAO.findAll().isEmpty()) {
                    btnEditer.setEnabled(false);
                    btnSupprimer.setEnabled(false);
                    btnEditer.setForeground(Color.black);
                    btnSupprimer.setForeground(Color.black);
                    panBtnEditOrDelete.setVisible(false);
                }
            } catch (DateTimeException dte) {
                JOptionPane.showMessageDialog(this,
                        "La date doit être dans le format suivant : dd/MM/yyyy",
                        "Erreur de saisie", JOptionPane.ERROR_MESSAGE);
                LOGGER.log(Level.WARNING, dte.getMessage());
            } catch (NumberFormatException nft) {
                JOptionPane.showMessageDialog(this,
                        "La valeur saisie doit être uniquement composé de chiffres", "Erreur de saisie",
                        JOptionPane.ERROR_MESSAGE);
                LOGGER.log(Level.WARNING, nft.getMessage());
            } catch (ExceptionEntity ee) {
                JOptionPane.showMessageDialog(this, ee.getMessage(),
                        "Erreur de saisie", JOptionPane.ERROR_MESSAGE);
                LOGGER.log(Level.WARNING, ee.getMessage());
            } catch (ExceptionDAO exceptionDAO) {
                switch (exceptionDAO.getGravite()) {
                    case 5:
                        exceptionDAO.printStackTrace();
                        JOptionPane.showMessageDialog(this, "Erreur dans l'application, l'application doit fermer", "Erreur dans l'application",
                                JOptionPane.ERROR_MESSAGE);
                        LOGGER.log(Level.SEVERE, exceptionDAO.getMessage());
                        System.exit(1);
                        break;
                }
            } catch (Exception exception) {
                exception.printStackTrace();
                JOptionPane.showMessageDialog(this, "Erreur dans l'application, l'application doit fermer", "Erreur dans " +
                                "l'application",
                        JOptionPane.ERROR_MESSAGE);
                LOGGER.log(Level.SEVERE, exception.getMessage());
                System.exit(1);
            }
        });

        // Affiche liste des sociétés du type sélectionné
        btnAfficher.addActionListener(e -> {
            this.dispose();
            AffichageFrame affichageFrame =
                    new AffichageFrame(enumInstanceDeSociete, super.largeur,
                            super.hauteur, super.x, super.y,
                            super.estEnPleinEcran, this.mainFrame);
            affichageFrame.updateEnumInstanceDeSociete(
                    enumInstanceDeSociete);
        });

        // Affiche le formulaire de création pour le type de société sélectionné
        btnCreer.addActionListener(e -> {
            this.dispose();
            new FormFrame(this.enumInstanceDeSociete, EnumCRUD.CREATE,
                    super.largeur, super.hauteur, super.x, super.y,
                    super.estEnPleinEcran, mainFrame);
        });

        /* Affiche le formulaire de modification ou de suppression en
        fonction de l'action choisi pour l'instance de Société sélectionnée */
        btnValiderSupprimerOuEditer.addActionListener(e -> {
            this.dispose();
            if (this.enumCRUD == EnumCRUD.READ_CONTRAT) {
                new AffichageContratFrame((Client) societeSelection,
                        super.largeur, super.hauteur, super.x, super.y,
                        super.estEnPleinEcran, mainFrame);
            } else {
                new FormFrame(this.societeSelection, this.enumCRUD,
                        super.largeur, super.hauteur, super.x, super.y,
                        super.estEnPleinEcran, mainFrame);
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

        btnAfficherContratByClient.addActionListener(e -> {
            this.enumCRUD = EnumCRUD.READ_CONTRAT;
            afficheComboBoxSociete();
        });

        /* Réinitialise la liste déroulante, rempli la liste déroulante,
        mémorise le choix de la société sélectionnée et modifie le texte du
        bouton en fonction de l'action du CRUD sélectionnée */
        comboBoxSociete.addActionListener(e -> {
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
                    case READ_CONTRAT:
                        this.btnValiderSupprimerOuEditer.setText("Afficher les contrats du client : " +
                                societeSelection.getRaisonSociale());
                        break;
                }
            }
        });

        btnSelectMySQL.addActionListener(e -> {
            mainFrame.abstractDAOFactory = AbstractDAOFactory.getFactory(MySQLDAOFactory.class);
            mainFrame.clientDAO = mainFrame.abstractDAOFactory.getClientDAO();
            mainFrame.prospectDAO = mainFrame.abstractDAOFactory.getProspectDAO();
            panChoisirBDD.setVisible(false);
            panChoisirTypeSociete.setVisible(true);
            panBtnsCRUD.setVisible(true);
            super.panNorth.setVisible(true);
        });

        btnSelectMongoDB.addActionListener(e -> {
            mainFrame.abstractDAOFactory = AbstractDAOFactory.getFactory(MongoDBDAOFactory.class);
            mainFrame.clientDAO = mainFrame.abstractDAOFactory.getClientDAO();
            mainFrame.prospectDAO = mainFrame.abstractDAOFactory.getProspectDAO();
            panChoisirBDD.setVisible(false);
            panChoisirTypeSociete.setVisible(true);
            panBtnsCRUD.setVisible(true);
            super.panNorth.setVisible(true);
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
        btnAfficherContratByClient = createButton("Afficher les contrats");

        this.listBtnsCRUD = new ArrayList<>();

        this.listBtnsCRUD.add(btnAfficher);
        this.listBtnsCRUD.add(btnCreer);
        this.listBtnsCRUD.add(btnEditer);
        this.listBtnsCRUD.add(btnSupprimer);
        this.listBtnsCRUD.add(btnAfficherContratByClient);

        for (JButton button : listBtnsCRUD) {
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            buttonPanel.add(button);
            panBtnsCRUD.add(buttonPanel);
        }
        enabledBtnsCRUD(false);
        btnAfficherContratByClient.setEnabled(false);
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
                    if (mainFrame.clientDAO.findAll().isEmpty()) {
                        btnEditer.setEnabled(false);
                        btnSupprimer.setEnabled(false);
                        return;
                    }
                    for (Client client :
                            mainFrame.clientDAO.findAll()) {
                        comboBoxSociete.addItem(client);
                    }
                    comboBoxSociete.setSelectedIndex(0);
                    break;
                case Prospect:
                    if (mainFrame.prospectDAO.findAll().isEmpty()) {
                        btnEditer.setEnabled(false);
                        btnSupprimer.setEnabled(false);
                        return;
                    }
                    for (Prospect prospect :
                            mainFrame.prospectDAO.findAll()) {
                        comboBoxSociete.addItem(prospect);
                    }
                    comboBoxSociete.setSelectedIndex(0);
                    break;
            }
        } catch (DateTimeException dte) {
            JOptionPane.showMessageDialog(this,
                    "La date doit être dans le format suivant : dd/MM/yyyy",
                    "Erreur de saisie", JOptionPane.ERROR_MESSAGE);
            LOGGER.log(Level.WARNING, dte.getMessage());
        } catch (NumberFormatException nft) {
            JOptionPane.showMessageDialog(this,
                    "La valeur saisie doit être uniquement composé de chiffres", "Erreur de saisie",
                    JOptionPane.ERROR_MESSAGE);
            LOGGER.log(Level.WARNING, nft.getMessage());
        } catch (ExceptionEntity ee) {
            JOptionPane.showMessageDialog(this, ee.getMessage(),
                    "Erreur de saisie", JOptionPane.ERROR_MESSAGE);
            LOGGER.log(Level.WARNING, ee.getMessage());
        } catch (ExceptionDAO exceptionDAO) {
            switch (exceptionDAO.getGravite()) {
                case 5:
                    exceptionDAO.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Erreur dans l'application, l'application doit fermer", "Erreur dans l'application",
                            JOptionPane.ERROR_MESSAGE);
                    LOGGER.log(Level.SEVERE, exceptionDAO.getMessage());
                    System.exit(1);
                    break;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erreur dans l'application, l'application doit fermer", "Erreur dans " +
                            "l'application",
                    JOptionPane.ERROR_MESSAGE);
            LOGGER.log(Level.SEVERE, exception.getMessage());
            System.exit(1);
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
        //btnAfficherContratByClient.setEnabled(isEnabled);
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
        btnEditer.setText("Modifier un " + nomDuGroupe);
        //btnAfficherContratByClient.setText("Afficher les contrats");
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
                btnValiderSupprimerOuEditer.setText(
                        "Supprimer : " + societeSelection.getRaisonSociale());
                btnSupprimer.setBackground(colorGreenCustom);
                btnSupprimer.setForeground(Color.WHITE);
                btnEditer.setBackground(null);
                btnEditer.setForeground(Color.BLACK);
                btnAfficherContratByClient.setBackground(null);
                btnAfficherContratByClient.setForeground(Color.BLACK);
                break;
            case UPDATE:
                btnValiderSupprimerOuEditer.setText(
                        "Modifer : " + societeSelection.getRaisonSociale());
                btnEditer.setBackground(colorGreenCustom);
                btnEditer.setForeground(Color.WHITE);
                btnSupprimer.setBackground(null);
                btnSupprimer.setForeground(Color.BLACK);
                btnAfficherContratByClient.setBackground(null);
                btnAfficherContratByClient.setForeground(Color.BLACK);
                break;
            case READ_CONTRAT:
                btnValiderSupprimerOuEditer.setText(
                        "Afficher les contrats du client : " + societeSelection.getRaisonSociale());
                btnAfficherContratByClient.setBackground(colorGreenCustom);
                btnAfficherContratByClient.setForeground(Color.WHITE);
                btnSupprimer.setBackground(null);
                btnSupprimer.setForeground(Color.BLACK);
                btnEditer.setBackground(null);
                btnEditer.setForeground(Color.BLACK);
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

        panChoisirBDD = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnSelectMySQL = createButton("Base de données MySQL");
        btnSelectMongoDB = createButton("Base de données MongoDB");

        panChoisirTypeSociete = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnGererClient = createButton("Gérer les clients");
        btnGererProspect = createButton("Gérer les prospects");

        panChoisirTypeSociete.add(btnGererClient);
        panChoisirTypeSociete.add(btnGererProspect);

        panChoisirBDD.add(btnSelectMySQL);
        panChoisirBDD.add(btnSelectMongoDB);
        super.panCentral.add(panChoisirBDD);

        super.panCentral.add(panChoisirTypeSociete);
        panChoisirTypeSociete.setVisible(false);

        labelTypeSociete = createLabel("");
        labelTypeSociete.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 24));
        panLabelTypeSociete = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panLabelTypeSociete.add(labelTypeSociete);
        super.panCentral.add(panLabelTypeSociete);

        super.panCentral.add(panBtnsCRUD);
        panBtnsCRUD.setVisible(false);

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
        if (abstractDAOFactory != null) {
            panChoisirBDD.setVisible(false);
        }
        setVisible(true);
    }
    //--------------------- ABSTRACT METHODS -----------------------------------
    //--------------------- STATIC - GETTERS - SETTERS -------------------------
    //--------------------- GETTERS - SETTERS ----------------------------------
    //--------------------- TO STRING METHOD------------------------------------
}
