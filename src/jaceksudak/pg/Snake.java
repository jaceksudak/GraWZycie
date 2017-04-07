package jaceksudak.pg;

/**
 * Created by Jacek on 2017-04-07.
 * Snake extends an Animal. It has 2 strength, sign 'S'.
 * It has a special collision method - if it's attached by a stronger Animal it dies but it also kills the attackingOrganism.
 */
public class Snake extends Animal {

    public Snake(int location) {
        super(2, location, 'S');
    }

    @Override
    public boolean collision(World world, Organism attackingOrganism) {
        if(this.getStrength()>attackingOrganism.getStrength()) {
            System.out.println(this.toString()+"("+this.getStrength()+") and dies to it.");
            return false;
        } else {
            System.out.println(this.toString()+"("+this.getStrength()+") and kills it but dies to its poison.");
            world.nullToBoard(getLocation());
            this.setAlive(false);
            return false;
        }
    }

    /**
     *  spawn - trying to spawn a new this.class object.
     *  When to same class objects meet the spawn method is executed.
     *  It looks for an empty location around currentLocation. If it finds it then it spawns a new this.class object.
     *  Else nothing happens and spawn fails.
     */
    @Override
    public void spawn(World world) {
        int location = world.generateNearbyEmptyLocation(this.getLocation());
        if(location!=-1) {                                                                  // spawn successful
            System.out.println("and spawns a new "+this.toString()+" on ("+ location % world.getSize() + "," + location / world.getSize() + ").");
            world.addToBoard(location,new Snake(location));
            world.getAddingList().add(world.getBoard().get(location));
        } else {                                                                            // spawn failed - no empty space
            System.out.println("but cannot spawn a new "+this.toString()+" - no empty space around");
        }
    }

    @Override
    public String toString() {
        return "Snake";
    }
}
