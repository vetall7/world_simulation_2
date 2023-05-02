package Animals;
import main.World;

import java.util.Random;

public class Fox extends Animal{

    public Fox(int x, int y, World world) {
        super (x,y,world,'f', "Fox", 3, 7);
    }

    @Override
    public void Action(int range){
        age++;
        x_priv = x;
        y_priv = y;
        Random random = new Random();
        int rand_num = random.nextInt(4);
        int x = this.x;
        int y = this.y;
        boolean action = false;
        int counter = 0;
        for (int i = -1; i <= 1; i+=2){
            if (x + i >= 0 && x + i < world.GetWidth() && world.GetPoint(x+i, y) != null && world.GetPoint(x+i, y).GetPower() > power){
                counter++;
            }
            if (y + i >= 0 && y + i < world.GetHeight() && world.GetPoint(x, y+i) != null && world.GetPoint(x, y+i).GetPower() > power){
                counter++;
            }
        }
        if (counter >= 4){
            return;
        }
        while (!action){
            if (rand_num == 0) {
                if (x + range >= world.GetWidth()) {
                    rand_num = 1;
                }
                else {
                    x += range;
                    action = true;
                }
            }
            else if (rand_num == 1) {
                if (x - range < 0) {
                    rand_num = 2;
                }
                else {
                    x -= range;
                    action = true;
                }
            }
            else if (rand_num == 2) {
                if (y - range < 0) {
                    rand_num = 3;
                }
                else {
                    y -= range;
                    action = true;
                }
            }
            else if (rand_num == 3) {
                if (y + range >= world.GetHeight()) {
                    rand_num = 1;
                }
                else {
                    y += range;
                    action = true;
                }
            }
            if (world.GetPoint(x, y) != null && world.GetPoint(x,y).GetPower() > power){
                action = false;
                rand_num = random.nextInt(4);
            }
        }

        if (world.GetPoint(x,y) != null){
            //if (IsRunAway() == true){
            //return;
            //}
            Collision(world.GetPoint(x, y), x, y);
        }
        else {
            this.x = x;
            this.y = y;
            world.SetPoint(y_priv,x_priv, null);
            world.SetPoint(this.y,this.x, this);
            world.AddComments(this.GetName() + " moved ");
        }
    }
}
