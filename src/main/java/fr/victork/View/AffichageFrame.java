/**
 * @author Victor K
 * @version 1.00
 * Cette classe a pour but d'afficher toute la collection d'un type de
 * société sous la forme d'un tableau et de créer, supprimer ou éditer un
 * élément de cette liste
 */
package fr.victork.View;

import fr.victork.DAO.mysql.MySQLClientDAO;
import fr.victork.DAO.mysql.MySQLProspectDAO;
import fr.victork.Entity.*;
import fr.victork.Exception.ExceptionDAO;
import fr.victork.Exception.ExceptionEntity;
import fr.victork.Tools.FormatterDate;
import fr.victork.Tools.Tools;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.HierarchyBoundsAdapter;
import java.awt.event.HierarchyEvent;
import java.time.DateTimeException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;

import static fr.victork.Log.LoggerReverso.LOGGER;

/**
 * Cette classe a pour but d'afficher toute la collection d'un type de
 * société sous la forme d'un tableau et de créer, supprimer ou éditer un
 * élément de cette liste
 */
public class AffichageFrame extends MainFrame {
    //--------------------- CONSTANTS ------------------------------------------
    //--------------------- STATIC VARIABLES -----------------------------------
    //--------------------- INSTANCE VARIABLES ---------------------------------
    private JTable table;
    protected EnumInstanceDeSociete enumInstanceDeSociete;
    protected String[] columnNames = {};
    private Object[][] data;
    private JScrollPane scrollPane;
    private Societe societeSelection;
    private JButton btnCreer, btnSupprimer, btnEditer;
    private ArrayList listeSocieteSelection;
    private MainFrame mainFrame;

    //--------------------- CONSTRUCTORS ---------------------------------------

    /**
     * @param enumInstanceDeSociete Type de société
     * @param largeurFenetre        int Largeur de la fenêtre
     * @param hauteurFenetre        int Hauteur de la fenêtre
     * @param positionX             int Position X sur l'écran
     * @param positionY             int Position Y sur l'écran
     * @param pleinEcran            Boolean True si le mode plein écran est
     *                              activé
     */
    public AffichageFrame(EnumInstanceDeSociete enumInstanceDeSociete,
                          int largeurFenetre, int hauteurFenetre, int positionX,
                          int positionY, boolean pleinEcran, MainFrame mainFrame) {
        super(largeurFenetre, hauteurFenetre, positionX, positionY, pleinEcran);
        this.enumInstanceDeSociete = enumInstanceDeSociete;
        this.mainFrame = mainFrame;

        try {
            switch (enumInstanceDeSociete) {
                case Client:
                    listeSocieteSelection = mainFrame.clientDAO.findAll();
                    break;
                case Prospect:
                    listeSocieteSelection = mainFrame.prospectDAO.findAll();
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

        updateData();
        setupGUI(largeurFenetre, hauteurFenetre, positionX, positionY,
                pleinEcran);
        setTitle("Liste des " + enumInstanceDeSociete.name());
        setupPanBtnsCRUDAndSetEnabledToFalse();

        /**
         * Trie le tableau en ordre croissant par raison sociale
         */
        table.setAutoCreateRowSorter(true);
        TableRowSorter<TableModel> sorter =
                (TableRowSorter<TableModel>) table.getRowSorter();
        sorter.setSortKeys(Collections.singletonList(
                new RowSorter.SortKey(1, SortOrder.ASCENDING)));
        table.setRowSorter(sorter);

        /**
         * Ajuste la taille des colonnes quand la fenêtre est redimensionnée
         */
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                updateColumnWidths();
            }
        });

        /**
         * Ajuste la taille des colonnes si la fenêtre passe en mode plein
         * écran ou fenêtré
         */
        addHierarchyBoundsListener(new HierarchyBoundsAdapter() {
            @Override
            public void ancestorResized(HierarchyEvent e) {
                super.ancestorResized(e);
                updateColumnWidths();
            }
        });

