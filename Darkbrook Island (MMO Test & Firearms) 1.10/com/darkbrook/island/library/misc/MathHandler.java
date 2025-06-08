package com.darkbrook.island.library.misc;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Location;

public class MathHandler {
	
	public static final Random RANDOM = new Random();

	public static double getRawPercent(int value, int constant) {
		double percent = ((double) value / (double) constant) * 100;
		return percent;
	}
	
	public static int getPercent(int value, int constant) {
		return (int) Math.round(getRawPercent(value, constant));
	}
	
	public static float getFloatPercent(double value, double constant) {
		return (float) (value / constant);
	}
	
	public static double distance3D(double x1, double y1, double z1, double x2, double y2, double z2) {
		double x = Math.pow((x2 - x1), 2);
		double y = Math.pow((y2 - y1), 2);
		double z = Math.pow((z2 - z1), 2);
		return Math.sqrt(x + y + z);
	}

	public static double distance2D(double x1, double y1, double x2, double y2) {
		double x = Math.pow((x2 - x1), 2);
		double y = Math.pow((y2 - y1), 2);
		return Math.sqrt(x + y);
	}
	
	public static double distance3D(Location from, Location to) {
		return distance3D(from.getX(), from.getY(), from.getZ(), to.getX(), to.getY(), to.getZ());
	}
	
	public static double distance2DXZ(Location from, Location to) {
		return distance2D(from.getX(), from.getZ(), to.getX(), to.getZ());
	}	
	
	public static double distance2DXY(Location from, Location to) {
		return distance2D(from.getX(), from.getY(), to.getX(), to.getY());
	}	
	
	public static int getRandomNumber(int min, int max) {
		return RANDOM.nextInt((max - min) + 1) + min;
	}
	
	public static double getSlope(double x1, double y1, double x2, double y2) {
		return (y2 - y1) / (x2 - x1);
	}
	
	public static double getGCF(double numerator, double denominator) {
		if(denominator == 0) return numerator;
		return getGCF(denominator, numerator % denominator);
	}
	
	public static double getNumerator(double numerator, double denominator) {
		double gcf = getGCF(numerator, denominator);
		return numerator / gcf;
	}
	
	public static double getDenominator(double numerator, double denominator) {
		double gcf = getGCF(numerator, denominator);
		return denominator / gcf;
	}
	
	public static String getGCFString(double numerator, double denominator) {
		return getNumerator(numerator, denominator) + "/" + getDenominator(numerator, denominator);
	}
	
	public static List<Point> getBresenhamLine(int x0, int y0, int x1, int y1) {     
    	
        List<Point> line = new ArrayList<Point>();
 
        int dx = Math.abs(x1 - x0);
        int dy = Math.abs(y1 - y0);
 
        int sx = x0 < x1 ? 1 : -1; 
        int sy = y0 < y1 ? 1 : -1; 
 
        int err = dx - dy;
        int e2;
 
        while (true)  {
        	
            line.add(new Point(x0, y0));
            if (x0 == x1 && y0 == y1) break;
 
            e2 = 2 * err;
            if (e2 > -dy) {
                err = err - dy;
                x0 = x0 + sx;
            }
 
            if (e2 < dx) {
                err = err + dx;
                y0 = y0 + sy;
            }
            
        }      
        
        return line;
        
    }
	
}
