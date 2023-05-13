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
    private static int width;
    private static int height;
    private static World world;
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
        ActionListener yesListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                height = Integer.parseInt(JOptionPane.showInputDialog("Enter height: "));
                width = Integer.parseInt(JOptionPane.showInputDialog("Enter width: "));
                world = new SimpleWorld(height, width);
                generateWorld(frame);
                dialog.dispose();
            }
        };

        ActionListener noListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                height = Integer.parseInt(JOptionPane.showInputDialog("Enter height: "));
                width = Integer.parseInt(JOptionPane.showInputDialog("Enter width: "));
                world = new HexWorld(height, width);
                generateWorld(frame);
                dialog.dispose();
            }
        };

        // dodanie sluchacze
        simpleWorld.addActionListener(yesListener);
        hexWorld.addActionListener(noListener);
        dialog.setVisible(true);
    }
    private static void generateWorld(JFrame frame) { // jeżeli użytkownik wybral nową grę, to generujemy swiat
        WorldGenerator generator = new WorldGenerator(world);
        generator.Generate();
        world.DrawWorld(frame, generator);
        frame.requestFocusInWindow();
        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                ReadKey(e, frame, generator);
            }
        });
    }

    private static void ReadGame(JFrame frame){ // wczytujemy gre
        WorldGenerator generator = new WorldGenerator();
        generator.ReadGame();
        world = generator.GetWorld();
        world.DrawWorld(frame, generator);
        frame.requestFocusInWindow();
        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                ReadKey(e, frame, generator);
            }
        });
    }
    private static void ReadKey(KeyEvent e, JFrame frame, WorldGenerator generator){ // który przycisk został wciśnięty
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