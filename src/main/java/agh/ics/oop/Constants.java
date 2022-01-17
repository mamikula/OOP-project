package agh.ics.oop;

public class Constants {
    public static int mapHeight;    // każdy może to zmodyfikować - czy to bezpieczne?
    public static int mapWidth;
    public static int startEnergy;
    public static int moveEnergy;
    public static int plantEnergy;
    public static double jungleRatio;
    public static boolean grassMagic;
    public static boolean rectangularMagic;
    public static int animalsAtStart;

    public static void setAnimalsAtStart(int animalsAtStart) {
        Constants.animalsAtStart = animalsAtStart;
    }


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
