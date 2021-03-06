
import java.util.ArrayList;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;

public class PointSET {

    private SET<Point2D> points;

    public PointSET() {

        points = new SET<Point2D>();
    }
    public boolean isEmpty() {
        // is the set empty? 

        return points.isEmpty(); 
    }
    public int size() {
        // number of points in the set 

        return points.size(); 
    }
    public void insert(Point2D p) {
        // add the point to the set (if it is not already in the set)
        if (p == null)
            throw new IllegalArgumentException();
        
        points.add(p);

    }
    public boolean contains(Point2D p) {
        // does the set contain point p? 
        if (p == null)
            throw new IllegalArgumentException();
        
        return points.contains(p);
    }
    public void draw() {
        // draw all points to standard draw 
        for (Point2D p : points) {
            p.draw();
        }
    }
    public Iterable<Point2D> range(RectHV rect) {
        // all points that are inside the rectangle (or on the boundary) 
        
        if (rect == null)
            throw new IllegalArgumentException();
        
        ArrayList<Point2D> inRange = new ArrayList<Point2D>();

        for (Point2D p : points) {
            if (rect.contains(p))
                inRange.add(p);
        }
        
        return inRange;
    }
    public Point2D nearest(Point2D p) {
        // a nearest neighbor in the set to point p; null if the set is empty 
        
        if (p == null)
            throw new IllegalArgumentException();
        if (points.isEmpty())
            return null;
        
        Point2D nearest = new Point2D(p.x(), p.y());
        double dist = Double.MAX_VALUE;
        for (Point2D k : points) {
            double tmpDist = p.distanceTo(k);
            if (dist > tmpDist) {
                dist = tmpDist;
                nearest = k;
            }
        }
        
        return nearest;
    }

    public static void main(String[] args) {
        // unit testing of the methods (optional) 
    }
}