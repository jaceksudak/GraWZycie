package jaceksudak.pg;

/**
 * Created by Jacek on 2017-04-06.
 * Thorn extends a Plant. It has strength = 2, sign 'T', and 100% spawn chance. Also has a special collision method - when eaten by ThornEater it boosts its strength by 2.
 */
public class Thorn extends Plant {
    public Thorn(int location) {
        super(2, location,'T');
    }

    @Override
    public void spawn(World world) {
        System.out.print("Log: \tThorn on space (" + this.getLocation() % world.getSize() + "," + this.getLocation() / world.getSize() +
                ") ");
        int location = world.generateNearbyEmptyLocation(this.getLocation());
        if(location!=-1) {
            world.addToBoard(location,new Thorn(location));
            world.getAddingList().add(world.getBoard().get(location));
            System.out.println(" spawned new Thorn on space (" + location % world.getSize() +  "," + location / world.getSize() + ").");
        } else
            System.out.println(" failed to spawn thorn - no empty space around.");
    }

    @Override
    public boolean collision(World world, Organism attackingOrganism) {
        if(this.getStrength()>attackingOrganism.getStrength()) {
            System.out.println(this.toString() + "(" + this.getStrength() + ") and dies to its thorns.");
            return false;
        } else {
            System.out.print(this.toString()+"("+this.getStrength()+") and eats it");
            if(attackingOrganism.getClass()==ThornEater.class) {
                attackingOrganism.setStrength(2);
                System.out.print(" gaining 2 strength");
            }
            world.nullToBoard(getLocation());
            this.setAlive(false);
            System.out.println(".");
            return true;
        }
    }

    @Override
    public String toString() {
        return "Thorn";
    }
}