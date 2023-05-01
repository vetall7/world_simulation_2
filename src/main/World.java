package main;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;
public class World {
    private int height, width;
    private Vector<Organism> organisms;
    private Organism[][] board;

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
    }
    public int GetWidth(){
        return width;
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
    private void drawSquare(Graphics g, int row, int col, int size) {
        int x = col * size;
        int y = row * size;
        g.setColor(Color.BLACK);
        g.drawRect(x, y, size, size);
        g.setColor(Color.BLUE);
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
                int boardHeight = height * cellSize + 40;
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

        frame.getContentPane().removeAll();
        frame.getContentPane().add(boardPanel);
        frame.pack();
        frame.setVisible(true);
    }
}
