package Engine;

import org.junit.Test;

import java.util.ArrayList;

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
        assertEquals(vector1, vector1);
        assertNotSame(vector1, vector2);
        assertEquals(vector1, vector2);
        assertNotEquals(vector1, vector3);
        assertNotEquals(vector1,vector4);
        assertNotEquals(vector1, vector5);
        assertEquals(new Vector(), new Vector(0, 0));
        System.out.println(vector1);
        System.out.println("Above should read Vector (2.0, 4.0)");
    }

    @Test
    public void simpleCohesionRuleTest() {
        ArrayList<Boid> someBoids = new ArrayList<>();
        someBoids.add(new Boid(0, 0, 0, 0));
        someBoids.add(new Boid(1, 0, 0, 0));
        someBoids.add(new Boid(1, 1, 0, 0));
        someBoids.add(new Boid(0, 1, 0, 0));
        Boid singleBoid = new Boid(1000, 1000, 1000, 1000);
        Vector cohesionAdjustment = new Vector(0.5, 0.5);
        // current COHESION_RATE is 1 / 100
        cohesionAdjustment = cohesionAdjustment.multiply(1.0 / 100);
        assertEquals(singleBoid.cohesionRule(someBoids), cohesionAdjustment);
    }

    @Test
    public void simpleAlignmentRuleTest() {
        ArrayList<Boid> someBoids = new ArrayList<>();
        someBoids.add(new Boid(0, 0, 0, 0));
        someBoids.add(new Boid(0, 0, 1, 0));
        someBoids.add(new Boid(0, 0, 1, 1));
        someBoids.add(new Boid(0, 0, 0, 1));
        Boid singleBoid = new Boid(1000, 1000, 1000, 1000);
        Vector alignmentAdjustment = new Vector(0.5, 0.5);
        // current ALIGNMENT_RATE is 1 / 8
        alignmentAdjustment = alignmentAdjustment.multiply(1.0 / 8);
        assertEquals(singleBoid.alignmentRule(someBoids), alignmentAdjustment);
    }
}
