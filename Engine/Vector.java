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

    /** returns a new vector with x and y values equal to the value of self values minus otherVector values
     * without modifying either input vectors*/
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

    public boolean equals(Object o) {
        Vector other = (Vector) o;
        boolean equal = true;
        for (int i=0; i < dims.size(); i++) {
            equal = equal && this.getDims().get(i) == other.getDims().get(i);
        }
        return equal;
    }

    public String toString() {
        String string = "(" + Double.toString(getX()) + ", " + Double.toString(getY());
        if (getDims().size() == 3) {
            string += ", " + Double.toString((getZ()));
        }
        string += ")";
        return string;
    }
}
