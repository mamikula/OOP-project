package agh.ics.oop;

import java.util.LinkedList;
import java.util.List;


public class SimulationEngine implements IEngine, Runnable {

    private final AbstractWorldMap map;
    private List<ISimulationObserver> observers = new LinkedList<>();

    private int moveDelay = 1000;
//    sawanna

    private double jungleRatio;

    public SimulationEngine(AbstractWorldMap map) {
        this.map = map;
    }

    public void addObserver(ISimulationObserver observer) {
        this.observers.add(observer);
    }

    public void changesUpdate(){
        for(ISimulationObserver observer : observers){
            observer.changesUpdate(map);
        }
    }


    @Override
    public void run() {
        map.placeAnimalInJungle();
        map.plantGrass();

//        System.out.println(map);
        try {
            for (int i = 0; i < 300; i++) {
//                System.out.println(i);
                map.removeDeadAnimals();
                map.moveAnimals();
                map.whoCanEat();
                map.multiplication();
                map.dayEnergyDeload();
                map.plantGrass();
                map.magicEvolution();
                map.magicEvolution();
//                System.out.println(map);
                changesUpdate();
                Thread.sleep(moveDelay);
            }
        } catch (InterruptedException e) {
            System.out.println("Interrupdet: " + e.getMessage());
        }
    }
}


