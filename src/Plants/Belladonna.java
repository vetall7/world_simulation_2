package Plants;
import main.Organism;
import main.World;
public class Belladonna extends Plant{
    public Belladonna(int x, int y, World world ) {
        super(x,y,world,'b',"Belladonna", 99, 0);
    }
    @Override
    public void Collision(Organism attacker, int x, int y){
        world.AddComments(this.GetName() + " killed " + attacker.GetName());
        world.SetPoint(x, y, null); // zwierze ktore zjadlo umiera  (ustalenia zajmowanego wczesniej pola jako puste)
        world.DeleteOrg(attacker); // usuniecie organizmy ze swiatu
    }
}
