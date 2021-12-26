package agh.ics.oop.gui;

import agh.ics.oop.Animal;
import agh.ics.oop.IMapElement;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.awt.*;
import java.io.FileNotFoundException;

public class GuiElementBox{
    private Image image;
    private String label;


    public GuiElementBox(IMapElement object) {
        try {
            this.image = object.getImage();
            if (object instanceof Animal) {
                this.label = "Z" + object.getPosition();
            } else this.label = "Grass";
        } catch (FileNotFoundException exception){
            System.out.println("Picture does not exists");
            this.image = null;
            this.label = "";
        }
    }

    public VBox mapElementView() {
        Label elementLabel = new Label(label);
        ImageView elementView = new ImageView(image);
        VBox elementVBox = new VBox();

        elementView.setFitWidth(20);
        elementView.setFitHeight(20);

        elementVBox.getChildren().addAll(elementView, elementLabel);
        elementVBox.setAlignment(Pos.CENTER);

        return elementVBox;
    }
}
