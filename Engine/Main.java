package Engine;

import edu.princeton.cs.algs4.StdDraw;
import java.util.Random;
import static java.lang.Math.max;
import static java.lang.Math.min;

public class Main {
    private static Random rand = new Random(52);
    private static int SCREEN_WIDTH = 700;
    private static int SCREEN_HEIGHT = 700;
    private static double BOID_SIZE_MAX = .025 / 2;
    // smallest fraction of BOID_SIZE_MAX that Boid can be (when Z=0)
    private static double BOID_SIZE_MIN_FRACTION = 1.0 / 4;

    /** adds random number of boids with random positions and velocities to flock */
    private static void addInitialBoids(Flock flock) {
        // makes sure we get at least a few boids
        int numBoids = rand.nextInt(15) + 5;
        double posMin = 0.1;
        double posMax = .9;
        double velMax = 0.01;
        for (int i = 0; i < numBoids; i ++) {
            // get random values for pos and vel between posMin and posMax or -velMax and velMax
            // since grid space is between -2 and 2
            // this is a weird way to get random number between -posMax and posMax
            double xPos = min(max(rand.nextDouble(), posMin), posMax);
            double yPos = min(max(rand.nextDouble(), posMin), posMax);
            double zPos = min(max(rand.nextDouble(), posMin), posMax);
            Vector pos = new Vector(xPos, yPos, zPos);
            double xVel = rand.nextDouble() * velMax - velMax;
            double yVel = rand.nextDouble() * velMax - velMax;
            double zVel = rand.nextDouble() * velMax - velMax;
            Vector vel = new Vector(xVel, yVel, zVel);
            new Boid(pos, vel, flock);
        }
    }

    // StdDraw works so that grid is from (-2,-2) to (2,2) for positions no matter the screen width and height
    public static void drawBoids(Flock thisFlock) {
        double xPos;
        double yPos;
        double xVel;
        double yVel;
        double boidSizeMin = BOID_SIZE_MAX * BOID_SIZE_MIN_FRACTION;
        for(int i = 0; i < thisFlock.getBoids().size(); i++) {
            Boid thisBoid = thisFlock.getBoids().get(i);
            Vector pos = thisBoid.getPosition();
            xPos = pos.getX();
            yPos = pos.getY();
            // to have boid in right direction
            Vector vel = thisBoid.getVelocity();
            xVel = vel.getX();
            yVel = vel.getY();
            double radians = Math.atan(yVel / xVel);
            double degrees = radians * 180 / Math.PI + 90;
            if (xVel > 0) {
                degrees += 180;
            }
            double boidSize = BOID_SIZE_MAX;
            if (pos.getDims().size() == 3) {
                boidSize = (boidSize - boidSizeMin) * pos.getZ() + boidSizeMin;
            }
            StdDraw.picture(xPos,yPos,"Engine/boid.png", boidSize, boidSize * 2, degrees);
        }
    }

    public static void main(String[] args) {
        Flock flock = new Flock();
        addInitialBoids(flock);

        StdDraw.setCanvasSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        StdDraw.setScale(0, 1);
        StdDraw.enableDoubleBuffering();

        for (double t = 0.0; true; t += 0.02) {
            StdDraw.clear();
            drawBoids(flock);
            flock.updateBoids();
            StdDraw.pause(10);
            StdDraw.show();
            StdDraw.pause(20);
        }
    }
}
