package Components.Gui;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class TabPanel extends JPanel {
    public List<JComponent> componentList;

    public TabPanel() {
        super();

        this.setLayout(null);
        this.isDoubleBuffered();

        componentList = new ArrayList<>();
    }

    public void addComponents(JComponent ...components) {
        for(JComponent component : components) {
            this.add(component);
            componentList.add(component);
        }
    }
}
