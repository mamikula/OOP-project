package agh.ics.oop;

import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;


public class Animal implements IMapElement {
    private MapDirection orientation;
    private Vector2d position;
    private IWorldMap map;
    private List<IPositionChangeObserver> subscribers;

    private int startEnergy;
    private int energy;
    public Genes genes;

    //   --------------------New items-----------------------
    public Animal(IWorldMap map, Vector2d initialPosition, int energy) {
        this.position = initialPosition;
        this.map = map;
        this.orientation = MapDirection.EAST.randomDirection();
        this.subscribers = new ArrayList<>();
        this.energy = energy;
        this.startEnergy = energy;
        genes = new Genes(8, 32);
    }

    //    ENERGY SECTION
    public boolean isDead() {
        return this.energy <= 0;
    }

    public void changeEnergy(int value) {
        this.energy = this.energy + value;
        if (this.energy < 0) this.energy = 0;
    }

    //COPULATION
    public Animal copulation(Animal mother) {

        int childEnergy = (int) (0.25 * mother.energy) + (int) (this.energy * 0.25);
        mother.changeEnergy((int) -(0.25 * mother.energy));
        this.changeEnergy((int) -(this.energy * 0.25));

        Animal child = new Animal(map, mother.getPosition(), childEnergy);
        child.genes = new Genes(this, mother);

        return child;
    }
    public float getEnergy() {
        return energy;
    }


    //    -----------------------------------------------------
    @Override
    public String toString() {
        return orientation.toString();
    }

    public void move(MoveDirection direction) {
        switch (direction) {
            case RIGHT -> orientation = orientation.next();
            case LEFT -> orientation = orientation.previous();
            case FORWARD -> {
                if (map.canMoveTo(position.add(orientation.toUnitVector()))) {
                    Vector2d oldPos = position;
                    position = position.add(orientation.toUnitVector());
                    positionChanged(oldPos, oldPos.add(orientation.toUnitVector()), this);
                }

            }
            case BACKWARD -> {
                if (map.canMoveTo(position.subtract(orientation.toUnitVector()))) {
                    Vector2d oldPos = position;
                    position = position.subtract(orientation.toUnitVector());
                    positionChanged(oldPos, oldPos.subtract(orientation.toUnitVector()), this);
                }
            }
        }
    }

    public MapDirection getOrientation() {
        return orientation;
    }

    @Override
    public Vector2d getPosition() {
        return position;
    }


    public void addObserver(IPositionChangeObserver observer) {
        this.subscribers.add(observer);
    }

    public void removeObserver(IPositionChangeObserver observer) {
        this.subscribers.remove(observer);
    }

    public void positionChanged(Vector2d oldPosition, Vector2d newPosition, Animal animal) {
        for (IPositionChangeObserver subscriber : subscribers) {
            subscriber.positionChanged(oldPosition, newPosition, animal);
        }
    }

    public void setPosition(Vector2d position) {
        this.position = position;
    }

    public Genes getGenes() {
        return genes;
    }


    @Override
    public Image getImage() throws FileNotFoundException {
        if (this.energy < this.startEnergy * 0.7 && this.energy >= this.startEnergy*.5) {
            return new Image(new FileInputStream("src/main/resources/" +
                    switch (this.orientation) {
                        case NORTH -> "up-orange.png";
                        case EAST -> "right-orange.png";
                        case SOUTH -> "down-orange.png";
                        case WEST -> "left-orange.png";
                        case NORTHWEST -> "northwest-orange.png";
                        case SOUTHWEST -> "southwest-orange.png";
                        case NORTHEAST -> "northeast-orange.png";
                        case SOUTHEAST -> "southeast-orange.png";
                    }));
        } else if (this.energy < this.startEnergy * 0.5) {
            return new Image(new FileInputStream("src/main/resources/" +
                    switch (this.orientation) {
                        case NORTH -> "up-red.png";
                        case EAST -> "right-red.png";
                        case SOUTH -> "down-red.png";
                        case WEST -> "left-red.png";
                        case NORTHWEST -> "northwest-red.png";
                        case SOUTHWEST -> "southwest-red.png";
                        case NORTHEAST -> "northeast-red.png";
                        case SOUTHEAST -> "southeast-red.png";
                    }));
        } else {
            return new Image(new FileInputStream("src/main/resources/" +
                    switch (this.orientation) {
                        case NORTH -> "up-yellow.png";
                        case EAST -> "right-yellow.png";
                        case SOUTH -> "down-yellow.png";
                        case WEST -> "left-yellow.png";
                        case NORTHWEST -> "northwest-yellow.png";
                        case SOUTHWEST -> "southwest-yellow.png";
                        case NORTHEAST -> "northeast-yellow.png";
                        case SOUTHEAST -> "southeast-yellow.png";
                    }));
        }

    }
}
