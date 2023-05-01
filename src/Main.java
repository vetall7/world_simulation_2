import main.World;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        int[] width = new int[1];
        int[] height = new int[1];

        JFrame frame = new JFrame("Vitalii Shapovalov 196633");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 450);

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        JTextField heightField = new JTextField( 10);
        JTextField widthField = new JTextField(10);

        widthField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                width[0] = Integer.parseInt(widthField.getText());
                height[0] = Integer.parseInt(heightField.getText());
                heightField.setVisible(false);
                widthField.setVisible(false);
                panel.setVisible(false);
                World world = new World(height[0], width[0]);
                world.DrawWorld(frame);
            }
        });

        panel.add(heightField);
        panel.add(widthField);

        frame.add(panel);
        frame.setVisible(true);

    }
}