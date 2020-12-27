package Engine;

public class Vector {
    private double x;
    private double y;

    public Vector(double givenX, double givenY) {
        x = givenX;
        y = givenY;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    /** adds vector1 and vector2 x and y values */
    public Vector add(Vector vector1, Vector vector2) {
        double xSum = vector1.getX() + vector2.getX();
        double ySum = vector1.getY() + vector2.getY();
        return new Vector(xSum, ySum);
    }

    /** subtracts vector2 from vector1 x and y values */
    public Vector subtract(Vector vector1, Vector vector2) {
        double xDiff = vector1.getX() - vector2.getX();
        double yDiff = vector1.getY() - vector2.getY();
        return new Vector(xDiff, yDiff);
    }

    public boolean equals(Object o) {
        Vector other = (Vector) o;
        return x == other.getX() && y == other.getY();
    }

    public String toString() {
        return "Vector (" + Double.toString(x) + ", " + Double.toString(y) + ")";
    }
}
