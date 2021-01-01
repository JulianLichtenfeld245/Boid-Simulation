package Engine;

import edu.princeton.cs.introcs.StdDraw;

import java.util.Random;

public class Main {
    private static Random rand = new Random();
    private static int SCREEN_WIDTH = 100;
    private static int SCREEN_HEIGHT = 100;

    /** adds random number of boids with random positions and velocities to flock */
    private static void addInitialBoids(Flock flock) {
        int numBoids = rand.nextInt(10);
        int posMax = 100;
        int velMax = 10;
        for (int i = 0; i < numBoids; i ++) {
            double xPos = rand.nextInt(posMax + posMax) - posMax;
            double yPos = rand.nextInt(posMax + posMax) - posMax;
            double xVel = rand.nextInt(velMax + velMax) - velMax;
            double yVel = rand.nextInt(velMax + velMax) - velMax;
            new Boid(xPos, yPos, xVel, yVel, flock);
        }
    }

    public static void drawBoids() {

    }

    public static void main(String[] args) {
//        Flock flock = new Flock();
//        addInitialBoids(flock);
//        while (true) {
//            drawBoids();
//            flock.updateBoids();
//        }
        StdDraw.setPenRadius(0.05);
        StdDraw.setPenColor(StdDraw.BLUE);
        StdDraw.point(0.5, 0.5);
        StdDraw.setPenColor(StdDraw.MAGENTA);
        StdDraw.line(0.2, 0.2, 0.8, 0.2);
    }
}
