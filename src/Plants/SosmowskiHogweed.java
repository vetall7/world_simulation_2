package Plants;

import Animals.Animal;
import main.Organism;
import main.World;

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
        for (int i = -1; i <= 1; i++){
            for (int j = -1; j <= 1; j++){
                if (i == 0 && j ==0){
                    continue;
                }
                if (y + i >= 0 && y + i < world.GetHeight() && x + j >= 0 && x +j < world.GetWidth()
                && world.GetPoint(x + j, y + i) != null && world.GetPoint(x + j, y + i) instanceof Animal){
                    world.AddComments("SosmowskiHogweed killed " +  world.GetPoint(x + j, y + i).GetName());
                    //world.DeleteOrg(world.GetPoint(x + j, y + i));
                    //world.SetPoint(y + i, x + j, null);
                }
            }
        }
    }
}