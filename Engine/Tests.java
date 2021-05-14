package Engine;

import edu.princeton.cs.introcs.StdDraw;
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
    // the commented tests are outdated but i don't wanna get rid of them quite yet
//
//    @Test
//    public void simpleCohesionRuleTest() {
//        Flock flock = new Flock();
//        new Boid(0, 0, 0, 0, flock);
//        new Boid(1, 0, 0, 0, flock);
//        new Boid(1, 1, 0, 0, flock);
//        new Boid(0, 1, 0, 0, flock);
//        Boid singleBoid = new Boid(1000, 1000, 1000, 1000, flock);
//        Vector cohesionAdjustment = new Vector(0.5, 0.5);
//        // current COHESION_RATE is 1 / 100
//        cohesionAdjustment = cohesionAdjustment.multiply(1.0 / 100);
//        assertEquals(cohesionAdjustment, singleBoid.cohesionRule(flock.getBoids()));
//    }

//    @Test
//    public void simpleAlignmentRuleTest() {
//        Flock flock = new Flock();
//        new Boid(0, 0, 0, 0, flock);
//        new Boid(0, 0, 1, 0, flock);
//        new Boid(0, 0, 1, 1, flock);
//        new Boid(0, 0, 0, 1, flock);
//        Boid singleBoid = new Boid(0.4, 0.4, 1000, 1000, flock);
//        Vector alignmentAdjustment = new Vector(0.5, 0.5);
//        // current ALIGNMENT_RATE is 1 / 8
//        alignmentAdjustment = alignmentAdjustment.multiply(1.0 / 8);
//        assertEquals(alignmentAdjustment, singleBoid.alignmentRule(singleBoid.getBoids()));
//    }
//
//    @Test
//    public void simpleSeparationRuleTest() {
//        Flock flock = new Flock();
//        new Boid(0, 0, 0, 0, flock);
//        new Boid(1, 0, 0, 0, flock);
//        new Boid(1, 1, 0, 0, flock);
//        new Boid(0, 1, 0, 0, flock);
//        Boid farBoid = new Boid(1000, 1000, 1000, 1000, flock);
//        Vector noSeparationAdjustment = new Vector(0, 0);
//        assertEquals(noSeparationAdjustment, farBoid.separationRule(farBoid.getBoids()));
//        // boid below shouldn't have sepeartionAdjustment bc there are boids that cancel each
//        // other's separationAdjustment
//        Boid closeCenterBoid = new Boid(0.5, 0.5, 0 ,0, flock);
//        assertEquals(noSeparationAdjustment, closeCenterBoid.separationRule(closeCenterBoid.getBoids()));
//        // should move right (+x value) bc of birds to left (at 0,0 and 0,1)
//        Boid closeRightBoid = new Boid(1, 0.5, 0, 0, flock);
//        Vector separationAdjustment = new Vector(2, 0);
//        assertEquals(separationAdjustment, closeRightBoid.separationRule(closeRightBoid.getBoids()));
//        Flock flock2 = new Flock();
//        new Boid(0, 1, 0, 0,flock2);
//        new Boid(0, 0, 0, 0, flock2);
//        new Boid(0, -1, 0, 0, flock2);
//        new Boid(0, 2, 0, 0, flock2);
//        new Boid(0, -2, 0, 0, flock2);
//        Boid closeLeftBoid = new Boid(-1, 0, 0, 0, flock2);
//        Vector separationAdjustment2 = new Vector(-5, 0);
//        assertEquals(separationAdjustment2, closeLeftBoid.separationRule(closeLeftBoid.getBoids()));
//    }

    @Test
    public void boidsAddedToSameList() {
        Flock flock = new Flock();
        Boid boid1 = new Boid(0, 0, 0, 0, flock);
        Boid boid2 = new Boid(1, 0, 0, 0, flock);
        Boid boid3 = new Boid(0, 1, 0, 0, flock);
        Boid boid4 = new Boid(1, 1, 0, 0, flock);
        assertSame(flock.getBoids(), flock.getBoids());
        assertTrue(flock.getBoids().contains(boid1));
        assertTrue(flock.getBoids().contains(boid2));
        assertTrue(flock.getBoids().contains(boid3));
        assertTrue(flock.getBoids().contains(boid4));
        System.out.println(boid1.getBoids().toString());
    }

    @Test
    public void nearbyBoidsTest() {
        Flock flock = new Flock();
        Boid boid1 = new Boid(0, 0, 0, 0, flock);
        Boid boid2 = new Boid(1, 0, 0, 0, flock);
        Boid boid3 = new Boid(0, 1, 0, 0, flock);
        Boid boid4 = new Boid(1, 1, 0, 0, flock);
        Boid boid5 = new Boid(1000, 1, 0, 0, flock);
        Boid boid6 = new Boid(0, 400, 0, 0, flock);
        Boid boid7 = new Boid(20, 450, 0, 0, flock);
        assertTrue(boid1.nearbyBoids().contains(boid2));
        assertTrue(boid2.nearbyBoids().contains(boid1));
        assertTrue(boid1.nearbyBoids().contains(boid3));
        assertTrue(boid1.nearbyBoids().contains(boid4));
        assertTrue(boid4.nearbyBoids().contains(boid3));
        assertTrue(boid5.nearbyBoids().isEmpty());
        assertTrue(boid7.nearbyBoids().contains(boid6));
        assertTrue(boid6.nearbyBoids().contains(boid7));
    }

    @Test
    public void cohesionTest() {
        Flock flock = new Flock();
        new Boid(0, 0, 0, 0, flock);
        new Boid(1, 0, 0, 0, flock);
        new Boid(1, 1, 0, 0, flock);
        new Boid(0, 1, 0, 0, flock);
        Boid singleBoid = new Boid(20, 0, 0, 0, flock);
        Vector cohesionAdjustment = new Vector(0.5 - 20, 0.5);
        // current COHESION_RATE is 1 / 100
        cohesionAdjustment = cohesionAdjustment.multiply(1.0 / 100);
        flock.updateBoids();
        assertEquals(cohesionAdjustment, singleBoid.getVelocity());
    }

    @Test
    public void alignmentTest() {
        Flock flock = new Flock();
        new Boid(0, 0, 0, 1, flock);
        new Boid(40, 0, 1, 1, flock);
        new Boid(40, 40, 0, 1, flock);
        new Boid(0, 40, -1, 1, flock);
        Vector singleVel = new Vector(10, 10);
        Boid singleBoid = new Boid(new Vector(20, 20), singleVel, flock);
        Vector avgVel = new Vector(0, 1);
        // current ALIGNMENT_RATE is 1 / 8
        Vector alignmentAdjustment = avgVel.subtract(singleVel).multiply(1.0 / 8);
        Vector newSingleVel = singleVel.add(alignmentAdjustment);
        flock.updateBoids();
        assertEquals(newSingleVel, singleBoid.getVelocity());
    }

    @Test
    public void separationTest() {
        Flock flock = new Flock();
        new Boid(1, 1, 0, 0, flock);
        Boid singleBoid = new Boid(1, 2, 0, 0, flock);
        Vector separationAdjustment = new Vector(1 - 1, 2 - 1);
        Vector cohesionAdjustment = new Vector(1 - 1, 1 - 2);
        cohesionAdjustment = cohesionAdjustment.multiply(1.0 / 100);
        Vector newSingleVel = separationAdjustment.add(cohesionAdjustment);
        flock.updateBoids();
        assertEquals(newSingleVel, singleBoid.getVelocity());
    }

    @Test
    public void stdDrawTest() {
        StdDraw.setPenRadius(0.05);
        StdDraw.setPenColor(StdDraw.BLUE);
        StdDraw.point(0.5, 0.5);
        StdDraw.setPenColor(StdDraw.MAGENTA);
        StdDraw.line(0.2, 0.2, 0.8, 0.2);
    }

    @Test
    public void arctanTest() {
        double degree = Math.atan(-1);
        System.out.println(degree * 180 / Math.PI);
    }

    @Test
    public void whereAreYou() {
        edu.princeton.cs.algs4.StdDraw.clear();
        edu.princeton.cs.algs4.StdDraw.setCanvasSize(700, 700);
        edu.princeton.cs.algs4.StdDraw.setScale(0, 1);
        edu.princeton.cs.algs4.StdDraw.enableDoubleBuffering();
        edu.princeton.cs.algs4.StdDraw.picture(.5,.65,"Engine/boid.png", .025, .05, 0);
        edu.princeton.cs.algs4.StdDraw.show();
        edu.princeton.cs.algs4.StdDraw.pause(100000);

    }
}
