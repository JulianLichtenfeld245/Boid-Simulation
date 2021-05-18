package Engine;

import java.util.ArrayList;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class Boid {
    public Vector position;
    public Vector velocity;
    private Flock myFlock;
    private static double GRID_LENGTH = 1.0;
    private static double COHESION_RATE = 1.0 / 100;
    private static double ALIGNMENT_RATE = 1.0 / 10;
    private static double TOO_CLOSE_RATE = 1.0 / 2;
    //    private static double COHESION_RATE = 0;
//    private static double ALIGNMENT_RATE = 0;
    private static double TOO_CLOSE_DISTANCE = GRID_LENGTH / 20;
    private static double NEARBY_BOID_RADIUS = GRID_LENGTH / 4;
    // when its w/in 1/8 of the grid length (1x1) from the wall
    private static double tooClosetoWall = GRID_LENGTH / 8;
    private static double wallAdjustAmount = GRID_LENGTH;
    private static double AVOID_WALL_RATE = GRID_LENGTH / 70;
    private static double SPEED_LIMIT = GRID_LENGTH / 50;

    public Boid(Vector pos, Vector vel, Flock flock) {
        position = pos;
        velocity = vel;
        myFlock = flock;
        flock.add(this);
    }

    public Boid(double posX, double posY, double velX, double velY, Flock flock) {
        this(new Vector(posX, posY), new Vector(velX, velY), flock);
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
        // the max w/ 1 stops bugs if nearbyBoids is empty
        massCenter = massCenter.multiply(1.0 / max(nearbyBoids.size(), 1));
        Vector cohesionAdjument = massCenter.subtract(getPosition());
        return cohesionAdjument.multiply(COHESION_RATE);
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
        // the max w/ 1 stops bugs if nearbyBoids is empty
        avgVel = avgVel.multiply(1.0 / max(nearbyBoids.size(), 1));
        Vector alignmentAdjustment = avgVel.subtract(getVelocity());
        return alignmentAdjustment.multiply(ALIGNMENT_RATE);
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
        separationVel = separationVel.multiply(TOO_CLOSE_RATE);
        return separationVel;
    }

    /** If boid gets within tooClosetoWall, then add to vel vectors so boid will move away from walls.
     * Later, make it so they move from walls with increasing intensity as it gets closer to the wall*/
    public Vector avoidWalls() {
        Vector avoidWallVel = new Vector();
        // how long grid is from center to a side
        if (position.getX() < tooClosetoWall) {
            avoidWallVel = new Vector(wallAdjustAmount, 0);
//            avoidWallVel = avoidWallVel.subtract(new Vector(getPosition().getX() - GRID_LENGTH, 0));
        }
        else if (position.getX() > GRID_LENGTH - tooClosetoWall) {
            avoidWallVel = new Vector(-wallAdjustAmount, 0);
//            avoidWallVel = avoidWallVel.subtract(new Vector(getPosition().getX(), 0));
        }
        if (position.getY() < tooClosetoWall) {
            avoidWallVel = new Vector(0, wallAdjustAmount);
//            avoidWallVel = avoidWallVel.subtract(new Vector(0, getPosition().getY() - GRID_LENGTH));
        }
        else if (position.getY() > GRID_LENGTH - tooClosetoWall) {
            avoidWallVel = new Vector(0, -wallAdjustAmount);
//            avoidWallVel = avoidWallVel.subtract(new Vector(0, getPosition().getY()));
        }
        return avoidWallVel.multiply(AVOID_WALL_RATE);
    }

    /** takes in a vector and outputs a vector where x and y are at most the absolute values
     * of the SPEED_Limit*/
    public Vector speedLimit(Vector v) {
        return new Vector(min(max(-SPEED_LIMIT, v.getX()), SPEED_LIMIT), min(max(-SPEED_LIMIT, v.getY()), SPEED_LIMIT));
    }

    public Flock getMyFlock() {
        return myFlock;
    }

    /** returns the boids in this boid's flock not including the current boid */
    public ArrayList<Boid> getBoids() {
        ArrayList<Boid> boids = new ArrayList<>(getMyFlock().getBoids());
        boids.remove(this);
        return boids;
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
        for (Boid boid: getBoids()) {
            if (boid != this && boid.getPosition().distance(getPosition()) < NEARBY_BOID_RADIUS) {
                nearby.add(boid);
            }
        }
        return nearby;
    }

    /** takes all nearbyBoids and applies each rule to each boid. It then updates the velocity and
     * position of each boid */
    public void updateBoid() {
        ArrayList<Boid> nearbyBoids = nearbyBoids();
        // apply all rules to boid
        Vector v1 = cohesionRule(nearbyBoids);
        Vector v2 = alignmentRule(nearbyBoids);
        Vector v3 = separationRule(nearbyBoids);
        Vector v4 = avoidWalls();
        velocity = speedLimit(getVelocity().add(v1).add(v2).add(v3).add(v4));
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
