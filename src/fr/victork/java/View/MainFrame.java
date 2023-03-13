package fr.victork.java.View;

import fr.victork.java.Tools.ControlString;

import javax.swing.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import java.awt.*;
import java.awt.event.*;

public class MainFrame extends JFrame {
    //--------------------- CONSTANTS ------------------------------------------
    //--------------------- STATIC VARIABLES -----------------------------------
    //--------------------- INSTANCE VARIABLES ---------------------------------
    protected JButton btnAccueil, btnQuitter;
    protected JPanel contentPane, panNorth, panSouth, panCentral;
    protected int largeur, hauteur, x, y;
    protected boolean estEnPleinEcran;

    //--------------------- CONSTRUCTORS ---------------------------------------
    public MainFrame(int largeurFenetre, int hauteurFenetre, int positionX, int positionY, boolean pleinEcran) {
        this.estEnPleinEcran = pleinEcran;
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 750);
        setMaximumSize(new Dimension(1920, 1080));
        setDefaultLookAndFeelDecorated(true);
        try {
            UIManager.setLookAndFeel(new NimbusLookAndFeel());
        } catch (UnsupportedLookAndFeelException e) {
            throw new RuntimeException(e);
        }
        contentPane = (JPanel) getContentPane();
        panCentral = new JPanel();
        creerBarDeMenu();
        contentPane.add(panNorth, BorderLayout.NORTH);
        contentPane.add(panCentral, BorderLayout.CENTER);
        contentPane.add(panSouth, BorderLayout.SOUTH);
        contentPane.revalidate();
        panCentral.repaint();

        if (this.getClass().getSimpleName().equals("AccueilFrame")) {
            panNorth.setVisible(false);
        }

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                largeur = getWidth();
                hauteur = getHeight();
            }

            public void componentMoved(ComponentEvent e) {
                x = getX();
                y = getY();
            }
        });

        addWindowStateListener(new WindowStateListener() {
            @Override
            public void windowStateChanged(WindowEvent e) {
                if ((e.getNewState() & JFrame.MAXIMIZED_BOTH) == JFrame.MAXIMIZED_BOTH) {
                    estEnPleinEcran = true;
                } else {
                    estEnPleinEcran = false;
                }
            }
        });
    }

    public JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
        button.setForeground(Color.BLACK);
        button.setBackground(new Color(180, 180, 180));
        button.setFocusPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return button;
    }

    public JTextField createJTextField(String text, int columns) {
        JTextField jTextField;
        if (ControlString.controlString(text)) {
            jTextField = new JTextField(text, columns);
        } else {
            jTextField = new JTextField(columns);
        }
        jTextField.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
        jTextField.setForeground(Color.BLACK);
        return jTextField;
    }

    public JLabel createLabel(String text) {
        JLabel jLabel = new JLabel(text);
        jLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
        jLabel.setForeground(Color.BLACK);
        return jLabel;
    }

    protected void creerBarDeMenu() {
        panNorth = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 15));
        panSouth = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        btnAccueil = createButton("Accueil");
        btnQuitter = createButton("Quitter");
        btnAccueil.addActionListener(e -> {
            this.dispose();
            new AccueilFrame(MainFrame.this.largeur, MainFrame.this.hauteur, MainFrame.this.x, MainFrame.this.y,
                    MainFrame.this.estEnPleinEcran);
        });
        btnQuitter.addActionListener(e -> this.dispose());
        panNorth.add(btnAccueil);
        panSouth.add(btnQuitter);
    }

    protected void windowPositionLowerRight() {
        int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
        int windowWidth = getWidth();
        int windowHeight = getHeight();
        setLocation(screenWidth - windowWidth, screenHeight - windowHeight);
    }
}
