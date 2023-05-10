package main;

import Animals.Animal;
import Animals.Human;
import Plants.Plant;
import Plants.SosmowskiHogweed;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class HexWorld extends World{

    public HexWorld(int height, int width) {
        super(height, width);
    }

    @Override
    protected void drawSquare(Graphics g, int row, int col, int size) {
        int x = col * size;
        int y = row * size;

        int[] xPoints = {distance_x + x, distance_x + x, distance_x + x + size/2, distance_x + x + size, distance_x + x + size, distance_x + x + size/2};
        int[] yPoints = {distance_y + y + 3 * size/4, distance_y + y + size/4,distance_y +  y, distance_y + y + size/4,distance_y +  y + size*3/4,distance_y + y + size};


        if (GetPoint(col, row) instanceof Human) {
            g.setColor(Color.YELLOW);
        } else if (GetPoint(col, row) instanceof Plant) {
            g.setColor(Color.GREEN);
        } else if (GetPoint(col, row) instanceof Animal) {
            g.setColor(Color.BLUE);
        } else {
            g.setColor(Color.BLACK);
        }

        if (GetPoint(col, row) instanceof SosmowskiHogweed) {
            g.setColor(Color.RED);
        }

        g.fillPolygon(xPoints, yPoints, 6);

        g.setColor(Color.WHITE);
        if (board[row][col] != null) {
            g.drawString(new String(String.valueOf(board[row][col].GetSign())), distance_x + x + size / 2 - 5, distance_y + y + size / 2 + 5);
        }
        g.setColor(Color.GRAY);
        g.drawPolygon(xPoints, yPoints, 6);
    }
    private int distance_x = 0;
    private int distance_y = 0;
    @Override
    public void DrawWorld(JFrame frame, WorldGenerator generator) {
        JPanel boardPanel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                int cellSize = 50;
                int boardWidth = width * cellSize + 15 + width*cellSize/2 ;
                int boardHeight = height * cellSize + 350;
                frame.setSize(boardWidth,boardHeight);
                for (int row = 0; row < height; row++) {
                   for (int col = 0; col < width; col++) {
                        drawSquare(g,  row, col, cellSize);
                   }
                   distance_x+= cellSize/2;
                   distance_y -= 15;
                }
                distance_x = 0;
                distance_y = 0;
                setPreferredSize(new Dimension(boardWidth, boardHeight));
                g.setColor(Color.BLACK);
                Font font = g.getFont().deriveFont(15f);
                Font font2 = g.getFont().deriveFont(18f);
                g.setFont(font2);
                int position = height*52;
                g.drawString(new String("Organisms counter: " + organisms.size() + "     Turn counter: " + turn), 0, position );
                g.setColor(Color.RED);
                g.setFont(font);
                g.drawString(new String("Yellow - HUMAN "), 0, position + 20 );
                g.setColor(Color.GREEN);
                g.drawString(new String("Green  - PLANTS "), 0, position + 40 );
                g.setColor(Color.BLUE);
                g.drawString(new String("Blue   - ANIMALS "), 0, position + 60 );
                g.setColor(Color.BLACK);
                g.drawString(new String("PRESS S TO SAVE THE GAME "), 0, position + 85 );
                g.drawString(new String("ARROWS - HUMAN MOVEMENT "), 0, position + 100 );
                if (human != null && human.GetSuperPower()){
                    g.setColor(Color.RED);
                    g.drawString(new String("ACTIVATED"), 180, position + 115 );
                }
                g.setFont(font);
                g.setColor(Color.BLACK);
                g.drawString(new String("ENTER - SUPERPOWER "), 0, position + 115);
                g.setColor(Color.RED);
                g.drawString(new String("PRESS P TO SEE THE PROMPTS"), 0, position + 130);

            }
        };
        boardPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = e.getY() / 50;
                int col = e.getX() / 50;
                Object point = GetPoint(col, row);
                if (point instanceof Human) {
                    JOptionPane.showMessageDialog(frame, "You clicked on a human.");
                } else if (point instanceof Plant) {
                    JOptionPane.showMessageDialog(frame, "You clicked on a plant.");
                } else if (point instanceof Animal) {
                    JOptionPane.showMessageDialog(frame, "You clicked on an animal.");
                }
                else {
                    String options[] = {
                            "Antelope",
                            "Fox",
                            "Wolf",
                            "Turtle",
                            "Sheep",
                            "Belladonna",
                            "Dandelion",
                            "Grass",
                            "Guarana",
                            "SosmowskiHogweed"
                    };
                    String Selected = (String) JOptionPane.showInputDialog(
                            frame,
                            "Choose an organism:",
                            "Organism Selection",
                            JOptionPane.PLAIN_MESSAGE,
                            null,
                            options,
                            options[0]);
                    if (Selected != null){
                        Organism new_org = generator.ReadOrganism(Selected, col, row);
                        if (new_org != null){
                            AddOrganism(new_org);
                            DrawWorld(frame, generator);
                            new_org.SetAge(1);
                        }
                    }
                }

            }
        });


        JList<String> list = new JList<>(comments);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setPreferredSize(new Dimension(0, 150));

        frame.getContentPane().removeAll();
        frame.getContentPane().add(scrollPane, BorderLayout.SOUTH);
        frame.getContentPane().add(boardPanel);
        frame.pack();
        frame.setVisible(true);
    }
}
