package jaceksudak.pg;

/**
 * Created by Jacek on 2017-04-07.
 * Mouse extends an Animal. It has 1 strength, sign 'M'.
 * It has a special collision method - if it's attached by a stronger Animal it tries to escape to another empty space.
 * If failed cause of lack of empty space - it dies.
 * It cannot escape from a Snake.
 */
public class Mouse extends Animal {
    public Mouse(int location) {
        super(1, location, 'M');
    }

    @Override
    public boolean collision(World world, Organism attackingOrganism) {
        if(this.getStrength()>attackingOrganism.getStrength()) {
            System.out.println(this.toString()+"("+this.getStrength()+") and dies to it.");
            return false;
        } else {
            int location = world.generateNearbyEmptyLocation(this.getLocation());
            if ((location != -1)&&(attackingOrganism.getClass()!=Snake.class)) {
                world.addToBoard(location,this);
                world.addToBoard(this.getLocation(),null);
                this.setLocation(location);
                System.out.println(this.toString()+"("+this.getStrength()+") and chase it away to ("+ location % world.getSize() + "," + location / world.getSize() + ").");
            } else {
                System.out.println(this.toString()+"("+this.getStrength()+") and kills it because it fails to escape.");
                world.nullToBoard(this.getLocation());
                this.setAlive(false);
            }
            return true;
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
            world.addToBoard(location,new Mouse(location));
            world.getAddingList().add(world.getBoard().get(location));
        } else {                                                                            // spawn failed - no empty space
            System.out.println("but cannot spawn a new "+this.toString()+" - no empty space around");
        }
    }

    @Override
    public String toString() {
        return "Mouse";
    }
}
