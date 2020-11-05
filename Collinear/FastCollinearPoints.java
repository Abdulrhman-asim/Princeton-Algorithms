import java.util.ArrayList;
import java.util.Arrays;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class FastCollinearPoints {

    private LineSegment[] segments;

    public FastCollinearPoints(Point[] points) {

        checkException(points);

        ArrayList<LineSegment> tmpSegments = new ArrayList<LineSegment>();
        int n = points.length;


        Point[] tmpPoints = Arrays.copyOf(points, points.length);
        
        for (int i = 0; i < n; i++) {

            Arrays.sort(tmpPoints, points[i].slopeOrder());
            int count = 1;
            double tmpSlope = points[i].slopeTo(tmpPoints[0]);
            int j;
            for(j = 1; j < tmpPoints.length; j++) {
                if (points[i].slopeTo(tmpPoints[j]) == tmpSlope) {
                    count++;
                    continue;
                }
                else { 
                    if (count >= 3) {
                        Arrays.sort(tmpPoints, j - count, j);
                        if (points[i].compareTo(tmpPoints[j - count]) < 0)
                            tmpSegments.add(new LineSegment(points[i], tmpPoints[j-1]));
                    }

                    tmpSlope = points[i].slopeTo(tmpPoints[j]);
                    count = 1;
                }
            }
            if (count >= 3) {
                Arrays.sort(tmpPoints, j - count, j);
                if (points[i].compareTo(tmpPoints[j - count]) < 0)
                    tmpSegments.add(new LineSegment(points[i], tmpPoints[j-1]));
            }

        }

        segments = tmpSegments.toArray(new LineSegment[tmpSegments.size()]);
    }

    public int numberOfSegments() {
        return segments.length;
    }

    public LineSegment[] segments() {
        // the line segments
        return segments.clone();
    }

    public static void main(String[] args) {

        // For Debugging Purposes

        //        Point[] points = new Point[8];
        //        points[0] = new Point(10000, 0);
        //        points[1] = new Point(0, 10000);
        //        points[2] = new Point(3000, 7000);
        //        points[3] = new Point(7000, 3000);
        //        points[4] = new Point(20000, 21000);
        //        points[5] = new Point(3000, 4000);
        //        points[6] = new Point(14000, 15000);
        //        points[7] = new Point(6000, 7000);
        //        FastCollinearPoints f = new FastCollinearPoints(points);

        //     
        //         read the n points from a file;

                        In in = new In(args[0]);
                        int n = in.readInt();
                        Point[] points = new Point[n];
                        for (int i = 0; i < n; i++) {
                            int x = in.readInt();
                            int y = in.readInt();
                            points[i] = new Point(x, y);
                        }
                
                        // draw the points
                        StdDraw.enableDoubleBuffering();
                        StdDraw.setXscale(0, 32768);
                        StdDraw.setYscale(0, 32768);
                        for (Point p : points) {
                            p.draw();
                        }
                        StdDraw.show();
                
                        // print and draw the line segments
                        FastCollinearPoints collinear = new FastCollinearPoints(points);
                        for (LineSegment segment : collinear.segments()) {
                            StdOut.println(segment);
                            segment.draw();
                        }
                        StdDraw.show();
    }

    private void checkException(Point[] points) {
        if (points == null) throw new java.lang.IllegalArgumentException();

        for (int i = 0; i < points.length; i++) {
            if (points[i] == null)
                throw new java.lang.IllegalArgumentException();
            for (int j = i + 1; j< points.length; j++) {
                if (points[j] == null)
                    throw new java.lang.IllegalArgumentException();
                if (points[i].compareTo(points[j]) == 0)
                    throw new java.lang.IllegalArgumentException();
            }
        }
    }

}