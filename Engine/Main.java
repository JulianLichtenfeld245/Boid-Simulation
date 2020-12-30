package Engine;

import edu.princeton.cs.introcs.StdDraw;

import java.util.Random;

public class Main {
    private static Random rand = new Random();
    private static int SCREEN_WIDTH = 100;
    private static int SCREEN_HEIGHT = 100;

    private static void addInitialBoids() {
        int numBoids = rand.nextInt();
        int posMax = 100;
        int velMax = 10;
        for (int i = 0; i < numBoids; i ++) {
            double xPos = rand.nextInt(posMax + posMax) - posMax;
            double yPos = rand.nextInt(posMax + posMax) - posMax;
            double xVel = rand.nextInt(velMax + velMax) - velMax;
            double yVel = rand.nextInt(velMax + velMax) - velMax;
            new Boid(xPos, yPos, xVel, yVel);
        }
    }

    public static void drawBoids() {

    }

    public static void updateBoids() {
//        for (Boid boid: Boids) {
//            boid.updateBoid();
//        }
    }

    public static void main(String[] args) {
        addInitialBoids();
        while (true) {
            drawBoids();
            updateBoids();
        }
    }
}
