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
        frame.setSize(400, 400);
        frame.requestFocusInWindow();
        JPanel panel = new JPanel();
        panel.setBackground(Color.BLACK);
        panel.setLayout(new BorderLayout());
        UIManager.put("Button.background", Color.BLACK);
        UIManager.put("Button.foreground", Color.WHITE);
        JLabel label = new JLabel("The World Simulation Game!");
        Font font = new Font("Arial", Font.ITALIC, 27);
        label.setFont(font);
        label.setForeground(Color.BLUE);
        label.setHorizontalAlignment(JLabel.CENTER); // Center-align the label
        panel.add(label, BorderLayout.CENTER);

        JButton newGameButton = new JButton("New Game");
        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                height = Integer.parseInt(JOptionPane.showInputDialog("Enter height: "));
                width = Integer.parseInt(JOptionPane.showInputDialog("Enter width: "));
                generateWorld(frame);
            }
        });

        JButton loadGameButton = new JButton("Load Game");
        loadGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ReadGame(frame);
            }
        });

// Create two smaller panels and add each button to one of them
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        topPanel.add(newGameButton, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());
        bottomPanel.add(loadGameButton, BorderLayout.CENTER);

// Add the two smaller panels to the main panel, positioning them at the top and bottom respectively
        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        frame.add(panel);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }
    private static void generateWorld(JFrame frame) {
        World world = new World(height, width);
        WorldGenerator generator = new WorldGenerator(world);
        generator.Generate();
        world.DrawWorld(frame, generator);
        frame.requestFocusInWindow();
        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    world.Turn(world.TURN_NONE);
                    world.DrawWorld(frame, generator);
                }
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    world.Turn(world.TURN_UP);
                    world.DrawWorld(frame, generator);
                }
                if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    world.Turn(world.TURN_DOWN);
                    world.DrawWorld(frame, generator);
                }
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    world.Turn(world.TURN_LEFT);
                    world.DrawWorld(frame, generator);
                }
                if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    world.Turn(world.TURN_RIGHT);
                    world.DrawWorld(frame, generator);
                }
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    world.Turn(world.TURN_SUPER);
                    world.DrawWorld(frame, generator);
                }
                if (e.getKeyCode() == KeyEvent.VK_S) {
                    generator.SaveGame();
                }
                if (e.getKeyCode() == KeyEvent.VK_P) {
                    JOptionPane.showMessageDialog(frame, "a - Antelope\nf - Fox\ns - Sheep\nt - Turtle\nw - Wolf\nb - Belladonna\nd - Dandelion\nG - Grass\ng - Guarana\nh - SosmowskiHogweed");
                }
            }
        });
    }

    private static void ReadGame(JFrame frame){
        WorldGenerator generator = new WorldGenerator();
        generator.ReadGame();
        World world = generator.GetWorld();
        world.DrawWorld(frame, generator);
        frame.requestFocusInWindow();
        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    world.Turn(world.TURN_NONE);
                    world.DrawWorld(frame, generator);
                }
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    world.Turn(world.TURN_UP);
                    world.DrawWorld(frame, generator);
                }
                if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    world.Turn(world.TURN_DOWN);
                    world.DrawWorld(frame, generator);
                }
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    world.Turn(world.TURN_LEFT);
                    world.DrawWorld(frame, generator);
                }
                if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    world.Turn(world.TURN_RIGHT);
                    world.DrawWorld(frame, generator);
                }
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    world.Turn(world.TURN_SUPER);
                    world.DrawWorld(frame, generator);
                }
                if (e.getKeyCode() == KeyEvent.VK_S) {
                    generator.SaveGame();
                }
                if (e.getKeyCode() == KeyEvent.VK_P) {
                    JOptionPane.showMessageDialog(frame, "a - Antelope\nf - Fox\ns - Sheep\nt - Turtle\nw - Wolf\nb - Belladonna\nd - Dandelion\nG - Grass\ng - Guarana\nh - SosmowskiHogweed");
                }
            }
        });
    }
}