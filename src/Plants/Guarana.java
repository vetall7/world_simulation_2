package Plants;
import main.Organism;
import main.World;
public class Guarana extends Plant{
    public Guarana(int x, int y, World world) {
        super (x, y, world, 'g', "Guarana", 0, 0);
    }
    @Override
    public void Collision(Organism attacker, int x, int y) {
        attacker.PowerIncrease(3); // zwierze ktore zjadlo zwieksza sile o 3
    }
}
