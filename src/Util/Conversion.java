package Util;

import java.util.ArrayList;

import Model.CoordGeo;
import Model.Point;

public class Conversion {

	
	
	 
	public static Point gps2xy(double lat, double longi)
	{
		double lat0=18.750310;
		double longi0=-9.107666;
		double X= Math.cos(lat0)*(longi- longi0);
		double Y=(lat-lat0);
		return new Point(X,Y);
	
	}
	
	public static CoordGeo xy2gps(double x, double y) {
		
		double lat0=18.750310;
		double longi0=-9.107666;
		
		double longi = x/Math.cos(lat0) + longi0;
		double latt = y + lat0;
		return new CoordGeo(latt,longi);
		
	}
	

	
	public static Point xy2pixel(ArrayList<Point> points) {
		double minx=points.get(0).x;
		double miny=points.get(0).y;
		double maxx=minx;
		double maxy=miny;
		
		
		int i=0;
		for (i=0;i<points.size();i++) {
			if (points.get(i).x<minx) minx= points.get(i).x;
			if (points.get(i).y<miny) miny= points.get(i).y;
			if (points.get(i).x>maxx) maxx= points.get(i).x;
			if (points.get(i).y>maxy) maxy= points.get(i).y;
		}
	
		System.out.println("minx: "+minx);
		System.out.println("maxx: "+maxx);
		System.out.println("miny: "+miny);
		System.out.println("maxy: "+maxy);
		
		double dx=distance(new Point(minx,0),new Point(maxx,0));
		System.out.println("dx: "+dx);
		double dy=distance(new Point(0,miny),new Point(0,maxy));
		System.out.println("dy: "+dy);
		double width=600;
		double height=400;
		double echellex=width/dx;
		double echelley=height/dy;
		System.out.println(echellex+"  "+ echelley);
		for (i=0;i<points.size();i++) {
			System.out.println(points.get(i).x+ " "+points.get(i).y);
			points.get(i).x = (points.get(i).x-minx) *echellex+300;
			points.get(i).y = (points.get(i).y-miny) *echelley+50;
			System.out.println(points.get(i).x+ " "+points.get(i).y);
		}		
		return (new Point(echellex,echelley));
	}
	
	public static double distance(Point p1, Point p2) {
		return Math.sqrt((p1.x - p2.x)*(p1.x-p2.x)+(p1.y-p2.y)*(p1.y - p2.y));
	}
	
	
	

	
}
