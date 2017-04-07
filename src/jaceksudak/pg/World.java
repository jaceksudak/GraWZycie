package jaceksudak.pg;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Random;

/**
 * Created by Jacek on 2017-04-05.
 * World is the core class.
 * It contains the most important board ArrayList that contains any Organism that's currently alive.
 * Then there are a few LinkedList defining order in which objects.actions are called.
 * addingList is a list to which just spawned objects are added.
 * At the end of iterating through particular initList, new objects are added from addingList to initList, then the addingList is cleared.
 */

public class World {
    private Random generator = new Random();
    private int size;
    private ArrayList<Organism> board;
    private LinkedList<Organism> init6Mouses;
    private LinkedList<Organism> init5Wolfs;
    private LinkedList<Organism> init4SheepAndThornEaters;
    private LinkedList<Organism> init3Snakes;
    private LinkedList<Organism> init1Plants;
    private LinkedList<Organism> addingList;

    public World(int size) {
        this.size = size;
        this.board = new ArrayList<>();
        this.init6Mouses = new LinkedList<>();
        this.init5Wolfs = new LinkedList<>();
        this.init4SheepAndThornEaters =  new LinkedList<>();
        this.init3Snakes =  new LinkedList<>();
        this.init1Plants =  new LinkedList<>();
        this.addingList = new LinkedList<>();
        initializeBoard();
    }

    /**
     *  executeTurn is a method for executing one turn in a game.
     */
    public void executeTurn() {
        execActionForInitList(init6Mouses);
        execActionForInitList(init5Wolfs);
        execActionForInitList(init4SheepAndThornEaters);
        execActionForInitList(init3Snakes);
        execActionForInitList(init1Plants);
    }

    /**
     *  execActionForInitList is a method that iterates through currentList LinkedList and calls action() method.
     *  It prints the board state after every action().
     *  First it clears the addingList.
     *  When it encounters an object which status alive = false it deletes it.
     *  At the end it adds new Organisms from addingList.
     */
    private void execActionForInitList(LinkedList<Organism> currentList) {
        addingList.clear();
        ListIterator<Organism> iterator=currentList.listIterator();
        while(iterator.hasNext()){
            if(iterator.next().isAlive()) {
                iterator.previous();
                iterator.next().action(this);
                printBoard();
            } else {
                iterator.remove();
            }
        }
        iterator=addingList.listIterator();
        while(iterator.hasNext()) {
            currentList.add(iterator.next());
        }
    }

    /**
     *  initializeBoard is a method that fills the board with nulls and starting Animals and Plants.
     *  First it counts how many Animals and Plants should be added.
     *  The formula for Plants (expect Thorns) is toUpper(size*size/25);
     *  The formula for Animals and Thorns is toUpper(size*size/15);
     */
    private void initializeBoard() {
        for(int i=0; i<size*size; i++)
            board.add(null);

        Double howManyAnimals = new Double(size*size);
        howManyAnimals/=25;
        if(howManyAnimals%1>0.5)
            howManyAnimals++;
        int animalsAmount = howManyAnimals.intValue();
        Double howManyPlants = new Double(size*size);
        howManyPlants/=15;
        if(howManyPlants%1>0.5)
            howManyPlants++;
        int plantsAmount = howManyPlants.intValue();

        for(int i=0;i<plantsAmount;i++) {
            Organism temp = new Grass(generateNearbyEmptyLocation());
            addToBoard(temp.getLocation(),temp);
            init1Plants.add(temp);
        }
        for(int i=0;i<plantsAmount;i++) {
            Organism temp = new Guarana(generateNearbyEmptyLocation());
            addToBoard(temp.getLocation(),temp);
            init1Plants.add(temp);
        }
        for(int i=0;i<animalsAmount;i++) {
            Organism temp = new Thorn(generateNearbyEmptyLocation());
            addToBoard(temp.getLocation(),temp);
            init1Plants.add(temp);
        }

        for(int i=0;i<animalsAmount;i++) {
            Organism temp = new Wolf(generateNearbyEmptyLocation());
            addToBoard(temp.getLocation(),temp);
            init5Wolfs.add(temp);
        }
        for(int i=0;i<animalsAmount;i++) {
            Organism temp = new Sheep(generateNearbyEmptyLocation());
            addToBoard(temp.getLocation(),temp);
            init4SheepAndThornEaters.add(temp);
        }
        for(int i=0;i<animalsAmount;i++) {
            Organism temp = new ThornEater(generateNearbyEmptyLocation());
            addToBoard(temp.getLocation(),temp);
            init4SheepAndThornEaters.add(temp);
        }
        for(int i=0;i<animalsAmount;i++) {
            Organism temp = new Snake(generateNearbyEmptyLocation());
            addToBoard(temp.getLocation(),temp);
            init3Snakes.add(temp);
        }
        for(int i=0;i<animalsAmount;i++) {
            Organism temp = new Mouse(generateNearbyEmptyLocation());
            addToBoard(temp.getLocation(),temp);
            init6Mouses.add(temp);
        }

    }

