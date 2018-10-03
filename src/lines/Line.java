package lines;

public class Line {
	//with direction from a to b
	public Point a, b;
	public double slope, intercept;
	public boolean vertical = false;
	public Line(Point a, Point b) {
		
		this.a = new Point(a.x, a.y);
		this.b = new Point(b.x, b.y);
		if(a.x == b.x) {
			vertical = true;
		}else {
			slope = (a.y - b.y)/(a.x - b.x);
			intercept = a.y - slope * a.x;
		}
	}
	
	public Line(double slope, double intercept) {
		this.vertical = false;
		this.slope = slope;
		this.intercept = intercept;
		double xa = 0, xb = 1;
		double ya = xa * slope + intercept;
		double yb = xb * slope + intercept;
		this.a = new Point(xa, ya);
		this.b = new Point(xb, yb);
	}
	
	public boolean intersectWLine(Line l, Point intersect) {
		if(l.vertical && this.vertical) {
			return false;
		}
		if(!l.vertical && !this.vertical && l.slope == this.slope) {
			return false;
		}
		
		double p_x, p_y;
		if(l.vertical || this.vertical) {
			
			if(l.vertical) {
				p_y = this.slope * l.a.x + this.intercept;
			    p_x = l.a.x;
			}else {
				p_y = l.slope * a.x + l.intercept;
			    p_x = a.x;
			}
		}else {
			p_x = (intercept - l.intercept)/(l.slope - slope);
			p_y = (p_x * slope) + intercept;
		}
		intersect.x = p_x;
		intersect.y = p_y;
		return true;
		
	}
	
	public boolean sameLine(Line l) {
		if(l.vertical && this.vertical) {
			if(l.a.x == a.x) {
				return true;
			}else {
				return false;
			}
		}
		if((l.vertical && !this.vertical) || (!l.vertical && this.vertical)) {
			return false;
		}
		if(l.slope == slope && l.intercept == intercept) {
			return true;
		}
		return false;
	}
	
	public Point moveByDist(Point p, double dist) {
		if(vertical) {
			double move_dist = (a.y > b.y)?-dist:dist;
			return new Point(p.x, p.y + move_dist);
		}
		double dist_ab = a.distanceTo(b);
		double dist_dx = (dist/dist_ab)*(b.x - a.x);
		double dist_dy = (dist/dist_ab)*(b.y - a.y);
		return new Point(p.x + dist_dx, p.y + dist_dy);
		
	}
	
	public double distanceToP(Point p, Point intersect) {
		if(vertical) {
			intersect.x = a.x;
			intersect.y = p.y;
			return Math.abs(p.x - a.x);
		}
		Line vert_line;
		if(slope != 0) {
			double vert_slope = -1 * (1/slope);
			double vert_intercept = p.y - vert_slope * p.x;
		    vert_line  = new Line(vert_slope, vert_intercept);
		}else {
			
			Point next = new Point(p.x, p.y +1);
			vert_line = new Line(p, next);
		}
		
//		double i_x = (vert_slope - slope)/(intercept - vert_intercept);
//		double i_y = (i_x * slope) + intercept;
		Point inter = new Point(0,0);
		//intersect with relative vertical line no worries about parallel
		vert_line.intersectWLine(this, inter);
		intersect.x = inter.x;
		intersect.y = inter.y;
		return intersect.distanceTo(p);
	}
	
	
}
