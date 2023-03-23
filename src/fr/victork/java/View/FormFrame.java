/**
 * @author Victor K
 * @version 1.00
 * Cette classe a pour but d'administrer les méthodes Create, Update et
 * Delete du CRUD et d'organiser l'affichage en fonction
 */
package fr.victork.java.View;

import fr.victork.java.DAO.mysql.MySQLClientDAO;
import fr.victork.java.DAO.mysql.MySQLProspectDAO;
import fr.victork.java.Entity.*;
import fr.victork.java.Exception.ExceptionDAO;
import fr.victork.java.Exception.ExceptionEntity;
import fr.victork.java.Tools.FormatterDate;

import javax.swing.*;
import java.awt.*;
import java.time.DateTimeException;
import java.util.logging.Level;

import static fr.victork.java.Log.LoggerReverso.LOGGER;

/**
 * Cette classe a pour but d'administrer les méthodes Create, Update et
 * Delete du CRUD et d'organiser l'affichage en fonction
 */
public class FormFrame extends MainFrame {
    //--------------------- CONSTANTS ------------------------------------------
    //--------------------- STATIC VARIABLES -----------------------------------
    //--------------------- INSTANCE VARIABLES ---------------------------------
    private JPanel subpanel, panFormulaire, panValiderFormulaire,
            panRadioButtonGroup;
    private EnumInstanceDeSociete enumInstanceDeSociete;
    private EnumCRUD enumCRUD;
    private JTextField inputIdentifiant, inputRaisonSociale, inputNumeroDeRue,
            inputNomDeRue, inputCodePostal, inputVille, inputTelephone,
            inputAdresseMail, inputCommentaire, inputChiffreAffaires,
            inputNombreEmployes, inputDateProspection;
    private JLabel labelIdentifiant, labelRaisonSociale, labelNumeroDeRue,
            labelNomDeRue, labelCodePostal, labelVille, labelTelephone,
            labelAdresseMail, labelCommentaire, labelChiffreAffaires,
            labelNombreEmployes, labelDateProspection, labelProspectInteresse;
    private JButton btnValider;
    private Societe societeSelection;
    private JRadioButton btnInterestingProspectYes, btnInterestingProspectNo;
    private ButtonGroup buttonGroupInterestingProspect;
    private String interestingProspectString;

    //--------------------- CONSTRUCTORS ---------------------------------------

    /**
     * Ce constructeur ouvre une nouvelle fenêtre permettant de créer une
     * nouvelle société de type @param enumInstanceDeSociete
     *
     * @param enumInstanceDeSociete Type de société
     * @param enumCRUD              Action du CRUD
     * @param largeurFenetre        int Largeur de la fenêtre
     * @param hauteurFenetre        int Hauteur de la fenêtre
     * @param positionX             int Position X sur l'écran
     * @param positionY             int Position Y sur l'écran
     * @param pleinEcran            Boolean True si le mode plein écran est
     *                              activé
     */
    public FormFrame(EnumInstanceDeSociete enumInstanceDeSociete,
                     EnumCRUD enumCRUD, int largeurFenetre, int hauteurFenetre,
                     int positionX, int positionY, boolean pleinEcran) {
        super(largeurFenetre, hauteurFenetre, positionX, positionY, pleinEcran);
        this.enumInstanceDeSociete = enumInstanceDeSociete;
        this.enumCRUD = enumCRUD;
        setupGUI(largeurFenetre, hauteurFenetre, positionX, positionY,
                pleinEcran);
        initLabels();
        setupInterestingProspectButtongroup();
        setupInputs();
        setupBtnValider();
        affichageFormulaire();
        addListenerToBtnValider();
    }

