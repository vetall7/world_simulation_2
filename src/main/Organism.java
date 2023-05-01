package main;

public abstract class  Organism {
    protected int age;
    protected int power;
    protected int initiative;
    protected char sign;
    protected String name;
    protected int x, y;
    protected World world;
    public Organism(){};
    public int GetX(){
        return x;
    }
    public int GetY(){
        return y;
    }
    public char GetSign(){
        return sign;
    }

    public abstract void Action();
    public abstract void Collision();
}
