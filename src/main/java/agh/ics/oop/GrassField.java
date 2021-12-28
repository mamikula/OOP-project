package agh.ics.oop;

import java.util.*;
import java.lang.Math;


public class GrassField extends AbstractWorldMap {


    public GrassField(int mapHeight, int mapWidth, double jungleRatio, int startEnergy, int animalsAtStart, int plantEnergy, int moveEnergy, boolean magic){
//map
        this.startEnergy = startEnergy;
        this.mapHeight = mapHeight;
        this.mapWidth = mapWidth;
        this.animalsAtStart = animalsAtStart;
        this.plantEnergy = plantEnergy;
        this.grasses = new LinkedHashMap<>();
        this.animalsLinked  = new HashMap<Vector2d, LinkedList<Animal>>();
        this.moveEnergy = (-1)*moveEnergy;
        this.magicField = magic;
        this.file = new Files("GrassField_data.csv");

//sawanna
        this.sawannaLL = new Vector2d(0, 0);
        this.sawannaUR = new Vector2d(mapWidth, mapHeight);
//jungle
        double factor = Math.sqrt(jungleRatio);
        this.jungleHeight = (int) (mapHeight * factor);
        this.jungleWidth = (int) (mapWidth * factor);
        this.jungleLL = new Vector2d(mapWidth/2 - jungleWidth / 2, mapHeight / 2 - jungleHeight / 2);
        this.jungleUR = new Vector2d(mapWidth/2 + jungleWidth / 2, mapHeight / 2 + jungleHeight / 2);
    }

    @Override
    public void moveAnimals() {
        for (Animal animal : animals) {
            int numOfRotation = animal.genes.returnRandomGen();
            if (numOfRotation == 0) {
                animal.move(MoveDirection.FORWARD);
                bounders(animal);
            } else if (numOfRotation == 4) {
                animal.move(MoveDirection.BACKWARD);
                bounders(animal);
            } else {
                for (int i = 0; i < numOfRotation; i++) {
                    animal.move(MoveDirection.RIGHT);
                }
            }
        }
    }

    public void bounders(Animal animal){
        Vector2d oldPos;
        Vector2d newPos;
        if(animal.getPosition().x > mapWidth) {
            oldPos = animal.getPosition();
            newPos = new Vector2d(0, animal.getPosition().y);
            animal.setPosition(newPos);
            positionChanged(oldPos, newPos, animal);
        }
        if(animal.getPosition().y > mapHeight){
            oldPos = animal.getPosition();
            newPos = new Vector2d(animal.getPosition().x, 0);
            animal.setPosition(newPos);
            positionChanged(oldPos, newPos, animal);
        }
        if(animal.getPosition().x < 0) {
            oldPos = animal.getPosition();
            newPos = new Vector2d(mapWidth, animal.getPosition().y);
            animal.setPosition(newPos);
            positionChanged(oldPos, newPos, animal);
        }
        if(animal.getPosition().y < 0) {
            oldPos = animal.getPosition();
            newPos = new Vector2d(animal.getPosition().x, mapHeight);
            animal.setPosition(newPos);
            positionChanged(oldPos, newPos, animal);
        }
    }

    public int getAnimalsSize(){
        return animals.size();
    }

    public int getGrassesSize(){
        return grasses.size();
    }



}
