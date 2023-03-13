package fr.victork.java.View;

import javax.swing.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import java.awt.*;

public interface FrameInterface {

    default void windowPositionLowerRight(JFrame jFrame) {
        int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
        int windowWidth = jFrame.getWidth();
        int windowHeight = jFrame.getHeight();
        jFrame.setLocation(screenWidth - windowWidth, screenHeight - windowHeight);
    }

    default void initFrame(JFrame jFrame) {
        jFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        jFrame.setSize(650, 700);
        //jFrame.setMaximumSize(new Dimension(1000, 1000));
        windowPositionLowerRight(jFrame);
        jFrame.setDefaultLookAndFeelDecorated(true);
        try {
            UIManager.setLookAndFeel(new NimbusLookAndFeel());
        } catch (UnsupportedLookAndFeelException e) {
            throw new RuntimeException(e);
        }
        //jFrame.setJMenuBar(creerBarDeMenu(jFrame));
        jFrame.setVisible(true);
    }

  /*  default JMenuBar creerBarDeMenu(JFrame jFrame) {
        JMenuBar menuBar = new JMenuBar();
        JMenu mnuAccueil = new JMenu("Accueil");
        JMenu mnuQuitter = new JMenu("Quitter");
        mnuAccueil.addActionListener(e -> new AccueilFrame());
        mnuQuitter.addActionListener(e -> jFrame.dispose());
        menuBar.add(mnuAccueil);
        menuBar.add(mnuQuitter);
        return menuBar;
    }*/
}
