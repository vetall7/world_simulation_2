import main.HexWorld;
import main.SimpleWorld;
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
        // mowi nam o tym jak będą się mieścili elementy w oknie (w danym przypadku są 5 sposobów  NORTH, SOUTH, EAST, WEST, CENTER)
        panel.setLayout(new BorderLayout());
        UIManager.put("Button.background", Color.BLACK);
        UIManager.put("Button.foreground", Color.WHITE);
        JLabel label = new JLabel("The World Simulation Game!");
        Font font = new Font("Arial", Font.ITALIC, 27);
        label.setFont(font);
        label.setForeground(Color.BLUE);
        label.setHorizontalAlignment(JLabel.CENTER); // Center-align the label
        panel.add(label, BorderLayout.CENTER);
        // na samym początku gry użytkownik ma do wyboru rozpocząć nową grę lub kontynuować grę wczytanej z pliku
        JButton newGameButton = new JButton("New Game");
        newGameButton.addActionListener(new ActionListener() { // sluchacz zdarzeń na przycisku
            @Override
            public void actionPerformed(ActionEvent e) {
                ChooseWorld(frame);
            }
        });

        JButton loadGameButton = new JButton("Load Game");
        loadGameButton.addActionListener(new ActionListener() { // sluchacz zdarzeń na przycisku
            @Override
            public void actionPerformed(ActionEvent e) {
                ReadGame(frame);
            }
        });

// tworzenie dwóch małych paneli i dodawanie przycisków
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        topPanel.add(newGameButton, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());
        bottomPanel.add(loadGameButton, BorderLayout.CENTER);

// Dodaj dwa mniejsze paneli do panelu głównego, umieszczając je odpowiednio na górze i na dole.
        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        frame.add(panel);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }
    private static void ChooseWorld(JFrame frame){
        // tworzenie paneli z przyciskami
        JPanel buttonPanel = new JPanel();
        // uzytkownik ma do wybory zwyklą planszę (kwadratową) lub planszę sześcienną
        JButton simpleWorld = new JButton("Simple World ");
        JButton hexWorld = new JButton("Hex World ");
        buttonPanel.add(simpleWorld);
        buttonPanel.add(hexWorld);
        JOptionPane optionPane = new JOptionPane(buttonPanel, JOptionPane.QUESTION_MESSAGE, JOptionPane.DEFAULT_OPTION);
        optionPane.setOptions(new Object[]{simpleWorld, hexWorld});
        optionPane.setInitialValue(simpleWorld);
        // twórzymy okno dialogowe i czekamy na wybór
        javax.swing.JDialog dialog = optionPane.createDialog(null, "Choose the world");
        // sluchacze zdarzeń na przyciskach
        ActionListener simple = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int height = Integer.parseInt(JOptionPane.showInputDialog("Enter height: "));
                int width = Integer.parseInt(JOptionPane.showInputDialog("Enter width: "));
                World world = new SimpleWorld(height, width);
                generateWorld(frame, world);
                dialog.dispose();
            }
        };

        ActionListener hex = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int height = Integer.parseInt(JOptionPane.showInputDialog("Enter height: "));
                int width = Integer.parseInt(JOptionPane.showInputDialog("Enter width: "));
                World world = new HexWorld(height, width);
                generateWorld(frame, world);
                dialog.dispose();
            }
        };

        // dodanie sluchacze
        simpleWorld.addActionListener(simple);
        hexWorld.addActionListener(hex);
        dialog.setVisible(true);
    }
    private static void generateWorld(JFrame frame, World world) { // jeżeli użytkownik wybral nową grę, to generujemy swiat
        WorldGenerator generator = new WorldGenerator(world);
        generator.Generate();
        world.DrawWorld(frame, generator);
        frame.requestFocusInWindow();
        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                ReadKey(e, frame, generator, world);
            }
        });
    }

    private static void ReadGame(JFrame frame){ // wczytujemy gre
        WorldGenerator generator = new WorldGenerator();
        generator.ReadGame();
        generator.GetWorld().DrawWorld(frame, generator);
        frame.requestFocusInWindow();
        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                ReadKey(e, frame, generator, generator.GetWorld());
            }
        });
    }
    private static void ReadKey(KeyEvent e, JFrame frame, WorldGenerator generator, World world){ // który przycisk został wciśnięty
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            world.Turn(world.TURN_NONE);
            world.DrawWorld(frame, generator);
        }
        if (e.getKeyCode() == KeyEvent.VK_W) {
            world.Turn(world.TURN_UP);
            world.DrawWorld(frame, generator);
        }
        if (e.getKeyCode() == KeyEvent.VK_S) {
            world.Turn(world.TURN_DOWN);
            world.DrawWorld(frame, generator);
        }
        if (e.getKeyCode() == KeyEvent.VK_A) {
            world.Turn(world.TURN_LEFT);
            world.DrawWorld(frame, generator);
        }
        if (e.getKeyCode() == KeyEvent.VK_Q){
            world.Turn(world.TURN_UP_RIGHT);
            world.DrawWorld(frame, generator);
        }
        if (e.getKeyCode() == KeyEvent.VK_E){
            world.Turn(world.TURN_DOWN_LEFT);
            world.DrawWorld(frame, generator);
        }
        if (e.getKeyCode() == KeyEvent.VK_D) {
            world.Turn(world.TURN_RIGHT);
            world.DrawWorld(frame, generator);
        }
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            world.Turn(world.TURN_SUPER);
            world.DrawWorld(frame, generator);
        }
        if (e.getKeyCode() == KeyEvent.VK_X) {
            generator.SaveGame();
        }
        if (e.getKeyCode() == KeyEvent.VK_P) {
            JOptionPane.showMessageDialog(frame, "a - Antelope\nf - Fox\ns - Sheep\nt - Turtle\nw - Wolf\nb - Belladonna\nd - Dandelion\nG - Grass\ng - Guarana\nh - SosmowskiHogweed");
        }
    }
}