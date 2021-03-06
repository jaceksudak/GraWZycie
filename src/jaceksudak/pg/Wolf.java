package jaceksudak.pg;

/**
 * Created by Jacek on 2017-04-06.
 * Wolf extends an Animal. It has 9 strength, sign 'W', no special collision method.
 */
public class Wolf extends Animal {

    public Wolf(int location) {
        super(9, location, 'W');
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
            world.addToBoard(location,new Wolf(location));
            world.getAddingList().add(world.getBoard().get(location));
        } else {                                                                            // spawn failed - no empty space
            System.out.println("but cannot spawn a new "+this.toString()+" - no empty space around");
        }
    }

    @Override
    public String toString() {
        return "Wolf";
    }
}
