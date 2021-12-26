package agh.ics.oop;
import javafx.scene.image.Image;

import java.io.FileNotFoundException;

public interface IMapElement {
    String toString();
    Vector2d getPosition();
    Image getImage() throws FileNotFoundException;
}
