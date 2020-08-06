package Components.Gui;

import Components.DataStructures.ArrayMap;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Vector;

public class Gui extends JFrame{
    private JTabbedPane tabMenu;
    private ArrayMap<String> tabIndex;

    /**
     * the Gui constructor initialises the frame variable
     * @param width the window width
     * @param height the window height
     */
    public Gui(int width, int height) {
        UIManager.put("TabbedPane.selected", new Color(255, 255, 219));
        tabMenu = new JTabbedPane();
        tabIndex = new ArrayMap<>();

        this.setLayout(null);
        this.setSize(width, height);
        this.getContentPane().setBackground(new Color(88, 88, 61));
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    /**
     * hides/clears elements in the frame
     */
    public void clearScreen() {
        this.setVisible(false);
    }

    /**
     * shows elements in the frame
     */
    public void showScreen() {
        this.setVisible(true);
    }

    /**
     * Initialises the tab menu.
     * @param x the x location
     * @param y the y location
     * @param width the width of menu
     * @param height the height of menu
     */
    public void setupTabMenu(int x, int y, int width, int height) {
        tabMenu.setBounds(x,y,width,height);

        this.add(tabMenu);
    }

    public void setTabColor(String tabName, Color color) {
        tabMenu.setBackgroundAt(tabIndex.getIndex(tabName), color);
    }

    /**
     * Adds a tab to the menu.
     * @param title the title of the pane
     * @param panel the panel to be added to said pane
     */
    public void addPane(String title, JPanel panel) {
        tabMenu.addTab(title, panel);
        tabIndex.put(title);
    }

    /**
     * Adds a tab to the menu, also setting colour.
     * @param title the title of the pane
     * @param panel the panel to be added to said pane
     * @param color the color of the panel
     */
    public void addPane(String title, JPanel panel, Color color) {
        tabMenu.addTab(title, panel);
        tabIndex.put(title);
        tabMenu.setBackgroundAt(tabIndex.getIndex(title), color.darker());
    }

    /**
     * Creates a Button
     * @param text the button text
     * @param x the buttons x location
     * @param y the buttons y location
     * @param width the buttons width
     * @param height the buttons height
     * @param ac the actionListener for the button
     * @return the button
     */
    public static JButton CreateButton(String text, int x, int y, int width, int height, ActionListener ac) {
        JButton btn = new JButton(text);
        btn.setBounds(x,y,width,height);
        btn.setBackground(new Color(240, 240, 210));
        btn.addActionListener(ac);
        return btn;
    }

    /**
     * Creates a Button
     * @param text the button text
     * @param x the buttons x location
     * @param y the buttons y location
     * @param width the buttons width
     * @param height the buttons height
     * @return the button
     */
    public static JButton CreateButton(String text, int x, int y, int width, int height) {
        JButton btn = new JButton(text);
        btn.setBounds(x,y,width,height);
        btn.setBackground(new Color(240, 240, 210));
        return btn;
    }

    /**
     * Creates a Button. Doesn't set the bounds.
     * @param text the button text
     * @return the button
     */
    public static JButton CreateButton(String text, ActionListener ac) {
        JButton btn = new JButton(text);
        btn.addActionListener(ac);
        return btn;
    }

    /**
     * Creates a text area
     * @param text the content
     * @param x the x location
     * @param y the y location
     * @param size the size of the text
     * @return the JTextArea
     */
    public static JLabel CreateText(String text, int x, int y, int width, int height, int size) {
        JLabel Label = new JLabel();

        Label.setLocation(x,y);
        Label.setSize(width, height);
        Label.setText(text);
        Label.setForeground(new Color(255, 255, 219));
        Label.setFont(new Font(Label.getFont().getName(), Font.PLAIN, Math.min(size, Label.getHeight())));

        return Label;
    }
}
