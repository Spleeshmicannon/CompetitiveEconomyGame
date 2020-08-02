package Components.Gui;

import javax.swing.*;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

public class Gui {
    private JFrame frame;
    private JTabbedPane tabMenu;

    /**
     * the Gui constructor initialises the frame variable
     * @param width the window width
     * @param height the window height
     */
    public Gui(int width, int height) {
        frame = new JFrame();
        frame.setLayout(null);
        frame.setSize(width, height);
        frame.setBackground(Color.LIGHT_GRAY);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    /**
     * gets the frame object
     * @return Frame
     */
    public Frame getFrame() {
        return frame;
    }

    /**
     * add component to the frame
     * @param component the component to be added
     */
    public void add(Component component) {
        frame.add(component);
    }

    /**
     * remove all elements in the frame
     */
    public void reset() {
        frame.removeAll();
    }

    /**
     * hides/clears elements in the frame
     */
    public void clearScreen() {
        frame.setVisible(false);
    }

    /**
     * shows elements in the frame
     */
    public void showScreen() {
        frame.setVisible(true);
    }

    /**
     * Initialises the tab menu.
     * @param x the x location
     * @param y the y location
     * @param width the width of menu
     * @param height the height of menu
     */
    public void setupTabMenu(int x, int y, int width, int height) {
        tabMenu = new JTabbedPane();
        tabMenu.setBounds(x,y,width,height);
        frame.add(tabMenu);
    }

    /**
     * Adds a tab to the menu.
     * @param title the title of the pane
     * @param panel the panel to be added to said pane
     */
    public void addPane(String title, JPanel panel) {
        tabMenu.addTab(title, panel);
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
    public static JButton CreateButton(String text, int x, int y, int width, int height, ActionListener ac) {
        JButton btn = new JButton(text);
        btn.setBounds(x,y,width,height);
        btn.addActionListener(ac);
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
        Label.setForeground(Color.BLACK);
        Label.setFont(new Font(Label.getFont().getName(), Font.PLAIN, Math.min(size, Label.getHeight())));

        return Label;
    }
}
