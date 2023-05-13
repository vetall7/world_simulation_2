package Animals;

import Plants.Plant;
import main.Organism;
import main.World;

import java.util.Random;
import java.util.Vector;

public class Animal extends Organism {
    protected Animal(int x, int y, World world, char sign, String name, int power, int initiative){
        this.x = x;
        this.y = y;
        this.world = world;
        this.sign = sign;
        this.name = name;
        this.power = power;
        this.initiative = initiative;
        age = 0;
    }
    protected Animal(){}

    @Override
    public void Action(int range){
        age++;
        x_priv = x;
        y_priv = y;
        Random random = new Random();
        int rand_num = random.nextInt(world.GetCellNeighboursCounter());
        int x = this.x;
        int y = this.y;
        boolean action = false;
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
            else if (rand_num == 4) {
                if (y - range < 0 || x + range >= world.GetWidth()) {
                    rand_num = 5;
                }
                else {
                    x += range;
                    y -= range;
                    action = true;
                }
            }
            else if (rand_num == 5) {
                if (y + range >= world.GetHeight() || x - range < 0) {
                    rand_num = 1;
                }
                else {
                    x -= range;
                    y += range;
                    action = true;
                }
            }
        }

        if (world.GetPoint(x,y) != null){
            if (world.GetPoint(x, y).TarczeAlzura(this)){  // trzeba zeby zwierze zaatakowalo czlowieka
                return;
            }
            if (IsRunAway() == true){
              return;
            }
            Collision(world.GetPoint(x, y), x, y);
        }
        else {
            this.x = x;
            this.y = y;
            world.SetPoint(y_priv,x_priv, null);
            world.SetPoint(this.y,this.x, this);
            world.AddComments(this.GetName() + " moved from [" + x_priv + ";" + y_priv + "]" + " to [" + x + ";" + y + "]");
        }
    }
    @Override
    public void Collision(Organism victim, int x, int y){
        if (victim.GetAge() == 0){
           world.AddComments(this.GetName() + "stands in the same place");
            return;
        }
        if (victim.GetSign() == this.GetSign()){
            Organism temp = NewOrganizm(victim);
            if (temp != null){
                world.AddOrganism(temp);
            }
        }
       else if (this.GetPower() > victim.GetPower()){
           if (victim.DidDeflectedAttack(this)){
               return;
           }
           world.AddComments(this.GetName() + " killed " + victim.GetName());
            this.x = x;
            this.y = y;
            world.SetPoint(y_priv, x_priv, null);
            world.SetPoint(y, x, this);
            if (victim instanceof Plant){
                victim.Collision(this, x, y);
            }
            world.DeleteOrg(victim);
        }else if (this.GetPower() <= victim.GetPower()){
            world.AddComments(victim.GetName() + " killed " + this.GetName());
            world.SetPoint(y_priv, x_priv, null);
            world.DeleteOrg(this);
        }
    }

    private Organism NewOrganizm(Organism victim){
        Vector<Integer> x = new Vector<>();
        Vector<Integer> y = new Vector<>();
        world.FindPoints(this, x, y);
        world.FindPoints(victim, x, y);
        if (x.size() == 0){
            return null;
        }
        Random random = new Random();
        int point = random.nextInt(x.size());
        int x_temp = x.get(point);
        int y_temp = y.get(point);

        if (this instanceof Wolf){
            Wolf w = new Wolf(x_temp, y_temp, world);
            world.AddComments("Wolf was born");
            return w;
        }
        if (this instanceof Fox){
            Fox f = new Fox(x_temp, y_temp, world);
            world.AddComments("Fox was born");
            return f;
        }
        if (this instanceof Sheep){
            Sheep s = new Sheep(x_temp, y_temp, world);
            world.AddComments("Sheep was born");
            return s;
        }
        if (this instanceof Turtle){
            Turtle t = new Turtle(x_temp, y_temp, world);
            world.AddComments("Turtle was born");
            return t;
        }
        if (this instanceof Antelope){
            Antelope a = new Antelope(x_temp, y_temp, world);
            world.AddComments("Antelope was born");
            return a;
        }
        return null;
    }

    public boolean IsRunAway(){return false;}
}
