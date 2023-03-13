package fr.victork.java.View;

import fr.victork.java.Entity.*;
import fr.victork.java.Exception.ExceptionEntity;
import fr.victork.java.Tools.FormatterDate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.DateTimeException;

public class FormFrame extends MainFrame {
    //--------------------- CONSTANTS ------------------------------------------
    //--------------------- STATIC VARIABLES -----------------------------------
    //--------------------- INSTANCE VARIABLES ---------------------------------
    private JPanel subpanel, panForm, panVtnValiderFormulaire, panButtonGroup;
    private EnumInstanceDeSociete enumInstanceDeSociete;
    private EnumCRUD enumCRUD;
    private JTextField inputIdentifiant, inputRaisonSociale, inputNumeroDeRue,
            inputNomDeRue, inputCodePostal, inputVille,
            inputTelephone, inputAdresseMail, inputCommentaire, inputChiffreAffaires, inputNombreEmployes,
            inputDateProspection;
    private JLabel labelIdentifiant, labelRaisonSociale, labelNumeroDeRue, labelNomDeRue, labelCodePostal, labelVille,
            labelTelephone, labelAdresseMail, labelCommentaire, labelChiffreAffaires, labelNombreEmployes,
            labelDateProspection, labelProspectInteresse;
    private Societe societeSelectionne;
    private JButton btnValiderFormulaire;
    private Societe objetSelectionne;
    private JRadioButton prospectInteresseOui, prospectInteresseNon;
    private ButtonGroup buttonGroup;
    private String textProspectInteresse;

    //--------------------- CONSTRUCTORS ---------------------------------------
    public FormFrame(EnumInstanceDeSociete enumInstanceDeSociete, EnumCRUD enumCRUD,
                     int largeurFenetre, int hauteurFenetre, int positionX, int positionY, boolean pleinEcran) {
        super(largeurFenetre, hauteurFenetre, positionX, positionY, pleinEcran);
        initFrameConstructeurs();
        this.enumInstanceDeSociete = enumInstanceDeSociete;
        this.enumCRUD = enumCRUD;
        initLabels();
        setupButtonGroup();
        setupInputs();
        ajouterBtnValider();
        remplirPanel();
        setSize(largeurFenetre, hauteurFenetre);
        setLocation(positionX, positionY);
        super.estEnPleinEcran = pleinEcran;
        if (super.estEnPleinEcran) {
            setExtendedState(JFrame.MAXIMIZED_BOTH);
        }
        setVisible(true);
    }

    public FormFrame(Societe societe, EnumCRUD enumCRUD, int largeurFenetre, int hauteurFenetre,
                     int positionX, int positionY, boolean pleinEcran)
            throws ExceptionEntity {
        super(largeurFenetre, hauteurFenetre, positionX, positionY, pleinEcran);
        initFrameConstructeurs();
        if (societe instanceof Client) {
            this.enumInstanceDeSociete = EnumInstanceDeSociete.Client;
        } else if (societe instanceof Prospect) {
            this.enumInstanceDeSociete = EnumInstanceDeSociete.Prospect;
        }
        this.enumCRUD = enumCRUD;
        this.objetSelectionne = societe;
        initLabels();
        setupButtonGroup();
        setupInputs();
        ajouterBtnValider();
        remplirPanel();
        setSize(largeurFenetre, hauteurFenetre);
        setLocation(positionX, positionY);
        super.estEnPleinEcran = pleinEcran;
        if (super.estEnPleinEcran) {
            setExtendedState(JFrame.MAXIMIZED_BOTH);
        }
        setVisible(true);
    }

    private void initFrameConstructeurs() {
        setTitle("Form frame");
        super.panCentral.setLayout(new FlowLayout(FlowLayout.LEFT));
    }

