package Engine;

import java.util.ArrayList;

import static java.lang.Math.*;

public class Boid {
    public Vector position;
    public Vector velocity;
    private Flock myFlock;
//    Grid is a 1 x 1
    private static double GRID_LENGTH = 1.0;

//    Alter below private static variables to change boid characteristics
    private static double COHESION_RATE = 1.0 / 100;
    private static double ALIGNMENT_RATE = 1.0 / 10;
    private static double TOO_CLOSE_RATE = 1.0 / 10;
    private static double TOO_CLOSE_DISTANCE = GRID_LENGTH / 10;
    private static double NEARBY_BOID_RADIUS = GRID_LENGTH / 4;
    private static double NEARBY_BOID_NUMBER = 12;
    // incentivize moving away from wall when boid w/in 1/x of the grid length (1x1) from the wall
    private static double tooClosetoWall = GRID_LENGTH / 8;
    private static double tooClosetoCenter = GRID_LENGTH / 15;
    private static double wallAdjustAmount = GRID_LENGTH;
    private static double AVOID_WALL_RATE = GRID_LENGTH / 70;
    private static double SPEED_LIMIT = GRID_LENGTH / 50;


//    Fun shapes
//    If any shape_rate is set to 0, the shape incentive function will not be run
//    To use a shape, a rate above 50 is recommended
//    CENTER is not recommended, mostly used for testing, rate should be a fraction of 1
    private static double CENTER_RATE = 0;

//    SPHERE
//    recommend rate of 100
    private static double SPHERE_RATE = 100;
    private static double SPHERE_RADIUS = 1.0 / 3;
//    exponential seems to smooth movement (prevent jerking) but non-expon also has
//    cool movement
    private static boolean USE_EXPONENTIAL = true;
//    DISK
    private static double DISK_RATE =0;
    private static double DISK_RADIUS = 1.0 / 3;
    private static double DISK_Y_ADJ = 1;


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
        ArrayList<Vector> positions = new ArrayList<>();
        for (Boid boid: nearbyBoids) {
            positions.add(boid.getPosition());
        }
        Vector massCenter = Vector.add(positions);
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
        ArrayList<Vector> vels = new ArrayList<>();
        for (Boid boid: nearbyBoids) {
            vels.add(boid.getVelocity());
        }

