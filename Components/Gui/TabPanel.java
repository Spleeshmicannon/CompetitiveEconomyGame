package Components.Gui;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class TabPanel extends JPanel {
    public List<JComponent> componentList;

    /**
     * Initialises the TabPanel, with desired settings extending from JPanel
     */
    public TabPanel() {
        super();

        this.setLayout(null);
        this.isDoubleBuffered();
        this.setBackground(new Color(81, 80, 66));
        //this.setLayout(new BorderLayout());

        componentList = new ArrayList<>();
    }

    /**
     * Graphically adds the given compnmonents to the panels
     * @param components components to add to the panel
     */
    public void addComponents(JComponent ...components) {
        for(JComponent component : components) {
            this.add(component);
            componentList.add(component);
        }
    }
}
