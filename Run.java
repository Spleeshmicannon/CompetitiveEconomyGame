import Components.Economy;
import Components.Gui;
import Components.Network;
import Components.Resource;

import javax.swing.*;
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
        playEcon.addNeed(Resource.getRandomResource());
        playEcon.addProduct(Resource.getRandomResource(playEcon.getNeeds()[0].resource));
    }

    public static void CreateGui() {
        win = new Gui(WIDTH,HEIGHT); // initialising the window

        // checking if the user wants to play online (0 if yes)
        if(JOptionPane.showConfirmDialog(win.getFrame(),"Do you want to play online?") == 0) CreateNetwork();
        // adding window elements
        labels = new JLabel[]{ // creating various text elements
                Gui.CreateText("Economy", WIDTH / 32, 40, 170, 80, 40),
                Gui.CreateText("Needs:" + Resource.ResourceToString(playEcon.getNeeds()[0].resource), WIDTH / 32, 120, 170, 40, 20),
                Gui.CreateText("Amount:" + playEcon.getNeeds()[0].amount, WIDTH / 32, 160, 170, 40, 20),
                Gui.CreateText("Products:" + Resource.ResourceToString(playEcon.getProducts()[0].resource), WIDTH / 32, 200, 170, 40, 20),
                Gui.CreateText("Amount:" + playEcon.getProducts()[0].amount, WIDTH / 32, 240, 170, 40, 20),
                Gui.CreateText("Deficit:" + playEcon.deficit, WIDTH / 32, 280, 170, 40, 20),
                Gui.CreateText("Reliability:" + playEcon.reliability, WIDTH / 32, 320, 170, 40, 20)
        };

        JButton[] btns = new JButton[] { // creating various buttons
                Gui.CreateButton("Buy Needs", WIDTH / 2, 80, 120, 50, e -> playEcon.BuyNeeds()),
                Gui.CreateButton("Sell Products", WIDTH / 2, 80 + 53, 120, 50, e -> playEcon.SellProducts()),
                Gui.CreateButton("Print Money", WIDTH / 2, 80 + 53*2, 120, 50, e -> playEcon.PrintMoney()),
                Gui.CreateButton("Ad Campaign", WIDTH / 2, 80 + 53*3, 120, 50, e -> playEcon.AdCampaign())
        };

        for (JLabel label : labels) win.add(label); // adding the labels to the window
        for (JButton btn : btns) win.add(btn); // adding the buttons to the window
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
        labels[1].setText("Needs:" + Resource.ResourceToString(playEcon.getNeeds()[0].resource));
        labels[2].setText("Amount:" + playEcon.getNeeds()[0].amount);
        labels[3].setText("Products:" + Resource.ResourceToString(playEcon.getProducts()[0].resource));
        labels[4].setText("Amount:" + playEcon.getProducts()[0].amount);
        labels[5].setText("Deficit:" + playEcon.deficit);
        labels[6].setText("Reliability:" + playEcon.reliability);
    }
}
