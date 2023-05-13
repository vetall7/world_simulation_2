package Plants;

import Animals.*;
import main.Organism;
import main.World;

import java.util.Random;
import java.util.Vector;

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
    public void Action(int range) {
        Random random = new Random();
        int random_number = random.nextInt(20); // szansa pojawienia nowego organizmu = 1/20
        if (random_number == 0){
            Organism new_plant = NewPlant();
            if (new_plant != null){
                world.AddOrganism(new_plant);
            }
        }
    }

    private Organism NewPlant(){ // tworzenie nowej rosliny
        Vector<Integer> x = new Vector<>();
        Vector<Integer> y = new Vector<>();
        world.FindPoints(this, x, y);
        if (x.size() == 0){
            return null;
        }
        Random random = new Random();
        int point = random.nextInt(x.size());
        int x_temp = x.get(point);
        int y_temp = y.get(point);
        // sprawdzanie jakiego gatunku jest roslina i tworzenie takiej samej
        if (this instanceof Grass){
            Grass w = new Grass(x_temp, y_temp, world);
            world.AddComments("Grass was born");
            return w;
        }
        if (this instanceof Belladonna){
            Belladonna f = new Belladonna(x_temp, y_temp, world);
            world.AddComments("Belladonna was born");
            return f;
        }
        if (this instanceof Dandelion){
            Dandelion s = new Dandelion(x_temp, y_temp, world);
            world.AddComments("Dandelion was born");
            return s;
        }
        if (this instanceof Guarana){
            Guarana t = new Guarana(x_temp, y_temp, world);
            world.AddComments("Guarana was born");
            return t;
        }
        if (this instanceof SosmowskiHogweed){
            SosmowskiHogweed a = new SosmowskiHogweed(x_temp, y_temp, world);
            world.AddComments("SosmowskiHogweed was born");
            return a;
        }
        return null;
    }
}
