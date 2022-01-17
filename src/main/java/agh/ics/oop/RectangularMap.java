package agh.ics.oop;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

public class RectangularMap extends AbstractWorldMap {


    public RectangularMap(int mapHeight, int mapWidth, double jungleRatio, int startEnergy, int animalsAtStart, int plantEnergy, int moveEnergy, boolean magic){
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
        this.file = new Files("Rectangular_map_data.csv");

//sawanna
        this.sawannaLL = new Vector2d(0, 0);
        this.sawannaUR = new Vector2d(mapWidth, mapHeight); // jaką szerokość ma mapa o szerokości 10?
//jungle
        double factor = Math.sqrt(jungleRatio);
        this.jungleHeight = (int) (mapHeight * factor);
        this.jungleWidth = (int) (mapWidth * factor);
        this.jungleLL = new Vector2d(mapWidth/2 - jungleWidth / 2, mapHeight / 2 - jungleHeight / 2);
        this.jungleUR = new Vector2d(mapWidth/2 + jungleWidth / 2, mapHeight / 2 + jungleHeight / 2);
    }


    @Override
    public boolean canMoveTo(Vector2d position) {
        return position.follows(sawannaLL) && position.precedes(sawannaUR);
    }

    @Override
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

    public int getAnimalsSize(){
        return animals.size();  // to by nie mogło być w abstrakcyjnej?
    }

    public int getGrassesSize(){
        return grasses.size();
    }




}