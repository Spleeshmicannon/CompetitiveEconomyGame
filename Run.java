import Components.Network;
import Components.DataStructures.*;
import Components.Gui.*;

import javax.swing.*;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.util.Random;

public class Run {
    static Gui win; // the window object
    static Economy playEcon; // the players economy
    static Boolean Online; // are you playing online?
    static Network net; // the network object for online play
    static JLabel[] labels; // the text labels
    static JTable table; // the table with the data
    static GlobalPrices globalPrices; // the HashMap of global prices
    final static int WIDTH = 1080; // the windows width
    final static int HEIGHT = 720; // the windows height

    public static void main(String[] args) {
        Online = false; // online is set to false by default
        CreateEconomy(); // initialises the economy
        SetupGlobalMarket(); // initialises the prices of resources
        CreateGui(); // initialises the gui
        new Thread(Run::CycleEconomy).start(); // the main loop running in a thread - this is not yet required
    }

    /**
     * Initialises the economy named 'playEcon'.
     */
    public static void CreateEconomy() {
        // initialises the economy with a reliability of 30 and deficit of 1000
        playEcon = new Economy(30, 1000);

        // gets 4 random ints
        int[] needInts = fourRandomInts();

        // randomly chooses what needs/products the economy has
        if(new Random().nextBoolean()) {
            playEcon.addNeeds(needInts[0], 0, needInts[2], 0);
            playEcon.addProducts(0, needInts[1], 0, needInts[3]);
        } else {
            playEcon.addNeeds(0, needInts[1], 0, needInts[3]);
            playEcon.addProducts(needInts[0], 0, needInts[2], 0);
        }
    }


    /**
     * Initialises the GUI with the variable named win (and the Gui object).
     */
    public static void CreateGui() {
        win = new Gui(WIDTH,HEIGHT); // initialising the window

        // checking if the user wants to play online (0 if yes)
        if(JOptionPane.showConfirmDialog(win.getFrame(),"Do you want to play online?") == 0) CreateNetwork();

        // adding window elements
        labels = new JLabel[]{ // creating various text elements
                Gui.CreateText("Economy", WIDTH / 32, 40, 170, 80, 40),
                Gui.CreateText("Deficit:" + playEcon.deficit, WIDTH / 32, 280, 170, 40, 20),
                Gui.CreateText("Reliability:" + playEcon.reliability, WIDTH / 32, 320, 170, 40, 20)
        };

        // creates the JTable, complete with data and column headings.
        table = new JTable(new Object[][]{
            {
                "Food",
                "Buy/Sell",
                Integer.toString(playEcon.getNeeds().get(Resource.food)),
                Integer.toString(playEcon.getProducts().get(Resource.food)),
                globalPrices.getGlobalPrices(Resource.food)
            }, {
                "Minerals",
                "Buy/Sell",
                Integer.toString(playEcon.getNeeds().get(Resource.minerals)),
                Integer.toString(playEcon.getProducts().get(Resource.minerals)),
                globalPrices.getGlobalPrices(Resource.minerals)
            }, {
                "Technology",
                "Buy/Sell",
                Integer.toString(playEcon.getNeeds().get(Resource.technology)),
                Integer.toString(playEcon.getProducts().get(Resource.technology)),
                globalPrices.getGlobalPrices(Resource.technology)
            }, {
                "Medicine",
                "Buy/Sell",
                Integer.toString(playEcon.getNeeds().get(Resource.medicine)),
                Integer.toString(playEcon.getProducts().get(Resource.medicine)),
                globalPrices.getGlobalPrices(Resource.medicine)
                }
            }, new String[]{" ", "Buy/Sell", "Needs", "Products", "Prices"}
        );

        table.setFont((new Font(table.getFont().getName(), Font.PLAIN, 20))); // sets the text size
        table.setRowHeight(25); // sets the height of each cell
        setTableColumnWidths(table, 200, 200, 100, 100, 100, 100); // sets the width of each cell

        // for rendering buttons, note that this is only for the 'Buy/Sell' column
        table.getColumn("Buy/Sell").setCellRenderer(new TableButton.Renderer()); // a button cell renderer
        TableButton.Editor editor = new TableButton.Editor(new JCheckBox(), e -> { }); // a placeholder editor values
        editor.addActionListner(e -> { // adding an action listener with the editor.rowNumber variable
            System.out.println(editor.rowNumber);
            switch(editor.rowNumber) { // based on the row, what the buttons do
                case 0:
                    if(playEcon.getNeeds().get(Resource.food) == 0) {
                        playEcon.BuyNeeds(globalPrices.getGlobalPrices(Resource.food), Resource.food);
                    } else {
                        playEcon.SellProducts(globalPrices.getGlobalPrices(Resource.food), Resource.food);
                    }
                    break;
                case 1:
                    if(playEcon.getNeeds().get(Resource.minerals) == 0) {
                        playEcon.BuyNeeds(globalPrices.getGlobalPrices(Resource.minerals), Resource.minerals);
                    } else {
                        playEcon.SellProducts(globalPrices.getGlobalPrices(Resource.minerals), Resource.minerals);
                    }
                    break;
                case 2:
                    if(playEcon.getNeeds().get(Resource.technology) == 0) {
                        playEcon.BuyNeeds(globalPrices.getGlobalPrices(Resource.technology), Resource.technology);
                    } else {
                        playEcon.SellProducts(globalPrices.getGlobalPrices(Resource.technology), Resource.technology);
                    }
                    break;
                case 3:
                    if(playEcon.getNeeds().get(Resource.medicine) == 0) {
                        playEcon.BuyNeeds(globalPrices.getGlobalPrices(Resource.medicine), Resource.medicine);
                    } else {
                        playEcon.SellProducts(globalPrices.getGlobalPrices(Resource.medicine), Resource.medicine);
                    }
                    break;
                default: break;
            }
        });
        table.getColumn("Buy/Sell").setCellEditor(editor); // assigning the button editor to the table

        // assigning the table to a JScrollPane
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(WIDTH / 32, 140, 400, 130); // setting the location and size of the pane

        JButton[] btns = new JButton[] { // creating various buttons
                Gui.CreateButton("Print Money", WIDTH / 2, 80 + 53*2, 120, 50, e -> playEcon.PrintMoney()),
                Gui.CreateButton("Ad Campaign", WIDTH / 2, 80 + 53*3, 120, 50, e -> playEcon.AdCampaign())
        };

        for (JButton btn : btns) win.add(btn); // adding the buttons to the window
        for (JLabel label : labels) win.add(label); // adding the labels to the window
        win.add(scrollPane); // adding the scrollPane (with the table) to the window
        win.showScreen(); // showing all elements
    }

