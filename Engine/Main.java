package Engine;

import edu.princeton.cs.algs4.StdDraw;
import java.util.Random;
import static java.lang.Math.max;
import static java.lang.Math.min;

public class Main {
    private static Random rand = new Random(50);
    // aud feel free to change the size back
    private static int SCREEN_WIDTH = 700;
    private static int SCREEN_HEIGHT = 700;
    private static double BOID_SIZE = .025;

    /** adds random number of boids with random positions and velocities to flock */
    private static void addInitialBoids(Flock flock) {
        int numBoids = rand.nextInt(10);
        double posMin = 0.1;
        double posMax = .9;
        double velMax = 0.01;
        for (int i = 0; i < numBoids; i ++) {
            // get random values for pos and vel between posMin and posMax or -velMax and velMax
            // since grid space is between -2 and 2
            // this is a weird way to get random number between -posMax and posMax
            double xPos = min(max(rand.nextDouble(), posMin), posMax);
            double yPos = min(max(rand.nextDouble(), posMin), posMax);
            double xVel = rand.nextDouble() * velMax - velMax;
            double yVel = rand.nextDouble() * velMax - velMax;
            new Boid(xPos, yPos, xVel, yVel, flock);
        }
    }

    // StdDraw works so that grid is from (-2,-2) to (2,2) for positions no matter the screen width and height
    public static void drawBoids(Flock thisFlock) {
        double xPos;
        double yPos;
        double xVel;
        double yVel;
        for(int i = 0; i < thisFlock.getBoids().size(); i++){
            xPos = thisFlock.getBoids().get(i).position.getX();
            yPos = thisFlock.getBoids().get(i).position.getY();
            // to have boid in right direction
            xVel = thisFlock.getBoids().get(i).getVelocity().getX();
            yVel = thisFlock.getBoids().get(i).getVelocity().getY();
            double radians = Math.atan(yVel / xVel);
            double degrees = radians * 180 / Math.PI + 90;
            if (xVel > 0) {
                degrees += 180;
            }
            StdDraw.picture(xPos,yPos,"Engine/boid.png", BOID_SIZE, BOID_SIZE * 2, degrees);
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
            StdDraw.pause(20);
            StdDraw.show();
            StdDraw.pause(30);
        }
    }
}
