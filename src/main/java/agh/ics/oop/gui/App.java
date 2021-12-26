package agh.ics.oop.gui;

import agh.ics.oop.*;
import agh.ics.oop.GrassField;
import agh.ics.oop.SimulationEngine;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import org.w3c.dom.css.Rect;

import java.util.Objects;


public class App extends Application implements ISimulationObserver{
    protected GridPane gridpane;
    protected GridPane gridpane2;
    private GridPane initGrid;
    protected GrassField map;
    private RectangularMap map2;
    protected SimulationEngine engine;
    protected SimulationEngine engine2;
    protected BorderPane borderPane;
    protected LineChartClass grassFieldChart;
    protected LineChartClass rectangularChart;
    public boolean flag1 = true;
    public boolean flag2 = true;

    private GridPane chartGrid;
    int day1 = 0;
    int day2 = 0;

    Thread engineThread;
    Thread engineThread2;




    @Override
    public void init() throws Exception {
        super.init();
        this.borderPane = new BorderPane();

        //https://docs.oracle.com/javafx/2/get_started/form.htm

        //InputFrom section
        initGrid = new GridPane();
        borderPane.setCenter(initGrid);

        Label mapHeightText = new Label("Map Width:");
        initGrid.add(mapHeightText, 0, 2);
        TextField mapHeight = new TextField();
        initGrid.add(mapHeight, 1, 2);

        Label mapWidthText = new Label("Map Width:");
        initGrid.add(mapWidthText, 0, 1);
        TextField mapWidth = new TextField();
        initGrid.add(mapWidth, 1, 1);

        Label startEnergyText = new Label("Start Energy:");
        initGrid.add(startEnergyText, 0, 3);
        TextField startEnergy = new TextField();
        initGrid.add(startEnergy, 1, 3);

        Label moveText = new Label("Move Energy:");
        initGrid.add(moveText, 0, 4);
        TextField moveEnergy = new TextField();
        initGrid.add( moveEnergy, 1, 4);

        Label plantText = new Label("Plant energy:");
        initGrid.add(plantText, 0, 5);
        TextField plantEnergy = new TextField();
        initGrid.add(plantEnergy, 1, 5);

        Label ratioText = new Label("Jungle ratio:");
        initGrid.add(ratioText, 0, 6);
        TextField jungleRatio = new TextField();
        initGrid.add(jungleRatio, 1, 6);

        Label mapGrassMagic = new Label("Grass Magic Evolution:");
        initGrid.add(mapGrassMagic, 0, 7);
        TextField grassMagic = new TextField();
        initGrid.add(grassMagic, 1, 7);

        Label mapRectangularMagic = new Label("Rectangular Magic Evolution:");
        initGrid.add(mapRectangularMagic, 0, 8);
        TextField rectangularMagic = new TextField();
        initGrid.add(rectangularMagic, 1, 8);

        Label startAnimals = new Label("Animals at start:");
        initGrid.add(startAnimals, 0, 9);
        TextField animalsAtStart = new TextField();
        initGrid.add(animalsAtStart, 1, 9);

        Button btn = new Button("Send");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        initGrid.add(hbBtn, 1, 10);

        initGrid.setPrefSize(200,300);
        //InputFrom section

        btn.setOnAction(e -> {
            Constants.setMapHeight(Integer.parseInt(mapHeight.getText()));
            Constants.setMapWidth(Integer.parseInt(mapWidth.getText()));
            Constants.setStartEnergy(Integer.parseInt(startEnergy.getText()));
            Constants.setMoveEnergy(Integer.parseInt(moveEnergy.getText()));
            Constants.setPlantEnergy(Integer.parseInt(plantEnergy.getText()));
            Constants.setJungleRatio(Double.parseDouble(jungleRatio.getText()));
            Constants.setGrassMagic(Boolean.parseBoolean(grassMagic.getText()));
            Constants.setRectangularMagic(Boolean.parseBoolean(rectangularMagic.getText()));
            Constants.setAnimalsAtStart(Integer.parseInt(animalsAtStart.getText()));


            this.map = new GrassField(Constants.mapHeight, Constants.mapWidth, Constants.jungleRatio, Constants.startEnergy, Constants.animalsAtStart, Constants.plantEnergy, Constants.moveEnergy, Constants.grassMagic);
            this.engine = new SimulationEngine(map);
            engine.addObserver(this);
            this.gridpane = new GridPane();


            this.map2 = new RectangularMap(Constants.mapHeight, Constants.mapWidth, Constants.jungleRatio, Constants.startEnergy, Constants.animalsAtStart,Constants.plantEnergy  ,Constants.moveEnergy, Constants.rectangularMagic);
            this.engine2 = new SimulationEngine(map2);
            engine2.addObserver(this);
            this.gridpane2 = new GridPane();

            chartGrid = new GridPane();
            chartGrid.setPrefSize(600, 600);
            grassFieldChart = new LineChartClass();
            grassFieldChart.printChart(0, map.getAnimalsSize(), map.getGrassesSize(), map.avgEnergy(), map.getLifeTime());
            chartGrid.add(grassFieldChart.getLineChart(), 0, 0);


            rectangularChart = new LineChartClass();
            rectangularChart.printChart(0, map2.getAnimalsSize(),  map2.getGrassesSize(), map2.avgEnergy(), map.getLifeTime());
            chartGrid.add(rectangularChart.getLineChart(), 0, 1);
            borderPane.setCenter(chartGrid);


            engineThread = new Thread(engine);
            engineThread.start();

            engineThread2 = new Thread(engine2);
            engineThread2.start();

            borderPane.setLeft(gridpane);
            borderPane.setRight(gridpane2);
            borderPane.getChildren().remove(initGrid);




            Button btn1 = new Button("START/STOP GrassField");
            HBox hbBtn1 = new HBox(10);
            hbBtn1.setAlignment(Pos.BOTTOM_RIGHT);
            hbBtn1.getChildren().add(btn1);

            btn1.setOnAction(e1 -> {

                if(flag1) {engineThread.suspend(); flag1 = false;}
                else {engineThread.resume(); flag1 = true;}
            });

            GridPane gridpaneLeft = new GridPane();

            gridpaneLeft.add(gridpane, 0, 0);
            gridpaneLeft.add(hbBtn1, 0, 1);



            Button btn2 = new Button("START/STOP RectangularMap");
            HBox hbBtn2 = new HBox(10);
            hbBtn2.setAlignment(Pos.BOTTOM_RIGHT);
            hbBtn2.getChildren().add(btn2);


            btn2.setOnAction(e2 -> {
                if(flag2) {
                    engineThread2.suspend();
                    flag2 = false;
                }
                else {
                    engineThread2.resume();
                    flag2 = true;
                }
            });

            GridPane gridpaneRight = new GridPane();
            gridpaneRight.add(gridpane2, 0, 0);
            gridpaneRight.add(hbBtn2, 0, 10);


            borderPane.setLeft(gridpaneLeft);
            borderPane.setRight(gridpaneRight);

        });


    }
    public void start(Stage primaryStage) throws Exception {

        Scene scene = new Scene(borderPane, 1200, 600);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void draw(AbstractWorldMap map) {
        if(map instanceof GrassField) {
            this.gridpane.getChildren().clear();
            drawCrissCross(gridpane, map);
            drawContent(gridpane, map);
        }
        else {
            this.gridpane2.getChildren().clear();
            drawCrissCross(gridpane2, map2);
            drawContent(gridpane2, map2);
        }
    }

    public void drawCrissCross(GridPane gridpane, AbstractWorldMap map) {

        gridpane.getColumnConstraints().clear();
        gridpane.getRowConstraints().clear();
        gridpane.setGridLinesVisible(false);
        gridpane.setGridLinesVisible(true);

        Label sign = new Label("y/x");
        gridpane.add(sign, 0, 0);
        GridPane. setHalignment(sign, HPos. CENTER);

        int height = map.getMapHeight();
        int width = map.getMapWidth();

        for (int i = 0; i <= height; i++) {
            gridpane.getRowConstraints().add(new RowConstraints(30));

        }

        for (int i = 0; i <= width; i++) {
            gridpane.getColumnConstraints().add(new ColumnConstraints(30));
        }

        gridpane.getColumnConstraints().add(new ColumnConstraints(30));
        gridpane.getRowConstraints().add(new RowConstraints(30));

        for (int i = 1; i <= height + 1; i++) {
            Label rowNumber = new Label(Integer.toString(height + 1 - i));
            GridPane. setHalignment(rowNumber, HPos. CENTER);
            gridpane.add(rowNumber, 0, i);
        }

        for (int i = 1; i <= width + 1; i++) {
            Label columnNumber = new Label(Integer.toString(i - 1));
            GridPane. setHalignment(columnNumber, HPos. CENTER);
            gridpane.add(columnNumber, i, 0);
        }
    }

    public void drawContent(GridPane gridpane, AbstractWorldMap map) {

        int height = map.getMapHeight();
        int width = map.getMapWidth();
        for (int i = 0; i <= width; i++) {
            for (int j = 0; j <= height; j++) {

                Vector2d position = new Vector2d(i, j);
                Object object = map.objectAt(position);
                if(object != null){
                    gridpane.add((new GuiElementBox((IMapElement) object)).mapElementView(), i + 1, height + 1 - j);
                }
            }
        }
    }


    int animals;
    int grasses;
    @Override
    public void changesUpdate(AbstractWorldMap map) {
        Platform.runLater(()->{
            draw(map);
            if(map instanceof GrassField) {
                day1 += 1;
                animals = map.getAnimalsSize();
                grasses = map.getGrassesSize();
                grassFieldChart.printChart(day1, animals, grasses, map.avgEnergy(), map.getLifeTime()/(day1 + 1));

            }
            else {
                day2 += 1;
                animals = map2.getAnimalsSize();
                grasses = map2.getGrassesSize();
                rectangularChart.printChart(day2, animals, grasses, map2.avgEnergy(), map2.getLifeTime()/(day2 + 1));
            }
        });
    }
}