    /**
     * Initialises the network whether that's from a hosting or connecting standpoint.
     * TODO: To be implemented
     */
    public static void CreateNetwork() {
        Online = true;
        net = new Network(true);
    }

    /**
     * Sets up the global market for online/offline play.
     * TODO: Implement online stuff
     */
    public static void SetupGlobalMarket() {
        globalPrices = new GlobalPrices();
        globalPrices.setGlobalPrices();
    }

    /**
     * Cycles through the economy once every second.
     */
    public static void CycleEconomy() {
        while(playEcon.deficit >= -100) {
            sleep(1000); // sleep for one second
            playEcon.CycleEconomy(); // cycles through the economy (i.e. changing various values)
            RefreshGui(); // refreshing the GUI with the new values that were assigned
        }
    }

    /**
     * Refreshes the GUI with new variable values.
     */
    public static void RefreshGui() {
        labels[1].setText("Deficit:" + playEcon.deficit); // updates Deficit variable
        labels[2].setText("Reliability:" + playEcon.reliability); // updates the reliability variable

        TableModel tableModel = table.getModel(); // makes an editable table model

        // setting need values
        tableModel.setValueAt(Integer.toString(playEcon.getNeeds().get(Resource.food)), 0, 2);
        tableModel.setValueAt(Integer.toString(playEcon.getNeeds().get(Resource.minerals)), 1, 2);
        tableModel.setValueAt(Integer.toString(playEcon.getNeeds().get(Resource.technology)), 2, 2);
        tableModel.setValueAt(Integer.toString(playEcon.getNeeds().get(Resource.medicine)), 3, 2);

        // setting product values
        tableModel.setValueAt(Integer.toString(playEcon.getProducts().get(Resource.food)), 0, 3);
        tableModel.setValueAt(Integer.toString(playEcon.getProducts().get(Resource.minerals)), 1, 3);
        tableModel.setValueAt(Integer.toString(playEcon.getProducts().get(Resource.technology)), 2, 3);
        tableModel.setValueAt(Integer.toString(playEcon.getProducts().get(Resource.medicine)), 3, 3);

        // assigning model with updated values to the table
        table.setModel(tableModel);
    }

    /**
     * Sets table column widths
     * @param table the JTable where the widths get set.
     * @param widths the widths to be set too
     */
    public static void setTableColumnWidths(JTable table, int... widths) {
        TableColumnModel columnModel = table.getColumnModel();
        for (int i = 0; i < widths.length; i++) {
            if (i < columnModel.getColumnCount()) {
                columnModel.getColumn(i).setMaxWidth(widths[i]);
            }
            else break;
        }
    }

    /**
     * Handles Thread.Sleep to make use easier.
     * @param milliseconds the milliseconds to be slept for
     */
    public static void sleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns 4 random ints.
     * @return an int[4] of random ints
     */
    public static int[] fourRandomInts() {
        return new int[] {(int) Math.round(Math.random()*10),
                (int) Math.round(Math.random()*10),
                (int) Math.round(Math.random()*10),
                (int) Math.round(Math.random()*10)
        };
    }
}
