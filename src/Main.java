import main.World;
import main.WorldGenerator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Scanner;
public class Main {
    private static int width;
    private static int height;

    public static void main(String[] args) {
        showInputDialog();
    }

    private static void showInputDialog() {
        JFrame frame = new JFrame("Vitalii Shapovalov 196633");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 450);
        frame.requestFocusInWindow();
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        JButton newGameButton = new JButton("New Game");
        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                height = Integer.parseInt(JOptionPane.showInputDialog("Enter height: "));
                width = Integer.parseInt(JOptionPane.showInputDialog("Enter width: "));
                generateWorld(frame);
            }
        });
        panel.add(newGameButton);

        JButton loadGameButton = new JButton("Load Game");
        loadGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ReadGame(frame);
            }
        });
        panel.add(loadGameButton);

        frame.add(panel);
        frame.setVisible(true);
    }
    private static void generateWorld(JFrame frame) {
        World world = new World(height, width);
        WorldGenerator generator = new WorldGenerator(world);
        generator.Generate();
        world.DrawWorld(frame);
        frame.requestFocusInWindow();
        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    world.Turn(world.TURN_NONE);
                    world.DrawWorld(frame);
                }
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    world.Turn(world.TURN_UP);
                    world.DrawWorld(frame);
                }
                if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    world.Turn(world.TURN_DOWN);
                    world.DrawWorld(frame);
                }
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    world.Turn(world.TURN_LEFT);
                    world.DrawWorld(frame);
                }
                if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    world.Turn(world.TURN_RIGHT);
                    world.DrawWorld(frame);
                }
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    world.Turn(world.TURN_SUPER);
                    world.DrawWorld(frame);
                }
                if (e.getKeyCode() == KeyEvent.VK_S) {
                    generator.SaveGame();
                }
            }
        });
    }

    private static void ReadGame(JFrame frame){
        WorldGenerator generator = new WorldGenerator();
        generator.ReadGame();
        World world = generator.GetWorld();
        world.DrawWorld(frame);
        frame.requestFocusInWindow();
        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    world.Turn(world.TURN_NONE);
                    world.DrawWorld(frame);
                }
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    world.Turn(world.TURN_UP);
                    world.DrawWorld(frame);
                }
                if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    world.Turn(world.TURN_DOWN);
                    world.DrawWorld(frame);
                }
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    world.Turn(world.TURN_LEFT);
                    world.DrawWorld(frame);
                }
                if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    world.Turn(world.TURN_RIGHT);
                    world.DrawWorld(frame);
                }
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    world.Turn(world.TURN_SUPER);
                    world.DrawWorld(frame);
                }
                if (e.getKeyCode() == KeyEvent.VK_S) {
                    generator.SaveGame();
                }
            }
        });
    }
}