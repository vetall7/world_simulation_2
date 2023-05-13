package Plants;

import Animals.Animal;
import main.Organism;
import main.World;

import java.util.Vector;

public class SosmowskiHogweed extends Plant{
    public SosmowskiHogweed(int x, int y, World world){
        super (x, y, world, 'h', "SosmowskiHogweed", 10, 0);
    }

    @Override
    public void Collision(Organism attacker, int x , int y){
        world.AddComments(this.GetName() + " killed " + attacker.GetName());
        world.SetPoint(x, y, null);
        world.DeleteOrg(attacker);
    }

    @Override
    public void Action(int range){
        Vector<Integer> x_coo = new Vector<>();
        Vector<Integer> y_coo = new Vector<>();
        world.FindNeighbours(this, x_coo, y_coo);
        if (x_coo.isEmpty()){
            return;
        }
        for (int i = 0; i < x_coo.size(); i++){
            if (world.GetPoint(x_coo.get(i), y_coo.get(i)) != null && world.GetPoint(x_coo.get(i), y_coo.get(i)) instanceof Animal){
                world.AddComments("SosmowskiHogweed killed " +  world.GetPoint(x_coo.get(i), y_coo.get(i)).GetName());
                world.DeleteOrg(world.GetPoint(x_coo.get(i), y_coo.get(i)));
                world.SetPoint(y_coo.get(i), x_coo.get(i), null);
            }
        }
    }
}