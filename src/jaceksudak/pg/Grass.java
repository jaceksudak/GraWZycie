package jaceksudak.pg;

/**
 * Created by Jacek on 2017-04-05.
 * Grass extends a Plant and is the most basic Plant extension. It has 0 strength, sign 'G', 50% chance for a new spawn and no special collision method.
 */
public class Grass extends Plant {


    public Grass(int location) {
        super(0, location, 'G');
    }

    @Override
    public void spawn(World world) {
        int location;
        System.out.print("Log: \tGrass on space (" + this.getLocation() % world.getSize() + "," + this.getLocation() / world.getSize() + ") ");
        if (world.getGenerator().nextInt(2) == 1) { /// 50% chance for spawn
            location = world.generateNearbyEmptyLocation(this.getLocation());
            System.out.print("succeeded to spawn new Grass");
            if (location != -1) {
                world.addToBoard(location, new Grass(location));
                world.getAddingList().add(world.getBoard().get(location));
                System.out.println(" on space (" + location % world.getSize() +  "," + location / world.getSize() + ").");
            } else {
                System.out.println(" but there is no empty space around to put it. Spawn failed.");
            }
        } else
            System.out.println("failed to spawned a new Grass.");

    }

    @Override
    public String toString() {
        return "Grass";
    }
}
