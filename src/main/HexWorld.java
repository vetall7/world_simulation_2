package main;

import Animals.Animal;
import Animals.Human;
import Plants.Plant;
import Plants.SosmowskiHogweed;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

public class HexWorld extends World{
    public HexWorld(int height, int width) {
        super(height, width);
        CellNeighboursCounter = 6;
    }

    @Override
    protected void drawSquare(Graphics g, int row, int col) {
        int x = col * cellSize;
        int y = row * cellSize;
        // koordynaty wszystkich wierzchołków szściokąta
        int[] xPoints = {distance_x + x, distance_x + x, distance_x + x + cellSize/2, distance_x + x + cellSize, distance_x + x + cellSize, distance_x + x + cellSize/2};
        int[] yPoints = {distance_y + y + 3 * cellSize/4, distance_y + y + cellSize/4,distance_y +  y, distance_y + y + cellSize/4,distance_y +  y + cellSize*3/4,distance_y + y + cellSize};
        // w zależności od tego, jaki jest organizm zmieniamy kolor pola
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
        if (board[row][col] != null) { // wypisujemy znak każdego organizmu
            g.drawString(new String(String.valueOf(board[row][col].GetSign())), distance_x + x + cellSize / 2 - 5, distance_y + y + cellSize / 2 + 5);
        }
        g.setColor(Color.GRAY);
        g.drawPolygon(xPoints, yPoints, 6); // rysujemy pole
    }
    private int distance_x = 0;
    private int distance_y = 0;
    @Override
    public void DrawWorld(JFrame frame, WorldGenerator generator) {
        JPanel boardPanel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                int boardWidth = width * cellSize + 15 + width*cellSize/2 ;
                int boardHeight = height * cellSize + 350;
                frame.setSize(boardWidth,boardHeight);
                for (int row = 0; row < height; row++) {
                   for (int col = 0; col < width; col++) {
                        drawSquare(g,  row, col);
                   }
                   distance_x+= cellSize/2;  // w Hex implementacji każda linia ma być zmiszczona o polowe rozmiaru zeby z każdej krawędzi wychodzil nowy sześciokąt
                   distance_y -= cellSize/4;
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
                g.drawString(new String("PRESS X TO SAVE THE GAME "), 0, position + 85 );
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
            public void mouseClicked(MouseEvent e) { // sluchacz zdarzen zeby zrozumieć na ktore pole nacisnąl użytkownik
                int row = (e.getY() / (cellSize - cellSize/4));
                int col = (e.getX() - (row *  (cellSize/2))) / cellSize; // wyliczanie gdzie sie znajduje organizm w tablice
                if (row >= height || row < 0 || col > width || col < 0){
                    return;
                }
                Object point = GetPoint(col, row);
                if (point instanceof Human) {
                    JOptionPane.showMessageDialog(frame, "You clicked on a human.");
                } else if (point instanceof Plant) {
                    JOptionPane.showMessageDialog(frame, "You clicked on a plant.");
                } else if (point instanceof Animal) {
                    JOptionPane.showMessageDialog(frame, "You clicked on an animal.");
                }
                else {  // wybór organizmu
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
    @Override
    public void FindPoints(Organism org, Vector<Integer> x, Vector<Integer> y){ // szukanie wszyskich pustych sąsiadujących pól
        int x_coo = org.GetX();
        int y_coo = org.GetY();
        if (y_coo-1 >= 0 && this.GetPoint(x_coo, y_coo-1) == null){
            x.add(x_coo);
            y.add(y_coo-1);
        }
        if (x_coo + 1 < this.GetWidth() && this.GetPoint(x_coo + 1, y_coo) == null){
            x.add(x_coo + 1);
            y.add(y_coo);
        }
        if (y_coo + 1 < this.GetHeight() && this.GetPoint(x_coo, y_coo +1 ) == null){
            x.add(x_coo);
            y.add(y_coo + 1);
        }
        if (x_coo - 1 >= 0 && this.GetPoint(x_coo-1, y_coo) == null){
            x.add(x_coo - 1);
            y.add(y_coo);
        }
        if (x_coo - 1 >= 0 && y_coo + 1 < this.GetHeight() && this.GetPoint(x_coo-1, y_coo+1) == null){
            x.add(x_coo - 1);
            y.add(y_coo + 1);
        }
        if (x_coo + 1 < this.GetWidth() && y_coo - 1 >= 0 && this.GetPoint(x_coo + 1, y_coo - 1) == null){
            x.add(x_coo + 1);
            y.add(y_coo - 1);
        }
    }

    @Override
    public void FindNeighbours(Organism org, Vector<Integer> x, Vector<Integer> y){// szukanie wszyskich sąsiadujących zwierząt
        for (int i = -1; i <= 1; i++){
            for (int j = -1; j <= 1; j++){
                if (i == 0 && j == 0 || i == -1 && j == -1 || i == 1 && j == 1){
                    continue;
                }
                if (org.GetY() + i >= 0 && org.GetY() + i < GetHeight() && org.GetX() + j >= 0 &&org.GetX() +j < GetWidth()
                        &&GetPoint(org.GetX() + j, org.GetY() + i) != null && GetPoint(org.GetX() + j, org.GetY() + i) instanceof Animal){
                    x.add(org.GetX() + j);
                    y.add(org.GetY() + i);
                }
            }
        }
    }
}