    /**
     * Ce constructeur ouvre une nouvelle fenêtre permettant de modifier
     * ou supprimer en fonction de la valeur de @param enumCRUD une instance
     * de société @param societe
     *
     * @param societe        Instance de Société
     * @param enumCRUD       Action du CRUD
     * @param largeurFenetre int Largeur de la fenêtre
     * @param hauteurFenetre int Hauteur de la fenêtre
     * @param positionX      int Position X sur l'écran
     * @param positionY      int Position Y sur l'écran
     * @param pleinEcran     Boolean True si le mode plein écran est activé
     * @throws ExceptionEntity Remonte une exception en cas d'erreur
     */
    public FormFrame(Societe societe, EnumCRUD enumCRUD, int largeurFenetre,
                     int hauteurFenetre, int positionX, int positionY,
                     boolean pleinEcran) {
        super(largeurFenetre, hauteurFenetre, positionX, positionY, pleinEcran);
        if (societe instanceof Client) {
            this.enumInstanceDeSociete = EnumInstanceDeSociete.Client;
        } else if (societe instanceof Prospect) {
            this.enumInstanceDeSociete = EnumInstanceDeSociete.Prospect;
        }
        this.enumCRUD = enumCRUD;
        this.societeSelection = societe;
        setupGUI(largeurFenetre, hauteurFenetre, positionX, positionY,
                pleinEcran);
        initLabels();
        setupInterestingProspectButtongroup();
        setupInputs();
        setupBtnValider();
        affichageFormulaire();
        addListenerToBtnValider();
    }

    //--------------------- STATIC METHODS -------------------------------------
    //--------------------- INSTANCE METHODS -----------------------------------

    /**
     * Cette méthode définit le positionnement et les dimensions de la fenêtre.
     * Cette méthode modifie le titre de la fenêtre en fonction de l'action du
     * CRUD
     *
     * @param largeurFenetre int Largeur de la fenêtre
     * @param hauteurFenetre int Hauteur de la fenêtre
     * @param positionX      int Position X sur l'écran
     * @param positionY      int Position Y sur l'écran
     * @param pleinEcran     Boolean True si le mode plein écran est activé
     */
    private void setupGUI(int largeurFenetre, int hauteurFenetre, int positionX,
                          int positionY, boolean pleinEcran) {
        switch (enumCRUD) {
            case UPDATE:
                setTitle("Modifier le " +
                        societeSelection.getClass().getSimpleName()
                                .toLowerCase() + " " +
                        societeSelection.getRaisonSociale());
                break;
            case DELETE:
                setTitle("Supprimer le " +
                        societeSelection.getClass().getSimpleName()
                                .toLowerCase() + " " +
                        societeSelection.getRaisonSociale());
                break;
            case CREATE:
                setTitle("Créer un " + enumInstanceDeSociete.name());
                break;
        }
        super.panCentral.setLayout(new FlowLayout(FlowLayout.LEFT));
        setSize(largeurFenetre, hauteurFenetre);
        setLocation(positionX, positionY);
        super.estEnPleinEcran = pleinEcran;
        if (super.estEnPleinEcran) {
            setExtendedState(JFrame.MAXIMIZED_BOTH);
        }
        setVisible(true);
    }

