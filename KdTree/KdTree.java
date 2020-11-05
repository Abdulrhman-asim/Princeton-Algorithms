import java.util.ArrayList;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class KdTree {

    private Node root;
    private int size;

    private class Node {
        Point2D p;
        // True = Horizontal ### False = Vertical
        Node left;
        Node right;
        RectHV boundary;

        public Node(Point2D point, RectHV b) {
            p = point;
            left = null;
            right = null;
            boundary = b;

        }
    }

    public KdTree() {
        // construct an empty set of points 
        root = null;
        size = 0;

    }
    public boolean isEmpty() {
        // is the set empty? 
        return size == 0;
    }
    public int size() {
        // number of points in the set 
        return size;
    }
    public void insert(Point2D p) {
        // add the point to the set (if it is not already in the set)


        root = add(root, p, false, 0, 0, 1, 1);


    }

    private Node add(Node root, Point2D p, boolean orient, double x0, double y0, double x1, double y1) {


        if (p == null) {
            throw new IllegalArgumentException();
        }

        if (root == null) {
            size++;
            return new Node(p, new RectHV( x0, y0, x1, y1));
        }

        else if (root.p.equals(p)) {
            return root;
        }

        else if(orient == true) {
            if(root.p.y() > p.y()) 
                root.left = add(root.left, p, !orient, x0, y0, x1, root.p.y());
            else 
                root.right = add(root.right, p, !orient, x0, root.p.y(), x1, y1);
        }

        else if(orient == false) {

            if(root.p.x() > p.x()) 
                root.left = add(root.left, p, !orient, x0, y0, root.p.x(), y1);

            else 
                root.right = add(root.right, p, !orient, root.p.x(),y0, x1, y1);
        }

        return root;

    }

    public boolean contains(Point2D p) {
        // does the set contain point p?

        return search(root, p, false);
    }

    private boolean search(Node root, Point2D p, boolean orientation) {
        if (p == null) {
            throw new IllegalArgumentException();
        }

        boolean exists = false;

        if (root == null) {
            return false;
        }

        else if (root.p.x() == p.x() && root.p.y() == p.y() )
            return true;



        else if(orientation == true) {
            if(root.p.y() > p.y())
                exists = search(root.left, p, !orientation);
            else
                exists = search(root.right, p, !orientation);
        }

        else if(orientation == false) {
            if(root.p.x() > p.x())
                exists = search(root.left, p, !orientation);
            else
                exists = search(root.right, p, !orientation);
        }

        return exists;

    }

    public void draw() {
        // draw all points to standard draw 
        RectHV rect = new RectHV(0.0, 0.0, 1.0, 1.0);
        rect.draw();
        drawPoint(root, false);

    }

    private void drawPoint(Node toDraw, boolean orientation) {

        if (toDraw == null)
            return;

        StdDraw.setPenRadius();
        if (orientation == true) {
            StdDraw.setPenColor(StdDraw.BLUE);
            Point2D tmp;

            tmp = new Point2D(toDraw.boundary.xmin(), toDraw.p.y());

            tmp.drawTo(new Point2D(toDraw.boundary.xmax(), toDraw.p.y()));
        }

        else if (orientation == false) {
            StdDraw.setPenColor(StdDraw.RED);
            Point2D tmp;

            tmp = new Point2D(toDraw.p.x(), toDraw.boundary.ymin());

            tmp.drawTo(new Point2D(toDraw.p.x(), toDraw.boundary.ymax()));
        }

        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        toDraw.p.draw();


        drawPoint(toDraw.left, !orientation);
        drawPoint(toDraw.right, !orientation);


    }


    public Iterable<Point2D> range(RectHV rect) {
        // all points that are inside the rectangle (or on the boundary) 

        if (rect == null)
            throw new IllegalArgumentException();
        
        ArrayList<Point2D> inRange = new ArrayList<Point2D>();

        range(root, rect, inRange, false);

        return inRange;
    }

    private void range(Node root, RectHV rect, ArrayList<Point2D> points, boolean orientation) {

        if (root == null)
            return;

        if (rect.intersects(root.boundary)) {

            if(rect.contains(root.p))
                points.add(root.p);
            range(root.left, rect, points, !orientation);
            range(root.right, rect, points, !orientation);
        }

    }

    public Point2D nearest(Point2D p) {
        // a nearest neighbor in the set to point p; null if the set is empty 
        if (p == null)
            throw new IllegalArgumentException();
        
        if(root == null)
            return null;
        
        return nearest(root, p, new Point2D(Double.MAX_VALUE, Double.MAX_VALUE), false);

    }

    private Point2D nearest(Node root, Point2D p, Point2D champ, boolean orientation) {

        if(root == null)
            return champ;

        if(root.p.distanceSquaredTo(p) < champ.distanceSquaredTo(p)) { 
            champ = root.p;

        }

        if(root.boundary.distanceSquaredTo(p) < champ.distanceSquaredTo(p)) {


            if (orientation == false) {
                if (root.p.x() > p.x()) {
                    champ = nearest(root.left, p, champ, !orientation);
                    champ = nearest(root.right, p, champ, !orientation);   
                }
                else {
                    champ = nearest(root.right, p, champ, !orientation);   
                    champ = nearest(root.left, p, champ, !orientation);

                }

            }
            else {
                if (root.p.y() > p.y()) {
                    champ = nearest(root.left, p, champ, !orientation);
                    champ = nearest(root.right, p, champ, !orientation);    

                }
                else {
                    champ = nearest(root.right, p, champ, !orientation);    
                    champ = nearest(root.left, p, champ, !orientation);

                }
            }
        }
        return champ;

    }

    public static void main(String[] args) {

    }
}
