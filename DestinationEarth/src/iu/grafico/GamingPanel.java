package iu.grafico;

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import logicaJogo.*;
import javax.imageio.*;
import logicaEstados.*;

public class GamingPanel extends JPanel implements Observer {

    ObservableGame game;
    GameOptionsPanel gameOptionsP;
    String[] jt;
    int jtTurn;

    JPanel jtP, infoP, diceShowPanel;
    JLabel x, infoL, diceValue;
    private GridBagConstraints c;

    @SuppressWarnings({"LeakingThisInConstructor", "OverridableMethodCallInConstructor"})
    GamingPanel(ObservableGame g) {
        game = g;
        game.addObserver(this);

        setupComponents();
        setupLayout();
        validate();
    }

    private void setupComponents() {
        setOpaque(false);
        //Journey Tracker Panel NORTH
        jtP = new JPanel(new FlowLayout(FlowLayout.LEFT));
        jtP.setPreferredSize(new Dimension(100, 30));
        jtP.setOpaque(false);
        jt = game.getJourneyTacker();
        x = new JLabel("Journey Tracker: ");
        x.setOpaque(false);
        x.setFont(new Font("Arial Black", Font.PLAIN, 16));
        x.setForeground(Color.red);
        jtP.add(x);
        for (int i = 0; jt.length > i; i++) {
            x = new JLabel(jt[i]);
            x.setOpaque(false);
            x.setFont(new Font("Arial Black", Font.PLAIN, 14));
            x.setForeground(Color.white);
            jtP.add(x);
        }

        //Dialogue SOUTH
        infoP = new JPanel();
        infoP.setLayout(new BoxLayout(infoP, BoxLayout.PAGE_AXIS));
        infoP.setPreferredSize(new Dimension(200, 30));
        infoP.setOpaque(false);
        infoL = new JLabel();
        infoL.setForeground(Color.YELLOW);
        infoL.setFont(new Font("Arial Black", Font.PLAIN, 20));
        infoL.setOpaque(false);

        //CENTER PANEL
        gameOptionsP = new GameOptionsPanel(game);
        //gameOptionsP = new Insets(0, -100, 0, 0);
        gameOptionsP.setOpaque(false);

        diceShowPanel = new JPanel();
        diceShowPanel.setLayout(new GridBagLayout());
        diceValue = new JLabel();
        diceValue.setFont(new Font("Arial Black", Font.PLAIN, 50));
        diceValue.setForeground(Color.YELLOW);
        diceShowPanel.setOpaque(false);
        c = new GridBagConstraints();
        c.insets = new Insets(0, 0, 0, 100);

        diceShowPanel.add(diceValue, c);

    }

    private void setupLayout() {
        setLayout(new BorderLayout());

        //SOUTH Journy tracker, FlowLayout com labels
        add(jtP, BorderLayout.SOUTH);

        //NORTH Dialogue box
        infoP.add(infoL);
        add(infoP, BorderLayout.NORTH);

        //CENTER Game Options Panel
        add(gameOptionsP, BorderLayout.CENTER);
        add(diceShowPanel, BorderLayout.EAST);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        diceValue.setText(Integer.toString(game.getGame().getGameData().getCurrentDice()));

        try {
            Component a = jtP.getComponent(jtTurn + 1);
            a.setForeground(Color.red);
            a.repaint();
            for (int i = 2; jt.length > i; i++) {
                a = jtP.getComponent(jtTurn);
                a.setForeground(Color.white);
                a.repaint();
            }
            jtTurn = game.getJourneyTackerTurn();
            a = jtP.getComponent(0);
            a.setForeground(Color.red);
            a.repaint();
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("Painting Journey Tracker out of bounds 15+");
        }
        if (game.getState() instanceof AwaitThirdTokenPlacementCM1) {
            infoL.setText("Click the Room to place the #1 Crew Member!");
        } else if (game.getState() instanceof AwaitThirdTokenPlacementCM2) {
            infoL.setText("Click the Room to place the #2 Crew Member!");
        } else if (game.getState() instanceof AwaitCrewPhase) {
            infoL.setText("It's your turn, choose your actions wisely! " + game.getActionPoints() + " AP remaning!");
        } else if (game.getState() instanceof AwaitMove) {
            infoL.setText("Click the Room to where The " + game.getNameMoving() + " is moving!");
        } else if (game.getState() instanceof AwaitDieRoll) {
            infoL.setText("Attacking..........");
        } else if (game.getState() instanceof AwaitSeal) {
            infoL.setText("Click a Room to Seal!");
        } else if (game.getState() instanceof AwaitRestPhase) {
            infoL.setText("Resting Phase: Use your Inspiration Points for Upgrades!");
        } else if (game.getState() instanceof GameOver) {
            infoL.setText("!!!!!!!!!!!!!!!!!!!!!!!!!!GAME OVER!!!!!!!!!!!!!!!!!!!!!!!!!!");
        } else if (game.getState() instanceof Victory) {
            infoL.setText("!!!!!!!!!!!!!!!!!!!!!!!!!!VICTORY!!!!!!!!!!!!!!!!!!!!!!!!!!");
//            if (game.getGame().getGameData().getHealthTracker() == 0) {
//                JOptionPane.showMessageDialog(null,
//                        "You lost all HEALTH!", "GAME OVER!",
//                        JOptionPane.ERROR_MESSAGE);
//            } else {
//                JOptionPane.showMessageDialog(null,
//                        "You lost the HULL!", "GAME OVER!",
//                        JOptionPane.ERROR_MESSAGE);
//            }
        }

    }

    @Override
    public void update(Observable o, Object arg) {
        setVisible(true);
        revalidate();
        repaint();
    }

}
