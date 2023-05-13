package main;
import Animals.*;
import Plants.*;
import Plants.Belladonna;

import java.io.*;
import java.util.Random;
import java.util.Scanner;
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
    public WorldGenerator(){}
    public World GetWorld(){
        return world;
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

    public void SaveGame() {
        String filename = "game.txt";
        try
        {
            File file = new File(filename);
            FileWriter fw = new FileWriter(filename, false);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(world.GetCellNeighboursCounter() + " ");
            bw.write(Integer.toString(world.GetOrgCounter()) + " ");
            bw.write(Integer.toString(world.GetHeight()) + " ");
            bw.write(Integer.toString(world.GetWidth()) + " ");
            bw.write(Integer.toString(world.GetTurn()) + " ");
            for (Organism i : world.getOrganisms()){
                bw.write(i.GetName() + " ");
                bw.write(Integer.toString(i.GetX()) + " ");
                bw.write(Integer.toString(i.GetY()) + " ");
                bw.write(Integer.toString(i.GetXpriv()) + " ");
                bw.write(Integer.toString(i.GetYpriv()) + " ");
                bw.write(Integer.toString(i.GetAge()) + " ");
                bw.write(Integer.toString(i.GetPower()) + " ");
                if (i instanceof Human){
                    if (((Human) i).GetSuperPower()) {
                        bw.write(Integer.toString(1) + " ");
                    }
                    else {
                        bw.write(Integer.toString(0) + " ");
                    }
                    bw.write(Integer.toString(((Human) i).GetTurn()) + " ");
                }
            }
            bw.close();
            world.AddComments("The game has been saved");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void ReadGame(){
        String fileName = "game.txt";
        try (Scanner scanner = new Scanner(new File(fileName))) {
            int counter_org, height, width, turn, neighbours_counter;
            neighbours_counter = Integer.parseInt(scanner.next());
            counter_org = Integer.parseInt(scanner.next());
            height = Integer.parseInt(scanner.next());
            width = Integer.parseInt(scanner.next());
            turn = Integer.parseInt(scanner.next());
            if (neighbours_counter == 4){
                world = new SimpleWorld(height, width);
            }else {
                world = new HexWorld(height, width);
            }
            world.SetTurn(turn);
            int x_tmp, y_tmp, x_p, y_p, age, power, super_turn;
            int is_super;
            String name;
            for (int i = 0; i < counter_org; i++){
                name = scanner.next();
                if (name == null){
                    break;
                }
                x_tmp = Integer.parseInt(scanner.next());
                y_tmp = Integer.parseInt(scanner.next());;
                x_p = Integer.parseInt(scanner.next());
                y_p = Integer.parseInt(scanner.next());
                age = Integer.parseInt(scanner.next());
                power = Integer.parseInt(scanner.next());
                Organism new_org = ReadOrganism(name, x_tmp, y_tmp);
                if (new_org == null){
                    continue;
                }
                if (new_org instanceof Human){
                    is_super = Integer.parseInt(scanner.next());
                    if (is_super == 0){
                        ((Human) new_org).SetSuperPower(false);
                    }else {
                        ((Human) new_org).SetSuperPower(true);
                    }
                    super_turn = Integer.parseInt(scanner.next());
                    ((Human) new_org).SetTurn(super_turn);
                    world.SetHuman(((Human) new_org));
                }
                new_org.SetAge(age);
                new_org.SetXpriv(x_p);
                new_org.SetYpriv(y_p);
                new_org.SetPower(power);
                world.AddOrganism(new_org);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Organism ReadOrganism(String name, int x, int y) {
        Organism temp_org = null;
        if (name.equals("Wolf")) {
            temp_org = new Wolf(x, y, world);
        } else if (name.equals("Antelope")) {
            temp_org = new Antelope(x, y, world);
        } else if (name.equals("SosmowskiHogweed")) {
            temp_org = new SosmowskiHogweed(x, y, world);
        } else if (name.equals("Guarana")) {
            temp_org = new Guarana(x, y, world);
        } else if (name.equals("Fox")) {
            temp_org = new Fox(x, y, world);
        } else if (name.equals("Dandelion")) {
            temp_org = new Dandelion(x, y, world);
        } else if (name.equals("Sheep")) {
            temp_org = new Sheep(x, y, world);
        } else if (name.equals("Grass")) {
            temp_org = new Grass(x, y, world);
        } else if (name.equals("Belladonna")) {
            temp_org = new Belladonna(x, y, world);
        } else if (name.equals("Human")) {
            temp_org = new Human(x, y, world);
        } else if (name.equals("Turtle")) {
            temp_org = new Turtle(x, y, world);
        }
        return temp_org;
    }
}
