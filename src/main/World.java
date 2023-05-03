package main;

import Animals.Animal;
import Animals.Antelope;
import Animals.Human;
import Plants.Plant;
import Plants.SosmowskiHogweed;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;
public class World {
    private int height, width, turn;
    private Vector<Organism> organisms;
    private Vector<String> comments;
    private Organism[][] board;
    private Human human;
    public static final int TURN_NONE = 0;
    public static final int TURN_UP = 1;
    public static final int TURN_DOWN = 2;
    public static final int TURN_LEFT = 3;
    public static final int TURN_RIGHT = 4;
    public static final int TURN_SUPER = 5;
    public int GetTurn(){
        return turn;
    }
    public void SetHuman(Human human) {
        this.human = human;
    }
    public void AddComments(String comment){
        comments.add(comment);
    }
    public World(int height, int width) {
        this.height = height;
        this.width = width;
        organisms = new Vector<>();
        board = new Organism[height][width];
        for (int i = 0; i < height; i++){
            for (int j = 0; j < width; j++){
                board[i][j] = null;
            }
        }
        turn = 0;
        comments = new Vector<String>();
    }
    public int GetWidth(){
        return width;
    }
    public int GetOrgCounter(){
        return organisms.size();
    }

    public Vector<Organism> getOrganisms() {
        return organisms;
    }
    public void SetTurn(int turn){
        this.turn = turn;
    }
    public int GetHeight(){
        return height;
    }
    public void SetPoint(int y, int x, Organism organism){
        board[y][x] = organism;
    }
    public void AddOrganism(Organism organism){
        organisms.add(organism);
        SetPoint(organism.GetY(), organism.GetX(), organism);
    }
    public Organism GetPoint(int x, int y){
        return board[y][x];
    }
    private void drawSquare(Graphics g, int row, int col, int size) {
        int x = col * size;
        int y = row * size;
        g.setColor(Color.WHITE);
        g.drawRect(x, y, size, size);
        if (GetPoint(col, row) instanceof Human) {
            g.setColor(Color.YELLOW);
        }
        else if (GetPoint(col, row) instanceof Plant){
            g.setColor(Color.GREEN);
        }else if (GetPoint(col, row) instanceof Animal){
            g.setColor(Color.BLUE);
        }
        else {
            g.setColor(Color.BLACK);
        }
        if (GetPoint(col, row) instanceof SosmowskiHogweed){
            g.setColor(Color.RED);
        }
        g.fillRect(x + 1, y + 1, size - 1, size - 1);
        g.setColor(Color.WHITE);
        if (board[row][col] != null) {
            g.drawString(new String(String.valueOf(board[row][col].GetSign())), x + size / 2 - 5, y + size / 2 + 5);
        }

    }
    public void DrawWorld(JFrame frame){
        JPanel boardPanel = new JPanel() {
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            int cellSize = 50;
            int boardWidth = width * cellSize + 15;
            int boardHeight = height * cellSize + 400;
            frame.setSize(boardWidth,boardHeight);
            for (int row = 0; row < height; row++) {
                for (int col = 0; col < width; col++) {
                    drawSquare(g, row, col, cellSize);
                }
            }
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
            if (human.GetSuperPower()){
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
                String message = "";
                if (point instanceof Human) {
                    message = "You clicked on a human.";
                } else if (point instanceof Plant) {
                    message = "You clicked on a plant.";
                } else if (point instanceof Animal) {
                    message = "You clicked on an animal.";
                } else if (point instanceof SosmowskiHogweed) {
                    message = "You clicked on a Sosmowski Hogweed.";
                } else {
                    message = "You clicked on an empty square.";
                }
                JOptionPane.showMessageDialog(frame, message);
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

    public void Turn(int direction ){
        turn++;
        human.SetDirection(direction);
        comments.clear();
        for (int i = 0; i < organisms.size(); i++){
            if (organisms.get(i).GetAge() == 0){
                organisms.get(i).SetAge(1);
            }
        }
        Collections.sort(organisms, new Comparator<Organism>() {
            @Override
            public int compare(Organism o1, Organism o2) {
                if (o1.GetInitiative() != o2.GetInitiative()) {
                    return o2.GetInitiative() - o1.GetInitiative();
                } else {
                    return o2.GetAge() - o1.GetAge();
                }
            }
        });

        for (int i = 0; i < organisms.size(); i++){
            if (organisms.get(i).GetAge() != 0) {
                organisms.get(i).Action(1);
            }
        }
    }

    public void FindPoints(Organism org, Vector<Integer> x, Vector<Integer> y){
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
    }

    public void DeleteOrg(Organism org){
        organisms.removeElement(org);
    }
}
