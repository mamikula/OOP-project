package agh.ics.oop;

import java.util.LinkedList;
import java.util.List;


public class SimulationEngine implements IEngine, Runnable {

    private final AbstractWorldMap map;
    private final List<ISimulationObserver> observers = new LinkedList<>();


    private final int moveDelay = 1000;
//    sawanna

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
        int days = 300;
        map.placeAnimalInJungle();
        map.plantGrass();

        try {
            for (int i = 0; i < days; i++) {
                map.removeDeadAnimals(i + i);
                map.moveAnimals();
                map.whoCanEat();
                map.multiplication(i + i);
                map.dayEnergyDeload();
                map.plantGrass();
                map.magicEvolution();
                map.getFile().writeToFile(i, map.getAnimalsSize(), map.getGrassesSize(), map.avgEnergy(), map.getLifeTime()/(i + 1));
                map.countData(i);
                changesUpdate();
                Thread.sleep(moveDelay);
            }
            int animCnt = map.countData(days)[0];
            int grasCnt = map.countData(days)[1];
            int avgLife = map.countData(days)[2];
            map.getFile().writeToFile(0, animCnt/days, grasCnt/days, map.avgEnergy(), avgLife);
        } catch (InterruptedException e) {
            System.out.println("Interrupdet: " + e.getMessage());
        }
    }
}


