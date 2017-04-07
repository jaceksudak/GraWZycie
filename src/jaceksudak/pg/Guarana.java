package jaceksudak.pg;

/**
 * Created by Jacek on 2017-04-06.
 * Guarana extends a Plant and is similar to basic Grass but when eaten by another Organism (collision method) it boost the attackingOrganism strength by 3;
 * It's sign is 'U'.
 */
public class Guarana extends Plant {

    public Guarana(int location) {
        super(0, location,'U');
    }

    @Override
    public void spawn(World world) {
        int location;
        System.out.print("Log: \tGuarana on space (" + this.getLocation() % world.getSize() + "," + this.getLocation() / world.getSize() + ") ");
        if (world.getGenerator().nextInt(2) == 1) { /// 50% chance for spawn
            location = world.generateNearbyEmptyLocation(this.getLocation());
            System.out.print("succeeded to spawn new Guarana");
            if (location != -1) {
                world.addToBoard(location, new Guarana(location));
                world.getAddingList().add(world.getBoard().get(location));
                System.out.println(" on space (" + location % world.getSize() +  "," + location / world.getSize() + ").");
            } else {
                System.out.println(" but there is no empty space around to put it. Spawn failed.");
            }
        } else
            System.out.println("failed to spawned a new Guarana.");
    }

    @Override
    public boolean collision(World world, Organism attackingOrganism) {
        System.out.println(this.toString()+"("+this.getStrength()+") and eats it gaining 3 strength.");
        attackingOrganism.setStrength(3);
        world.nullToBoard(getLocation());
        this.setAlive(false);
        return true;
    }

    @Override
    public String toString() {
        return "Guarana";
    }
}
