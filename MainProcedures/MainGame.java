package MainProcedures;

import Components.Data.letters;
import Components.DataStructures.Enums.Country;
import Components.DataStructures.Enums.CreditRating;
import Components.DataStructures.Enums.Resource;
import Components.DataStructures.Enums.SocialClass;
import Components.DataStructures.Enums.CreditRating;
import Components.Network;
import Components.DataStructures.*;
import Components.Gui.*;

import javax.swing.*;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.util.*;

public class MainGame {
    static Gui win; // the window object

    static Economy playEcon; // the players economy
    static GlobalPrices globalPrices; // the HashMap of global prices

    static Boolean Online; // are you playing online?
    static Network net; // the network object for online play

    static JLabel[] labels; // the text labels
    static JTable table; // the table with the data

    // The panels for the GUI to be displayed
    static TabPanel politicsPanel = new TabPanel(); //TODO  SPLIT INTO LOCAL AND INTERNATIONAL
    static TabPanel economyPanel = new TabPanel();
    static TabPanel militaryPanel = new TabPanel();
    static TabPanel productionPanel = new TabPanel();
    static TabPanel educationPanel = new TabPanel();
    static TabPanel sciencePanel = new TabPanel();
    static TabPanel lettersPanel = new TabPanel();

    static TabPanel localPoliticsPanel = new TabPanel();
    static TabPanel intPoliticsPanel = new TabPanel();

    static HashMap<TabPanel, String> panelList = new HashMap<TabPanel, String>(); // initialises the HashMap for the above panels

    final static int WIDTH = 1080; // the windows width
    final static int HEIGHT = 720; // the windows height

    static CreditRating credit = CreditRating.AAA; // Initiates the country's credit rating

    static ArrayList<Integer> letterYValues = new ArrayList<Integer>(); // Initiates the list of co-ordinates for messages in letter tab

