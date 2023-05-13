package Animals;
import main.World;

import java.util.Random;

public class Antelope extends  Animal{

    public Antelope(int x, int y, World world) {
        super(x, y, world, 'a', "Antelope", 4,4);
    }

    @Override
    public void Action(int range){
        super.Action(2);
    }  // zasieg ruchu dla Antylopy = 2

    @Override
    public boolean IsRunAway(){
        Random random = new Random();
        int rand = random.nextInt(10);
        if (rand < 5){  // sprawdzanie czy ucieka Antylopa przed walka (szansa 50%)
            if (x - 2 > 0 && world.GetPoint(x-2, y) == null){
                    world.SetPoint(y, x, null); // poprzednie miejsce zajmowane przez antylope oznaczamy jako puste
                    x = x-2; // przemieszczamy antylope
                    world.SetPoint(y, x, this); // oznaczamy nowe pole jako zajete
                }
            else if (x + 2 < world.GetWidth() && world.GetPoint(x+2, y) == null){
                world.SetPoint(y, x, null);
                x = x+2;
                world.SetPoint(y, x, this);
            }
            else if (y - 2 > 0 && world.GetPoint(x, y-2) == null){
                world.SetPoint(y, x, null);
                y = y-2;
                world.SetPoint(y, x, this);
            }else if (y + 2 < world.GetHeight() && world.GetPoint(x, y+2) == null){
                world.SetPoint(y, x, null);
                y = y+2;
                world.SetPoint(y, x, this);
            }
            world.AddComments("Antelope ran away [" + x + ";" + y + "]");
            return true;
        }
        return false;
    }
}
