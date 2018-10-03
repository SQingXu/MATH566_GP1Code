package lines;

public class RestrictedLine {
	public Point start;
	public Point end;
	public RestrictedLine(Point s, Point e) {
		start = new Point();
		end = new Point();
		start.copyValFrom(s);
		end.copyValFrom(e);
	}
	
	public void addOffset(double offsetX, double offsetY) {
		start.x += offsetX;
		end.x += offsetX;
		start.y += offsetY;
		end.y += offsetY;
	}
	
	public void scale(double s) {
		start.x *= s;
		start.y *= -1 * s;
		end.x *= s;
		end.y *= -1 * s;
	}
}
