package main;

import Animals.Human;

import javax.swing.*;
import java.awt.*;
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
        g.setColor(Color.BLACK);
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

                int cellSize = 50; // Размер одной клетки на доске
                int boardWidth = width * cellSize + 15;
                int boardHeight = height * cellSize + 400;
                frame.setSize(boardWidth,boardHeight);
                // Рисуем доску
                for (int row = 0; row < height; row++) {
                    for (int col = 0; col < width; col++) {
                        drawSquare(g, row, col, cellSize);
                    }
                }
                setPreferredSize(new Dimension(boardWidth, boardHeight));
            }
        };
        JList<String> list = new JList<>(comments);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setPreferredSize(new Dimension(0, 300));


        frame.getContentPane().removeAll();
        frame.getContentPane().add(boardPanel);
        frame.add(scrollPane, BorderLayout.SOUTH);
        frame.pack();
        frame.setVisible(true);

        // System.out.println(organisms.size());
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
