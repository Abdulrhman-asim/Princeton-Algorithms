
import java.util.ArrayList;
import java.util.Arrays;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints {

    private LineSegment[] segments;

    public BruteCollinearPoints(Point[] points) {
        // finds all line segments containing 4 points
        
        checkException(points);

        ArrayList<LineSegment> tmpSegments = new ArrayList<LineSegment>();
        int n = points.length;
        Point[] tmp = points.clone();
        Arrays.sort(tmp);
        
        for (int i = 0; i < n-3; i++)
            for (int j = i + 1; j < n-2; j++)
                for (int k = j + 1; k < n-1; k++) {
                    if (tmp[i].slopeTo(tmp[j]) != tmp[i].slopeTo(tmp[k]))
                        continue;
                    for (int l = k + 1; l < n; l++) {
                        if (tmp[i].slopeTo(tmp[j]) == tmp[i].slopeTo(tmp[l]))
                            tmpSegments.add(new LineSegment(tmp[i], tmp[l]));
                    }
                }
        segments = tmpSegments.toArray(new LineSegment[tmpSegments.size()]);
        
    }

    public int numberOfSegments() {
        return segments.length;
    }
    public LineSegment[] segments() {
        return segments.clone();
    }
    
    private void checkException(Point[] points) {
        if (points == null) throw new java.lang.IllegalArgumentException();
        
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null)
                throw new java.lang.IllegalArgumentException();
            for (int j = i + 1; j < points.length; j++) {
                if (points[j] == null)
                    throw new java.lang.IllegalArgumentException();
                if (points[i].compareTo(points[j]) == 0)
                    throw new java.lang.IllegalArgumentException();
            }
        }
    }

    public static void main(String[] args) {
        
        Point x = new Point(1,2);
        Point y = new Point(2,1);
        StdOut.println(x.compareTo(y));
//        // read the n points from a file
//        In in = new In(args[0]);
//        int n = in.readInt();
//        Point[] points = new Point[n];
//        for (int i = 0; i < n; i++) {
//            int x = in.readInt();
//            int y = in.readInt();
//            points[i] = new Point(x, y);
//        }
//
//        // draw the points
//        StdDraw.enableDoubleBuffering();
//        StdDraw.setXscale(0, 32768);
//        StdDraw.setYscale(0, 32768);
//        for (Point p : points) {
//            p.draw();
//        }
//        StdDraw.show();
//
//        // print and draw the line segments
//        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
//        for (LineSegment segment : collinear.segments()) {
//            StdOut.println(segment);
//            segment.draw();
//        }
//        StdDraw.show();
    }
}