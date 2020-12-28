package Engine;

import java.util.ArrayList;

public class Boid {
    private Vector position;
    private Vector velocity;
    private static double COHESION_RATE = 1.0 / 100;

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
}