        /**
         * Au click sur une ligne du tableau, mémorise le numéro de la ligne,
         * récupère l'identifiant de la ligne et boucle sur la collection
         * pour obtenir l'instance de l'objet associée à l'identifiant
         */
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = table.getSelectedRow();
                Object value = table.getValueAt(selectedRow, 0);
                int id = ((Integer) value).intValue();
                if (selectedRow != -1) {
                    etatsBoutons(true);
                    switch (enumInstanceDeSociete) {
                        case Client:
                            try {
                                societeSelection = mainFrame.clientDAO.find(id);
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
                                        JOptionPane.showMessageDialog(this, "Erreur dans l'application, l'application" +
                                                        " doit fermer", "Erreur dans l'application",
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
                            break;
                        case Prospect:
                            try {
                                societeSelection = mainFrame.prospectDAO.find(id);
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
                            break;
                    }
                }
            }
        });

        /**
         * Ferme la fenêtre et affiche le formulaire d'édition pour la ligne
         * sélectionnée
         */
        btnEditer.addActionListener(e -> {
            this.dispose();
            new FormFrame(societeSelection, EnumCRUD.UPDATE, super.largeur,
                    super.hauteur, super.x, super.y, super.estEnPleinEcran, mainFrame);
        });

        /**
         * Ferme la fenêtre et affiche le formulaire de suppression pour la
         * ligne sélectionnée
         */
        btnSupprimer.addActionListener(e -> {
            this.dispose();
            new FormFrame(societeSelection, EnumCRUD.DELETE, super.largeur,
                    super.hauteur, super.x, super.y, super.estEnPleinEcran, mainFrame);
        });

        /**
         * Ferme la fenêtre et affiche le formulaire de création pour le type
         * de société actuellement affiché
         */
        btnCreer.addActionListener(e -> {
            this.dispose();
            new FormFrame(enumInstanceDeSociete, EnumCRUD.CREATE, super.largeur,
                    super.hauteur, super.x, super.y, super.estEnPleinEcran, mainFrame);
        });

