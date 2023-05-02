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
        super(x, y, world, 'x', "Human", 5, 6);
        isSuperPower = false;
        turn_counter = -10;
    }
    public boolean GetSuperPower(){
        return isSuperPower;
    }
    public void SetDirection(int direction){
        this.direction = direction;
    }

    public int GetTurn(){
        return turn_counter;
    }

    public void SetSuperPower(boolean is){
        isSuperPower = is;
    }
    public void SetTurn(int turn_counter){
        this.turn_counter = turn_counter;
    }
    @Override
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
        if (direction == world.TURN_DOWN && this.y + 1 < world.GetHeight()){
            y_tmp++;
        }
        if (direction == world.TURN_UP && this.y - 1 >= 0){
            y_tmp--;
        }
        if (direction == world.TURN_LEFT && this.x - 1 >= 0){
            x_tmp--;
        }
        if (direction == world.TURN_RIGHT && this.x + 1 < world.GetWidth()){
            x_tmp++;
        }
        if (direction == world.TURN_SUPER){
            if (world.GetTurn() <= turn_counter + 10){
                world.AddComments("SUPERPOWER CANNOT BE ACTIVATED");
            }
            else {
                turn_counter = world.GetTurn();
                world.AddComments("ACTIVATED SUPERPOWER");
                isSuperPower = true;
            }
        }
        if (isSuperPower){
            if (world.GetTurn() == turn_counter + 5){
                isSuperPower = false;
                world.AddComments("SUPERPOWER DEACTIVATED");
            }
        }
        if (world.GetPoint(x_tmp, y_tmp) != null){
            this.Collision(world.GetPoint(x_tmp, y_tmp), x_tmp, y_tmp);
        }
        else
        {
            this.x = x_tmp;
            this.y = y_tmp;
            world.SetPoint(y_priv, x_priv, null);
            world.SetPoint(y, x, this);
            world.AddComments(this.GetName() + " moved form [" + x_priv + ";" + y_priv + "]" + " to [" + x + ";" + y + "]");
        }
    }
}
