package Components.Gui;

import javax.swing.*;
import javax.swing.plaf.metal.MetalBorders;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class CustomTableColor {
    public static class Renderer extends JTextPane implements TableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText(value.toString());
            setEditable(false);
            setBackground(new Color(60, 60, 44));
            setForeground(new Color(255, 255, 219));
            setFont(new Font(this.getFont().getName(), Font.PLAIN, 16));
            return this;
        }
    }

    public static class Editor extends DefaultCellEditor {
        public Editor(JCheckBox checkBox) {
            super(checkBox);
        }
    }
}