    /** Method printing current board state */
    public void printBoard() {
        for(int i=size-1;i>=0;i--) {
            for(int j=0; j<size;j++) {
                if(board.get(i*size+j)!=null)
                    System.out.print(board.get(i*size+j).getSign());
                else
                    System.out.print('.');
            }
            System.out.println("");
        }
    }


    /** generateNearbyEmptyLocation return an empty location on a board for a new Organism to be spawned,
    *   it's used at the initialization of the World method   **/
    protected int generateNearbyEmptyLocation() {
        int location = generator.nextInt(this.size*this.size);
        while(board.get(location)!=null) {
            location = generator.nextInt(this.size * this.size);
        }
        return location;
    }


    /** generateNearbyEmptyLocation returns an empty slot around the currentLocation of an Organism.
     *  It's used during action() - moving process and spawning a new Organism object.
     *  At the beginning it checks if the currentLocation is around the edges of the board
     *  and defines the state depending on it. The state = 0 if it's not around the edges.
     *  State: = 1 - when currentLocation is at the bottom left corner,
     *  = 2 - currentLocation is at the bottom line but not at corner,
     *  = 3 - currentLocation is at the bottom right corner,
     *  = 4 - currentLocation is at the right line but not at corner,
     *  = ... - and so on in a counterclockwise order.
     *  After the state is defined the switch statement decides the range of a randomNumber that are possible in particular state.
     *  At the end a new emptyLocation is returned.
     *  If there is no empty location around currentLocation the method returns -1.
     * **/
    protected int generateNearbyEmptyLocation(int currentLocation) {
        int locationType, noOfPossibleLocations;
        if (currentLocation == 0) {
            locationType = 1;
            noOfPossibleLocations = 3;
        } else if (currentLocation < size - 1) {
            locationType = 2;
            noOfPossibleLocations = 5;
        } else if (currentLocation == size - 1) {
            locationType = 3;
            noOfPossibleLocations = 3;
        } else if ((currentLocation % size == size - 1) && (currentLocation != size * size - 1)) {
            locationType = 4;
            noOfPossibleLocations = 5;
        } else if (currentLocation == size * size - 1) {
            locationType = 5;
            noOfPossibleLocations = 3;
        } else if (currentLocation > size * (size - 1)) {
            locationType = 6;
            noOfPossibleLocations = 5;
        } else if (currentLocation == size * (size - 1)) {
            locationType = 7;
            noOfPossibleLocations = 3;
        } else if (currentLocation % size == 0) {
            locationType = 8;
            noOfPossibleLocations = 5;
        } else {
            locationType = 0;
            noOfPossibleLocations = 8;
        }

        int randomNumber;
        int foundLocation;
        ArrayList<Integer> bannedList = new ArrayList<>();
        for(int i=0; i<noOfPossibleLocations; i++) {
            switch(locationType) {
                case 0:
                    randomNumber = generator.nextInt(8)+1;
                    while(bannedList.contains(randomNumber)) {
                        randomNumber = generator.nextInt(8) + 1;
                    }
                    break;
                case 1:
                    randomNumber = generator.nextInt(3)+4;
                    while(bannedList.contains(randomNumber)) {
                        randomNumber = generator.nextInt(3) + 4;
                    }
                    break;
                case 2:
                    randomNumber = generator.nextInt(5)+4;
                    while(bannedList.contains(randomNumber)) {
                        randomNumber = generator.nextInt(5) + 4;
                    }
                    break;
                case 3:
                    randomNumber = generator.nextInt(3)+6;
                    while(bannedList.contains(randomNumber)) {
                        randomNumber = generator.nextInt(3) + 6;
                    }
                    break;
                case 4:
                    randomNumber = (generator.nextInt(5)+6)%8;
                    if(randomNumber==0) randomNumber=8;
                    while(bannedList.contains(randomNumber)) {
                        randomNumber = (generator.nextInt(5) + 6) % 8;
                        if (randomNumber == 0) randomNumber = 8;
                    }
                    break;
                case 5:
                    randomNumber = (generator.nextInt(3)+8)%8;
                    if(randomNumber==0) randomNumber=8;
                    while(bannedList.contains(randomNumber)) {
                        randomNumber = (generator.nextInt(3)+8)%8;
                        if(randomNumber==0) randomNumber=8;
                    }
                    break;
                case 6:
                    randomNumber = (generator.nextInt(5));
                    if(randomNumber==0) randomNumber=8;
                    while(bannedList.contains(randomNumber)) {
                        randomNumber = (generator.nextInt(5));
                        if(randomNumber==0) randomNumber=8;
                    }
                    break;
                case 7:
                    randomNumber = (generator.nextInt(3)+2);
                    while(bannedList.contains(randomNumber)) {
                        randomNumber = (generator.nextInt(3) + 2);
                    }
                    break;
                case 8:
                    randomNumber = (generator.nextInt(5)+2);
                    while(bannedList.contains(randomNumber)) {
                        randomNumber = (generator.nextInt(5) + 2);
                    }
                    break;
                default:
System.out.println("___________________UWAGA !! switch sie wysypal");
                    randomNumber=-1;
            }
            switch (randomNumber) {
                case 1:
                    foundLocation = currentLocation - this.size - 1;
                    break;
                case 2:
                    foundLocation = currentLocation - this.size;
                    break;
                case 3:
                    foundLocation = currentLocation - this.size + 1;
                    break;
                case 4:
                    foundLocation = currentLocation + 1;
                    break;
                case 5:
                    foundLocation = currentLocation + this.size + 1;
                    break;
                case 6:
                    foundLocation = currentLocation + this.size;
                    break;
                case 7:
                    foundLocation = currentLocation + this.size - 1;
                    break;
                case 8:
                    foundLocation = currentLocation -1;
                    break;
                default:
                    foundLocation = currentLocation;
            }

            if(board.get(foundLocation)==null) {
                return foundLocation;
            } else {
                bannedList.add(randomNumber);
            }
        }
        return -1;
    }


