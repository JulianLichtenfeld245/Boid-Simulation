package Engine;

import edu.princeton.cs.algs4.StdDraw;

import java.util.Random;

public class Main {
    private static Random rand = new Random();
    private static int SCREEN_WIDTH = 512;
    private static int SCREEN_HEIGHT = 512;

    /** adds random number of boids with random positions and velocities to flock */
    private static void addInitialBoids(Flock flock) {
        int numBoids = rand.nextInt(10);
        int posMax = 15;
        int velMax = 1;
        for (int i = 0; i < numBoids; i ++) {
            double xPos = rand.nextInt(posMax + posMax) - posMax;
            double yPos = rand.nextInt(posMax + posMax) - posMax;
            double xVel = rand.nextInt(velMax + velMax) - velMax;
            double yVel = rand.nextInt(velMax + velMax) - velMax;
            new Boid(xPos, yPos, xVel, yVel, flock);
        }
    }

    public static void drawBoids(Flock thisFlock) {
        double x;
        double y;
        for(int i = 0; i < thisFlock.getBoids().size(); i++){
            x = thisFlock.getBoids().get(i).position.getX()*0.1;
            y = thisFlock.getBoids().get(i).position.getY()*0.1;
            StdDraw.picture(x,y,"Engine/boid.png");
        }
    }

    public static void main(String[] args) {
        Flock flock = new Flock();
        addInitialBoids(flock);

        StdDraw.setCanvasSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        StdDraw.setScale(-2, +2);
        StdDraw.enableDoubleBuffering();

        for (double t = 0.0; true; t += 0.02) {
            StdDraw.clear();
            drawBoids(flock);
            flock.updateBoids();
            StdDraw.pause(200);
            StdDraw.show();
            StdDraw.pause(30);
        }
    }
}