    //--------------------- STATIC METHODS -------------------------------------
    //--------------------- INSTANCE METHODS -----------------------------------
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
                this.textProspectInteresse = "Oui";
                this.prospectInteresseOui.setSelected(true);
                break;
            case DELETE:
            case UPDATE:
                this.inputIdentifiant = createJTextField(String.valueOf(this.objetSelectionne.getIdentifiant()),
                        25);
                this.inputRaisonSociale = createJTextField(String.valueOf(this.objetSelectionne.getRaisonSociale()),
                        25);
                this.inputNumeroDeRue = createJTextField(String.valueOf(this.objetSelectionne.getNumeroDeRue()),
                        25);
                this.inputNomDeRue = createJTextField(String.valueOf(this.objetSelectionne.getNomDeRue()),
                        25);
                this.inputCodePostal = createJTextField(String.valueOf(this.objetSelectionne.getCodePostal()),
                        25);
                this.inputVille = createJTextField(String.valueOf(this.objetSelectionne.getVille()),
                        25);
                this.inputTelephone = createJTextField(String.valueOf(this.objetSelectionne.getTelephone()),
                        25);
                this.inputAdresseMail = createJTextField(String.valueOf(this.objetSelectionne.getAdresseMail()),
                        25);
                this.inputCommentaire = createJTextField(String.valueOf(this.objetSelectionne.getCommentaires()),
                        25);
                this.inputIdentifiant.setEditable(false);
                switch (enumInstanceDeSociete) {
                    case Client:
                        Client client = (Client) this.objetSelectionne;
                        this.inputChiffreAffaires =
                                createJTextField(String.valueOf(client.getChiffreAffaires()),
                                        25);
                        this.inputNombreEmployes = createJTextField(String.valueOf(client.getNombreEmployes()),
                                25);
                        break;
                    case Prospect:
                        Prospect prospect = (Prospect) this.objetSelectionne;
                        this.inputDateProspection =
                                createJTextField(FormatterDate
                                                .convertiEtFormatDateEnChaine(prospect.getDateProsprection()),
                                        25);
                        if (prospect.getProspectInteresse().equals("Oui")) {
                            prospectInteresseOui.setSelected(true);
                            textProspectInteresse = "Oui";
                        } else {
                            prospectInteresseNon.setSelected(true);
                            textProspectInteresse = "Non";
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
                    prospectInteresseOui.setEnabled(false);
                    prospectInteresseNon.setEnabled(false);
                    break;
            }
        }
    }

    private void ajouterBtnValider() {
        switch (enumCRUD) {
            case CREATE:
                this.btnValiderFormulaire = createButton("Créer un nouveau " + enumInstanceDeSociete.name());
                break;
            case UPDATE:
                this.btnValiderFormulaire = createButton("Modifier ce " + enumInstanceDeSociete.name());
                break;
            case DELETE:
                this.btnValiderFormulaire = createButton("Supprimer ce " + enumInstanceDeSociete.name());
                break;
        }
        btnValiderFormulaire.addActionListener(e -> {
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
                        "La date doit être dans le format suivant : dd/MM/yyyy", "Erreur de saisie",
                        JOptionPane.ERROR_MESSAGE);
            } catch (NumberFormatException nft) {
                JOptionPane.showMessageDialog(this,
                        "La chaîne ne peut pas être convertit en nombre", "Erreur de saisie",
                        JOptionPane.ERROR_MESSAGE);
            } catch (ExceptionEntity ee) {
                JOptionPane.showMessageDialog(this, ee.getMessage(), "Erreur de saisie",
                        JOptionPane.ERROR_MESSAGE);
            } catch (Exception exception) {
                exception.printStackTrace();
                System.exit(1);
            }
        });
        panVtnValiderFormulaire = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        panVtnValiderFormulaire.add(btnValiderFormulaire);
    }

    private void update() throws ExceptionEntity {
        objetSelectionne.setRaisonSociale(inputRaisonSociale.getText());
        objetSelectionne.setNumeroDeRue(inputNumeroDeRue.getText());
        objetSelectionne.setNomDeRue(inputNomDeRue.getText());
        objetSelectionne.setCodePostal(inputCodePostal.getText());
        objetSelectionne.setVille(inputVille.getText());
        objetSelectionne.setTelephone(inputTelephone.getText());
        objetSelectionne.setAdresseMail(inputAdresseMail.getText());
        objetSelectionne.setCommentaires(inputCommentaire.getText());
        objetSelectionne.setAdresseMail(inputAdresseMail.getText());
        objetSelectionne.setAdresseMail(inputAdresseMail.getText());
        switch (enumInstanceDeSociete) {
            case Client:
                ((Client) objetSelectionne).setChiffreAffaires(Double.parseDouble(inputChiffreAffaires.getText()));
                ((Client) objetSelectionne).setNombreEmployes(Integer.parseInt(inputNombreEmployes.getText()));
                break;
            case Prospect:
                ((Prospect) objetSelectionne).setDateProsprection(FormatterDate
                        .convertiEtFormatDateEnLocalDate(inputDateProspection.getText()));
                ((Prospect) objetSelectionne).setProspectInteresse(textProspectInteresse);
                break;
        }
        this.dispose();
        AffichageFrame affichageFrame = new AffichageFrame(enumInstanceDeSociete, super.largeur, super.hauteur,
                super.x, super.y, super.estEnPleinEcran);
        affichageFrame.updateEnumInstanceDeSociete(enumInstanceDeSociete);
    }


    private void create() throws ExceptionEntity {
        switch (enumInstanceDeSociete) {
            case Client:
                Client nouveauClient = new Client(
                        inputRaisonSociale.getText(),
                        inputNumeroDeRue.getText(),
                        inputNomDeRue.getText(),
                        inputCodePostal.getText(),
                        inputVille.getText(),
                        inputTelephone.getText(),
                        inputAdresseMail.getText(),
                        inputCommentaire.getText(),
                        Double.parseDouble(inputChiffreAffaires.getText()),
                        Integer.parseInt(inputNombreEmployes.getText())
                );
                CollectionClients.getCollection().add(nouveauClient);
                break;
            case Prospect:
                Prospect nouveauProspect = new Prospect(
                        inputRaisonSociale.getText(),
                        inputNumeroDeRue.getText(),
                        inputNomDeRue.getText(),
                        inputCodePostal.getText(),
                        inputVille.getText(),
                        inputTelephone.getText(),
                        inputAdresseMail.getText(),
                        inputCommentaire.getText(),
                        FormatterDate.convertiEtFormatDateEnLocalDate(inputDateProspection.getText()),
                        textProspectInteresse
                );
                CollectionProspects.getCollection().add(nouveauProspect);
                break;
        }

        this.dispose();
        AffichageFrame affichageFrame = new AffichageFrame(enumInstanceDeSociete, super.largeur, super.hauteur,
                super.x, super.y, super.estEnPleinEcran);
        affichageFrame.updateEnumInstanceDeSociete(enumInstanceDeSociete);
    }

    private void delete() throws ExceptionEntity {
        int choix = JOptionPane.showConfirmDialog(super.panCentral,
                "Supprimer : " + objetSelectionne.getRaisonSociale() + " ?",
                "Confirmation", JOptionPane.YES_NO_OPTION);
        if (choix == JOptionPane.YES_OPTION) {
            switch (enumInstanceDeSociete) {
                case Client:
                    CollectionClients.getCollection().remove(this.objetSelectionne);
                    break;
                case Prospect:
                    CollectionProspects.getCollection().remove(this.objetSelectionne);
                    break;
            }
            this.dispose();
            AffichageFrame affichageFrame = new AffichageFrame(enumInstanceDeSociete, super.largeur,
                    super.hauteur, super.x, super.y, super.estEnPleinEcran);
            affichageFrame.updateEnumInstanceDeSociete(enumInstanceDeSociete);
        } else if (choix == JOptionPane.NO_OPTION) {
            JOptionPane.getRootFrame().dispose();
        } else {
            JOptionPane.getRootFrame().dispose();
        }
    }

    private void setupButtonGroup() {
        buttonGroup = new ButtonGroup();

        prospectInteresseOui = new JRadioButton("Oui");
        prospectInteresseNon = new JRadioButton("Non");

        buttonGroup.add(prospectInteresseOui);
        buttonGroup.add(prospectInteresseNon);
        panButtonGroup = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panButtonGroup.add(prospectInteresseOui);
        panButtonGroup.add(prospectInteresseNon);

        prospectInteresseOui.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textProspectInteresse = "Oui";
            }
        });

        prospectInteresseNon.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textProspectInteresse = "Non";
            }
        });
    }

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

    private void remplirPanel() {
        panForm = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.LINE_START;

        switch (enumCRUD) {
            case UPDATE:
            case DELETE:
                panForm.add(labelIdentifiant, gbc);
                gbc.gridx++;
                panForm.add(inputIdentifiant, gbc);
                gbc.gridx = 0;
                gbc.gridy++;
                break;
        }


        panForm.add(labelRaisonSociale, gbc);
        gbc.gridx++;
        panForm.add(inputRaisonSociale, gbc);
        gbc.gridx = 0;
        gbc.gridy++;

        panForm.add(labelNumeroDeRue, gbc);
        gbc.gridx++;
        panForm.add(inputNumeroDeRue, gbc);
        gbc.gridx = 0;
        gbc.gridy++;

        panForm.add(labelNomDeRue, gbc);
        gbc.gridx++;
        panForm.add(inputNomDeRue, gbc);
        gbc.gridx = 0;
        gbc.gridy++;

        panForm.add(labelCodePostal, gbc);
        gbc.gridx++;
        panForm.add(inputCodePostal, gbc);
        gbc.gridx = 0;
        gbc.gridy++;

        panForm.add(labelVille, gbc);
        gbc.gridx++;
        panForm.add(inputVille, gbc);
        gbc.gridx = 0;
        gbc.gridy++;

        panForm.add(labelTelephone, gbc);
        gbc.gridx++;
        panForm.add(inputTelephone, gbc);
        gbc.gridx = 0;
        gbc.gridy++;

        panForm.add(labelAdresseMail, gbc);
        gbc.gridx++;
        panForm.add(inputAdresseMail, gbc);
        gbc.gridx = 0;
        gbc.gridy++;

        panForm.add(labelCommentaire, gbc);
        gbc.gridx++;
        panForm.add(inputCommentaire, gbc);
        gbc.gridx = 0;
        gbc.gridy++;

        switch (this.enumInstanceDeSociete) {
            case Client:
                panForm.add(labelChiffreAffaires, gbc);
                gbc.gridx++;
                panForm.add(inputChiffreAffaires, gbc);
                gbc.gridx = 0;
                gbc.gridy++;

                panForm.add(labelNombreEmployes, gbc);
                gbc.gridx++;
                panForm.add(inputNombreEmployes, gbc);
                gbc.gridx = 0;
                gbc.gridy++;
                break;
            case Prospect:
                panForm.add(labelDateProspection, gbc);
                gbc.gridx++;
                panForm.add(inputDateProspection, gbc);
                gbc.gridx = 0;
                gbc.gridy++;

                panForm.add(labelProspectInteresse, gbc);
                gbc.gridx++;
                panForm.add(panButtonGroup, gbc);
                gbc.gridx = 0;
                gbc.gridy++;
                break;
        }
        gbc.gridx++;

        gbc.gridx = 0;
        panForm.add(Box.createHorizontalGlue(), gbc);

        gbc.gridx++;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.CENTER;
        panForm.add(btnValiderFormulaire, gbc);

        gbc.gridx++;
        gbc.anchor = GridBagConstraints.LINE_END;
        panForm.add(Box.createHorizontalGlue(), gbc);

        JScrollPane scrollPane = new JScrollPane(panForm);
        super.panCentral.setLayout(new BorderLayout());
        super.panCentral.add(scrollPane, BorderLayout.CENTER);
    }

    //--------------------- ABSTRACT METHODS -----------------------------------
    //--------------------- STATIC - GETTERS - SETTERS -------------------------
    //--------------------- GETTERS - SETTERS ----------------------------------
    //--------------------- TO STRING METHOD------------------------------------
}
