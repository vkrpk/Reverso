/**
 * @author Victor K
 * @version 1.00
 * La classe CenterTableCellRenderer est une sous-classe de DefaultTableCellRenderer qui permet de centrer le contenu d'une cellule dans un JTable.
 */
package fr.victork.java.View;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

/**
 * La classe CenterTableCellRenderer est une sous-classe de DefaultTableCellRenderer qui permet de centrer le contenu d'une cellule dans un JTable.
 */
public class CenterTableCellRenderer extends DefaultTableCellRenderer {
    /**
     * redéfinition de la méthode parente pour centrer horizontalement le contenu de la cellule.
     *
     * @param table      the <code>JTable</code>
     * @param value      the value to assign to the cell at
     *                   <code>[row, column]</code>
     * @param isSelected true if cell is selected
     * @param hasFocus   true if cell has focus
     * @param row        the row of the cell to render
     * @param column     the column of the cell to render
     * @return
     */
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        ((JLabel) c).setHorizontalAlignment(SwingConstants.CENTER);
        return c;
    }
}

