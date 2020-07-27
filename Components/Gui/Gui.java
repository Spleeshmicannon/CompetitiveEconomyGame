package Components.Gui;

import javax.swing.*;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.ActionListener;

public class Gui {
    private Frame frame;

    /**
     * the Gui constructor initialises the frame variable
     * @param width the window width
     * @param height the window height
     */
    public Gui(int width, int height) {
        frame = new Frame();
        frame.setLayout(null);
        frame.setSize(width, height);
        frame.setBackground(Color.LIGHT_GRAY);
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
