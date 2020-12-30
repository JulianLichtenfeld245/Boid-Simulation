package Engine;

import java.util.ArrayList;

public class Boid {
    private Vector position;
    private Vector velocity;
    private static double COHESION_RATE = 1.0 / 100;
    private static double ALIGNMENT_RATE = 1.0 / 8;
    private static double TOO_CLOSE_DISTANCE = 10;
    private static double NEARBY_BOID_RADIUS = 100;
    // may want to have a Boids class with methods getBoids and addBoid so that we can have different
    // types of boids on one screen
    private static ArrayList<Boid> BOIDS = new ArrayList<>();

    public Boid(Vector pos, Vector vel) {
        position = pos;
        velocity = vel;
        BOIDS.add(this);
    }

    public Boid(double posX, double posY, double velX, double velY) {
        this(new Vector(posX, posY), new Vector(velX, velY));
    }

    public Vector getPosition() {
        return position;
    }

    public Vector getVelocity() {
        return  velocity;
    }

    /** calculates the center of mass of nearby boids and adjusts velocity vector to match
     * center of mass by COSHESION_RATE. Returns the adjustment to the boid's velocity vector
     * that should be added to its current velocity vector
     * @param nearbyBoids
     */
    public Vector cohesionRule(ArrayList<Boid> nearbyBoids) {
        Vector massCenter = new Vector();
        for (Boid boid: nearbyBoids) {
            massCenter = massCenter.add(boid.getPosition());
        }
        massCenter = massCenter.multiply(1.0 / nearbyBoids.size());
        return massCenter.multiply(COHESION_RATE);
    }

    /** calculates the average velocity of neraby boids and adjusts velocity vector to match
     * this average by some ALIGNMENT_RATE. Returns the adjustment to the boid's velocity vector
     * that should be added to its current velocity vector
     * @param nearbyBoids
     * @return
     */
    public Vector alignmentRule(ArrayList<Boid> nearbyBoids) {
        Vector avgVel = new Vector();
        for (Boid boid: nearbyBoids) {
            avgVel = avgVel.add(boid.getVelocity());
        }
        avgVel = avgVel.multiply(1.0 / nearbyBoids.size());
        return avgVel.multiply(ALIGNMENT_RATE);
    }

    /** returns velocity vector to adjust boid's velocity to move away from nearby boids
     * that are within TOO_CLOSE_DISTANCE distance away
     * @param nearbyBoids
     * @return
     */
    public Vector separationRule(ArrayList<Boid> nearbyBoids) {
        Vector separationVel = new Vector();
        for (Boid boid: nearbyBoids) {
            double dist = getPosition().distance(boid.getPosition());
            if (dist < TOO_CLOSE_DISTANCE) {
                separationVel = separationVel.add(getPosition().subtract(boid.getPosition()));
            }
        }
        return separationVel;
    }

    public ArrayList<Boid> getBOIDS() {
        return BOIDS;
    }

    public ArrayList<Boid> nearbyBoids() {
//        need a good algorithm for range search and can then specify a circle around the boid to include
//        in nearbyBoids
//        ex:
//        circle = circleAroundBoid()
//        return rangeSearch(BOIDS, circle)
        // naiveNearbyBoids below needs to be replaced with more efficient version, KD Tree maybe with range
        // searching, but we want it to be able to handle 3 dimensions as well?
        ArrayList<Boid> nearby = new ArrayList<>();
        for (Boid boid: BOIDS) {
            if (boid != this && boid.getPosition().distance(getPosition()) < NEARBY_BOID_RADIUS) {
                nearby.add(boid);
            }
        }
        return nearby;
    }

    public void updateBoid() {
        ArrayList<Boid> nearbyBoids = nearbyBoids();
        // apply all rules to boid
        Vector v1 = cohesionRule(nearbyBoids);
        Vector v2 = alignmentRule(nearbyBoids);
        Vector v3 = separationRule(nearbyBoids);
        velocity = getVelocity().add(v1).add(v2).add(v3);
        position = getPosition().add(getVelocity());
    }

    public boolean equals(Object o) {
        Boid other = (Boid) o;
        return getPosition().equals(other.getPosition()) && getVelocity().equals(other.getVelocity());
    }

    public String toString() {
        return "pos: " + getPosition().toString() + ", vel: " + getVelocity().toString();
    }
}
