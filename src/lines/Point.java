package lines;

public class Point {
	public double x;
	public double y;
	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}
	public Point() {
		this.x = 0;
		this.y = 0;
	}
	
	public double distanceTo(Point p) {
		return Math.sqrt(Math.pow(p.y-y, 2) + Math.pow(p.x-x, 2));
	}
	
	public void copyValFrom(Point p) {
		this.x = p.x;
		this.y = p.y;
	}
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
	
	@Override
	public boolean equals(Object o) {
		Point p = (Point)o;
		if(p == null) {
			return false;
		}
		if(p.x == this.x && p.y == this.y) {
			return true;
		}
		return false;
	}
	
	public Point minus(Point p) {
		return new Point(this.x - p.x, this.y - p.y);
	}
	
}
