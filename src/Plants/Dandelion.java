package Plants;
import main.World;
public class Dandelion extends Plant{
    public Dandelion(int x, int y, World world){
        super(x, y, world, 'd', "Dandelion", 0,0);
    }
    @Override
    public void Action(int range){
        super.Action(range);
        super.Action(range);
        super.Action(range);
    }
}