    /**
     * If you want to just start the game but not go through a main menu,
     * run this method.
     * @param args command line arguments
     */
    public static void main(String[] args) {

        // adds the given panels to the HashMap to be looped through when required
        panelList.put(politicsPanel, "Politics");
        panelList.put(economyPanel, "Economy");
        panelList.put(militaryPanel, "Military");
        panelList.put(productionPanel, "Production");
        panelList.put(educationPanel, "Education");
        panelList.put(sciencePanel, "Science");
        panelList.put(lettersPanel, "Letters");

        Online = false; // online is set to false by default
        CreateEconomy(); // initialises the economy
        SetupGlobalMarket(); // initialises the prices of resources
        CreateGui(); // initialises the gui
        CycleEconomy(); // cycles the economy
        endSequence(); // the final sequence in the game
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

    public static void buyOrSell(Resource r) {

        if(playEcon.getNeeds().get(r) != -1) {
            globalPrices.incrementPrice(r, playEcon.getNeeds().get(r));
            playEcon.BuyNeeds(globalPrices.getGlobalPrices(r), r);
        } else {
            globalPrices.incrementPrice(r, playEcon.getProducts().get(r)*-1);
            playEcon.SellProducts(globalPrices.getGlobalPrices(r), r);
        }
    }

    public static Object[] initResource(Resource r) {

        int temp;
        Object[] initialisation;
        initialisation = new Object[]
                {
                    r.toString(),
                    "Buy/Sell",
                    (temp = playEcon.getNeeds().get(r)) == -1 ? "" : temp,
                    (temp = playEcon.getProducts().get(r)) == -1 ? "" : temp,
                    globalPrices.getGlobalPrices(r)
                };

        return initialisation;
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

        table = new JTable(new Object[][]{
                initResource(Resource.food),
                initResource(Resource.minerals),
                initResource(Resource.technology),
                initResource(Resource.medicine)
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
                    buyOrSell(Resource.food);
                    break;

                case 1:
                    buyOrSell(Resource.minerals);
                    break;

                case 2:
                    buyOrSell(Resource.technology);
                    break;

                case 3:
                    buyOrSell(Resource.medicine);
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

        letterYValues.clear(); // Clears the record of y positions of letters

        // Adds text titles for each panel
        for (Map.Entry<TabPanel, String> panel : panelList.entrySet()) {
            panel.getKey().addComponents(
                    Gui.CreateText(panel.getValue(), 10, -130, 100, 300, 20));
        }

        // Creates the pie chart of current populations
        PieChart governmentChart = new PieChart(Map.of(
                SocialClass.classToColor(SocialClass.Proletariat), playEcon.getPeopleInClass(SocialClass.Proletariat),
                SocialClass.classToColor(SocialClass.Aristocrat), playEcon.getPeopleInClass(SocialClass.Aristocrat),
                SocialClass.classToColor(SocialClass.Royalty), playEcon.getPeopleInClass(SocialClass.Royalty)
        ));

        // adds the pie chart to the GUI
        politicsPanel.add(governmentChart);
        governmentChart.setBounds(10, 20, 500, 500);
        governmentChart.finaliseSize();

        // Sets up the letters pane with randomly place letters
        Random rand = new Random();
        for(int i = 0; i < 12; ++i) {

            int yValue = (rand.nextInt(12)-6)*40;

            if (!letterYValues.contains(yValue)) {
                lettersPanel.add(Gui.CreateText(letters.getRandomText(), rand.nextInt(4)*10, yValue, 1000, 500, 14));
                letterYValues.add(letterYValues.size(), yValue);
            }
        }

        // Adds the given panels to the GUI to be displayed
        Color panelColor = new Color(255, 255, 219);
        for (Map.Entry<TabPanel, String> panel : panelList.entrySet()) {
            win.addPane(panel.getValue(), panel.getKey(), panelColor);
        }
    }

    /**
     * Cycles through the economy once every second.
     */
    public static void CycleEconomy() {
        while(playEcon.deficit >= -1000 & playEcon.Health > 0) {
            sleep(1000); // sleep for one second
            playEcon.CycleEconomy(); // cycles through the economy (i.e. changing various values)
            globalPrices.CyclePrices(); // changes the prices incrementally and randomly
            RefreshGui(); // refreshing the GUI with the new values that were assigned

            if(playEcon.deficit <= -1000 || playEcon.Health <= 0) { // if the while loop finished because of the deficit, tell the player they lost
                JOptionPane.showMessageDialog(win,"You lost");
                endSequence();
        }

        // some stuff may happen here
        }
    }


    /**
     * Refreshes the GUI with new variable values.
     */
    public static void RefreshGui() {


        letterYValues.clear(); // Clears the record of y positions of letters

        // updating various Labels/text
        labels[1].setText("Deficit:" + playEcon.deficit); // updates Deficit variable
        labels[2].setText("Reliability:" + playEcon.reliability); // updates the reliability variable
        labels[3].setText("Health:" + playEcon.Health); // updates the health variable
        labels[4].setText("Royalty:" + playEcon.getPeopleInClass(SocialClass.Royalty)); // updates the royalty variable
        labels[5].setText("Aristocracy:" + playEcon.getPeopleInClass(SocialClass.Aristocrat)); // updates the aristocracy variable
        labels[6].setText("Proletariat:" + playEcon.getPeopleInClass(SocialClass.Proletariat)); // update the proletariat variable

        // updating the pie chart - this doesn't really work at the moment
        PieChart governmentChart = new PieChart(Map.of(
                SocialClass.classToColor(SocialClass.Proletariat), playEcon.getPeopleInClass(SocialClass.Proletariat),
                SocialClass.classToColor(SocialClass.Aristocrat), playEcon.getPeopleInClass(SocialClass.Aristocrat),
                SocialClass.classToColor(SocialClass.Royalty), playEcon.getPeopleInClass(SocialClass.Royalty)
        ));

        // Re-initialising the politics panels
        governmentChart.setBounds(10, 20, 500, 500);
        governmentChart.finaliseSize();

        politicsPanel.remove(0); // breaks all other stuff in the gui
        politicsPanel.add(governmentChart);

        // resetting and updating the letters panel
        lettersPanel.removeAll();

        Random rand = new Random();
        for(int i = 0; i < 12; ++i) {
            int yValue = (rand.nextInt(12) - 6) * 40;

            if (!letterYValues.contains(yValue)) {
                lettersPanel.add(Gui.CreateText(letters.getRandomText(), rand.nextInt(4) * 10, yValue, 1000, 500, 14));
                letterYValues.add(letterYValues.size(), yValue);
            }
        }

        // updating all panels
        for (Map.Entry<TabPanel, String> panel : panelList.entrySet()) {
            panel.getKey().updateUI();
        }

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
     * Meant to be a little text box with "Dear Ms President, " and allows you
     * to type a message before closing. Can't get anything to work though.
     */
    public static void endSequence() {

        // Clears all objects from the screen
        win.removeAll();
        win.setBackground(new Color(60, 60, 30));

        // Creates a final prompt to write a message for closing office
        JLabel text = new JLabel();
        text.setText("Dear Ms President, ");
        text.setBounds(WIDTH/32, 160, 500, 100);
        politicsPanel.add(text);

        // Exits Application
        System.exit(0);
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
