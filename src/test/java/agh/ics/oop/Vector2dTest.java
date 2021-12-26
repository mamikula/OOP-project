package agh.ics.oop;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class Vector2dTest {
    Vector2d x;
    Vector2d y;

    Vector2d a;
    Vector2d b;

    Vector2d v1;
    Vector2d v2;
    Vector2d v3;
    Vector2d v4;
    Vector2d v5;


    @Test
    void testToString() {
        assertEquals(x.toString(), "(2,1)" );
        assertEquals(y.toString(), "(1,2)" );
    }

    @Test
    void testprecedes() {

        assertFalse(b.precedes(v1));
        assertFalse(b.precedes(v2));
        assertFalse(b.precedes(v3));
        assertTrue(b.precedes(v4));
        assertFalse(b.precedes(v5));

//        assertFalse(new Vector2d(1, 5),precedes(new Vector2d(1, 5)));
//        zwracającą wartość true, jeśli oba pola mają wartość mniejszą bądź równą polom drugiego obiektu,
        //zamienic and na or i jezeli testy przejda to jest zle, za slabo testujemy
    }

    @Test
    void testfollows(){
        assertTrue(b.follows(v1));
        assertTrue(b.follows(v2));
        assertFalse(b.follows(v3));
        assertFalse(b.follows(v4));
        assertTrue(b.follows(v5));
    }

    @Test
    void upperRight() {
        assertEquals(x.upperRight(y), new Vector2d(2, 2));
    }

    @Test
    void lowerLeft() {
        assertEquals(x.lowerLeft(y), new Vector2d(1, 1));
    }

    @Test
    void add() {
        assertEquals(x.add(y), new Vector2d(3, 3));
    }

    @Test
    void subtract() {
        assertEquals(x.subtract(y), new Vector2d(1, -1));
    }

    @Test
    void opposite() {
        assertEquals(x.opposite(), new Vector2d(-2, -1));
        assertEquals(y.opposite(), new Vector2d(-1, -2));
    }

    @Test
    void testHashCode() {
        if(a.equals(b)){
            assertEquals(a.hashCode(), b.hashCode());
        }
    }

    @Test
    void testEquals() {
        assertFalse(x.equals(y));
        assertNotEquals(x, null);
        assertNotEquals(x, "asd");
        assertNotEquals(true, null);
        assertTrue(a.equals(b));
    }

    @BeforeEach
    void setup() {
        x = new Vector2d(2, 1);
        y = new Vector2d(1, 2);

        a = new Vector2d(2, 2);
        b = new Vector2d(2, 2);

        v1 = new Vector2d(0, 0);
        v2 = new Vector2d(-2, 2);
        v3 = new Vector2d(1, 3);
        v4 = new Vector2d(3, 2);
        v5 = new Vector2d(2, 1);
    }
}