package Animals;

import main.Organism;
import main.World;
public class Animal extends Organism {

    protected Animal(int x, int y, World world, char sign, String name, int power, int initiative){
        this.x = x;
        this.y = y;
        this.world = world;
        this.sign = sign;
        this.name = name;
        this.power = power;
        this.initiative = initiative;
    }

    protected Animal(){}

    @Override
    public void Collision(){}

    @Override
    public void Action(){}
}
