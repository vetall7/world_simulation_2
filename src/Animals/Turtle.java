package Animals;

import main.Organism;
import main.World;

public class Turtle extends Animal{

    public Turtle(int x, int y, World world){
        super (x,y,world, 't', "Turtle", 2, 1);
    }
    @Override
    public boolean  DidDeflectedAttack(Organism attacker)
    {
        if (attacker.GetPower() < 5){
            attacker.SetX(attacker.GetXpriv());
            attacker.SetY(attacker.GetYpriv());
            world.AddComments("Turtle deflected attack");
            return true;
        }
        return false;
    }
}
