import Components.Economy;
import Components.Gui;
import Components.Network;
import Components.Resource;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Run {
    static Gui win; // the window object
    static Economy playEcon; // the players economy
    static Boolean Online; // are you playing online?
    static JLabel[] labels; // the text labels
    final static int WIDTH = 1080; // the windows width
    final static int HEIGHT = 720; // the windows height

    public static void main(String[] args) {
        Online = false;
        CreateEconomy();
        CreateGui();
        new Thread(Run::CycleEconomy).start();
    }

    public static void CreateEconomy() {
        playEcon = new Economy(30, 1000);
        int[] needInts = fourRandomInts();
        int[] productInts = fourRandomInts();
        playEcon.addNeeds(productInts[0] >= needInts[0] ? needInts[0] : 0,
                productInts[1] >= needInts[1] ? needInts[1] : 0,
                productInts[2] >= needInts[2] ? needInts[2] : 0,
                productInts[3] >= needInts[3] ? needInts[3] : 0
        );
        playEcon.addProducts(productInts[0] >= needInts[0] ? productInts[0] : 0,
                productInts[1] >= needInts[1] ? productInts[1] : 0,
                productInts[2] >= needInts[2] ? productInts[2] : 0,
                productInts[3] >= needInts[3] ? productInts[3] : 0
        );
    }

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

        JTable table = new JTable(new String[][]{
                {"Needs Amounts", Integer.toString(playEcon.getNeeds()[Resource.ResourceToInt(Resource.food)].amount),
                        Integer.toString(playEcon.getNeeds()[Resource.ResourceToInt(Resource.minerals)].amount),
                        Integer.toString(playEcon.getNeeds()[Resource.ResourceToInt(Resource.technology)].amount),
                        Integer.toString(playEcon.getNeeds()[Resource.ResourceToInt(Resource.medicine)].amount)
                },
                {"Products Amounts", Integer.toString(playEcon.getProducts()[Resource.ResourceToInt(Resource.food)].amount),
                        Integer.toString(playEcon.getProducts()[Resource.ResourceToInt(Resource.minerals)].amount),
                        Integer.toString(playEcon.getProducts()[Resource.ResourceToInt(Resource.technology)].amount),
                        Integer.toString(playEcon.getProducts()[Resource.ResourceToInt(Resource.medicine)].amount)
                }
                }, new String[]{"","food", "minerals", "technology", "medicine"}
        );

        table.setBounds(WIDTH / 32, 140, 500, 140);

        JButton[] btns = new JButton[] { // creating various buttons
                Gui.CreateButton("Buy Needs", WIDTH / 2, 80, 120, 50, e -> playEcon.BuyNeeds(20, Resource.food)),
                Gui.CreateButton("Sell Products", WIDTH / 2, 80 + 53, 120, 50, e -> playEcon.SellProducts(20, Resource.food)),
                Gui.CreateButton("Print Money", WIDTH / 2, 80 + 53*2, 120, 50, e -> playEcon.PrintMoney()),
                Gui.CreateButton("Ad Campaign", WIDTH / 2, 80 + 53*3, 120, 50, e -> playEcon.AdCampaign())
        };

        for (JButton btn : btns) win.add(btn); // adding the buttons to the window
        for (JLabel label : labels) win.add(label); // adding the labels to the window
        win.add(table);
        win.showScreen(); // showing all elements
    }

    public static void CreateNetwork() {
        Online = true;
        Network net = new Network();
    }

    public static void CycleEconomy() {
        while(playEcon.deficit >= -100) {
            sleep(1000);
            playEcon.CycleEconomy();
            RefreshLabels();
        }
    }

    public static void sleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void RefreshLabels() {
        labels[1].setText("Deficit:" + playEcon.deficit);
        labels[2].setText("Reliability:" + playEcon.reliability);
    }

    public static int[] fourRandomInts() {
        return new int[] {(int) Math.round(Math.random()*10),
                (int) Math.round(Math.random()*10),
                (int) Math.round(Math.random()*10),
                (int) Math.round(Math.random()*10)
        };
    }
}
