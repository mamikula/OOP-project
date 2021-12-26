package agh.ics.oop;

public class Constants {
    public static int mapHeight = 5;
    public static int mapWidth = 5;
    public static int startEnergy = 100;
    public static int moveEnergy = 1;
    public static int plantEnergy = 10;
    public static double jungleRatio = 1;
    public static boolean grassMagic = false;
    public static boolean rectangularMagic = false;
    public static int animalsAtStart = 10;
//    public static boolean start = false;

    public static void setAnimalsAtStart(int animalsAtStart) {
        Constants.animalsAtStart = animalsAtStart;
    }

//    public static void setStart(boolean start) {
//        Constants.start = start;
//    }

    public static void setMapHeight(int mapHeight) {
        Constants.mapHeight = mapHeight;
    }

    public static void setMapWidth(int mapWidth) {
        Constants.mapWidth = mapWidth;
    }

    public static void setStartEnergy(int startEnergy) {
        Constants.startEnergy = startEnergy;
    }

    public static void setMoveEnergy(int moveEnergy) {
        Constants.moveEnergy = moveEnergy;
    }

    public static void setPlantEnergy(int plantEnergy) {
        Constants.plantEnergy = plantEnergy;
    }

    public static void setJungleRatio(double jungleRatio) {
        Constants.jungleRatio = jungleRatio;
    }

    public static void setGrassMagic(boolean grassMagic) {
        Constants.grassMagic = grassMagic;
    }

    public static void setRectangularMagic(boolean rectangularMagic) {
        Constants.rectangularMagic = rectangularMagic;
    }
}