    /** generateNearbyLocation returns any space around the currentLocation of an Organism.
     *  It works similar to generateNearbyEmptyLocation but it doesn't checks if the generated location is empty or not.
     * **/
    protected int generateNearbyLocation(int currentLocation) {
        int locationType;
        if (currentLocation == 0) {
            locationType = 1;
        } else if (currentLocation < size - 1) {
            locationType = 2;
        } else if (currentLocation == size - 1) {
            locationType = 3;
        } else if ((currentLocation % size == size - 1) && (currentLocation != size * size - 1)) {
            locationType = 4;
        } else if (currentLocation == size * size - 1) {
            locationType = 5;
        } else if (currentLocation > size * (size - 1)) {
            locationType = 6;
        } else if (currentLocation == size * (size - 1)) {
            locationType = 7;
        } else if (currentLocation % size == 0) {
            locationType = 8;
        } else {
            locationType = 0;
        }

        int randomNumber;
        int foundLocation;
        switch (locationType) {
            case 0:
                randomNumber = generator.nextInt(8) + 1;
                break;
            case 1:
                randomNumber = generator.nextInt(3) + 4;
                break;
            case 2:
                randomNumber = generator.nextInt(5) + 4;
                break;
            case 3:
                randomNumber = generator.nextInt(3) + 6;
                break;
            case 4:
                randomNumber = (generator.nextInt(5) + 6) % 8;
                if (randomNumber == 0) randomNumber = 8;
                break;
            case 5:
                randomNumber = (generator.nextInt(3) + 8) % 8;
                if (randomNumber == 0) randomNumber = 8;
                break;
            case 6:
                randomNumber = (generator.nextInt(5));
                if (randomNumber == 0) randomNumber = 8;
                break;
            case 7:
                randomNumber = (generator.nextInt(3) + 2);
                break;
            case 8:
                randomNumber = (generator.nextInt(5) + 2);
                break;
            default:
System.out.println("___________________UWAGA !! switch sie wysypal Nearby Location");
                randomNumber = -1;
        }
        switch (randomNumber) {
            case 1:
                foundLocation = currentLocation - this.size - 1;
                break;
            case 2:
                foundLocation = currentLocation - this.size;
                break;
            case 3:
                foundLocation = currentLocation - this.size + 1;
                break;
            case 4:
                foundLocation = currentLocation + 1;
                break;
            case 5:
                foundLocation = currentLocation + this.size + 1;
                break;
            case 6:
                foundLocation = currentLocation + this.size;
                break;
            case 7:
                foundLocation = currentLocation + this.size - 1;
                break;
            case 8:
                foundLocation = currentLocation - 1;
                break;
            default:
                foundLocation = currentLocation;
        }
        return foundLocation;
    }


    /** Simple method adding organism in given location
     *  It removes previous Organism (or Null) from the board ArrayList and adds organism in it's place
     */
    protected void addToBoard(int location, Organism organism) {
        board.remove(location);
        board.add(location,organism);
    }

    /** Simple method adding null in given location
     *  It removes previous Organism (or Null) from the board ArrayList and adds null in it's place
     */
    protected void nullToBoard(int location) {
        board.remove(location);
        board.add(location,null);
    }


    protected Random getGenerator() {
        return generator;
    }

    public int getSize() {
        return size;
    }

    public ArrayList<Organism> getBoard() {
        return board;
    }

    protected LinkedList<Organism> getAddingList() { return addingList; }
}
