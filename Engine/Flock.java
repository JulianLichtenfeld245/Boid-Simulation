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

    /** updates each boid in a flock */
    public void updateBoids() {
        for (Boid boid: getBoids()) {
            boid.updateBoid();
        }
    }
}
