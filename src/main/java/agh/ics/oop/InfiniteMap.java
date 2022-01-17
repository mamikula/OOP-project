package agh.ics.oop;


import java.util.*;
import java.lang.Math;

public class InfiniteMap implements IPositionChangeObserver, IWorldMap {

    protected HashMap<Vector2d, LinkedList<Animal>> animalsLinked;
    protected LinkedList<Animal> animals = new LinkedList<>();
    protected Map<Vector2d, Grass> grasses;


    //    map
    private int mapHeight;  // final
    private int mapWidth;
    private int plantEnergy;
    private int startEnergy;    // czy to na pewno część mapy?
    private int dailyEnergyCost;
    private int animalsAtStart;
//    sawanna

    private Vector2d sawannaUR;
    private Vector2d sawannaLL;
    //    jungle
    private int jungleHeight;
    private int jungleWidth;
    private int jungleAnimalsCount;
    private int jungleGrassesCount;
    private double jungleRatio;
    private Vector2d jungleUR;
    private Vector2d jungleLL;


    public InfiniteMap(int mapHeight, int mapWidth, double jungleRatio, int startEnergy, int animalsAtStart) {
//map
        this.startEnergy = startEnergy;
        this.mapHeight = mapHeight;
        this.mapWidth = mapWidth;
        this.animalsAtStart = 10;
        this.grasses = new LinkedHashMap<>();
        this.animalsLinked = new HashMap<Vector2d, LinkedList<Animal>>();
        this.dailyEnergyCost = -1;
//sawanna
        this.sawannaLL = new Vector2d(0, 0);
        this.sawannaUR = new Vector2d(mapWidth, mapHeight);
//jungle
        double factor = Math.sqrt(jungleRatio);
        this.jungleHeight = (int) (mapHeight * factor);
        this.jungleWidth = (int) (mapWidth * factor);
        this.jungleLL = new Vector2d(mapWidth / 2 - jungleWidth / 2, mapHeight / 2 - jungleHeight / 2);
        this.jungleUR = new Vector2d(mapWidth / 2 + jungleWidth / 2, mapHeight / 2 + jungleHeight / 2);
    }


    public void removeAnimal(Vector2d key, Animal object) {
        if (this.animalsLinked.get(key) != null) {
            this.animalsLinked.get(key).remove(object);
            if (this.animalsLinked.get(key).isEmpty()) animalsLinked.remove(key);
        }
    }

