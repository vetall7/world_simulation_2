package main;
import Animals.*;
import Plants.*;
import Plants.Belladonna;

import java.util.Random;
import java.util.Vector;

public class WorldGenerator {
    private World world;
    public WorldGenerator(World world){
        this.world = world;
    }
    private void CoordinateGenerate(int x[], int y[], Vector<Integer> ocupied){
        Random random = new Random();
        x[0] = random.nextInt(world.GetWidth());
        y[0] = random.nextInt(world.GetHeight());
        for (int i = 0; i < ocupied.size(); i+=2){
            if (ocupied.get(i) == x[0] && ocupied.get(i+1) == y[0]){
                x[0] = random.nextInt(world.GetWidth());
                y[0] = random.nextInt(world.GetHeight());
                i = -2;
            }
        }
        ocupied.add(x[0]);
        ocupied.add(y[0]);
    }
    public void Generate() {
        int x[] = new int[1];
        int y[] = new int[1];
        Vector<Integer> ocupied = new Vector<>();
        int counter = ((world.GetHeight()) * world.GetWidth() / 4) * 2;
        if (counter < 20) {
            counter += 9;
        }

        CoordinateGenerate(x, y, ocupied);
        Human human = new Human(x[0], y[0], world);
        world.AddOrganism(human);
        world.SetHuman(human);
        human.SetDirection(world.TURN_NONE);

        for (int i = 0; i < counter/20; i++){

            CoordinateGenerate(x, y, ocupied);
            Organism wolf = new Wolf(x[0], y[0], world);
            world.AddOrganism(wolf);

            CoordinateGenerate(x, y, ocupied);
            Organism fox = new Fox(x[0], y[0], world);
            world.AddOrganism(fox);

            CoordinateGenerate(x, y, ocupied);
            Organism turtle = new Turtle(x[0], y[0], world);
            world.AddOrganism(turtle);

            CoordinateGenerate(x, y, ocupied);
            Organism antelope = new Antelope(x[0], y[0], world);
            world.AddOrganism(antelope);

            CoordinateGenerate(x, y, ocupied);
            Organism sheep = new Sheep(x[0], y[0], world);
            world.AddOrganism(sheep);

            CoordinateGenerate(x, y, ocupied);
            Organism belladonna = new Belladonna(x[0], y[0], world);
            world.AddOrganism(belladonna);

            CoordinateGenerate(x, y, ocupied);
            Organism dandelion = new Dandelion(x[0], y[0], world);
            world.AddOrganism(dandelion);

            CoordinateGenerate(x, y, ocupied);
            Organism grass =  new Grass(x[0], y[0], world);
            world.AddOrganism(grass);

            CoordinateGenerate(x, y, ocupied);
            Organism guarana = new Guarana(x[0], y[0], world);
            world.AddOrganism(guarana);

            CoordinateGenerate(x, y, ocupied);
            Organism Sosmowskihogweed = new SosmowskiHogweed(x[0], y[0], world);
            world.AddOrganism(Sosmowskihogweed);
        }
    }
}
