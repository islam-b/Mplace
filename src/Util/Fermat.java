package Util;

import java.util.ArrayList;

import Model.CoordGeo;
import Model.Point;

public class Fermat {
	
	public ArrayList<CoordGeo> getFermatPoint(ArrayList<Point> points) {
		Point p0 = average(points);
		double sumdis = summation(p0,points);
		double step = sumdis / points.size();
		double rate = 0.99;
		double threshold = step/100;
		Point tp=new Point(0,0);
		int index=0;
		double newsum;
		int i=0;
		double x,y;
		ArrayList<Point> pps=new ArrayList<Point>();
		
		ArrayList<Point> targets=new ArrayList<Point>();
		while (step>threshold) {
			
			x=p0.x;
			y=p0.y;
			
			targets.add(new Point(x+step,y+step));
			targets.add(new Point(x+step,y-step));
			targets.add(new Point(x-step,y+step));
			targets.add(new Point(x-step,y-step));
			
			for (i=0; i<targets.size();i++) {
				tp = targets.get(i);
				newsum = summation (tp,points);
				if (newsum < sumdis) {
					p0.x = tp.x;
					p0.y = tp.y;
					pps.add(new Point(p0.x,p0.y));
					sumdis=newsum;
				}
			}
			step = step*rate;
			index++;	
		}
		ArrayList<CoordGeo> best_points=new ArrayList<CoordGeo>();
		Point tst=new Point(0,0);
		for (i=0;i<11;i++) {
			tst=pps.get(pps.size()-i-1);
			best_points.add(Conversion.xy2gps(tst.x, tst.y));
		}
		return best_points;
	}
	
	public Point average(ArrayList<Point> points) {
		Point point=new Point(0,0);
		double x=0, y=0;
		int size = points.size();
		int i=0;
		for (i=0; i<size; i++) {
			point = points.get(i);
			x=(double) x + point.x;
			y=(double) y + point.y;
		}
		return new Point(x,y);
	}
	
	public double summation (Point P, ArrayList<Point> points) {
		Point point = new Point(0,0);
		double sum=0;
		
		int i=0;
		for (i=0;i<points.size();i++) {
			point=points.get(i);
			sum=sum+distance(P,point);
		}
		return sum;
	}
	
	public double distance(Point p1, Point p2) {
		return Math.sqrt((p1.x - p2.x)*(p1.x-p2.x)+(p1.y-p2.y)*(p1.y - p2.y));
	}

}
