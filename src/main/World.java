package main;

import javax.swing.JFrame;
import javax.swing.JLabel;
public class World {
    private int height, width;
    public World(int height, int width) {
        this.height = height;
        this.width = width;
    }
    public int GetWidth(){
        return width;
    }
    public int GetHeight(){
        return height;
    }
    public void DrawWorld(){
        JFrame frame = new JFrame("Vitalii Shapovalov 196633");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JLabel label = new JLabel("Привет, мир!");
        frame.setSize(1200, 900);
        frame.add(label);
        frame.setVisible(true);
    }
}
