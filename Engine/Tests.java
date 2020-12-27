package Engine;

import org.junit.Test;

import static org.junit.Assert.*;

public class Tests {

    @Test
    public void vectorOperationsTest() {
        // testing equals
        Vector vector1 = new Vector(2, 4);
        Vector vector2 = new Vector(2, 4);
        Vector vector3 = new Vector(1, 1);
        Vector vector4 = new Vector(2, 9);
        Vector vector5 = new Vector(3, 4);
        assertTrue(vector1.equals(vector1));
        assertFalse(vector1 == vector2);
        assertTrue(vector1.equals(vector2));
        assertFalse(vector1.equals(vector3));
        assertFalse(vector1.equals(vector4));
        assertFalse(vector1.equals(vector5));
        System.out.println(vector1);
        System.out.println("Above should read Vector (2.0, 4.0)");
    }
}