        Vector sumVel = Vector.add(vels);
        // the max w/ 1 stops bugs if nearbyBoids is empty
        Vector avgVel = sumVel.multiply(1.0 / max(nearbyBoids.size(), 1));
        Vector alignmentAdjustment = avgVel.subtract(getVelocity());
        return alignmentAdjustment.multiply(ALIGNMENT_RATE);
    }

    /** returns velocity vector to adjust boid's velocity to move away from nearby boids
     * that are within TOO_CLOSE_DISTANCE distance away
     * @param nearbyBoids
     * @return
     */
    public Vector separationRule(ArrayList<Boid> nearbyBoids) {
        Vector separationVel = Vector.zeroVector(getPosition().getDims().size());
        for (Boid boid: nearbyBoids) {
            double dist = getPosition().distance(boid.getPosition());
            if (dist < TOO_CLOSE_DISTANCE) {
                separationVel = separationVel.add(getPosition().subtract(boid.getPosition()));
            }
        }
        separationVel = separationVel.multiply(TOO_CLOSE_RATE);
        return separationVel;
    }

    /** If boid gets within tooClosetoWall, then add to vel vectors so boid will move away from walls */
    public Vector avoidWalls() {
        ArrayList<Double> avoidDims = new ArrayList<>();
        for (double dim: position.getDims()) {
            if (dim < tooClosetoWall) {
                avoidDims.add(wallAdjustAmount);
            }
            else if (dim > GRID_LENGTH - tooClosetoWall) {
                avoidDims.add(-wallAdjustAmount);
            }
            else {
                avoidDims.add(0.0);
            }
        }

        Vector avoidWallVel = new Vector(avoidDims);
        return avoidWallVel.multiply(AVOID_WALL_RATE);
    }

    public Vector circleCenter() {
        double halfGrid = GRID_LENGTH / 2;
        ArrayList<Double> centerDims = new ArrayList<>();
        for (double dim: position.getDims()) {
            centerDims.add(halfGrid);
        }
        Vector center = new Vector(centerDims);
        double dist = center.distance(getPosition());
        Vector circCenter = center.subtract(getPosition());
        return circCenter.multiply(CENTER_RATE);
    }

    /** returns a vector that incentivizes boids to maintain SPHERE_RADIUS distance (in 3D)
     * from the grid center
     *
     * @return
     */
    public Vector sphereCenter() {
        double halfGrid = GRID_LENGTH / 2;
        ArrayList<Double> centerDims = new ArrayList<>();
        for (double dim : position.getDims()) {
            centerDims.add(halfGrid);
        }
        Vector center = new Vector(centerDims);
        double distToCenter = center.distance(getPosition());
        Vector toCenter = center.subtract(getPosition());
//        exponential smooths distance to radius vector so locations close to sphere edge (radius
//         dist from center) don't receive large incentive to move closer to sphere edge
        double total_sphere_rate;
        if (USE_EXPONENTIAL) {
            total_sphere_rate = Math.pow(distToCenter - SPHERE_RADIUS, 3) * SPHERE_RATE;
        } else {
            total_sphere_rate = (distToCenter - SPHERE_RADIUS) / 200 * SPHERE_RATE;
        }
        return toCenter.multiply(total_sphere_rate);
    }

    /** returns a vector that incentivizes boids to maintain SPHERE_RADIUS distance (in 2D,
     * X and Z, NOT Y) from the grid center, adds vector encouraging towards center y value
     * @return
     */
    public Vector DiskCenter() {
        double halfGrid = GRID_LENGTH / 2;
        ArrayList<Double> centerDims = new ArrayList<>();
        for (double dim : position.getDims()) {
            centerDims.add(halfGrid);
        }
        Vector center = new Vector(centerDims);
//        set center Y to position Y so they cancel in distance calculations
        Vector cancelY = new Vector(0, getPosition().getY() - center.getY(), 0);
//        center in X and Z but not Y
        Vector pseudoCenter = center.add(cancelY);
        Vector toCenter = pseudoCenter.subtract(getPosition());
//        Double sum = 0.0;
////        X Dimension
//        sum += Math.pow(toCenter.getDims().get(0), 2);
////        Z Dimension
//        sum += Math.pow(toCenter.getDims().get(2), 2);
//        double distToCenter = Math.sqrt(sum);

        double distToCenter = pseudoCenter.distance(getPosition());
        double total_disk_rate;
        if (USE_EXPONENTIAL) {
            total_disk_rate = Math.pow(distToCenter - DISK_RADIUS, 3) * DISK_RATE;
        } else {
            total_disk_rate = (distToCenter - DISK_RADIUS) / 200 * DISK_RATE;
        }
//        add to vector to incentivize being close to Y center plane
        double yMag = (center.getY() - getPosition().getY()) * DISK_Y_ADJ;
        Vector yAdjustment = new Vector(0, yMag, 0);
        toCenter = toCenter.add(yAdjustment);

        return toCenter.multiply(total_disk_rate);
    }

    /** takes in a vector and outputs a vector where each dimension is at most the absolute values
     * of the SPEED_Limit*/
    public Vector speedLimit(Vector v) {
        ArrayList<Double> newVel= new ArrayList<>();
        for (int i=0; i < v.getDims().size(); i++)
            newVel.add(min(max(-SPEED_LIMIT, v.getDims().get(i)), SPEED_LIMIT));
        return new Vector(newVel);
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

    /** return all boids within a boid's radius
     *
     * @return
     */
    public ArrayList<Boid> nearbyBoids() {
//        Future capabilities:
//        - more efficient search
//        - only find boids next to or in front of boid, not behind (see Craig Reynold's boid neighborhood)
        ArrayList<Boid> nearby = new ArrayList<>();
        for (Boid boid: getBoids()) {
            if (nearby.size() >= NEARBY_BOID_NUMBER) {
                return nearby;
            }
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
        Vector vel = getVelocity();
        // apply all rules to boid, first 3 rules only apply if there are nearby boids
        if (!nearbyBoids.isEmpty()) {
            Vector v1 = cohesionRule(nearbyBoids);
            Vector v2 = alignmentRule(nearbyBoids);
            Vector v3 = separationRule(nearbyBoids);
            vel = vel.add(v1).add(v2).add(v3);
        }
            if (CENTER_RATE > 0) {
                Vector v5 = circleCenter();
                vel = vel.add(v5);
            }
            if (SPHERE_RATE > 0) {
                Vector v6 = sphereCenter();
                vel = vel.add(v6);
            }
            if (DISK_RATE > 0) {
                Vector v7 = DiskCenter();
                vel = vel.add(v7);
            }
        Vector v4 = avoidWalls();
        System.out.println(vel);
        velocity = speedLimit(vel.add(v4));
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
