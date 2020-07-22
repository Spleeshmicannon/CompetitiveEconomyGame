import Components.Economy;
import Components.Gui;
import Components.Network;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Run {
    static Gui win; // the window object
    static Economy playEcon; // the players economy
    static Boolean Online; // are you playing online?
    final static int WIDTH = 1080; // the windows width
    final static int HEIGHT = 720; // the windows height

    public static void main(String[] args) {
        Online = false;
        CreateEconomy();
        CreateGui();
        new Thread(Run::CycleEconomy).start();
        new Thread(Run::RefreshGui).start();
    }

    public static void CreateEconomy() {
        playEcon = new Economy(30, 1000);
        playEcon.addNeed(Economy.Resource.getRandomResource());
        playEcon.addProduct(Economy.Resource.getRandomResource(playEcon.getNeeds()[0]));
    }

    public static void CreateGui() {
        win = new Gui(WIDTH,HEIGHT); // initialising the window

        // checking if the user wants to play online (0 if yes)
        if(JOptionPane.showConfirmDialog(win.getFrame(),"Do you want to play online?") == 0) CreateNetwork();
        // adding window elements
        JLabel[] labels = new JLabel[]{ // creating various text elements
                Gui.CreateText("Economy", WIDTH / 32, 40, 170, 80, 40),
                Gui.CreateText("Needs:", WIDTH / 32, 120, 170, 40, 20),
                Gui.CreateText("Products:", WIDTH / 32, 160, 170, 40, 20),
                Gui.CreateText("Deficit:", WIDTH / 32, 200, 170, 40, 20),
                Gui.CreateText("Reliability:", WIDTH / 32, 240, 170, 40, 20)
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
            //Todo: do things?
        }
    }

    public static void RefreshGui() {
        while(playEcon.deficit >= -100) {
            JLabel[] labels = new JLabel[]{ // creating and updating text elements
                    // Todo: get economy text
                    Gui.CreateText("", WIDTH / 32, 120, 170, 40, 20),
                    Gui.CreateText("", WIDTH / 32, 160, 170, 40, 20),
                    Gui.CreateText("", WIDTH / 32, 200, 170, 40, 20),
                    Gui.CreateText("", WIDTH / 32, 240, 170, 40, 20)
            };
        }
    }
}