        setVisible(true);
    }

    //--------------------- STATIC METHODS -------------------------------------
    //--------------------- INSTANCE METHODS -----------------------------------

    /**
     * Cette méthode définit le positionnement et les dimensions de la fenêtre.
     *
     * @param largeurFenetre int Largeur de la fenêtre
     * @param hauteurFenetre int Hauteur de la fenêtre
     * @param positionX      int Position X sur l'écran
     * @param positionY      int Position Y sur l'écran
     * @param pleinEcran     Boolean True si le mode plein écran est activé
     */
    private void setupGUI(int largeurFenetre, int hauteurFenetre, int positionX,
                          int positionY, boolean pleinEcran) {
        this.table = new JTable(this.data, this.columnNames);
        this.table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        this.table.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
        this.table.setForeground(Color.BLACK);
        this.table.setBackground(new Color(180, 180, 180));
        this.table.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        this.table.setRowHeight(30);
        this.table.setIntercellSpacing(new Dimension(0, 1));
        this.table.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        scrollPane = new JScrollPane(this.table);
        super.panCentral.setLayout(new BorderLayout());
        super.panCentral.add(scrollPane, BorderLayout.CENTER);

        setSize(largeurFenetre, hauteurFenetre);
        setLocation(positionX, positionY);
        super.estEnPleinEcran = pleinEcran;
        if (super.estEnPleinEcran) {
            setExtendedState(JFrame.MAXIMIZED_BOTH);
        }
    }

    /**
     * Cette méthode a pour but d'instancier les boutons du CRUD, de les
     * disposer dans un panel et de les désactiver
     */
    private void setupPanBtnsCRUDAndSetEnabledToFalse() {
        btnSupprimer = createButton("Supprimer");
        btnEditer = createButton("Modifier");
        btnCreer = createButton("Créer");
        JPanel panBtnCrud = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panBtnCrud.add(btnSupprimer);
        panBtnCrud.add(btnEditer);
        panBtnCrud.add(btnCreer);
        super.panCentral.add(panBtnCrud, BorderLayout.SOUTH);
        etatsBoutons(false);
    }

    /**
     * Cette méthode a pour but d'activer ou désactiver les boutons "supprimer"
     * et "éditer"
     *
     * @param etat Boolean état des boutons
     */
    private void etatsBoutons(boolean etat) {
        btnEditer.setEnabled(etat);
        btnSupprimer.setEnabled(etat);
    }

    /**
     * Cette méthode a pour but de réinitialisé et de mettre à jour le modèle
     * du tableau
     *
     * @param enumInstanceDeSociete Type de société
     * @throws ExceptionEntity Remonte une exception en cas d'erreur
     */
    public void updateEnumInstanceDeSociete(
            EnumInstanceDeSociete enumInstanceDeSociete) {
        this.enumInstanceDeSociete = enumInstanceDeSociete;
        updateData();
        TableModel model = new DefaultTableModel(this.data, this.columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        this.table.setModel(model);
        updateColumnWidths();
    }

    /**
     * Cette méthode applique une taille minimale à chaque colonne de 75
     * pixels et répartie équitablement la taille des colonnes en fonction de
     * la place disponible
     */
    private void updateColumnWidths() {
        int tableWidth = AffichageFrame.this.panCentral.getWidth();
        TableColumnModel columnModel = table.getColumnModel();
        int columnCount = columnModel.getColumnCount();
        int columnWidth = Math.max(tableWidth / columnCount, 75);
        ;
        for (int i = 0; i < columnCount; i++) {
            TableColumn column = columnModel.getColumn(i);
            column.setPreferredWidth(columnWidth);
        }
        table.revalidate();
    }

    /**
     * Cette méthode a pour but de changer le nom des colonnes du tableau en
     * fonction du type de société et de remplir le tableau avec tous les
     * éléments de la collection
     */
    private void updateData() {
        switch (enumInstanceDeSociete) {
            case Client:
                this.data =
                        new Object[listeSocieteSelection.size()][10];
                columnNames = new String[]{"Identifiant", "Raison sociale",
                        "Numéro" + " de rue", "Nom de rue", "Code postal", "Ville",
                        "Téléphone", "Adresse mail", "Chiffre " + "d'affaires",
                        "Nombre " + "d'employés"};
                for (int i = 0; i < listeSocieteSelection.size();
                     i++) {
                    Client client = (Client) listeSocieteSelection.get(i);
                    this.data[i][0] = client.getIdentifiant();
                    this.data[i][1] = client.getRaisonSociale();
                    this.data[i][2] = client.getNumeroDeRue();
                    this.data[i][3] = client.getNomDeRue();
                    this.data[i][4] = client.getCodePostal();
                    this.data[i][5] = client.getVille();
                    this.data[i][6] = client.getTelephone();
                    this.data[i][7] = client.getAdresseMail();
                    this.data[i][8] = Tools.DECIMAL_FORMAT.format(client.getChiffreAffaires());
                    this.data[i][9] = client.getNombreEmployes();
                }
                break;
            case Prospect:
                this.data = new Object[listeSocieteSelection.size()][10];
                columnNames = new String[]{"Identifiant", "Raison sociale",
                        "Numéro" + " de rue", "Nom de rue", "Code postal", "Ville",
                        "Téléphone", "Adresse mail", "Date de " + "prospection",
                        "Prospect" + " intéressé"};
                for (int i = 0; i < listeSocieteSelection.size();
                     i++) {
                    Prospect prospect = (Prospect) listeSocieteSelection.get(i);
                    this.data[i][0] = prospect.getIdentifiant();
                    this.data[i][1] = prospect.getRaisonSociale();
                    this.data[i][2] = prospect.getNumeroDeRue();
                    this.data[i][3] = prospect.getNomDeRue();
                    this.data[i][4] = prospect.getCodePostal();
                    this.data[i][5] = prospect.getVille();
                    this.data[i][6] = prospect.getTelephone();
                    this.data[i][7] = prospect.getAdresseMail();
                    this.data[i][8] =
                            FormatterDate.convertiEtFormatDateEnChaine(
                                    prospect.getDateProsprection());
                    this.data[i][9] = prospect.getProspectInteresse();
                }
                break;
        }
    }
    //--------------------- ABSTRACT METHODS -----------------------------------
    //--------------------- STATIC - GETTERS - SETTERS -------------------------
    //--------------------- GETTERS - SETTERS ----------------------------------
    //--------------------- TO STRING METHOD------------------------------------
}
