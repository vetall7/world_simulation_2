package Animals;

import main.Organism;
import main.World;

import java.util.Random;
import java.util.Vector;

public class Human extends Animal{
    private boolean isSuperPower;
    private int turn_counter;
    private int direction;
    public Human(int x, int y, World world){
        super(x, y, world, 'x', "Human", 5, 4);
        isSuperPower = false;
        turn_counter = -10;
    }
    public boolean GetSuperPower(){
        return isSuperPower;
    }
    public void SetDirection(int direction){
        this.direction = direction;
    }

    public void SetSuperPower(boolean is){
        isSuperPower = is;
    }
    public void SetTurn(int turn_counter){
        this.turn_counter = turn_counter;
    }
    public boolean TarczeAlzura(Organism attacker){
        if (!isSuperPower){
            return false;
        }
        Vector<Integer> x_points = new Vector<>();
        Vector<Integer> y_points = new Vector<>();
        world.SetPoint(attacker.GetY(), attacker.GetX(), null);
        world.FindPoints(this, x_points, y_points);
        if (x_points.size() ==0 ){
            return false;
        }
        Random random = new Random();
        int rand = random.nextInt(x_points.size());
        attacker.SetX(x_points.get(rand));
        attacker.SetY(y_points.get(rand));
        world.SetPoint(attacker.GetY(), attacker.GetX(), attacker);
        world.AddComments("Human scared away " + attacker.GetName());
        return true;
    }

    @Override
    public void Action(int range){
        age++;
        int x_tmp = this.x;
        int y_tmp = this.y;
        x_priv = x;
        y_priv = y;
        if (direction == world.TURN_UP && this.y + 1 < world.GetHeight()){
            y_tmp++;
        }
        if (direction == world.TURN_DOWN && this.y - 1 >= 0){
            y_tmp--;
        }
        if (direction == world.TURN_LEFT && this.x - 1 >= 0){
            x_tmp--;
        }
        if (direction == world.TURN_RIGHT && this.x + 1 < world.GetWidth()){
            x++;
        }

    }
}
