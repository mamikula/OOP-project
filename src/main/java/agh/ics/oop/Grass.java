package agh.ics.oop;

import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Grass implements IMapElement{
    private Vector2d position;
    private float energy;

    public Grass(Vector2d position) {
        this.position = position;
    }

    public Grass(Vector2d position, float energy) {
        this.position = position;
        this.energy = energy;
    }

    public float getEnergy() { return energy; }

    @Override
    public Vector2d getPosition() {
        return position;
    }

    @Override
    public Image getImage() throws FileNotFoundException {
        return new Image(new FileInputStream("src/main/resources/grass.png"));
    }

    @Override
    public String toString() {
        return "*";
    }
}
