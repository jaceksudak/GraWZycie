# GraWZycie
One of my first Java OOP programs. It illustrates most of OOP basic concepts. It's a simple life symulator. There are Animal and Plants objects going around the gameboard interacting with each other.


 * This Program is a simulation of a virtual world. The world is squared board in size NxN (N is set by the user at the start).
 * On a board there are N^2 spaces - empty or occupied by one instance of an Organism.
 * Organism is an abstract class which abstract Animals and abstract Plants inherit from.
 * Every Organism has int strength, char sign, int location and flag alive and 3 basic methods - action(), spawn(), collision().
 * There are 5 classes that inherit from Animal class and 3 classes that inherit from Plants class.
 * Each class has some unique properties. They differ with strength, signs and sometimes with implementation of basic methods (e.g. spawn()).
 * Every turn every Organism perform an action().
 * Every Animal inheritor has the same action method. It tries to move to a contiguous space.
 * Depending on what is currently on this space, it will perform different actions:
 * -If the space is empty it will move there.
 * -If the space is occupied by the same class object it will try to spawn a new object of its class around currentLocation.
 * -If the space is occupied by any other Organism it will try to kill/eat it. If it succeed it will move to this space, if not it will die and disappear.
 * Every Plant inheritor cannot move, they can only try to spawn a new object of its class around current location with 50% probability.
 * When an Animal moves over to a space that's occupied it calls the occupant.collision() method.
 * This method decides with Organism wins and what additional effects it will have (for e.g. Guarana when eaten it boost the strength of an attacker).
