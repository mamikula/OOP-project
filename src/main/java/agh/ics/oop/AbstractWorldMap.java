package agh.ics.oop;

import java.util.*;
import java.lang.Math;

public abstract class AbstractWorldMap implements IWorldMap, IPositionChangeObserver {

    protected HashMap<Vector2d, LinkedList<Animal>> animalsLinked;
    protected LinkedList<Animal> animals = new LinkedList<>();
    protected Map<Vector2d, Grass> grasses;

    protected int mapHeight;
    protected int mapWidth;
    protected int plantEnergy;
    protected int startEnergy;
    protected int moveEnergy;
    protected double jungleRatio;
    protected int animalsAtStart;

    //    sawanna
    protected Vector2d sawannaUR;
    protected Vector2d sawannaLL;

    protected int jungleHeight;
    protected int jungleWidth;
    protected Vector2d jungleUR;
    protected Vector2d jungleLL;
    protected boolean magicField;
    protected int magicTimes = 3;


    public void removeAnimal(Vector2d key, Animal object){
        if(this.animalsLinked.get(key) != null){
            this.animalsLinked.get(key).remove(object);
            if(this.animalsLinked.get(key).isEmpty()) animalsLinked.remove(key);
        }
    }

    public void addAnimal(Vector2d key, Animal object){
        if(animalsLinked.containsKey(key)) animalsLinked.get(key).add(object);
        else{
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
        if (animalsLinked.get(position) != null){
            LinkedList<Animal> tmpList = animalsLinked.get(position);
            Animal tmpAnimal = new Animal(this, new Vector2d(-1, -1), -100);
            for(Animal animal : tmpList){
                if (animal.getEnergy() > tmpAnimal.getEnergy()) tmpAnimal = animal;
            }
            return tmpAnimal;
        }
        return grasses.get(position);
    }


    public void magicEvolution(){
        if(animals.size() == 5 && magicField && magicTimes > 0){
            Animal newAnim;
            Vector2d cords;
            for(Animal animal : animals){
                cords = cordsGenerator();
                while(animalsLinked.containsKey(cords) || grasses.containsKey(cords)){
                    cords = cordsGenerator();
                }
                newAnim = new Animal(this, cords, startEnergy);
                newAnim.genes = animal.getGenes();
            }
            magicTimes -= 1;
        }
    }

    public void multiplication(){
        Animal mother;
        Animal father;
        for(LinkedList<Animal> animalsAtPos : animalsLinked.values()) {
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
                if(father.getEnergy() >= startEnergy*(0.5) && mother.getEnergy() >= startEnergy*(0.5)) {
                    Animal child = father.copulation(mother);
                    animals.add(child);
                    addAnimal(child.getPosition(), child);
                    child.addObserver(this);
                }
            }
        }
        System.out.println(animals);
    }

    public void eatGrass(Animal animal, Vector2d position, int part){
        Grass grass = grasses.get(position);
        animal.changeEnergy( (int)grass.getEnergy()/part);
    }

    public void whoCanEat(){
        LinkedList<Vector2d> grassesToDel = new LinkedList();
        for(Vector2d position : grasses.keySet()){
            if(animalsLinked.containsKey(position)){

                LinkedList<Animal> list = animalsLinked.get(position);
                grassesToDel.add(position);

                if(list.size() == 1) {eatGrass(list.get(0), position, 1);}
                else{
                    int maxenergy = 0;
                    int part = 1;
                    for(Animal animal : list){
                        if(animal.getEnergy() > maxenergy){ maxenergy = (int) animal.getEnergy(); part = 1;}
                        else if(animal.getEnergy() == maxenergy) part += 1;
                    }
                    for(Animal animal : list){
                        if(animal.getEnergy() == maxenergy) eatGrass(animal, position, part);
                    }
                }
            }
        }
        for(Vector2d toDel : grassesToDel){
            grasses.remove(toDel);
        }
    }

    protected boolean placeAnimal(Animal animal){
        addAnimal(animal.getPosition(), animal);
        animals.add(animal);
        animal.addObserver(this);
        return true;
    }

    protected void placeAnimalInJungle(){
        Vector2d tmp = cordsGenerator();
        for(int i = 0; i < animalsAtStart; i++){
            if(jungleObs() < jungleWidth*jungleHeight) {
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

    public void removeDeadAnimals(){
        for(int i = 0; i < animals.size(); i++){
            Animal anim = animals.get(i);
            if(anim.isDead()){
                removeAnimal(anim.getPosition(), anim);
                animals.remove(anim);
                anim.removeObserver(this);
                i -= 1;
            }
        }
    }

    public void moveAnimals() {
        for (Animal animal : animals) {
            int numOfRotation = animal.genes.returnRandomGen();
            if (numOfRotation == 0) animal.move(MoveDirection.FORWARD);
            else if (numOfRotation == 4) animal.move(MoveDirection.BACKWARD);
            else {
                for (int i = 0; i < numOfRotation; i++) {
                    animal.move(MoveDirection.RIGHT);
                }
            }
        }
    }

    public void dayEnergyDeload() {
        for (Animal animal : animals) {
            animal.changeEnergy(moveEnergy);
        }
    }


    //nakładanie trawy na mapę
    protected void plantGrass(){
//        najpierw w jungli
        Vector2d tmp;
        int jungleObjects = jungleObs();
        while (jungleObjects < jungleHeight*jungleWidth){
            tmp = cordsGenerator();
            if (!grasses.containsKey(tmp) && !animalsLinked.containsKey(tmp) && inJungle(tmp)){
                grasses.put(tmp, new Grass(tmp, plantEnergy));
                break;
            }
        }
//        na sawannie
        int sawannaObjects = sawannaObs();
        while(sawannaObjects < (mapHeight + 1) * (mapWidth + 1) - jungleWidth*jungleHeight){
            tmp = cordsGenerator();
            if(atSawanna(tmp) && objectAt(tmp) == null){
                grasses.put(tmp, new Grass(tmp, plantEnergy));
                break;
            }
        }
    }

    //HELPERS
    public int jungleObs(){
        int counter = 0;
        for(Vector2d key : animalsLinked.keySet()){
            if(inJungle(key)) counter++;
        }
        for(Vector2d key : grasses.keySet()){
            if(inJungle(key)) counter++;
        }
        return counter;
    }

    public int sawannaObs(){
        int counter = 0;
        for(Vector2d key : animalsLinked.keySet()){
            if(!inJungle(key)) counter++;
        }
        for(Vector2d key : grasses.keySet()){
            if(!inJungle(key)) counter++;
        }

        return counter;
    }


    public Vector2d cordsGenerator(){
        Random generator = new Random();
        int x = generator.nextInt(mapWidth + 1);
        int y = generator.nextInt( mapHeight + 1);
        return new Vector2d(x, y);
    }
    @Override
    public String toString() {
        return new MapVisualizer(this).draw(sawannaLL, sawannaUR);
    }
    public boolean inJungle(Vector2d position){
        return position.follows(jungleLL) && position.precedes(jungleUR);
    }

    public boolean atSawanna(Vector2d position){
        return !inJungle(position);
    }

    public int getMapHeight() {
        return mapHeight;
    }

    public int getMapWidth() {
        return mapWidth;
    }

    public int getAnimalsSize(){
        return animals.size();
    }

    public int getGrassesSize(){
        return grasses.size();
    }
}
