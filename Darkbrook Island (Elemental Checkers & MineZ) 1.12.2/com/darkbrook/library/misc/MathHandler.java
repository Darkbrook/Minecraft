package com.darkbrook.library.misc;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

	public static double getDistance(double x1, double y1, double x2, double y2) {
		double x = Math.pow((x2 - x1), 2);
		double y = Math.pow((y2 - y1), 2);
		return Math.sqrt(x + y);
	}
	
	public static int getRandomNumber(int min, int max) {
		return RANDOM.nextInt((max - min) + 1) + min;
	}
	
	public static int getDivision(double a, double b) {
		double division = (double) (a / b);
		return (int) division;
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
