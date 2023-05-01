package Plants;

import main.Organism;
import main.World;

public class Plant extends Organism {

    protected Plant(int x, int y, World world, char sign, String name, int power, int initiative){
        this.x = x;
        this.y = y;
        this.world = world;
        this.sign = sign;
        this.name = name;
        this.power = power;
        this.initiative = initiative;
    }

    protected Plant(){}
    @Override
    public void Collision(Organism victim, int x , int y){}

    @Override
    public void Action(int range) {}
}
