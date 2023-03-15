/**
 * @author Victor K
 * @version 1.00
 * Cette classe a pour but d'afficher toute la collection d'un type de
 * société sous la forme d'un tableau et de créer, supprimer ou éditer un
 * élément de cette liste
 */
package fr.victork.java.View;

import fr.victork.java.Entity.*;
import fr.victork.java.Exception.ExceptionEntity;
import fr.victork.java.Tools.FormatterDate;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.HierarchyBoundsAdapter;
import java.awt.event.HierarchyEvent;
import java.util.Collections;

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
            int positionY, boolean pleinEcran) {
        super(largeurFenetre, hauteurFenetre, positionX, positionY, pleinEcran);
        this.enumInstanceDeSociete = enumInstanceDeSociete;
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
                            for (Client client :
                                    CollectionClients.getCollection()) {
                                if (client.getIdentifiant() == id) {
                                    societeSelection = client;
                                }
                            }
                            break;
                        case Prospect:
                            for (Prospect prospect :
                                    CollectionProspects.getCollection()) {
                                if (prospect.getIdentifiant() == id) {
                                    societeSelection = prospect;
                                }
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
            try {
                this.dispose();
                new FormFrame(societeSelection, EnumCRUD.UPDATE, super.largeur,
                        super.hauteur, super.x, super.y, super.estEnPleinEcran);
            } catch (ExceptionEntity ex) {
                throw new RuntimeException(ex);
            }
        });

        /**
         * Ferme la fenêtre et affiche le formulaire de suppression pour la
         * ligne sélectionnée
         */
        btnSupprimer.addActionListener(e -> {
            try {
                this.dispose();
                new FormFrame(societeSelection, EnumCRUD.DELETE, super.largeur,
                        super.hauteur, super.x, super.y, super.estEnPleinEcran);
            } catch (ExceptionEntity ex) {
                throw new RuntimeException(ex);
            }
        });

        /**
         * Ferme la fenêtre et affiche le formulaire de création pour le type
         * de société actuellement affiché
         */
        btnCreer.addActionListener(e -> {
            this.dispose();
            new FormFrame(enumInstanceDeSociete, EnumCRUD.CREATE, super.largeur,
                    super.hauteur, super.x, super.y, super.estEnPleinEcran);
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
            EnumInstanceDeSociete enumInstanceDeSociete)
            throws ExceptionEntity {
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
                        new Object[CollectionClients.getCollection().size()][9];
                columnNames = new String[]{"Identifiant", "Raison sociale",
                        "Numéro" + " de rue", "Code postal", "Ville",
                        "Téléphone", "Adresse mail", "Chiffre " + "d'affaires",
                        "Nombre " + "d'employés"};
                for (int i = 0; i < CollectionClients.getCollection().size();
                     i++) {
                    Client client = CollectionClients.getCollection().get(i);
                    this.data[i][0] = client.getIdentifiant();
                    this.data[i][1] = client.getRaisonSociale();
                    this.data[i][2] = client.getNumeroDeRue();
                    this.data[i][3] = client.getCodePostal();
                    this.data[i][4] = client.getVille();
                    this.data[i][5] = client.getTelephone();
                    this.data[i][6] = client.getAdresseMail();
                    this.data[i][7] = client.getChiffreAffaires();
                    this.data[i][8] = client.getNombreEmployes();
                }
                break;
            case Prospect:
                this.data = new Object[CollectionProspects.getCollection()
                        .size()][9];
                columnNames = new String[]{"Identifiant", "Raison sociale",
                        "Numéro" + " de rue", "Code postal", "Ville",
                        "Téléphone", "Adresse mail", "Date de " + "prospection",
                        "Prospect" + " intéressé"};
                for (int i = 0; i < CollectionProspects.getCollection().size();
                     i++) {
                    Prospect prospect =
                            CollectionProspects.getCollection().get(i);
                    this.data[i][0] = prospect.getIdentifiant();
                    this.data[i][1] = prospect.getRaisonSociale();
                    this.data[i][2] = prospect.getNumeroDeRue();
                    this.data[i][3] = prospect.getCodePostal();
                    this.data[i][4] = prospect.getVille();
                    this.data[i][5] = prospect.getTelephone();
                    this.data[i][6] = prospect.getAdresseMail();
                    this.data[i][7] =
                            FormatterDate.convertiEtFormatDateEnChaine(
                                    prospect.getDateProsprection());
                    this.data[i][8] = prospect.getProspectInteresse();
                }
                break;
        }
    }
    //--------------------- ABSTRACT METHODS -----------------------------------
    //--------------------- STATIC - GETTERS - SETTERS -------------------------
    //--------------------- GETTERS - SETTERS ----------------------------------
    //--------------------- TO STRING METHOD------------------------------------
}
