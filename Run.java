import Components.DataStructures.Enums.Country;
import Components.DataStructures.Enums.Resource;
import Components.DataStructures.Enums.SocialClass;
import Components.Network;
import Components.DataStructures.*;
import Components.Gui.*;

import javax.swing.*;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.util.Map;
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

        // initialises class populations
        playEcon.addPeople();

        // gets 4 random ints
        int[] needInts = fourRandomInts();

        // randomly chooses what needs/products the economy has
        if(new Random().nextBoolean()) {
            playEcon.addNeeds(needInts[0], -1, needInts[2], -1);
            playEcon.addProducts(-1, needInts[1], -1, needInts[3]);
        } else {
            playEcon.addNeeds(-1, needInts[1], -1, needInts[3]);
            playEcon.addProducts(needInts[0], -1, needInts[2], -1);
        }
    }

    /**
     * Initialises the GUI with the variable named win (and the Gui object).
     */
    public static void CreateGui() {
        win = new Gui(WIDTH,HEIGHT); // initialising the window

        // checking if the user wants to play online (0 if yes)
        //TODO Implementation: if(JOptionPane.showConfirmDialog(win.getFrame(),"Do you want to play online?") == 0) CreateNetwork();

        initLabels(); // setting up the 'labels' variable
        initTable(); // setting up the 'table' variable

        // assigning the table to a JScrollPane
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(WIDTH / 32, 140, 400, 130); // setting the location and size of the pane

        initTabMenu(); // setting up the tabbed menu

        JButton[] btns = new JButton[] { // creating various buttons
                Gui.CreateButton("Print Money", WIDTH / 14 + 100, 280, 120, 35, e -> {
                    playEcon.PrintMoney();
                    RefreshGui();
                }),
                Gui.CreateButton("Ad Campaign", WIDTH / 14 + 100, 320, 120, 35, e -> {
                    playEcon.AdCampaign();
                    RefreshGui();
                })
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
     * Initialises the table.
     */
    public static void initLabels() {
        labels = new JLabel[]{ // creating various text elements
                Gui.CreateText(Country.getCountryString(Country.getRandomCountry()), WIDTH / 32, 40, 650, 80, 40),
                Gui.CreateText("Deficit:" + playEcon.deficit, WIDTH / 32, 280, 170, 40, 20),
                Gui.CreateText("Reliability:" + playEcon.reliability, WIDTH / 32, 320, 170, 40, 20),
                Gui.CreateText("Health:" + playEcon.Health, WIDTH / 32, 360, 170, 40, 20),
                Gui.CreateText("Royalty:" + playEcon.getPeopleInClass(SocialClass.Royalty), WIDTH / 32, 400, 170, 40, 20),
                Gui.CreateText("Aristocracy:" + playEcon.getPeopleInClass(SocialClass.Aristocrat), WIDTH / 32, 440, 170, 40, 20),
                Gui.CreateText("Proletariat:" + playEcon.getPeopleInClass(SocialClass.Proletariat), WIDTH / 32, 480, 170, 40, 20)
        };
    }

    /**
     * Initialises the table.
     */
    public static void initTable() {
        // creates the JTable, complete with data and column headings.
        int temp;
        table = new JTable(new Object[][]{
        {
                "Food",
                "Buy/Sell",
                (temp = playEcon.getNeeds().get(Resource.food)) == -1 ? "" : temp ,
                (temp = playEcon.getProducts().get(Resource.food)) == -1 ? "" : temp,
                globalPrices.getGlobalPrices(Resource.food)
        }, {
                "Minerals",
                "Buy/Sell",
                (temp = playEcon.getNeeds().get(Resource.minerals)) == -1 ? "" : temp ,
                (temp = playEcon.getProducts().get(Resource.minerals)) == -1 ? "" : temp,
                globalPrices.getGlobalPrices(Resource.minerals)
        }, {
                "Technology",
                "Buy/Sell",
                (temp = playEcon.getNeeds().get(Resource.technology)) == -1 ? "" : temp ,
                (temp = playEcon.getProducts().get(Resource.technology)) == -1 ? "" : temp,
                globalPrices.getGlobalPrices(Resource.technology)
        }, {
                "Medicine",
                "Buy/Sell",
                (temp = playEcon.getNeeds().get(Resource.medicine)) == -1 ? "" : temp ,
                (temp = playEcon.getProducts().get(Resource.medicine)) == -1 ? "" : temp,
                globalPrices.getGlobalPrices(Resource.medicine)
        }
        }, new String[]{" ", "Buy/Sell", "Needs", "Products", "Prices"}
        );

        table.setRowHeight(25); // sets the height of each cell
        setTableColumnWidths(table, 300, 150, 60, 60, 60); // sets the width of each cell


        // for rendering buttons, note that this is only for the 'Buy/Sell' column
        table.getColumn("Buy/Sell").setCellRenderer(new TableButton.Renderer()); // a button cell renderer
        table.getColumn(" ").setCellRenderer(new CustomTableColor.Renderer());
        table.getColumn("Needs").setCellRenderer(new CustomTableColor.Renderer());
        table.getColumn("Products").setCellRenderer(new CustomTableColor.Renderer());
        table.getColumn("Prices").setCellRenderer(new CustomTableColor.Renderer());
        TableButton.Editor editor = new TableButton.Editor(new JCheckBox(), e -> { }); // a placeholder editor values
        editor.addActionListner(e -> { // adding an action listener with the editor.rowNumber variable
            switch(editor.rowNumber) { // based on the row, what the buttons do
                case 0:
                    if(playEcon.getNeeds().get(Resource.food) != -1) {
                        globalPrices.incrementPrice(Resource.food, playEcon.getNeeds().get(Resource.food));
                        playEcon.BuyNeeds(globalPrices.getGlobalPrices(Resource.food), Resource.food);
                    } else {
                        globalPrices.incrementPrice(Resource.food, playEcon.getProducts().get(Resource.food)*-1);
                        playEcon.SellProducts(globalPrices.getGlobalPrices(Resource.food), Resource.food);
                    }
                    break;
                case 1:
                    if(playEcon.getNeeds().get(Resource.minerals) != -1) {
                        globalPrices.incrementPrice(Resource.minerals, playEcon.getNeeds().get(Resource.minerals));
                        playEcon.BuyNeeds(globalPrices.getGlobalPrices(Resource.minerals), Resource.minerals);
                    } else {
                        globalPrices.incrementPrice(Resource.minerals, playEcon.getProducts().get(Resource.minerals)*-1);
                        playEcon.SellProducts(globalPrices.getGlobalPrices(Resource.minerals), Resource.minerals);
                    }
                    break;
                case 2:
                    if(playEcon.getNeeds().get(Resource.technology) != -1) {
                        globalPrices.incrementPrice(Resource.technology, playEcon.getNeeds().get(Resource.technology));
                        playEcon.BuyNeeds(globalPrices.getGlobalPrices(Resource.technology), Resource.technology);
                    } else {
                        globalPrices.incrementPrice(Resource.technology, playEcon.getProducts().get(Resource.technology)*-1);
                        playEcon.SellProducts(globalPrices.getGlobalPrices(Resource.technology), Resource.technology);
                    }
                    break;
                case 3:
                    if(playEcon.getNeeds().get(Resource.medicine) != -1) {
                        globalPrices.incrementPrice(Resource.medicine, playEcon.getNeeds().get(Resource.medicine));
                        playEcon.BuyNeeds(globalPrices.getGlobalPrices(Resource.medicine), Resource.medicine);
                    } else {
                        globalPrices.incrementPrice(Resource.medicine, playEcon.getProducts().get(Resource.medicine)*-1);
                        playEcon.SellProducts(globalPrices.getGlobalPrices(Resource.medicine), Resource.medicine);
                    }
                    break;
                default: break;
            }
            RefreshGui();
        });
        table.getColumn("Buy/Sell").setCellEditor(editor); // assigning the button editor to the table
    }

    /**
     * Initialises the tabbed menu
     */
    public static void initTabMenu() {
        win.setupTabMenu(WIDTH / 2 - 50, 140, 550, 500);

        TabPanel politicsPanel = new TabPanel();
        PieChart governmentChart = new PieChart(Map.of(
                new Color(65, 85, 128), 50D,
                new Color(73, 127, 67), 20D,
                new Color(76, 130, 134), 5D,
                new Color(142, 64, 60), 25D
        ));

        governmentChart.setBounds(10, 20, 500, 500);
        governmentChart.finaliseSize();

        politicsPanel.addComponents(
            governmentChart
        );

        TabPanel productionPanel = new TabPanel();
        TabPanel militaryPanel = new TabPanel();
        TabPanel economyPanel = new TabPanel();


        win.addPane("Politics", politicsPanel, new Color(255, 255, 219));
        win.addPane("Production", productionPanel, new Color(255, 255, 219));
        win.addPane("Military", militaryPanel, new Color(255, 255, 219));
        win.addPane("Local Economy", economyPanel, new Color(255, 255, 219));
    }

    /**
     * Cycles through the economy once every second.
     */
    public static void CycleEconomy() {
        while(playEcon.deficit >= -100 || playEcon.Health <= 0) {
            sleep(1000); // sleep for one second
            playEcon.CycleEconomy(); // cycles through the economy (i.e. changing various values)
            globalPrices.CyclePrices(); // changes the prices incrementally and randomly
            RefreshGui(); // refreshing the GUI with the new values that were assigned
        }

        // some stuff may happen here

        if(playEcon.deficit <= -100 || playEcon.Health <= 0) { // if the while loop finished because of the deficit, tell the player they lost
            JOptionPane.showMessageDialog(win,"You lost");
        }
    }


    /**
     * Refreshes the GUI with new variable values.
     */
    public static void RefreshGui() {

        labels[1].setText("Deficit:" + playEcon.deficit); // updates Deficit variable
        labels[2].setText("Reliability:" + playEcon.reliability); // updates the reliability variable
        labels[3].setText("Health:" + playEcon.Health); // updates the health variable
        labels[4].setText("Royalty:" + playEcon.getPeopleInClass(SocialClass.Royalty)); // updates the royalty variable
        labels[5].setText("Aristocracy:" + playEcon.getPeopleInClass(SocialClass.Aristocrat)); // updates the aristocracy variable
        labels[6].setText("Proletariat:" + playEcon.getPeopleInClass(SocialClass.Proletariat)); // update the proletariat variable


        TableModel tableModel = table.getModel(); // makes an editable table model
        int temp;

        // setting need values
        tableModel.setValueAt((temp = playEcon.getNeeds().get(Resource.food)) == -1 ? "" : temp, 0, 2);
        tableModel.setValueAt((temp = playEcon.getNeeds().get(Resource.minerals)) == -1 ? "" : temp, 1, 2);
        tableModel.setValueAt((temp = playEcon.getNeeds().get(Resource.technology)) == -1 ? "" : temp, 2, 2);
        tableModel.setValueAt((temp = playEcon.getNeeds().get(Resource.medicine)) == -1 ? "" : temp, 3, 2);

        // setting product values
        tableModel.setValueAt((temp = playEcon.getProducts().get(Resource.food)) == -1 ? "" : temp, 0, 3);
        tableModel.setValueAt((temp = playEcon.getProducts().get(Resource.minerals)) == -1 ? "" : temp, 1, 3);
        tableModel.setValueAt((temp = playEcon.getProducts().get(Resource.technology)) == -1 ? "" : temp, 2, 3);
        tableModel.setValueAt((temp = playEcon.getProducts().get(Resource.medicine)) == -1 ? "" : temp, 3, 3);

        // setting price values
        tableModel.setValueAt(globalPrices.getGlobalPrices(Resource.food), 0, 4);
        tableModel.setValueAt(globalPrices.getGlobalPrices(Resource.minerals), 1, 4);
        tableModel.setValueAt(globalPrices.getGlobalPrices(Resource.technology), 2, 4);
        tableModel.setValueAt(globalPrices.getGlobalPrices(Resource.medicine), 3, 4);

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
