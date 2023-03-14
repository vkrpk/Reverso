/**
 * @author Victor K
 * @version 1.00
 * Cette classe représente le squelette de toutes les fenêtres de l'application
 * Cette classe implémente des écouteurs d'évènements nécessaires à toutes
 * les fenêtres de l'application
 */
package fr.victork.java.View;

import fr.victork.java.Tools.Tools;

import javax.swing.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class MainFrame extends JFrame implements Tools {
    //--------------------- CONSTANTS ------------------------------------------
    //--------------------- STATIC VARIABLES -----------------------------------
    //--------------------- INSTANCE VARIABLES ---------------------------------
    protected JButton btnAccueil, btnQuitter;
    protected JPanel contentPane, panNorth, panSouth, panCentral;
    protected int largeur, hauteur, x, y;
    protected boolean estEnPleinEcran;

    //--------------------- CONSTRUCTORS ---------------------------------------

    /**
     * @param largeurFenetre int Largeur de la fenêtre
     * @param hauteurFenetre int Hauteur de la fenêtre
     * @param positionX      int Position X sur l'écran
     * @param positionY      int Position Y sur l'écran
     * @param pleinEcran     Boolean True si le mode plein écran est activé
     */
    public MainFrame(int largeurFenetre, int hauteurFenetre, int positionX,
            int positionY, boolean pleinEcran) {
        setupGUIAllFrames(pleinEcran);

        // Ferme la fenêtre actuelle et ouvre la page d'accueil
        btnAccueil.addActionListener(e -> {
            this.dispose();
            new AccueilFrame(MainFrame.this.largeur, MainFrame.this.hauteur,
                    MainFrame.this.x, MainFrame.this.y,
                    MainFrame.this.estEnPleinEcran);
        });
        // Ferme l'application
        btnQuitter.addActionListener(e -> this.dispose());

       /* Mémorise et stocke la position x et y, la largeur et
        la hauteur de la fenêtre à chaque nouveau déplacement */
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

        // Mémorise le passage en mode plein écran dans une variable
        addWindowStateListener(e -> {
            if ((e.getNewState() & JFrame.MAXIMIZED_BOTH) ==
                    JFrame.MAXIMIZED_BOTH) {
                estEnPleinEcran = true;
            } else {
                estEnPleinEcran = false;
            }
        });
    }

    /**
     * Cette méthode crée une nouvelle instance de JButton avec un style
     * particulier
     *
     * @param text Chaîne de caractère du bouton
     * @return Retourne une instance de la classe JButton
     */
    public JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
        button.setForeground(Color.BLACK);
        button.setBackground(new Color(180, 180, 180));
        button.setFocusPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return button;
    }

    /**
     * Cette méthode crée une nouvelle instance de JTextField avec un style
     * particulier
     *
     * @param text    Chaîne de caractère du champ
     * @param columns Longueur du champ
     * @return Retourne une instance de la classe JTextField
     */
    public JTextField createJTextField(String text, int columns) {
        JTextField jTextField;
        if (Tools.controlStringIsNotEmpty(text)) {
            jTextField = new JTextField(text, columns);
        } else {
            jTextField = new JTextField(columns);
        }
        jTextField.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
        jTextField.setForeground(Color.BLACK);
        return jTextField;
    }

    /**
     * Cette méthode crée une nouvelle instance de JLabel avec un style
     * particulier
     *
     * @param text Chaîne de caractère du label
     * @return Retourne une instance de la classe JLabel
     */
    public JLabel createLabel(String text) {
        JLabel jLabel = new JLabel(text);
        jLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
        jLabel.setForeground(Color.BLACK);
        return jLabel;
    }

    /**
     * Cette méthode définit le thème de l'application, la disposition des
     * panels et des boutons communs à toutes les fenêtres
     *
     * @param pleinEcran Boolean
     */
    protected void setupGUIAllFrames(boolean pleinEcran) {
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

        panNorth = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 15));
        panSouth = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        btnAccueil = createButton("Accueil");
        btnQuitter = createButton("Quitter");

        panNorth.add(btnAccueil);
        panSouth.add(btnQuitter);

        contentPane.add(panNorth, BorderLayout.NORTH);
        contentPane.add(panCentral, BorderLayout.CENTER);
        contentPane.add(panSouth, BorderLayout.SOUTH);
        contentPane.revalidate();
        panCentral.repaint();

        if (this.getClass().getSimpleName().equals("AccueilFrame")) {
            panNorth.setVisible(false);
        }
    }

    /**
     * Cette méthode place la fenêtre en bas à droite de l'écran en
     * soustrayant la largeur et la hauteur de la fenêtre à la largeur et la
     * hauteur de la fenêtre
     */
    protected void windowPositionLowerRight() {
        int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
        int windowWidth = getWidth();
        int windowHeight = getHeight();
        setLocation(screenWidth - windowWidth, screenHeight - windowHeight);
    }
}