    /**
     * Cette méthode récupère l'action du CRUD en cours et retourne la
     * méthode qui lui est associée. Cette méthode ouvre une boîte de
     * dialogue si le formulaire n'est pas correctement rempli ou ferme
     * l'application si une erreur non répertoriée est attrapée
     */
    private void addListenerToBtnValider() {
        btnValider.addActionListener(e -> {
            try {
                switch (enumCRUD) {
                    case CREATE:
                        create();
                        break;
                    case UPDATE:
                        update();
                        break;
                    case DELETE:
                        delete();
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
                    case 1:
                        JOptionPane.showMessageDialog(this,
                                "La raison sociale de cette société existe déjà.",
                                "Erreur de saisie",
                                JOptionPane.ERROR_MESSAGE);
                        LOGGER.log(Level.INFO, exceptionDAO.getMessage());
                        break;
                    case 5:
                        exceptionDAO.printStackTrace();
                        JOptionPane.showMessageDialog(this, "Erreur dans l'application, l'application doit fermer",
                                "Erreur dans l'application",
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
    }

    /**
     * Cette méthode instancie les champs du formulaire et les préremplis
     * avec l'instance de société sélectionné si la méthode du CRUD en cours
     * est "delete" ou "update"
     */
    private void setupInputs() {
        switch (enumCRUD) {
            case CREATE:
                this.inputIdentifiant = createJTextField(null, 25);
                this.inputRaisonSociale = createJTextField(null, 25);
                this.inputNumeroDeRue = createJTextField(null, 25);
                this.inputNomDeRue = createJTextField(null, 25);
                this.inputCodePostal = createJTextField(null, 25);
                this.inputVille = createJTextField(null, 25);
                this.inputTelephone = createJTextField(null, 25);
                this.inputAdresseMail = createJTextField(null, 25);
                this.inputCommentaire = createJTextField(null, 25);
                this.inputChiffreAffaires = createJTextField(null, 25);
                this.inputNombreEmployes = createJTextField(null, 25);
                this.inputDateProspection = createJTextField(null, 25);
                this.interestingProspectString = "Oui";
                this.btnInterestingProspectYes.setSelected(true);
                break;
            case DELETE:
            case UPDATE:
                this.inputIdentifiant = createJTextField(
                        String.valueOf(this.societeSelection.getIdentifiant()),
                        25);
                this.inputRaisonSociale = createJTextField(String.valueOf(
                        this.societeSelection.getRaisonSociale()), 25);
                this.inputNumeroDeRue = createJTextField(
                        String.valueOf(this.societeSelection.getNumeroDeRue()),
                        25);
                this.inputNomDeRue = createJTextField(
                        String.valueOf(this.societeSelection.getNomDeRue()),
                        25);
                this.inputCodePostal = createJTextField(
                        String.valueOf(this.societeSelection.getCodePostal()),
                        25);
                this.inputVille = createJTextField(
                        String.valueOf(this.societeSelection.getVille()), 25);
                this.inputTelephone = createJTextField(
                        String.valueOf(this.societeSelection.getTelephone()),
                        25);
                this.inputAdresseMail = createJTextField(
                        String.valueOf(this.societeSelection.getAdresseMail()),
                        25);
                this.inputCommentaire = createJTextField(
                        String.valueOf(this.societeSelection.getCommentaires()),
                        25);
                this.inputIdentifiant.setEditable(false);
                switch (enumInstanceDeSociete) {
                    case Client:
                        Client client = (Client) this.societeSelection;
                        this.inputChiffreAffaires = createJTextField(
                                String.valueOf(client.getChiffreAffaires()),
                                25);
                        this.inputNombreEmployes = createJTextField(
                                String.valueOf(client.getNombreEmployes()), 25);
                        break;
                    case Prospect:
                        Prospect prospect = (Prospect) this.societeSelection;
                        this.inputDateProspection = createJTextField(
                                FormatterDate.convertiEtFormatDateEnChaine(
                                        prospect.getDateProsprection()), 25);
                        if (prospect.getProspectInteresse().equals("Oui")) {
                            btnInterestingProspectYes.setSelected(true);
                            interestingProspectString = "Oui";
                        } else {
                            btnInterestingProspectNo.setSelected(true);
                            interestingProspectString = "Non";
                        }
                        break;
                }
        }
        if (this.enumCRUD == EnumCRUD.DELETE) {
            inputRaisonSociale.setEditable(false);
            inputNumeroDeRue.setEditable(false);
            inputNomDeRue.setEditable(false);
            inputCodePostal.setEditable(false);
            inputVille.setEditable(false);
            inputTelephone.setEditable(false);
            inputAdresseMail.setEditable(false);
            inputCommentaire.setEditable(false);
            switch (enumInstanceDeSociete) {
                case Client:
                    inputChiffreAffaires.setEditable(false);
                    inputNombreEmployes.setEditable(false);
                    break;
                case Prospect:
                    inputDateProspection.setEditable(false);
                    btnInterestingProspectYes.setEnabled(false);
                    btnInterestingProspectNo.setEnabled(false);
                    break;
            }
        }
    }

    /**
     * Cette méthode change le label du bouton "valider" en fonction de l'action
     * du CRUD en cours et le centre au milieu d'un panel
     */
    private void setupBtnValider() {
        switch (enumCRUD) {
            case CREATE:
                this.btnValider = createButton(
                        "Créer un nouveau " + enumInstanceDeSociete.name());
                break;
            case UPDATE:
                this.btnValider = createButton(
                        "Modifier ce " + enumInstanceDeSociete.name());
                break;
            case DELETE:
                this.btnValider = createButton(
                        "Supprimer ce " + enumInstanceDeSociete.name());
                break;
        }

        panValiderFormulaire =
                new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        panValiderFormulaire.add(btnValider);
    }

    /**
     * Récupère l'instance d'une société pour effectuer des modifications sur
     * ses propriétés, redirige l'utilisateur sur la liste des sociétés
     *
     * @throws ExceptionEntity Remonte une exception en cas d'erreur
     */
    private void update() throws ExceptionEntity, ExceptionDAO {
        societeSelection.setRaisonSociale(inputRaisonSociale.getText());
        societeSelection.setNumeroDeRue(inputNumeroDeRue.getText());
        societeSelection.setNomDeRue(inputNomDeRue.getText());
        societeSelection.setCodePostal(inputCodePostal.getText());
        societeSelection.setVille(inputVille.getText());
        societeSelection.setTelephone(inputTelephone.getText());
        societeSelection.setAdresseMail(inputAdresseMail.getText());
        societeSelection.setCommentaires(inputCommentaire.getText());
        societeSelection.setAdresseMail(inputAdresseMail.getText());
        societeSelection.setAdresseMail(inputAdresseMail.getText());
        switch (enumInstanceDeSociete) {
            case Client:
                ((Client) societeSelection).setChiffreAffaires(
                        Double.parseDouble(inputChiffreAffaires.getText()));
                ((Client) societeSelection).setNombreEmployes(
                        Integer.parseInt(inputNombreEmployes.getText()));
                new MySQLClientDAO().save((Client) societeSelection);
                this.dispose();
                AffichageFrame affichageFrameClient =
                        new AffichageFrame(enumInstanceDeSociete, super.largeur,
                                super.hauteur, super.x, super.y, super.estEnPleinEcran);
                affichageFrameClient.updateEnumInstanceDeSociete(enumInstanceDeSociete);
                break;
            case Prospect:
                ((Prospect) societeSelection).setDateProsprection(
                        FormatterDate.convertiEtFormatDateEnLocalDate(
                                inputDateProspection.getText()));
                ((Prospect) societeSelection).setProspectInteresse(
                        interestingProspectString);
                new MySQLProspectDAO().save((Prospect) societeSelection);
                this.dispose();
                AffichageFrame affichageFrameProspect =
                        new AffichageFrame(enumInstanceDeSociete, super.largeur,
                                super.hauteur, super.x, super.y, super.estEnPleinEcran);
                affichageFrameProspect.updateEnumInstanceDeSociete(enumInstanceDeSociete);
                break;
        }

    }

    /**
     * Crée une nouvelle société depuis les champs du formulaire et l'ajoute à
     * la collection, redirige l'utilisateur sur la liste des sociétés
     *
     * @throws ExceptionEntity Remonte une exception en cas d'erreur
     */
    private void create() throws ExceptionEntity, ExceptionDAO {
        switch (enumInstanceDeSociete) {
            case Client:
                Client nouveauClient = new Client(
                        null,
                        inputRaisonSociale.getText(),
                        inputNumeroDeRue.getText(), inputNomDeRue.getText(),
                        inputCodePostal.getText(), inputVille.getText(),
                        inputTelephone.getText(), inputAdresseMail.getText(),
                        inputCommentaire.getText(),
                        Double.parseDouble(inputChiffreAffaires.getText()),
                        Integer.parseInt(inputNombreEmployes.getText())
                );
                new MySQLClientDAO().save(nouveauClient);
                this.dispose();
                AffichageFrame affichageFrameClient =
                        new AffichageFrame(enumInstanceDeSociete, super.largeur,
                                super.hauteur, super.x, super.y, super.estEnPleinEcran);
                affichageFrameClient.updateEnumInstanceDeSociete(enumInstanceDeSociete);
                break;
            case Prospect:
                Prospect nouveauProspect =
                        new Prospect(
                                null,
                                inputRaisonSociale.getText(),
                                inputNumeroDeRue.getText(),
                                inputNomDeRue.getText(),
                                inputCodePostal.getText(), inputVille.getText(),
                                inputTelephone.getText(),
                                inputAdresseMail.getText(),
                                inputCommentaire.getText(),
                                FormatterDate.convertiEtFormatDateEnLocalDate(
                                        inputDateProspection.getText()),
                                interestingProspectString);
                new MySQLProspectDAO().save(nouveauProspect);
                this.dispose();
                AffichageFrame affichageFrameProspect =
                        new AffichageFrame(enumInstanceDeSociete, super.largeur,
                                super.hauteur, super.x, super.y, super.estEnPleinEcran);
                affichageFrameProspect.updateEnumInstanceDeSociete(enumInstanceDeSociete);
                break;
        }


    }

    /**
     * Ouvre une fenêtre de dialogue, supprimer l'entité sélectionnée en cas
     * de confirmation et retourne l'utilisateur sur la liste des sociétés
     *
     * @throws ExceptionEntity Remonte une exception en cas d'erreur
     */
    private void delete() throws ExceptionDAO {
        int choix = JOptionPane.showConfirmDialog(super.panCentral,
                "Supprimer : " + societeSelection.getRaisonSociale() + " ?",
                "Confirmation", JOptionPane.YES_NO_OPTION);
        if (choix == JOptionPane.YES_OPTION) {
            switch (enumInstanceDeSociete) {
                case Client:
                    new MySQLClientDAO().delete(societeSelection.getIdentifiant());
                    break;
                case Prospect:
                    new MySQLProspectDAO().delete(societeSelection.getIdentifiant());
                    break;
            }
            this.dispose();
            AffichageFrame affichageFrame =
                    new AffichageFrame(enumInstanceDeSociete, super.largeur,
                            super.hauteur, super.x, super.y,
                            super.estEnPleinEcran);
            affichageFrame.updateEnumInstanceDeSociete(enumInstanceDeSociete);
        } else if (choix == JOptionPane.NO_OPTION) {
            JOptionPane.getRootFrame().dispose();
        } else {
            JOptionPane.getRootFrame().dispose();
        }
    }

    /**
     * Crée un nouveau panel pour le champ "Prospect intéressé" et mémorise
     * la valeur du bouton radio sélectionné dans une variable
     */
    private void setupInterestingProspectButtongroup() {
        buttonGroupInterestingProspect = new ButtonGroup();

        btnInterestingProspectYes = new JRadioButton("Oui");
        btnInterestingProspectNo = new JRadioButton("Non");

        buttonGroupInterestingProspect.add(btnInterestingProspectYes);
        buttonGroupInterestingProspect.add(btnInterestingProspectNo);
        panRadioButtonGroup = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panRadioButtonGroup.add(btnInterestingProspectYes);
        panRadioButtonGroup.add(btnInterestingProspectNo);

        btnInterestingProspectYes.addActionListener(
                e -> interestingProspectString = "Oui");

        btnInterestingProspectNo.addActionListener(
                e -> interestingProspectString = "Non");
    }

    /**
     * Cette méthode instancie les labels des champs avec le nom du champ
     */
    private void initLabels() {
        this.labelIdentifiant = createLabel("Identifiant");
        this.labelRaisonSociale = createLabel("Raison sociale");
        this.labelNumeroDeRue = createLabel("Numéro de rue");
        this.labelNomDeRue = createLabel("Nom de rue");
        this.labelCodePostal = createLabel("Code postal");
        this.labelVille = createLabel("Ville");
        this.labelTelephone = createLabel("Téléphone");
        this.labelAdresseMail = createLabel("Adresse mail");
        this.labelCommentaire = createLabel("Commentaires");
        this.labelChiffreAffaires = createLabel("Chiffre d'affaires");
        this.labelNombreEmployes = createLabel("Nombre d'employés");
        this.labelDateProspection = createLabel("Date de prosprection");
        this.labelProspectInteresse = createLabel("Prospect intéressé");
    }

    /**
     * Cette méthode crée une grille pour placer les labels et les champs qui
     * leur sont associés de gauche à droite puis de haut en bas
     */
    private void affichageFormulaire() {
        panFormulaire = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.LINE_START;

        switch (enumCRUD) {
            case UPDATE:
            case DELETE:
                panFormulaire.add(labelIdentifiant, gbc);
                gbc.gridx++;
                panFormulaire.add(inputIdentifiant, gbc);
                gbc.gridx = 0;
                gbc.gridy++;
                break;
        }


        panFormulaire.add(labelRaisonSociale, gbc);
        gbc.gridx++;
        panFormulaire.add(inputRaisonSociale, gbc);
        gbc.gridx = 0;
        gbc.gridy++;

        panFormulaire.add(labelNumeroDeRue, gbc);
        gbc.gridx++;
        panFormulaire.add(inputNumeroDeRue, gbc);
        gbc.gridx = 0;
        gbc.gridy++;

        panFormulaire.add(labelNomDeRue, gbc);
        gbc.gridx++;
        panFormulaire.add(inputNomDeRue, gbc);
        gbc.gridx = 0;
        gbc.gridy++;

        panFormulaire.add(labelCodePostal, gbc);
        gbc.gridx++;
        panFormulaire.add(inputCodePostal, gbc);
        gbc.gridx = 0;
        gbc.gridy++;

        panFormulaire.add(labelVille, gbc);
        gbc.gridx++;
        panFormulaire.add(inputVille, gbc);
        gbc.gridx = 0;
        gbc.gridy++;

        panFormulaire.add(labelTelephone, gbc);
        gbc.gridx++;
        panFormulaire.add(inputTelephone, gbc);
        gbc.gridx = 0;
        gbc.gridy++;

        panFormulaire.add(labelAdresseMail, gbc);
        gbc.gridx++;
        panFormulaire.add(inputAdresseMail, gbc);
        gbc.gridx = 0;
        gbc.gridy++;

        panFormulaire.add(labelCommentaire, gbc);
        gbc.gridx++;
        panFormulaire.add(inputCommentaire, gbc);
        gbc.gridx = 0;
        gbc.gridy++;

        switch (this.enumInstanceDeSociete) {
            case Client:
                panFormulaire.add(labelChiffreAffaires, gbc);
                gbc.gridx++;
                panFormulaire.add(inputChiffreAffaires, gbc);
                gbc.gridx = 0;
                gbc.gridy++;

                panFormulaire.add(labelNombreEmployes, gbc);
                gbc.gridx++;
                panFormulaire.add(inputNombreEmployes, gbc);
                gbc.gridx = 0;
                gbc.gridy++;
                break;
            case Prospect:
                panFormulaire.add(labelDateProspection, gbc);
                gbc.gridx++;
                panFormulaire.add(inputDateProspection, gbc);
                gbc.gridx = 0;
                gbc.gridy++;

                panFormulaire.add(labelProspectInteresse, gbc);
                gbc.gridx++;
                panFormulaire.add(panRadioButtonGroup, gbc);
                gbc.gridx = 0;
                gbc.gridy++;
                break;
        }
        gbc.gridx++;

        gbc.gridx = 0;
        panFormulaire.add(Box.createHorizontalGlue(), gbc);

        gbc.gridx++;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.CENTER;
        panFormulaire.add(btnValider, gbc);

        gbc.gridx++;
        gbc.anchor = GridBagConstraints.LINE_END;
        panFormulaire.add(Box.createHorizontalGlue(), gbc);

        JScrollPane scrollPane = new JScrollPane(panFormulaire);
        super.panCentral.setLayout(new BorderLayout());
        super.panCentral.add(scrollPane, BorderLayout.CENTER);
    }

    //--------------------- ABSTRACT METHODS -----------------------------------
    //--------------------- STATIC - GETTERS - SETTERS -------------------------
    //--------------------- GETTERS - SETTERS ----------------------------------
    //--------------------- TO STRING METHOD------------------------------------
}
