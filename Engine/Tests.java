package Engine;

import org.junit.Assert;
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
        assertEquals(cohesionAdjustment, singleBoid.cohesionRule(someBoids));
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
        assertEquals(alignmentAdjustment, singleBoid.alignmentRule(someBoids));
    }

    @Test
    public void simpleSeparationRuleTest() {
        ArrayList<Boid> someBoids = new ArrayList<>();
        someBoids.add(new Boid(0, 0, 0, 0));
        someBoids.add(new Boid(1, 0, 0, 0));
        someBoids.add(new Boid(1, 1, 0, 0));
        someBoids.add(new Boid(0, 1, 0, 0));
        Boid farBoid = new Boid(1000, 1000, 1000, 1000);
        Vector noSeparationAdjustment = new Vector(0, 0);
        assertEquals(noSeparationAdjustment, farBoid.separationRule(someBoids));
        // boid below shouldn't have sepeartionAdjustment bc there are boids that cancel each
        // other's separationAdjustment
        Boid closeCenterBoid = new Boid(0.5, 0.5, 0 ,0);
        assertEquals(noSeparationAdjustment, closeCenterBoid.separationRule(someBoids));
        // should move right (+x value) bc of birds to left (at 0,0 and 0,1)
        Boid closeRightBoid = new Boid(1, 0.5, 0, 0);
        Vector separationAdjustment = new Vector(2, 0);
        assertEquals(separationAdjustment, closeRightBoid.separationRule(someBoids));
        ArrayList<Boid> someBoids2 = new ArrayList<>();
        someBoids2.add(new Boid(0, 1, 0, 0));
        someBoids2.add(new Boid(0, 0, 0, 0));
        someBoids2.add(new Boid(0, -1, 0, 0));
        someBoids2.add(new Boid(0, 2, 0, 0));
        someBoids2.add(new Boid(0, -2, 0, 0));
        Boid closeLeftBoid = new Boid(-1, 0, 0, 0);
        Vector separationAdjustment2 = new Vector(-5, 0);
        assertEquals(separationAdjustment2, closeLeftBoid.separationRule(someBoids2));
    }

    @Test
    public void boidsAddedToSameList() {
        Boid boid1 = new Boid(0, 0, 0, 0);
        Boid boid2 = new Boid(1, 0, 0, 0);
        Boid boid3 = new Boid(0, 1, 0, 0);
        Boid boid4 = new Boid(1, 1, 0, 0);
        assertSame(boid1.getBOIDS(), boid2.getBOIDS());
        assertSame(boid1.getBOIDS(), boid3.getBOIDS());
        assertSame(boid1.getBOIDS(), boid4.getBOIDS());
        assertTrue(boid1.getBOIDS().contains(boid2));
        assertTrue(boid2.getBOIDS().contains(boid1));
        assertTrue(boid1.getBOIDS().contains(boid3));
        assertTrue(boid1.getBOIDS().contains(boid4));
        assertTrue(boid4.getBOIDS().contains(boid3));
        System.out.println(boid1.getBOIDS().toString());
    }
}
