package Animals;

import main.World;

public class Turtle extends Animal{

    public Turtle(int x, int y, World world){
        super (x,y,world, 't', "Turtle", 2, 1);
    }
}
