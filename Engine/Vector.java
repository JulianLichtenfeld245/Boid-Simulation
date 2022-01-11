package Engine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Vector {
    private double x;
    private double y;
    private double z;
    private boolean dim3;
    private ArrayList<Double> dims = new ArrayList<>();

    public Vector(double givenX, double givenY, double givenZ) {
//        x = givenX;
//        y = givenY;
//        z = givenZ;
//        dim3 = true;
        List<Double> dimensions = Arrays.asList(givenX, givenY, givenZ);
        dims.addAll(dimensions);
    }

    public Vector(double givenX, double givenY) {
//        x = givenX;
//        y = givenY;
//        dim3 = false;
        List<Double> dimensions = Arrays.asList(givenX, givenY);
        dims.addAll(dimensions);
    }

    public Vector(ArrayList<Double> dimensions) {
        dims = dimensions;
    }

    /** creates a vector at (0, 0, 0), might need to change to avoid bugs with dimensionality
     * issues */
    public Vector() {
        this(new ArrayList<Double>(Arrays.asList(0.0, 0.0)));
    }

    public static Vector zeroVector(int numDims) {
        ArrayList<Double> dims = new ArrayList<>();
        for (int i=0; i < numDims; i++) {
            dims.add(0.0);
        }
        return new Vector(dims);
    }

    public double getX() {
        return dims.get(0);
    }

    public double getY() {
        return dims.get(1);
    }

    public double getZ() { return dims.get(2); }

    /** returns whether vector is 3D */
    public boolean getDim3() { return dim3; }

    public ArrayList<Double> getDims() { return dims; }

    public double getMagnitude() {
        Double sum = 0.0;
        for (int i=0; i < dims.size(); i++) {
            sum += Math.pow(getDims().get(i), 2);
        }
        return Math.sqrt(sum);
    }

    /** returns a new vector with x and y values equal to the sum of otherVector and self values
     * without modifying either input vectors*/
    public Vector add(Vector otherVector) {
        ArrayList<Double> newDims = new ArrayList();
        for (int i=0; i < dims.size(); i++) {
            Double v1Dim = getDims().get(i);
            Double v2Dim = otherVector.getDims().get(i);
            newDims.add(v1Dim + v2Dim);
        }
        return new Vector(newDims);
//        double xSum = getX() + otherVector.getX();
//        double ySum = getY() + otherVector.getY();
//        if (!this.getDim3()) {
//            return new Vector(xSum, ySum);
//        }
//        double zSum = getZ() + otherVector.getZ();
//        return new  Vector(xSum, ySum, zSum);
    }

    /** sums multiple vectors together with same technique as adding two vectors (see above)
     * is static so that an initial vector is not necessary. i.e. Vector.add(vectors) can be called */
    public static Vector add(ArrayList<Vector> vectors) {
        ArrayList<Double> newDims = new ArrayList();
        for (int i=0; i < vectors.get(0).getDims().size(); i++) {
            newDims.add(0.0);
        }
        for (Vector vector: vectors) {
            for (int i=0; i < vector.getDims().size(); i++) {
                newDims.set(i, vector.getDims().get(i) + newDims.get(i));
            }
        }
        return new Vector(newDims);
    }

    /** x.subtract(y) returns a new vector x - y (matrix subtraction) */
    public Vector subtract(Vector otherVector) {
        ArrayList<Double> newDims = new ArrayList();
        for (int i=0; i < dims.size(); i++) {
            Double v1Dim = getDims().get(i);
            Double v2Dim = otherVector.getDims().get(i);
            newDims.add(v1Dim - v2Dim);
        }
        return new Vector(newDims);
//        double xDiff = getX() - otherVector.getX();
//        double yDiff = getY() - otherVector.getY();
//        if (!this.getDim3()) {
//            return new Vector(xDiff, yDiff);
//        }
//        double zDiff = getZ() - otherVector.getZ();
//        return new Vector(xDiff, yDiff, zDiff);
    }

    public Vector multiply(double factor) {
        ArrayList<Double> newDims = new ArrayList();
        for (int i=0; i < dims.size(); i++) {
            Double v1Dim = getDims().get(i);
            newDims.add(v1Dim * factor);
        }
        return new Vector(newDims);
//        double xProduct = getX() * factor;
//        double yProduct = getY() * factor;
//        if (!this.getDim3()) {
//            return new Vector(xProduct, yProduct);
//        }
//        double zProduct = getZ() * factor;
//        return new Vector(xProduct, yProduct, zProduct);
    }

    /** exponentiates each dimension by factor
     *
     * @param factor
     * @return
     */
    public Vector expon(double factor) {
        ArrayList<Double> newDims = new ArrayList();
        for (int i=0; i < dims.size(); i++) {
            Double v1Dim = getDims().get(i);
            newDims.add(Math.pow(v1Dim, factor));
        }
        return new Vector(newDims);
    }

    /** returns the euclidian distance between two vectors */
    public double distance(Vector otherVector) {
        // vector describing the distance between the self vector and otherVector
        Vector distanceVector = subtract(otherVector);
        Double sum = 0.0;
        for (int i=0; i < dims.size(); i++) {
            sum += Math.pow(distanceVector.getDims().get(i), 2);
        }
        return Math.sqrt(sum);
    }

    public ArrayList<Double> angle() {
        ArrayList<Double> angle = new ArrayList();
        double xVel = getX();
        double yVel = getY();
        double thetaRadians = Math.atan(yVel / xVel);
        double thetaDegrees = thetaRadians * 180 / Math.PI + 90;
        if (xVel > 0) {
            thetaDegrees += 180;
        }
        angle.add(thetaDegrees);
        if (getDims().size() == 3) {
            double zVel = getZ();
            if (zVel == 0 || xVel == 0) {
                angle.add(0.0);
                return angle;
            }
            double phiRadians = Math.atan(Math.sqrt(Math.pow(yVel, 2) / Math.pow(xVel, 2)) / zVel);
            double phiDegrees = phiRadians * 180 / Math.PI + 90;
            angle.add(phiDegrees);
        }
        return angle;
    }

    /** returns the angle between the boid (based on its velocity vector) and a position
     * vector in terms of degrees
     * NOTE: This function does not generalize to higher dimensions than 3
     * @param posVector
     * @return
     */
    public double angle(Vector posVector) {
        return 0.0;
    }
    /** */
    public boolean nearby(Vector otherVector, double angle) {
        return false;
        }

    public boolean equals(Object o) {
        Vector other = (Vector) o;
        boolean equal = true;
        for (int i=0; i < dims.size(); i++) {
            equal = equal && this.getDims().get(i) == other.getDims().get(i);
        }
        return equal;
    }

    public String toString() {
        String str = "(";
        int i = 0;
        while(i + 1 < getDims().size()) {
            str += Double.toString(getDims().get(i)) + ", ";
            i += 1;
        }
        str += Double.toString(getDims().get(i));
        str += ")";
        return str;
    }
}
