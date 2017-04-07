package jaceksudak.pg;

/**
 * Created by Jacek on 2017-04-05.
 * Animal extends the Organism. It defines default action method for inheriting classes.
 */
public abstract class Animal extends Organism {

    protected Animal(int strength, int location, char sign) {
        super(strength, location, sign);
    }

    /**
     *  action - trying to move to nearby space. It can have 3 different effects:
     * 1. Move if space is empty.
     * 2. Try to spawn new object of this.class if space is taken by same class object. (execute spawn method)
     * 3. Fight if the space is taken by any other Organism (execute the collision method of an attacked Organism)
     */
    @Override
    public void action(World world) {
        int actionLocation = world.generateNearbyLocation(getLocation());
        System.out.print("Log: \t"+this.toString()+"("+this.getStrength()+") on space (" + this.getLocation() % world.getSize() + "," + this.getLocation() / world.getSize() + ") ");
        System.out.println("tries to move to (" + actionLocation % world.getSize() + "," + actionLocation / world.getSize() + ").");
        if(world.getBoard().get(actionLocation)==null) {                                    // move to empty location
            System.out.println("\t\tIt succeeded.");
            world.addToBoard(actionLocation,this);
            world.addToBoard(this.getLocation(),null);
            this.setLocation(actionLocation);
        } else if(world.getBoard().get(actionLocation).getClass()==this.getClass()) {      // spawn new Animal
            System.out.print("\t\tIt encounter same class Animal ");
            spawn(world);
        } else {                                                                            // fight
            System.out.print("\t\tIt encounter ");
            if(world.getBoard().get(actionLocation).collision(world,this)) { // win, moving over
                world.addToBoard(actionLocation,this);
                world.addToBoard(this.getLocation(),null);
                this.setLocation(actionLocation);
            } else {                                                                        // lose, dieing
                world.nullToBoard(getLocation());
                this.setAlive(false);
            }
        }
    }

    /**
     *  spawn - trying to spawn a new this.class object.
     *  When to same class objects meet the spawn method is executed.
     *  It looks for an empty location around currentLocation. If it finds it then it spawns a new this.class object.
     *  Else nothing happens and spawn fails.
     */
    @Override
    public abstract void spawn(World world);
}
