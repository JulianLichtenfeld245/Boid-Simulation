package Engine;

import java.util.ArrayList;

public class Boid {
    private Vector position;
    private Vector velocity;
    private static double COHESION_RATE = 1.0 / 100;
    private static double ALIGNMENT_RATE = 1.0 / 8;
    private static double TOO_CLOSE_DISTANCE = 10;

    public Boid(Vector pos, Vector vel) {
        position = pos;
        velocity = vel;
    }

    public Boid(double posX, double posY, double velX, double velY) {
        Vector pos = new Vector(posX, posY);
        Vector vel = new Vector(velX, velY);
        position = pos;
        velocity = vel;
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
     * @param proximityBoids
     */
    public Vector cohesionRule(ArrayList<Boid> proximityBoids) {
        Vector massCenter = new Vector();
        for (Boid boid: proximityBoids) {
            massCenter = massCenter.add(boid.getPosition());
        }
        massCenter = massCenter.multiply(1.0 / proximityBoids.size());
        return massCenter.multiply(COHESION_RATE);
    }

    /** calculates the average velocity of neraby boids and adjusts velocity vector to match
     * this average by some ALIGNMENT_RATE. Returns the adjustment to the boid's velocity vector
     * that should be added to its current velocity vector
     * @param proximityBoids
     * @return
     */
    public Vector alignmentRule(ArrayList<Boid> proximityBoids) {
        Vector avgVel = new Vector();
        for (Boid boid: proximityBoids) {
            avgVel = avgVel.add(boid.getVelocity());
        }
        avgVel = avgVel.multiply(1.0 / proximityBoids.size());
        return avgVel.multiply(ALIGNMENT_RATE);
    }

    /** returns velocity vector to adjust boid's velocity to move away from nearby boids
     * that are within TOO_CLOSE_DISTANCE distance away
     * @param proximityBoids
     * @return
     */
    public Vector separationRule(ArrayList<Boid> proximityBoids) {
        Vector separationVel = new Vector();
        for (Boid boid: proximityBoids) {
            double dist = getPosition().distance(boid.getPosition());
            if (dist < TOO_CLOSE_DISTANCE) {
                separationVel = separationVel.add(getPosition().subtract(boid.getPosition()));
            }
        }
        return separationVel;
    }

    public ArrayList<Boid> nearbyBoids()

    public boolean equals(Object o) {
        Boid other = (Boid) o;
        return getPosition().equals(other.getPosition()) && getVelocity().equals(other.getVelocity());
    }

    public String toString() {
        return "pos: " + getPosition().toString() + ", vel: " + getVelocity().toString();
    }
}