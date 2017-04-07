package jaceksudak.pg;

/**
 * Created by Jacek on 2017-04-05.
 * Organism is the basic class from which Animal and Plant classes inherit from.
 * It defines the basic collision method for all Organisms.
 */
public abstract class Organism {
    private int strength;
    private int location;
    private char sign;
    private boolean alive;

    public abstract void action(World world);
    public abstract void spawn(World world);

    /** When an Animal attacks an Organism it execute Organism's collision method.
     *  Collision method compares attackingOrganism strength and this.Organism strength.
     *  If the attackingOrganism strength is greater or equal to this.strength this Organism dies. Method return true.
     *  If the attackingOrganism strength is smaller than this.strength nothing happens. Method returns false.
     */
    public boolean collision(World world, Organism attackingOrganism) {
        if(this.getStrength()>attackingOrganism.getStrength()) {
            System.out.println(this.toString()+"("+this.getStrength()+") and dies to it.");
            return false;
        } else {
            System.out.println(this.toString()+"("+this.getStrength()+") and kills it.");
            world.nullToBoard(getLocation());
            this.setAlive(false);
            return true;
        }
    }

    protected Organism(int strength, int location, char sign) {
        this.strength = strength;
        this.location = location;
        this.sign = sign;
        this.alive = true;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public int getStrength() {
        return strength;
    }

    public int getLocation() {
        return location;
    }

    public char getSign() {
        return sign;
    }

    public void setStrength(int strength) {
        this.strength += strength;
    }

    public void setLocation(int location) {
        this.location = location;
    }

}
