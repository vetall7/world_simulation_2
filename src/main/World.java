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
public abstract class World {
    protected int height, width, turn;
    protected Vector<Organism> organisms;
    protected Vector<String> comments;
    protected Organism[][] board;
    protected Human human;
    protected int CellNeighboursCounter = 0; // maksymalna ilość sąsiadujących pól dla każdego świata
    protected int cellSize = 50;
    public int GetCellNeighboursCounter(){
        return CellNeighboursCounter;
    }
    public static final int TURN_NONE = 0; // wszyskie kierunki ruchu czlowieka
    public static final int TURN_UP = 1;
    public static final int TURN_DOWN = 2;
    public static final int TURN_LEFT = 3;
    public static final int TURN_RIGHT = 4;
    public static final int TURN_SUPER = 5;
    public static final int TURN_UP_RIGHT = 6;
    public static final int TURN_DOWN_LEFT = 7;
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

    protected abstract void drawSquare(Graphics g, int row, int col);

    public abstract void DrawWorld(JFrame frame, WorldGenerator generator);

    public void Turn(int direction ){
        turn++;  // zwiekszamy licznik tury
        if (human != null) {human.SetDirection(direction);} // w ktora strone idzie czlowiek
        comments.clear();
        for (int i = 0; i < organisms.size(); i++){
            if (organisms.get(i).GetAge() == 0){
                organisms.get(i).SetAge(1); // organizmy ktore dopiero sie urodzily juz przezyli jedna ture
            }
        }
        Collections.sort(organisms, new Comparator<Organism>() { // sortujemy organizmy wedlug inicjatywy lub wieku
            @Override
            public int compare(Organism o1, Organism o2) {
                if (o1.GetInitiative() != o2.GetInitiative()) {
                    return o2.GetInitiative() - o1.GetInitiative();
                } else {
                    return o2.GetAge() - o1.GetAge();
                }
            }
        });

        for (int i = 0; i < organisms.size(); i++){ // dla kazdego organizmu wywolujemy metode Akcja
            if (organisms.get(i).GetAge() != 0) {  // organizm ktory dopiero sie urodzil nie sie przemieszcza
                organisms.get(i).Action(1);
            }
        }
    }

    public abstract void FindPoints(Organism org, Vector<Integer> x, Vector<Integer> y);
    public abstract void FindNeighbours(Organism org, Vector<Integer> x, Vector<Integer> y);
    public void DeleteOrg(Organism org){
        organisms.removeElement(org);
    }
}
