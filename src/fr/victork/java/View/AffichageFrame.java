package fr.victork.java.View;

import fr.victork.java.Entity.*;
import fr.victork.java.Exception.ExceptionEntity;
import fr.victork.java.Tools.FormatterDate;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Collections;

public class AffichageFrame extends MainFrame {
    //--------------------- CONSTANTS ------------------------------------------
    //--------------------- STATIC VARIABLES -----------------------------------
    //--------------------- INSTANCE VARIABLES ---------------------------------
    private JTable table;
    protected EnumInstanceDeSociete enumInstanceDeSociete;
    protected String[] columnNames = {};
    private Object[][] data;
    private JScrollPane scrollPane;
    private Societe societeSelectionne;
    private JButton btnCreer, btnSupprimer, btnEditer;

    //--------------------- CONSTRUCTORS ---------------------------------------
    public AffichageFrame(EnumInstanceDeSociete enumInstanceDeSociete, int largeurFenetre, int hauteurFenetre,
                          int positionX, int positionY, boolean pleinEcran)
            throws ExceptionEntity {
        super(largeurFenetre, hauteurFenetre, positionX, positionY, pleinEcran);
        setTitle("Liste des " + enumInstanceDeSociete.name());
        this.enumInstanceDeSociete = enumInstanceDeSociete;
        updateData();
        this.table = new JTable(this.data, this.columnNames);
        this.table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        this.table.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
        this.table.setForeground(Color.BLACK);
        this.table.setBackground(new Color(180, 180, 180));
        this.table.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        this.table.setRowHeight(30);
        table.setIntercellSpacing(new Dimension(0, 1));
        table.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        scrollPane = new JScrollPane(this.table);
        super.panCentral.setLayout(new BorderLayout());
        super.panCentral.add(scrollPane, BorderLayout.CENTER);

        table.setAutoCreateRowSorter(true);
        TableRowSorter<TableModel> sorter = (TableRowSorter<TableModel>) table.getRowSorter();
        sorter.setSortKeys(Collections.singletonList(new RowSorter.SortKey(1, SortOrder.ASCENDING)));
        table.setRowSorter(sorter);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                updateColumnWidths();
            }
        });

        addHierarchyBoundsListener(new HierarchyBoundsAdapter() {
            @Override
            public void ancestorResized(HierarchyEvent e) {
                super.ancestorResized(e);
                updateColumnWidths();
            }
        });
        setSize(largeurFenetre, hauteurFenetre);
        setLocation(positionX, positionY);
        super.estEnPleinEcran = pleinEcran;
        if (super.estEnPleinEcran) {
            setExtendedState(JFrame.MAXIMIZED_BOTH);
        }
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = table.getSelectedRow();
                    Object value = table.getValueAt(selectedRow, 0);
                    int id = ((Integer) value).intValue();
                    if (selectedRow != -1) {
                        etatsBoutons(true);
                        switch (enumInstanceDeSociete) {
                            case Client:
                                for (Client client : CollectionClients.getCollection()
                                ) {
                                    if (client.getIdentifiant() == id) {
                                        societeSelectionne = client;
                                    }
                                }
                                break;
                            case Prospect:
                                for (Prospect prospect : CollectionProspects.getCollection()
                                ) {
                                    if (prospect.getIdentifiant() == id) {
                                        societeSelectionne = prospect;
                                    }
                                }
                                break;
                        }
                    }
                }
            }
        });

        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    System.out.println(societeSelectionne.getRaisonSociale());
                }
            }
        });
        btnSupprimer = createButton("Supprimer");
        btnEditer = createButton("Modifier");
        btnCreer = createButton("Créer");
        JPanel panBtnCrud = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panBtnCrud.add(btnSupprimer);
        panBtnCrud.add(btnEditer);
        panBtnCrud.add(btnCreer);
        super.panCentral.add(panBtnCrud, BorderLayout.SOUTH);
        etatsBoutons(false);

        setVisible(true);

        btnEditer.addActionListener(e -> {
            try {
                this.dispose();
                new FormFrame(societeSelectionne, EnumCRUD.UPDATE, super.largeur, super.hauteur,
                        super.x, super.y, super.estEnPleinEcran);
            } catch (ExceptionEntity ex) {
                throw new RuntimeException(ex);
            }
        });

        btnSupprimer.addActionListener(e -> {
            try {
                this.dispose();
                new FormFrame(societeSelectionne, EnumCRUD.DELETE, super.largeur, super.hauteur,
                        super.x, super.y, super.estEnPleinEcran);
            } catch (ExceptionEntity ex) {
                throw new RuntimeException(ex);
            }
        });

        btnCreer.addActionListener(e -> {
            this.dispose();
            new FormFrame(enumInstanceDeSociete, EnumCRUD.CREATE, super.largeur, super.hauteur,
                    super.x, super.y, super.estEnPleinEcran);
        });

        //pack();
    }

    //--------------------- STATIC METHODS -------------------------------------
    //--------------------- INSTANCE METHODS -----------------------------------
    private void etatsBoutons(boolean etat) {
        btnEditer.setEnabled(etat);
        btnSupprimer.setEnabled(etat);
    }

    public void updateEnumInstanceDeSociete(EnumInstanceDeSociete enumInstanceDeSociete) throws ExceptionEntity {
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

    private void updateData() {
        switch (enumInstanceDeSociete) {
            case Client:
                this.data = new Object[CollectionClients.getCollection().size()][9];
                columnNames = new String[]{"Identifiant", "Raison sociale", "Numéro de rue", "Code postal",
                        "Ville", "Téléphone", "Adresse mail", "Chiffre d'affaires", "Nombre d'employés"};
                for (int i = 0; i < CollectionClients.getCollection().size(); i++) {
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
                this.data = new Object[CollectionProspects.getCollection().size()][9];
                columnNames = new String[]{"Identifiant", "Raison sociale", "Numéro de rue", "Code postal",
                        "Ville", "Téléphone", "Adresse mail", "Date de prospection", "Prospect intéressé"};
                for (int i = 0; i < CollectionProspects.getCollection().size(); i++) {
                    Prospect prospect = CollectionProspects.getCollection().get(i);
                    this.data[i][0] = prospect.getIdentifiant();
                    this.data[i][1] = prospect.getRaisonSociale();
                    this.data[i][2] = prospect.getNumeroDeRue();
                    this.data[i][3] = prospect.getCodePostal();
                    this.data[i][4] = prospect.getVille();
                    this.data[i][5] = prospect.getTelephone();
                    this.data[i][6] = prospect.getAdresseMail();
                    this.data[i][7] = FormatterDate.convertiEtFormatDateEnChaine(prospect.getDateProsprection());
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