    public void addAnimal(Vector2d key, Animal object) {
        if (animalsLinked.containsKey(key)) animalsLinked.get(key).add(object);
        else {
            LinkedList<Animal> list = new LinkedList<>();
            list.add(object);
            animalsLinked.put(key, list);
        }
    }

    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition, Animal animal) {
        removeAnimal(oldPosition, animal);
        addAnimal(newPosition, animal);
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return true;
    }

    @Override
    public boolean place(Animal animal) {
        return false;
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return objectAt(position) != null;

    }

    @Override
    public Object objectAt(Vector2d position) {
        if (animalsLinked.get(position) != null) {
            LinkedList<Animal> tmpList = animalsLinked.get(position);
            Animal tmpAnimal = new Animal(this, new Vector2d(-1, -1), -100);
            for (Animal animal : tmpList) {
                if (animal.getEnergy() > tmpAnimal.getEnergy()) tmpAnimal = animal;
            }
            return tmpAnimal;
        }
        return grasses.get(position);
    }


    public void multiplication() {
        Animal mother;
        Animal father;
        for (LinkedList<Animal> animalsAtPos : animalsLinked.values()) {
            if (animalsAtPos.size() > 1) {
                if (animalsAtPos.get(0).getEnergy() > animalsAtPos.get(1).getEnergy()) {
                    mother = animalsAtPos.get(1);
                    father = animalsAtPos.get(0);
                } else {
                    mother = animalsAtPos.get(0);
                    father = animalsAtPos.get(1);
                }
                for (int j = 0; j < animalsAtPos.size(); j++) {
                    Animal animal = animalsAtPos.get(j);
                    if (animal.getEnergy() > father.getEnergy()) {
                        mother = father;
                        father = animal;
                    } else if (animal.getEnergy() > mother.getEnergy()) mother = animal;
                }
                Animal child = father.copulation(mother);
                animals.add(child);
                addAnimal(child.getPosition(), child);
                child.addObserver(this);
            }
        }
//        System.out.println(animals);
    }

    public void eatGrass(Animal animal, Vector2d position, int part) {
        Grass grass = grasses.get(position);
//        animal.changeEnergy( grass.getEnergy()/part);
    }

    public void whoCanEat() {
        LinkedList<Vector2d> grassesToDel = new LinkedList();
        for (Vector2d position : grasses.keySet()) {
            if (animalsLinked.containsKey(position)) {

                LinkedList<Animal> list = animalsLinked.get(position);
                grassesToDel.add(position);

                if (list.size() == 1) {
                    eatGrass(list.get(0), position, 1);
                } else {
                    float maxenergy = 0;
                    int part = 1;
                    for (Animal animal : list) {
                        if (animal.getEnergy() > maxenergy) {
                            maxenergy = animal.getEnergy();
                            part = 1;
                        } else if (animal.getEnergy() == maxenergy) part += 1;
                    }
                    for (Animal animal : list) {
                        if (animal.getEnergy() == maxenergy) eatGrass(animal, position, part);
                    }
                }
            }
        }
        for (Vector2d toDel : grassesToDel) {
            grasses.remove(toDel);
        }
    }

    protected boolean placeAnimal(Animal animal) {
        addAnimal(animal.getPosition(), animal);
        animals.add(animal);
        animal.addObserver(this);
        return true;
    }

    protected void placeAnimalInJungle() {
        Vector2d tmp = cordsGenerator();
        for (int i = 0; i < animalsAtStart; i++) {
            if (objectsCounter(jungleLL, jungleUR) < jungleWidth * jungleHeight) {
                while (!(!animalsLinked.containsKey(tmp) && inJungle(tmp))) {
                    tmp = cordsGenerator();
                }
                Animal newAnimal = new Animal(this, tmp, startEnergy);
                addAnimal(tmp, newAnimal);
                animals.add(newAnimal);
                newAnimal.addObserver(this);
            }

        }
    }

    public void removeDeadAnimals() {
        for (int i = 0; i < animals.size(); i++) {
            Animal anim = animals.get(i);
            if (anim.isDead()) {
                removeAnimal(anim.getPosition(), anim);
                animals.remove(anim);
                anim.removeObserver(this);
                i -= 1;
            }
        }
    }

    public void moveAnimals() {
        for (Animal animal : animals) {
            animal.move(MoveDirection.FORWARD);
        }
    }

    public void dayEnergyDeload() { // deload?
        for (Animal animal : animals) {
            animal.changeEnergy(dailyEnergyCost);
        }
    }


    //nakładanie trawy na mapę
    protected void plantGrass() {
//        najpierw w jungli
        Vector2d tmp;
        int jungleObjects = objectsCounter(jungleLL, jungleUR);
        while (jungleObjects < jungleHeight * jungleWidth) {
            tmp = cordsGenerator();
            if (!grasses.containsKey(tmp) && !animalsLinked.containsKey(tmp) && inJungle(tmp)) {
                grasses.put(tmp, new Grass(tmp, plantEnergy));
                break;
            }
        }
//        na sawannie
        int sawannaObjects = objectsCounter(sawannaLL, sawannaUR) - jungleObjects;
        while (sawannaObjects < mapHeight * mapWidth - jungleWidth * jungleHeight) {
            tmp = cordsGenerator();
            if (!grasses.containsKey(tmp) && !animalsLinked.containsKey(tmp) && atSawanna(tmp)) {
                grasses.put(tmp, new Grass(tmp, plantEnergy));
                break;
            }
        }
    }

    //HELPERS
    public int objectsCounter(Vector2d lowerLeft, Vector2d upperRight) {
        int counter = 0;
        Vector2d tmp;
        for (int i = lowerLeft.x; i <= upperRight.x; i += 1) {
            for (int j = lowerLeft.y; j <= upperRight.y; j++) {
                tmp = new Vector2d(i, j);
                if (animalsLinked.containsKey(tmp) || grasses.containsKey(tmp)) {
                    counter += 1;
                }
            }
        }
        return counter;
    }

    public Vector2d cordsGenerator() {
        Random generator = new Random();    // nowy obiekt co wywołanie
        int x = generator.nextInt(mapWidth);
        int y = generator.nextInt(mapHeight);
        return new Vector2d(x, y);
    }

    @Override
    public String toString() {
        return new MapVisualizer(this).draw(sawannaLL, sawannaUR);
    }

    public boolean inJungle(Vector2d position) { // public?
        return position.follows(jungleLL) && position.precedes(jungleUR);
    }

    public boolean atSawanna(Vector2d position) {
        return !inJungle(position);
    }


}
