package agh.ics.oop;


import java.util.Random;

public enum MapDirection {
    NORTH, SOUTH, WEST, EAST, NORTHWEST, SOUTHWEST, NORTHEAST, SOUTHEAST;   // kolejność ma znaczenie

    public static void main(String[] args) {    // nie robimy testów w taki sposób, od tego jest JUnit
        MapDirection polnoc = NORTH;
        System.out.println(polnoc.toString());
        System.out.println(polnoc.next());
        System.out.println(polnoc.previous());
        System.out.println(polnoc.toUnitVector());
    }

    public String toString(){
        return switch (this){
            case NORTH -> "N";
            case EAST -> "E";
            case SOUTH -> "S";
            case WEST -> "W";
            case NORTHWEST -> "NW";
            case SOUTHWEST -> "SW";
            case NORTHEAST ->"NE";
            case SOUTHEAST -> "SE";
        };
    }
    public MapDirection next(){
        return switch (this){
            case NORTH -> NORTHEAST;
            case SOUTH -> SOUTHWEST;
            case WEST -> NORTHWEST;
            case EAST -> SOUTHEAST;
            case NORTHWEST -> NORTH;
            case SOUTHWEST -> WEST;
            case NORTHEAST -> EAST;
            case SOUTHEAST -> SOUTH;
        };
    }

    public MapDirection previous(){
        return switch (this) {
            case NORTH -> NORTHWEST;
            case SOUTH -> SOUTHEAST;
            case WEST -> SOUTHWEST;
            case EAST -> NORTHEAST;
            case NORTHWEST -> WEST;
            case SOUTHWEST -> SOUTH;
            case NORTHEAST -> NORTH;
            case SOUTHEAST -> EAST;
        };
    }

    public Vector2d toUnitVector(){
        return switch (this) {
            case NORTH -> new Vector2d(0,1);    // nowy wektor co wywołanie
            case EAST -> new Vector2d(1,0);
            case SOUTH -> new Vector2d(0,-1);
            case WEST -> new Vector2d(-1,0);
            case NORTHWEST -> new Vector2d(-1, 1);
            case SOUTHWEST -> new Vector2d(-1, -1);
            case NORTHEAST -> new Vector2d(1, 1);
            case SOUTHEAST -> new Vector2d(1, -1);
        };
    }

    public static MapDirection randomDirection(){
        int random = new Random().nextInt(7);   // nowy obiekt co wywołanie
        return MapDirection.values()[random];
    }

}
