package Engine;

import java.util.ArrayList;

public class Flock {
    private ArrayList<Boid> boids;
//    can add color or other signifier later between different flocks
//    private color;

    public Flock() {
        boids = new ArrayList<>();
    }

    /** adds a boid to this collection of boids */
    public void add(Boid boid) {
        boids.add(boid);
    }

    /** returns all boids in this flock */
    public ArrayList<Boid> getBoids() {
        return boids;
    }

//    public void updateBoidsAlt() {
//        for (Boid boid: getBoids()) {
//            boid.updateBoid();
//        }
//    }

    /** updates each boid in a flock */
    public void updateBoids() {
        // array of new velocities in order of the boids in the flock
//        ArrayList<Vector> newVelocities = new ArrayList<>();
        for (Boid boid: getBoids()) {
//            ArrayList<Boid> nearbyBoids = boid.nearbyBoids();
            boid.updateBoid();
//            Vector v1 = boid.cohesionRule(nearbyBoids);
//            Vector v2 = boid.alignmentRule(nearbyBoids);
//            Vector v3 = boid.separationRule(nearbyBoids);
//            newVelocities.add(boid.getVelocity().add(v1).add(v2).add(v3));
//        }
//        for (int i = 0; i < newVelocities.size(); i ++) {
//            Boid boid = boids.get(i);
//            Vector newVelocity = newVelocities.get(i);
//            boid.position = boid.getPosition().add(boid.getVelocity());
//            boid.velocity = newVelocity;
        }
    }
}
