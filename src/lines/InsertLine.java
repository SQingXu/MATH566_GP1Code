package lines;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public class InsertLine {
	public Point start;
	public Line dir;
	public List<Point> circles;
	public List<RestrictedLine> lines;
	public double offsetX = 0;
	public double offsetY = 0;
	public InsertLine(Point start, Line l) {
		this.start = new Point(start.x, start.y);
		this.dir = l;
		this.circles = new ArrayList<>();
		this.lines = new ArrayList<>();
	}
	
	public Point intersectCircle() {
		double move_distance = 0;
		Point intersect = new Point();
		Point circle = new Point();
		boolean find_intersect = false;
		while(move_distance < 10) {
			do {
				Point end = dir.moveByDist(start, 1);
				//System.out.println("dir slope is: " +  dir.slope + " and move from " + dir.a.toString() + " to " +
				//dir.b.toString() + " intercept is " + dir.intercept);
				//System.out.println("check line between " + start.toString() + " and " + end.toString());
				if(checkFourSurroundingCircles(start, end, intersect, circle)) {
					//System.out.println("intersect with circle " + circle.toString() +  " at " + intersect.toString() + 
					//		" with distance to circle: " + intersect.distanceTo(circle));
					find_intersect = true;
					break;
				}
				lines.add(new RestrictedLine(start, end));
				start.copyValFrom(end);
				move_distance += 1;
			}while(move_distance < 10);
			
			if(find_intersect) {
				//find intersect lines start from start and intersect with circle at intersect
				//change direction and start point
				//take symmetry relative to line circle to intersect
				
				//also increase the distance
				move_distance += start.distanceTo(intersect);
				//System.out.println("move point from " + start.toString() + " to " + intersect.toString());
				RestrictedLine rl = new RestrictedLine(start, intersect);
				lines.add(rl);
				//System.out.println("distance is now: " + move_distance);
				if(move_distance > 10) {
					break;
				}
				Line circleC_to_inter = new Line(circle, intersect);
				//System.out.println("slope of circle_intersect is: " + circleC_to_inter.slope);
				Point middle = new Point();
				double dist_s_to_mid = circleC_to_inter.distanceToP(start, middle);
				//System.out.println("middle: " + middle.toString());
				Line ctoi_vert = new Line(start, middle);
				Point symmetry = ctoi_vert.moveByDist(start, 2*dist_s_to_mid);
				//System.out.println("symmetry: " + symmetry.toString());
				Line new_dir = new Line(intersect, symmetry);
				dir = new_dir;
				start.copyValFrom(intersect);
				find_intersect = false;
			}
			
		}
		return dir.moveByDist(intersect, 10 - move_distance);
		
	}
	
	public boolean checkFourSurroundingCircles(Point p1, Point p2, Point intersect, Point circle) {
		boolean find = false;
		double radius = ((double)1.0)/((double)3.0);
		double min_dist_to_p1 = Integer.MAX_VALUE;
		Point _intersect = new Point();
		Point _circle = new Point();
		for(int p = 0; p < 2; p++) {
			Point pt = (p == 0)?p1:p2;
			for(int i = 0; i < 2; i++) {
				for(int j = 0; j < 2; j++) {
					int cx = (pt.x < 0)? (int)pt.x + i -1: (int)pt.x + i;
					int cy = (pt.y < 0)? (int)pt.y + j -1: (int)pt.y + j;
					Point circleC = new Point(cx, cy);
					if(!circles.contains(circleC)) {
						circles.add(circleC);
						if(circleC.x - radius + offsetX < 0) {
							offsetX = Math.abs(circleC.x - radius);
						}
						if(circleC.y + radius + offsetY > 0) {
							offsetY = -1 * Math.abs(circleC.y + radius);
						}
					}
					Point inter = new Point();
					
					//System.out.println("test circle " + circleC.toString() + " getting from point " + pt);
					if(dir.distanceToP(circleC, inter) < radius) {
						//intersect
						double dist_p1p2 = p1.distanceTo(p2);
						//check if intersection with circle center is between p1 and p2
						if((inter.distanceTo(p1) <= dist_p1p2 && inter.distanceTo(p2) <= dist_p1p2) || 
								(inter.distanceTo(p1) > inter.distanceTo(p2))) {
							//find the point intersect with circle and close to p1
							double dist_interc = -1 * Math.sqrt(Math.pow(radius,2) - 
									Math.pow(circleC.distanceTo(inter), 2));
							Point _intersect_candid = dir.moveByDist(inter, dist_interc);
							if(_intersect_candid.distanceTo(p1) < min_dist_to_p1) {
								find = true;
								min_dist_to_p1 = _intersect_candid.distanceTo(p1);
								_intersect = _intersect_candid;
								_circle = circleC;
							}
						}
					}
				}
			}
		}
		intersect.copyValFrom(_intersect);
		circle.copyValFrom(_circle);
		return find;
	}
	
}
