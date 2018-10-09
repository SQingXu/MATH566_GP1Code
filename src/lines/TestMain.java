package lines;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;

public class TestMain extends JFrame{
	public InsertLine il;
	public Point start;
	public Point end;
	public TestMain() {
		start = new Point(0.5, 0.1);
		Point next = new Point(0.6, 0.1);
		Line dir = new Line(start, next);
		il = new InsertLine(start, dir);
		end = il.intersectCircle();
		System.out.println("end point x: " + end.x + " y: " + end.y );
		System.out.println("distance from original point: " + end.distanceTo(new Point(0,0)));
		
		this.setTitle("frame");
		this.setSize(960, 960);
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public void paint(Graphics g) {
		int scale = 100;
		double radius = 1.0/3.0;
		for(Point p: il.circles) {
			drawCircle(g, p, radius, scale);
		}
		for(RestrictedLine rl: il.lines) {
			drawLine(g, rl, scale);
		}
		g.setColor(Color.BLUE);
		drawPoint(g, start, scale, 10);
		g.setColor(Color.CYAN);
		drawPoint(g, end, scale, 10);
	}
	
	private void drawCircle(Graphics g, Point c, double radius, int scale) {
		int scaled_r = (int)(radius * scale);
		int cx = (int)((c.x + il.offsetX) * scale);
		int cy = (int)((c.y + il.offsetY) * -1 * scale);
		g.drawOval( cx - scaled_r, cy - scaled_r, scaled_r * 2, scaled_r *2);
	}
	
	private void drawLine(Graphics g, RestrictedLine rl, int scale) {
		rl.addOffset(il.offsetX, il.offsetY);
		rl.scale(scale);
		g.drawLine((int)rl.start.x, (int)rl.start.y, (int)rl.end.x,  (int)rl.end.y);
	}
	
	private void drawPoint(Graphics g, Point p, int scale, int size) {
		g.fillOval((int)(scale*(p.x + il.offsetX))-size/2, (int)(-1* scale*(p.y + il.offsetY))-size/2, size, size);
	}
	
	
	public static void main(String[] args) {
		
		TestMain frame = new TestMain();
		
	}
}
