package main;

public abstract class  Organism {
    protected int age;
    protected int power;
    protected int initiative;
    protected char sign;
    protected String name;
    protected int x, y, x_priv, y_priv;
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
    public int GetPower() {
        return power;
    }
    public int GetInitiative(){
        return initiative;
    }
    public int GetAge(){
        return age;
    }
    public void SetAge(int age){
        this.age = age;
    }
    public String GetName(){
        return name;
    }
    public abstract void Action(int range);
    public abstract void Collision(Organism victim, int x, int y);
}
