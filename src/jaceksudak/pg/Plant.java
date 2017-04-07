package jaceksudak.pg;

/**
 * Created by Jacek on 2017-04-05.
 * Plant extends the Organism. It defines default action and collision method for inheriting classes.
 * action - doing nothing else but trying to spawn a new Plant
 * collision - die switch alive to false and delete from board
 */
public abstract class Plant extends Organism {

    public Plant(int strength, int location, char sign) {
        super(strength, location, sign);
    }

    public void action(World world) { spawn(world); }

    @Override
    public boolean collision(World world, Organism attackingOrganism) {
        System.out.println(this.toString()+"("+this.getStrength()+") and eats it.");
        world.nullToBoard(getLocation());
        this.setAlive(false);
        return true;
    }
}
