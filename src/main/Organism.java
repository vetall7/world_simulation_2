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
    public void PowerIncrease(int p){
        power += p;
    }
    public void SetX(int x){
        this.x = x;
    }
    public void SetY(int y){
        this.y = y;
    }
    public int GetXpriv(){
        return x_priv;
    }
    public int GetYpriv(){
        return y_priv;
    }
    public void SetXpriv(int x_priv){
        this.x_priv = x_priv;
    }
    public void SetYpriv(int y_priv){
        this.y_priv = y_priv;
    }
    public void SetPower(int power){
        this.power = power;
    }
    public boolean TarczeAlzura(Organism attacker){return false;}
    public boolean DidDeflectedAttack(Organism attacker){return false;}
    public abstract void Action(int range);
    public abstract void Collision(Organism victim, int x, int y);
}
