package agh.ics.oop;

import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Grass implements IMapElement{
    private Vector2d position;
    private float energy;   // czy to nie powinna być stała?

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
    public Image getImage() throws FileNotFoundException {  // dobrze by to było przenieść do GUI
        return new Image(new FileInputStream("src/main/resources/grass.png"));  // czy jest sens wczytywać ten obrazek od nowa przy każdym wywołaniu?
    }

    @Override
    public String toString() {
        return "*";
    }
}
