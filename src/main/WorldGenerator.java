package main;
import java.util.Random;
public class WorldGenerator {
    private World world;
    public WorldGenerator(World world){
        this.world = world;
    }
    public void Generate() {
        int x, y;
        Random random = new Random();

        x = random.nextInt(world.GetWidth());
        y = random.nextInt(world.GetHeight());

        int counter = ((world.GetHeight()) * world.GetWidth() / 4) * 2;
        if (counter < 20) {
            counter += 9;
        }

    }
}
