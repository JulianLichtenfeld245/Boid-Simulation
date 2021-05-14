package Engine;

import java.util.ArrayList;

public class Vector {
    private double x;
    private double y;

    public Vector(double givenX, double givenY) {
        x = givenX;
        y = givenY;
    }

    /** creates a vector at (0, 0) */
    public Vector() {
        new Vector(0, 0);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    /** returns a new vector with x and y values equal to the sum of otherVector and self values
     * without modifying either input vectors*/
    public Vector add(Vector otherVector) {
        double xSum = getX() + otherVector.getX();
        double ySum = getY() + otherVector.getY();
        return new Vector(xSum, ySum);
    }

    public Vector add(ArrayList<Vector> vectors) {
        Vector sum_vec = new Vector();
        for (Vector vector: vectors) {
            sum_vec = sum_vec.add(vector);
        }
        return sum_vec;
    }

    /** returns a new vector with x and y values equal to the value of self values minus otherVector values
     * without modifying either input vectors*/
    public Vector subtract(Vector otherVector) {
        double xDiff = getX() - otherVector.getX();
        double yDiff = getY() - otherVector.getY();
        return new Vector(xDiff, yDiff);
    }

    public Vector multiply(double factor) {
        double xProduct = getX() * factor;
        double yProduct = getY() * factor;
        return new Vector(xProduct, yProduct);
    }

    /** returns the euclidian distance between two vectors */
    public double distance(Vector otherVector) {
        // vector describing the distance between the self vector and otherVector
        Vector distanceVector = subtract(otherVector);
        return Math.sqrt(Math.pow(distanceVector.getX(), 2) + Math.pow(distanceVector.getY(), 2));
    }

    public boolean equals(Object o) {
        Vector other = (Vector) o;
        return x == other.getX() && y == other.getY();
    }

    public String toString() {
        return "(" + Double.toString(x) + ", " + Double.toString(y) + ")";
    }
}
